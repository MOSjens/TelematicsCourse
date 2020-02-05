package server;

import client.Client;
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
                Question question = serverState.getActualCategorySelection().get(categorySelectionMessage.getCategoryIndex());
                QuestionMessage questionMessage = new QuestionMessage(
                        Server.getConfiguration().answerTimeout,
                        question);

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
            // TODO Await Buzz or screw
            // TODO Timeout
            return QUESTION_PLAY_PHASE;
        }
    },

    QUESTION_PLAY_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            if (Server.getServerState().getRoundsLeft() > 0){
                return GAME_PHASE.nextPhase(null);
            }
            return CLOSING_PHASE;
        }
    },

    CLOSING_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            //TODO start new round or Stay in the closing phase?
            return CLOSING_PHASE;
            // return GAME_PHASE;
        }
    };


    GamePhase () {

    }

    public abstract GamePhase nextPhase(IncomingMessage incomingMessage);

}
