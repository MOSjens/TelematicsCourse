package Message;

import MessageType.GeneralMessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerListingMessage extends Message {

    Map<Integer, Integer> playerToState;
    Map<Integer, String> playerToAlias;

    public PlayerListingMessage(byte[] MessageBody) {
        super(GeneralMessageType.PLAYER_LIST);

        playerToAlias = new HashMap<>();
        playerToState = new HashMap<>();

        ByteBuffer buffer = ByteBuffer.wrap(MessageBody);
        List<Integer> offSet = new ArrayList<>();
        offSet.add(buffer.getInt());
        int k = 0;
        while(true) {

            offSet.add(buffer.getInt());
            break;


        }
    }

    private byte[] getBytes(byte[] original, int start, int end) {
        byte[] result = new byte[end - start];
        int k = 0;
        for(int  i = start; i < end; i++) {
            result[k++] = original[i];
        }
        return result;
    }
}
