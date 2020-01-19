package Message;

import MessageType.PregamMessageType;

public class ReadyMessage extends Message {

	public ReadyMessage() {
		super(PregamMessageType.PLAYER_READY);
	}

}
