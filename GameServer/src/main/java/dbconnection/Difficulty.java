package dbconnection;

public enum Difficulty {
	
	EASY("Easy"), MEDIUM("Medium"), HARD("Hard");
	
	Difficulty( String string) {
		difficultyString = string;
	}
	
	private String difficultyString;
	
	@Override
	public String toString() {
		return difficultyString;
	}
	


}
