package message;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import Message.Message;
import Message.PlayerListMessage;
import  parser.ReceiveParser;;
class PlayerListing {

	@Test
	void test() {
		
		byte[] array = {0x01,0x03,0x01,0x00,0x00,0x00,0x18,0x00,0x00,0x00,0x08,0x00,0x00,0x00,0x10,0x00,0x00,0x00,0x2a,0x01,0x6c
				,0x65,0x6c,0x00,0x00,0x00,0x03,0x00,0x6c,0x75,0x6c};
		InputStream stream = new ByteArrayInputStream(array);
		try {
		Message message = ReceiveParser.ParsMessage(stream);
		assertEquals("lel", ((PlayerListMessage)message).getPlayerAlias(0));
		assertEquals("lel", ((PlayerListMessage)message).getPlayerAlias(0));
		
		} catch (Exception e) {e.printStackTrace();}
		
	}

}
