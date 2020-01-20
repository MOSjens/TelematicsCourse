package messages;

public class ScrewResult extends Message{
	
	private int screwingPlayerId;
	private int answeringPlayerId;
	private long answerTimeout;

	public ScrewResult() {
		super();
		this.setMessageType(MessageType.SCREW_RESULT);
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
