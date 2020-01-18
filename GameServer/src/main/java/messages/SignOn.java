package messages;

/**
 * SignOn Message with player alias
 * @author IG4
 *
 */
public class SignOn extends Message {
	private String playerAlias;

	public String getPlayerAlias() {
		return playerAlias;
	}

	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}
}
