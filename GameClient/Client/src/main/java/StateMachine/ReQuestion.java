package StateMachine;

import java.io.InputStream;
import java.io.OutputStream;

public class ReQuestion extends AbstractState {

    public ReQuestion (StateEnum stateEnum) {
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
