package messages;

public class GeneralTextMessage extends Message {
	
	private String generalText;

	public GeneralTextMessage() {
		super();
		this.setMessageType(MessageType.GENERAL_TEXT);
	}

	public String getGeneralText() {
		return generalText;
	}

	public void setGeneralText(String generalText) {
		this.generalText = generalText;
	}

}
