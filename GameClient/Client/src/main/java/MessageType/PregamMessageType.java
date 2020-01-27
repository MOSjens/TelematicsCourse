package MessageType;

import Message.MessageGroup;
import Message.ParseException;

public enum PregamMessageType implements MessageType {
    SIGN_ON(0), SIGN_ON_RESPONSE(1), PLAYER_READY(2);

    private PregamMessageType(int i) {
        this.value = (byte) i;
    }

    private final byte value;

    public byte getValue() {
        return value;
    }

    public static PregamMessageType fromByte(byte b) throws ParseException {
        if (b >= 3 || b < 0) {
            throw new ParseException("type");
        }
        return values()[b];
    }

    public MessageGroup getGroup() {
        return MessageGroup.PRE_GAME;
    }
}
