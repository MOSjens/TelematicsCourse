package server;

import java.io.IOException;
import java.net.ServerSocket;

import client.Client;
import client.MessageEvent;
import client.MessageListener;
import messages.SignOnMessage;

public class Server {

	private static ServerSocket serverSocket = null;
	private static int port = 6666;
	private static ServerState serverState;

	public static void main(String[] args) {
		int playerID = 0;
		GamePhase gamePhase = GamePhase.STARTUP_PHASE;

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

		// Start Phase: Here the clients connect to the server.
		while (true) {
			//TODO Start Thread for every client until all clients are ready for 30 seconds.
			if ( gamePhase == GamePhase.STARTUP_PHASE ) {
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
}

class HandleMessageLister implements MessageListener {
	@Override
	public void handleMessage(MessageEvent e) {
		
		//TODO Dummy Code here
		SignOnMessage signOn = (SignOnMessage) e.getMessage();
		System.out.println("Recieved: "+e.getMessage().getMessageType().name()+" Alias = "+ signOn.getPlayerAlias());
		Client source = (Client) e.getSource();
		source.setAlias(signOn.getPlayerAlias());
		System.out.println(source.getAlias());
	}
}