package StateMachine;

import Message.CSAnnounceMessage;
import Message.CategorySelectionMessage;
import Message.Message;
import MessageType.InGameMessageType;


public class CSA extends AbstractState {

    public CSA (StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        // if (context.getSelectedPlayerID() == context.getPlayerID()) {
          //  IState nextState = Context.SELECTOR;
            //context.setOutputMessage(new CategorySelectionMessage(context.getSelectedIndex()));
        //    return nextState;
        //}
        IState nextState = Context.CATEGORY_SELECTION_ANNOUNCEMENT;
        return nextState;
    }

    @Override
    public IState receiveMessage(Context context) {
        CSAnnounceMessage message =(CSAnnounceMessage) context.getInputMessage();
        switch (message.getType().getValue()) {
            case 0:

            case 1: { 
            	if (message.getSelectedplayerID() == context.getPlayerID()) {
            		if(message.getType()== InGameMessageType.CATEGORY_SELECTION_ANNOUNCEMENT) {
                        CSAnnounceMessage csAnnounceMessage = (CSAnnounceMessage)message;
                        context.setSelectedPlayerID(csAnnounceMessage.getSelectedplayerID());
                        context.setTimeOut(csAnnounceMessage.getTimeOut());
                        context.setDifficulties(csAnnounceMessage.getDifficulty());
                        context.setCategories(csAnnounceMessage.getCategories());
                        
            		return Context.SELECTOR;
            	}

            }}

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
