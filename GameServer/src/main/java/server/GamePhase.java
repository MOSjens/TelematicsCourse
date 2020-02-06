package server;

import client.Client;
import dbconnection.Difficulty;
import dbconnection.Question;
import messages.*;

import java.util.ArrayList;


public enum GamePhase {

    // Phase until all clients are connected.
    STARTUP_PHASE() {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();

            if (incomingMessage.getMessage().getMessageType() == MessageType.SIGN_ON) {
                SignOnMessage signOn = (SignOnMessage) incomingMessage.getMessage();
                System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()
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
                System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()+ " from id: " + sourceClient.getPlayerID() );
                if (Server.getServerState().everyPlayerReady()) {
                    // Wait for 30 sec.
                    try {
                        Thread.sleep(Server.getConfiguration().gameStartTimeout * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Send scoreboard before game starts.
                    for ( Client client: serverState.getPlayerList() ) {
                        client.sendMessage(serverState.createScoreboardMessage());
                    }

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

            // Send Category Selector Announcement.
            CategorySelectorAnnouncementMessage CSAMessage = serverState.createCategorySelectorAnnouncementMessage(
                    Server.getConfiguration().categoryTimeout,
                    serverState.getCategorySelector(),
                    serverState.getActualCategorySelection());
            for ( Client client: serverState.getPlayerList() ) {
                client.sendMessage(CSAMessage);
            }
            return CATEGORY_SELECTION_PHASE;
        }
    },

    CATEGORY_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            // TODO Timeout !
            ServerState serverState = Server.getServerState();
            // Check if the right person responses.
            if ((incomingMessage.getMessage().getMessageType() == MessageType.CATEGORY_SELECTION) &&
                    (incomingMessage.getSourceClient().getPlayerID() == serverState.getCategorySelector())) {
                // Get selected question.
                CategorySelectionMessage categorySelectionMessage = (CategorySelectionMessage) incomingMessage.getMessage();
                System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name() +
                		"Index:" + categorySelectionMessage.getCategoryIndex());
                Question question = serverState.getActualCategorySelection().get(categorySelectionMessage.getCategoryIndex());
                QuestionMessage questionMessage = new QuestionMessage(
                        Server.getConfiguration().answerTimeout,
                        question);
                serverState.setActualQuestion(question);
                serverState.returnQuestions();
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(questionMessage);
                }
                return PLAYER_SELECTION_PHASE;
            }
            return this;
        }
    },

    PLAYER_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            // TODO Timeout
            ServerState serverState = Server.getServerState();
            if (incomingMessage.getMessage().getMessageType() == MessageType.BUZZ) { 
            	System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()+ 
            			" from id: " + incomingMessage.getSourceClient().getPlayerID() );
                BuzzResultMessage BZMessage = new BuzzResultMessage(
                        incomingMessage.getSourceClient().getPlayerID(),
                        Server.getConfiguration().answerTimeout);
                serverState.setAnswerGiver(incomingMessage.getSourceClient().getPlayerID());
                serverState.setScrewer(-1); // Because Buzz round, there is no screwer.
                for ( Client client: serverState.getPlayerList() ) {
                    client.sendMessage(BZMessage);
                }
                return QUESTION_PLAY_PHASE;
            }

            // Check if screwing player has screws left.
            if ((incomingMessage.getMessage().getMessageType() == MessageType.SCREW) &&
                    (incomingMessage.getSourceClient().getScrewsLeft() > 0)) {
                ScrewMessage screwMessage = (ScrewMessage) incomingMessage.getMessage();
            	System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()+ 
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
                    return QUESTION_PLAY_PHASE;
            }
            return this;
        }
    },

    QUESTION_PLAY_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            ServerState serverState = Server.getServerState();
            // Check if client is allowed to answer
            if ((incomingMessage.getMessage().getMessageType() == MessageType.ANSWER) &&
                    (incomingMessage.getSourceClient().getPlayerID() == serverState.getAnswerGiver())) {
                AnswerMessage answerMessage = (AnswerMessage) incomingMessage.getMessage();
            	System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()+ 
            			" from id: " + incomingMessage.getSourceClient().getPlayerID()+
            			" with answer id: " + answerMessage.getAnswerId());
                serverState.decreaseRoundsLeft();

                boolean answerCorrect = (answerMessage.getAnswerId() == serverState.getActualQuestion().getCorrectAnswerIndex());
                int points = getPoints(serverState.getActualQuestion().getDifficulty(), answerCorrect);
                // change points to answering player
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
            // TODO Timeout
            return this;
        }
        int getPoints(Difficulty difficulty, Boolean answerCorrect) {
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

}
