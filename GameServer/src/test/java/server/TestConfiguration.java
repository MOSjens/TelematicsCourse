package server;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestConfiguration {

	@Test
	public void testGetPropValues() {
		Configuration config = new Configuration();
		/*System.out.println("rounds: "+ config.amountRounds
		+" qTimeout: "+ config.questionTimeout
		+" aTimeout: "+ config.answerTimeout
		+" cTimeout: "+ config.categoryTimeout);*/
		assertEquals( 10, config.amountRounds );
		assertEquals( 60, config.questionTimeout );
		assertEquals( 15, config.categoryTimeout );
		assertEquals( 15, config.answerTimeout );
		assertEquals( 30, config.gameStartTimeout);
		assertEquals( 10, config.maximumPlayers );
	}

}
