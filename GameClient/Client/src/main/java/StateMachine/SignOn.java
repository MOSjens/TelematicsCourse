package StateMachine;

import Message.Message;
import Message.SignOnMessage;
import Message.SignOnRespondMessage;

public class SignOn extends AbstractState {

    public SignOn(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
    	SignOnMessage so= (SignOnMessage)context.getInputMessage();
    	
    	context.setOutputMessage(so);
    	
        return Context.RE_SIGN_ON_STATE;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getOutputMessage();
        switch(message.getGroup().getValue()) {
            case 0: {
               break;
               
                }
            

            case 1:{ break;}

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }

            case 3: {
                System.out.println(context.getInputMessage().getMessageBody());
            }
        }

        return Context.SIGN_ON_STATE;
    }
}
