package StateMachine;

import java.io.InputStream;
import java.io.OutputStream;
import Message.Message;
import Message.QuestionMessage;
import Message.ScoreBoardMessage;
import MessageType.InGameMessageType;
import Message.AnswerMessage;
import Message.AnswerResultMessage;

public class NoneAnswerer extends AbstractState {
    public NoneAnswerer(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
        IState nextstate = Context.NONE_ANSWERER;
        return nextstate;
    }
    @Override
    public IState receiveMessage(Context context) {
    	Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0: {break;}
            

            case 1:{ 
            	
            	if(message.getType() == InGameMessageType.ANSWER_RESULT) {
            		AnswerResultMessage answerResult= (AnswerResultMessage) message;
            		
            		return Context.NONE_ANSWERER;
            	}
            	
            	if (message.getType()==InGameMessageType.SCOREBOARD) {
            	    ScoreBoardMessage scoreBoardMessage = (ScoreBoardMessage)message;
                    context.setRoundsLeft(scoreBoardMessage.getRoundsLeft());
                    context.setPlayerIds(scoreBoardMessage.getPlayerIds());
                    context.setPlayerScores(scoreBoardMessage.getPlayerScores());
                    return Context.RE_SCORE_BOARD_STATE;
            	}
            	return Context.NONE_ANSWERER;
            }
            	
            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }

            case 3: {
                System.out.println(context.getInputMessage().toString());
            }
        }
        return Context.NONE_ANSWERER;
    }
   
}
