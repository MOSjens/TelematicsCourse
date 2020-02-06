package StateMachine;


import Message.Message;

public class NoneSelector extends AbstractState {

    public NoneSelector(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        if (context.getCategories().isEmpty())
        	
        { 
        	IState nextState= Context.RE_QUESTION;
            
        	return nextState;
        }
       
        IState nextState = Context.SELECTOR;
        return nextState; 

    }

    @Override
    public IState receiveMessage(Context context) {
    	Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0:{break;}

            case 1: { 
            	switch(message.getType().getValue())
            	
            	{
            	case 0:
            	{ break;
                }
            	case 1:
            		
            	{ break;
                }

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }
            case 3: {
                System.out.println(context.getInputMessage());
            }

        }
      
    }
}  return Context.NONE_SELECTOR;
     
    }}