package StateMachine;

import Message.CategorySelectionMessage;
import Message.Message;


public class CSA extends AbstractState {

    public CSA (StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        if (context.getSelectedPlayerID() == context.getPlayerID()) {
            IState nextState = Context.SELECTOR;
            context.setOutputMessage(new CategorySelectionMessage(context.getSelectedIndex()));
            return nextState;
        }
        IState nextState = Context.NONE_SELECTOR;
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
                System.out.println(context.getInputMessage());
            }

        }
        return Context.CATEGORY_SELECTION_ANNOUNCEMENT;
    }
}
