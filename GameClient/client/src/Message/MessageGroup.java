package Message;

import MessageType.GeneralMessageType;
import MessageType.InGameMessageType;
import MessageType.MessageType;
import MessageType.PostGameMessageType;
import MessageType.PregamMessageType;

public enum MessageGroup {
	PRE_GAME(0),IN_GAME(1),POST_GAME(2),GENERAL(3);
	
	private MessageGroup(int i) {
		this.value = (byte)i;
	}
	
	private final byte value;
	
	public byte getValue() {
		return value;
	}
	
	public static MessageGroup fromByte(byte value) {
		return values()[value];
	}
	
	public MessageType getType(byte value) {
		switch(this) {
		case PRE_GAME:
			return PregamMessageType.fromByte(value);
		case IN_GAME:
			return InGameMessageType.fromByte(value);
		case POST_GAME:
			return PostGameMessageType.fromByte(value);
		case GENERAL:
			return GeneralMessageType.fromByte(value);
		}
		return null;
	}
}
