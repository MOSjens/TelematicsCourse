package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import Message.Message;
import Message.MessageGroup;
import Message.ParseException;
import Message.SignOnMessage;
import Message.SignOnRespondMessage;
import MessageType.PregamMessageType;
import parser.ReceiveParser;

class ParserTest {

	@Test
	void signonTest() {
		Message m = new SignOnMessage("Ghiath");
		byte[] encoded = {1, 0, 0, 0, 0, 0, 6, 71, 104, 105, 97, 116, 104};
		assertArrayEquals(encoded, m.getEncodedMessage());
		}
	
	@Test
	void signOnResponseTest() {
		byte[] messageBody = { 0, 0, 0, 1,  71, 104, 105, 97, 116, 104};
		SignOnRespondMessage m = new SignOnRespondMessage(messageBody);
		assertEquals(1, m.getPlayerID());
		assertEquals("Ghiath", m.getAlias());
	}
	
	@Test
	void parserTest() throws IOException, ParseException {
		byte[] message = {1, 0, 1, 0, 0, 0, 10, 0, 0, 0, 1, 71, 104, 105, 97, 116, 104};
		Message m = ReceiveParser.ParsMessage(new ByteArrayInputStream(message));
		assertEquals(1, m.getVersion());
		assertEquals(MessageGroup.PRE_GAME, m.getGroup());
		assertEquals(PregamMessageType.SIGN_ON_RESPONSE, m.getType());
		assertEquals(10, m.getPayloadLength());
		SignOnRespondMessage m2 = (SignOnRespondMessage)m;
		assertEquals(1, m2.getPlayerID());
		assertEquals("Ghiath", m2.getAlias());
	}

}
