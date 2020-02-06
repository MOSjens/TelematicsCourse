package StateMachine;
import Message.CategorySelectionMessage;
import Message.Message;
import Message.QuestionMessage;
import MessageType.InGameMessageType;

import java.io.InputStream;
import java.io.OutputStream;

public class ReQuestion extends AbstractState {

    public ReQuestion (StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
    	
    	 
         IState nextState = Context.RE_QUESTION;
         
         return nextState;
         
        }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0:{break;}

            case 1: { if(message.getType()== InGameMessageType.CATEGORY_SELECTION)
            {
            	QuestionMessage qm= (QuestionMessage)message;
            	 context.setCategory(qm.getCategory());
            	 context.setDifficulty(qm.getDifficulty());
            	 context.setTimeOut(qm.getTimeOut());
            	 context.setAnswer(qm.getAnswer());
            	 context.setQuestion(qm.getQuestion());
            	 return Context.BUZZ_SCREW;
            	
            }
            	
            }

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }
            case 3: {
                System.out.println(context.getInputMessage());
            }

        }
        return Context.RE_QUESTION;
        
    }
}
