package parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import messages.AnswerMessage;
import messages.BuzzMessage;
import messages.CategorySelectionMessage;
import messages.GeneralTextMessage;
import messages.Message;
import messages.PlayerReadyMessage;
import messages.ScrewMessage;
import messages.SignOnMessage;

/**
 * Parser to read bytearray and get message details
 * 
 * @author david
 *
 */
public class RecieveParser {
	private final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
	private byte[] header = new byte[7];
	private byte version;
	private byte group;
	private byte type;
	private byte[]length = new byte[4];
	
	public Message parse(byte[] data){
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
		payload = new byte[0];
		if(data.length < (recievedMessage.getLength()+7)) {
			//TODO
			System.out.print(data.length+","+recievedMessage.getLength());
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
			recievedMessage = this.parseAnswer(recievedMessage, payload);
			break;
		case ANSWER_RESULT:
			break;
		case BUZZ:
			recievedMessage = this.parseBuzz(recievedMessage, payload);
			break;
		case BUZZ_RESULT:
			break;
		case CATEGORY_SELECTION:
			recievedMessage = this.parseCategorySelection(recievedMessage, payload);
			break;
		case CATEGORY_SELECTOR_ANNOUNCEMENT:
			break;
		case GAME_END:
			break;
		case GENERAL_TEXT:
			recievedMessage = this.parseGeneralText(recievedMessage, payload);
			break;
		case PLAYER_LIST:
			break;
		case PLAYER_READY:
			recievedMessage = this.parsePlayerReady(recievedMessage, payload);
			break;
		case QUESTION:
			break;
		case SCOREBOARD:
			break;
		case SCREW:
			recievedMessage = this.parseScrew(recievedMessage, payload);
			break;
		case SCREW_RESULT:
			break;
		case SIGN_ON:
			recievedMessage = this.parseSignOn(recievedMessage, payload);
			break;
		case SIGN_ON_RESPONSE:
			break;
		default:
			break;
		
		}

		return recievedMessage;
		
	}
	private Message parseBuzz(Message message, byte[] data) {
		BuzzMessage buzz = new BuzzMessage();
		buzz.setVersion(message.getVersion());
		buzz.setLength(message.getLength());
		return buzz;
	}
	private Message parseAnswer(Message message, byte[] data) {
		AnswerMessage answer = new AnswerMessage();
		answer.setVersion(message.getVersion());
		answer.setLength(message.getLength());
		answer.setAnswerId(byteArrayToInt(data));
		return answer;

	}
	private CategorySelectionMessage parseCategorySelection(Message message, byte[] data) {
		CategorySelectionMessage categorySelection = new CategorySelectionMessage();
		categorySelection.setVersion(message.getVersion());
		categorySelection.setLength(message.getLength());
		categorySelection.setCategoryIndex(byteArrayToInt(data));
		return categorySelection;
	}
	private GeneralTextMessage parseGeneralText(Message message, byte[] data) {
		GeneralTextMessage generalText = new GeneralTextMessage();
		generalText.setVersion(message.getVersion());
		generalText.setLength(message.getLength());
		generalText.setGeneralText(new String(data,UTF8_CHARSET));
		return generalText;
	}
	private PlayerReadyMessage parsePlayerReady(Message message, byte[] data) {
		PlayerReadyMessage playerReady = new PlayerReadyMessage();
		playerReady.setVersion(message.getVersion());
		playerReady.setLength(message.getLength());
		return playerReady;
	}
	private ScrewMessage parseScrew(Message message, byte[] data) {
		ScrewMessage screw = new ScrewMessage();
		screw.setVersion(message.getVersion());
		screw.setLength(message.getLength());
		screw.setScrewedPlayerId(byteArrayToInt(data));
		return screw;
	}
	private SignOnMessage parseSignOn(Message message, byte[] data) {
		SignOnMessage signOn = new SignOnMessage();
		signOn.setVersion(message.getVersion());
		signOn.setLength(message.getLength());
		signOn.setPlayerAlias(new String(data,UTF8_CHARSET));

		return signOn;
	}
	public static int byteArrayToInt(byte[] b) {
	    final ByteBuffer bb = ByteBuffer.wrap(b);
	    return bb.getInt();
	}
}
