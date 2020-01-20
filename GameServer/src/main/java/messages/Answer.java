package messages;

public class Answer extends Message{
	
	private int answerID;

	public Answer() {
		super();
		this.setMessageType(MessageType.ANSWER);
	}

	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
}
