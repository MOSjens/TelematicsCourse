package MessageType;

import Message.MessageGroup;

public enum PostGameMessageType implements MessageType {
	GAME_END(0);

	private PostGameMessageType(int i) {
		this.value = (byte)i;
	}
	
	private final byte value;
	
	public byte getValue() {
		return value;
	}

	public static PostGameMessageType fromByte(byte b) {
		return values()[b];
	}

	public MessageGroup getGroup() {
		return MessageGroup.POST_GAME;
	}
}
