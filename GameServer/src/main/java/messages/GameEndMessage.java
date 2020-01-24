package messages;

public class GameEndMessage extends Message {

	public GameEndMessage() {
		super();
		this.setMessageType(MessageType.GAME_END);
	}

}
