package MessageType;

import Message.MessageGroup;

public enum PregamMessageType implements MessageType{
	SIGN_ON(0),SIGN_ON_RESPONSE(1),PLAYER_READY(2);
	
	private PregamMessageType(int i) {
		this.value = (byte)i;
	}
	
	private final byte value;
	
	public byte getValue() {
		return value;
	}

	public static PregamMessageType fromByte(byte b) {
		return values()[b];
	}

	public MessageGroup getGroup() {
		return MessageGroup.PRE_GAME;
	}
}
