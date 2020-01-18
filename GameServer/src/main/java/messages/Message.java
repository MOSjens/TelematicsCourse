package messages;

/**
 * Superclass for all Messages. Contains Header information
 * 
 * @author IG4
 *
 */
public class Message {
	private int version;
	private int group;
	private int type;
	private int length;
	private MessageType messageType;
	
	public Message(){
		this.version = 0;
		this.group = 0;
		this.type = 0;
		this.length = 0;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
}
