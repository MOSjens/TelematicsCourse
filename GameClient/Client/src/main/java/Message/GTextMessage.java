package Message;

import java.nio.charset.StandardCharsets;

import java.nio.ByteBuffer;

import MessageType.GeneralMessageType;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.Message;


public class GTextMessage extends Message {
	private String Text;

	public GTextMessage( byte[] messageBody) {
		super( GeneralMessageType.GENERAL_TEXT,messageBody);
		
		ByteBuffer buffer= ByteBuffer.wrap(messageBody);
		SetTextMessage(StandardCharsets.UTF_8.decode(buffer).toString());
	}

	
	 public void SetTextMessage(String text) {
	        this.Text = text;
	    }
	
	
	 @Override
	    public String toString() {
	        return Integer.toString(getVersion()) + getGroup().name() + getType() + Integer.toString(getPayloadLength())
	                 + Text;
	    }

}
