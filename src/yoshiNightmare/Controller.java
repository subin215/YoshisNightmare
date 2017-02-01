package yoshiNightmare;
//Controller for the Yoshi Nightmare game.
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.*;

public class Controller extends TimerTask implements KeyListener {
	private JFrame mainFrame; //JFrame for game.
	private Maze maze; // Maze object
	private Yoshi myYoshi; //Yoshi object
	
	//Labels for points and lives.
	private JLabel points; 
	private JLabel livesLabel;
	
	public static boolean makeWindowVisible = false;
	
	private int TIME_TO_MOVE_CHARACTERS = 100; // milliseconds on timer
	private final int YOSHI_RUN_TIME = 2; //Time to run Yoshi.
	private final int GHOSTS_RUN_TIME = 1; //Time to run ghosts.
	private int SPECIAL_RUN_TIME = 30; //Time to run specials of ghosts.

	private java.util.Timer gameTimer = new java.util.Timer(); //Game timer
	private int counter_of_ghosts_special_runtime = 0; //Counter to know when special needs to be activated.
    private int counter_of_timer_runtime = 0; //Counter for timer.
    private int counter_of_bullet_runtime = 0; //Counter to know when bullets need to be activated.
    private int counter_glow_timer_runtime = 0; //Counter for character glow timer.
	private int bullet_bill_timer = 0; //counter for shooting bullet bills
	
	private int time_between_bullet_bills = 90; //time between shooting bullet bills
    private int time_to_reset_glow = 50; //How many counts will characters glow?

	private final int maxLives = 3; //Maximum Lives for Yoshi. 

	//Ghosts Location in Array
	private final int MARIO = 0;
	private final int LUIGI = 1;
	private final int BOWSER = 2;
	private final int PEACH = 3;
	
	//Map objects identifiers in Maze.
	private final int MUSHROOMS = 2;
	private final int CORNER_SHROOMS = 9;
	private final int STARS = 3;
	private final int YOSHI = 4;
	
	//Points
	private final int ghostPoints = 100; //Ghosts
	private final int mushroomPoints = 10;
	private final int starPoints = 50;
	
	//Direction values of keypress.
	private final int LEFT_ARROW = 37;
	private final int UP_ARROW = 38;
	private final int RIGHT_ARROW = 39;
	private final int DOWN_ARROW = 40;
	
	//Direction index for images in array.
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
    private final int noDirection = 8; //Special Direction, this tells Yoshi that user hasn't stored a direction to
    									// move. 
    
    //Screen resolution and scale of images to fit them in screen.
    private double width;
    private double height;
    private int scale;
	
	private final int NUMBER_OF_GHOSTS = 4;
	private final int NUMBER_OF_DIRECTIONS = 4;
	private final int NUMBER_OF_BULLETS = 4;

	//Character Arrays.
	private Ghosts ghosts[] = new Ghosts[NUMBER_OF_GHOSTS];
	private BulletBill bullets[] = new BulletBill[NUMBER_OF_BULLETS];
	 
	public static void main(String[] args) {
		//Calls the class with the Welcome window.
		@SuppressWarnings("unused")
		WelcomeWindow window = new WelcomeWindow();
	}
	
	public Controller(){
		//Initialize JFrame and set dimensions and scale.
		mainFrame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
		scale = (int) (screenSize.getWidth()-100)/39; //Scale for images in maze.
				
		//Create Characters.
		initializeCharacters();
		
		createMainWindow(); //Create main window components for game.
		makeWindow(); //Method to start putting components into main window.
	}
	
	private void createMainWindow(){
		//Makes the main window for Game. 
		mainFrame.setTitle("Yoshi's Nightmare");
		mainFrame.setSize((int)width,(int)height);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(makeWindowVisible);
		mainFrame.setResizable(false);
		mainFrame.addKeyListener(this);
	}
	
	//Creates Characters.
	private void initializeCharacters(){
		//initialize Yoshi
		myYoshi = new Yoshi(mainFrame.getContentPane(),maxLives,scale);
		
		//Create Ghosts
		ghosts[MARIO] = new Mario(mainFrame.getContentPane(), scale);
		ghosts[LUIGI] = new Luigi(mainFrame.getContentPane());
		ghosts[BOWSER] = new Bowser(mainFrame.getContentPane());
		ghosts[PEACH] = new Peach(mainFrame.getContentPane());
		
		//create bullet bills
		bullets[UP] = new BulletBill(mainFrame.getContentPane(), scale);
		bullets[DOWN] = new BulletBill(mainFrame.getContentPane(), scale);
		bullets[LEFT] = new BulletBill(mainFrame.getContentPane(), scale);
		bullets[RIGHT] = new BulletBill(mainFrame.getContentPane(), scale);
	}
	
