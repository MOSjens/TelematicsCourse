package parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Message.BRMessage;
import Message.CSAnnounceMessage;
import Message.EndGameMessage;
import Message.GTextMessage;
import Message.Message;
import Message.MessageGroup;
import Message.ParseException;
import Message.SRMessage;
import Message.ScoreBoardMessage;
import Message.SignOnRespondMessage;
import Message.PlayerListingMessage;
import MessageType.GeneralMessageType;
import MessageType.InGameMessageType;
import MessageType.MessageType;
import MessageType.PostGameMessageType;
import MessageType.PregamMessageType;

import client.Parser;

public class ReceiveParser extends Parser {

    public ReceiveParser() {


    }

    public static Message ParsMessage(InputStream InStream) throws IOException, ParseException {
        byte[] Input = InStream.readNBytes(7);
        ByteBuffer buffer = ByteBuffer.wrap(Input);
        buffer.order(ByteOrder.BIG_ENDIAN);
        byte versionb = buffer.get();
        byte groupb = buffer.get();
        byte typeb = buffer.get();
        int Payloadlength = buffer.getInt();
        byte[] Messagebody = InStream.readNBytes(Payloadlength);
        MessageGroup group = MessageGroup.fromByte(groupb);
        MessageType type = group.getType(typeb);
        switch (group) {
            case PRE_GAME:
                switch ((PregamMessageType) type) {
                    case SIGN_ON_RESPONSE:
                        return new SignOnRespondMessage(Messagebody);
                    default:
                        break;
                }
                break;
            case IN_GAME:
                switch ((InGameMessageType) type) {
                
                    case SCOREBOARD:
                        return new ScoreBoardMessage(Messagebody);
                    case  BUZZ_RESULT:
                    	return new  BRMessage(Messagebody);
                    case SCREW_RESULT:
                    	return new SRMessage(Messagebody);
                    case CATEGORY_SELECTION_ANNOUNCEMENT:
                    	return new CSAnnounceMessage(Messagebody);
                    
                 
                    	
                    default:
                        break;
                }
                break;
            case POST_GAME:
            	switch((PostGameMessageType) type)
            	{
            	case GAME_END:
            		return new EndGameMessage();
            		default:
            			break;
            	}
        
             
            case GENERAL:
            	switch ((GeneralMessageType) type)
            	{ 
            	case GENERAL_TEXT:
            		return new GTextMessage(Messagebody);
            		case PLAYER_LIST:
            		    return new PlayerListingMessage(Messagebody);
            		default:
                break;
            	}
        }
        return null;


    }
}
