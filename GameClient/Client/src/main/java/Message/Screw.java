package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.InGameMessageType;

public class Screw extends Message {

    public Screw(int PlayerId) {

        super(InGameMessageType.SCREW);
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(PlayerId);
        setMessageBody(buffer.array());

    }


}
