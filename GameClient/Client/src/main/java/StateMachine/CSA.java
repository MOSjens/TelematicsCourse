package StateMachine;

public class CSA extends AbstractState {

    public CSA (StateEnum stateEnum) {
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
