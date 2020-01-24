package messages;

public class AnswerMessage extends Message{
	
	private int answerId;

	public AnswerMessage() {
		super();
		this.setMessageType(MessageType.ANSWER);
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
}
