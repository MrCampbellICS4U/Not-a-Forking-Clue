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
	private String name = "Judy Hopps";
	private int playerX = 210; // initial X coordinate for Judy
	private int playerY = PANH - 200; // initial Y coordinate for Judy
	private Rectangle judyBox = new Rectangle(playerX, playerY, 100, 100); // updated judyBox
	
	// create Character objects
	private Character po = new Character("Po", 21, "Chef", "A gluttonous but adorable panda.");
	private Character tramp = new Character("Tramp", 27, "Waiter", "A lovesick dog.");
	
	// create Hint Rectangles
	private Hint clue1 = new Hint("The fur found by the crime scene is fairly dark.");
	private Hint ghost1 = new Hint("Today is not a murder mystery. It’s a gift. That’s why it’s called the present.");
	private Riddle riddle1 = new Riddle("This is Master Oogway’s cherry blossom branch. The Cheshire Cat offers to tell you how it got there…if you can answer this riddle:\n"
			+ "\n"
			+ "Why is a raven like a drawing desk?\n", "Master Ping Xiao Po found the branch lying in the hallway and decided it would make an excellent garnish for his meal.",
			"Because it can produce a few notes (though they are very flat)",
			"They have quills in common",
			"They are both black, if the desk is crafted from ebony",
			"Because in French all the letters in bureau are contained in corbeau");
	private Hint clue2 = new Hint("The floor is covered in juice.");
	private Hint ghost2 = new Hint("I have no idea who got me! I was making a drink for the Cheshire Cat when, suddenly, everything went dark…");
	private Riddle riddle2 = new Riddle("Jaq will tell you where Winnie the Pooh was before he died…if you can name what movie Jaq is from:\n",
			"Snow White",
			"Alice in Wonderland",
			"Bambi",
			"Cinderella",
			"The Cheshire Cat asked Winnie to grab him a drink… despite having just come from the bar himself.");
	private Hint clue3 = new Hint("The Cheshire Cat seems to be conspiring with Melman about something very important.");
	private Hint ghost3 = new Hint("Right before I died, someone pulled my beanie over my head! I could hear heavy footsteps…");
	private Riddle riddle3 = new Riddle("You seem to recall the Cheshire Cat mentioning something special about this beanie. Hmm… you might remember better if you can recall something else he said:\n"
			+ "\n"
			+ "Which of the following French words did the Cheshire Cat mention?\n",
			"Meurtrier",
			"Corbeau",
			"Couteau",
			"Cuisiner",
			"Right after he asked you that silly riddle, the Cheshire Cat mentioned how badly he wanted that beanie for himself.");
	private Rectangle clueRect1, ghostRect1, riddleRect1;
	private Rectangle clueRect2, ghostRect2, riddleRect2;
	private Rectangle clueRect3, ghostRect3, riddleRect3;
	
	// create Round objects
	private int round = 0;
	private Round round1 = new Round(clue1, ghost1, riddle1);
	private Round round2;
	private Round round3;
	
	// variables
	private JPanel mainPanel;
	private DrawingPanel dp;
	
	// timer stuff
	private Timer timer;
	private int TIMERSPEED = 1000; // speed in seconds
	private int count;
	private int roundTime = 90; // time for each round
	
	// image declaration
	private BufferedImage restaurantbg, judyPlayer;
	
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
    private JButton guessButton;
    private JLabel right, wrong;
    private JButton nextButton2;
	
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
	    		+ "\nQuick! The restaurant’s dishboy, Master Oogway, has just been reported dead. Start looking for clues!");

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
		
		// load different backgrounds based on the current round
	    switch (round) {
	        case 0:
	            restaurantbg = loadImage("/res/round_1_background.png");
	            break;
	        case 1:
	            restaurantbg = loadImage("/res/round_2_background.png");
	            break;
	        case 2:
	            restaurantbg = loadImage("/res/round_3_background.png");
	            break;
	    }
	    
	    // add clue Rectangles
	    switch (round) {
	    	case 0: 
	    		clueRect1 = new Rectangle(round1.getClueX(round), round1.getClueY(round), round1.getClueW(round), round1.getClueH(round));
	    		ghostRect1 = new Rectangle(round1.getGhostX(round), round1.getGhostY(round), round1.getGhostW(round), round1.getGhostH(round));
	    		riddleRect1 = new Rectangle(round1.getRiddleX(round), round1.getRiddleY(round), round1.getRiddleW(round), round1.getGhostH(round));
	    		break;
	    	case 1:
	    		clueRect2 = new Rectangle(round2.getClueX(round), round2.getClueY(round), round2.getClueW(round), round2.getClueH(round));
	    		ghostRect2 = new Rectangle(round2.getGhostX(round), round2.getGhostY(round), round2.getGhostW(round), round2.getGhostH(round));
	    		riddleRect2 = new Rectangle(round2.getRiddleX(round), round2.getRiddleY(round), round2.getRiddleW(round), round2.getGhostH(round));
	    		break;
	    	case 2:
	    		clueRect3 = new Rectangle(round3.getClueX(round), round3.getClueY(round), round3.getClueW(round), round3.getClueH(round));
	    		ghostRect3 = new Rectangle(round3.getGhostX(round), round3.getGhostY(round), round3.getGhostW(round), round3.getGhostH(round));
	    		riddleRect3 = new Rectangle(round3.getRiddleX(round), round3.getRiddleY(round), round3.getRiddleW(round), round3.getGhostH(round));
	    		break;
	    }
		
		// add player
		judyPlayer = loadImage("/res/judy_three_quarters.png");
		
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
	    
	    title = new JLabel("Who was the murderer?");
	    
	    // set ImageIcons to use as radio buttons
	    po.setImagePath("/res/po.png");
		tramp.setImagePath("/res/tramp.png");
	    poIcon = new ImageIcon();
	    poIcon = po.getImageIcon();
	    trampIcon = new ImageIcon();
	    trampIcon = tramp.getImageIcon();
	    
	    // create radio buttons
	    choice1 = new JRadioButton("Po", poIcon);
        choice2 = new JRadioButton("Tramp", trampIcon);
        
        // labels to indicate whether user guess was correct
        right = new JLabel("You guessed right!");
        wrong = new JLabel("You guessed wrong!");

        ButtonGroup group = new ButtonGroup();
        group.add(choice1);
        group.add(choice2);
        
        // add guess button
        guessButton = new JButton("GUESS");
        
        // add next button
        nextButton2 = new JButton("NEXT");
        
        // add Action Listener so that user can guess who is murderer
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// check if a radio button is selected
            	if (choice1.isSelected() || choice2.isSelected()) {
                    // if selected, remove from view
                    endPanel.remove(choice1);
                    endPanel.remove(choice2);
                    endPanel.remove(guessButton);
                    endPanel.remove(nextButton2);
                    endPanel.remove(quitButton);

                    // repaint the container to reflect above changes
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
                } else {
                    // if neither radio button is selected, display a mildly threatening message
                    JOptionPane.showMessageDialog(endPanel, "Please select a suspect before proceeding.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

	    // add quit button so user can exit game completely
        quitButton = new JButton("QUIT");
	    quitButton.addActionListener(new ActionListener(){
	    	@Override
            public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
            }
	    });
	    
	    // next button moves on to next round
	    nextButton2.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			round++;
                resetRound();
                goToMainPanel();
    		}
    	});

	    endPanel.add(title);
	    endPanel.add(Box.createVerticalStrut(10));
	    endPanel.add(choice1);
        //endPanel.add(choice2);
        endPanel.add(Box.createVerticalStrut(10));
        endPanel.add(guessButton);
        // only allow user to click next button if there's still another round; they must guess on round 3
        if (round < 2) {
        	endPanel.add(Box.createVerticalStrut(10));
        	endPanel.add(nextButton2);
        }
	    endPanel.add(Box.createVerticalStrut(10));
	    endPanel.add(quitButton);

	    this.add(endPanel);
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
	
	private void resetRound() {
	    // reset timer and start the next round
	    roundTime = 90;
	    timer.restart();
	}
	
	private class DrawingPanel extends JPanel {
	    DrawingPanel() {
	        // set preferred size + focus component
	        this.setPreferredSize(new Dimension(PANW, PANH));
	        this.setFocusable(true);

	        // register key bindings
	        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
	        ActionMap actionMap = getActionMap();

	        // left, right, up, down buttons
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");

	        // add ability to use WASD buttons as well
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");

	        actionMap.put("left", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                movePlayer(-10, 0);
	            }
	        });
	        actionMap.put("right", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                movePlayer(10, 0);
	            }
	        });
	        actionMap.put("up", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                movePlayer(0, -10);
	            }
	        });
	        actionMap.put("down", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                movePlayer(0, 10);
	            }
	        });
	    }

	    private void movePlayer(int deltaX, int deltaY) {
	        int newPlayerX = playerX + deltaX;
	        int newPlayerY = playerY + deltaY;

	        // Check if the new position is within the boundaries
	        if (newPlayerX >= 0 && newPlayerX + judyBox.width <= PANW && newPlayerY >= 0 && newPlayerY + judyBox.height <= PANH) {
	            // Update player position only if within boundaries
	            playerX = newPlayerX;
	            playerY = newPlayerY;
	            judyBox.setLocation(playerX, playerY);
	            repaint();
	        }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;

	        // draw background
	        g2.drawImage(restaurantbg, 0, 0, PANW, PANH, null);

	        // draw Judy
	        g2.drawImage(judyPlayer, playerX, playerY, 100, 100, null);
	        
	        g2.setColor(Color.WHITE);
	        g2.drawRect(0, 0, 100, 100);
	        
	        // check for collisions with hints
	        if (judyBox.intersects(clueRect1)) {
	            showMessageDialog(null, clue1.getMessage());
	        } else if (judyBox.intersects(ghostRect1)) {
	            showMessageDialog(null, ghost1.getMessage());
	        } else if (judyBox.intersects(riddleRect1)) {
	            //int choice = showConfirmDialog(null, "Answer the riddle:", "Riddle", YES_NO_OPTION);
	            // Handle the riddle interaction based on the user's choice
	        }
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		roundTime--;
		if (roundTime == 0) {
			goToEndPanel();
			timer.stop();
		}
	}
	
	private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
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
