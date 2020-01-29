package server;

import static org.junit.Assert.*;

import org.junit.Test;

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
}
