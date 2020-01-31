package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import client.Client;
import client.MessageEvent;
import client.MessageListener;
import messages.Message;
import messages.SignOnMessage;

public class Server {

	private static ServerSocket serverSocket = null;
	private static int port = 6666;
	private static ServerState serverState;
	private static BlockingQueue<Message> inputMessages;


	public static void main(String[] args) {
		GamePhase gamePhase = GamePhase.STARTUP_PHASE;
		inputMessages = new LinkedBlockingQueue<Message>();

		Configuration config = new Configuration();
		serverState = new ServerState( config.amountRounds );

		// Start Server Socket.
		try {
			serverSocket = new ServerSocket(port);
			System.out.println( "Server started on IP: " + serverSocket.getInetAddress().getHostAddress()
					+ " and Port: "	+ serverSocket.getLocalPort()  );
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
			System.exit(-1);
		}

		// Connect clients in the background.
		Thread connectClients = new Thread(){
			@Override
			public void run() {
				int playerID = 0;
				while (true) {
					try {
						Client client = new Client( serverSocket.accept(), serverState, playerID );
						client.addMessageListener(new HandleMessageLister());
						client.start();
						serverState.addPlayer( client );
					} catch (IOException e) {
						e.printStackTrace();
					}
					playerID++;
				}
			}
		};

		//TODO stop after 30 seconds.
		connectClients.start();


		while (true) {
			if ( gamePhase == GamePhase.STARTUP_PHASE ) {

			}

			// Or shift all logic to Enum and Handle every Phase equal, by waiting on the Message, save it in the
			// serverState and execute GamePhase.nextPhase?
			// Alternative leave it as below and only execute nextPhase when it was the correct Message?

			if (gamePhase == GamePhase.GAME_PHASE) {
				// GamePhase
			}

			if (gamePhase == GamePhase.CATEGORY_SELECTION_PHASE) {
				// Category selection
			}

			if (gamePhase == GamePhase.PLAYER_SELECTION_PHASE) {
				// Player Selection
			}

			if (gamePhase == GamePhase.QUESTION_PLAY_PHASE) {
				// Question Play Phase
			}

			if ( gamePhase == GamePhase.CLOSING_PHASE ) {
				break;
			}
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startNewClient() {

	}

	public static BlockingQueue<Message> getInputMessages() {
		return inputMessages;
	}

	public static ServerState getServerState() {
		return serverState;
	}

}

class HandleMessageLister implements MessageListener {
	@Override
	public void handleMessage(MessageEvent e) {
		try {
			Server.getInputMessages().put (e.getMessage());
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		SignOnMessage signOn = (SignOnMessage) e.getMessage();
		System.out.println("Recieved: "+e.getMessage().getMessageType().name()+" Alias = "+ signOn.getPlayerAlias());
		Client source = (Client) e.getSource();
		source.setAlias(signOn.getPlayerAlias());
		System.out.println(source.getAlias());
	}
}