package dbconnection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import messages.QuestionMessage;
import parser.SendParser;

public class TestQuestionDatabase {

	@Test
	public void testGetQuestion1() {
		QuestionDatabase qdb = new QuestionDatabase();
		qdb.getQuestion(1, Category.ANIMALS, Difficulty.EASY);
	}
	
	//@Test
	public void testGetQuestion2() {
		QuestionDatabase qdb = new QuestionDatabase();
		qdb.getQuestion(2, Category.ENTERTAINMENT_CARTOON, Difficulty.EASY);
	}
	
	@Test
	public void testGetRandomQuestion() {
		QuestionDatabase qdb = new QuestionDatabase();
		ArrayList<Question> questions = qdb.getRandomQuestionsOffline();
		assertEquals(50,questions.size());			
		SendParser sendParser = new SendParser();
		byte[] dataTest;
		QuestionMessage QuestionMessage;
		Question question = questions.get(0);
		assertEquals("What is the name of \"Team Fortress 2\" update, in which it became Free-to-play?", question.getQuestionText());
		QuestionMessage = new QuestionMessage(0,question);
		dataTest = sendParser.messageToByteArray(QuestionMessage);
		assertEquals(216, dataTest.length);
		//System.out.println(Arrays.toString(dataTest));
		//System.out.print(dataTest.length);
		
	}

	

}
