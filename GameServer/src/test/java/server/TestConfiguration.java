package server;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestConfiguration {

	@Test
	public void testGetPropValues() {
		Configuration config = new Configuration();
		System.out.println("rounds: "+ config.amountRounds
		+" qTimeout: "+ config.questionTimeout
		+" aTimeout: "+ config.answerTimeout
		+" cTimeout: "+ config.categoryTimeout);
	}

}
