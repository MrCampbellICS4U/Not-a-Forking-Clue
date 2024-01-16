/*
 * Character.java
 * By Emma Lane-Smith, Julie Pham, and Kelly Xiang
 * Jan 12, 2023
 * Class for character profiles: includes things like name, age, job, etc.
 */
package grade12;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Character {
	private String name, role;
	private int age;
	private String description;
	
	private String imagePath;  // path to character's image file
    private ImageIcon imageIcon;  // ImageIcon to store the character's image on JLabel later
	
	/**
	 * class constructor
	 * @param name	character's name
	 * @param age	character's age
	 * @param role	character's role/job
	 */
	public Character(String name, int age, String role, String description) {
		this.name = name;
		this.age = age;
		this.role = role;
		this.description = description;
	}
	
	/**
	 * getter method for character's name
	 * @return 	character's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * setter method for character's name
	 * @param name	character's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter method for character's role
	 * @return	character's role
	 */
	public String getRole() {
		return this.role;
	}
	
	/**
	 * setter method for character's role
	 * @param role	character's role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * getter method for character's age
	 * @return	character's age
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * setter method for character's age
	 * @param age	character's age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        loadImageIcon();  // call method to load the image when the path is set
    }
	
	/**
	 * loads an image from a file in the resource folder (but for an ImageIcon)
	 * @param filename	name of the file
	 * @return	returns a ImageIcon connected to filename
	 */
	private void loadImageIcon() {
	    ImageIcon icon = null;    
	    java.net.URL imageURL = this.getClass().getResource(imagePath);
	    
	    if (imageURL != null) {
	        try {
	            icon = new ImageIcon(imageURL);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "An image failed to load: " + imagePath, "ERROR", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    imageIcon = icon;
	}
}
