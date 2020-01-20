package messages;

public class PlayerList extends Message {

	public PlayerList() {
		super();
		this.setMessageType(MessageType.PLAYER_LIST);
	}

}
