package StateMachine;


import Message.Message;
import Message.PlayerReadyMessage;
import Message.SignOnMessage;
import Message.SignOnRespondMessage;


public class ReSignOn extends AbstractState   {

    public ReSignOn(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
       IState nextState = Context.READY_STATE;
       context.setOutputMessage(new PlayerReadyMessage());
       return nextState;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0: { }

            case 1: { break;}

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }
            case 3: {
                System.out.println(context.getInputMessage().toString());
            }

        }
        return Context.RE_SIGN_ON_STATE;

    }
   
}
