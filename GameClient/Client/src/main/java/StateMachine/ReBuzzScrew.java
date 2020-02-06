package StateMachine;

import Message.Message;
import MessageType.InGameMessageType;
import Message.BRMessage;
import Message.Buzz;



public class ReBuzzScrew extends AbstractState {

    public ReBuzzScrew(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {

       
       context.setOutputMessage(new Buzz());
       IState nextState =Context.RE_BUZZ_SCREW;
       return nextState;
       
    }

    @Override
    public IState receiveMessage(Context context) {
    	  Message message = context.getInputMessage();
          switch (message.getGroup().getValue()) {
              case 0: {
            	  
            	  break;
              }
              case 1: 
            	  if (message.getType()==InGameMessageType.BUZZ_RESULT)
            	  { BRMessage br=(BRMessage)message;
            		  context.setSelectedPlayerID(br.getAnsweringResultID());
            	    context.setTimeOut(br.getTimeOut());
            	while(context.getTimeOut()>0)
            	  { 
            		  if(context.getAnswer()!=null) {
            			  return Context.ANSWERER;
            	  }
            	  }
            		  if(context.getAnswer()!=null) {
            			  return Context.ANSWERER;
            		  }else {
            			  return Context.NONE_ANSWERER;
            		  }
            		  
            	  }

              case 2: {
                  IState nextState = Context.END_GAME;
                  return nextState;
              }
              case 3: {
                  System.out.println(context.getInputMessage());
              }

          }
          return Context.BUZZ_SCREW;
          
      }
    }

