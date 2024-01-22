/*
 * Barrier.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 22, 2024
 * Methods to check if Judy is in-bounds or under a "door"
 */
package grade12;

import java.awt.*;

public class Barrier {
	public static void checkWalls(Rectangle player){
		if (player.x+28<95) MainGameCode.setJudyBoxX(90);
	}

}
