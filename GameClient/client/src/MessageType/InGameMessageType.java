package MessageType;

import Message.MessageGroup;
import Message.ParseException;

public enum InGameMessageType implements MessageType {
	CATAGORY_SELECTION_ANNOUNCEMENT(0),CATAGORY_SELECTION(1),QUESTION(2),ANSWER(3),BUZZ(4),BUZZ_RESULT(5),
	SCOREBOARD(6),ANSWER_RESULT(7),SCREW(8),SCREW_RESULT(9);
	
	private InGameMessageType(int i) {
		this.value = (byte)i;
	}
	
	private final byte value;
	
	public byte getValue() {
		return value;
	}

	public static InGameMessageType fromByte(byte b) throws ParseException {
		if (b >= 10 || b < 0) {
			throw new ParseException("type");
		}
		return values()[b];
	}

	@Override
	public MessageGroup getGroup() {
		return MessageGroup.IN_GAME;
	}		
}
