package messages;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMessage {

	@Test
	public void testSetMessageType() {
		Message message = new Message();
		message.setMessageType();
		assertEquals( MessageType.SIGN_ON, message.getMessageType() );
		message.setGroup(1);
		message.setType(9);
		message.setMessageType();
		assertEquals( MessageType.SCREW_RESULT, message.getMessageType() );
	}

}
