/*
* Project: Scrabble
* Package: scrabble
* Class: Word
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: The word class is a string and has a score. It contains methods that update the score of the 
* word along with the string of the word
* */
package scrabble; //package

public class Word { // class name

	private String word; // string of word
	private int wordScore; // score of word
	private int doubleWordsLandedOn; // num of doubleWordLandedOn
	private int tripleWordsLandedOn; // num of tripWordLandedOn

	public Word() // word constructor
	{
		this.word = ""; // set the word to blank for now
		this.wordScore = 0; // set the word score to zero
		this.doubleWordsLandedOn = 0; // set the double words count to 0
		this.tripleWordsLandedOn = 0; // set the triple words count to 0
	}

	public int getDWLO() { // method that returns the number of double words landed on
		return this.doubleWordsLandedOn;
	}

	public int getTWLO() { // method that returns the number of triple words landed on
		return this.tripleWordsLandedOn;
	}

	public int getWordScore() { // method that returns the word score
		return this.wordScore;
	}

	public void addToWord(BoardTile b)// add to word method that takes in a board tile
	{

		this.word = this.word + b.getLetter().getChar(); // get the letter from the board tile , then the char from the
															// letter and add it to the words

		if (b.isInPlay() == true) // if the tile is in play

		{

			// check what the name of the tile is to see if you need to apply any
			// multipliers
			if (b.getName().equalsIgnoreCase("doubleLetter")) {
				this.wordScore += 2 * (b.getLetter().getValue()); // apply multiplier to letter score

			} else if (b.getName().equalsIgnoreCase("tripleLetter")) {
				this.wordScore += 3 * (b.getLetter().getValue()); // apply multiplier to letter score

			} else if (b.getName().equalsIgnoreCase("doubleWord")) {
				this.wordScore += b.getLetter().getValue(); // just add the letter sa normal
				this.doubleWordsLandedOn++; // increase the double word c counter bc these arent applied until whole
											// word collected
			} else if (b.getName().equalsIgnoreCase("tripleWord")) {
				this.tripleWordsLandedOn++; // increase the triple word counter bc these arent applied until whole word
											// collected
				this.wordScore += b.getLetter().getValue(); // just add the letter as normal
			} else // if its a normal letter just add the letter score
			{
				this.wordScore += b.getLetter().getValue();
			}
		} else // if its used/not in play just add the letter to the letter score
		{
			this.wordScore += b.getLetter().getValue();
		}
	}

	public void wordMultiplier() { // method for the word multiplier

		for (int i = 0; i < this.getDWLO(); i++) // loop thru using the number of double words landed on
		{
			this.wordScore = this.wordScore * 2; // multiply the word score times 2
		}
		for (int i = 0; i < this.getTWLO(); i++) // loop thru using number of triple words landed o
		{
			this.wordScore = this.wordScore * 3; // multiply the word score times 3
		}
	}

	public void nullWord() // method that nulls the word
	{
		this.word = null;
	}

	public String getString() { // method that returns the word string
		return this.word;

	}
}
