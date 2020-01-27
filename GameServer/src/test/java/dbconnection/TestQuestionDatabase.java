package dbconnection;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQuestionDatabase {

	@Test
	public void testGetQuestion1() {
		QuestionDatabase qdb = new QuestionDatabase();
		qdb.getQuestion(1, Category.ANIMALS, Difficulty.EASY);
	}
	
	@Test
	public void testGetQuestion20() {
		QuestionDatabase qdb = new QuestionDatabase();
		qdb.getQuestion(20, Category.ENTERTAINMENT_CARTOON, Difficulty.EASY);
	}
	
	@Test
	public void testGetRandomQuestion() {
		QuestionDatabase qdb = new QuestionDatabase();
		qdb.getRandomQuestions(20);
	}

}