	//Add component to main window and make it visible.
	private void makeWindow(){
		maze = new Maze(mainFrame.getContentPane(), scale); //Initialize Maze.
		
		mainFrame.getContentPane().setBackground(Color.BLACK); //Set Background to Black.
		
		//Points and Lives Display.
		points = new JLabel("Points: " + myYoshi.getScore());
		livesLabel = new JLabel("Lives: " + myYoshi.getLives());
		
		//Labels design.
		livesLabel.setForeground(Color.WHITE);
		points.setForeground(Color.WHITE);
		
		points.setBounds(50, 15, 100, 50);
		livesLabel.setBounds(50, 0, 100, 50);
		
		//Add labels to main window.
		mainFrame.getContentPane().add(points);
		mainFrame.getContentPane().add(livesLabel);
		
		mainFrame.repaint();
		
		startCharacters("ALL"); //Starts characters.
		gameTimer.schedule(this, 0, TIME_TO_MOVE_CHARACTERS); //Starts Timer.
	}
	
	//Starts characters by looking at the map in the maze.
	private void startCharacters(String characterToBeReset){	
		
		for(int i = 0; i < maze.getRowsOfBlocks(); i++){
			for(int j = 0; j < maze.getColumnsOfBlocks(); j++){
				switch(maze.mazeArray[i][j]){
				case YOSHI: //Draw Yoshi.
					if(characterToBeReset.equalsIgnoreCase("ALL") || characterToBeReset.equalsIgnoreCase("YOSHI")){
						myYoshi.changeDirection(UP);
						myYoshi.setXIndex(i);
						myYoshi.setYIndex(j);
						myYoshi.drawYoshi();
					}
					break;
				case 5: //Draw Mario.
					if(characterToBeReset.equalsIgnoreCase("ALL") || characterToBeReset.equalsIgnoreCase("MARIO")){
						ghosts[MARIO].changeDirection(DOWN);
						ghosts[MARIO].setXIndex(i);
						ghosts[MARIO].setYIndex(j);
						ghosts[MARIO].drawGhost();
					}
					break;
				case 6: //Draw Luigi
					if(characterToBeReset.equalsIgnoreCase("ALL") || characterToBeReset.equalsIgnoreCase("LUIGI")){
						ghosts[LUIGI].changeDirection(DOWN);
						ghosts[LUIGI].setXIndex(i);
						ghosts[LUIGI].setYIndex(j);
						ghosts[LUIGI].drawGhost();
					}
					break;
				case 7: //Draw Bowser
					if(characterToBeReset.equalsIgnoreCase("ALL") || characterToBeReset.equalsIgnoreCase("BOWSER")){
						ghosts[BOWSER].changeDirection(DOWN);
						ghosts[BOWSER].setXIndex(i);
						ghosts[BOWSER].setYIndex(j);
						ghosts[BOWSER].drawGhost();
					}
					break;
				case 8: //Draw Peach
					if(characterToBeReset.equalsIgnoreCase("ALL") || characterToBeReset.equalsIgnoreCase("PEACH")){
						ghosts[PEACH].changeDirection(DOWN);
						ghosts[PEACH].setXIndex(i);
						ghosts[PEACH].setYIndex(j);
						ghosts[PEACH].drawGhost();
					}
					break;

				}
			}
		}
		mainFrame.repaint();
	}
	
