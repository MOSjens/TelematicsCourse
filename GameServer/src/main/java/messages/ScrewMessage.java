package messages;

/** screw message from player to force another player to answer
 * @author IG4
 *
 */
public class ScrewMessage extends Message {
	
	private int screwedPlayerId;

	public ScrewMessage() {
		super();
		this.setMessageType(MessageType.SCREW);
	}
	
	public ScrewMessage(int screwedPlayerId) {
		super();
		this.setMessageType(MessageType.SCREW);
		this.setScrewedPlayerId(screwedPlayerId);
	}


	public int getScrewedPlayerId() {
		return screwedPlayerId;
	}

	public void setScrewedPlayerId(int screwedPlayerId) {
		this.screwedPlayerId = screwedPlayerId;
	}

}
