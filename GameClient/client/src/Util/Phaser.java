package Util;


import Message.BRMessage;
import Message.Message;
import Message.SRMessage;
import Message.SignOnRespondMessage;

/**
 * phase string to message
 * @author Thomas
 *
 */
public class Phaser {
	public static Message phaserToMessage(String message) {
		int version = Character.getNumericValue(message.charAt(0));
		int group = Character.getNumericValue(message.charAt(1));
		int type = Character.getNumericValue(message.charAt(2));
		int payloadlength = Integer.parseInt(message.substring(3, 7));
		String messageCon = message.substring(7, 7 + payloadlength);
		
		System.out.println(version);
		System.out.println(group);
		System.out.println(type);
		System.out.println(payloadlength);
		
		switch (group) {
		case 0:
			switch(type) {
			    case 1: 
				    return new SignOnRespondMessage(version, group, type, payloadlength, messageCon);
				
			}
		case 1:
			switch(type) {
			case 5:
				return new BRMessage(version, group, type, payloadlength, messageCon);
			case 9:
				return new SRMessage(version, group, type, payloadlength, messageCon);
			}
		}
		return null;
	}

}
