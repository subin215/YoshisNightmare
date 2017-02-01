package yoshiNightmare;

import java.awt.Container;
import java.awt.Image;

import javax.swing.*;

public class Ghosts {
	
	//Index of image direction in image array.
	protected final static int UP = 0;
	protected final static int DOWN = 1;
	protected final static int LEFT = 2;
	protected final static int RIGHT = 3;
	protected final static int BLUE_UP = 4;
	protected final static int BLUE_DOWN = 5;
	protected final static int BLUE_LEFT = 6;
	protected final static int BLUE_RIGHT = 7;
	protected final static int NUMBER_OF_IMAGES = 8;
    
    //Index of elements
	protected final int EMPTY_SPOTS = 0;
	protected final int MUSHROOMS = 2;
	protected final int CORNER_SHROOMS = 9;
	protected final int BLOCKS = 1;
	protected final int STARS = 3;
    
	//Scale and offset for images.
	protected static int scale = 0;
	protected int offset = 50;
	
    protected String ghostsImageName[] = new String[NUMBER_OF_IMAGES];
    protected String ghostsName;
    protected ImageIcon ghostsImage[] = new ImageIcon[NUMBER_OF_IMAGES];
	protected JLabel ghostsLabel;
	
	protected boolean ghostsSpecialinitiated = false; //Set to true when ghosts special moves are initiated.
	private boolean ghostsGlow = false; //To check if ghosts are glowing.
	
	private Container gamePanel;
	public int ghostDirection = DOWN; //Initial starting direction for ghosts. 
	public int glowDirection = ghostDirection; //Initial starting direction for glowing ghosts. 
	
	public int xIndex = 0; // x position of ghosts in screen.
	public int yIndex = 0; // y position of ghosts in screen. 
	
	public Ghosts(Container panel){
		gamePanel = panel;
		ghostsLabel = new JLabel();
		ghostsLabel.setBounds(10, 10, 10, 10);
		ghostsLabel.setVisible(true);
		gamePanel.add(ghostsLabel);
	}
	
	//Scale images to fit resolution.
	protected void scaleImages(){
		for(int i = 0; i < NUMBER_OF_IMAGES; i++){
			Image newImage = ghostsImage[i].getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
			ghostsImage[i] = new ImageIcon(newImage);
		}
	}
	//Draw ghosts on panel. 
	public void drawGhost(){
		ghostsLabel.setIcon(ghostsImage[ghostDirection]);
		ghostsLabel.setBounds(getXIndex()*scale + offset, getYIndex()*scale+offset, scale,scale);
		ghostsLabel.setVisible(true);
	}
	
	//Draws glowing ghosts.
	public void drawGlowingGhosts(){
		ghostsLabel.setIcon(ghostsImage[glowDirection]);
		ghostsLabel.setBounds(getXIndex()*scale + offset, getYIndex()*scale +offset,scale,scale);
		ghostsLabel.setVisible(true);
	}
	
	//Change direction of ghosts.
	public void changeDirection(int newDirection){
		if(ghostsGlow){
			ghostDirection = newDirection;
			glowDirection = newDirection + BLUE_UP;
		} else {
			ghostDirection = newDirection;
		}
	}
	
	//Change the glow characteristics of ghosts
	public void setGhostsGlow(){
		ghostsGlow = !ghostsGlow;
	}
	
	public boolean getGhostsGlow(){
		return ghostsGlow;
	}
	
	public String getGhostName(){
		return ghostsName;
	}
	
	//Change the special move value, ghosts either has initiated special or hasn't. 
	public void setSpecialInitiated(){
		ghostsSpecialinitiated = !ghostsSpecialinitiated;
	}
	
	public boolean getSpecialValue(){
		return ghostsSpecialinitiated;
	}
	
	//Move ghosts to the desired location.
	public void moveGhosts(){
		switch(ghostDirection){
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
		if(ghostsGlow){
			drawGlowingGhosts();
		} else {
			drawGhost();
		}
	}
	
	public int getDirection(){
		return ghostDirection;
	}
	
	//Set x position of ghost
	public void setXIndex(int xPoint){
		xIndex = xPoint;
	}
	
	//Set y position of ghost
	public void setYIndex(int yPoint){
		yIndex = yPoint;
	}
	
	public int getXIndex(){
		return xIndex;
	}
	
	public int getYIndex(){
		return yIndex;
	}
	
	//Reset special if it is initiated.
	public void resetSpecial(){
		if(ghostsSpecialinitiated){
			ghostsSpecialinitiated = false;
		}
	}
	
	//Check if the given element of array is a wall or empty spot.
	public boolean isWallHit(int arrayComponent){
		if(arrayComponent == BLOCKS || arrayComponent == EMPTY_SPOTS){
			return true;
		} else {
			return false;
		}
	}
}
