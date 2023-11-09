/*
* Project: Scrabble
* Package: scrabble
* Class: Pile
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: Contrains a "pile" of letters in an array, this is where the user will get thier letters from
* */
package scrabble;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Pile {

	// attributes of the pile class

	private int numTiles;
	private Letter[] lettArr;

	public Pile() { // pile constructor
		this.lettArr = new Letter[100]; // creates a new letter array of length 100
		this.numTiles = 0;// set the number of tiles to 0
		readFile();// call the read file method
	}

	public void readFile() { // read file method that reads from a text file of the number of each letter
								// tile, an its score and image and creates letters

		File inputFile = new File("scrabbletiles.txt"); // import the scrabble tiles file

		try {
			Scanner myScanner = new Scanner(inputFile);// initialize scanner for the file

			// variables needed for creating letters
			int numOfTileType;
			int score;
			String C;
			String image;
			ImageIcon i;

			// while there are still lines in the file
			while (myScanner.hasNext()) {
				numOfTileType = myScanner.nextInt(); // the first line is the number of tile types
				C = myScanner.next(); // the next line is the letter/character
				score = myScanner.nextInt(); // the next line is the score
				image = myScanner.next();// the next line is the image
				i = new ImageIcon(image); // create image icon using image

				for (int n = numOfTileType; n > 0; n--) { // loop thru for the number of that type and make that many of
															// the letter
					this.lettArr[numTiles] = new Letter(score, C, i); // make the letter using the variable from above
					this.numTiles++; // increase the numTiles count

				}
			}
			myScanner.close(); // close the file and scanner
		} catch (IOException e) {
			System.out.println("file error");
		}
	}

	public Letter pickupTile() { // method for "picking up" a random tile

		Random r = new Random(); // instance of the random class
		Letter l; // letter
		int randomInt = r.nextInt(99) + 1;// generate a random number

		if (this.numTiles == 0) // if there are no tiles left in the pile just return null
		{
			return null;
		} else { // otherwise
			while (this.lettArr[randomInt] == null) { // check the array at that int if its null, generate a new random
														// number until its not null
				randomInt = r.nextInt(99) + 1;
			}

			this.numTiles--; // then decrease the number of tiles
			l = this.lettArr[randomInt]; // set the letter to the letter in the array at that random int
			this.lettArr[randomInt] = null; // then set that space in the array to null
			return l; // return the letter
		}
	}

	public int getNumTiles() { // method that returns the number of tiles in the pile
		return this.numTiles;
	}

	public void printArray() { // method for printing the array for debugging
		for (int i = 0; i < lettArr.length; i++) { // loops thru array and prints it
			if (lettArr[i] == null) {
				System.out.println("null");
			} else {
				System.out.println(lettArr[i].getChar());
			}
		}
	}
}
