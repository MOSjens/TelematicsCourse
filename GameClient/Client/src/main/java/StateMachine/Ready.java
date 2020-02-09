package StateMachine;

import Message.Message;
import Message.PlayerReadyMessage;
import Message.ScoreBoardMessage;
import MessageType.InGameMessageType;

public class Ready extends AbstractState {

    public Ready (StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
        return Context.READY_STATE;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch(message.getGroup().getValue()) {
            case 0:{ break;}

            case 1:{
                if(message.getType() == InGameMessageType.SCOREBOARD) {
                    ScoreBoardMessage scoreBoardMessage = (ScoreBoardMessage)message;
                    context.setRoundsLeft(scoreBoardMessage.getRoundsLeft());
                    context.setPlayerIds(scoreBoardMessage.getPlayerIds());
                    context.setPlayerScores(scoreBoardMessage.getPlayerScores());
                    return Context.RE_SCORE_BOARD_STATE;
                }
            }

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }

            case 3: {
                System.out.println(context.getInputMessage().toString());
            }
        }

        return Context.READY_STATE;
    }
    
}
