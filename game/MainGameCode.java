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

	// player variables
	private String name = "Judy";
	private Rectangle judyBox;
	
	// create Character objects
	Character po = new Character("Po", 21, "Chef", "A gluttonous but adorable panda.");
	Character tramp = new Character("Tramp", 27, "Waiter", "A lovesick dog.");
	
	// variables
	private JPanel mainPanel;
	private DrawingPanel dp;
	private int round = 0;
	private Round currentRound;
	
	// timer stuff
	private Timer timer;
	private ActionListener action;
	private int TIMERSPEED = 1000; // speed in seconds
	private int count;
	private int roundTime = 5; // time for each round
	
	// image declaration
	private BufferedImage restaurantbg;
	
	// context panel components
	private JButton nextButton;
    private JLabel title;
    private JTextArea context;
    private JScrollPane scrollPane;
    
    // end panel components
    private JButton quitButton;
    private JRadioButton choice1, choice2;
    private ImageIcon poIcon;
    private ImageIcon trampIcon;
    private JButton okayButton;
    private JLabel right, wrong;
	
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
		action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//count--;
				roundTime--;
				System.out.print(roundTime);
				if (roundTime == 0) {
					//timer.stop();
					goToEndPanel();
					timer.stop();
				}
			}
		};
		timer = new Timer(TIMERSPEED, action);
	}
	
	private void setupContextPanel() {
	    JPanel contextPanel = new JPanel();
	    contextPanel.setLayout(new BoxLayout(contextPanel, BoxLayout.Y_AXIS));
	    contextPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

	    title = new JLabel("Welcome to Campbell's Cuisine!");

	    context = new JTextArea("CONGRATULATIONS! You have been selected to solve the most devious "
	    		+ "ongoing crime in London, ON.\n\nLondon’s infamous “Cereal Killer”—not serial…CEREAL—has been "
	    		+ "attacking restaurants at random, killing chefs, patrons, waitresses, and many more. Secret intel "
	    		+ "points towards Campbell’s Cuisine as the Cereal Killer’s next stop; they will be there tonight.\n"
	    		+ "\nYour job is to unmask the killer before it’s too late. If you don’t act quickly enough, employees "
	    		+ "and customers will die. After anybody dies, you will have ONE 90-second round to search for hints, "
	    		+ "before the Cereal Killer will have covered up their tracks—and murdered another innocent creature!\n"
	    		+ "\nThere will be 3 hints per round. A hint may exist in the form of a CLUE (a written envelope full "
	    		+ "of useful information), a RIDDLE (an object that belonged to the victim), or a GHOST (the victim’s "
	    		+ "vengeful spirit). After your 90 seconds are up, all hints will disappear and the Cereal Killer will "
	    		+ "find another victim (thereby starting a new round).\n\nTo aid you in your endeavours, your "
	    		+ "detective agency has prepared a list of suspects (and descriptions). At any point in the game, you "
	    		+ "can arrest someone from the suspect list; however, if you’re wrong, you will have failed and "
	    		+ "another detective will replace you. THERE ARE NO SECOND CHANCES. On the other hand, the sooner you "
	    		+ "guess correctly, the better; you will be evaluated based on the number of people you let die. \n"
	    		+ "\nQuick! The restaurant’s dishboy, Donald Duck, has just been reported dead. Start looking for clues!");

	    context.setLineWrap(true);
	    context.setWrapStyleWord(true);

	    scrollPane = new JScrollPane(context);
	    scrollPane.setPreferredSize(new Dimension(500,200));
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	    nextButton = new JButton("NEXT");
	    nextButton.addActionListener(new ActionListener(){
	    	@Override
            public void actionPerformed(ActionEvent e) {
                goToMainPanel();
            }
	    });

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
        timer.start();
    }
	
	private void setupMainPanel() {
		// set up main panel (on top of background)
		mainPanel = new JPanel();
		
		// drawing panel
		dp = new DrawingPanel();
		mainPanel.add(dp);
		
		// add background
		restaurantbg = loadImage("/full_background_black.png");
		
		JButton hi = new JButton("hi");
		mainPanel.add(hi);
		hi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEndPanel();
			}
		});
		
		// pack, centre, display
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void goToEndPanel() {
        // remove main panel and show the end panel
        getContentPane().removeAll();
        setupEndPanel();
        validate();
        repaint();
    }
	
	private void setupEndPanel() {
	    JPanel endPanel = new JPanel();
	    endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
	    endPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
	    //endPanel.setBackground(Color.BLACK);

	    // set all text to be white
	    /*title.setForeground(Color.WHITE);
	    right.setForeground(Color.WHITE);
	    wrong.setForeground(Color.WHITE);*/
	    
	    title = new JLabel("Who was the murderer?");
	    
	    // set ImageIcons to use as radio buttons
	    po.setImagePath("/po.png");
		tramp.setImagePath("/tramp.png");
	    poIcon = new ImageIcon();
	    poIcon = po.getImageIcon();
	    trampIcon = new ImageIcon();
	    trampIcon = tramp.getImageIcon();
	    
	    // create radio buttons
	    choice1 = new JRadioButton("Po", poIcon);
        choice2 = new JRadioButton("Tramp", trampIcon);
        
        right = new JLabel("You guessed right!");
        wrong = new JLabel("You guessed wrong!");

        ButtonGroup group = new ButtonGroup();
        group.add(choice1);
        group.add(choice2);

        okayButton = new JButton("NEXT");
        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // regardless of what button is pressed, remove choices from screen after they guess
            	endPanel.remove(choice1);
                endPanel.remove(choice2);
                endPanel.remove(okayButton);
                endPanel.remove(quitButton);

                // repaint the container to reflect changes
                endPanel.revalidate();
                endPanel.repaint();
            	
            	// check which radio button is selected and display label accordingly
                if (choice1.isSelected()) {
                    endPanel.add(right);
                    endPanel.add(quitButton);
                } else if (choice2.isSelected()) {
                    endPanel.add(wrong);
                    endPanel.add(quitButton);
                }
            }
        });

	    quitButton = new JButton("QUIT");
	    quitButton.addActionListener(new ActionListener(){
	    	@Override
            public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
            }
	    });

	    endPanel.add(title);
	    endPanel.add(Box.createVerticalStrut(10));
	    endPanel.add(choice1);
        //endPanel.add(choice2);
        endPanel.add(Box.createVerticalStrut(10));
        endPanel.add(okayButton);
	    endPanel.add(Box.createVerticalStrut(10));
	    endPanel.add(quitButton);

	    this.add(endPanel);
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
