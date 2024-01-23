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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.io.*;

public class MainGameCode extends JFrame implements ActionListener{

	// panel width & height
	private final static int PANW = 1440 - 700;
	private final static int PANH = 1440 - 700;
		
	// player variables
	private static int playerX = 210; // initial X coordinate for Judy
	private static int playerY = PANH - 200; // initial Y coordinate for Judy
	private Rectangle judyBox = new Rectangle(playerX+28, playerY+40, 24, 35); // updated judyBox
	private int jsx1=0, jsy1 = 0, jsx2=2000, jsy2=2000; //source coordinates for Judy (to flip when turn)
	
	// variables to track whether messages are shown
    private boolean clueMessageShown = false;
    private boolean ghostMessageShown = false;
    private boolean riddleMessageShown = false;
	
	// create Character objects
	private Character po = new Character("Master Ping Xiao Po", 21, "Chef", "Master Ping Xiao Po has been the head "
			+ "chef at Campbell’s Cuisine for a few months now. He certainly has access to an arsenal of weapons…");
	private Character pooh = new Character("Winnie the Pooh", 24, "Host", "Winnie the Pooh is a very new addition to the "
			+ "staff at Campbell’s Cuisine…apparently he’s been hopping from job to job for the past several years. "
			+ "He’s never held a position higher than a host.");
	private Character jaq = new Character("Jaq", 19, "Patron", "Jaq loves to sample the finest restaurants in "
			+ "London, ON. Unfortunately—or perhaps, rather suspiciously—most of his favourite establishments have "
			+ "been struck by the Cereal Killer. Campbell’s Cuisine seems to be the last one standing…");
	private Character cheshire = new Character("Cheshire", 27, "Waiter", "The Cheshire Cat has been a waiter at "
			+ "Campbell’s Cuisine for many years now. He’s incredibly sly and devious…certainly capable of "
			+ "organized crime.");

	// create Hint Rectangles
	private Hint clue1 = new Hint("The fur found by the crime scene is fairly dark.");
	private Hint ghost1 = new Hint("Today is not a murder mystery. It’s a gift. That’s why it’s called the present.");
	private Riddle riddle1 = new Riddle("This is Master Oogway’s cherry blossom branch. \n"
			+ "The Cheshire Cat offers to tell you how it got there…if you can answer this riddle:\n\n"
			+ "Why is a raven like a drawing desk?\n", 
			"CORRECT! Master Ping Xiao Po found the branch lying in the hallway and decided it would make an excellent garnish for his meal.",
			"Because it can produce a few notes (though they are very flat)",
			"They have quills in common",
			"They are both black, if the desk is crafted from ebony",
			"Because in French all the letters in bureau are contained in corbeau");
	private Hint clue2 = new Hint("The floor is covered in juice.");
	private Hint ghost2 = new Hint("I have no idea who got me! I was making a drink for the Cheshire Cat when, suddenly, everything went dark…");
	private Riddle riddle2 = new Riddle("Jaq will tell you where Winnie the Pooh was before he died…if you can name what movie Jaq is from:\n",
			"CORRECT! The Cheshire Cat asked Winnie to grab him a drink… despite having just come from the bar himself.",
			"Snow White",
			"Alice in Wonderland",
			"Bambi",
			"Cinderella");
	private Hint clue3 = new Hint("The Cheshire Cat seems to be conspiring with Melman about something very important.");
	private Hint ghost3 = new Hint("Right before I died, someone pulled my beanie over my head! I could hear heavy footsteps…");
	private Riddle riddle3 = new Riddle("You seem to recall the Cheshire Cat mentioning something special about this beanie. \n"
			+ "Hmm… you might remember better if you can recall something else he said:\n"
			+ "\n"
			+ "Which of the following French words did the Cheshire Cat mention?\n",
			"CORRECT! Right after he asked you that silly riddle, the Cheshire Cat mentioned how badly he wanted that beanie for himself.",
			"Meurtrier",
			"Corbeau",
			"Couteau",
			"Cuisiner");
	private Rectangle clueRect1, ghostRect1, riddleRect1;
	private Rectangle clueRect2, ghostRect2, riddleRect2;
	private Rectangle clueRect3, ghostRect3, riddleRect3;
	
	// create Round objects
	private int round = 0;
	private Round round1 = new Round(clue1, ghost1, riddle1);
	private Round round2 = new Round(clue2, ghost2, riddle2);
	private Round round3 = new Round(clue3, ghost3, riddle3);
	
