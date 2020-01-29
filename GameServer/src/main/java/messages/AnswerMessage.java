package messages;

/** answer message with the index of the answer
 * @author IG4
 *
 */
public class AnswerMessage extends Message{
	
	private int answerId;

	public AnswerMessage() {
		super();
		this.setMessageType(MessageType.ANSWER);
	}
	
	public AnswerMessage(int answerId) {
		super();
		this.setMessageType(MessageType.ANSWER);
		this.setAnswerId(answerId);
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
}
