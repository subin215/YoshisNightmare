package yoshiNightmare;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Mario extends Ghosts{
	
	private final int NUMBER_OF_FIREBALL_IMAGES = 4;
    private String fireBallImageName[] = new String[NUMBER_OF_IMAGES];
    private ImageIcon fireBallImage[] = new ImageIcon[NUMBER_OF_IMAGES];
	private JLabel fireLabel;
	
	private int fireXIndex = 0;
	private int fireYIndex = 0;
	private int fireBallDirection = UP;
	
	public Mario(Container mainPane, int scalar){
		super(mainPane);
		scale = scalar;
		ghostsName = "MARIO";
		initializeArray();
		fireLabel = new JLabel();
		fireLabel.setBounds(10, 10, 10, 10);
		fireLabel.setVisible(true);
		mainPane.add(fireLabel);
		scaleImages();
	}
	
	//Initialize Image arrays
	private void initializeArray(){
		ghostsImageName[UP] = "mario_up.png";
		ghostsImage[UP] = new ImageIcon(ghostsImageName[UP]);
		ghostsImageName[DOWN] = "mario_down.png";
		ghostsImage[DOWN] = new ImageIcon(ghostsImageName[DOWN]);
		ghostsImageName[LEFT] = "mario_left.png";
		ghostsImage[LEFT] = new ImageIcon(ghostsImageName[LEFT]);
		ghostsImageName[RIGHT] = "mario_right.png";
		ghostsImage[RIGHT] = new ImageIcon(ghostsImageName[RIGHT]);
		ghostsImageName[BLUE_UP] = "mario_up_blue.png";
		ghostsImage[BLUE_UP] = new ImageIcon(ghostsImageName[BLUE_UP]);
		ghostsImageName[BLUE_DOWN] = "mario_down_blue.png";
		ghostsImage[BLUE_DOWN] = new ImageIcon(ghostsImageName[BLUE_DOWN]);
		ghostsImageName[BLUE_LEFT] = "mario_left_blue.png";
		ghostsImage[BLUE_LEFT] = new ImageIcon(ghostsImageName[BLUE_LEFT]);
		ghostsImageName[BLUE_RIGHT] = "mario_right_blue.png";
		ghostsImage[BLUE_RIGHT] = new ImageIcon(ghostsImageName[BLUE_RIGHT]);
		
		fireBallImageName[UP] = "fireball_up.png";
		fireBallImage[UP] = new ImageIcon(fireBallImageName[UP]);
		fireBallImageName[DOWN] = "fireball_down.png";
		fireBallImage[DOWN] = new ImageIcon(fireBallImageName[DOWN]);
		fireBallImageName[LEFT] = "fireball_left.png";
		fireBallImage[LEFT] = new ImageIcon(fireBallImageName[LEFT]);
		fireBallImageName[RIGHT] = "fireball_right.png";
		fireBallImage[RIGHT] = new ImageIcon(fireBallImageName[RIGHT]);
		scaleFireBallImage();
	}
	
	//Scale all the FireBall Images.
	private void scaleFireBallImage(){
		for(int i = 0; i < NUMBER_OF_FIREBALL_IMAGES; i++){
			Image newImage = fireBallImage[i].getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
			fireBallImage[i] = new ImageIcon(newImage);	
		}
	}
	
	//Draw Fire Ball on screen.
	public void drawFireBall(){
		fireLabel.setIcon(fireBallImage[fireBallDirection]);
		fireLabel.setBounds(fireXIndex*scale + offset, fireYIndex*scale + offset, scale,scale);
		fireLabel.setVisible(true);
	}
	
	//Move fire ball based on the direction of the ball.
	public void moveFireBall(){
		switch(fireBallDirection){
		case UP:
			setFireYIndex(fireYIndex-1);
			break;
		case DOWN:
			setFireYIndex(fireYIndex+1);
			break;
		case LEFT:
			setFireXIndex(fireXIndex-1);
			break;
		case RIGHT:
			setFireXIndex(fireXIndex+1);
			break;
		}
		drawFireBall();
	}
	
	//Destroy the fire ball by setting label to invisible.
	public void destroyFireBall(){
		fireLabel.setIcon(null);
		fireLabel.setVisible(false);
	}
	
	//Check if fire ball is visible on screen.
	public boolean isFireBallVisible(){
		if(fireLabel.getIcon() ==  null){
			return false;
		} else {
			return true;
		}
	}
	
	//Change fire ball direction
	public void setFireBallDirection(int direction){
		fireBallDirection = direction;
	}
	
	public int getFireBallDirection(){
		return fireBallDirection;
	}
	
	//Change x position of fire ball
	public void setFireXIndex(int xIndex){
		fireXIndex = xIndex;
	}
	
	public int getFireXIndex(){
		return fireXIndex;
	}
	
	//Change y position of fire ball
	public void setFireYIndex(int yIndex){
		fireYIndex = yIndex;
	}
	
	public int getFireYIndex(){
		return fireYIndex;
	}
	
}
