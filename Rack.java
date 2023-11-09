/*
* Project: Scrabble
* Package: scrabble
* Class: Rack
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: the rack class is an array of letters, and has an in play letter
* varaible, this varaible can be set to the specific letter that you press on your rack. 
* The rack holds all of the players letters and allows the user to play the letters 
* */
package scrabble;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Rack extends JPanel {

	// constants
	private static final int WIDTH = 300;
	private static final int HEIGHT = 50;
	// make constant dimension using constant width and height
	private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);

	// layout
	private LayoutManager myLayout;

	// attributes
	private Pile pile;
	private Letter inHand;
	private Letter[] letArr;

	Rack(Pile p)// constructor, that takes a pile as a parameter
	{
		this.pile = p; // set the pile
		this.inHand = null; // set in hand to null
		this.setSize(dimension); // set the size to the dimension
		this.letArr = new Letter[7]; // create the letter array
		this.myLayout = new GridLayout(1, 7, 1, 1);
		this.setLayout(this.myLayout);
		for (int i = 0; i < 7; i++) {
			this.letArr[i] = this.pile.pickupTile();
			this.letArr[i].setRack(this); // when you add a letter to a rack set its rack to this rack
			this.letArr[i].setRackIndex(i);
			System.out.println(letArr[i].getChar());
			this.add(this.letArr[i], i); // this puts them in the right spot in grid layout bc it goes across row first
											// then down
		}
	}

	public Letter getLetter(int i) { // method that returns the letter at the passed index
		return this.letArr[i];
	}

	public void setInHand(Letter l) { // method that sets the in hand letter to the passed letter
		this.inHand = l;
	}

	public Letter getInHand() { // method that returns the in hand letter
		return this.inHand;
	}

	public int getInHandIndex() { // method that returns the inhand index
		return this.inHand.getRackIndex();
	}

	public void fillRack() { // method that fills the rack

		// if you get to a spot in the rack where there is not letter
		// than pickup a tile and add it to the array
		// loop thru array til you find a nill spot then fill it by callign pickup tile
		// , continue until rack is full
		for (int i = 0; i < this.letArr.length; i++) {
			if (this.letArr[i] == null) {
				this.letArr[i] = this.pile.pickupTile();
				this.add(this.letArr[i]);
				this.letArr[i].setRack(this);
				this.letArr[i].setVisible(true);
				this.letArr[i].setRackIndex(i);
			}
		}
	}

	public void insertLetter(Letter l) { // method that inserts a letter into the rack

		// if you get to a spot in the rack where there is not letter
		// "insert" the letter into that spot and set inserted to true so that it exits
		// the loop
		boolean letterInserted = false;
		for (int i = 0; i < this.letArr.length && !letterInserted; i++) {
			if (this.letArr[i] == null) {
				this.letArr[i] = l;
				this.add(this.letArr[i]);
				this.letArr[i].setRackIndex(i);
				this.letArr[i].setRack(this);
				this.letArr[i].setVisible(true);
				letterInserted = true;
			}
		}
	}

	public void removeLetter() { // method for removing a letter
		this.remove(this.letArr[this.getInHandIndex()]); // remove the letter of the in hand index from the array
		this.letArr[this.getInHandIndex()] = null; // set it to null
		this.repaint(); // repaint
	}

	public boolean checkIfEmpty() { // method to check if the rack is empty

		boolean empty = true; // boolean

		for (int i = 0; i < this.letArr.length; i++) { // loop thru
			if (this.letArr[i] != null) {
				empty = false; // if any spot does not equal null, set emtpty to false
			}
		}

		return empty; // return the boolean
	}
}
