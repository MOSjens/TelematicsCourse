package dbconnection;

import com.google.gson.annotations.SerializedName;

/** class for one question in the json result from the database
 * @author IG4
 *
 */
public class Result {
	@SerializedName("category") private String category;
	@SerializedName("type") private String type;
	@SerializedName("difficulty") private String difficulty;
	@SerializedName("question") private String question;
	@SerializedName("correct_answer") private String correctAnswer;
	@SerializedName("incorrect_answers") private String[] incorrectAnswers;
	
	public Result(String category, String type, String difficulty, String question, String correctAnswer,
			String[] incorrectAnswers) {
		super();
		this.category = category;
		this.type = type;
		this.difficulty = difficulty;
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.incorrectAnswers = incorrectAnswers;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String[] getIncorrectAnswers() {
		return incorrectAnswers;
	}

	public void setIncorrectAnswers(String[] incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
}
