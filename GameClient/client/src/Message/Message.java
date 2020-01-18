package Message;

import MessageType.GeneralMessageType;
import MessageType.InGameMessageType;
import MessageType.MessageType;
import MessageType.PostGameMessageType;
import MessageType.PregamMessageType;

public class Message {
	private int version;
	private MessageGroup group;
	private MessageType type;
	private int payloadLength;
	private String messageBody;

	public Message(int version, int group, int type, int payLoadLength, String messsgeBody) {
		setVersion(version);
		setGroup(group);
		setType(type, group);
		setPayloadLength(payLoadLength);
		setMessageBody(messsgeBody);
	}
	
	public int getVersion() {
		return version;
	}

	public MessageGroup getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = MessageGroup.values()[group];
	}

	public String  getType() {
		switch(group.ordinal()) { 
		case 0:
			return ((PregamMessageType)type).name();
			
		case 1:
			return ((InGameMessageType)type).name();
		case 2:
			return ((PostGameMessageType)type).name();
		case 3:
			return ((GeneralMessageType)type).name();
		}
		return null;
	}

	public void setType(int type,int group) {
		switch(group) {
		case 0:
			this.type = PregamMessageType.values()[type];
			break;
		case 1:
			this.type = InGameMessageType.values()[type];
			break;
		case 2:
			this.type = PostGameMessageType.values()[type];
			break;
		case 3:
			this.type = GeneralMessageType.values()[type];
			break;
		}
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
