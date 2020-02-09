package StateMachine;
import Message.BRMessage;
import Message.Message;
import Message.SRMessage;
import MessageType.InGameMessageType;

public class BuzzScrew extends AbstractState {

    public BuzzScrew (StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        return null;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0:{break;}

            case 1: {
                if(message.getType() == InGameMessageType.SCREW_RESULT) {
                    SRMessage srMessage = (SRMessage) message;
                    context.setTimeOut(srMessage.getTimeOut());
                    context.setScrewerPlayerID(srMessage.getScrewerPlayerID());
                    context.setAnsweringPlayerID(srMessage.getAnsweringPlayerID());
                    return Context.RE_BUZZ_SCREW;
                }

                if(message.getType() == InGameMessageType.BUZZ_RESULT) {
                    BRMessage brMessage = (BRMessage)message;
                    context.setTimeOut(brMessage.getTimeOut());
                    context.setAnsweringPlayerID(brMessage.getAnsweringResultID());
                    return Context.RE_BUZZ_SCREW;
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
        return Context.BUZZ_SCREW;
    }
    
}
