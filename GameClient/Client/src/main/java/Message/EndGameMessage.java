package Message;

import MessageType.PostGameMessageType;

public class EndGameMessage extends Message {
    public EndGameMessage() {
        super(PostGameMessageType.GAME_END);
    }
}
