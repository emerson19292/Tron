package com.emersonswansondobbs.Main;

public class LightCycle {

	public int upKey, downKey, leftKey, rightKey;
	public int x, y, direction;
	public String color;
	public boolean alive = false;
	
	public LightCycle(int upKey, int downKey, int leftKey, int rightKey, int startX, int startY, int startDir, String color) {
		
		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		
		this.x = startX;
		this.y = startY;
		this.direction = startDir;
		
		this.color = color;
		
	}
	
	public void move() {
		
		Main.cellArray[y][x].changeColor(color);
		
		if (direction == Main.UP) {
			y--;
		} else if (direction == Main.DOWN) {
			y++;
		} else if (direction == Main.LEFT) {
			x--;
		} else if (direction == Main.RIGHT) {
			x++;
		} else {
			System.out.println("Didn't move");
		}
		
		if (Main.cellArray[y][x].color != null || x == 199 || x == 0 || y == 139 || y == 0) {
			
			Main.playerKilled(color);
			
		}
		
		//System.out.println("x = " + x + ", y = " + y);
		
	}
	
}
