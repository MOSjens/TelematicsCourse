package parser;
import messages.*;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class TestRecieveParser {
	private RecieveParser recieveParser;
	
	@Before
	public void setUp() throws Exception {
		recieveParser = new RecieveParser();
	}
	
	@Test
	public void testParse() {
		
		
		byte[] data = new byte[] { 
		0x1,0x1,0x2,0x0,0x0,0x0,0x46,0x0,0x0,0x0,0x24,0x0,0x0,0x0,0x2d,0x0,0x0,0x0,0x32,0x0,0x0,0x0,0x3
		,0x0,0x0,0x0,0x0,0x0,0x0,0x27,0x10,0x0,0x0,0x0,0x3f,0x0,0x0,0x0,0x40,0x0,0x0,0x0,0x44,0x56,0x65
		,0x72,0x79,0x20,0x45,0x61,0x73,0x79,0x4d,0x61,0x74,0x68,0x73,0x57,0x68,0x61
		,0x74,0x27,0x73,0x20,0x31,0x20,0x2b,0x20,0x31,0x3f,0x32,0x66,0x6f,0x75,0x72,0x34,0x32 };
		
		Message newMessage = recieveParser.parse(data);
		/*System.out.println("Version: "+ newMessage.getVersion()
							+" Group: "+ newMessage.getGroup()
							+" Type: "+ newMessage.getType()
							+" Length: "+ newMessage.getLength());*/
		assertEquals( newMessage.getVersion(), 1 );
		assertEquals( newMessage.getGroup(), 1 );
		assertEquals( newMessage.getType(), 2 );
		assertEquals( newMessage.getLength(), 70 );
		//fail("Not yet implemented");
	}

	@Test
	public void testParseSignOn() {
		byte[]data = new byte[] {
				0x01,0x00,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f
		};
		
		Message newMessage = recieveParser.parse(data);
		SignOn signOn = (SignOn) newMessage;
		assertEquals( signOn.getVersion(), 1 );
		assertEquals( signOn.getGroup(), 0);
		assertEquals( signOn.getType(), 0);
		assertEquals( signOn.getLength(), 11 );
		assertEquals(signOn.getPlayerAlias(), "🦄🐿️");
	}
	
	@Test
	public void testParseAnswer() {
		byte[]data = new byte[] {
				0x01,0x01,0x03,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x00
		};
		
		Message newMessage = recieveParser.parse(data);
		Answer answer = (Answer) newMessage;
		assertEquals( answer.getVersion(), 1 );
		assertEquals( answer.getGroup(), 1);
		assertEquals( answer.getType(), 3);
		assertEquals( answer.getMessageType(), MessageType.ANSWER);
		assertEquals( answer.getLength(), 4 );
		assertEquals(answer.getAnswerId(), 0);
	}
}