	//Key Press Event.
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case LEFT_ARROW:
			//Check if there's wall where user wants yoshi to move. 
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()-1][myYoshi.getYIndex()]) ){
				//No wall so change Yoshi's direction.
				myYoshi.changeDirection(LEFT);
				myYoshi.setLastSpecifiedDirection(noDirection);
			} else { //If wall exists, then store the input direction for later use.
				myYoshi.setLastSpecifiedDirection(LEFT);
			}
			break;
		case UP_ARROW:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()][myYoshi.getYIndex()-1])){
				myYoshi.changeDirection(UP);
				myYoshi.setLastSpecifiedDirection(noDirection);
			} else {
				myYoshi.setLastSpecifiedDirection(UP);
			}
			break;
		case RIGHT_ARROW:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()+1][myYoshi.getYIndex()])){
				myYoshi.changeDirection(RIGHT);
				myYoshi.setLastSpecifiedDirection(noDirection);
			} else {
				myYoshi.setLastSpecifiedDirection(RIGHT);
			}
			break;
		case DOWN_ARROW:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()][myYoshi.getYIndex()+1])){
				myYoshi.changeDirection(DOWN);
				myYoshi.setLastSpecifiedDirection(noDirection);
			} else {
				myYoshi.setLastSpecifiedDirection(DOWN);
			}
			break;
		}
	}
	
	//Runs every time timer runs.
	@Override
	public void run() {
		counter_of_timer_runtime++;	
		//Ghosts special check every time timer runs.
		marioSpecial();
		bowserSpecial();
		luigiSpecial();
					
		if(counter_of_timer_runtime == YOSHI_RUN_TIME){ //Move Yoshi.
			
			//If User had pressed a direction before and it was stored, then check if there's a wall there now.
			if(myYoshi.getLastSpecifiedDirection() != noDirection && isWallHitInDirection(myYoshi.getLastSpecifiedDirection())){
				//No wall in last specified direction. Change direction.
				myYoshi.changeDirection(myYoshi.getLastSpecifiedDirection());
				moveYoshi();
				myYoshi.setLastSpecifiedDirection(noDirection);
			} else if(isWallHitInDirection(myYoshi.getDirection())){ //If user hasn't pressed a direction before.
					myYoshi.changeDirection(myYoshi.getDirection());
					moveYoshi();		
			}
			
			counter_of_timer_runtime = 0; //Reset counter.			
		
			//GHOSTS MOVEMENTS
		} else if(counter_of_timer_runtime == GHOSTS_RUN_TIME){
			
			counter_of_ghosts_special_runtime++;//Add ghosts special runtime counter.
			
			//Is it itme for ghosts special to activate?
			if(counter_of_ghosts_special_runtime >= SPECIAL_RUN_TIME){
				//If mario special hasn't already been activated and if yoshi isn't glowing.
				if((!ghosts[MARIO].getSpecialValue()) && !myYoshi.getYoshiGlow()){
					((Mario)ghosts[MARIO]).setFireBallDirection(ghosts[MARIO].getDirection());
					((Mario)ghosts[MARIO]).setFireXIndex(ghosts[MARIO].getXIndex());
					((Mario)ghosts[MARIO]).setFireYIndex(ghosts[MARIO].getYIndex());
					((Mario)ghosts[MARIO]).drawFireBall();
					ghosts[MARIO].setSpecialInitiated();
				} 
				//If BOWSER special hasn't already been activated and if yoshi isn't glowing.
				if((!ghosts[BOWSER].getSpecialValue()) && !myYoshi.getYoshiGlow()){
					ghosts[BOWSER].setSpecialInitiated();
				} 
				//If LUIGI special hasn't already been activated and if yoshi isn't glowing.
				if ((!ghosts[LUIGI].getSpecialValue()) && !myYoshi.getYoshiGlow()){
					((Luigi)ghosts[LUIGI]).setShellXIndex(ghosts[LUIGI].getXIndex());
					((Luigi)ghosts[LUIGI]).setShellYIndex(ghosts[LUIGI].getYIndex());
					((Luigi)ghosts[LUIGI]).drawShell();
					ghosts[LUIGI].setSpecialInitiated();
				} 
				//If Yoshi isn't glowing activate Peach.
				if(!myYoshi.getYoshiGlow()){
					((Peach)ghosts[PEACH]).setTrapXIndex(ghosts[PEACH].getXIndex());
					((Peach)ghosts[PEACH]).setTrapYIndex(ghosts[PEACH].getYIndex());
					((Peach)ghosts[PEACH]).drawTrap();
				}
				//Reset special runtime counter.
				counter_of_ghosts_special_runtime = 0;
			}
			
			for(int i=0; i<NUMBER_OF_GHOSTS; i++){
				//Move individual ghosts
				ghostsMovement(i);
			}
		}
		
		//Check if glowing timer for ghosts has ended.
		if(counter_glow_timer_runtime > time_to_reset_glow){
			counter_glow_timer_runtime = 0;
			myYoshi.setYoshiGlow(); //Reset Yoshi's glow
			for(int i = 0; i < NUMBER_OF_GHOSTS; i++){ //Reset Ghosts to have them stop glowing.
				if(ghosts[i].getGhostsGlow()){
					ghosts[i].setGhostsGlow();
				}
			}		
		}

		//bullet bill, launch and move them
		launchBullets();
		if (counter_of_bullet_runtime >= time_between_bullet_bills){
			for(int i = 0; i < NUMBER_OF_BULLETS; i++){
				bullets[i].moveBullet(i);
				didYoshiTouchBullet(); //Check if Yoshi touched Bullet.
			}
		}	
	}
	
	//Method to Move ghosts.
	private void ghostsMovement(int ghostNumber){
		// If Ghosts hit corner
		if(maze.mazeArray[ghosts[ghostNumber].getXIndex()][ghosts[ghostNumber].getYIndex()] == CORNER_SHROOMS){
			Random randomGenerator = new Random();
			while (true) {
				int newDirection = randomGenerator.nextInt(NUMBER_OF_DIRECTIONS); //Random direction for ghosts.
				//Is new ghost direction going backwards?
				if((newDirection == DOWN && ghosts[ghostNumber].getDirection() == UP) || (newDirection == UP && ghosts[ghostNumber].getDirection() == DOWN)
						|| (newDirection == LEFT && ghosts[ghostNumber].getDirection() == RIGHT) || (newDirection == RIGHT && ghosts[ghostNumber].getDirection() == LEFT)) {
					//Loop again if ghosts is gowing backwards.
				} else if (!willGhostHitWall(newDirection, ghostNumber)) { //If ghost doesn't hit walls.
					
					if(ghosts[ghostNumber].getGhostsGlow()){ //If ghost is glowing.
						counter_glow_timer_runtime++;
						ghosts[ghostNumber].changeDirection(newDirection);
						ghosts[ghostNumber].moveGhosts();
						break;
						
					} else{ //If ghost isn't glowing.		
							//If bowser has his special value activated.
							if(ghostNumber == BOWSER && ghosts[BOWSER].getSpecialValue()){
								ghosts[ghostNumber].changeDirection(newDirection);
								((Bowser)ghosts[ghostNumber]).moveBowserShell();
							} else{ //If ghost being moved isn't bowser.
								ghosts[ghostNumber].changeDirection(newDirection);
								ghosts[ghostNumber].moveGhosts();
							}
							didYoshiTouchGhost();
						break;	
					}
				}
			}
		} else { // If corner is not hit.
			if(ghostNumber == BOWSER && ghosts[BOWSER].getSpecialValue()){
				((Bowser)ghosts[ghostNumber]).moveBowserShell();
				didYoshiTouchGhost();
			} else{
				ghosts[ghostNumber].moveGhosts();
				didYoshiTouchGhost();
			}
		}
	}
	
	//Method that calls all methods needed when Yoshi is moved.
	private void moveYoshi(){
		myYoshi.moveYoshi(); //Move Yoshi.
		updateScore(); //Update Score.
		updateMaze(); //Update the Maze Array for the element Yoshi just moved to.
		repaintPoints(); //Repaint points.
		didYoshiTouchGhost();//Check if Yoshi touched Ghosts.
		didYoshiTouchFire(); //Check if Yoshi touched Fire Ball.
		didYoshiTouchTrap(); //Check if Yoshi touched Penguin Suit Trap.
		didYoshiTouchShell(); //Check if Yoshi touched Green Shell.
		didYoshiTouchBullet(); //Check if Yoshi touched Bullet Bills.
			
		if(didYoshiWin()){ //Did yoshi win.
			maze.setLevel(); //Increase level.
			if(maze.getLevel() < maze.getNumberOfMaps()){ //If another level exists.
				TIME_TO_MOVE_CHARACTERS = TIME_TO_MOVE_CHARACTERS - 15; //Move characters faster.
				SPECIAL_RUN_TIME = SPECIAL_RUN_TIME + 5; //Ghosts enable special power faster
				time_between_bullet_bills = time_between_bullet_bills - 5; //Bullet bills shoot faster
				time_to_reset_glow = time_to_reset_glow - 5; // Glow time for Yoshi is shorter.
				//Restrat maze, character and labels.
				maze.startMaze();
				startCharacters("ALL");
				repaintLives();
				repaintPoints();
			} else{
				//If Yoshi won then show winning screen.
				mainFrame.setVisible(false);
				JFrame winScreen = new JFrame("Congratulations!!");
				winScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				winScreen.getContentPane().add(new JLabel(new ImageIcon("win_screen.png")));
				winScreen.pack();
				winScreen.setLocationRelativeTo(null);
				winScreen.setResizable(false);
				winScreen.setVisible(true);
			}
		}
	}
		
	//This method updates Maze by setting the element to invisible. 
	private void updateMaze(){
		maze.updateBlockArray(myYoshi.getXIndex(), myYoshi.getYIndex());
		mainFrame.getContentPane().repaint();
	}
	
	//Updates Score of user. Called when Yoshi eats a mushroom or a star. 
	private void updateScore(){
		//if the object eaten is visible.
		if(maze.block[myYoshi.getXIndex()][myYoshi.getYIndex()].isVisible()){ 
			//Get mazeArray component to compare.
			switch(maze.mazeArray[myYoshi.getXIndex()][myYoshi.getYIndex()]){
			case (MUSHROOMS):
					myYoshi.addScore(mushroomPoints); //increment Score
				break;
			case CORNER_SHROOMS:
				myYoshi.addScore(mushroomPoints); //increment Score
				break;
			case STARS:
				myYoshi.addScore(starPoints); //Increment Score.
				
				//if it's a star then convert ghosts to glow and set new counter for glow timer.
				counter_glow_timer_runtime = 0;
				
				for(int i = 0; i < NUMBER_OF_GHOSTS; i++){
					//If yoshi isn't glowing then make him glow.
					if(!myYoshi.getYoshiGlow()){
						myYoshi.setYoshiGlow();
						// Destroy Penguin Trap if it is visible.
						// This is because we don't check if trap needs to be removed every character runtime. 
						if(((Peach)ghosts[PEACH]).isTrapVisible()){
							((Peach)ghosts[PEACH]).destroyTrap();
						}
					}
					if(!ghosts[i].getGhostsGlow()){ //if ghosts aren't glowing already.
						ghosts[i].setGhostsGlow(); //Make ghosts glow.
					}
				}	
				resetBullet(); //Reset bullets once star is consumed.
				break;
			}
		}	
	}
	
	//Repaint the points label on window.
	private void repaintPoints(){
		points.setText("Points: " + myYoshi.getScore());
		mainFrame.getContentPane().add(points);
		mainFrame.repaint();
	}
	
	//Repaint the lives label on window.
	private void repaintLives(){
		livesLabel.setText("Lives: " + myYoshi.getLives());
		mainFrame.getContentPane().add(livesLabel);
		mainFrame.repaint();
	}
	
	//Resets counter and sets all the Bullets to invisible.
	private void resetBullet(){
		for(int k = 0; k < NUMBER_OF_BULLETS; k++){
			bullets[k].destroyBullet();
		}
		mainFrame.repaint();
		bullet_bill_timer = 0;
		counter_of_bullet_runtime = 0;
	}
	
	//Resets special counter and resets all specials of ghosts.
	private void resetSpecials(){
		((Peach)ghosts[PEACH]).destroyTrap();
		((Mario)ghosts[MARIO]).destroyFireBall();
		((Luigi)ghosts[LUIGI]).destroyShell();
		counter_of_ghosts_special_runtime = 0;
	}
	
	//Check if Yoshi Touched Penguin Trap.
	private boolean didYoshiTouchTrap(){
		if(myYoshi.getXIndex() == ((Peach)ghosts[PEACH]).getTrapXIndex() && myYoshi.getYIndex() == ((Peach)ghosts[PEACH]).getTrapYIndex() && ((Peach)ghosts[PEACH]).isTrapVisible()){
			//Was this Yoshi's last life?
			if(myYoshi.getLives() == 1){ 
				JOptionPane.showMessageDialog(null, "Game Over, dear!");
				System.exit(0);
			} else {
				resetSpecials();
				resetBullet();
				myYoshi.changeLives(); //Decrement lives left.
				repaintLives(); //Repaint lives display.
				startCharacters("ALL"); //Restart characters (Ghosts).
			}
		}
		return false;
	}
	
	//Checks if Yoshi Touched Fire.
	private boolean didYoshiTouchFire(){
		if(myYoshi.getXIndex() == ((Mario)ghosts[MARIO]).getFireXIndex() && myYoshi.getYIndex() == ((Mario)ghosts[MARIO]).getFireYIndex() && ((Mario)ghosts[MARIO]).isFireBallVisible()){
			//Was this Yoshi's last life?
			if(myYoshi.getLives() == 1){ 
				JOptionPane.showMessageDialog(null, "Game Over, darlings!");
				System.exit(0);
			} else {
				resetSpecials();
				resetBullet();
				myYoshi.changeLives(); //Decrement lives left.
				repaintLives(); //Repaint lives display.
				startCharacters("ALL"); //Restart characters (Ghosts).
			}
		}
		return false;
	}
	
	//Checks if Yoshi Touched Green Shell.
	private boolean didYoshiTouchShell(){
		if(myYoshi.getXIndex() == ((Luigi)ghosts[LUIGI]).getShellXIndex() && myYoshi.getYIndex() == ((Luigi)ghosts[LUIGI]).getShellYIndex() && ((Luigi)ghosts[LUIGI]).isShellVisible()){
			//Was this Yoshi's last life?
			if(myYoshi.getLives() == 1){ 
				JOptionPane.showMessageDialog(null, "Game Over, darlings!");
				System.exit(0);
			} else {
				resetSpecials();
				resetBullet();
				myYoshi.changeLives(); //Decrement lives left.
				repaintLives(); //Repaint lives display.
				startCharacters("ALL"); //Restart characters (Ghosts).
			}
		}
		return false;
	}
	
	//Checks if Yoshi Touched Bullet.
	private boolean didYoshiTouchBullet(){
		for(int i = 0; i < NUMBER_OF_BULLETS; i++){
			if(myYoshi.getXIndex() == bullets[i].getXIndex() && myYoshi.getYIndex() == bullets[i].getYIndex() && bullets[i].isBulletVisible()){
				//Was this Yoshi's last life?
				if(myYoshi.getLives() == 1){ 
					JOptionPane.showMessageDialog(null, "Game Over, darling!");
					System.exit(0);
				} else {
					resetSpecials();
					resetBullet();
					myYoshi.changeLives(); //Decrement lives left.
					repaintLives(); //Repaint lives display.
					startCharacters("ALL"); //Restart characters (Ghosts).
				}
			}
		}
		
		return false;
	}
	
	//Check if Yoshi touched any ghosts. 
	private boolean didYoshiTouchGhost(){
		//Loop through all ghosts.
		for(int i = 0; i < NUMBER_OF_GHOSTS; i++){
			if(myYoshi.getXIndex() == ghosts[i].getXIndex() && myYoshi.getYIndex() == ghosts[i].getYIndex()){ //Is yoshi on same x and y value as ghosts?
				if(ghosts[i].getGhostsGlow()){ //If yoshi is glowing.
					myYoshi.addScore(ghostPoints); //Add points for eating ghosts.
					repaintPoints(); 
					ghosts[i].setGhostsGlow(); //Reset glowing of ghosts.
					startCharacters(ghosts[i].getGhostName()); //Restart that ghost.
				} else {
					//Was this Yoshi's last life?
					if(myYoshi.getLives() == 1){ 
						JOptionPane.showMessageDialog(null, "Game Over, darling!");
						System.exit(0);
					} else {
						resetSpecials();
						resetBullet();
						myYoshi.changeLives(); //Decrement lives left.
						repaintLives(); //Repaint lives display.
						startCharacters("ALL"); //Restart characters (Ghosts).
					}
				}		
			}
		}
		return false;
	}
	
	//Check if Yoshi won the game by checking if all Shrooms/stars are invisible.
	private boolean didYoshiWin(){
		for(int i = 0; i < maze.getRowsOfBlocks(); i++){
			for(int j = 0; j < maze.getColumnsOfBlocks(); j++){
				switch(maze.mazeArray[i][j]){
				case MUSHROOMS:
					if(maze.block[i][j].isVisible()){
						return false;
					}
					break;
				case CORNER_SHROOMS:
					if(maze.block[i][j].isVisible()){
						return false;
					}
					break;
				case STARS:
					if(maze.block[i][j].isVisible()){
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	//Bowser's special powers
	private void bowserSpecial(){
		//Bowser special power initiated?
		if(ghosts[BOWSER].getSpecialValue()){					
			if(!myYoshi.getYoshiGlow()){ //If Yoshi isn't glowing then move bowser
				ghostsMovement(BOWSER);
			}
			if(willGhostHitWall(ghosts[BOWSER].getDirection(),BOWSER) || myYoshi.getYoshiGlow()){
				ghosts[BOWSER].setSpecialInitiated();
			}
		}
	}
	
	//Run Mario's Special
	private void marioSpecial(){
		//Mario special power initiated?
		if(ghosts[MARIO].getSpecialValue()){
			//Will fireball hit wall? If it does then remove it.
			if(!willFireHitWall() && !myYoshi.getYoshiGlow()){
				((Mario)ghosts[MARIO]).moveFireBall();
				didYoshiTouchFire();
			} else if (willFireHitWall() || myYoshi.getYoshiGlow()){
				ghosts[MARIO].setSpecialInitiated();
				((Mario)ghosts[MARIO]).destroyFireBall();
			}
		}
	}
	
	//Run Luigi's Special.
	private void luigiSpecial(){
		//LUIGI Special run
		if(ghosts[LUIGI].getSpecialValue()){
			if(!myYoshi.getYoshiGlow()){
				if(!((Luigi)ghosts[LUIGI]).canShellBeRemoved()){ //If shell can't be removed.
					if(!willShellHitWall(false)){ //If shell doesn't hit wall.
						if(!willShellHitWall(true)){ //If shell doesn't hit intersection.
							((Luigi)ghosts[LUIGI]).moveShell();
							didYoshiTouchShell();
						} else {
							changeShellDirection();
							((Luigi)ghosts[LUIGI]).setShellHitCounter();
							didYoshiTouchShell();
						}
						
					} else { //If shell hits wall.
						changeShellDirection(); //Change direction.
						didYoshiTouchShell(); //See if Green Shell touches Yoshi.
						((Luigi)ghosts[LUIGI]).setShellHitCounter();
					}
				} else { //If shell can be removed then destroy it.
					((Luigi)ghosts[LUIGI]).destroyShell();
					ghosts[LUIGI].setSpecialInitiated();
				}
			} else { //If Yoshi is glowing then destroy shell.
				ghosts[LUIGI].setSpecialInitiated();
				((Luigi)ghosts[LUIGI]).destroyShell();
			}
		}
	}
	
	//Method for the shell to change direction.	
	private void changeShellDirection(){
		Random randomGenerator = new Random();
		while (true) {
			int newShellDirection = randomGenerator.nextInt(NUMBER_OF_DIRECTIONS);
			((Luigi)ghosts[LUIGI]).setShellDirection(newShellDirection);
			if(!willShellHitWall(false)){
				((Luigi)ghosts[LUIGI]).moveShell();
				break;
			}
		}
	}
	
	//Does Green Shell hit wall
	private boolean willShellHitWall(boolean checkIntersection){
		switch(((Luigi)ghosts[LUIGI]).getShellDirection()){
		case UP:
			if(((Luigi)ghosts[LUIGI]).isWallHit(maze.mazeArray[((Luigi)ghosts[LUIGI]).getShellXIndex()][((Luigi)ghosts[LUIGI]).getShellYIndex()-1], checkIntersection)){
				return true;
			} 
			break;
		case DOWN:
			if(((Luigi)ghosts[LUIGI]).isWallHit(maze.mazeArray[((Luigi)ghosts[LUIGI]).getShellXIndex()][((Luigi)ghosts[LUIGI]).getShellYIndex()+1], checkIntersection)){
				return true;
			}
			break;
		case LEFT:
			if(((Luigi)ghosts[LUIGI]).isWallHit(maze.mazeArray[((Luigi)ghosts[LUIGI]).getShellXIndex() - 1][((Luigi)ghosts[LUIGI]).getShellYIndex()], checkIntersection)){
				return true;
			}
			break;
		case RIGHT:
			if(((Luigi)ghosts[LUIGI]).isWallHit(maze.mazeArray[((Luigi)ghosts[LUIGI]).getShellXIndex() + 1][((Luigi)ghosts[LUIGI]).getShellYIndex()], checkIntersection)){
				return true;
			}
			break;
		}
		return false;
	}
	
	//Does fireball hit wall
	private boolean willFireHitWall(){
			switch(((Mario)ghosts[MARIO]).getFireBallDirection()){
			case UP:
				if(ghosts[MARIO].isWallHit(maze.mazeArray[((Mario)ghosts[MARIO]).getFireXIndex()][((Mario)ghosts[MARIO]).getFireYIndex()-1])){
					return true;
				} 
				break;
			case DOWN:
				if(ghosts[MARIO].isWallHit(maze.mazeArray[((Mario)ghosts[MARIO]).getFireXIndex()][((Mario)ghosts[MARIO]).getFireYIndex()+1])){
					return true;
				}
				break;
			case LEFT:
				if(ghosts[MARIO].isWallHit(maze.mazeArray[((Mario)ghosts[MARIO]).getFireXIndex() - 1][((Mario)ghosts[MARIO]).getFireYIndex()])){
					return true;
				}
				break;
			case RIGHT:
				
				if(ghosts[MARIO].isWallHit(maze.mazeArray[((Mario)ghosts[MARIO]).getFireXIndex() + 1][((Mario)ghosts[MARIO]).getFireYIndex()])){
					return true;
				}
				break;
			}
			return false;
	}
	
	//Check if Ghosts hit wall.
	private boolean willGhostHitWall(int direction, int ghost_number){
		switch(direction){
		case 0:
			if((maze.mazeArray[ghosts[ghost_number].getXIndex()][ghosts[ghost_number].getYIndex()-1] == 1) || (maze.mazeArray[ghosts[ghost_number].getXIndex()][ghosts[ghost_number].getYIndex()-1] == 0)){
				return true;
			}
			break;
			
		case 1:
			if((maze.mazeArray[ghosts[ghost_number].getXIndex()][ghosts[ghost_number].getYIndex()+1] == 1) || (maze.mazeArray[ghosts[ghost_number].getXIndex()][ghosts[ghost_number].getYIndex()+1] == 0)){
				return true;
			}
			break;
			
		case 2:
			if((maze.mazeArray[ghosts[ghost_number].getXIndex()-1][ghosts[ghost_number].getYIndex()] == 1) || (maze.mazeArray[ghosts[ghost_number].getXIndex()-1][ghosts[ghost_number].getYIndex()] == 0)){
				return true;
			}
			break;
			
		case 3:
			if((maze.mazeArray[ghosts[ghost_number].getXIndex()+1][ghosts[ghost_number].getYIndex()] == 1) || (maze.mazeArray[ghosts[ghost_number].getXIndex()+1][ghosts[ghost_number].getYIndex()] == 0)){
				return true;
			}
			break;
		}
		return false;
	}
	
	//Check if Yoshi hit wall.
	private boolean isWallHitInDirection(int direction){
		switch(direction){
		case 2:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()-1][myYoshi.getYIndex()]) ){
				return true;
			} 
			break;
		case 0:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()][myYoshi.getYIndex()-1])){
				return true;
			} 
			break;
		case 3:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()+1][myYoshi.getYIndex()])){
				return true;
			} 
			break;
		case 1:
			if(!myYoshi.isWallHit(maze.mazeArray[myYoshi.getXIndex()][myYoshi.getYIndex()+1])){
				return true;
			}
			break;
		}
		return false;
	}
	
	//Launch bullet bill. Bullet bill appears at random points in the map.
	private void launchBullets() {
		int numberOfHorizontalBlocks = 37;
		int numberOfVerticalBlocks = 20;
		bullet_bill_timer++;
		counter_of_bullet_runtime++;
		
		if (bullet_bill_timer >= time_between_bullet_bills){
			
			Random randomGenerator = new Random();
			int xlocation1 = randomGenerator.nextInt(numberOfHorizontalBlocks) + 1;
			int xlocation2 = randomGenerator.nextInt(numberOfHorizontalBlocks) + 1;
			int ylocation1 = randomGenerator.nextInt(numberOfVerticalBlocks) + 1;
			int ylocation2 = randomGenerator.nextInt(numberOfVerticalBlocks) + 1;
			
			bullets[UP].setXIndex(xlocation1);
			bullets[UP].setYIndex(22);
			bullets[UP].drawBullet(UP);
			
			bullets[DOWN].setXIndex(xlocation2);
			bullets[DOWN].setYIndex(-1);
			bullets[DOWN].drawBullet(DOWN);
			
			bullets[LEFT].setXIndex(39);
			bullets[LEFT].setYIndex(ylocation1);
			bullets[LEFT].drawBullet(LEFT);
			
			bullets[RIGHT].setXIndex(-1);
			bullets[RIGHT].setYIndex(ylocation2);
			bullets[RIGHT].drawBullet(RIGHT);
			
			bullet_bill_timer = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
