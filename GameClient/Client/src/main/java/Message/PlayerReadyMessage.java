package Message;

import MessageType.PregamMessageType;

public class PlayerReadyMessage extends Message {

    public PlayerReadyMessage() {
        super(PregamMessageType.PLAYER_READY);
    }

}
