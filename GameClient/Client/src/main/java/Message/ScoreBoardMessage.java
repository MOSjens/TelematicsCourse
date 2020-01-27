package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import MessageType.InGameMessageType;

public class ScoreBoardMessage extends Message {
    private int roundsLeft;
    private int[] playerIds;
    private int[] playerScores;

    public ScoreBoardMessage(byte[] messageBody) {
        super(InGameMessageType.SCOREBOARD, messageBody);
        ByteBuffer buffer = ByteBuffer.wrap(messageBody);
        buffer.order(ByteOrder.BIG_ENDIAN);
        this.roundsLeft = buffer.getInt();
        int numPlayers = buffer.getInt();
        this.playerIds = new int[numPlayers];
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            this.playerIds[i] = buffer.getInt();
            this.playerScores[i] = buffer.getInt();

        }


    }

    public int getRoundsLeft() {
        return this.roundsLeft;
    }

    public int getNumPlayers() {
        return this.playerIds.length;
    }

    public int getPlayerId(int i) {
        return this.playerIds[i];
    }

    public int getPlayerScore(int i) {
        return this.playerScores[i];
    }

}
