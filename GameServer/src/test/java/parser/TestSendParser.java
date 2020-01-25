package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import dbconnection.Category;
import dbconnection.Difficulty;
import dbconnection.Question;
import messages.*;

public class TestSendParser {
	private SendParser sendParser;

	@Before
	public void setUp() throws Exception {
		sendParser = new SendParser();
	}

	@Test
	public void testParse() {
		SignOnResponseMessage signOnResponse = new SignOnResponseMessage();
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
		GeneralTextMessage GeneralText = new GeneralTextMessage();
		GeneralText.setGeneralText("🦄🐿️");
		byte[]dataGeneralText = new byte[] {
				0x01,0x03,0x00,0x00,0x00,0x00,0x0b,(byte) 0xf0,(byte) 0x9f,(byte) 0xa6,
				(byte) 0x84,(byte) 0xf0,(byte) 0x9f,(byte) 0x90,(byte) 0xbf,(byte) 0xef,(byte) 0xb8,(byte) 0x8f
		};
		dataTest = sendParser.messageToByteArray(GeneralText);
		for(int i = 0; i < dataGeneralText.length; i++) {
			assertEquals(dataGeneralText[i], dataTest[i]);
		}
		AnswerResultMessage answerResult = new AnswerResultMessage();
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
		GameEndMessage gameEnd = new GameEndMessage();
		byte[]dataGameEnd = new byte[] {0x01,0x02,0x00,0x00,0x00,0x00,0x00
		};
		dataTest = sendParser.messageToByteArray(gameEnd);
		assertEquals( dataGameEnd.length, dataTest.length);
		for(int i = 0; i < dataGameEnd.length; i++) {
			assertEquals(dataGameEnd[i], dataTest[i]);
		}
		
		ScrewResultMessage screwResult = new ScrewResultMessage();
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
		BuzzResultMessage buzzResult = new BuzzResultMessage();
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
		ScoreboardMessage scoreBoard = new ScoreboardMessage();
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
		PlayerListMessage playerList = new PlayerListMessage();
		LinkedHashMap<Integer,PairReadyAliasMessage> playerMap = new LinkedHashMap<Integer,PairReadyAliasMessage>();
		playerMap.put(42, new PairReadyAliasMessage(ReadyState.READY,"lel"));
		playerMap.put(3, new PairReadyAliasMessage(ReadyState.NOT_READY,"lul"));

		playerList.setMapPlayerIdToAlias(playerMap);
		byte[]dataplayerList = new byte[] {0x01, 0x03, 0x01, 0x00, 0x00, 0x00, 0x18
				, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x2a, 0x01, 
				0x6c, 0x65, 0x6c, 0x00, 0x00, 0x00, 0x03, 0x00, 0x6c, 0x75, 0x6c
		};
		dataTest = sendParser.messageToByteArray(playerList);
		assertEquals( dataplayerList.length, dataTest.length);
		for(int i = 0; i < dataplayerList.length; i++) {
			assertEquals(dataplayerList[i], dataTest[i]);
		}
	}
	
	@Test
	public void testQuestionMessagetoByteArray() {
		byte[] dataTest;
		QuestionMessage QuestionMessage = new QuestionMessage();
		QuestionMessage.setAnsweringTimeout(0);
		Question question = new Question();
		question.setCategory(Category.ANIMALS);
		question.setDifficulty(Difficulty.EASY);
		question.setQuestionText("Rabbits are carnivores");
		ArrayList<String> options = new ArrayList<String>();
		options.add(0, "True");
		options.add(1,"False");
		question.setAnswerOptions(options);
		QuestionMessage.setQuestion(question);
		byte[]dataQuestionMessage = new byte[] {0x1, 0x1, 0x2, 0x0, 0x0, 0x0, 0x4A,
				0x0, 0x0, 0x0, 0x20,    0x0, 0x0, 0x0, 0x24,     0x0, 0x0, 0x0, 0x2B, 
				0x0, 0x0, 0x0, 0x2,     0x0, 0x0, 0x0, 0x0,      0x0, 0x0, 0x00, 0x00, 
				0x0, 0x0, 0x0, 0x41,     0x0, 0x0, 0x0, 0x45,   
				0x45, 0x61, 0x73, 0x79,   0x41, 0x6E, 0x69, 0x6D, 0x61, 0x6C, 0x73, 
				0x52, 0x61, 0x62, 0x62, 0x69, 0x74, 0x73, 0x20, 
				0x61, 0x72, 0x65,0x20, 0x63, 0x61, 0x72, 0x6E, 0x69, 0x76, 
				0x6F, 0x72, 0x65, 0x73, 0x54, 0x72, 0x75, 0x65, 0x46, 0x61, 
				0x6C, 0x73, 0x65
		};
		
		dataTest = sendParser.messageToByteArray(QuestionMessage);
		assertEquals( dataQuestionMessage.length, dataTest.length);
		for(int i = 0; i < dataQuestionMessage.length; i++) {
			assertEquals(dataQuestionMessage[i], dataTest[i]);
		}
	}

	@Test
	public void testIntToByteArray() {
		//fail("Not yet implemented");
	}

}
