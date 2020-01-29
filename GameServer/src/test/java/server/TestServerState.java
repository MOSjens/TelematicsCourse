package server;

import static org.junit.Assert.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;

import org.junit.Test;

import client.Client;
import messages.MessageType;
import messages.ScoreboardMessage;

public class TestServerState {

	@Test
	public void testFillQuestionList() {
		ServerState serverState = new ServerState(10);
		serverState.fillQuestionList(5);
		assertEquals(5, serverState.getQuestionList().size());
		serverState.fillQuestionList(5);
		assertEquals(10, serverState.getQuestionList().size());
	}

	@Test
	public void testDecreaseRounds() {
		ServerState serverState = new ServerState(10);
		serverState.decreaseRoundsLeft();
		assertEquals(9, serverState.getRoundsLeft());
	}
	
	//@Test throws exceptions because no real sockets are used
	public void TestCreateScoreboard() {
	ServerState serverState = new ServerState(10);
	Socket serverSocket = new Socket();
	Client client1 = new Client(serverSocket, null, 0);
	serverState.addPlayer( client1 );
	Client client2 = new Client(serverSocket, null ,1);
	serverState.addPlayer( client2 );
	client1.setPlayerID(0);
	client1.setScore(10);
	client2.setPlayerID(1);
	client2.setScore(20);
	ScoreboardMessage scoreboard = serverState.createScoreboardMessage();
	assertEquals(MessageType.SCOREBOARD, scoreboard.getMessageType());
	assertEquals(10, scoreboard.getRoundLeft());
	LinkedHashMap<Integer, Integer> scoreMap = scoreboard.getMapPlayerIdToScore();
	int score0 = scoreMap.get(0);
	int score1 = scoreMap.get(1);
	assertEquals(10, score0);
	assertEquals(20, score1);
	
	}
}

