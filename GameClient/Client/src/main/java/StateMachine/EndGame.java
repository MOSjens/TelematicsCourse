package StateMachine;

import Message.Message;

public class EndGame extends AbstractState {

    public EndGame(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
       return Context.END_GAME;
    }

    @Override
    public IState receiveMessage(Context context) {
    	
    	Message message = context.getInputMessage();
    	switch (message.getGroup().getValue())
    	{
    		case 0: {}
    		case 1: {
    			IState nexIState= Context.END_GAME;
    			return nexIState;
    		}

    		case 2:{
            	IState nextState = Context.END_GAME;
            	return nextState;
    		}

    		case 3:{
            	System.out.println(context.getInputMessage().toString());
    		}

    	}
        return Context.END_GAME;
    }
    
}
