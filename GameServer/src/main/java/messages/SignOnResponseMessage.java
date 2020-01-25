package messages;

public class SignOnResponseMessage extends Message {
	
	private int playerId;
	private String playerAlias;

	public SignOnResponseMessage() {
		super();
		this.setMessageType(MessageType.SIGN_ON_RESPONSE);
	}
	
	public SignOnResponseMessage(int playerId, String playerAlias ) {
		super();
		this.setMessageType(MessageType.SIGN_ON_RESPONSE);
		this.setPlayerId(playerId);
		this.setPlayerAlias(playerAlias);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerAlias() {
		return playerAlias;
	}

	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}

}
