package messages;

public class GameEnd extends Message {

	public GameEnd() {
		super();
		this.setMessageType(MessageType.GAME_END);
	}

}
