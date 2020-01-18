package Util;


import Message.Message;
/**
 * phase string to message
 * @author Thomas
 *
 */
public class Phaser {
	public static Message phaserToMessage(String message) {
		int version = message.charAt(0);
		int group = message.charAt(1);
		int type = message.charAt(2);
		int payloadlength = Integer.parseInt(message.substring(3, 6));
		String messageCon = message.substring(7, 7 + payloadlength);
		
		
		return null;
	}

}
