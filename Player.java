
/*
* Project: Scrabble
* Package: scrabble
* Class: Player
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: a player has a a rack and a score and a word, the are just players, not much more
* */
package scrabble;

public class Player {

	// attributes
	private int playerScore;
	private Rack rack;
	private Word playerWord;

	public Player(Rack r) { // constructor that takes a rack as a parameter
		this.playerScore = 0; // set the player score to 0
		this.playerWord = null; // set the player score to null
		this.rack = r; // set the rack
	}

	public int getScore() { // method that returns the player score
		return this.playerScore;
	}

	public void addScore(int n) { // method that adds a passed value to the player score
		this.playerScore += n;
	}

	public void fillPlayerRack() { // method to fill the player rack which
		this.rack.fillRack(); // call the fill rack method
	}

	public Word getPlayerWord() { // method that returns player word
		return this.playerWord;
	}

	public void setPlayerWord(Word w) { // method to set player word to passed word
		this.playerWord = w;
	}
}