package parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Message.Message;
import Message.MessageGroup;
import Message.ParseException;
import Message.ScoreBoardMessage;
import Message.SignOnRespondMessage;
import MessageType.InGameMessageType;
import MessageType.MessageType;
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
                    default:
                        break;
                }
                break;
            case POST_GAME:
                break;
            case GENERAL:
                break;
        }
        return null;


    }
}
