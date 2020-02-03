package StateMachine;

public interface IState {
      public IState sendMessage(Context context);

      public IState receiveMessage(Context context);
}
