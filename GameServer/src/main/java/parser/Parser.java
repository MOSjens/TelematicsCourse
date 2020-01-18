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
		recievedMessage.setLength(byteArrayToLeInt(length));
		
		
		return recievedMessage;
		
	}
	public static int byteArrayToLeInt(byte[] b) {
	    final ByteBuffer bb = ByteBuffer.wrap(b);
	    return bb.getInt();
	}

	public static byte[] intToByteArray(int i) {
	    final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
	    bb.putInt(i);
	    return bb.array();
	}
}
