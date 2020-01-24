package messages;

import dbconnection.Question;

public class QuestionMessage extends Message{
	
	private Question question;
	private long answeringTimeout;
	

	public QuestionMessage() {
		super();
		this.setMessageType(MessageType.QUESTION);
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getAnsweringTimeout() {
		return answeringTimeout;
	}

	public void setAnsweringTimeout(long answeringTimeout) {
		this.answeringTimeout = answeringTimeout;
	}
	
	

}
