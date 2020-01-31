package server;

import client.Client;
import messages.SignOnMessage;

public enum GamePhase {

    // Phase until all clients are connected.
    STARTUP_PHASE() {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            // Define Transition here ..
            SignOnMessage signOn = (SignOnMessage) incomingMessage.getMessage();
            System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()
                    + " Alias = " + signOn.getPlayerAlias());
            Client source = incomingMessage.getSourceClient();
            source.setAlias(signOn.getPlayerAlias());
            System.out.println(source.getAlias());
            return this;
            //TODO Start Timer if everyone is ready and leave phase after 30 seconds.
        }
    },

    GAME_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            return CATEGORY_SELECTION_PHASE;
        }
    },

    CATEGORY_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            return PLAYER_SELECTION_PHASE;
        }
    },

    PLAYER_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            return QUESTION_PLAY_PHASE;
        }
    },

    QUESTION_PLAY_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            if (Server.getServerState().getRoundsLeft() > 0){
                return CATEGORY_SELECTION_PHASE;
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
