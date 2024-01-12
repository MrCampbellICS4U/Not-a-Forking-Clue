/*
 * MainGameCode.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 11, 2024
 * Main code for Not a Forking Clue
 */
package isp;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

public class MainGameCode extends JFrame implements ActionListener{

	// declare variables
	JPanel mainPanel;
	DrawingPanel dp;
	JButton next;
	
	private final static int PANW = 800;
	private final static int PANH = 500;
	
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
		
		// set up main panel
		mainPanel = new JPanel();
		//mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// drawing panel
		dp = new DrawingPanel();
		mainPanel.add(dp);
		
		// initialize and add button
		next = new JButton("Hi");
		mainPanel.add(next);
		
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
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
