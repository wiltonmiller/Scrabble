/*
* Project: Scrabble
* Package: scrabble
* Class: Letter
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: lettter that has a rack a score and a string, these letter also extend
* JButton and implement thier own action listener. They are created in a pile and are used 
* to fil the players racks and make words
* */
package scrabble;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Letter extends JButton implements ActionListener {

	// attributes of the letter class
	private int score;
	private int rackIndex;
	private String letter;
	private ImageIcon letterImage;
	private Rack rack; // reference to rack it belongs to

	// declare constants
	private final int WIDTH = 25;
	private final int HEIGHT = 25;

	Letter(int s, String c, ImageIcon i) // letter constructor
	{

		// takes int score, string character, image icon as parameter

		// assigns the score, lettter and image
		this.score = s;
		this.letter = c;
		this.letterImage = i;
		this.rack = null; // sets the rack to null initially
		this.addActionListener(this); // add actionlistener
		this.setIcon(this.letterImage); // set the icon to the image
		this.setSize(new Dimension(this.WIDTH, this.HEIGHT)); // set the size to constants
	}

	Letter() // letter constructor that creates a letter with no paramaters
	{

	}

	Letter(Letter l) // letter that creates a letter from another letter
	{
		this.score = l.score; // set the letters score to the parameters score, letter to its letter and image
								// to its image
		this.letter = l.letter;
		this.letterImage = l.letterImage;
		this.addActionListener(this); // add actionlistener
		this.setIcon(l.letterImage); // set icon
		this.setSize(new Dimension(this.WIDTH, this.HEIGHT)); // set size
	}

	public int getValue() { // method that returns the value or score of letter
		return this.score;
	}

	public ImageIcon getImage() { // method that returns the image
		return this.letterImage;
	}

	public void nullImage() { // method that gets rid of the image

		this.setIcon(null);
	}

	public String getChar() { // method that returns the letter/char
		return this.letter;
	}

	public void setChar(String s) // methd that sets the character/string to a passed string
	{
		this.letter = s;
	}

	public void setImage(ImageIcon i) // method that sets the image to a passed image
	{
		this.letterImage = i;
	}

	public void setRackIndex(int n) { // method that sets the rack index to a passed int
		this.rackIndex = n;
	}

	public int getRackIndex() { // method that returns the rack index
		return this.rackIndex;
	}

	public void setRack(Rack r) { // method that sets the rack to a passed rack
		this.rack = r;
	}

	public Rack getRack() { // methods that returns the rack
		return this.rack;
	}

	@Override
	public void actionPerformed(ActionEvent e) { // actionperformed for when a letter button is pressed
		// TODO Auto-generated method stub

		this.rack.setInHand(this); // set the in hand letter for the rack
									// to this letter

	}

}
