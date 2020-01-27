package MessageType;

import Message.MessageGroup;
import Message.ParseException;

public enum PostGameMessageType implements MessageType {
    GAME_END(0);

    private PostGameMessageType(int i) {
        this.value = (byte) i;
    }

    private final byte value;

    public byte getValue() {
        return value;
    }

    public static PostGameMessageType fromByte(byte b) throws ParseException {
        if (b >= 1 || b < 0) {
            throw new ParseException("type");
        }
        return values()[b];
    }

    public MessageGroup getGroup() {
        return MessageGroup.POST_GAME;
    }
}
