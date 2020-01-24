package messages;

public class QuestionMessage extends Message{

	public QuestionMessage() {
		super();
		this.setMessageType(MessageType.QUESTION);
	}

}
