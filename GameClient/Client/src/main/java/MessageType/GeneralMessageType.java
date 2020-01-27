package MessageType;

import Message.MessageGroup;
import Message.ParseException;

public enum GeneralMessageType implements MessageType {
    GENERAL_TEXT(0), PLAYER_LIST(1);

    private GeneralMessageType(int i) {
        this.value = (byte) i;
    }

    private final byte value;

    public byte getValue() {
        return value;
    }

    public static GeneralMessageType fromByte(byte b) throws ParseException {
        if (b >= 2 || b < 0) {
            throw new ParseException("type");
        }
        return values()[b];
    }

    public MessageGroup getGroup() {
        return MessageGroup.GENERAL;
    }
}
