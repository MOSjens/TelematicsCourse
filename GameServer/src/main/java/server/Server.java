package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import client.Client;
import client.MessageEvent;
import client.MessageListener;

public class Server {

	private static ServerSocket serverSocket = null;
	private static int port = 6666;
	private static ServerState serverState;
	private static BlockingQueue<IncomingMessage> inputMessages;
	private static AtomicBoolean stillConnectClients;
	private static int secondsTillStart;


	public static void main(String[] args) {
		GamePhase gamePhase = GamePhase.STARTUP_PHASE;
		inputMessages = new LinkedBlockingQueue<IncomingMessage>();

		Configuration config = new Configuration();
		serverState = new ServerState( config.amountRounds );

		stillConnectClients = new AtomicBoolean(true);
		secondsTillStart = config.gameStartTimeout;

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
				while ( stillConnectClients.get() ) {
					try {
						Client client = new Client( serverSocket.accept(), serverState, playerID );
						if (!stillConnectClients.get()) break; // Because the function above is blocking.
						client.addMessageListener(new HandleMessageLister());
						client.start();
						serverState.addPlayer( client );
						// reset Timeout if already started
						secondsTillStart = config.gameStartTimeout;
					} catch (IOException e) {
						e.printStackTrace();
					}
					playerID++;
					if (playerID >= config.maximumPlayers) break;
				}
			}
		};

		connectClients.start();





		while (true) {

			try {
				IncomingMessage incomingMessage = inputMessages.take();
				gamePhase = gamePhase.nextPhase( incomingMessage );
			} catch (InterruptedException e) {
				e.printStackTrace();
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


	public static BlockingQueue<IncomingMessage> getInputMessages() {
		return inputMessages;
	}

	public static ServerState getServerState() {
		return serverState;
	}

	public static void setStillConnectClients( Boolean connectClients ) {
		stillConnectClients.set( connectClients );
	}

	public static int getSecondsTillStart() {
		return secondsTillStart;
	}

	public static void decreaseSecondsTillStart() {
		secondsTillStart--;
	}

}

class HandleMessageLister implements MessageListener {
	@Override
	public void handleMessage(MessageEvent e) {
		try {
			IncomingMessage incomingMessage = new IncomingMessage(e.getMessage(), ( Client ) e.getSource() );
			Server.getInputMessages().put (incomingMessage);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}