package messages;

public class Question extends Message{

	public Question() {
		super();
		this.setMessageType(MessageType.QUESTION);
	}

}
