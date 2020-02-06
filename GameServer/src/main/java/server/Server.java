package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
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
	private static Configuration config;


	public static void main(String[] args) {
		GamePhase gamePhase = GamePhase.STARTUP_PHASE;
		inputMessages = new LinkedBlockingQueue<IncomingMessage>();

		config = new Configuration();
		serverState = new ServerState( config.amountRounds );

		stillConnectClients = new AtomicBoolean(true);

		// Start Server Socket.
		try {
			final DatagramSocket socket = new DatagramSocket();
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			//serverSocket.getInetAddress().getHostAddress() //doesnt give a correct ip address
			serverSocket = new ServerSocket(port);
			System.out.println( "Server started on IP: " + socket.getLocalAddress().getHostAddress()
					+ " and Port: "	+ serverSocket.getLocalPort()  );
			socket.close();
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
				if (incomingMessage != null) {
					gamePhase = gamePhase.nextPhase( incomingMessage );
				}
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

	public static Configuration getConfiguration () {
		return config;
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