package StateMachine;

import Message.CategorySelectionMessage;
import Message.Message;
import Message.QuestionMessage;
import MessageType.InGameMessageType;

public class Selector extends AbstractState{

    public Selector (StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
    	 CategorySelectionMessage cs= (CategorySelectionMessage)context.getInputMessage();
    	 while(context.getTimeOut()>0) {
    		 if(cs!=null) {
    	 context.setOutputMessage(cs);
    	 
    	 }
    	 }
    	 return Context.RE_QUESTION;
    	 
    	 
    	 
       
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getOutputMessage();
        switch(message.getGroup().getValue()) {
            case 0:{ break;}

            case 1:{ break;
                
                }
            

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }

            case 3: {
                System.out.println(context.getInputMessage().getMessageBody());
            }
        }

        return Context.SELECTOR;
    }
}
