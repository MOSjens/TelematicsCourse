package messages;

/**
 * SignOn Message with player alias
 * @author IG4
 *
 */
public class SignOnMessage extends Message {
	
	private String playerAlias;
	
	public SignOnMessage() {
		super();
		this.setMessageType(MessageType.SIGN_ON);
	}
	
	public SignOnMessage(String playerAlias) {
		super();
		this.setMessageType(MessageType.SIGN_ON);
		this.setPlayerAlias(playerAlias);
	}

	public String getPlayerAlias() {
		return playerAlias;
	}

	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}
}
