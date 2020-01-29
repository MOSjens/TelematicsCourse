package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.event.EventListenerList;

import messages.Message;
import parser.RecieveParser;
import server.ServerState;

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
    private ServerState serverState;
    private String playerAlias;
    private int playerId;

    private EventListenerList messageListenerList;
    //private MessageListener messageListener;

    public Client(Socket socket, ServerState serverState) {
        super("Client");
        this.socket = socket;
        this.serverState = serverState;
        messageListenerList = new EventListenerList();
        try {
            out = new DataOutputStream( socket.getOutputStream());
            in = new DataInputStream( socket.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerAlias() {
		return playerAlias;
	}

	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}

	public void run() {

        while (true) {
            // Only messages with a size of 1024 byte can be handled.
            byte[] temp = new byte[1024];
            byte[] data;
            int number = 0;
            try {
                if ((number = in.read( temp )) != -1 ) {
                    data = Arrays.copyOf(temp, number);
                    System.out.println("Bytes Read: " + number);

                    RecieveParser recieveParser = new RecieveParser();
                    Message message = recieveParser.parse(data);
                    notifyListeners( new MessageEvent(this, message ) );

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // TODO disconnect handling
        // TODO client logic
        // TODO how does the client inform the server? listener?

    }

    public void addMessageListener ( MessageListener listener ) {
        messageListenerList.add( MessageListener.class, listener );
        System.out.println("Hello from the thread");
        //messageListener = listener;
    }

    public void removeMessageListener( MessageListener listener ) {
        messageListenerList.remove( MessageListener.class, listener );
    }

    protected synchronized void notifyListeners( MessageEvent event ) {
        for ( MessageListener listener : messageListenerList.getListeners(MessageListener.class) ) {
            listener.handleMessage( event );
        }
    }


}
