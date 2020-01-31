package messages;

/** buzzresult message to tell a player that he buzzed first and is aloud to answer
 * @author IG4
 *
 */
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
		this.setAnsweringPlayerId(answeringPlayerId);
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
