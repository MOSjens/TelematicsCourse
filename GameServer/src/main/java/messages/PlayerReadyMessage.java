package messages;

/** player ready mesage signaling one player is ready
 * @author IG4
 *
 */
public class PlayerReadyMessage extends Message{

	public PlayerReadyMessage() {
		super();
		this.setMessageType(MessageType.PLAYER_READY);
	}

}
