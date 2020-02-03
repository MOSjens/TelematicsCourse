package StateMachine;

public class Initial extends AbstractState {

    public Initial(StateEnum stateEnum) {
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
