/*
 * Hint.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 16, 2023
 * Base class for game Hints - clues, ghosts, and Riddles
 */
package grade12;

public class Hint {
	private String message;
	private String[] alpha = {"d", "c", "b", "a"};
	
	/**
	 * Class constructor
	 * @param message	String helpful message for player
	 */
	public Hint(String message) {
		this.message = message;
	}
	
	/**
	 * Getter method for the message
	 * @return	the message for the Hint
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Setter method for the message
	 * @param message	String helpful message for the player
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Getter method for alpha choices
	 * @return	String array with alpha
	 */
	public String[] getAlpha() {
		return this.alpha;
	}
	
}

class Riddle extends Hint {
	private String prompt;
	private String[] answers = new String[4];
	boolean solved = false;
	
	/**
	 * Class constructor
	 * @param prompt	String riddle prompt for user to solve
	 * @param ansA		String for first option of answers
	 * @param ansB		String for second option of answers
	 * @param ansC		String for third option of answers
	 * @param ansD		String for fourth option of answers
	 */
	public Riddle(String prompt, String message, String ansA, String ansB, String ansC, String ansD) {
		super(message);
		this.prompt = prompt;
		answers[0] = ansA;
		answers[1] = ansB;
		answers[2] = ansC;
		answers[3] = ansD;
	}
	
	/**
	 * Getter method for the prompt
	 * @return	the String prompt for the Riddle
	 */
	public String getPrompt() {
		return this.prompt;
	}
	
	/**
	 * Setter method for the prompt
	 * @param prompt	the String prompt for the Riddle
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	/**
	 * Getter method for the answers
	 * @return	the String[] for the answers
	 */
	public String[] getAnswers() {
		return this.answers;
	}
	
	/**
	 * Setter method for the prompt
	 * @param ansA		String for first option of answers
	 * @param ansB		String for second option of answers
	 * @param ansC		String for third option of answers
	 * @param ansD		String for fourth option of answers
	 */
	public void setAnswers(String ansA, String ansB, String ansC, String ansD) {
		answers[0] = ansA;
		answers[1] = ansB;
		answers[2] = ansC;
		answers[3] = ansD;
	}
	
	/**
	 * Sets the Riddle (solved boolean) as solved (true)
	 */
	public void setSolved() {
		solved=true;
	}
	
	/**
	 * Sets the Riddle (solved boolean) as unsolved or hidden (false)
	 */
	public void setHidden() {
		solved=false;
	}
	
	/**
	 * Checks if the Riddle has been solved (solved boolean is true)
	 */
	public boolean checkWin() {
		if (this.solved==true) return true;
		else return false;
	}
	
}
