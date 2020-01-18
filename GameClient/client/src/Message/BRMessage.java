package Message;

public class BRMessage extends Message {

	private int answeringResultID;
	private int timeOut;
	
	public BRMessage(int version, int group, int type, int payLoadLength, String messsgeBody) {
		super(version, group, type, payLoadLength, messsgeBody);
		setAnsweringResultID(Integer.parseInt(messsgeBody.substring(0, 4)));
		setTimeOut(Integer.parseInt(messsgeBody.substring(4)));
	}

	public int getAnsweringResultID() {
		return answeringResultID;
	}

	public void setAnsweringResultID(int answeringResultID) {
		this.answeringResultID = answeringResultID;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	@Override
	public String toString() {
		return Integer.toString(getVersion()) + getGroup().name() + getType() + Integer.toString(getPayloadLength())
		   + Integer.toString(answeringResultID) +  Integer.toString(timeOut);
	}

	

}
