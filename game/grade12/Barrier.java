/*
 * Barrier.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 22, 2024
 * Methods to check if Judy is in-bounds or under a "door"
 */
package grade12;

import java.awt.Rectangle;

public class Barrier {
	//create Rectangle chunks (Judy cannot cross)
	private static Rectangle bar = new Rectangle(145,218,132,80);
	private static Rectangle leftWall = new Rectangle(0,317,180,400); //large chunk in bar + hallway
	private static Rectangle smallChunk1 = new Rectangle(181,384,17,65); //small chunk left of the biggest door
	private static Rectangle smallChunk2 = new Rectangle(261,384,96,65); //small chunk right of the biggest door
	private static Rectangle smallChunk3 = new Rectangle(295,341,62,44); //small chunk above smallChunk2
	private static Rectangle largeChunk1 = new Rectangle(358,341,300,165);; //large chunk covering Po + walls
	private static Rectangle smallChunk4 = new Rectangle(358,507,55,108); //small chunk below largeChunk1
	private static Rectangle smallChunk5 = new Rectangle(440, 507, 130, 73); //small chunk below largeChunk1
	private static Rectangle largeChunk2 = new Rectangle(311,111,88,198); //large chunk right of the bar
	private static Rectangle chair = new Rectangle(465, 248, 20, 42);
	private static Rectangle tableChunk1 = new Rectangle(486, 240, 122, 200); //south table
	private static Rectangle tableChunk2 = new Rectangle(466, 100, 135, 120); //north table
	private static Rectangle smallChunk6 = new Rectangle(609, 320, 35, 50); //small chunk above largeChunk1
	private static Rectangle smallChunk7 = new Rectangle(358, 650, 40, 70); //small chunk below smallChunk4
	private static Rectangle kitchenTable = new Rectangle(458, 612, 200, 100);
	
	//array to check barriers
	private static Rectangle[] barriers = {bar, leftWall, smallChunk1, smallChunk2, smallChunk3, largeChunk1,
			smallChunk4, smallChunk5, largeChunk2, chair, tableChunk1, tableChunk2, smallChunk6, smallChunk7, kitchenTable};
	
	//create Rectangle doors/covers (Judy is hidden)
	private static Rectangle bush = new Rectangle(316,310,13,29); //bush between largeChunk2 and smallChunk3
	private static Rectangle door1 = new Rectangle(370,310,13,29); //small door next to the bush
	private static Rectangle betweenTables = new Rectangle(486, 201, 102, 119); //behind the chairs
	private static Rectangle door2 = new Rectangle(199, 379, 61, 69); //biggest door
	private static Rectangle door3 = new Rectangle(355, 616, 45, 33); //kitchen door
	
	//array to check covers
	private static Rectangle[] covers = {bush, door1, betweenTables, door2, door3};
	
	/**
	 * Prevents Judy from entering certain chunks on the map
	 * @param playerX	int updated x coordinate of Judy
	 * @param playerY	int updated y coordinate of Judy
	 * @param ogX		int original x coordinate of Judy
	 * @param ogY		int original y coordinate of Judy
	 */
	public static void checkWalls(int playerX, int playerY, int ogX, int ogY){
		/*
		 * CHECK AGAINST FURTHEST GAME WALLS
		 */
		if (playerX<67) MainGameCode.setPlayerX(67); //check against barrier, farthest left
		if (playerY<110) MainGameCode.setPlayerY(110); //farthest up
		if (playerX+52>640) MainGameCode.setPlayerX(640-52); //farthest right
		if (playerY+75>680) MainGameCode.setPlayerY(680-75); //farthest down
		
		/*
		 * CHECK AGAINST CHUNKS (furniture + walls)
		 */
		for (int i=0; i<barriers.length; i++) {
			if (barriers[i].contains(playerX+28, playerY+75) || barriers[i].contains(playerX+52, playerY+75)) {
				MainGameCode.setPlayerX(ogX);
				MainGameCode.setPlayerY(ogY);
			}
		}
	}//end checkWalls(int, int)
	
	/**
	 * Hides Judy when she is in certain areas of the map
	 * @param playerX	int updated x coordinate of Judy
	 * @param playerY	int updated y coordinate of Judy
	 * @return			returns a boolean for if Judy should be drawn
	 */
	public static boolean judyIsVisible(int playerX, int playerY ) {
		boolean isVisible = true;
		for (int i=0; i<covers.length; i++) {
			if (covers[i].contains(playerX+28, playerY+75) || covers[i].contains(playerX+52,playerY+75)
					|| covers[i].contains(playerX+45,playerY+75) || covers[i].contains(playerX+35,playerY+75)) {
				isVisible=false;
			}
		}
		return isVisible;
	}

}
