package yoshiNightmare;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Peach extends Ghosts{
	
	private String trapImageName = new String();
    private ImageIcon trapImage = new ImageIcon();
	private JLabel trapLabel;
	
	private int trapXIndex = 0; //X position of trap
	private int trapYIndex = 0; //Y position of trap

	public Peach(Container mainPane){
		super(mainPane);
		ghostsName = "PEACH";
		initializeArray();
		trapLabel = new JLabel();
		trapLabel.setBounds(10, 10, 10, 10);
		trapLabel.setVisible(true);
		mainPane.add(trapLabel);
		scaleImages();
	}
	
	//Initialize the images array of peach.
	public void initializeArray(){
		ghostsImageName[UP] = "peach_up.png";
		ghostsImage[UP] = new ImageIcon(ghostsImageName[UP]);
		ghostsImageName[DOWN] = "peach_down.png";
		ghostsImage[DOWN] = new ImageIcon(ghostsImageName[DOWN]);
		ghostsImageName[LEFT] = "peach_left.png";
		ghostsImage[LEFT] = new ImageIcon(ghostsImageName[LEFT]);
		ghostsImageName[RIGHT] = "peach_right.png";
		ghostsImage[RIGHT] = new ImageIcon(ghostsImageName[RIGHT]);
		ghostsImageName[BLUE_UP] = "peach_up_blue.png";
		ghostsImage[BLUE_UP] = new ImageIcon(ghostsImageName[BLUE_UP]);
		ghostsImageName[BLUE_DOWN] = "peach_down_blue.png";
		ghostsImage[BLUE_DOWN] = new ImageIcon(ghostsImageName[BLUE_DOWN]);
		ghostsImageName[BLUE_LEFT] = "peach_left_blue.png";
		ghostsImage[BLUE_LEFT] = new ImageIcon(ghostsImageName[BLUE_LEFT]);
		ghostsImageName[BLUE_RIGHT] = "peach_right_blue.png";
		ghostsImage[BLUE_RIGHT] = new ImageIcon(ghostsImageName[BLUE_RIGHT]);
		
		trapImageName = "penguinsuit.png";
		trapImage = new ImageIcon(trapImageName);
		Image newImage = trapImage.getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT); //Scale trap image to fit in maze.
		trapImage = new ImageIcon(newImage);
		
	}
	
	//Draw Trap on Screen
	public void drawTrap(){
		trapLabel.setIcon(trapImage);
		trapLabel.setBounds(trapXIndex*scale + offset, trapYIndex*scale + offset, scale,scale);
		trapLabel.setVisible(true);
	}
	
	//Destroy Trap by setting the icon to null.
	public void destroyTrap(){
		trapLabel.setIcon(null);
		trapLabel.setVisible(false);
	}
	
	//Check if trap is visible.
	public boolean isTrapVisible(){
		if(trapLabel.getIcon() == null){
			return false;
		} else {
			return true;
		}
	}
	
	//Set X position of Trap
	public void setTrapXIndex(int xIndex){
		trapXIndex = xIndex;
	}
	
	public int getTrapXIndex(){
		return trapXIndex;
	}
	
	//Set Y position of Trap.
	public void setTrapYIndex(int yIndex){
		trapYIndex = yIndex;
	}
	
	public int getTrapYIndex(){
		return trapYIndex;
	}
	
}
