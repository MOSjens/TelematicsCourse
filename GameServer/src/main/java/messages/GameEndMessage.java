package messages;

/** game end message signaling the end of the game
 * @author IG4
 *
 */
public class GameEndMessage extends Message {

	public GameEndMessage() {
		super();
		this.setMessageType(MessageType.GAME_END);
	}

}
