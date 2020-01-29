package messages;

/** answer result message with the chosen answer by the player and the correct answer
 * @author IG4
 *
 */
public class AnswerResultMessage extends Message {
	
	private int correctAnswerID;
	private int selectedAnswerID;

	public AnswerResultMessage() {
		super();
		this.setMessageType(MessageType.ANSWER_RESULT);
	}
	
	public AnswerResultMessage(int correctAnswerId, int selectedAnswerId) {
		super();
		this.setMessageType(MessageType.ANSWER_RESULT);
		this.setCorrectAnswerID(correctAnswerId);
		this.setSelectedAnswerID(selectedAnswerId);
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
