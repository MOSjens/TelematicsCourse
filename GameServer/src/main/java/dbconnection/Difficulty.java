package dbconnection;


/** Enum for the difficulties of questions
 * @author IG4
 *
 */
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
