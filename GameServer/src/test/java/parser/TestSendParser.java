package parser;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

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
		GameEnd gameEnd = new GameEnd();
		byte[]dataGameEnd = new byte[] {0x01,0x02,0x00,0x00,0x00,0x00,0x00
		};
		dataTest = sendParser.messageToByteArray(gameEnd);
		assertEquals( dataGameEnd.length, dataTest.length);
		for(int i = 0; i < dataGameEnd.length; i++) {
			assertEquals(dataGameEnd[i], dataTest[i]);
		}
		
		ScrewResult screwResult = new ScrewResult();
		screwResult.setScrewingPlayerId(10);
		screwResult.setAnsweringPlayerId(5);
		screwResult.setAnswerTimeout(15000);
		byte[]dataScrewResult = new byte[] {0x01,0x01,0x09,0x00,0x00,0x00,0x10
				,0x00,0x00,0x00,0x0a,0x00,0x00,0x00, 0x05
				,0x00,0x00,0x00,0x00,0x00,0x00,0x3A, (byte)0x98
		};
		dataTest = sendParser.messageToByteArray(screwResult);
		assertEquals( dataScrewResult.length, dataTest.length);
		for(int i = 0; i < dataScrewResult.length; i++) {
			assertEquals(dataScrewResult[i], dataTest[i]);
		}
		
	}
	
	@Test
	public void testBuzzResulttoByteArray() {
		byte[] dataTest;
		BuzzResult buzzResult = new BuzzResult();
		buzzResult.setAnsweringPlayerId(99);
		buzzResult.setAnswerTimeout(15000);
		byte[]databuzzResult = new byte[] {0x01,0x01,0x05,0x00,0x00,0x00,0x0c
				,0x00,0x00,0x00,0x63
				,0x00,0x00,0x00,0x00,0x00,0x00,0x3A, (byte)0x98
		};
		dataTest = sendParser.messageToByteArray(buzzResult);
		assertEquals( databuzzResult.length, dataTest.length);
		for(int i = 0; i < databuzzResult.length; i++) {
			assertEquals(databuzzResult[i], dataTest[i]);
		}
	}
	
	@Test
	public void testScoreBoardToByteArray() {
		byte[] dataTest;
		Scoreboard scoreBoard = new Scoreboard();
		scoreBoard.setRoundLeft(10);
		LinkedHashMap<Integer,Integer> scoreMap = new LinkedHashMap<Integer,Integer>();
		scoreMap.put(42, 4096);
		scoreMap.put(1, 3);
		scoreMap.put(5, 9);
		scoreBoard.setMapPlayerIdToScore(scoreMap);
		byte[]datascoreBoard = new byte[] {0x1, 0x1, 0x6, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0xA, 
				0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x2A, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x1, 
				0x00, 0x00, 0x00, 0x3, 0x00, 0x00, 0x00, 0x5, 0x00, 0x00, 0x00, 0x9
		};
		dataTest = sendParser.messageToByteArray(scoreBoard);
		assertEquals( datascoreBoard.length, dataTest.length);
		for(int i = 0; i < datascoreBoard.length; i++) {
			assertEquals(datascoreBoard[i], dataTest[i]);
		}
	}
	
	@Test
	public void testplayerListToByteArray() {
		byte[] dataTest;
		PlayerList playerList = new PlayerList();
		LinkedHashMap<Integer,PairReadyAlias> playerMap = new LinkedHashMap<Integer,PairReadyAlias>();
		playerMap.put(42, new PairReadyAlias(ReadyState.READY,"lel"));
		playerMap.put(3, new PairReadyAlias(ReadyState.NOT_READY,"lul"));

		playerList.setMapPlayerIdToAlias(playerMap);
		byte[]dataplayerList = new byte[] {0x01, 0x03, 0x01, 0x00, 0x00, 0x00, 0x18
				, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x2a, 0x01, 
				0x6c, 0x65, 0x6c, 0x00, 0x00, 0x00, 0x03, 0x00, 0x6c, 0x75, 0x6c
		};
		dataTest = sendParser.messageToByteArray(playerList);
		assertEquals( dataplayerList.length, dataTest.length);
		for(int i = 0; i < dataplayerList.length; i++) {
			System.out.print(i);
			assertEquals(dataplayerList[i], dataTest[i]);
		}
	}

	@Test
	public void testIntToByteArray() {
		//fail("Not yet implemented");
	}

}
