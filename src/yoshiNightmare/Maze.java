package yoshiNightmare;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;

public class Maze {
	
	public int mazeArray[][];
	public JLabel[][] block;

	private int rowsOfBlocks = 0;
	private int columnsOfBlocks = 0;
	private String[] mapName = {"World1-1", "World1-2","World1-3"};
	private int level = 0; //Level that user is in.
	private int scale = 40; //For image bounds. 
	private int offset = 50; //For image bounds. 
	
	private final int EMPTY_SPOTS = 0;
	private final int MUSHROOMS = 2;
	private final int CORNER_SHROOMS = 9;
	private final int BLOCKS = 1;
	private final int STARS = 3;
	private final int YOSHI = 4;
	
	private final static int NUMBER_OF_IMAGES = 6;
	private String blockImageName[] = new String[NUMBER_OF_IMAGES];

	private Container mainPane;
	
	//Initialize the block images arrays. 
	private void initializeArray(){
		blockImageName[0] = "block.png";
		blockImageName[1] = "block_ice.png";
		blockImageName[2] = "block_castle.png";
		blockImageName[3] = "block_gold.png";
		blockImageName[4] = "block_fire.png";
		blockImageName[5] = "block_jungle.png";
	}
	
	
	//Constructor
	public Maze(Container Pane, int scalar){
		scale = scalar;
		mainPane = Pane;
		initializeArray();
		startMaze();
	}
	
	//Restart the mazes. 
	public void startMaze(){
		//Remove pre-existing elements from screen if changing levels. 
		if(level > 0){
			for(int i = 0; i < rowsOfBlocks; i++){
				for(int j = 0; j < columnsOfBlocks; j++){
					switch(mazeArray[i][j]){
					case BLOCKS:
					case YOSHI:
					case MUSHROOMS:
					case CORNER_SHROOMS:
					case STARS:
						block[i][j].setIcon(null);
						break;
					default:
						break;
					}
				}
			}
		}
		//Redraw the maze. 
		Random randomGenerator = new Random();
		int blocknumber = randomGenerator.nextInt(NUMBER_OF_IMAGES);
		createMapArray();	
		block = new JLabel[rowsOfBlocks][columnsOfBlocks];
		drawMap(blocknumber);	
	}
	
	//Increase level by 1.
	public void setLevel(){
		level = level + 1;
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getNumberOfMaps(){
		return mapName.length;
	}
	
	//Checks the mazeArray and creates a block array that stores all the elements of the maze. 
	public void drawMap(int blocknumber){
		mainPane.setLayout(null);
		//Loop through the maze Array.
		for(int i = 0; i < rowsOfBlocks; i++){
			for(int j = 0; j < columnsOfBlocks; j++){
				switch(mazeArray[i][j]){ //Check maze Array Component
				case BLOCKS:
					Image newImage = (new ImageIcon(blockImageName[blocknumber])).getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT); //Scale image
					block[i][j] = new JLabel(new ImageIcon(newImage)); //Store image in block array.
					block[i][j].setBounds(i*scale + offset, j*scale + offset, scale, scale); //Set image position for screen.
					block[i][j].setVisible(true);
					mainPane.add(block[i][j]); //add block to the panel used in screen.
					break;
				case MUSHROOMS: 
					Image mushrooms = (new ImageIcon("mushroom.png")).getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
					block[i][j] = new JLabel(new ImageIcon(mushrooms));
					block[i][j].setBounds(i*scale+offset, j*scale+offset, scale, scale);
					block[i][j].setVisible(true);
					mainPane.add(block[i][j]);
					break;
				case STARS: 
					Image stars = (new ImageIcon("star.png")).getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
					block[i][j] = new JLabel(new ImageIcon(stars));
					block[i][j].setBounds(i*scale+offset, j*scale+offset, scale, scale);
					block[i][j].setVisible(true);
					mainPane.add(block[i][j]);
					break;
				case CORNER_SHROOMS:
					Image cornerShrooms = (new ImageIcon("mushroom.png")).getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
					mainPane.setLayout(null);
					block[i][j] = new JLabel(new ImageIcon(cornerShrooms));
					block[i][j].setBounds(i*scale + offset, j*scale + offset, scale, scale);
					block[i][j].setVisible(true);
					mainPane.add(block[i][j]);
					break;
				case EMPTY_SPOTS:
					break;
				case YOSHI:
					block[i][j] = new JLabel();
					block[i][j].setBounds(i*scale + offset, j*scale + offset, scale, scale);
					block[i][j].setVisible(false);
					break;	
				}
			}
		}
	}
	
	public int getColumnsOfBlocks(){
		return columnsOfBlocks;
	}
	
	public int getRowsOfBlocks(){
		return rowsOfBlocks;
	}
	
	//Makes the block invisible.
	public void updateBlockArray(int xIndex, int yIndex){
		block[xIndex][yIndex].setVisible(false);
	}
	
	//Read from the world map files and store the values in the maze array.
	private void createMapArray(){
		Scanner fileReader;
        ArrayList<String> lineList = new ArrayList<String>();

		try{
            fileReader = new Scanner(new File(mapName[level])); //Read from file stored in mapName array.
            while (true) {
                String line = null;

                try {
                    line = fileReader.nextLine();
                } catch (Exception eof) {

                }

                if (line == null) {
                    break;
                }

                lineList.add(line);
            }
            
            //Initialize a map array with required number of rows and columns. 
            rowsOfBlocks = lineList.size();
            columnsOfBlocks  = lineList.get(0).length();
            
            mazeArray = new int[rowsOfBlocks][columnsOfBlocks];
            
            //Get a line, that's the row.
            for (int row = 0; row < rowsOfBlocks; row++) {
            	String line = lineList.get(row);
            	//Get characters from line, that's the column for maze array.
                for (int column = 0; column < columnsOfBlocks; column++) {
                	char type = line.charAt(column);
                    mazeArray[row][column] = Character.getNumericValue(type);
                }
            }
            
		}catch (FileNotFoundException e) {
            System.out.println("Maze map file not found");
        }
	}

}