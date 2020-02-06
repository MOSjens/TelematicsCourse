package StateMachine;
import Message.Buzz;
import Message.Message;
import Message.QuestionMessage;
import Message.Screw;
import MessageType.InGameMessageType;

public class BuzzScrew extends AbstractState {

    public BuzzScrew (StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
         
    	 if( InGameMessageType.BUZZ != null)
    	 {
    	IState  nextState = Context.RE_BUZZ_SCREW;
         context.setOutputMessage(new Buzz());
         return nextState;
         }
    	 
    	 else {
    		 Screw s= (Screw)context.getInputMessage();
    		 context.setOutputMessage(s);
    	   IState nextState = Context.RE_BUZZ_SCREW;
    	   
    	   return nextState;
    	 }
         
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0:{break;}

            case 1: { 
            	switch (message.getType().getValue())
            	{
            	case 2:
            	{  
            		QuestionMessage QN= (QuestionMessage) message;
            		
            		context.setTimeOut(QN.getTimeOut());
            		context.setDifficulty(QN.getDifficulty());
            		context.setCategory(QN.getCategory());
            		context.setQuestion(QN.getQuestion());
            		 return Context.RE_QUESTION;
            		
            	}
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
        return Context.BUZZ_SCREW;
    }
}
