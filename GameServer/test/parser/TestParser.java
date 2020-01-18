package parser;
import messages.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestParser {

	@Test
	public void testParse() {
		Parser newParser = new Parser();
		byte[] data = new byte[] { (byte)0x1, (byte) 0x1, (byte)0x2,
			    0x0, (byte)0x0, 0x0, 0x46, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
			    0x30, 0x30, (byte)0x9d };
		/* \x1\x1\x2\x0\x0\x0\x46\x0\x0\x0\x24\x0\x0\x0\x2d\x0\x0\x0\x32\x0\x0\x0\x3
		\x0\x0\x0\x0\x0\x0\x27\x10\x0\x0\x0\x3f\x0\x0\x0\x40\x0\x0\x0\x44\x56\x65
		\x72\x79\x20\x45\x61\x73\x79\x4d\x61\x74\x68\x73\x57\x68\x61
		\x74\x27\x73\x20\x31\x20\x2b\x20\x31\x3f\x32\x66\x6f\x75\x72\x34\x32 */ 
		Message newMessage = newParser.parse(data);
		System.out.println("Version: "+ newMessage.getVersion()
							+" Group: "+ newMessage.getGroup()
							+" Type: "+ newMessage.getType()
							+" Length: "+ newMessage.getLength());
		//fail("Not yet implemented");
	}

}
