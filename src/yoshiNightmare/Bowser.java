package yoshiNightmare;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bowser extends Ghosts{
	
	protected ImageIcon bowserShell = new ImageIcon();
	
	public Bowser(Container mainPane){
		super(mainPane);
		ghostsName = "BOWSER";
		initializeArray();
		scaleImages();
	}
	
	//Initialize Array of images of Bowser
	public void initializeArray(){
		ghostsImageName[UP] = "bowser_up.png";
		ghostsImage[UP] = new ImageIcon(ghostsImageName[UP]);
		ghostsImageName[DOWN] = "bowser_down.png";
		ghostsImage[DOWN] = new ImageIcon(ghostsImageName[DOWN]);
		ghostsImageName[LEFT] = "bowser_left.png";
		ghostsImage[LEFT] = new ImageIcon(ghostsImageName[LEFT]);
		ghostsImageName[RIGHT] = "bowser_right.png";
		ghostsImage[RIGHT] = new ImageIcon(ghostsImageName[RIGHT]);
		ghostsImageName[BLUE_UP] = "bowser_up_blue.png";
		ghostsImage[BLUE_UP] = new ImageIcon(ghostsImageName[BLUE_UP]);
		ghostsImageName[BLUE_DOWN] = "bowser_down_blue.png";
		ghostsImage[BLUE_DOWN] = new ImageIcon(ghostsImageName[BLUE_DOWN]);
		ghostsImageName[BLUE_LEFT] = "bowser_left_blue.png";
		ghostsImage[BLUE_LEFT] = new ImageIcon(ghostsImageName[BLUE_LEFT]);
		ghostsImageName[BLUE_RIGHT] = "bowser_right_blue.png";
		ghostsImage[BLUE_RIGHT] = new ImageIcon(ghostsImageName[BLUE_RIGHT]);
		
		//Initialize and scale Bowser Shell Image to fit screen.
		bowserShell = new ImageIcon("bowserShell.png");
		Image newImage = bowserShell.getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
		bowserShell = new ImageIcon(newImage);	
	}
	
	//Draw Bowser Shell
	public void drawBowserShell(){
		ghostsLabel.setIcon(bowserShell);
		ghostsLabel.setBounds(getXIndex()*scale + offset, getYIndex()*scale + offset, scale,scale);
		ghostsLabel.setVisible(true);
	}
	
	//Move Bowser Shell 
	public void moveBowserShell(){
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
		drawBowserShell();
	}
	
	
}
