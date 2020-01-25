package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.InGameMessageType;

public class AnswerMessage extends Message {

	public AnswerMessage(int AnswerIndex) {
		super(InGameMessageType.ANSWER);
		ByteBuffer buffer= ByteBuffer.allocate(4);
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.putInt(AnswerIndex);
		setMessageBody(buffer.array());
	}

	

}
