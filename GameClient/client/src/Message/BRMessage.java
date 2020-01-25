package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.InGameMessageType;

public class BRMessage extends Message {

	private int answeringResultID;
	private long timeOut;
	
	public BRMessage(byte[] messsgeBody) {
		super(InGameMessageType.BUZZ_RESULT, messsgeBody);
		ByteBuffer buffer = ByteBuffer.wrap(messsgeBody);
		buffer.order(ByteOrder.BIG_ENDIAN);
		setAnsweringResultID(buffer.getInt());
		setTimeOut(buffer.getLong());
	}

	public int getAnsweringResultID() {
		return answeringResultID;
	}

	public void setAnsweringResultID(int answeringResultID) {
		this.answeringResultID = answeringResultID;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	
	@Override
	public String toString() {
		return Integer.toString(getVersion()) + getGroup().name() + getType() + Integer.toString(getPayloadLength())
		   + Integer.toString(answeringResultID) +  Long.toString(timeOut);
	}

	

}