	// variables
	private JPanel mainPanel;
	private DrawingPanel dp;
	
	// timer stuff
	private Timer timer;
	private int TIMERSPEED = 1000; // speed in seconds
	private int roundTime = 10; // time for each round in seconds
	JLabel showTime; // shows the time
	int minutes, seconds;
	String filled = " "; // String to place on JLabel time
	
	// image declaration
	private BufferedImage restaurantbg, judyPlayer;
	
	// context panel components
	private JButton nextButton;
    private JLabel title;
    private JTextArea context;
    private JScrollPane scrollPane;
    
    // end panel components
    private JButton quitButton;
    private JRadioButton choice1, choice2, choice3, choice4;
    private ImageIcon poIcon, poohIcon, jaqIcon, cheshireIcon;
    private JButton guessButton;
    private JLabel right, wrong;
    private JButton nextButton2;
    private JPanel endPanel, radioPanel, buttonPanel;
    private int endPANW = 800;
    private int endPANH = 400;
    
    // endPanel imageicon labels + audio
    private ImageIcon stars0, stars1, stars2, stars3;
    private JLabel zeroStar, oneStar, twoStar, threeStar;
    private String songFilePath = "res/bgmusic.wav";
	private Clip clip;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGameCode();
			}
		});
	}//end main()
	
	/**
	 * Constructor
	 */
	public MainGameCode() {
		this.setTitle("Campbell's Cuisine");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// set up menu screen
		setupContextPanel();
		
		// set up timer
		timer = new Timer(TIMERSPEED, this);
	}//end MainGameCode()
	
	/**
	 * Set up context panel (explains background information before game)
	 */
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
	    		+ "\n=======================\nSUSPECT LIST:\n"
	    		+ "\n" + po.getDescription() + "\n"
	    		+ "\n" + jaq.getDescription() + "\n"
	    		+ "\n" + pooh.getDescription() + "\n"
	    		+ "\n" + cheshire.getDescription()
	    		+ "\n=======================\n"
	    		+ "\nQuick! The restaurant’s dishboy, Master Oogway, has just been reported dead. Start looking for clues!");

	    context.setLineWrap(true);
	    context.setWrapStyleWord(true);

	    scrollPane = new JScrollPane(context);
	    scrollPane.setPreferredSize(new Dimension(500,200));
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	    nextButton = new JButton("NEXT"); //start game
	    nextButton.addActionListener(new ActionListener(){ //anonymous actionListener to start game
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

	    // pack, center, display
	    this.add(contextPanel);
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}//end setupContextPanel()

	/**
	 * Start the game (display main game visuals)
	 */
    private void goToMainPanel() {
        // remove context panel and show the main panel
        getContentPane().removeAll();
        setupMainPanel();
        validate();
        repaint();
        timer.start();
    }//end goToMainPanel()
	
    /**
     * Set up main game visuals
     */
	private void setupMainPanel() {
		// set up main panel (on top of background)
		mainPanel = new JPanel();
		
		// drawing panel
		dp = new DrawingPanel();
		mainPanel.add(dp);
		
		//playSong(songFilePath);
		
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
	    
	    /*
	    // JLabel to show the amount filled
 		minutes = roundTime / 60;
        seconds = roundTime % 60;
        filled = String.format("%01d:%02d", minutes, seconds);
 		showTime = new JLabel(filled, SwingConstants.CENTER);
 		showTime.setAlignmentX(CENTER_ALIGNMENT);
 		mainPanel.add(showTime);*/
	    
	    /*
	     * Add Hint rectangles (for user to interact with)
	     * Coordinates and sizes saved in arrays in the Round class
	     */
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
		judyPlayer = loadImage("/res/judy.png");
		
		// pack, center, display
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	} //end setupMainPanel()
	
	/**
	 * End the game (display game-end visuals)
	 */
	private void goToEndPanel() {
        // stop timer
		timer.stop();
		
		// remove main panel and show the end panel
        getContentPane().removeAll();
        setupEndPanel();
        validate();
        repaint();
    }//end goToEndPanel()
	
	/**
	 * Set up game-end visuals
	 */
	private void setupEndPanel() {
	    endPanel = new JPanel();
	    endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
	    endPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
	    endPanel.setPreferredSize(new Dimension(endPANW, endPANH));
	    
	    title = new JLabel("Who is the Kitchen Killer?"); //prompt player to guess
	    
	    // set ImageIcons to use as radio buttons
	    po.setImagePath("/res/po.png");
	    poIcon = new ImageIcon();
	    poIcon = po.getImageIcon();
		pooh.setImagePath("/res/pooh.png");
	    poohIcon = new ImageIcon();
	    poohIcon = pooh.getImageIcon();
	    jaq.setImagePath("/res/jaq.png");
	    jaqIcon = new ImageIcon();
	    jaqIcon = jaq.getImageIcon();
	    cheshire.setImagePath("/res/cheshirecat.png");
	    cheshireIcon = new ImageIcon();
	    cheshireIcon = cheshire.getImageIcon();
	    
	    // create radio buttons
	    choice1 = new JRadioButton("", poIcon);
        choice2 = new JRadioButton("", poohIcon);
        choice3 = new JRadioButton("", jaqIcon);
        choice4 = new JRadioButton("", cheshireIcon);
        
        // labels to indicate whether user guess was correct
        right = new JLabel("Good job! You have successfully found the murderer.");
        wrong = new JLabel("You failed to find the murderer.");
        
        // labels to show user score
        stars0 = loadImageIcon("/res/zerostar.png");
		zeroStar = new JLabel(stars0);
		stars1 = loadImageIcon("/res/onestar.png");
		oneStar = new JLabel(stars1);
		stars2 = loadImageIcon("/res/twostar.png");
		twoStar = new JLabel(stars2);
		stars3 = loadImageIcon("/res/threestar.png");
		threeStar = new JLabel(stars3);

        ButtonGroup group = new ButtonGroup();
        group.add(choice1);
        group.add(choice2);
        group.add(choice3);
        group.add(choice4);
        
        // add guess button
        guessButton = new JButton("GUESS NOW");
        
        // add next button
        nextButton2 = new JButton("NEXT ROUND");
        
        // add quit button so user can exit game completely
        quitButton = new JButton("QUIT");
	    quitButton.addActionListener(new ActionListener(){
	    	@Override
            public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
            }
	    });
	    
	    // xext button moves on to the next round
	    nextButton2.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            resetRound();
	            goToMainPanel();
	        }
	    });

	    // create a panel for the radio buttons using GridLayout
	    radioPanel = new JPanel(new GridLayout(1, 4));
	    radioPanel.add(choice1);
	    radioPanel.add(choice2);
	    radioPanel.add(choice3);
	    radioPanel.add(choice4);

	    // create a panel for the buttons using FlowLayout
	    buttonPanel = new JPanel(new FlowLayout());
	    buttonPanel.add(guessButton);
	    if (round < 2) {
	        buttonPanel.add(nextButton2);
	    }
	    buttonPanel.add(quitButton);
        
        // add Action Listener so that user can guess who is murderer
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Check if a radio button is selected
                if (choice1.isSelected() || choice2.isSelected() || choice3.isSelected() || choice4.isSelected()) {
                    // If selected, hide other components
                	radioPanel.remove(choice1);
                    radioPanel.remove(choice2);
                    radioPanel.remove(choice3);
                    radioPanel.remove(choice4);
                    buttonPanel.remove(guessButton);
                    buttonPanel.remove(nextButton2);
                    buttonPanel.remove(quitButton);
                    
                    // Check which radio button is selected and display the label accordingly
                    if (round == 0 && choice1.isSelected()) {
                        endPanel.add(right);
                        endPanel.add(threeStar);
                    } else if (round == 1 && choice1.isSelected()) {
                        endPanel.add(right);
                        endPanel.add(twoStar);
                    } else if (round == 2 && choice1.isSelected()) {
                        endPanel.add(right);
                        endPanel.add(oneStar);
                    } else if (choice2.isSelected() || choice3.isSelected() || choice4.isSelected()) {
                        endPanel.add(wrong);
                        endPanel.add(zeroStar);
                    }

                    endPanel.add(quitButton);

                    // Repaint the container to reflect the changes
                    endPanel.revalidate();
                    endPanel.repaint();
                } else {
                    // If neither radio button is selected, display a warning message
                    JOptionPane.showMessageDialog(endPanel, "Please select a suspect before proceeding.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

	    // add components to the endPanel
	    endPanel.add(title);
	    endPanel.add(Box.createVerticalStrut(10));
	    endPanel.add(radioPanel);
	    endPanel.add(Box.createVerticalStrut(10));
	    endPanel.add(buttonPanel);

	    // pack, center, display
	    this.add(endPanel);
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
	
	/**
	 * Reset the timer and start the next round
	 */
	private void resetRound() {
		// reset timer and start the next round
	    roundTime = 90;
	    round++;
	    /*minutes = roundTime / 60;
        seconds = roundTime % 60;
        filled = String.format("%01d:%02d", minutes, seconds);
		showTime.setOpaque(true);
		showTime.setText(filled);*/
		dp.repaint();
		
		// set variables to be false again so that hints will appear
	    clueMessageShown = false;
	    ghostMessageShown = false;
	    riddleMessageShown = false;
	    
	    // restart song
		/*clip.stop();
		clip.setMicrosecondPosition(0); // rewind to the beginning
        clip.start();*/
	    
	    // restart timer
		timer.start();
	} //end resetRound()
	
	private class DrawingPanel extends JPanel {
		/**
		 * Constructor
		 */
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

	        //move judy according to player input
	        actionMap.put("left", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                movePlayer(-10, 0);
	                jsx1=0; //flip judy's view (turn left)
	                jsx2=2000; //"
	            }
	        });
	        actionMap.put("right", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                movePlayer(10, 0);
	                jsx1=2000; //flip judy's view (turn right)
	            	jsx2=0; //"
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
	    } //end DrawingPanel()

	    /**
	     * Move player according to given delta variables & checking for collisions
	     * @param deltaX	int number of x coordinates to move
	     * @param deltaY	int number of y coordinates to move
	     */
	    private void movePlayer(int deltaX, int deltaY) {
	        int ogX = playerX; //to check collisions with furniture
	        int ogY = playerY; //"
	        playerX += deltaX;
	        playerY += deltaY;
	        Barrier.checkWalls(playerX, playerY, ogX, ogY); //ensure within boundaries
	        judyBox.setLocation(playerX+28, playerY+40);
	        repaint();
	    }//end movePlayer(int, int)

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;

	        // draw background
	        g2.drawImage(restaurantbg, 0, 0, PANW, PANH, null);
	        
	        // draw timer bar
            g2.setPaint(Color.RED);
            int barLength = (int) (((double) roundTime / 100) * PANW);
            g2.fillRect(10, PANH - 10, barLength, 20);

	        // draw Judy
	        if (Barrier.judyIsVisible(playerX,playerY)==true) {
	        	g2.drawImage(judyPlayer, playerX, playerY, playerX+80, playerY+80, jsx1, jsy1, jsx2, jsy2, null);
	        }
	      
	        /*
	         * TEST BARRIER COORDINATES
	         */
	        //g2.setColor(Color.WHITE);
	        //g2.drawRect(311,310,28,29);
	        //g2.drawRect(357,310,20,29);
	        //g2.drawRect(134,127,22,27);
	        //g2.drawRect(413, 134, 10, 5);
	        //g2.drawRect(460, 259, 26, 26);
	        //g2.drawRect(253, 237, 27, 53);

	        // check for collisions with hints for round1
	        if (round == 0 && judyBox.intersects(clueRect1) && !clueMessageShown) {
	            showMessageDialog(null, clue1.getMessage());
	            clueMessageShown = true;
	        } else if (round == 0 && judyBox.intersects(ghostRect1) && !ghostMessageShown) {
	            showMessageDialog(null, ghost1.getMessage());
	            ghostMessageShown = true;
	        } else if (round == 0 && judyBox.intersects(riddleRect1) && !riddleMessageShown) {
	        	// make formatted message with answers displayed vertically
	            String message = riddle1.getPrompt();
	            for (int i = 0; i < riddle1.getAnswers().length; i++) {
	                message += (char) ('a' + i) + ". " + riddle1.getAnswers()[i] + "\n";
	            }

	            // show the option dialog with the formatted message
	            int choice = JOptionPane.showOptionDialog(
	                    null, message, "Riddle",
	                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
	                    null, riddle1.getAlpha(), null
	            );

	            // handle the user's choice
	            handleRiddleChoice(choice);

	            riddleMessageShown = true;
	        }
	        
	        // check for collisions with hints for round 2
	        if (round == 1 && judyBox.intersects(clueRect2) && !clueMessageShown) {
	            showMessageDialog(null, clue2.getMessage());
	            clueMessageShown = true;
	        } else if (round == 1 && judyBox.intersects(ghostRect2) && !ghostMessageShown) {
	            showMessageDialog(null, ghost2.getMessage());
	            ghostMessageShown = true;
	        } else if (round == 1 && judyBox.intersects(riddleRect2) && !riddleMessageShown) {
	            // make formatted message with answers displayed vertically
	            String message = riddle2.getPrompt();
	            for (int i = 0; i < riddle2.getAnswers().length; i++) {
	                message += (char) ('a' + i) + ". " + riddle2.getAnswers()[i] + "\n";
	            }

	            // show the option dialog with the formatted message
	            int choice = JOptionPane.showOptionDialog(
	                    null, message, "Riddle",
	                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
	                    null, riddle2.getAlpha(), null
	            );

	            // handle the user's choice
	            handleRiddleChoice(choice);

	            riddleMessageShown = true;
	        }
	        
	        // check for collisions with hints for round 3
	        if (round == 2 && judyBox.intersects(clueRect3) && !clueMessageShown) {
	            showMessageDialog(null, clue3.getMessage());
	            clueMessageShown = true;
	        } else if (round == 2 && judyBox.intersects(ghostRect3) && !ghostMessageShown) {
	            showMessageDialog(null, ghost3.getMessage());
	            ghostMessageShown = true;
	        } else if (round == 2 && judyBox.intersects(riddleRect3) && !riddleMessageShown) {
	            // make formatted message with answers displayed vertically
	            String message = riddle3.getPrompt();
	            for (int i = 0; i < riddle3.getAnswers().length; i++) {
	                message += (char) ('a' + i) + ". " + riddle3.getAnswers()[i] + "\n";
	            }

	            // show the option dialog with the formatted message
	            int choice = JOptionPane.showOptionDialog(
	                    null, message, "Riddle",
	                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
	                    null, riddle3.getAlpha(), null
	            );

	            // handle the user's choice
	            handleRiddleChoice(choice);

	            riddleMessageShown = true;
	        }
	        
	        // when user interacts with messagedialog, repaint the screen
            SwingUtilities.invokeLater(() -> repaint());
        
	    } //end paintComponent(Graphics)
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		roundTime--; //timer ticks
		if (roundTime == 0) {
			//clip.stop();
			goToEndPanel(); //end game
			timer.stop();
		}
	}//end actionPerformed(ActionEvent)
	
	/**
	 * Handle the user's choice for the riddle
	 * @param choice	int choice for whichever answer the player picked
	 */
	private void handleRiddleChoice(int choice) {
		// Round 1
	    if (round == 0) {
	        if (choice == 3) { // Option 'a'
	            showMessageDialog(null, riddle1.getMessage());
	        } else {
	            showMessageDialog(null, "INCORRECT! The Cheshire Cat frowns eerily at you.");
	        }
	    }
	    // Round 2
	    if (round == 1) {
	        if (choice == 0) { // Option 'd'
	            showMessageDialog(null, riddle2.getMessage());
	        } else {
	            showMessageDialog(null, "INCORRECT…\n"
	                    + "Jaq doesn’t remember!\n");
	        }
	    }
	    // Round 3
	    if (round == 2) {
	        if (choice == 2) { // Option 'b'
	            showMessageDialog(null, riddle3.getMessage());
	        } else {
	            showMessageDialog(null, "INCORRECT…\n"
	                    + "You have no recollection of what he said.\n");
	        }
	    }
	}//end handleRiddleChoice(int)
	
	/**
	 * Display pop-up message
	 * @param title		String title for pop-up
	 * @param message	String message for pop-up to display
	 */
	private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        this.repaint();
	}//end showMessageDialog(String, String)
	
	/**
     * try playing the WAV file
     */
    public void playSong(String soundName) {
    	try {
    		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
    		clip = AudioSystem.getClip();
    		clip.open(audioInputStream);
    		clip.start();
    	} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
    		System.out.println("Error with playing sound.");
    		ex.printStackTrace( );
    	}
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
	
	/**
	 * loads an image from a file in the resource folder (but for an ImageIcon)
	 * @param filename	name of the file
	 * @return	returns a ImageIcon connected to filename
	 */
	ImageIcon loadImageIcon(String filename) {
	    ImageIcon icon = null;    
	    java.net.URL imageURL = this.getClass().getResource(filename);
	    
	    if (imageURL != null) {
	        try {
	            icon = new ImageIcon(imageURL);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "ERROR", JOptionPane.ERROR_MESSAGE);
	    }
	    return icon;
	}
	
	/**
	 * Updates playerX (when accounting for collisions in Barrier class)
	 * @param x		int coordinate to set 'x' at
	 */
	public static void setPlayerX(int x) {
		playerX=x;
	}//end setPlayerX(int)
	
	/**
	 * Updates playerY (when accounting for collisions in Barrier class)
	 * @param y		int coordinate to set 'y' at
	 */
	public static void setPlayerY(int y) {
		playerY=y;
	}//end setPlayerY(int)

}
