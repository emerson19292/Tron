package com.emersonswansondobbs.Main;

import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class Cell extends Rectangle2D.Float {

	public int gridX, gridY;
	public String color = null;
	
	public Cell(int gridX, int gridY) {
		
		width = 5;
		height = 5;
		
		this.gridX = gridX;
		this.gridY = gridY;
		
		x = gridX * 5;
		y = gridY * 5;
		
	}
	
	public void changeColor(String color) {
		
		this.color = color;
		
	}

}
