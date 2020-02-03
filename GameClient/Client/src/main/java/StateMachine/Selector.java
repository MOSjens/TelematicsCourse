package StateMachine;

public class Selector extends AbstractState{

    public Selector (StateEnum stateEnum) {
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
