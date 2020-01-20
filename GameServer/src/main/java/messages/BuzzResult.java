package messages;

public class BuzzResult extends Message {
	
	private int answeringPlayerId;
	private long answerTimeout;

	public BuzzResult() {
		super();
		this.setMessageType(MessageType.BUZZ_RESULT);
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
