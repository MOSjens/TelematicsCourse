package StateMachine;

public class Ready extends AbstractState {

    public Ready (StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState sendMessage(Context context) {
        return null;
    }

    @Override
    public IState receiveMessage(Context context) {
        return null;
    }
}
