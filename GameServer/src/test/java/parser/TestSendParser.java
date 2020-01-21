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
		SignOnResponse signOnResponse = new SignOnResponse();
		signOnResponse.setPlayerId(60);
		signOnResponse.setPlayerAlias("🦄🐿️");
		byte[]dataTest;
		byte[]dataSignOnResponse = new byte[] {0x01,0x00,0x01,0x00,0x00,0x00,0x0f,0x00,0x00,0x00,0x3c,
				(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f
		};
		dataTest = sendParser.messageToByteArray(signOnResponse);
		for(int i = 0; i < dataSignOnResponse.length; i++) {
			assertEquals(dataSignOnResponse[i], dataTest[i]);
		}
		GeneralText GeneralText = new GeneralText();
		GeneralText.setGeneralText("🦄🐿️");
		byte[]dataGeneralText = new byte[] {
				0x01,0x03,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f
		};
		dataTest = sendParser.messageToByteArray(GeneralText);
		for(int i = 0; i < dataGeneralText.length; i++) {
			assertEquals(dataGeneralText[i], dataTest[i]);
		}
		AnswerResult answerResult = new AnswerResult();
		answerResult.setCorrectAnswerID(10);
		answerResult.setSelectedAnswerID(5);
		byte[]dataAnswerResult = new byte[] {0x01,0x01,0x07,0x00,0x00,0x00,0x08
				,0x00,0x00,0x00,0x0a,0x00,0x00,0x00, 0x05
		};
		dataTest = sendParser.messageToByteArray(answerResult);
		assertEquals( dataAnswerResult.length, dataTest.length);
		for(int i = 0; i < dataAnswerResult.length; i++) {
			assertEquals(dataAnswerResult[i], dataTest[i]);
		}

	}

	@Test
	public void testIntToByteArray() {
		//fail("Not yet implemented");
	}

}
