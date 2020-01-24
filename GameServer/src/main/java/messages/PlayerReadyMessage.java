package messages;

public class PlayerReadyMessage extends Message{

	public PlayerReadyMessage() {
		super();
		this.setMessageType(MessageType.PLAYER_READY);
	}

}
