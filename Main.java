package com.emersonswansondobbs.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	public static final int boardWidth = 1000;
	public static final int boardHeight = 700;
	
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	
	//W = 200, H = 140
	public static Cell[][] cellArray = new Cell[boardHeight / 5][boardWidth / 5];
	
	public static boolean gameInProgress = false;
	public static boolean firstGameFinished;
	public static String lastGameLoser;
	
	public static Image blueWin, orangeWin, start, youBothSuck;
	
	public static Color background = new Color(20, 20, 20);
	
	public static Color backgroundBlack = new Color(20, 20, 20);
	public static Color backgroundWhite = new Color(205, 205, 205);
	
	//public LightCycle(int upKey, int downKey, int leftKey, int rightKey, int startX, int startY, int startDir, String color)
	public static LightCycle blueCycle = new LightCycle(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, 30, 70, RIGHT, "blue");
	public static LightCycle orangeCycle = new LightCycle(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 270, 70, LEFT, "orange");
	
	public static void main(String[] args) {
		
		new Main();

	}
	
	public Main() {
		
		this.setSize(boardWidth, 722);
		this.setTitle("Main bike game");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		for (int y = 0; y < cellArray.length; y++) {
			
			for (int x = 0; x < cellArray[y].length; x++) {
				
				cellArray[y][x] = new Cell(x,y);
				
			}
			
		}
		
		for (int i = 0; i < cellArray.length; i++) cellArray[i][0].color = "white"; //left
		for (int i = 0; i < cellArray.length; i++) cellArray[i][199].color = "white"; //right
		for (int i = 0; i < cellArray[0].length; i++) cellArray[0][i].color = "white"; //top
		for (int i = 0; i < cellArray[0].length; i++) cellArray[139][i].color = "white"; //bottom 
		
		GraphicsPanel game = new GraphicsPanel();
		
		try {
			
			blueWin = ImageIO.read(new File("./images/blue wins.png"));
			orangeWin = ImageIO.read(new File("images/orange wins.png"));
			start = ImageIO.read(new File("images/start.png"));
			youBothSuck = ImageIO.read(new File("images/you both suck.png"));
			
		} catch (IOException ex) {
			System.out.println("Error with file IO");
		}
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0, 20, TimeUnit.MILLISECONDS);
		
		InputMap im = game.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		im.put(KeyStroke.getKeyStroke(orangeCycle.upKey, 0, false), "orange up");
		im.put(KeyStroke.getKeyStroke(orangeCycle.rightKey, 0, false), "orange right");
		im.put(KeyStroke.getKeyStroke(orangeCycle.downKey, 0, false), "orange down");
		im.put(KeyStroke.getKeyStroke(orangeCycle.leftKey, 0, false), "orange left");
		
		im.put(KeyStroke.getKeyStroke(blueCycle.upKey, 0, false), "blue up");
		im.put(KeyStroke.getKeyStroke(blueCycle.rightKey, 0, false), "blue right");
		im.put(KeyStroke.getKeyStroke(blueCycle.downKey, 0, false), "blue down");
		im.put(KeyStroke.getKeyStroke(blueCycle.leftKey, 0, false), "blue left");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "start");
		
		ActionMap am = game.getActionMap();
		am.put("orange up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (orangeCycle.direction != DOWN) orangeCycle.direction = UP;
			}
		});
		am.put("orange right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (orangeCycle.direction != LEFT) orangeCycle.direction = RIGHT;
			}
		});
		am.put("orange down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (orangeCycle.direction != UP) orangeCycle.direction = DOWN;
			}
		});
		am.put("orange left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (orangeCycle.direction != RIGHT) orangeCycle.direction = LEFT;
			}
		});
		
		
		am.put("blue up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (blueCycle.direction != DOWN) blueCycle.direction = UP;
			}
		});
		am.put("blue right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (blueCycle.direction != LEFT) blueCycle.direction = RIGHT;
			}
		});
		am.put("blue down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (blueCycle.direction != UP) blueCycle.direction = DOWN;
			}
		});
		am.put("blue left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (blueCycle.direction != RIGHT) blueCycle.direction = LEFT;
			}
		});
		
		am.put("start", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!gameInProgress) {
					
					Main.blueCycle.x = 30;
					Main.blueCycle.y = 70;
					Main.blueCycle.direction = RIGHT;
					Main.blueCycle.alive = true;
					
					Main.orangeCycle.x = 170;
					Main.orangeCycle.y = 70;
					Main.orangeCycle.direction = LEFT;
					Main.orangeCycle.alive = true;
					
					for (int y = 0; y < cellArray.length; y++) {
						
						for (int x = 0; x < cellArray[y].length; x++) {
							
							cellArray[y][x].color = null;
							
						}
						
					}
					
					for (int i = 0; i < cellArray.length; i++) cellArray[i][0].color = "white"; //left
					for (int i = 0; i < cellArray.length; i++) cellArray[i][199].color = "white"; //right
					for (int i = 0; i < cellArray[0].length; i++) cellArray[0][i].color = "white"; //top
					for (int i = 0; i < cellArray[0].length; i++) cellArray[139][i].color = "white"; //bottom
					
					
				}
				
				gameInProgress = true;
				
			}
			
		});
		
		this.add(game);
		this.setVisible(true);
		
	}
	
	public static void playerKilled(String color) {
		
		gameInProgress = false;
		lastGameLoser = color;
		firstGameFinished = true;
		
	}

}

class RepaintTheBoard implements Runnable {

	Main theBoard;
	
	public void run() {
		
		if (Main.gameInProgress) {
			
			Main.blueCycle.move();
			Main.orangeCycle.move();
			theBoard.repaint();
			
		}
		
	}
	
	public RepaintTheBoard(Main theBoard) {
		
		this.theBoard = theBoard;
		
	}

}

@SuppressWarnings("serial")
class GraphicsPanel extends JComponent {
	
	public void paint(Graphics g) {
		
		Graphics2D gs = (Graphics2D)g;
	    
	    for (int y = 0; y < Main.cellArray.length; y++) {
			
			for (int x = 0; x < Main.cellArray[y].length; x++) {
				
				if (Main.cellArray[y][x].color == "orange") {
					gs.setColor(Color.ORANGE);
				} else if (Main.cellArray[y][x].color == "blue") {
					gs.setColor(Color.CYAN);
				}else if (Main.cellArray[y][x].color == "white") {
					gs.setColor(Color.WHITE);
				} else {
					gs.setColor(Main.background);
				}
				
				gs.fill(Main.cellArray[y][x]);
				
			}
			
		}
	    
	    if (!Main.gameInProgress) {
	    	
	    	gs.drawImage(Main.start, 176, 10, this);
	    	
	    	if (Main.firstGameFinished) {
	    		if (Main.lastGameLoser.equals("orange") && Main.blueCycle.alive) {
	    			gs.drawImage(Main.blueWin, 283, 300, this);
	    		} else if (Main.lastGameLoser.equals("blue") && Main.orangeCycle.alive) {
	    			gs.drawImage(Main.orangeWin, 229, 300, this);
	    		} else if (!Main.blueCycle.alive && !Main.orangeCycle.alive) {
	    			gs.drawImage(Main.youBothSuck, 300, 201, this);
	    		}
	    		
	    	}
	    	
	    }
	     	     
	}
	
}