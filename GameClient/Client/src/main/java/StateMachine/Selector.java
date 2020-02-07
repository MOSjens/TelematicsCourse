package StateMachine;

import Message.CategorySelectionMessage;
import Message.Message;
import Message.QuestionMessage;
import MessageType.InGameMessageType;

public class Selector extends AbstractState{

    public Selector (StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
    	 return Context.SELECTOR;
    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch(message.getGroup().getValue()) {
            case 0:{ break;}

            case 1:{
                if(message.getType() == InGameMessageType.QUESTION) {
                    QuestionMessage questionMessage = (QuestionMessage)message;
                    context.setQuestion(questionMessage.getQuestion());
                    context.setAnswer(questionMessage.getAnswer());
                    return Context.RE_QUESTION;
                }
            }
            

            case 2: {
                IState nextState = Context.END_GAME;
                return nextState;
            }

            case 3: {
                System.out.println(context.getInputMessage().getMessageBody());
            }
        }

        return Context.SELECTOR;
    }
}
