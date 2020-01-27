package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.InGameMessageType;

public class CSAmessage extends Message {

    public CSAmessage(int categoryIndex) {
        super(InGameMessageType.CATAGORY_SELECTION);
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(categoryIndex);
        setMessageBody(buffer.array());
    }

}
