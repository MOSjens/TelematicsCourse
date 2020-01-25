package messages;

public class BuzzResultMessage extends Message {
	
	private int answeringPlayerId;
	private long answerTimeout;

	public BuzzResultMessage() {
		super();
		this.setMessageType(MessageType.BUZZ_RESULT);
	}
	
	public BuzzResultMessage(int answeringPlayerId, long answerTimeout) {
		super();
		this.setMessageType(MessageType.BUZZ_RESULT);
		this.setAnswerTimeout(answerTimeout);
		this.setAnswerTimeout(answerTimeout);
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
