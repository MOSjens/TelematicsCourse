package StateMachine;

public class NoneSelector extends AbstractState {

    public NoneSelector(StateEnum stateEnum) {
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
