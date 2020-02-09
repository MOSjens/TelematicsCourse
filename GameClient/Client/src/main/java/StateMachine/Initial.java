package StateMachine;

import Message.Message;
import Message.SignOnMessage;

public class Initial extends AbstractState  {

    public Initial(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        IState nextState = Context.SIGN_ON_STATE;
        context.setOutputMessage(new SignOnMessage(context.getPlayerAlias()));
        return nextState;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getType().getValue()) {
            case 0:

            case 1: { break;}

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }
            case 3: {
                System.out.println(context.getInputMessage().toString());
            }

        }
        return Context.INITIAL_STATE;
    }
   
}