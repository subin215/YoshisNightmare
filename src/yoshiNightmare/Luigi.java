package yoshiNightmare;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Luigi extends Ghosts{
	
	private String shellImageName = new String();
    private ImageIcon shellImage = new ImageIcon();
	private JLabel shellLabel;
	private int shellDirection = UP;
	
	protected int shellHitCounter = 0; //Starting counter of shell.
	protected int shellDisappearCounter = 6; //Max counter the shell can go to.
	
	public int shellXIndex = 0; //X position of shell
	public int shellYIndex = 0; // Y position of shell.

	public Luigi(Container mainPane){		
		super(mainPane);
		ghostsName = "LUIGI";
		initializeArray();
		shellLabel = new JLabel(new ImageIcon("shellImage"));
		shellLabel.setBounds(10,10,10,10);
		shellLabel.setVisible(true);
		mainPane.add(shellLabel);
		scaleImages();
	}
	
	public void initializeArray(){
		ghostsImageName[UP] = "luigi_up.png";
		ghostsImage[UP] = new ImageIcon(ghostsImageName[UP]);
		ghostsImageName[DOWN] = "luigi_down.png";
		ghostsImage[DOWN] = new ImageIcon(ghostsImageName[DOWN]);
		ghostsImageName[LEFT] = "luigi_left.png";
		ghostsImage[LEFT] = new ImageIcon(ghostsImageName[LEFT]);
		ghostsImageName[RIGHT] = "luigi_right.png";
		ghostsImage[RIGHT] = new ImageIcon(ghostsImageName[RIGHT]);
		ghostsImageName[BLUE_UP] = "luigi_up_blue.png";
		ghostsImage[BLUE_UP] = new ImageIcon(ghostsImageName[BLUE_UP]);
		ghostsImageName[BLUE_DOWN] = "luigi_down_blue.png";
		ghostsImage[BLUE_DOWN] = new ImageIcon(ghostsImageName[BLUE_DOWN]);
		ghostsImageName[BLUE_LEFT] = "luigi_left_blue.png";
		ghostsImage[BLUE_LEFT] = new ImageIcon(ghostsImageName[BLUE_LEFT]);
		ghostsImageName[BLUE_RIGHT] = "luigi_right_blue.png";
		ghostsImage[BLUE_RIGHT] = new ImageIcon(ghostsImageName[BLUE_RIGHT]);
		
		//Initialize and scale shell image.
		shellImageName = "green_shell.png";
		shellImage = new ImageIcon(shellImageName);
		Image newImage = shellImage.getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
		shellImage = new ImageIcon(newImage);	

	}
	
	//Draw Green Shell on Screen.
	public void drawShell(){
		shellLabel.setIcon(shellImage);
		shellLabel.setBounds(shellXIndex*scale + offset, shellYIndex*scale + offset, scale,scale);
		shellLabel.setVisible(true);
	}
	
	//Move Green Shell by changing x or y position based on shell's direction.
	public void moveShell(){
		switch(shellDirection){
		case UP:
			setShellYIndex(shellYIndex-1);
			break;
		case DOWN:
			setShellYIndex(shellYIndex+1);
			break;
		case LEFT:
			setShellXIndex(shellXIndex-1);
			break;
		case RIGHT:
			setShellXIndex(shellXIndex+1);
			break;
		}
		drawShell();
	}
	
	//Check if the component shell is moving towards is a wall or empty spot or corner shroom.
	public boolean isWallHit(int arrayComponent, boolean checkIntersection){
		if(!checkIntersection){ //If not checking intersection.
			if(arrayComponent == BLOCKS || arrayComponent == EMPTY_SPOTS){
				return true;
			} else {
				return false;
			}
		} else { //If checking Intersection.
			if(arrayComponent == BLOCKS || arrayComponent == EMPTY_SPOTS || arrayComponent == CORNER_SHROOMS){
				return true;
			} else {
				return false;
			}
		}
	
	}
	
	//Change the direction shell is moving in.
	public void setShellDirection(int newDirection){
		shellDirection = newDirection;
	}
	
	//Increment shell Hit Counter
	public void setShellHitCounter(){
		shellHitCounter++;
	}
	
	public int getShellHitCounter(){
		return shellHitCounter;
	}
	
	//Check if shellHitCounter is above or equal to the max counter set for it.
	public boolean canShellBeRemoved(){
		if(shellHitCounter >= shellDisappearCounter){
			//Shell can be removed.
			shellHitCounter = 0;
			return true;
		} else{
			return false;
		}
	}
	
	public int getShellDirection(){
		return shellDirection;
	}
	
	//Set Green Shell icon to null to take it away from the screen.
	public void destroyShell(){
		shellLabel.setIcon(null);
		shellLabel.setVisible(false);
	}
	
	//Check if shell is visible.
	public boolean isShellVisible(){
		if(shellLabel.getIcon() == null){
			return false;
		} else {
			return true;
		}
	}
	
	//Change x position of shell
	public void setShellXIndex(int xIndex){
		shellXIndex = xIndex;
	}
	
	public int getShellXIndex(){
		return shellXIndex;
	}
	
	//Change y position of shell.
	public void setShellYIndex(int yIndex){
		shellYIndex = yIndex;
	}
	
	public int getShellYIndex(){
		return shellYIndex;
	}
	
	
}
