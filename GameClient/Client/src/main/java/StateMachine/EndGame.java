package StateMachine;

public class EndGame extends AbstractState {

    public EndGame(StateEnum stateEnum) {
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
