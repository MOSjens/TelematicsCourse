package StateMachine;

import Message.Message;
import client.ClientData;

import java.util.List;

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

    private IState state;
    private Message inputMessage;
    private Message outputMessage;
    private final ClientData date = new ClientData();

    public Context(IState initState)
    {
        initState(initState);

    }
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




    public void SendMessage() {
        setState(this.state.sendMessage(this));
    }

    public void ReceiveMessage() {
        setState(this.state.receiveMessage(this));
    }



    public String getPlayerAlias() {
        return date.getAlias();
    }

    public void setPlayerAlias(String playerAlias) {
        System.out.println("your player Alias is" + playerAlias);
        date.setAlias(playerAlias);
    }

    public int getPlayerID() { return date.getPlayerID();}

    public void setPlayerID(int playerID) {
        System.out.println("your player ID is" + playerID);
        date.setPlayerID(playerID);
    }

    public int getRoundsLeft() {
        return date.getRoundsLeft();
    }

    public void setRoundsLeft(int roundsLeft) {
        System.out.println("the round left is" + roundsLeft);
        date.setRoundsLeft(roundsLeft);
    }

    public List<Integer> getPlayerIds() {
        return date.getPlayerIds();
    }

    public void setPlayerIds(int[] playerIds) {
        date.setPlayerIds(playerIds);
    }

    public List<Integer> getPlayerScores() {
        return date.getPlayerScores();
    }

    public void setPlayerScores(int[] playerScores) {
        System.out.println("The Score Board are as follow");
        System.out.println("ID---------Score");
        List<Integer> playerID = getPlayerIds();
        for (int i = 0; i < playerScores.length; i++) {
            System.out.println(playerID.get(i) + "---------" + playerScores[i]);
        }
        date.setPlayerScores(playerScores);
    }


    public long getTimeOut() {
        return date.getTimeOut();
    }

    public void setTimeOut(long timeOut) {
        System.out.println("you have " + timeOut + "s in this stage");
        date.setTimeOut(timeOut);
    }

    public List<String> getDifficulties() {
        return date.getDifficulties();
    }

    public void setDifficulties(List<String> difficulty) {
        date.setDifficulties(difficulty);
    }

    public List<String> getCategories() {
        return date.getCategories();
    }

    public void setCategories(List<String> categories) {
        System.out.println("There are following categories and correspond difficulty");
        List<String> difficulty = getDifficulties();
        for(int  i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i) + "with difficulty" + difficulty.get(i));
        }
        date.setCategories(categories);
    }


    public int getSelectedPlayerID() {
        return date.getSelectedPlayerID();
    }

    public void setSelectedPlayerID(int selectedPlayerID) {
        System.out.println("selected player is" + selectedPlayerID);
        date.setSelectedPlayerID(selectedPlayerID);
    }

    public int getSelectedIndex() {
        return date.getSelectedIndex();
    }

    public void setSelectedIndex(int selectedIndex) {
        System.out.println("selected category index is" + selectedIndex);
        date.setSelectedIndex(selectedIndex);
    }

    public String getDifficulty() {
        return date.getDifficulty();
    }

    public void setDifficulty(String difficulty) {
        System.out.println("The difficulty of this question is " + difficulty);
        date.setDifficulty(difficulty);
    }

    public String getCategory() {
        return date.getCategory();
    }

    public void setCategory(String category) {
        System.out.println("The category of this question is " + category);
        date.setCategory(category);
    }

    public String getQuestion() {
        return date.getQuestion();
    }

    public void setQuestion(String question) {
        System.out.println("The context of this question is " + question);
        date.setQuestion(question);
    }

    public String[] getAnswer() {
        return date.getAnswer();
    }

    public void setAnswer(String[] answer) {
        System.out.println("There are following answer can be selected");
        date.setAnswer(answer);
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
