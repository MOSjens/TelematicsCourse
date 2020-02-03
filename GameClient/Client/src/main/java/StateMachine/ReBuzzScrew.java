package StateMachine;

public class ReBuzzScrew extends AbstractState {

    public ReBuzzScrew(StateEnum stateEnum) {
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
