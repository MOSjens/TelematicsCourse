package dbconnection;

public enum Difficulty {
	
	EASY("easy"), MEDIUM("medium"), HARD("hard");
	
	Difficulty( String string) {
		difficultyString = string;
	}
	
	private String difficultyString;
	
	@Override
	public String toString() {
		return difficultyString;
	}
	


}
