package dbconnection;

import java.util.ArrayList;

/** one question in the game
 * @author IG4
 *
 */
public class Question {
	
	private Difficulty difficulty;
	private Category category;
	private int correctAnswerIndex;
	private String questionText;
	private ArrayList<String> answerOptions = new ArrayList<String>();

	public Question() {
		
	}

	public Question(Difficulty difficulty, Category category, String questionText, ArrayList<String> answerOptions) {
		this.setDifficulty(difficulty);
		this.setCategory(category);
		this.setQuestionText(questionText);
		this.setCorrectAnswerIndex(-1);
		this.setAnswerOptions(answerOptions);
	}
	
	public Question(Difficulty difficulty, Category category, String questionText, int correctAnswer, ArrayList<String> answerOptions) {
		this.setDifficulty(difficulty);
		this.setCategory(category);
		this.setQuestionText(questionText);
		this.setCorrectAnswerIndex(correctAnswer);
		this.setAnswerOptions(answerOptions);
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public int getCorrectAnswerIndex() {
		return correctAnswerIndex;
	}


	public void setCorrectAnswerIndex(int correctAnswerIndex) {
		this.correctAnswerIndex = correctAnswerIndex;
	}


	public String getQuestionText() {
		return questionText;
	}


	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}


	public ArrayList<String> getAnswerOptions() {
		return answerOptions;
	}


	public void setAnswerOptions(ArrayList<String> answerOptions) {
		this.answerOptions = answerOptions;
	}

}
