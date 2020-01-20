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
	private byte[] header = new byte[7];
	private int length;
	private byte[] payloadLength;

	public SendParser() {
		
	}
	
	public byte[] parse(Message sendMessage) {
		header [0]= (byte) sendMessage.getVersion();
		header [1]= (byte) sendMessage.getGroup();
		header [2] = (byte) sendMessage.getType();
		length = 0;
		
		payloadLength = intToByteArray(length);
		for(int i = 0; i < 4; i++) {
			header[i] = payloadLength[i];
		}
		return header;
		
	}
	
	
	
	public static byte[] intToByteArray(int i) {
	    final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
	    bb.putInt(i);
	    return bb.array();
	}
}
