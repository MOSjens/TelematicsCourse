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
		assertEquals( config.amountRounds, 10 );
		assertEquals( config.questionTimeout, 60 );
		assertEquals( config.categoryTimeout, 15 );
		assertEquals( config.answerTimeout, 15 );
	}

}
