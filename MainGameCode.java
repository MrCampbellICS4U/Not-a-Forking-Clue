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
	private Round currentRound;
	
	// timer stuff
	Timer timer;
	int TIMERSPEED = 1000; // speed in seconds
	
	// image declaration
	BufferedImage restaurantbg;
	
	// panel width & height
	private final static int PANW = 1440;
	private final static int PANH = 1440;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGameCode();
			}
		});
	}
	
	public MainGameCode() {
		// set up window
		this.setTitle("Not a Forking Clue");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
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
		
		// pack, centre, display
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class DrawingPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		DrawingPanel(){
			this.setPreferredSize(new Dimension(PANW, PANH));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			// draw background
			g2.drawImage(restaurantbg, 0, 0, restaurantbg.getWidth(), restaurantbg.getHeight(), null);
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
