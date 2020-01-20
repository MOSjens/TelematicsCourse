package messages;

public class Answer extends Message{
	
	private int answerId;

	public Answer() {
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
