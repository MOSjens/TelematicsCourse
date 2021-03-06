package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.InGameMessageType;

public class CategorySelectionMessage extends Message {

    public CategorySelectionMessage(int categoryIndex) {
        super(InGameMessageType.CATEGORY_SELECTION);
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(categoryIndex);
        setMessageBody(buffer.array());
    }

}
