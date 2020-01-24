package messages;

public class AnswerResultMessage extends Message {
	
	private int correctAnswerID;
	private int selectedAnswerID;

	public AnswerResultMessage() {
		super();
		this.setMessageType(MessageType.ANSWER_RESULT);
	}

	public int getCorrectAnswerID() {
		return correctAnswerID;
	}

	public void setCorrectAnswerID(int correctAnswerID) {
		this.correctAnswerID = correctAnswerID;
	}
	
	public int getSelectedAnswerID() {
		return selectedAnswerID;
	}

	public void setSelectedAnswerID(int selectedAnswerID) {
		this.selectedAnswerID = selectedAnswerID;
	}



}
