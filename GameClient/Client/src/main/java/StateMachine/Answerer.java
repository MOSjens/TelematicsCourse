package StateMachine;
import Message.BRMessage;
import Message.Buzz;
import Message.Message;
import Message.SRMessage;
import Message.ScoreBoardMessage;
import MessageType.InGameMessageType;
import Message.AnswerMessage;
import java.io.InputStream;
import java.io.OutputStream;

public class Answerer extends AbstractState {
    public Answerer(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
    
    	
    	if (context.getSelectedPlayerID()==context.getPlayerID())
    		context.setOutputMessage(new AnswerMessage(context.getSelectedIndex()));  	
    	    IState nextState =Context.RE_SCORE_BOARD_STATE;
        return nextState;
    }

    @Override
    public IState receiveMessage(Context context) {
    	Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0: {break;}

            case 1:
            	/*if (message.getType()==InGameMessageType.SCOREBOARD)
            	{
           
            	
            		 ScoreBoardMessage scoreBoardMessage = (ScoreBoardMessage)message;
                     context.setRoundsLeft(scoreBoardMessage.getRoundsLeft());
                     context.setPlayerIds(scoreBoardMessage.getPlayerIds());
                     context.setPlayerScores(scoreBoardMessage.getPlayerScores());
                     return Context.RE_SCORE_BOARD_STATE;
            	}*/
            	
     	
            	
            	
            case 2: {IState nextState = Context.END_GAME;
            return nextState;}
            
            case 3: 
            	switch (message.getType().getValue())
            	{
            	case 0 :
            		System.out.println(context.getOutputMessage());
        }
            	
        }
        return Context.ANSWERER;

}
}
