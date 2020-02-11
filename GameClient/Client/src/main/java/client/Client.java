package client;

import java.net.*;
import parser.ReceiveParser;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

import Message.BRMessage;
import Message.Message;
import Message.ScoreBoardMessage;
import Message.SignOnMessage;
import Message.SignOnRespondMessage;
import MessageType.MessageType;
import StateMachine.Answerer;
import StateMachine.CSA;
import StateMachine.Context;
import StateMachine.IState;
import StateMachine.Initial;
import StateMachine.NoneAnswerer;
import StateMachine.NoneSelector;
import StateMachine.ReBuzzScrew;
import StateMachine.ReQuestion;
import StateMachine.ReScoreBoard;
import StateMachine.ReSignOn;
import StateMachine.Ready;
import StateMachine.Selector;
import StateMachine.SignOn;
import StateMachine.StateEnum;
import parser.ReceiveParser;

import java.io.*;
import java.io.ObjectInputStream.GetField;

public class Client {
	private static Context context;

	public static void main(String[] args) {

		IState initial = new Initial(StateEnum.INITIAL);
		context = new Context(initial);
		String serverName = "192.168.43.87";
		int port = 6666;
		while (true) {
			try {
				System.out.println("Connecting to " + serverName + " on port " + port);
				Socket client = new Socket(serverName, port);

				System.out.println("Just connected to " + client.getRemoteSocketAddress());
				DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
				DataInputStream inFromServer = new DataInputStream(client.getInputStream());

				while (true) {

					if (inFromServer.available() != 0) {

						Message m = (Message) ReceiveParser.ParsMessage(inFromServer);
						if (m == null) {
							System.out.println("empty");
						}
						System.out.println("done with" + m.getType());
						context.setInputMessage(m);
						context.receiveMessage();
						System.out.println(context.getState().toString());
					}

					if (Initial.class == context.getState().getClass()) {

						System.out.println("Enter your Alias");
						Scanner myObj = new Scanner(System.in);
						String Userinput = myObj.nextLine();

						context.setPlayerAlias(Userinput);

						context.sendMessage();
						outToServer.write(context.getOutputMessage().getEncodedMessage());
						outToServer.flush();

					}

					if (context.getState().getClass() == ReSignOn.class) {

						Scanner myObj = new Scanner(System.in);
						String Userinput = myObj.nextLine();

						if (Userinput.contains("ready")) {

							context.sendMessage();
							outToServer.write(context.getOutputMessage().getEncodedMessage());
							outToServer.flush();

						}
					}
					if (context.getState().getClass() == CSA.class) {

						Scanner myObj = new Scanner(System.in);
						String Userinput = myObj.nextLine();

						context.setCategory(Userinput);

						context.sendMessage();
						outToServer.write(context.getOutputMessage().getEncodedMessage());
						outToServer.flush();
					}
					if (context.getState().getClass() == ReQuestion.class) {

						Scanner myObj = new Scanner(System.in);
						String Userinput = myObj.nextLine();

						if (Userinput.contains("buzz"))

						{
							context.sendMessage();
							outToServer.write(context.getOutputMessage().getEncodedMessage());
							outToServer.flush();
						} else if (Userinput.contains("screw")) {

							String[] idScrew = Userinput.split(" ");
							String Part2 = idScrew[1];
							int y = Integer.parseInt(Part2);
							context.setDecision(true);
							context.setScrewID(y);

							context.sendMessage();
							outToServer.write(context.getOutputMessage().getEncodedMessage());
							outToServer.flush();

						} else {
							context.sendMessage();
						}
					}

					if (context.getState().getClass() == ReBuzzScrew.class) {

						Scanner myObj = new Scanner(System.in);
						String Userinput = myObj.nextLine();

						if (!Userinput.isBlank()) {

							int y = Integer.parseInt(Userinput);
							context.setSelectedAnswerIndex(y);
							context.sendMessage();
							outToServer.write(context.getOutputMessage().getEncodedMessage());
							System.out.println("send success" + context.getState().toString());
							outToServer.flush();
						} else {
							context.sendMessage();
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}