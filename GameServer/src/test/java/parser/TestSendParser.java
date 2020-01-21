package parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import messages.*;

public class TestSendParser {
	private SendParser sendParser;

	@Before
	public void setUp() throws Exception {
		sendParser = new SendParser();
	}

	@Test
	public void testParse() {
		SignOn signOn = new SignOn();
		signOn.setPlayerAlias("🦄🐿️");
		byte[]data = new byte[] {0x01,0x00,0x00,0x00,0x00,0x00,0x00
				//0x01,0x00,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				//(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f
		};

		for(int i = 0; i < data.length; i++) {
			assertEquals(data[i], sendParser.parse(signOn)[i]);
		}

	}

	@Test
	public void testIntToByteArray() {
		//fail("Not yet implemented");
	}

}
