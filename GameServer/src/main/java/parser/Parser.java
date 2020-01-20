package parser;

import java.nio.*;

import messages.*;

/**
 * Parser to read bytearray and get message details and the other way around
 * 
 * @author david
 *
 */
public class Parser {
	private byte[] header = new byte[7];
	private byte version;
	private byte group;
	private byte type;
	private byte[]length = new byte[4];
	
	public Message parse(byte[] data) {
		Message recievedMessage = new Message();
		for(int i = 0; i < 7; i++) {
			header[i] = data[i];
		}
		version = header[0];
		group = header[1];
		type = header[2];
		for(int i = 3; i < 7; i++) {
			length[i-3] = header[i];
		}
		
		
		recievedMessage.setVersion(version);
		recievedMessage.setGroup(group);
		recievedMessage.setType(type);
		recievedMessage.setLength(byteArrayToInt(length));
		recievedMessage.setMessageType();
		
		switch(recievedMessage.getMessageType()) {
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
			recievedMessage = this.parseSignOn(recievedMessage, data);
			break;
		case SIGN_ON_RESPONSE:
			break;
		default:
			break;
		
		}

		return recievedMessage;
		
	}
	private SignOn parseSignOn(Message message, byte[] data) {
		SignOn signOn = new SignOn();
		signOn.setVersion(message.getVersion());
		signOn.setLength(message.getLength());
		if(data.length < (message.getLength()+7)) {
			//TODO
			return null;
		} else if(data.length > (message.getLength()+7)){
			//TODO
		}
		else {
			byte[] alias = new byte[message.getLength()];
			for(int i = 0; i < message.getLength(); i++) {
				alias[i] = data[i+7];
			}
			signOn.setPlayerAlias(new String(alias));
		}
		return signOn;
	}
	public static int byteArrayToInt(byte[] b) {
	    final ByteBuffer bb = ByteBuffer.wrap(b);
	    return bb.getInt();
	}

	public static byte[] intToByteArray(int i) {
	    final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
	    bb.putInt(i);
	    return bb.array();
	}
}
