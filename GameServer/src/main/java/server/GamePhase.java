package server;

import client.Client;
import dbconnection.Difficulty;
import dbconnection.Question;
import messages.*;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;


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
                serverState.broadcast( serverState.createPlayerlistMessage() );
            }
            if (incomingMessage.getMessage().getMessageType() == MessageType.PLAYER_READY) {
                PlayerReadyMessage playerReady = (PlayerReadyMessage) incomingMessage.getMessage();
                Client sourceClient = incomingMessage.getSourceClient();
                sourceClient.setReadyState(ReadyState.READY);
                System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name() +
                        " from id: " + sourceClient.getPlayerID() );

                // Send Player List to everyone in the playerList.
                serverState.broadcast( serverState.createPlayerlistMessage() );

                if (Server.getServerState().everyPlayerReady()) {
                	System.out.println("All players ready");
                    Server.setStillConnectClients( false );
                    // Wait for 30 sec.
                    try {
                        Thread.sleep(Server.getConfiguration().gameStartTimeout );

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Send scoreboard before game starts.
                    serverState.broadcast( serverState.createScoreboardMessage() );
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
            // Wait for 2 seconds to ensure there is enough time between two rounds
            try {
                Thread.sleep( 2000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
            serverState.broadcast( CSAMessage );
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
                    serverState.broadcast( questionMessage );
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
                int categoryIndex = categorySelectionMessage.getCategoryIndex();
                if (categoryIndex < serverState.getActualCategorySelection().size() && categoryIndex > 0 ) {
                    // OK
                } else {
                    Random random = new Random();
                    categoryIndex = random.nextInt( 4 );
                }
                Question question = serverState.getActualCategorySelection().get(categoryIndex);
                QuestionMessage questionMessage = new QuestionMessage(
                        Server.getConfiguration().questionTimeout,
                        question);
                serverState.setActualQuestion(question);
                serverState.returnQuestions();
                serverState.broadcast( questionMessage );
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

                    serverState.broadcast( answerResultMessage );
                    // Divided in two loops to not send both messages at once.
                    serverState.broadcast( serverState.createScoreboardMessage() );
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
                serverState.broadcast( BZMessage );
                Server.startTimeoutTimer( Server.getConfiguration().answerTimeout, MessageType.ANSWER );
                return QUESTION_PLAY_PHASE;
            }

            // Check if screwing player has screws left.
            if ((incomingMessage.getMessage().getMessageType() == MessageType.SCREW) &&
                    (incomingMessage.getSourceClient().getScrewsLeft() > 0)) {
                ScrewMessage screwMessage = (ScrewMessage) incomingMessage.getMessage();

                System.out.println("Received: " + incomingMessage.getMessage().getMessageType().name()+
                        " from id: " + incomingMessage.getSourceClient().getPlayerID()+
                        " screwing id: " + screwMessage.getScrewedPlayerId());

                // If a Client want to screw himself, ignore message
                if (screwMessage.getScrewedPlayerId() == incomingMessage.getSourceClient().getPlayerID()) {
                    return this;
                }
                // Check if screw is valid
                boolean screwValid = false;
                for (Client client: serverState.getPlayerList()) {
                    if ( client.getPlayerID() == screwMessage.getScrewedPlayerId() &&
                            client.getReadyState() != ReadyState.DISCONNECTED){
                        screwValid = true;
                    }
                }
                System.out.println( "Screw valid: " + screwValid );
                if (!screwValid) {
                    return this;
                }

                Server.stopTimeoutTimer();
            	incomingMessage.getSourceClient().decreaseScrewsLeft();

                ScrewResultMessage SRMessage = new ScrewResultMessage(
                        incomingMessage.getSourceClient().getPlayerID(),
                        screwMessage.getScrewedPlayerId(),
                        Server.getConfiguration().answerTimeout);

                serverState.setAnswerGiver(screwMessage.getScrewedPlayerId());
                serverState.setScrewer(incomingMessage.getSourceClient().getPlayerID());
                serverState.broadcast( SRMessage );
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
                    serverState.broadcast( answerResultMessage );
                    serverState.broadcast( serverState.createScoreboardMessage() );
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
                Server.stopTimeoutTimer();
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

                serverState.broadcast( answerResultMessage );
                serverState.broadcast( serverState.createScoreboardMessage() );
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
            ServerState serverState = Server.getServerState();
            GameEndMessage gameEndMessage = new GameEndMessage();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverState.broadcast( gameEndMessage );
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
