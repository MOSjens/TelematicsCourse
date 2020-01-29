package messages;

import dbconnection.Question;

/** question message with question text answer options and difficulty
 * @author IG4
 *
 */
public class QuestionMessage extends Message{
	
	private Question question;
	private long answerTimeout;
	

	public QuestionMessage() {
		super();
		this.setMessageType(MessageType.QUESTION);
	}
	
	public QuestionMessage(long answerTimeout, Question question) {
		super();
		this.setMessageType(MessageType.QUESTION);
		this.setAnswerTimeout(answerTimeout);
		this.setQuestion(question);
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getAnswerTimeout() {
		return answerTimeout;
	}

	public void setAnswerTimeout(long answeringTimeout) {
		this.answerTimeout = answeringTimeout;
	}
	
	

}
