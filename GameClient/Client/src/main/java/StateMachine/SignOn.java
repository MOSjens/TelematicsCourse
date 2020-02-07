package StateMachine;

import Message.Message;
import Message.SignOnMessage;
import Message.SignOnRespondMessage;
import MessageType.PregamMessageType;

public class SignOn extends AbstractState {

    public SignOn(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
        return Context.SIGN_ON_STATE;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch(message.getGroup().getValue()) {
            case 0: {
                if(message.getType() == PregamMessageType.SIGN_ON_RESPONSE) {
                    context.setPlayerID(((SignOnRespondMessage)message).getPlayerID());
                    return Context.RE_SIGN_ON_STATE;
                }
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
