package StateMachine;

import Message.CSAnnounceMessage;
import Message.CategorySelectionMessage;


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
        CSAnnounceMessage message =(CSAnnounceMessage) context.getInputMessage();
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
