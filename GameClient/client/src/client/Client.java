package client;
import java.net.*;
import java.util.Scanner;

import Message.BRMessage;
import Message.Message;
import Message.ScoreBoardMessage;
import Message.SignOnMessage;
import Message.SignOnRespondMessage;

import java.io.*;
import java.io.ObjectInputStream.GetField;

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
	         InputStream inFromServer = client.getInputStream();
	          Message signOn = new SignOnMessage("Ghiath");
	          System.out.println(signOn.getMessageBody());
	          System.out.println(signOn.getType());
	       // System.out.println(signOn.getEncodedMessage());
	        
			
	         
	         System.out.println("Enter your Alias for this game");
	         Scanner myObj = new Scanner(System.in);
	         String playerName= myObj.nextLine();
	         Message signon =new SignOnMessage(playerName);
	         System.out.println(signOn.getMessageBody().toString());
	         //string playerName=system.in.readln();
	         // Message signOn = new SignOnMessage(playerName);
	         outToServer.write(signon.getEncodedMessage());
	         while (true) {
	        	 //genausso sollte bei den parser sein also getrente header und body behandeln und den body can man an den constroctor geben.
	        	 byte[] header = inFromServer.readNBytes(7);
	        	 int payloadLength = 0;
	        	 byte[] body = inFromServer.readNBytes(payloadLength);
	        	 ScoreBoardMessage m = new ScoreBoardMessage(body);
	         }
	         
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
      }
   }
}