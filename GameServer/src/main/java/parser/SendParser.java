package parser;

import java.nio.ByteBuffer;

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
			break;
		case BUZZ:
			break;
		case BUZZ_RESULT:
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
			break;
		case SCREW:
			break;
		case SCREW_RESULT:
			break;
		case SIGN_ON:
			break;
		case SIGN_ON_RESPONSE:
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
