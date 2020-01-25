package dbconnection;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQuestionDatabase {

	@Test
	public void testConnect() {
		QuestionDatabase qdb = new QuestionDatabase();
		qdb.getQuestion(2, Category.ANIMALS, Difficulty.EASY);
	}

}
