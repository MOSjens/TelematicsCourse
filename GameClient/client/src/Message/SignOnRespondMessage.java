package Message;

public class SignOnRespondMessage extends Message {

	private int playerID;
	private String alias;
	
	public SignOnRespondMessage(int version, int group, int type, int payLoadLength, String messsgeBody) {
		super(version, group, type, payLoadLength, messsgeBody);
		setPlayerID(Integer.parseInt(messsgeBody.substring(0, 4)));
		setAlias(messsgeBody.substring(4));
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public String toString() {
		return Integer.toString(getVersion()) + getGroup().name() + getType() + Integer.toString(getPayloadLength())
		   + Integer.toString(playerID) + alias;
	}

}
