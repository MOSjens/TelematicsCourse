package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.MessageType;

public class Message {
    private MessageType type;
    private byte[] messageBody;

    public Message(MessageType type, byte[] messageBody) {
        this.type = type;
        this.messageBody = messageBody;
    }

    public Message(MessageType type) {
        this.type = type;
        this.messageBody = new byte[0];
    }

    public byte getVersion() {
        return 1;
    }

    public MessageGroup getGroup() {
        return type.getGroup();
    }

    public MessageType getType() {
        return type;
    }

    public int getPayloadLength() {
        return messageBody.length;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    public byte[] getEncodedMessage() {
        ByteBuffer buffer = ByteBuffer.allocate(7 + messageBody.length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(getVersion());
        buffer.put(getGroup().getValue());
        buffer.put(getType().getValue());
        buffer.putInt(getPayloadLength());
        buffer.put(getMessageBody());

        return buffer.array();

    }
}
