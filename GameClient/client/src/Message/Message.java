package Message;


public class Message {
	private int version;
	private MessageGroup group;
	private MessageType type;
	private int payloadLength;
	private String messageBody;

	public int getVersion() {
		return version;
	}

	public MessageGroup getGroup() {
		return group;
	}

	public void setGroup(MessageGroup group) {
		this.group = group;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getPayloadLength() {
		return payloadLength;
	}

	public void setPayloadLength(int payloadLength) {
		this.payloadLength = payloadLength;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
}
