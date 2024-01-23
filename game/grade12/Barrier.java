/*
 * Barrier.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 22, 2024
 * Methods to check if Judy is in-bounds or under a "door"
 */
package grade12;

import java.awt.*;

public class Barrier {
	/**
	 * COMMENT LATER
	 * @param playerX
	 * @param playerY
	 */
	public static void checkWalls(int playerX, int playerY){
		if (playerX<67) MainGameCode.setPlayerX(67); //check against barrier, farthest left
		if (playerY<110) MainGameCode.setPlayerY(110); //farthest up
		if (playerX+52>640) MainGameCode.setPlayerX(640-52); //farthest right
		if (playerY+75>700) MainGameCode.setPlayerY(700-75); //farthest down
	}//end checkWalls(int, int)

}
