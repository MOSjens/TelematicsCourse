package Message;

public class SRMessage extends Message {

	private int screwerPlayerID;
	private int answeringPlayerID;
	private int timeOut;
	
	public SRMessage(int version, int group, int type, int payLoadLength, String messsgeBody) {
		super(version, group, type, payLoadLength, messsgeBody);
		setScrewerPlayerID(Integer.parseInt(messsgeBody.substring(0, 4)));
		setAnsweringPlayerID(Integer.parseInt(messsgeBody.substring(4, 8)));
		setTimeOut(Integer.parseInt(messsgeBody.substring(8)));
		
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

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public String toString() {
		return Integer.toString(getVersion()) + getGroup().name() + getType() + Integer.toString(getPayloadLength())
		   + Integer.toString(screwerPlayerID)  + Integer.toString(answeringPlayerID) 
		   +  Integer.toString(timeOut);
	}
	
}
