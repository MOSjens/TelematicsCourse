package messages;

public class PlayerReady extends Message{

	public PlayerReady() {
		super();
		this.setMessageType(MessageType.PLAYER_READY);
	}

}
