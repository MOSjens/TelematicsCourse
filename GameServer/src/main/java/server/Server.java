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
		boolean startPhase = true;

		Configuration config = new Configuration();
		serverState = new ServerState( config.amountRounds );

		// TODO implement game phases
		try {
			serverSocket = new ServerSocket(port);
			System.out.println( "Server started on IP: " + serverSocket.getInetAddress().getHostAddress()
					+ " and Port: "	+ serverSocket.getLocalPort()  );
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
			System.exit(-1);
		}

		// Start Phase: Here the clients connect to the server.
		while (startPhase) {
			//TODO Start Thread for every client until all clients are ready for 30 seconds.
			try {
				Client client = new Client( serverSocket.accept(), serverState );
				client.addMessageListener(new HandleMessageLister());
				client.start();
				serverState.addPlayer( client );

			} catch (IOException e) {
				e.printStackTrace();
			}
			// TODO break the loop
			// TODO finish
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
		SignOnMessage signOn = (SignOnMessage) e.getMessage();
		System.out.println("Recieved: "+e.getMessage().getMessageType().name()+" Alias = "+ signOn.getPlayerAlias());
	}
}