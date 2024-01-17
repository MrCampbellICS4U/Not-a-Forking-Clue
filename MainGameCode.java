/*
 * MainGameCode.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 11, 2024
 * Main code for Not a Forking Clue
 */
package grade12;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;

public class MainGameCode extends JFrame implements ActionListener{

	// player name
	String name = "Judy";
	
	// variables
	JPanel mainPanel;
	DrawingPanel dp;
	JButton next;
	//private Round currentRound;
	
	// timer stuff
	Timer timer;
	ActionListener action;
	int TIMERSPEED = 1000; // speed in seconds
	int count;
	
	// image declaration
	BufferedImage restaurantbg;
	
	// context panel components
	JButton nextButton;
    JLabel title;
    JTextArea context;
    JScrollPane scroll;
	
	// panel width & height
	private final static int PANW = 1440 - 700;
	private final static int PANH = 1440 - 700;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGameCode();
			}
		});
	}
	
	/**
	 * constructor
	 */
	public MainGameCode() {
		this.setTitle("Campbell's Cuisine");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// set up menu screen
		setupContextPanel();
		
		// set up timer
		timer = new Timer(TIMERSPEED, this);
	}
	
	private void setupContextPanel() {
	    JPanel contextPanel = new JPanel();
	    contextPanel.setLayout(new BoxLayout(contextPanel, BoxLayout.Y_AXIS));
	    contextPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

	    title = new JLabel("Welcome to Campbell's Cuisine!");

	    context = new JTextArea("CONGRATULATIONS! You have been selected to solve the most devious "
	    		+ "ongoing crime in London, ON.\n London’s infamous “Cereal Killer”—not serial…CEREAL—has been "
	    		+ "attacking restaurants at random, killing chefs, patrons, waitresses, and many more. Secret intel "
	    		+ "points towards Campbell’s Cuisine as the Cereal Killer’s next stop; they will be there tonight.\n"
	    		+ "\n Your job is to unmask the killer before it’s too late. If you don’t act quickly enough, employees "
	    		+ "and customers will die. After anybody dies, you will have ONE 90-second round to search for hints, "
	    		+ "before the Cereal Killer will have covered up their tracks—and murdered another innocent creature!\n"
	    		+ "\n There will be 3 hints per round. A hint may exist in the form of a CLUE (a written envelope full "
	    		+ "of useful information), a RIDDLE (an object that belonged to the victim), or a GHOST (the victim’s "
	    		+ "vengeful spirit). After your 90 seconds are up, all hints will disappear and the Cereal Killer will "
	    		+ "find another victim (thereby starting a new round).\n\n To aid you in your endeavours, your "
	    		+ "detective agency has prepared a list of suspects (and descriptions). At any point in the game, you "
	    		+ "can arrest someone from the suspect list; however, if you’re wrong, you will have failed and "
	    		+ "another detective will replace you. THERE ARE NO SECOND CHANCES. On the other hand, the sooner you "
	    		+ "guess correctly, the better; you will be evaluated based on the number of people you let die. \n"
	    		+ "\n Quick! The restaurant’s dishboy, Donald Duck, has just been reported dead. Start looking for clues!");

	    context.setLineWrap(true);
	    context.setWrapStyleWord(true);
	    context.setPreferredSize(new Dimension(300, 150));

	    JScrollPane scrollPane = new JScrollPane(context);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	    nextButton = new JButton("NEXT");
	    nextButton.addActionListener(e -> goToMainPanel());

	    contextPanel.add(title);
	    contextPanel.add(Box.createVerticalStrut(10));
	    contextPanel.add(scrollPane);
	    contextPanel.add(Box.createVerticalStrut(10));
	    contextPanel.add(nextButton);

	    this.add(contextPanel);
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}


    private void goToMainPanel() {
        // remove context panel and show the main panel
        getContentPane().removeAll();
        setupMainPanel();
        validate();
        repaint();
    }
	
	private void setupMainPanel() {
		// set up main panel
		mainPanel = new JPanel();
		
		// drawing panel
		dp = new DrawingPanel();
		mainPanel.add(dp);
		
		// add background
		restaurantbg = loadImage("/full_background_black.png");
		
		// create characters
		Character po = new Character("Po", 21, "Chef", "A gluttonous but adorable panda.");
		
		// test
		po.setName("Protagonist");
		po.setImagePath("/po.png");
		JLabel character = new JLabel(po.getImageIcon());
		character.setBounds(50, 50, character.getWidth(), character.getHeight());
		this.add(character);
		
		action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				count--;
				if (count == 0) timer.stop();
			}
		};
		
		// pack, centre, display
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class DrawingPanel extends JPanel{

		DrawingPanel(){
			this.setPreferredSize(new Dimension(PANW, PANH));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			// draw background
			g2.drawImage(restaurantbg, 0, 0, PANW, PANH, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * loads an image from a file in the resource folder
	 * @param filename	name of the file
	 * @return	returns a BufferedImage connected to filename
	 */
	BufferedImage loadImage(String filename) {
		BufferedImage image = null;	
		java.net.URL imageURL = this.getClass().getResource(filename);
		
		if (imageURL != null) {
			try {
				image = ImageIO.read(imageURL);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { 
			JOptionPane.showMessageDialog(null, "An image failed to load: " + filename , "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

}
