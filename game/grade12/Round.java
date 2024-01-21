/*
 * Round.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 16, 2023
 * Class for each round including hints, clues, riddles, etc.
 */
package grade12;

public class Round {
	private int points = -1;
	
	// parallel arrays for coordinates of clue, ghost, and riddle images
	private int[] clueX = {50};
	private int[] clueY = {50};
	private int[] ghostX = {100};
	private int[] ghostY = {100};
	private int[] riddleX = {150};
	private int[] riddleY = {150};
	
	// create object for round
	private Hint clue;
	private Hint ghost;
    private Riddle riddle;
    
    /**
     * class constructor
     * @param initialHint
     * @param initialRiddle
     */
    public Round(Hint roundClue, Hint roundGhost, Riddle roundRiddle) {
        this.clue = roundClue;
        this.ghost = roundGhost;
        this.riddle = roundRiddle;
    }
    
    /**
     * getter method for number of points collected through round
     * @return	number of points in current round
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * setter method for number of points collected through round
     * @param points	number of points in current round
     */
    public void setPoints(int numPoints) {
    	this.points = numPoints;
    }
    
    /**
     * getter method for current round's hint
     * @return	current round's hint
     */
    public Hint getClue() {
        return clue;
    }
    
    /**
     * setter method for current round's hint
     * @param Clue	current round's hint
     */
    public void setClue(Hint Clue) {
    	this.clue = Clue;
    }
    
    /**
     * getter method for current round's ghost clue
     * @return	current round's ghost clue
     */
    public Hint getGhost() {
        return ghost;
    }
    
    /**
     * setter method for current round's ghost clue
     * @param Ghost	current round's ghost clue
     */
    public void setGhost(Hint Ghost) {
    	this.ghost = Ghost;
    }

    /**
     * getter method for current round's riddle
     * @return	current round's riddle
     */
    public Riddle getRiddle() {
        return riddle;
    }
    
    /**
     * setter method for current round's riddle
     * @param Riddle	current round's riddle
     */
    public void setRiddle(Riddle Riddle) {
    	this.riddle = Riddle;
    }
    
    /**
     * getter method for clue x-coordinate
     * @param round		round number
     * @return	x-coordinate of appropriate clue
     */
    public int getClueX(int round) {
    	return clueX[round];
    }
    
    /**
     * getter method for clue y-coordinate
     * @param round		round number
     * @return	y-coordinate of appropriate clue
     */
    public int getClueY(int round) {
    	return clueY[round];
    }
    
    /**
     * getter method for ghost x-coordinate
     * @param round		round number
     * @return	x-coordinate of appropriate ghost
     */
    public int getGhostX(int round) {
    	return ghostX[round];
    }
    
    /**
     * getter method for ghost y-coordinate
     * @param round		round number
     * @return	y-coordinate of appropriate ghost
     */
    public int getGhostY(int round) {
    	return ghostY[round];
    }
    
    /**
     * getter method for riddle x-coordinate
     * @param round		round number
     * @return	x-coordinate of appropriate riddle
     */
    public int getRiddleX(int round) {
    	return riddleX[round];
    }
    
    /**
     * getter method for riddle y-coordinate
     * @param round		round number
     * @return	y-coordinate of appropriate riddle
     */
    public int getRiddleY(int round) {
    	return riddleY[round];
    }

}
