package StateMachine;

import java.io.InputStream;
import java.io.OutputStream;

public class ReScoreBoard extends AbstractState {

    public ReScoreBoard(StateEnum stateEnum) {
        super(stateEnum);
    }


    @Override
    public IState sendMessage(Context context, OutputStream stream) {
        return null;
    }

    @Override
    public IState receiveMessage(Context context, InputStream stream) {
        return null;
    }
}
