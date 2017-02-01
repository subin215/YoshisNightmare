package yoshiNightmare;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BulletBill {
	protected static final int NUMBER_OF_IMAGES = 4;

	protected String bulletBillImageName[] = new String[NUMBER_OF_IMAGES];
    protected ImageIcon bulletBillImage[] = new ImageIcon[NUMBER_OF_IMAGES];
	protected JLabel bulletLabel;
	
	//x and y position of bullet bill
	private int XIndex = 0;
	private int YIndex = 0;
	private int scale; //scale of image to fit screen.
	private int offset = 50; //offset of image from top,left,right, and bottom of screen.
	
	//Directions index of images in array.
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	
	private Container gamePanel;
	
	public BulletBill(Container panel, int scalar){
		scale = scalar;
		gamePanel = panel;
		bulletLabel = new JLabel();
		bulletLabel.setBounds(10, 10, 10, 10);
		bulletLabel.setVisible(true);
		gamePanel.add(bulletLabel);
		initializeArray();
	}
	
	//Initialize Bullet Bill Image Array.
	public void initializeArray(){
		bulletBillImageName[UP] = "bullet_up.png";
		bulletBillImage[UP] = new ImageIcon(bulletBillImageName[0]);
		bulletBillImageName[DOWN] = "bullet_down.png";
		bulletBillImage[DOWN] = new ImageIcon(bulletBillImageName[1]);
		bulletBillImageName[LEFT] = "bullet_left.png";
		bulletBillImage[LEFT] = new ImageIcon(bulletBillImageName[2]);
		bulletBillImageName[RIGHT] = "bullet_right.png";
		bulletBillImage[RIGHT] = new ImageIcon(bulletBillImageName[3]);
		scaleImage();
	}
	
	//Scale images to fit the screen resolution.
	private void scaleImage(){
		for(int i = 0; i < NUMBER_OF_IMAGES;i++){
			Image newImage = bulletBillImage[i].getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
			bulletBillImage[i] = new ImageIcon(newImage);
		}
	}
	
	//Draw Bullet on Maze.
	public void drawBullet(int bulletDirection){
			bulletLabel.setIcon(bulletBillImage[bulletDirection]);
			bulletLabel.setBounds(getXIndex()*scale + offset, getYIndex()*scale + offset, scale,scale);
			bulletLabel.setVisible(true);
		
	}
	
	//Destroy Bullet from map by setting it to invisible and taking away the icon from the label.
	public void destroyBullet(){
		bulletLabel.setIcon(null);
		bulletLabel.setVisible(false);
	}
	
	//Check if bullet bill is visible.
	public boolean isBulletVisible(){
		if(bulletLabel.getIcon() == null){
			return false;
		} else {
			return true;
		}
	}
	
	//Move the x or y position of bullet based on it's direction.
	public void moveBullet(int bulletDirection) {
			switch(bulletDirection){
			case UP:
				setYIndex(YIndex-1);
				break;
			case DOWN:
				setYIndex(YIndex+1);
				break;
			case LEFT:
				setXIndex(XIndex-1);
				break;
			case RIGHT:
				setXIndex(XIndex+1);
				break;
			}
			drawBullet(bulletDirection);
		}
	
	//Set the X position of Bullet Bill
	public void setXIndex(int xIndex){
		XIndex = xIndex;
	}
	
	public int getXIndex(){
		return XIndex;
	}
	
	//Set Y position of Bullet Bill.
	public void setYIndex(int yIndex){
		YIndex = yIndex;
	}
	
	public int getYIndex(){
		return YIndex;
	}
}
