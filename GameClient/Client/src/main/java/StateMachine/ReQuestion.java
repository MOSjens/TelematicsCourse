package StateMachine;

import Message.Message;
import Message.Screw;
import Message.Buzz;

public class ReQuestion extends AbstractState {

    public ReQuestion (StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
        IState nextState = Context.BUZZ_SCREW;
        if(context.isDecision()) {
            context.setOutputMessage(new Screw(context.getScrewID()));
        } else {
            context.setOutputMessage(new Buzz());
        }
        return nextState;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0:{}

            case 1: {break;}

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }
            case 3: {
                System.out.println(context.getInputMessage().toString());
            }

        }
        return Context.RE_QUESTION;
        
    }
}
