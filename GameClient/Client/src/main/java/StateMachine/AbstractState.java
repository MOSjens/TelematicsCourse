package StateMachine;

import Message.Message;

public abstract class AbstractState implements IState{
    private StateEnum stateEnum;




    public AbstractState(StateEnum stateEnum)
    {
        this.stateEnum = stateEnum;
    }

    public StateEnum getStateEnum() {
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    public String toString()
    {
        return(stateEnum.toString());
    }
}
