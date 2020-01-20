package messages;

/**
 * SignOn Message with player alias
 * @author IG4
 *
 */
public class SignOn extends Message {
	
	private String playerAlias;
	
	public SignOn() {
		super();
		this.setMessageType(MessageType.SIGN_ON);
	}
	

	public String getPlayerAlias() {
		return playerAlias;
	}

	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}
}
