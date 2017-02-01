package yoshiNightmare;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Yoshi {
	
	//Directions for Yoshi Images.
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;
    private final int GLOW_UP = 4;
    private final int GLOW_DOWN = 5;
    private final int GLOW_LEFT = 6;
    private final int GLOW_RIGHT = 7;
    private final int noDirection = 8; //Special direction to check if Yoshi's had a previously saved direction to move.
    
    private final int BLOCK = 1;
    private final int EMPTY_SPOTS = 0;
    
    private int lives;
    private int score = 0;
    
    private final static int NUMBER_OF_IMAGES = 8;
    
    private String ImageName[] = new String[NUMBER_OF_IMAGES];
    private ImageIcon Image[] = new ImageIcon[NUMBER_OF_IMAGES];
	private JLabel yoshiLabel;
	
	private Container gamePanel; 
	private int yoshiDirection = UP; //Initial Yoshi Direction
	private int glowDirection = GLOW_UP; //Initial Glow Direction
	
	private int xIndex = 0; //Yoshi's x position.
	private int yIndex = 0; // Yoshi's y position.
	private int lastSpecifiedDirection = noDirection; //Yoshi's last saved direction. Starts off as noDirection, which means there was no last specified direction.
	private boolean yoshiGlow = false; // To check if yoshi is glowing.
	private int scale; //Scale for images.
	private int offset = 50; //Offset for images.
	
	public Yoshi(Container panel, int maxLives, int scalar){
		scale = scalar;
		lives = maxLives;
		gamePanel = panel;
		initializeArray();
		yoshiLabel = new JLabel();
		gamePanel.add(yoshiLabel);
		scaleImages();
	}
	
	//Initialize Yoshi's images array.
	public void initializeArray(){
		ImageName[UP] = "yoshi_up.png";
		Image[UP] = new ImageIcon(ImageName[UP]);
		ImageName[DOWN] = "yoshi_down.png";
		Image[DOWN] = new ImageIcon(ImageName[DOWN]);
		ImageName[LEFT] = "yoshi_left.png";
		Image[LEFT] = new ImageIcon(ImageName[LEFT]);
		ImageName[RIGHT] = "yoshi_right.png";
		Image[RIGHT] = new ImageIcon(ImageName[RIGHT]);
		ImageName[GLOW_UP] = "yoshi_up_glow.png";
		Image[GLOW_UP] = new ImageIcon(ImageName[GLOW_UP]);
		ImageName[GLOW_DOWN] = "yoshi_down_glow.png";
		Image[GLOW_DOWN] = new ImageIcon(ImageName[GLOW_DOWN]);
		ImageName[GLOW_LEFT] = "yoshi_left_glow.png";
		Image[GLOW_LEFT] = new ImageIcon(ImageName[GLOW_LEFT]);
		ImageName[GLOW_RIGHT] = "yoshi_right_glow.png";
		Image[GLOW_RIGHT] = new ImageIcon(ImageName[GLOW_RIGHT]);		
	}
			
	//Scale Images to fit screen.
	public void scaleImages(){
		for(int i = 0; i < NUMBER_OF_IMAGES; i++){
				Image newImage = Image[i].getImage().getScaledInstance(scale, scale, Image.length);
				Image[i] = new ImageIcon(newImage);
		}
	}
	
	//Draw Yoshi in map.
	public void drawYoshi(){
		yoshiLabel.setIcon(Image[yoshiDirection]);
		yoshiLabel.setBounds(((xIndex)*scale + offset), ((yIndex) * scale + offset), scale,scale);
		yoshiLabel.setVisible(true);
	}
	
	//Draw Glowing Yoshi in Map.
	public void drawGlowingYoshi(){
		yoshiLabel.setIcon(Image[glowDirection]);
		yoshiLabel.setBounds(((xIndex)*scale + offset),((yIndex) * scale + offset),scale,scale);
		 yoshiLabel.setVisible(true);
	}
	
	//Change Yoshi's Direction.
	public void changeDirection(int newDirection){
		if(!yoshiGlow){ //If yoshi isn't glowing.
			yoshiDirection = newDirection;
		} else{ //If Yoshi is Glowing.
			yoshiDirection = newDirection;
			glowDirection = newDirection + GLOW_UP;
		}
	}
	
	//Change yoshi's glow.
	public void setYoshiGlow(){
		yoshiGlow = !yoshiGlow;
	}
	
	public boolean getYoshiGlow(){
		return yoshiGlow;
	}
	
	//Check if the array Component is a wall or empy spaces.
	public boolean isWallHit(int arrayComponent){
		if(arrayComponent == BLOCK){
			return true;
		} else if(arrayComponent == EMPTY_SPOTS){
			return true;
		} else {
			return false;
		}
	}
	
	//Decrement Yoshi's Lives.
	public void changeLives(){
		lives = lives - 1;
	}
	
	public int getLives(){
		return lives;
	}
	
	//Change Yoshi's Last direction specified by user.
	public void setLastSpecifiedDirection(int newDirection){
		lastSpecifiedDirection = newDirection;
	}
	
	public int getLastSpecifiedDirection(){
		return lastSpecifiedDirection;
	}
	
	//Change Yoshi's x and y index based on the direction he is moving in.
	public void moveYoshi(){
		switch(yoshiDirection){
		case 0:
			setYIndex(yIndex-1);
			break;
		case 1:
			setYIndex(yIndex+1);
			break;
		case 2:
			setXIndex(xIndex-1);
			break;
		case 3:
			setXIndex(xIndex+1);
			break;
		}
		if(yoshiGlow){
			drawGlowingYoshi();
		} else {
			drawYoshi();
		}
	}
	
	//Set X position of Yoshi
	public void setXIndex(int xPoint){
		xIndex = xPoint;
	}
	
	//Set Y position of Yoshi
	public void setYIndex(int yPoint){
		yIndex = yPoint;
	}
	
	public int getXIndex(){
		return xIndex;
	}
	
	public int getYIndex(){
		return yIndex;
	}
	
	public int getDirection(){
		return yoshiDirection;
	}
	
	//Add user's score.
	public void addScore(int newPoint){
		score = score + newPoint;
	}
	
	public int getScore(){
		return score;
	}
}
