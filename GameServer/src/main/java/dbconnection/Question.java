package dbconnection;

import java.util.ArrayList;

public class Question {
	
	private Difficulty difficulty;
	private Category category;
	private int correctAnswerIndex;
	private ArrayList answerOptions = new ArrayList();

	public Question() {
		// TODO Auto-generated constructor stub
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

}
