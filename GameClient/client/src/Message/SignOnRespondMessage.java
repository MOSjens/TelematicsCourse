package Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import MessageType.PregamMessageType;

public class SignOnRespondMessage extends Message {

	private int playerID;
	private String alias;
	
	public SignOnRespondMessage(byte[] messsgeBody) {
		super(PregamMessageType.SIGN_ON_RESPONSE, messsgeBody);
		ByteBuffer buffer = ByteBuffer.wrap(messsgeBody);
		setPlayerID(buffer.getInt());
		setAlias(StandardCharsets.UTF_8.decode(buffer).toString());
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
