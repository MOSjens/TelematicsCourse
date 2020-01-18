package client;
import java.net.*;

import Message.BRMessage;
import Message.SignOnRespondMessage;
import Util.Phaser;

import java.io.*;

public class Client {

   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      while(true) {
	      try {
	         System.out.println("Connecting to " + serverName + " on port " + port);
	         Socket client = new Socket(serverName, port);
	         
	         System.out.println("Just connected to " + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         
	         out.writeUTF("Hello from " + client.getLocalSocketAddress());
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         
	         System.out.println("Server says " + in.readUTF());
	         String message = in.readUTF();
	         System.out.println(message);
	         System.out.println(Phaser.phaserToMessage(message).toString());
	         
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
      }
   }
}