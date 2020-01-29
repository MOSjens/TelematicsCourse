package server;

public enum GamePhase {

    STARTUP_PHASE() {
        @Override
        public GamePhase nextPhase( ServerState serverState ) {
            // Define Transition here ..
            return GAME_PHASE;
        }
    },

    GAME_PHASE {
        @Override
        public GamePhase nextPhase( ServerState serverState ) {
            return CATEGORY_SELECTION_PHASE;
        }
    },

    CATEGORY_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase( ServerState serverState ) {
            return PLAYER_SELECTION_PHASE;
        }
    },

    PLAYER_SELECTION_PHASE {
        @Override
        public GamePhase nextPhase( ServerState serverState ) {
            return QUESTION_PLAY_PHASE;
        }
    },

    QUESTION_PLAY_PHASE {
        @Override
        public GamePhase nextPhase( ServerState serverState ) {
            if (serverState.getRoundsLeft() > 0){
                return CATEGORY_SELECTION_PHASE;
            }
            return CLOSING_PHASE;
        }
    },

    CLOSING_PHASE {
        @Override
        public GamePhase nextPhase( ServerState serverState ) {
            //TODO start new round or Stay in the closing phase?
            return CLOSING_PHASE;
            // return GAME_PHASE;
        }
    };

    //private ServerState serverState;

    GamePhase () {

    }

    public abstract GamePhase nextPhase( ServerState serverState );

}
