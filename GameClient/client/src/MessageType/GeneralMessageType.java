package MessageType;

import Message.MessageGroup;

public enum GeneralMessageType implements MessageType {
	GENERAL_TEXT(0),PLAYER_LIST(1);
	
	private GeneralMessageType(int i) {
		this.value = (byte)i;
	}
	
	private final byte value;
	
	public byte getValue() {
		return value;
	}

	public static GeneralMessageType fromByte(byte b) {
		return values()[b];
	}

	public MessageGroup getGroup() {
		return MessageGroup.GENERAL;
	}
}
