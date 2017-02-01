package yoshiNightmare;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class WelcomeWindow extends JFrame implements ActionListener {
	JButton startButton; 
	JButton controlsButton;
	JButton exitButton;
	JButton okButton;
	JFrame controlsWindow;
	
	public WelcomeWindow(){
		super("Yoshi's Nightmare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		makeButtons();
		drawGUI();
	}
	
	private void makeButtons(){
		
		//Initialize Buttons
		startButton = new JButton("Start Game");
		controlsButton  = new JButton("Controls");
		exitButton  = new JButton("Exit");

		//Fonts
		startButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		controlsButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		
		//Font Color.
		startButton.setForeground(Color.WHITE);
		controlsButton.setForeground(Color.WHITE);
		exitButton.setForeground(Color.WHITE);
		 
		//Set Background Color
		startButton.setBackground(Color.BLACK);
		controlsButton.setBackground(Color.BLACK);
		exitButton.setBackground(Color.GRAY);
		 
		//Action Listener
		startButton.addActionListener(this);
		controlsButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		//Set Size of Buttons
		startButton.setSize(130, 40);
		controlsButton.setSize(130,40);
		exitButton.setSize(130,40);

		//Set empty Borders to Buttons.
		Border emptyBorder = BorderFactory.createEmptyBorder();
		startButton.setBorder(emptyBorder);
		controlsButton.setBorder(emptyBorder);
		
	}
	
	private void drawGUI(){		
		//Add Background Image.
		ImageIcon background = new ImageIcon("StartWindow.png");
		JLabel backgroundImage = new JLabel(background);
		add(backgroundImage);
		
		//Add Buttons to screen.
		startButton.setLocation(200, 430);
		controlsButton.setLocation(520,430);
		exitButton.setLocation(730,540);
		
		//Add buttons to image.
		backgroundImage.add(exitButton);
		backgroundImage.add(controlsButton);
		backgroundImage.add(startButton);
		
		//JFrame 
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void drawControlsGUI(){
		controlsWindow = new JFrame("Controls");
			
		//Add background Image
		ImageIcon background = new ImageIcon("Controls.png");
		JLabel backgroundImage = new JLabel(background);
		controlsWindow.add(backgroundImage);
		
		//Ok Button.
		okButton = new JButton("OK");
		okButton.setSize(140,30);
		okButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		okButton.setBackground(Color.GRAY);
		okButton.setForeground(Color.WHITE);
		okButton.setLocation(350,205);
		okButton.addActionListener(this);
		
		backgroundImage.add(okButton);
		
		//Set properties of Controls window.
		controlsWindow.setResizable(false);
		controlsWindow.pack();
		controlsWindow.setLocationRelativeTo(null);
		controlsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlsWindow.setVisible(true);
	}
	

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == exitButton){
			JOptionPane.showMessageDialog(null, "Goodbye!");
			System.exit(0);
		} else if(e.getSource() == controlsButton){
			drawControlsGUI();
		} else if(e.getSource() == startButton){
			dispose();
			Controller.makeWindowVisible = true;
			Controller controller = new Controller();
		} else if(e.getSource() == okButton){
			controlsWindow.dispose();
		}
	}

}
