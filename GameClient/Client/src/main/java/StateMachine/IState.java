package StateMachine;

import java.io.InputStream;
import java.io.OutputStream;

public interface IState {
      public IState sendMessage(Context context);

      public IState receiveMessage(Context context);

	
    
}
