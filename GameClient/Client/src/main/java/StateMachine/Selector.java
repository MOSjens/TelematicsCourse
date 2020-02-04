package StateMachine;

import Message.Message;
import Message.QuestionMessage;

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
        Message message = context.getOutputMessage();
        switch(message.getGroup().getValue()) {
            case 0:{ break;}

            case 1:{
                if(message.getType().getValue() == 2) {
                    QuestionMessage questionMessage = (QuestionMessage)message;
                    context.setTimeOut(questionMessage.getTimeOut());
                    context.setCategory(questionMessage.getCategory());
                    context.setDifficulty(questionMessage.getDifficulty());
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
