package StateMachine;

import Message.Message;

import java.io.InputStream;
import java.io.OutputStream;

public class Context {
    public static final IState INITIAL_STATE = new Initial(StateEnum.INITIAL);
    public static final IState SIGN_ON_STATE = new SignOn(StateEnum.SIGN_ON);
    public static final IState RE_SIGN_ON_STATE = new ReSignOn(StateEnum.RE_SIGN_ON);
    public static final IState READY_STATE = new Ready(StateEnum.READY);
    public static final IState RE_SCORE_BOARD_STATE = new ReScoreBoard(StateEnum.RE_SCORE_BOARD);
    public static final IState CATEGORY_SELECTION_ANNOUNCEMENT = new CSA(StateEnum.CATEGORY_SELECTION_ANNOUNCEMENT);
    public static final IState SELECTOR = new Selector(StateEnum.SELECTOR);
    public static final IState RE_QUESTION = new ReQuestion(StateEnum.RE_QUESTION);
    public static final IState BUZZ_SCREW = new BuzzScrew(StateEnum.BUZZ_SCREW);
    public static final IState RE_BUZZ_SCREW = new ReBuzzScrew(StateEnum.RE_BUZZ_SCREW);
    public static final IState ANSWERER = new Answerer(StateEnum.ANSWERER);
    public static final IState NONE_SELECTOR = new NoneSelector(StateEnum.NONE_SELECTOR);
    public static final IState NONE_ANSWERER = new NoneAnswerer(StateEnum.NONE_ANSWERER);
    public static final IState END_GAME = new EndGame(StateEnum.END_GAME);

    private String playerAlias;
    private IState state;
    private Message inputMessage;
    private Message outputMessage;

    public Message getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(Message inputMessage) {
        this.inputMessage = inputMessage;
    }

    public Message getOutputMessage() {
        return outputMessage;
    }

    public void setOutputMessage(Message outputMessage) {
        this.outputMessage = outputMessage;
    }

    public Context(IState initState)
    {
        initState(initState);

    }


    public void SendMessage() {
        setState(this.state.sendMessage(this));
    }

    public void ReceiveMessage() {
        setState(this.state.receiveMessage(this));
    }

    public String getPlayerAlias() {
        return playerAlias;
    }

    public void setPlayerAlias(String playerAlias) {
        this.playerAlias = playerAlias;
    }


    public void initState(IState state)
    {
        this.setState(state);
    }

    public void setState(IState state)
    {
        this.state = state;
    }

    public IState getState()
    {
        return this.state;
    }
}
