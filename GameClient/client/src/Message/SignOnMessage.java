package Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import MessageType.PregamMessageType;

public class SignOnMessage extends Message {
	private String playerAlias;
	
	public SignOnMessage(String playerAlias) {
		super(PregamMessageType.SIGN_ON, playerAlias.getBytes(StandardCharsets.UTF_8));
		this.playerAlias = playerAlias;
	}
	
	/* möglicher lösung für die server-side encoding von signOn
	public SignOnMessage(byte[] messageBody) {
		super(PregamMessageType.SIGN_ON, messageBody);
		ByteBuffer buffer = ByteBuffer.wrap(messageBody);
		this.playerAlias = StandardCharsets.UTF_8.decode(buffer).toString();
	}
	*/
}