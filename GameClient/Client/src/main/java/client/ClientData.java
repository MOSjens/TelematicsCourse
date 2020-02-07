package client;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is for store the in game data and update it
 */
public class ClientData {
    private int playerID; //ID that client assigned for player
    private String alias;//alias that player tapped
    private int roundsLeft;//the rounds is game can play
    private List<Integer> playerIds;// the list of player id in the scoreboard
    private List<Integer> playerScores;// the list of score in the scoreboard
    private long timeOut;// time out of the message
    private List<String> difficulties;//list of difficulties in the category selection message
    private List<String> categories;//list of categories in the category selection message
    private int selectedIndex;// the index of selected question
    private int selectedPlayerID;// ID of the player who is chosen to select the category
    private String difficulty;// difficulty of the question
    private String category;// category of the question
    private String question;//question body
    private String[] answer;// answer that could be selected
    private boolean decision; //0 for buzz, 1 for screw
    private int screwID;//id of the player who get screwed
    private int answeringPlayerID;// id of player who answer the question
    private int screwerPlayerID;//id of the player who do the screw action
    private int selectedAnswerIndex;

    public int getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public void setSelectedAnswerIndex(int selectedAnswerIndex) {
        this.selectedAnswerIndex = selectedAnswerIndex;
    }

    public int getAnsweringPlayerID() {
        return answeringPlayerID;
    }

    public void setAnsweringPlayerID(int answeringPlayerID) {
        this.answeringPlayerID = answeringPlayerID;
    }

    public int getScrewerPlayerID() {
        return screwerPlayerID;
    }

    public void setScrewerPlayerID(int screwerPlayerID) {
        this.screwerPlayerID = screwerPlayerID;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public int getScrewID() {
        return screwID;
    }

    public void setScrewID(int screwID) {
        this.screwID = screwID;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswer() {
        return answer;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    public int getSelectedPlayerID() {
        return selectedPlayerID;
    }

    public void setSelectedPlayerID(int selectedPlayerID) {
        this.selectedPlayerID = selectedPlayerID;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public List<String> getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(List<String> difficulties) {
        this.difficulties = difficulties;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public ClientData() {
        playerIds = new ArrayList<>();
        playerScores = new ArrayList<>();
    }

    public int getRoundsLeft() {
        return roundsLeft;
    }

    public void setRoundsLeft(int roundsLeft) {
        this.roundsLeft = roundsLeft;

    }

    public List<Integer> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        for (int i: playerIds) {
            this.playerIds.add(i);
        }
    }

    public List<Integer> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(int[] playerScores) {
        for (int i: playerScores) {
            this.playerScores.add(i);
        }
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}