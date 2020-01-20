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
		byte[] payload;
		
		recievedMessage.setVersion(version);
		recievedMessage.setGroup(group);
		recievedMessage.setType(type);
		recievedMessage.setLength(byteArrayToInt(length));
		recievedMessage.setMessageType();
		
		if(data.length < (recievedMessage.getLength()+7)) {
			//TODO
			return null;
		} else if(data.length > (recievedMessage.getLength()+7)){
			//TODO
		}
		else {
			payload = new byte[recievedMessage.getLength()];
			for(int i = 0; i < recievedMessage.getLength(); i++) {
				payload[i] = data[i+7];
			}
		}
		
		switch(recievedMessage.getMessageType()) {
		case ANSWER:
			recievedMessage = this.parseAnswer(recievedMessage, data);
			break;
		case ANSWER_RESULT:
			break;
		case BUZZ:
			break;
		case BUZZ_RESULT:
			break;
		case CATEGORY_SELECTION:
			recievedMessage = this.parseCategorySelection(recievedMessage, data);
			break;
		case CATEGORY_SELECTOR_ANNOUNCEMENT:
			break;
		case GAME_END:
			break;
		case GENERAL_TEXT:
			recievedMessage = this.parseGeneralText(recievedMessage, data);
			break;
		case PLAYER_LIST:
			break;
		case PLAYER_READY:
			recievedMessage = this.parsePlayerReady(recievedMessage, data);
			break;
		case QUESTION:
			break;
		case SCOREBOARD:
			break;
		case SCREW:
			recievedMessage = this.parseScrew(recievedMessage, data);
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
	private Message parseAnswer(Message message, byte[] data) {
		Answer answer = new Answer();
		answer.setVersion(message.getVersion());
		answer.setLength(message.getLength());
		answer.setAnswerId(byteArrayToInt(data));
		return answer;

	}
	private CategorySelection parseCategorySelection(Message message, byte[] data) {
		CategorySelection categorySelection = new CategorySelection();
		categorySelection.setVersion(message.getVersion());
		categorySelection.setLength(message.getLength());
		categorySelection.setCategoryIndex(byteArrayToInt(data));
		return categorySelection;
	}
	private GeneralText parseGeneralText(Message message, byte[] data) {
		GeneralText generalText = new GeneralText();
		generalText.setVersion(message.getVersion());
		generalText.setLength(message.getLength());
		generalText.setGeneralText(new String(data));
		return generalText;
	}
	private PlayerReady parsePlayerReady(Message message, byte[] data) {
		PlayerReady playerReady = new PlayerReady();
		playerReady.setVersion(message.getVersion());
		playerReady.setLength(message.getLength());
		return playerReady;
	}
	private Screw parseScrew(Message message, byte[] data) {
		Screw screw = new Screw();
		screw.setVersion(message.getVersion());
		screw.setLength(message.getLength());
		screw.setScrewedPlayerId(byteArrayToInt(data));
		return screw;
	}
	private SignOn parseSignOn(Message message, byte[] data) {
		SignOn signOn = new SignOn();
		signOn.setVersion(message.getVersion());
		signOn.setLength(message.getLength());
		signOn.setPlayerAlias(new String(data));
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
