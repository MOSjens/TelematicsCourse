package server;

import client.Client;
import dbconnection.Difficulty;
import dbconnection.Question;
import messages.*;
import java.util.Random;


public enum GamePhase {

    // Phase until all clients are connected.
    STARTUP_PHASE() {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();

            if (incomingMessage.getMessage().getMessageType() == MessageType.SIGN_ON) {
                SignOnMessage signOn = (SignOnMessage) incomingMessage.getMessage();
                System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()
                        + " Alias = " + signOn.getPlayerAlias());
                Client sourceClient = incomingMessage.getSourceClient();
                sourceClient.setAlias(signOn.getPlayerAlias());
                serverState.addPlayer(sourceClient);
                System.out.println(sourceClient.getAlias());
                // Send SignOn Response to clint
                sourceClient.sendMessage(serverState.createSignOnResponseMessage(sourceClient));

                // Send Player List to everyone in the playerList.
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(serverState.createPlayerlistMessage());
                }
            }
            if (incomingMessage.getMessage().getMessageType() == MessageType.PLAYER_READY) {
                PlayerReadyMessage playerReady = (PlayerReadyMessage) incomingMessage.getMessage();
                Client sourceClient = incomingMessage.getSourceClient();
                sourceClient.setReadyState(ReadyState.READY);
                System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name() +
                        " from id: " + sourceClient.getPlayerID() );

                // Send Player List to everyone in the playerList.
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(serverState.createPlayerlistMessage());
                }
                
                if (Server.getServerState().everyPlayerReady()) {
                	System.out.println("All players ready");
                    // Wait for 30 sec.
                    try {
                        Thread.sleep(Server.getConfiguration().gameStartTimeout );

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Send scoreboard before game starts.
                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(serverState.createScoreboardMessage());
                    }
                    System.out.println("Start Game");
                    return GAME_PHASE.nextPhase(null);
                }
            }

            return this;
        }
    },

    GAME_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();
            // Every round an other Player is allowed to choose the category.
            serverState.setCategorySelector(serverState.getRoundsLeft() % serverState.getPlayerList().size());
            // Pick 4 questions with different Categories
            serverState.setActualCategorySelection(serverState.getQuestionSample(4));
            System.out.println("Player selecting: " + serverState.getCategorySelector());
            // Send Category Selector Announcement.
            CategorySelectorAnnouncementMessage CSAMessage = serverState.createCategorySelectorAnnouncementMessage(
                    Server.getConfiguration().categoryTimeout,
                    serverState.getCategorySelector(),
                    serverState.getActualCategorySelection());
            for ( Client client: serverState.getPlayerList() ) {
                client.sendMessage(CSAMessage);
            }
            Server.startTimeoutTimer( Server.getConfiguration().categoryTimeout, MessageType.CATEGORY_SELECTION );
            return CATEGORY_SELECTION_PHASE;
        }
    },

    CATEGORY_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();

            // Timeout
            if (incomingMessage.getMessage().getMessageType() == MessageType.TIMEOUT) {
                TimeoutMessage timeoutMessage = (TimeoutMessage) incomingMessage.getMessage();
                // Check for correct timeout
                if (timeoutMessage.getReplacingMassage() == MessageType.CATEGORY_SELECTION) {
                    // Choose random category
                    Random random = new Random();
                    int randomCategory = random.nextInt( 4 );
                    System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name() +
                            "Index: " + randomCategory);
                    Question question = serverState.getActualCategorySelection().get( randomCategory );
                    QuestionMessage questionMessage = new QuestionMessage(
                            Server.getConfiguration().questionTimeout,
                            question);
                    serverState.setActualQuestion(question);
                    serverState.returnQuestions();
                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(questionMessage);
                    }
                    Server.startTimeoutTimer( Server.getConfiguration().questionTimeout, MessageType.BUZZ );
                    return PLAYER_SELECTION_PHASE;
                }
            }

            // Check if the right person responses.
            if ((incomingMessage.getMessage().getMessageType() == MessageType.CATEGORY_SELECTION) &&
                    (incomingMessage.getSourceClient().getPlayerID() == serverState.getCategorySelector())) {
                Server.stopTimeoutTimer();
                // Get selected question.
                CategorySelectionMessage categorySelectionMessage = (CategorySelectionMessage) incomingMessage.getMessage();
                System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name() +
                		"Index: " + categorySelectionMessage.getCategoryIndex());
                Question question = serverState.getActualCategorySelection().get(categorySelectionMessage.getCategoryIndex());
                QuestionMessage questionMessage = new QuestionMessage(
                        Server.getConfiguration().questionTimeout,
                        question);
                serverState.setActualQuestion(question);
                serverState.returnQuestions();
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(questionMessage);
                }
                Server.startTimeoutTimer( Server.getConfiguration().questionTimeout, MessageType.BUZZ );
                return PLAYER_SELECTION_PHASE;
            }
            return this;
        }
    },

    PLAYER_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();

            // Timeout
            if (incomingMessage.getMessage().getMessageType() == MessageType.TIMEOUT) {
                TimeoutMessage timeoutMessage = (TimeoutMessage) incomingMessage.getMessage();
                // Check for correct timeout
                if (timeoutMessage.getReplacingMassage() == MessageType.BUZZ) {
                    System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()+
                            " while waiting on: " + timeoutMessage.getReplacingMassage() );

                    serverState.decreaseRoundsLeft();
                    AnswerResultMessage answerResultMessage = new AnswerResultMessage(
                            serverState.getActualQuestion().getCorrectAnswerIndex(),
                            -1 );

                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(answerResultMessage);
                    }
                    // Divided in two loops to not send both messages at once.
                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(serverState.createScoreboardMessage());
                    }
                    if (serverState.getRoundsLeft() > 0) {
                        return GAME_PHASE.nextPhase(null);
                    } else {
                        return CLOSING_PHASE.nextPhase(null);
                    }
                }
            }

            if (incomingMessage.getMessage().getMessageType() == MessageType.BUZZ) {
                Server.stopTimeoutTimer();
            	System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()+
            			" from id: " + incomingMessage.getSourceClient().getPlayerID() );
                BuzzResultMessage BZMessage = new BuzzResultMessage(
                        incomingMessage.getSourceClient().getPlayerID(),
                        Server.getConfiguration().answerTimeout);
                serverState.setAnswerGiver(incomingMessage.getSourceClient().getPlayerID());
                serverState.setScrewer(-1); // Because Buzz round, there is no screwer.
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(BZMessage);
                }
                Server.startTimeoutTimer( Server.getConfiguration().answerTimeout, MessageType.ANSWER );
                return QUESTION_PLAY_PHASE;
            }

            // Check if screwing player has screws left.
            if ((incomingMessage.getMessage().getMessageType() == MessageType.SCREW) &&
                    (incomingMessage.getSourceClient().getScrewsLeft() > 0)) {
                Server.stopTimeoutTimer();
            	incomingMessage.getSourceClient().decreaseScrewsLeft();
                ScrewMessage screwMessage = (ScrewMessage) incomingMessage.getMessage();
            	System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()+
            			" from id: " + incomingMessage.getSourceClient().getPlayerID()+
            			" screwing id: " + screwMessage.getScrewedPlayerId());
                ScrewResultMessage SRMessage = new ScrewResultMessage(
                        incomingMessage.getSourceClient().getPlayerID(),
                        screwMessage.getScrewedPlayerId(),
                        Server.getConfiguration().answerTimeout);
                serverState.setAnswerGiver(screwMessage.getScrewedPlayerId());
                serverState.setScrewer(incomingMessage.getSourceClient().getPlayerID());
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(SRMessage);
                }
                Server.startTimeoutTimer( Server.getConfiguration().answerTimeout, MessageType.ANSWER );
                return QUESTION_PLAY_PHASE;
            }
            return this;
        }
    },

    QUESTION_PLAY_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();

            // Timeout
            if (incomingMessage.getMessage().getMessageType() == MessageType.TIMEOUT) {
                TimeoutMessage timeoutMessage = (TimeoutMessage) incomingMessage.getMessage();
                // Check for correct timeout
                if (timeoutMessage.getReplacingMassage() == MessageType.ANSWER) {
                    System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()+
                            " while waiting on: " + timeoutMessage.getReplacingMassage() );
                    serverState.decreaseRoundsLeft();
                    boolean answerCorrect = false;
                    int points = getPoints(serverState.getActualQuestion().getDifficulty(), answerCorrect);
                    // Change points of player who should answer
                    serverState.getPlayerByID(serverState.getAnswerGiver()).changeScore(points);
                    // Change points of screwing player
                    if (serverState.getScrewer() != -1) {   // Screw
                        serverState.getPlayerByID(serverState.getScrewer()).changeScore(points * -1);
                    }
                    AnswerResultMessage answerResultMessage = new AnswerResultMessage(
                            serverState.getActualQuestion().getCorrectAnswerIndex(),
                            -1 );
                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(answerResultMessage);
                    }
                    // Divided in two loops to not send both messages at once.
                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(serverState.createScoreboardMessage());
                    }
                    if (serverState.getRoundsLeft() > 0) {
                        return GAME_PHASE.nextPhase(null);
                    } else {
                        return CLOSING_PHASE.nextPhase(null);
                    }
                }
            }

            // Check if client is allowed to answer
            if ((incomingMessage.getMessage().getMessageType() == MessageType.ANSWER) &&
                    (incomingMessage.getSourceClient().getPlayerID() == serverState.getAnswerGiver()) ||
                    (incomingMessage.getMessage().getMessageType() == MessageType.TIMEOUT)) {
                AnswerMessage answerMessage = (AnswerMessage) incomingMessage.getMessage();
            	System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()+
            			" from id: " + incomingMessage.getSourceClient().getPlayerID()+
            			" with answer id: " + answerMessage.getAnswerId());
                serverState.decreaseRoundsLeft();

                boolean answerCorrect = (answerMessage.getAnswerId() == serverState.getActualQuestion().getCorrectAnswerIndex());
                int points = getPoints(serverState.getActualQuestion().getDifficulty(), answerCorrect);
                // change points of answering player
                incomingMessage.getSourceClient().changeScore(points);
                // Change points of screwing player
                if (serverState.getScrewer() != -1) {   // Screw
                    serverState.getPlayerByID(serverState.getScrewer()).changeScore(points * -1);
                }
                AnswerResultMessage answerResultMessage = new AnswerResultMessage(
                        serverState.getActualQuestion().getCorrectAnswerIndex(),
                        answerMessage.getAnswerId());

                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(answerResultMessage);
                }
                // Divided in two loops to not send both messages at once.
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(serverState.createScoreboardMessage());
                }
                if (serverState.getRoundsLeft() > 0) {
                    return GAME_PHASE.nextPhase(null);
                } else {
                    return CLOSING_PHASE.nextPhase(null);
                }
            }
            return this;
        }

    },

    CLOSING_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            GameEndMessage gameEndMessage = new GameEndMessage();
            for ( Client client: Server.getServerState().getPlayerList() ) {
                client.sendMessage(gameEndMessage);
            }
            return this;
        }
    };


    GamePhase () {

    }

    public abstract GamePhase nextPhase(IncomingMessage incomingMessage);

    private static int getPoints(Difficulty difficulty, Boolean answerCorrect) {
        switch(difficulty){
            case EASY:
                return answerCorrect ? 1 : -1;
            case MEDIUM:
                return answerCorrect ? 2 : -2;
            case HARD:
                return answerCorrect ? 3 : -3;
            default:
                return 0;
        }
    }

}
