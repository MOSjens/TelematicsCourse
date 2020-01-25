package Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import MessageType.InGameMessageType;

public class SRMessage extends Message {

	private int screwerPlayerID;
	private int answeringPlayerID;
	private long timeOut;
	
	public SRMessage(byte[] messagebody) {
		super(InGameMessageType.SCREW_RESULT, messagebody);
		ByteBuffer buffer = ByteBuffer.wrap(messagebody);
		buffer.order(ByteOrder.BIG_ENDIAN);		
		setScrewerPlayerID(buffer.getInt());
		setAnsweringPlayerID(buffer.getInt());
		setTimeOut(buffer.getLong());
		
	}

	public int getScrewerPlayerID() {
		return screwerPlayerID;
	}

	public void setScrewerPlayerID(int screwerPlayerID) {
		this.screwerPlayerID = screwerPlayerID;
	}

	public int getAnsweringPlayerID() {
		return answeringPlayerID;
	}

	public void setAnsweringPlayerID(int answeringPlayerID) {
		this.answeringPlayerID = answeringPlayerID;
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
		   + Integer.toString(screwerPlayerID)  + Integer.toString(answeringPlayerID) 
		   +  Long.toString(timeOut);
	}
	
}
