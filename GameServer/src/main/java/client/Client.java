package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import messages.Message;
import messages.SignOn;
import parser.RecieveParser;

/**
 * Client with a connection to the server
 * 
 * @author IG4
 *
 */
public class Client extends Thread{
    private Socket socket = null;
    private DataOutputStream out;
    private DataInputStream in;

    public Client(Socket socket) {
        super("Client");
        this.socket = socket;
        try {
            out = new DataOutputStream( socket.getOutputStream());
            in = new DataInputStream( socket.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // Dummy client code:
        while (true) {
            byte[] temp = new byte[1000];
            int number = 0;
            try {
                number = in.read( temp );
            } catch (IOException e) {
                e.printStackTrace();
            }
            
           
            if (number > 0) {
            	System.out.println("Bytes Read: " + number);
            	byte[] data = Arrays.copyOf(temp, number); 
            	RecieveParser recieveParser = new RecieveParser();
            	Message message = recieveParser.parse(data);
            	SignOn signOn = (SignOn) message;
            	System.out.println("Recieved: "+message.getMessageType().name()+" Alias = "+ signOn.getPlayerAlias());
            	
            	System.out.println(Arrays.toString(data));
            }
        }
        // TODO disconnect handling
        // TODO client logic
        // TODO how does the client inform the server? listener?

    }

}
