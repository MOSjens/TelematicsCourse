package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.event.EventListenerList;

import messages.Message;
import messages.ReadyState;
import parser.RecieveParser;
import parser.SendParser;
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
    private BlockingQueue<Message> outgoingMessages;
    private RecieveParser recieveParser;
    private SendParser sendParser;
    private ServerState serverState;
    private int playerID;
    private int score;
    private int screwsLeft;
    private String alias;
    private ReadyState readyState;

    private EventListenerList messageListenerList;

    public Client(Socket socket, ServerState serverState, int playerID) {
        super("Client");
        this.socket = socket;
        this.outgoingMessages = new LinkedBlockingQueue<Message>();
        this.recieveParser = new RecieveParser();
        this.sendParser = new SendParser();
        this.serverState = serverState;
        this.readyState = ReadyState.NOT_READY;
        messageListenerList = new EventListenerList();
        this.score = 0; // Initial score is zero.
        this.screwsLeft = 1; //screws left for this player
        this.playerID = playerID;
        try {
            out = new DataOutputStream( socket.getOutputStream());
            in = new DataInputStream( socket.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
		Client source = this;
		Thread readData = new Thread() {
			@Override
			public void run() {
        while (true) {     	
            // Only messages with a size of 1024 byte can be handled.
            byte[] temp = new byte[1024];
            byte[] incomingData;
            int number = 0;
            try {
                // Receive Message
                if ((number = in.read( temp )) != -1 ) {
                    incomingData = Arrays.copyOf(temp, number);
                    // TODO delete
                    System.out.println("Bytes Read: " + number);

                    Message message = recieveParser.parse(incomingData);
                    notifyListeners( new MessageEvent(source, message ) );
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        // TODO disconnect handling
        // TODO client logic
		}
		};
		Thread writeData = new Thread() {
			@Override
			public void run() {
        while (true) {
            byte[] outgoingData;
            try {
                // Send Message
                Message outgoingMessage = outgoingMessages.poll();
                if ( outgoingMessage != null ) {
                	System.out.println("queue:");
                    outgoingData = sendParser.messageToByteArray(outgoingMessage);
                    out.write(outgoingData);
                    out.flush();
                    System.out.println( "Send Message: " + outgoingMessage.getMessageType().toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // TODO disconnect handling
        // TODO client logic
		}
		};
		readData.start();
		writeData.start();
    }

    public void sendMessage(Message message){
    	System.out.println("Added Message to queue: " + message.getMessageType());
        this.outgoingMessages.add(message);
    }

    public void addMessageListener ( MessageListener listener ) {
        messageListenerList.add( MessageListener.class, listener );
    }

    public void removeMessageListener( MessageListener listener ) {
        messageListenerList.remove( MessageListener.class, listener );
    }

    protected synchronized void notifyListeners( MessageEvent event ) {
        for ( MessageListener listener : messageListenerList.getListeners(MessageListener.class) ) {
            listener.handleMessage( event );
        }
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void changeScore(int points) {
    	this.score += points;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = serverState.solveAliasConflict(alias);
    }

    public ReadyState getReadyState() {
        return readyState;
    }

    public void setReadyState(ReadyState readyState) {
        this.readyState = readyState;
    }

	public int getScrewsLeft() {
		return screwsLeft;
	}

	public void setScrewsLeft(int screwsLeft) {
		this.screwsLeft = screwsLeft;
	}

	public void decreaseScrewsLeft() {
		this.screwsLeft--;
	}
}
