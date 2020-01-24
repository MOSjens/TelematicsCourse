package parser;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import messages.Answer;
import messages.Buzz;
import messages.CategorySelection;
import messages.GeneralText;
import messages.Message;
import messages.MessageType;
import messages.PlayerReady;
import messages.Screw;
import messages.SignOn;

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
		assertEquals(1, newMessage.getVersion() );
		assertEquals(1, newMessage.getGroup());
		assertEquals(2, newMessage.getType());
		assertEquals(70,  newMessage.getLength());
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
	
	@Test
	public void testParseBuzz() {
		byte[]data = new byte[] {
			0x01, 0x01, 0x04, 0x00, 0x00, 0x00, 0x00
		};
		
		Message newMessage = recieveParser.parse(data);
		Buzz buzz = (Buzz) newMessage;
		assertEquals( buzz.getVersion(), 1 );
		assertEquals( buzz.getGroup(), 1);
		assertEquals( buzz.getType(), 4);
		assertEquals( buzz.getMessageType(), MessageType.BUZZ);
		assertEquals( buzz.getLength(), 0 );
	}
	
	@Test
	public void testParseplayerReady() {
		byte[]data = new byte[] {
				0x01, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00
		};
		
		Message newMessage = recieveParser.parse(data);
		PlayerReady playerReady = (PlayerReady) newMessage;
		assertEquals( playerReady.getVersion(), 1 );
		assertEquals( playerReady.getGroup(), 0);
		assertEquals( playerReady.getType(), 2);
		assertEquals( playerReady.getMessageType(), MessageType.PLAYER_READY);
		assertEquals( playerReady.getLength(), 0 );
	}

	@Test
	public void testParsescrew() {
		byte[]data = new byte[] {
				0x01, 0x01, 0x08, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x02
		};
		
		Message newMessage = recieveParser.parse(data);
		Screw screw = (Screw) newMessage;
		assertEquals( screw.getVersion(), 1 );
		assertEquals( screw.getGroup(), 1);
		assertEquals( screw.getType(), 8);
		assertEquals( screw.getMessageType(), MessageType.SCREW);
		assertEquals( screw.getLength(), 4 );
		assertEquals(screw.getScrewedPlayerId(), 2);
	}	
	
	@Test
	public void testParsecategorySelection() {
		byte[]data = new byte[] {
				0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x02
		};
		
		Message newMessage = recieveParser.parse(data);
		CategorySelection categorySelection = (CategorySelection) newMessage;
		assertEquals( categorySelection.getVersion(), 1 );
		assertEquals( categorySelection.getGroup(), 1);
		assertEquals( categorySelection.getType(), 1);
		assertEquals( categorySelection.getMessageType(), MessageType.CATEGORY_SELECTION);
		assertEquals( categorySelection.getLength(), 4 );
		assertEquals(categorySelection.getCategoryIndex(), 2);
	}
	
	@Test
	public void testParsegeneralText() {
		byte[]data = new byte[] {
				0x01, 0x03, 0x00, 0x00, 0x00, 0x00, 0x14, 0x57, 0x65, 0x20, 0x6c, 0x6f, 
				0x76, 0x65, 0x20, 0x6e, 0x6f, 0x6f, 0x64, 0x6c, 0x65, 0x20, 0x73, 0x6f, 0x75, 0x70, 0x21
		};
		
		Message newMessage = recieveParser.parse(data);
		GeneralText generalText = (GeneralText) newMessage;
		assertEquals(1, generalText.getVersion());
		assertEquals(3, generalText.getGroup());
		assertEquals(0, generalText.getType());
		assertEquals(MessageType.GENERAL_TEXT, generalText.getMessageType());
		assertEquals(20, generalText.getLength() );
		assertEquals("We love noodle soup!", generalText.getGeneralText());
	}
}
