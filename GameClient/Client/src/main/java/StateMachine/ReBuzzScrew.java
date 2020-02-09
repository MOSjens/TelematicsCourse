package StateMachine;

import Message.Message;
import MessageType.InGameMessageType;
import Message.AnswerMessage;
import Message.Buzz;



public class ReBuzzScrew extends AbstractState  {

    public ReBuzzScrew(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        IState nextState = Context.ANSWERER;
        if(context.getAnsweringPlayerID() == context.getPlayerID()) {
            context.setOutputMessage(new AnswerMessage(context.getSelectedAnswerIndex()));
            return nextState;
        }
        nextState = Context.NONE_ANSWERER;
        return nextState;
    }

    @Override
    public IState receiveMessage(Context context) {
    	  Message message = context.getInputMessage();
          switch (message.getGroup().getValue()) {
              case 0: {}
              case 1: { break; }
              case 2: {
                  IState nextState = Context.END_GAME;
                  return nextState;
              }
              case 3: {
                  System.out.println(context.getInputMessage().toString());
              }

          }
          return Context.RE_BUZZ_SCREW;
          
      }
    
  
    }

