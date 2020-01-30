package parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import messages.AnswerMessage;
import messages.BuzzMessage;
import messages.CategorySelectionMessage;
import messages.GeneralTextMessage;
import messages.Message;
import messages.MessageType;
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
	private static final int  HEADER_LENGTH = 7;
	private byte[] header = new byte[HEADER_LENGTH];
	private byte version;
	private byte group;
	private byte type;
	private byte[]length;
	
	/** parse bytearray from tcp socket to a information in a message the server can handle
	 * @param data bytearray to parse
	 * @return message with information for server
	 */
	public Message parse(byte[] data){
		Message recievedMessage = new Message();
		header = new byte[HEADER_LENGTH];
		length = new byte[4];
		//get header information from byte array
		header = Arrays.copyOfRange(data, 0, HEADER_LENGTH);
		version = header[0];
		group = header[1];
		type = header[2];
		length = Arrays.copyOfRange(header, 3, HEADER_LENGTH);
		byte[] payload;
		
		//fill message with header information and get message type
		recievedMessage.setVersion(version);
		recievedMessage.setGroup(group);
		recievedMessage.setType(type);
		recievedMessage.setLength(byteArrayToInt(length));
		recievedMessage.setMessageType();
		payload = new byte[0];
		if(data.length < (recievedMessage.getLength()+HEADER_LENGTH)) {
			System.out.println("ERROR: Recieved message payload length is shorter than expected");
			recievedMessage.setMessageType(MessageType.UNDEFINED);
			return recievedMessage;
		} else if(data.length > (recievedMessage.getLength()+HEADER_LENGTH)){
			System.out.println("ERROR: Recieved message payload length is longer than expected");
			recievedMessage.setMessageType(MessageType.UNDEFINED);
			return recievedMessage;
		}
		else {
			payload = new byte[recievedMessage.getLength()];
			payload = Arrays.copyOfRange(data, HEADER_LENGTH, data.length);
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
		case UNDEFINED:
			System.out.println("ERROR: UNDEFINED Message");
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
