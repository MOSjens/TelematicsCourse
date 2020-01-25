package parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import dbconnection.Category;
import dbconnection.Difficulty;
import dbconnection.Question;
import messages.AnswerResultMessage;
import messages.BuzzResultMessage;
import messages.CategorySelectorAnnouncementMessage;
import messages.GeneralTextMessage;
import messages.Message;
import messages.PairReadyAliasMessage;
import messages.PlayerListMessage;
import messages.QuestionMessage;
import messages.ScoreboardMessage;
import messages.ScrewResultMessage;
import messages.SignOnResponseMessage;

/**
 * Parser to write message details into bytearray
 * 
 * @author IG4
 *
 */
public class SendParser {
	private final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
	private ByteBuffer header;
	private byte[] payload;
	private ByteBuffer byteBuffer;
	private int payloadLength;

	public SendParser() {
		
	}
	
	public byte[] messageToByteArray(Message sendMessage) {
		
		//fill header with version group and type
		header = ByteBuffer.allocate(7);
		payload = new byte[0];
		header.put((byte) sendMessage.getVersion());
		header.put((byte) sendMessage.getGroup());
		header.put((byte) sendMessage.getType());
		
		//parse based on message type
		switch(sendMessage.getMessageType()) {
		case ANSWER:
			break;
		case ANSWER_RESULT:
			payload =AnswerResultToByteArray(sendMessage);
			break;
		case BUZZ:
			break;
		case BUZZ_RESULT:
			payload = buzzResultToByteArray(sendMessage);
			break;
		case CATEGORY_SELECTION:
			break;
		case CATEGORY_SELECTOR_ANNOUNCEMENT:
			payload = categorySelectorAnnouncementToByteArray(sendMessage);
			break;
		case GAME_END:
			break;
		case GENERAL_TEXT:
			payload = generalTextToByteArray(sendMessage);
			break;
		case PLAYER_LIST:
			payload = playerListToByteArray(sendMessage);
			break;
		case PLAYER_READY:
			break;
		case QUESTION:
			payload = questionToByteArray(sendMessage);
			break;
		case SCOREBOARD:
			payload = scoreboardToByteArray(sendMessage);
			break;
		case SCREW:
			break;
		case SCREW_RESULT:
			payload = screwResultToByteArray(sendMessage);
			break;
		case SIGN_ON:
			break;
		case SIGN_ON_RESPONSE:
			payload = signOnResponseToByteArray(sendMessage);
			break;
		default:
			break;
		
		}
		
		//build complete tcp payload
		payloadLength = payload.length;
		header.putInt(payloadLength);
		byteBuffer = ByteBuffer.allocate(payload.length+header.capacity());
		byteBuffer.put(header.array());
		byteBuffer.put(payload);
		
		return byteBuffer.array();
		
	}
	
	
	
	private byte[] categorySelectorAnnouncementToByteArray(Message sendMessage) {
		CategorySelectorAnnouncementMessage csaMessage = (CategorySelectorAnnouncementMessage) sendMessage;
		LinkedHashMap<Category, Difficulty> categoryMap = csaMessage.getCategoryDifficultyMap(); 

		int[] categoryOffsets;
		int amountCategories ;
		int offsetCounter;
		final ByteBuffer bb;
		
		//get values for offset part of the payload
		amountCategories = categoryMap.size();
		categoryOffsets= new int[2*amountCategories];
		categoryOffsets[0] = (2*amountCategories+2)*(Integer.SIZE / Byte.SIZE)+(Long.SIZE/ Byte.SIZE);
		offsetCounter = categoryOffsets[0];
		int i= 0;
		for (Entry<Category, Difficulty> entry : categoryMap.entrySet()) {
			categoryOffsets[i] = offsetCounter;
		    offsetCounter += entry.getValue().toString().getBytes(UTF8_CHARSET).length;
		    i++;
		    categoryOffsets[i] = offsetCounter;
		    offsetCounter += entry.getKey().toString().getBytes(UTF8_CHARSET).length;
		    i++;
		}
		
		//fill payload with offset
		bb = ByteBuffer.allocate(offsetCounter);
		bb.putLong(csaMessage.getCategoryTimeout());
		bb.putInt(csaMessage.getSelectingPlayerId());
		bb.putInt(amountCategories);
		for(int j = 0; j < categoryOffsets.length; j++) {
			bb.putInt(categoryOffsets[j]);
		}
		
		//fill payload with values
		for (Entry<Category, Difficulty> entry : categoryMap.entrySet()) {
		    bb.put(entry.getValue().toString().getBytes(UTF8_CHARSET));
		    bb.put(entry.getKey().toString().getBytes(UTF8_CHARSET));
		}
		
		return bb.array();
	}

