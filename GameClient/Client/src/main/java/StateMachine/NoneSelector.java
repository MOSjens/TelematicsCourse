package StateMachine;


import Message.Message;
import Message.QuestionMessage;
import MessageType.InGameMessageType;

public class NoneSelector extends AbstractState {

    public NoneSelector(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context) {
        IState nextState = Context.NONE_SELECTOR;
        return nextState;

    }

    @Override
    public IState receiveMessage(Context context) {
        Message message = context.getInputMessage();
        switch (message.getGroup().getValue()) {
            case 0: {
                break;
            }

            case 1: {
                if(message.getType() == InGameMessageType.QUESTION) {
                    QuestionMessage questionMessage = (QuestionMessage)message;
                    context.setDifficulty(questionMessage.getDifficulty());
                    context.setCategory(questionMessage.getCategory());
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
                System.out.println(context.getInputMessage());
            }

        }
        return Context.NONE_SELECTOR;
    }
}
