package messages;

public class ScrewResultMessage extends Message{
	
	private int screwingPlayerId;
	private int answeringPlayerId;
	private long answerTimeout;

	public ScrewResultMessage() {
		super();
		this.setMessageType(MessageType.SCREW_RESULT);
	}
	
	public ScrewResultMessage(int screwingPlayerId, int answeringPlayerId, long answerTimeout) {
		super();
		this.setMessageType(MessageType.SCREW_RESULT);
		this.setScrewingPlayerId(screwingPlayerId);
		this.setAnsweringPlayerId(answeringPlayerId);
		this.setAnswerTimeout(answerTimeout);
	}

	public int getScrewingPlayerId() {
		return screwingPlayerId;
	}

	public void setScrewingPlayerId(int screwingPlayerId) {
		this.screwingPlayerId = screwingPlayerId;
	}

	public int getAnsweringPlayerId() {
		return answeringPlayerId;
	}

	public void setAnsweringPlayerId(int answeringPlayerId) {
		this.answeringPlayerId = answeringPlayerId;
	}

	public long getAnswerTimeout() {
		return answerTimeout;
	}

	public void setAnswerTimeout(long answerTimeout) {
		this.answerTimeout = answerTimeout;
	}

}