	private byte[] questionToByteArray(Message sendMessage) {
		QuestionMessage questionMessage = (QuestionMessage) sendMessage;
		Question question = questionMessage.getQuestion();
		ArrayList<String> answerOptions = question.getAnswerOptions();
		int[] generalOffsets = new int [3];
		int[] optionOffsets;
		int amountOptions ;
		int offsetCounter;
		final ByteBuffer bb;
		
		//get values for offset part of the payload
		amountOptions = answerOptions.size();
		optionOffsets= new int[amountOptions];
		generalOffsets[0] = (amountOptions+4)*(Integer.SIZE / Byte.SIZE)+(Long.SIZE/ Byte.SIZE);
		offsetCounter = generalOffsets[0];
		offsetCounter+= question.getDifficulty().toString().getBytes(UTF8_CHARSET).length;
		generalOffsets[1] = offsetCounter; 
		offsetCounter+= question.getCategory().toString().getBytes(UTF8_CHARSET).length;
		generalOffsets[2] = offsetCounter;
		offsetCounter+= question.getQuestionText().getBytes(UTF8_CHARSET).length;
		int i= 0;
		for(String answerOption: answerOptions){
			optionOffsets[i] = offsetCounter;
			offsetCounter += answerOption.getBytes(UTF8_CHARSET).length;
			i++;
		}
		
		//fill payload with offsets
		bb = ByteBuffer.allocate(offsetCounter);
		for(int j = 0; j < generalOffsets.length; j++) {
			bb.putInt(generalOffsets[j]);
		}
		bb.putInt(amountOptions);
		bb.putLong(questionMessage.getAnswerTimeout());
		
		for(int j = 0; j < optionOffsets.length; j++) {
			bb.putInt(optionOffsets[j]);
		}
		
		//fill payload with values
		bb.put(question.getDifficulty().toString().getBytes(UTF8_CHARSET));
		bb.put(question.getCategory().toString().getBytes(UTF8_CHARSET));
		bb.put(question.getQuestionText().getBytes(UTF8_CHARSET));
		for(String answerOption: answerOptions){
			bb.put( answerOption.getBytes(UTF8_CHARSET));
		}
		
		return bb.array();
	}

	private byte[] playerListToByteArray(Message sendMessage) {
		PlayerListMessage PlayerList = (PlayerListMessage) sendMessage;
		int[] playerOffsets;
		int amountPlayers ;
		int offsetCounter;
		final ByteBuffer bb;
		byte readyState;
		byte[] alias;
		LinkedHashMap<Integer, PairReadyAliasMessage> playerMap = PlayerList.getMapPlayerIdToAlias();
		amountPlayers = playerMap.size();
		
		//get values for offsets
		playerOffsets = new int[amountPlayers];
		playerOffsets[0] = amountPlayers *(Integer.SIZE / Byte.SIZE);
		offsetCounter = playerOffsets[0];
		int i = 0;
		for (Entry<Integer, PairReadyAliasMessage> entry : playerMap.entrySet()) {
			playerOffsets[i] = offsetCounter;
			offsetCounter += (entry.getValue().alias.getBytes(UTF8_CHARSET).length +(Integer.SIZE / Byte.SIZE)+1);
			i++;
		}

		//fill payload with offsets
		bb = ByteBuffer.allocate(offsetCounter);
		for(int j = 0; j < playerOffsets.length; j++) {
			bb.putInt(playerOffsets[j]);
		}
		
		//fill payload with values
		for (Entry<Integer, PairReadyAliasMessage> entry : playerMap.entrySet()) {
		    bb.putInt(entry.getKey());
		    readyState = (byte) entry.getValue().readyState.getValue();
		    alias = entry.getValue().alias.getBytes(UTF8_CHARSET);
		    bb.put(readyState);
		    bb.put(alias);
		}
		return bb.array();
	}

	private byte[] scoreboardToByteArray(Message sendMessage) {
		ScoreboardMessage scoreBoard = (ScoreboardMessage) sendMessage;
		LinkedHashMap<Integer, Integer> scoreMap = scoreBoard.getMapPlayerIdToScore();
		int amountPlayers = scoreMap.size();
		final ByteBuffer bb = ByteBuffer.allocate((2*amountPlayers+2)*Integer.SIZE / Byte.SIZE);
		bb.putInt(scoreBoard.getRoundLeft());
		bb.putInt(amountPlayers);
		for (Map.Entry<Integer, Integer> entry : scoreMap.entrySet()) {
		    bb.putInt(entry.getKey());
		    bb.putInt(entry.getValue());
		}
		return bb.array();
	}

	private byte[] buzzResultToByteArray(Message sendMessage) {
		BuzzResultMessage buzzResult = (BuzzResultMessage) sendMessage;
		final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE + Long.SIZE/Byte.SIZE);
		bb.putInt(buzzResult.getAnsweringPlayerId());
		bb.putLong(buzzResult.getAnswerTimeout());
		return bb.array();
	}

	private byte[] screwResultToByteArray(Message sendMessage) {
		ScrewResultMessage screwResult = (ScrewResultMessage) sendMessage;
		final ByteBuffer bb = ByteBuffer.allocate(2*Integer.SIZE / Byte.SIZE + Long.SIZE/Byte.SIZE);
		bb.putInt(screwResult.getScrewingPlayerId());
		bb.putInt(screwResult.getAnsweringPlayerId());
		bb.putLong(screwResult.getAnswerTimeout());
		return bb.array();
	}

	private byte[] signOnResponseToByteArray(Message sendMessage) {
		SignOnResponseMessage signOnResponse= (SignOnResponseMessage) sendMessage;
		byte[] playerAlias = null;
		playerAlias = signOnResponse.getPlayerAlias().getBytes(UTF8_CHARSET);
		int size = (Integer.SIZE / Byte.SIZE)+ playerAlias.length;
		final ByteBuffer bb = ByteBuffer.allocate(size);
		bb.putInt(signOnResponse.getPlayerId());
		bb.put(playerAlias);
		return bb.array();
	}

	private byte[] AnswerResultToByteArray(Message sendMessage) {
		AnswerResultMessage answerResult= (AnswerResultMessage) sendMessage;
		final ByteBuffer bb = ByteBuffer.allocate(2*Integer.SIZE / Byte.SIZE);
		bb.putInt(answerResult.getCorrectAnswerID());
		bb.putInt(answerResult.getSelectedAnswerID());
		return bb.array();
	}

	private byte[] generalTextToByteArray(Message sendMessage) {
		GeneralTextMessage generalText = (GeneralTextMessage) sendMessage;
		byte[] payload = generalText.getGeneralText().getBytes(UTF8_CHARSET);
		return payload;
	}

	public static byte[] intToByteArray(int i) {
	    final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
	    bb.putInt(i);
	    return bb.array();
	}
}
