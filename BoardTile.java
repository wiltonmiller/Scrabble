/*
* Project: Scrabble
* Package: scrabble
* Class: BoardTile
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: extends JButton and implements its own actionlistener. Can contains a letter
* and is mainly used when building words and putting letters on the board
* */
package scrabble;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoardTile extends JButton implements ActionListener {

	// attributes
	private Letter letter;
	private Board board;
	private int r;
	private int c;
	boolean containsLetter;
	boolean inPlay;
	private String name;

	// image icons for the special tiles
	static ImageIcon tripleWord = new ImageIcon("tripleword.PNG");
	static ImageIcon doubleWord = new ImageIcon("doubleword.PNG");
	static ImageIcon tripleLetter = new ImageIcon("tripleletter.PNG");
	static ImageIcon doubleLetter = new ImageIcon("doubleletter.PNG");
	static ImageIcon start = new ImageIcon("start.PNG");

	BoardTile(int r, int c, Board b) // constructor for boardtile , that takes row and column int
	{
		this.letter = null; // set the letter
		this.board = b; // set the board , row and column
		this.r = r; // set the r
		this.c = c; // set the c
		this.containsLetter = false; // set contains letter
		this.inPlay = false; // set in play
		this.setSize(35, 36); // set the size of the board tile
		this.addActionListener(this); // add an action listener to the button

		if (r % 7 == 0 && c % 7 == 0 && !(r == 7 && c == 7)) { // depending on what row or column youre in set the board
																// tiles name to
			// triple word and set the image of the board tile to triple word
			this.name = "tripleword"; // set the name
			this.setIcon(tripleWord); // set the icon
		} else if (r == 7 && c == 7) { // depending on what row or column youre in set the board tiles name to
			// start and set the image of the board tile to start
			this.name = "start"; // set the name
			this.setIcon(start); // set the icon
		} else if (((c > 0 && c < 5) || (c > 9 && c < 14)) && (c == r || c == 14 - r || r == 14 - c)) { // depending on
																										// what row or
																										// column youre
																										// in set the
																										// board tiles
																										// name to
			// double and set the image of the board tile to double word
			this.name = "doubleword"; // set the name
			this.setIcon(doubleWord); // set the letter
		}

		else if (((r == 0 || r == 14) && (c == 3 || c == 11)) || ((r == 2 || r == 12) && (c == 6 || c == 8))
				|| ((r == 3 || r == 11) && (c == 0 || c == 7 || c == 14))
				|| ((r == 6 || r == 8) && (c == 2 || c == 6 || c == 8 || c == 12)) || ((r == 7) && (c == 3 || c == 11))) // depending
																															// on
																															// what
																															// row
																															// or
																															// column
																															// youre
																															// in
																															// set
																															// the
																															// board
																															// tiles
																															// name
																															// to
		// dobule letter and set the image of the board tile to double letter
		{
			this.name = "doubleletter"; // set the name
			this.setIcon(doubleLetter); // set the icon
		} else if (((r == 5 || r == 9) && (c == 1 || c == 5 || c == 9 || c == 13))
				|| ((r == 13 || r == 1) && (c == 5 || c == 9))) // depending on what row or column youre in set the
																// board tiles name to
		// triple letter and set the image of the board tile to triple letter
		{
			this.name = "tripleletter"; // set the name
			this.setIcon(tripleLetter); // set the icon
		} else {
			this.name = "normal"; // else set the name to normal
		}

	}

	public Letter getLetter() { // reutnrh the letter for the board tile
		return this.letter;
	}

	// method to set the of the board tile
	public void setLetter(Letter l) { // take in a letter as the paramenter
		this.letter = l; // set the letter to l
		this.containsLetter = true; // set the contains letter to true
		this.inPlay = true; // set in play to true
		this.setIcon(l.getIcon()); // the the icon of the boardtile to the icon of the letter
	}

	// method to reset the tile
	public void resetTile() {

		// depending on what row or column ur in set the board tiles name to
		// triple word and set the image of the board tile to triple word

		// if the the tile contains a letter and it is in play then reset it
		if (this.containsLetter && this.inPlay) {
			this.letter = null; // set the letter to null
			this.containsLetter = false; // set contains to letter to false
			this.inPlay = false; // set in play to false

			this.setIcon(null); // set the icon to null

			// these if statements are the same as used previous for the patterns of
			// assigning the "special" spaces if it needs to be reset to a special space
			// which sets the proper name and image
			if (r % 7 == 0 && c % 7 == 0 && !(r == 7 && c == 7)) {
				this.name = "tripleword";
				this.setIcon(tripleWord);
			} else if (r == 7 && c == 7) {
				this.name = "start";
				this.setIcon(start);
			} else if (((c > 0 && c < 5) || (c > 9 && c < 14)) && (c == r || c == 14 - r || r == 14 - c)) {
				this.name = "doubleword";
				this.setIcon(doubleWord);
			}

			else if (((r == 0 || r == 14) && (c == 3 || c == 11)) || ((r == 2 || r == 12) && (c == 6 || c == 8))
					|| ((r == 3 || r == 11) && (c == 0 || c == 7 || c == 14))
					|| ((r == 6 || r == 8) && (c == 2 || c == 6 || c == 8 || c == 12))
					|| ((r == 7) && (c == 3 || c == 11))) {
				this.name = "doubleletter";
				this.setIcon(doubleLetter);
			} else if (((r == 5 || r == 9) && (c == 1 || c == 5 || c == 9 || c == 13))
					|| ((r == 13 || r == 1) && (c == 5 || c == 9))) {
				this.name = "tripleletter";
				this.setIcon(tripleLetter);
			} else {
				this.name = "normal";
			}

		}
	}

	public String getName() { // method that returns the name of the boardtile
		return this.name;
	}

	public int getColumn() {// method that returns the column
		return this.c;
	}

	public int getRow() { // method that returns the row
		return this.r;
	}

	public boolean isInPlay() { // method that returns if it is in play or not
		return this.inPlay;
	}

	public void setNotInPlay() { // method to set in play to flase
		this.inPlay = false;
	}

	public void setInPlay() { // method that sets in play to true
		this.inPlay = true;
	}

	public boolean containsLetter() { // method that returns whether it contains a letter
		return this.containsLetter;
	}

	public boolean isUsed() { // method thatreturns if it is used or not
		if (this.isInPlay()) // the tile cannot be used it is curretnly in play
		{
			return false;
		}
		if (!this.isInPlay() && this.containsLetter())// if it is not in play
		// but it has a letter on it then it is used
		{
			return true;
		} else {
			return false; // every other tile is not used by default
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("boardTile button pressed");
		this.board.setCurrentTile(this);
	}

}