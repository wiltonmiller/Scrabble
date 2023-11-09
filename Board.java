/*
* Project: Scrabble
* Package: scrabble
* Class: Board
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: visual representation of the scrabble board, is a 2D array of boardTiles
* */
package scrabble;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Board extends JPanel {

	// constants
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	// create a constant dimension using width and height constants
	private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);

	// attributes
	private BoardTile[][] btArr;
	private BoardTile currentTile;

	// layout
	private LayoutManager myLayout;

	Board() // board constructor
	{
		this.setSize(dimension); // sets size
		this.btArr = new BoardTile[15][15];// creates 15x15 2D array of board tiles
		this.currentTile = null; // set the current tile to null
		this.myLayout = new GridLayout(15, 15, 5, 5); // 15 by 15 and the spaces between the tiles is 5
		this.setLayout(this.myLayout); // set the layout of this to the gridlayout created
		for (int r = 0; r < 15; r++) { // loop through the rows
			for (int c = 0; c < 15; c++) { // loop throguh the columsn
				this.btArr[r][c] = new BoardTile(r, c, this); // add the tile to the board at the row and the column
				this.add(this.btArr[r][c]); // this puts them in the right spot in grid layout bc it goes across row
											// first then down
			}
		}
	}

	public BoardTile getBoardTile(int r, int c) { // method that takes a row and column int and returns the board tile
													// at that row and column
		return this.btArr[r][c];
	}

	public boolean containsLetter(int r, int c) // method that checks if a board tile contains a letter, takes a row and
												// column parameter
	{
		if (this.btArr[r][c].containsLetter()) // calls the board tiles contains letter method from the array at row,
												// column
		{
			return true; // if it does return true
		} else {
			return false; // otherwise return false
		}
	}

	public boolean inPlay(int r, int c) // method that takes a row and column and returns if the board tile is in play
	{
		if (this.btArr[r][c].isInPlay()) // calls the board tile in play method using the tile at the row and column
		{
			return true; // return true if it is
		} else {
			return false; // else return false
		}
	}

	public void setCurrentTile(BoardTile b) { // method for setting the current tile to a passed boardtile
		this.currentTile = b;
	}

	public BoardTile getCurrentTile() { // method that returns the current tile
		return this.currentTile;
	}
}
