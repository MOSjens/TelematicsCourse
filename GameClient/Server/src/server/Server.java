package server;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Server extends Thread {
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(0);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());

				//int messageLength = in.readAllBytes();
				byte[] playerMessage = new byte[1024];
				byte [] date ;
				int nummber=0;
				 if (( nummber= in.read(playerMessage))!=-1)
{
	date = Arrays.copyOf(playerMessage, nummber);
	System.out.println("sdansdad"+nummber);
	for (int i = 0; i < date.length; i++) {
    	
		System.out.println(date[i]);
	}




}

				//System.out.println("Recieving " + messageLength + " bytes");
            
				/*byte[] messagePayloadOnly = new byte[messageLength - 7];
				System.arraycopy(playerMessage, 7, messagePayloadOnly, 0, messagePayloadOnly.length);

				String name = new String(messagePayloadOnly);
*/
				//System.out.println(name + " connected");

				
				byte[] newNameBytes = {0x01,0x00,0x01,0x00,0x00,0x00,0x08,0x00,0x00,0x00,0x10,0x66,0x72,0x65,0x64};

				/*ByteBuffer buffer = ByteBuffer.allocate(7 + 4 + newNameBytes.length);
				buffer.order(ByteOrder.BIG_ENDIAN);
				buffer.put((byte) 1);
				buffer.put((byte) 0);
				buffer.put((byte) 1);
				buffer.putInt(4 + newNameBytes.length); // 4 for ID int, and name length
				buffer.putInt(15);*/
				ByteBuffer buffer = ByteBuffer.wrap(newNameBytes);

				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.write(buffer.array());
				out.flush();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				System.out.println("Socket timed out!");
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		int port = 8000;
		try {
			Thread t = new Server(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}