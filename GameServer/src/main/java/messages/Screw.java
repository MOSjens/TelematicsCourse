package messages;

public class Screw extends Message {
	
	private int screwedPlayerId;

	public Screw() {
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
