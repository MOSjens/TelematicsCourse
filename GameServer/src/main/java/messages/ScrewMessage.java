package messages;

public class ScrewMessage extends Message {
	
	private int screwedPlayerId;

	public ScrewMessage() {
		super();
		this.setMessageType(MessageType.SCREW);
	}

	public int getScrewedPlayerId() {
		return screwedPlayerId;
	}

	public void setScrewedPlayerId(int screwedPlayerId) {
		this.screwedPlayerId = screwedPlayerId;
	}

}
