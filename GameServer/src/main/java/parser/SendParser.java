package parser;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;

import messages.*;

/**
 * Parser to write message details into bytearray
 * 
 * @author IG4
 *
 */
public class SendParser {
	private byte[] header;
	private byte[] payload;
	private byte[] byteArray;
	private int length;
	private byte[] payloadLength;

	public SendParser() {
		
	}
	
	public byte[] messageToByteArray(Message sendMessage) {
		header = new byte[7];
		payload = new byte[0];
		header [0]= (byte) sendMessage.getVersion();
		header [1]= (byte) sendMessage.getGroup();
		header [2] = (byte) sendMessage.getType();
		length = 0;
		
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
			break;
		case GAME_END:
			break;
		case GENERAL_TEXT:
			payload = generalTextToByteArray(sendMessage);
			break;
		case PLAYER_LIST:
			break;
		case PLAYER_READY:
			break;
		case QUESTION:
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
		length = payload.length;
		payloadLength = intToByteArray(length);
		for(int i = 0; i < 4; i++) {
			header[i+3] = payloadLength[i];
		}
		
		byteArray = new byte[payload.length+header.length];
		int j =0;
		for(int i = 0; i < payload.length+header.length; i++) {
			if(i < header.length) {
				byteArray [i] = header[i];
			}
			else {
				byteArray [i] = payload[j];
				j++;
			}	
		}
		return byteArray;
		
	}
	
	
	
	private byte[] scoreboardToByteArray(Message sendMessage) {
		Scoreboard scoreboard = (Scoreboard) sendMessage;
		LinkedHashMap<Integer, Integer> scoreMap = scoreboard.getMapPlayerIdToScore();
		int amountPlayers = scoreMap.size();
		final ByteBuffer bb = ByteBuffer.allocate((2*amountPlayers+2)*Integer.SIZE / Byte.SIZE);
		
		return null;
	}

	private byte[] buzzResultToByteArray(Message sendMessage) {
		BuzzResult buzzResult = (BuzzResult) sendMessage;
		final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE + Long.SIZE/Byte.SIZE);
		bb.putInt(buzzResult.getAnsweringPlayerId());
		bb.putLong(buzzResult.getAnswerTimeout());
		return bb.array();
	}

	private byte[] screwResultToByteArray(Message sendMessage) {
		ScrewResult screwResult = (ScrewResult) sendMessage;
		final ByteBuffer bb = ByteBuffer.allocate(2*Integer.SIZE / Byte.SIZE + Long.SIZE/Byte.SIZE);
		bb.putInt(screwResult.getScrewingPlayerId());
		bb.putInt(screwResult.getAnsweringPlayerId());
		bb.putLong(screwResult.getAnswerTimeout());
		return bb.array();
	}

	private byte[] signOnResponseToByteArray(Message sendMessage) {
		SignOnResponse signOnResponse= (SignOnResponse) sendMessage;
		byte[] playerAlias = null;
		try {
			playerAlias = signOnResponse.getPlayerAlias().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int size = (Integer.SIZE / Byte.SIZE)+ playerAlias.length;
		final ByteBuffer bb = ByteBuffer.allocate(size);
		bb.putInt(signOnResponse.getPlayerId());
		bb.put(playerAlias);
		return bb.array();
	}

	private byte[] AnswerResultToByteArray(Message sendMessage) {
		AnswerResult answerResult= (AnswerResult) sendMessage;
		final ByteBuffer bb = ByteBuffer.allocate(2*Integer.SIZE / Byte.SIZE);
		bb.putInt(answerResult.getCorrectAnswerID());
		bb.putInt(answerResult.getSelectedAnswerID());
		return bb.array();
	}

	private byte[] generalTextToByteArray(Message sendMessage) {
		GeneralText generalText = (GeneralText) sendMessage;
		byte[] payload = generalText.getGeneralText().getBytes();
		return payload;
	}

	public static byte[] intToByteArray(int i) {
	    final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
	    bb.putInt(i);
	    return bb.array();
	}
}
