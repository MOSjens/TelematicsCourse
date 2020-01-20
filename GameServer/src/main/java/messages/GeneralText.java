package messages;

public class GeneralText extends Message {
	
	private String generalText;

	public GeneralText() {
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
