package Message;

import MessageType.InGameMessageType;

import java.nio.ByteBuffer;

public class AnswerResultMessage extends Message {

    private int roundLeft;
    private int numberOfPlayers;
    private int[] playerIDs;
    private int[] playerScores;

    public AnswerResultMessage(byte[] messageBody) {
        super(InGameMessageType.ANSWER_RESULT);
        ByteBuffer buffer = ByteBuffer.wrap(messageBody);
        roundLeft = buffer.getInt();
        numberOfPlayers = buffer.getInt();

        playerIDs = new int[numberOfPlayers];
        playerScores = new int[numberOfPlayers];

        for(int i = 0; i < numberOfPlayers; i++) {
            playerIDs[i] = buffer.getInt();
            playerScores[i] = buffer.getInt();
        }
    }

    public int getRoundLeft() {
        return roundLeft;
    }

    public void setRoundLeft(int roundLeft) {
        this.roundLeft = roundLeft;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int[] getPlayerIDs() {
        return playerIDs;
    }

    public void setPlayerIDs(int[] playerIDs) {
        this.playerIDs = playerIDs;
    }

    public int[] getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(int[] playerScores) {
        this.playerScores = playerScores;
    }
}
