package server;

import client.Client;
import messages.MessageType;
import messages.PlayerReadyMessage;
import messages.ReadyState;
import messages.SignOnMessage;

import java.util.Timer;
import java.util.TimerTask;

public enum GamePhase {

    // Phase until all clients are connected.
    STARTUP_PHASE() {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {

            if (incomingMessage.getMessage().getMessageType() == MessageType.SIGN_ON) {
                SignOnMessage signOn = (SignOnMessage) incomingMessage.getMessage();
                System.out.println("Recieved: " + incomingMessage.getMessage().getMessageType().name()
                        + " Alias = " + signOn.getPlayerAlias());
                Client sourceClient = incomingMessage.getSourceClient();
                sourceClient.setAlias(signOn.getPlayerAlias());
                System.out.println(sourceClient.getAlias());
            }
            if (incomingMessage.getMessage().getMessageType() == MessageType.PLAYER_READY) {
                PlayerReadyMessage playerReady = (PlayerReadyMessage) incomingMessage.getMessage();
                Client sourceClient = incomingMessage.getSourceClient();
                sourceClient.setReadyState(ReadyState.READY);
                // TODO send PlayerList Message
                if (Server.getServerState().everyPlayerReady()) {
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            if (Server.getSecondsTillStart() > 0)
                                Server.decreaseSecondsTillStart();

                            if (Server.getSecondsTillStart() == 0) {
                                Server.setStillConnectClients( false );
                                // TODO Send null Message to GamePhase
                                timer.cancel();
                            }
                        }
                    };
                    timer.schedule(task, 0, 1000);
                    return WAIT_FOR_GAME_PHASE;
                }
            }



            return this;
        }
    },

    WAIT_FOR_GAME_PHASE {
        @Override
        public GamePhase nextPhase(IncomingMessage incomingMessage) {
            return null;
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
