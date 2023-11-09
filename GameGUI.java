/*
* Project: Scrabble
* Package: scrabble
* Class: GameGUI
* Programmer: Wilton & Caelan
* Date Created: Saturday June, 25th
* Description: contains all of the graphics for the game, as well as declares all of the sublcasses
* needed for the game. Also contains the games logic as to when to collect a word, whether its a valid 
* position or if the game is over
* */
package scrabble; //package

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameGUI extends JFrame implements ActionListener { // class name and extensions

	// declare game attributes, board, pile, racks, players

	private Board gameBoard;
	private Pile gamePile;
	private Rack p1Rack;
	private Rack p2Rack;
	private Player player1;
	private Player player2;

	// declare panels

	private JPanel menuScreen;
	private JPanel gameScreen;

	// declare Jbuttons
	private JButton addTile;
	private JButton backToHand;
	private JButton playWord;
	private JButton skipTurn;
	private JButton playAgain;
	private JButton startGame;
	private JButton getNameP1, getNameP2;
	private JButton getBlankTile;

	// declare Jlabels
	private JLabel scoreLabelP1;
	private JLabel scoreLabelP2;
	private JLabel userName1Prompt;
	private JLabel userName2Prompt;
	private JLabel scrabble;
	private JLabel gifLabel;
	private JLabel playerTurn;
	private JLabel winner;

	// declare Jtextfields
	private JTextField nameFieldP1, nameFieldP2, blankTile;

	// declare strings
	private String name1;
	private String name2;

	// declare ints
	private int nameEnter = 0;
	private int skipTurnCount;
	private int turnCount;
	private int inPlayDirection;
	private int collectWordsIndex;

	// declare arrays
	private BoardTile[] inPlayTiles = new BoardTile[7];
	private BoardTile[] startingTiles = new BoardTile[7];
	private Word[] turnWords = new Word[8];

	// declare booleans
	public boolean played = false;

	// declare constants
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;

	GameGUI() // gui for the game
	{

		super("Scrabble"); // name window
		this.gameBoard = new Board(); // create a new board for the game
		this.gamePile = new Pile(); // create a pile for the game
		this.p1Rack = new Rack(this.gamePile); // create a rack for player 1 from the pile
		this.p2Rack = new Rack(this.gamePile); // create a rack for player 2 from the pile
		this.player1 = new Player(this.p1Rack); // create two players and assign their racks
		this.player2 = new Player(this.p2Rack);
		this.turnCount = 0; // set the turn count to 0

		// set specifications of the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(915, 537);
		this.setResizable(false);
		this.setLayout(null);

		// create the menu screen and its specifications
		this.menuScreen = new JPanel();
		this.menuScreen.setSize(new Dimension(2000, 2000));
		this.menuScreen.setLayout(null);
		this.menuScreen.setBackground(Color.BLACK);

		// make start button with image, add its specifications, then add it to screen w
		// visibility false
		ImageIcon startImage = new ImageIcon("startbutton.png");
		this.startGame = new JButton(startImage);
		this.startGame.setOpaque(false);
		this.startGame.setContentAreaFilled(false);
		this.startGame.setBorderPainted(false);
		this.startGame.setBounds(350, 400, 150, 47);
		this.startGame.setBackground(Color.RED);
		this.startGame.setForeground(Color.GREEN);
		this.startGame.addActionListener(this);
		this.startGame.setVisible(false);
		this.menuScreen.add(this.startGame);

		// create label for mensu screen and add it
		this.scrabble = new JLabel("SCRABBLE");
		this.scrabble.setFont(new Font("Courier", Font.PLAIN, 50));
		this.scrabble.setForeground(Color.BLUE);
		this.scrabble.setBounds(300, 5, 300, 50);
		this.scrabble.setVisible(false);
		this.menuScreen.add(this.scrabble);

		// create the jlabel prompt for player 1's name
		this.userName1Prompt = new JLabel("Player 1 Name: ");
		this.userName1Prompt.setFont(new Font("Calibri", Font.PLAIN, 20));
		this.userName1Prompt.setForeground(Color.WHITE);
		this.userName1Prompt.setBounds(175, 150, 300, 50);
		this.userName1Prompt.setVisible(false);
		this.menuScreen.add(this.userName1Prompt);

		// create the button for entering and retrieving player 1's name
		this.getNameP1 = new JButton("ENTER NAME");
		this.getNameP1.setBounds(550, 150, 120, 40);
		this.getNameP1.setBackground(Color.CYAN);
		this.getNameP1.setForeground(Color.BLACK);
		this.getNameP1.addActionListener(this);
		this.getNameP1.setVisible(false);
		this.menuScreen.add(this.getNameP1);

		// create the field that allows the user to enter a name for player 1
		this.nameFieldP1 = new JTextField(3);
		this.nameFieldP1.setEditable(true);
		this.nameFieldP1.setHorizontalAlignment(JTextField.CENTER);
		this.nameFieldP1.setFont(new Font("Monospaced", Font.BOLD, 25));
		this.nameFieldP1.addActionListener(this);
		this.nameFieldP1.setBounds(325, 150, 200, 50);
		this.nameFieldP1.setVisible(false);
		this.menuScreen.add(this.nameFieldP1);

		// create the jlabel prompt for player 2's name
		this.userName2Prompt = new JLabel("Player 2 Name: ");
		this.userName2Prompt.setFont(new Font("Calibri", Font.PLAIN, 20));
		this.userName2Prompt.setForeground(Color.WHITE);
		this.userName2Prompt.setBounds(175, 250, 300, 50);
		this.userName2Prompt.setVisible(false);
		this.menuScreen.add(this.userName2Prompt);

		// create the button for entering and retrieving player 2's name
		this.getNameP2 = new JButton("ENTER NAME");
		this.getNameP2.setBounds(550, 250, 120, 40);
		this.getNameP2.setBackground(Color.CYAN);
		this.getNameP2.setForeground(Color.BLACK);
		this.getNameP2.addActionListener(this);
		this.getNameP2.setVisible(false);
		this.menuScreen.add(this.getNameP2);

		// create the field that allows the user to enter a name for player 2
		this.nameFieldP2 = new JTextField(3);
		this.nameFieldP2.setEditable(true);
		this.nameFieldP2.setHorizontalAlignment(JTextField.CENTER);
		this.nameFieldP2.setFont(new Font("Monospaced", Font.BOLD, 25));
		this.nameFieldP2.addActionListener(this);
		this.nameFieldP2.setBounds(325, 250, 200, 50);
		this.nameFieldP2.setVisible(false);
		this.menuScreen.add(this.nameFieldP2);

		// create a gif label
		this.gifLabel = new JLabel();
		// set the icon of the label to the gif
		this.gifLabel.setIcon(new ImageIcon("scrabble.gif"));
		this.gifLabel.setBounds(0, 0, 1000, 500);
		this.gifLabel.setVisible(true);
		this.menuScreen.add(this.gifLabel);

		// create the actual game screen panel and its specifications
		this.gameScreen = new JPanel();
		this.gameScreen.setSize(new Dimension(2000, 2000));
		this.gameScreen.setLayout(null);
		this.gameScreen.setBackground(Color.WHITE);

		// on the game screen add the board and make it visible
		this.gameBoard.setVisible(true);
		this.gameBoard.setBackground(Color.LIGHT_GRAY);
		this.gameScreen.add(this.gameBoard);

		// create the play again button but set invisible
		this.playAgain = new JButton("PLAY AGAIN");
		this.playAgain.setBounds(850, 400, 150, 47);
		this.playAgain.setBackground(Color.WHITE);
		this.playAgain.setForeground(Color.BLACK);
		this.playAgain.addActionListener(this);
		this.playAgain.setVisible(false);
		this.gameScreen.add(this.playAgain);

		// create the Jlabel that displays player 1's score to the screen
		this.scoreLabelP1 = new JLabel(this.name1 + "'s Score: " + this.player1.getScore());
		this.scoreLabelP1.setFont(new Font("Calibri", Font.PLAIN, 20));
		this.scoreLabelP1.setForeground(Color.BLACK);
		this.setBackground(null);
		this.scoreLabelP1.setVisible(true);
		this.scoreLabelP1.setBounds(650, 50, 300, 50);
		this.gameScreen.add(this.scoreLabelP1);

		// create the Jlabel that displays player 2's score to the screen
		this.scoreLabelP2 = new JLabel(this.name2 + "'s Score: " + this.player2.getScore());
		this.scoreLabelP2.setFont(new Font("Calibri", Font.PLAIN, 20));
		this.scoreLabelP2.setForeground(Color.BLACK);
		this.setBackground(null);
		this.scoreLabelP2.setBounds(950, 50, 300, 50);
		this.scoreLabelP1.setVisible(true);
		this.gameScreen.add(this.scoreLabelP2);

		// create the Jlabel that will display the winner at the end of the game
		this.winner = new JLabel();
		this.winner.setFont(new Font("Calibri", Font.PLAIN, 40));
		this.winner.setForeground(Color.BLACK);
		this.setBackground(null);
		this.winner.setVisible(false);
		this.winner.setBounds(750, 300, 300, 50);
		this.gameScreen.add(this.winner);

		// create the label that says which players turn it is
		this.playerTurn = new JLabel(this.name1 + "'s Turn");
		this.playerTurn.setFont(new Font("Calibri", Font.PLAIN, 30));
		this.playerTurn.setForeground(Color.BLACK);
		this.playerTurn.setBounds(850, 300, 300, 50);
		this.playerTurn.setVisible(true);
		this.gameScreen.add(this.playerTurn);

		// make player 1's rcack and make it immediately visible
		this.p1Rack.setVisible(true);
		this.p1Rack.setBackground(Color.GREEN);
		this.p1Rack.setBounds(770, 400, 300, 50);
		this.gameScreen.add(this.p1Rack);

		// make player 2's rack and make it invisible for now
		this.p2Rack.setVisible(false);
		this.p2Rack.setBackground(Color.GREEN);
		this.p2Rack.setBounds(770, 400, 300, 50);
		this.gameScreen.add(this.p2Rack);

		// make + display the button that allows you to add the tile to the board
		this.addTile = new JButton("AddTile");
		this.addTile.setVisible(true);
		this.addTile.setBounds(650, 550, 100, 50);
		this.addTile.addActionListener(this);
		this.gameScreen.add(this.addTile);

		// make + display the button that allows you to move the tile back to hand
		this.backToHand = new JButton("BackToHand");
		this.backToHand.setVisible(true);
		this.backToHand.setBounds(800, 550, 100, 50);
		this.backToHand.addActionListener(this);
		this.gameScreen.add(this.backToHand);

		// make + display the button that allows you to play your word
		this.playWord = new JButton("PlayWord");
		this.playWord.setVisible(true);
		this.playWord.setBounds(950, 550, 100, 50);
		this.playWord.addActionListener(this);
		this.gameScreen.add(this.playWord);

		// make + display the button that allows you to skip your turn
		this.skipTurn = new JButton("SkipTurn");
		this.skipTurn.setVisible(true);
		this.skipTurn.setBounds(1100, 550, 100, 50);
		this.skipTurn.addActionListener(this);
		this.gameScreen.add(this.skipTurn);

		// make the textfield that allows the user to enter what letter they want their
		// blank tile to be
		this.blankTile = new JTextField(3);
		this.blankTile.setEditable(true);
		this.blankTile.setHorizontalAlignment(JTextField.CENTER);
		this.blankTile.setFont(new Font("Monospaced", Font.BOLD, 25));
		this.blankTile.addActionListener(this);
		this.blankTile.setBounds(750, 250, 200, 50);
		this.blankTile.setVisible(false);
		this.gameScreen.add(this.blankTile);

		// make the button that allows the user to enter the letter for their blank tile
		// and retrieve it
		this.getBlankTile = new JButton("ENTER LETTER");
		this.getBlankTile.setBounds(950, 250, 120, 40);
		this.getBlankTile.setBackground(Color.CYAN);
		this.getBlankTile.setForeground(Color.BLACK);
		this.getBlankTile.addActionListener(this);
		this.getBlankTile.setVisible(false);
		this.gameScreen.add(this.getBlankTile);

		// add the menu screen to the panel and set it to visible
		this.add(this.menuScreen);
		this.menuScreen.setVisible(true);

		this.setVisible(true);

		try {
			menuActions(); // method that delays the menu screen until the GIF is completed
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set the game screen to invisible but add it to panel
		this.gameScreen.setVisible(false);
		this.add(this.gameScreen);

	}

	@Override // action performed method for when any button is pressed
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == getNameP1) // if the button pressed was the get name button for P1
		{
			if (!this.nameFieldP1.getText().equals("")) // as long as the field is not null
			{
				// retrieve what was entered set it as the name and then set the box to
				// uneditable , and the button becomes invisible
				this.name1 = this.nameFieldP1.getText();
				this.nameFieldP1.setText(this.name1);
				this.nameFieldP1.setEditable(false);
				this.getNameP1.setVisible(false);
				this.nameEnter++; // increase the name entered count

				if (this.nameEnter == 2) { // check if both have been entered
					this.startGame.setVisible(true); // if so , the start button appears
				}

			}

		}

		if (e.getSource() == getNameP2) // if the button pressed was the get name button for P2
		{
			if (!this.nameFieldP2.getText().equals("")) // as long as the field is not null
			{
				// retrieve what was entered set it as the name and then set the box to
				// uneditable , and the button becomes invisible
				this.name2 = this.nameFieldP2.getText();
				this.nameFieldP2.setText(this.name2);
				this.nameFieldP2.setEditable(false);
				this.getNameP2.setVisible(false);
				this.nameEnter++; // increase the name entered count

				if (this.nameEnter == 2) { // check if both have been entered
					this.startGame.setVisible(true); // if so , the start button appears
				}

			}

		}

		if (e.getSource() == startGame) // if the button was the start game button
		{
			this.menuScreen.setVisible(false); // the menu screen becomes invisible
			this.setSize(2000, 2000);
			// set the score name for the players to the names of players entered
			this.scoreLabelP1.setText(this.name1 + "'s Score:" + this.player1.getScore());
			this.scoreLabelP2.setText(this.name2 + "'s Score:" + this.player2.getScore());
			this.playerTurn.setText(this.name1 + "'s Turn"); // set it as the first turn and so player 1's turn
																// displayed
			this.gameScreen.setVisible(true); // make the game screen visible
		}

		if (e.getSource() == addTile) // if the button pressed was add tile
		{

			if (this.gameBoard.getCurrentTile().isInPlay() == true
					|| this.gameBoard.getCurrentTile().containsLetter() == true
					|| this.gameBoard.getCurrentTile() == null) // if there is already a letter in that space or no
																// space has been selected
			{
				// do nothing
			} else // otherwise
			{
				if (this.turnCount % 2 == 0) // if its player 1's turn based on the turn count
				{
					if (this.p1Rack.getInHand() == null) { // if there is no letter in hand do nothing

					} else if (this.p1Rack.getInHand().getChar().equalsIgnoreCase("blank")) { // if the tile in hand is
																								// blank

						getBlankTile.setVisible(true); // make the blank tile button and textfield visible
						blankTile.setVisible(true);
					} else { // if the tile in hand is not blank and there is a tile in hand
						this.gameBoard.getCurrentTile().setLetter(this.p1Rack.getInHand()); // set the current selected
																							// board tile's letter to
																							// the letter in hand
						this.p1Rack.removeLetter(); // remove the letter from the rack
						this.p1Rack.setInHand(null); // set in hand to null

					}
				}

				else // otherwise if its player 2's turn
				{

					if (this.p2Rack.getInHand() == null) {
						// if there is no letter in hand do nothing
					} else if (this.p2Rack.getInHand().getChar().equalsIgnoreCase("blank")) { // if the tile in hand is
																								// blank

						getBlankTile.setVisible(true);
						blankTile.setVisible(true); // make the blank tile button and textfield visible
					} else { // if the tile in hand is not blank and there is a tile in hand
						this.gameBoard.getCurrentTile().setLetter(this.p2Rack.getInHand()); // set the current selected
																							// board tile's letter to
																							// the letter in hand
						this.p2Rack.removeLetter(); // remove the letter from the rack
						this.p2Rack.setInHand(null); // set in hand to null
					}
				}
			}
		}
		if (e.getSource() == backToHand) { // if the button pressed is back to hand

			// before doing anythign check if the tile was blank bc you need to "reset" it
			if (this.gameBoard.getCurrentTile().getLetter().getValue() == 0) { // if it was a blank tile
				this.gameBoard.getCurrentTile().getLetter().setChar("blank"); // set the "character" back to blank
				this.gameBoard.getCurrentTile().getLetter().setImage(new ImageIcon("BLANK.jpg")); // set the image and
																									// icon back to the
																									// blank image
				this.gameBoard.getCurrentTile().getLetter().setIcon(new ImageIcon("BLANK.jpg"));
			}

			// if its player 1's turn
			if (this.turnCount % 2 == 0) {
				this.p1Rack.insertLetter(this.gameBoard.getCurrentTile().getLetter()); // insert the letter from the
																						// selected tile back into the
																						// rack
				this.gameBoard.getCurrentTile().resetTile(); // reset the tile
				this.gameBoard.setCurrentTile(null); // set the current tile back to null
			}

			else // otherwise if its player 2's turn
			{
				this.p2Rack.insertLetter(this.gameBoard.getCurrentTile().getLetter()); // insert the letter from teh
																						// selected tile back into their
																						// rack
				this.gameBoard.getCurrentTile().resetTile(); // reset the tiel
				this.gameBoard.setCurrentTile(null); // set the current tile back to null
			}
		}
		if (e.getSource() == playWord) { // if the button pressed is play word

			findInPlayTiles(); // call the method that finds and collected all of the in play tiles

			if (validPosition() == true) // check if its a valid positon by calling the valid position method , if it is
											// a valid position
			{

				this.inPlayDirection = setInPlayDirection(); // find the in play direciton by calling the method ( going
																// to tell u the direction of the "played" word)
				BoardTile playerWordStart = findStartOfPlayerWord(); // find the board tile that is the start of the
																		// "played" word by calling the method
				collectInPlayWord(playerWordStart); // collect the "played" word by passing the starting tile to the
													// method
				findStartOfExistingWords(); // call the method that finds the start of all of the "existing" words that
											// you added onto, this fills the starting tiles array
				for (int b = 0; b < this.startingTiles.length && this.startingTiles[b] != null; b++) // loop thru the
																										// starting
																										// tiles array
																										// until there
																										// are no more
																										// elements
				{
					collectWords(this.startingTiles[b]); // and call the method to collect the words for that starting
															// tile
				}

				this.collectWordsIndex = 0; // reset collect words index to 0

				// once all of the words are collected, neeed to check that they are ALL valid
				// words
				if (checkIfValidWords() == true) // call the method that checks if they are valid words, if they are a
													// proper play has been completed
				{
					this.skipTurnCount = 0; // every time you reach this step you can reset the skipturn count as a
											// proper play has been completed

					for (int q = 0; q < this.turnWords.length && this.turnWords[q] != null; q++) // loop thru the array
																									// of the words from
																									// the turn until
																									// the end of the
																									// elements
					{
						this.turnWords[q].wordMultiplier(); // call the multiplier method for each which checks if they
															// had any multipliers and applies them

						// check who's turn it is and assign the points to the correct player for each
						// word
						if (this.turnCount % 2 == 0) {
							this.player1.addScore(this.turnWords[q].getWordScore());
						} else {
							this.player2.addScore(this.turnWords[q].getWordScore());
						}
					}
					nextTurn(); // call the method for the next turn
					this.played = true;// a full turn has been completed so set played to true
				}

				else { // if any of the words are invalid
					resetRack(); // call the method to reset the rack
				}

			}

			else // if it is an invalid position
			{
				resetRack(); // call the method to reset the rack
			}
		}

		if (e.getSource() == skipTurn) // if the button pressed was skip turn
		{
			this.skipTurnCount++; // increase the skip turn count
			findInPlayTiles(); // find any in play tiles so that u can return them to the players rack
			resetRack(); // reset their rack
			nextTurn(); // call the next turn method
		}

		if (e.getSource() == playAgain) // if the button pressed was play again
		{
			this.dispose();
			GameGUI GUI = new GameGUI(); // create a new game
		}

		if (e.getSource() == getBlankTile) { // if the button pressed was get blank tile

			String letter = ""; // create a letter string
			letter = this.blankTile.getText(); // set the letter string to the user input
			blankTile(letter); // call the blank tile method w the input
		}

	}

	public boolean checkIfValidWords() { // method for checking if all the words for the turn were valid

		boolean allWordsValid = true; // boolean starts as true unless proven otherwisse

		for (int k = 0; k < this.turnWords.length && this.turnWords[k] != null && allWordsValid; k++) // loop thru the
																										// words array
																										// until its
																										// null or one
																										// of the words
																										// was not valid
		{
			allWordsValid = binaryWordSearch(this.turnWords[k].getString()); // set the boolean equal to the method that
																				// checks the word against the scrabble
																				// dictionary using binary search
		}

		return allWordsValid; // return the boolean
	}

	public static boolean binaryWordSearch(String userWord) { // method that takes in a words and uses binary search to
																// see if that word is contianed in a file ( scrabble
																// dictionary) to see if its valid

		int location = 0;// variable for word location
		boolean found = false; // boolean for if the word is found
		String[] wordArray = new String[267753]; // the text file is 2567753 lines
		File inputFile = new File("scrabbleDictionary.txt"); // import the file

		try {
			Scanner myScanner = new Scanner(inputFile);

			int bottom = 0;
			int top = wordArray.length - 1;
			String wordCheckMiddle = "";
			for (int i = 0; i < wordArray.length; i++) {
				wordArray[i] = myScanner.nextLine(); // input each line from the file into the array
			}
			while (bottom <= top && !found) {
				int middle = (bottom + top) / 2;
				wordCheckMiddle = wordArray[middle]; // get middle word
				if (wordCheckMiddle.equalsIgnoreCase(userWord)) { // if it matches
					found = true;
					location = 1 + middle;// have to add one because the index starts at 0 so when middle is 4, it is
											// actually the 5th word in the list
				} else {
					if (userWord.compareToIgnoreCase(wordCheckMiddle) > 0) { // if the user word comes alphabetically
																				// after the middle word
						bottom = middle + 1; // discard the bottom half
					} else {
						top = middle - 1; // discard the top half
					}
				}
			}
			myScanner.close(); // close file and scanner
		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // initialize scanner for the file

		return found; // return whether the word was found in the file or not
	}

	public void findInPlayTiles() { // method for finding and collecting the in play tiles
		int arrayIndex = 0; // set the array index to 0

		for (int r = 0; r < 15; r++) { // loop thru each row
			for (int c = 0; c < 15; c++) { // through each column of the row
				if (this.gameBoard.getBoardTile(r, c).isInPlay()) // check if its in play
				{
					this.inPlayTiles[arrayIndex] = gameBoard.getBoardTile(r, c); // if it is add it to the array
					arrayIndex++; // increase the array index o that the next gets added into the next empty
									// position
				}
			}
		}
	}

	public int setInPlayDirection() { // method for settinf the in play direction
		int inPlayDirection = 0; // set an int for the direction

		if (this.inPlayTiles[1] == null) { // if there was only one in play tile

			int c = this.inPlayTiles[0].getColumn(); // get the column and row of the tile
			int r = this.inPlayTiles[0].getRow();

			// check for if its at the edge of the board
			if (c == 14 || c == 0 || r == 14 || r == 0) {
				if ((c == 14 && this.gameBoard.getBoardTile(r, c - 1).containsLetter())
						|| (c == 0 && this.gameBoard.getBoardTile(r, c + 1).containsLetter())) {
					inPlayDirection = HORIZONTAL; // if it contains letters to the left or right set the direction to
													// horizontal
				} else {
					inPlayDirection = VERTICAL; // otherwise set the direction to vertical
				}
			}

			else // if its not on the edge of the board
			{

				if (this.gameBoard.getBoardTile(r + 1, c).containsLetter()
						|| this.gameBoard.getBoardTile(r - 1, c).containsLetter()) {
					inPlayDirection = VERTICAL; // if it contains tiles above or below it , set the direction to
												// vertical
				}

				else if (this.gameBoard.getBoardTile(r, c + 1).containsLetter()
						|| this.gameBoard.getBoardTile(r, c - 1).containsLetter()) {
					inPlayDirection = HORIZONTAL; // if it contains letters to the left or right set it to horixontal
				}

				else { // by default if theres tiles in both directoin or its going to be only one
						// or long or honestly for whatever reason just set it to horizontal by default

					inPlayDirection = HORIZONTAL;
				}

			}
		} else { // otherwise if there were two or more tiles played

			if (this.inPlayTiles[0].getRow() == this.inPlayTiles[1].getRow()) { // if there rows are the same
				inPlayDirection = HORIZONTAL; // set the direction to horizontal
			} else if (this.inPlayTiles[0].getColumn() == this.inPlayTiles[1].getColumn()) { // otherwise if the columns
																								// were the same
				inPlayDirection = VERTICAL; // set the direction to veritcal
			}
		}
		return inPlayDirection; // return the direction
	}

	public BoardTile findStartOfPlayerWord() { // method for finding the start of the played word

		BoardTile returnTile = null; // set the return tile to null for now

		int c = this.inPlayTiles[0].getColumn(); // get the column and row of the first in play tile
		int r = this.inPlayTiles[0].getRow();

		if (this.inPlayDirection == VERTICAL) // if the direction is vertical
		{
			while (this.gameBoard.getBoardTile(r - 1, c).containsLetter()) // loop thru the board tiles moving up unitl
																			// you reach the top most letter as that
																			// will be the start of the word
			{
				if (r == 0) // IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
				{
					break;
				} else {
					r--;
				}
			}
			returnTile = this.gameBoard.getBoardTile(r, c); // set the return tile to the tile at the row and column
															// found
		}

		if (this.inPlayDirection == HORIZONTAL) // if the direction is horizontal
		{
			while (this.gameBoard.getBoardTile(r, c - 1).containsLetter()) // while it contains a letter to the left
			{
				if (c == 0)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
				{
					break;
				} else {
					c--;
				}
			}
			returnTile = this.gameBoard.getBoardTile(r, c); // set the return tile to the tile at the row and column
															// found
		}
		return returnTile; // return the tile found
	}

	public void collectInPlayWord(BoardTile b) { // method for collecting the word that take in a board tile at the
													// start of the word

		Word w = new Word(); // make a new word

		int c = b.getColumn(); // get the row and column of the board tile
		int r = b.getRow();

		if (this.inPlayDirection == HORIZONTAL) // if the in plya direction was horizontal t
		{
			// go right each time and collect the word until the end ( when there is no tile
			// ) or the edge of the board
			while (this.gameBoard.containsLetter(r, c)) {
				w.addToWord(this.gameBoard.getBoardTile(r, c));
				if (c == 14)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
				{
					break;
				} else {
					c++;
				}
			}

			this.turnWords[0] = w; // set the first turn word in the array to this word
		}

		if (this.inPlayDirection == VERTICAL) // if the direction was vertical
		{

			// go down each time and collect the word until the end ( when there is no tile
			// ) or the edge of the board
			while (this.gameBoard.containsLetter(r, c)) {
				w.addToWord(this.gameBoard.getBoardTile(r, c));
				if (r == 14)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
				{
					break;
				} else {
					r++;
				}
			}

			this.turnWords[0] = w; // set the first turn word to this word
		}
	}

	public void findStartOfExistingWords() { // method for finding the start of "existing" words

		for (int i = 0; i < this.inPlayTiles.length && (this.inPlayTiles[i] != null); i++) // loop thru the in play
																							// tiles array until it is
																							// null
		{
			int c = this.inPlayTiles[i].getColumn(); // get the column and the row of the tile
			int r = this.inPlayTiles[i].getRow();

			if (this.inPlayDirection == HORIZONTAL) // if the direction is horizontal for the played then any of the
													// "exisiting" words that were added on to HAVE to be in the
													// vertical direction
			{
				// loop thru the board checking up from the played tile until you find the
				// upmost tile which is the start of the word
				while (this.gameBoard.containsLetter(r - 1, c)) {
					System.out.println("finding vertiacl");
					if (r == 0)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
					{
						break;
					} else {
						r--;
					}
				}
				this.startingTiles[i] = this.gameBoard.getBoardTile(r, c); // add this to the array of starting tiles
			}

			if (this.inPlayDirection == VERTICAL) // if the direction was vertical for the played then any of the
													// "exisiting" words that were added on to HAVE to be in the
													// horizontal direction
			{
				// loop thru the board checking to the left from the played tile until you find
				// the leftmost tile which is the start of the word
				while (this.gameBoard.containsLetter(r, c - 1)) {
					if (c == 0)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
					{
						break;
					} else {
						c--;
					}
				}
				this.startingTiles[i] = this.gameBoard.getBoardTile(r, c); // add it to the starting tiles array
			}
		}
	}

	public boolean validPosition() { // method for checking if its a valid position
		boolean isValidPlay = false; // boolean for if its a valid play is set to false

		for (int i = 0; i < this.inPlayTiles.length && (this.inPlayTiles[i] != null); i++)// loop thru the in play tiles
																							// arra y
		{
			int column = this.inPlayTiles[i].getColumn(); // set column and row variables to the row and column of the
															// tile
			int row = this.inPlayTiles[i].getRow();

			if (!played) // if its the first word on board
			{
				if (this.inPlayTiles[i].getRow() == 7 && this.inPlayTiles[i].getColumn() == 7) // to be a valid play it
																								// must be on the start
																								// tile ( row 7 and
																								// column 7)
				{
					isValidPlay = true; // then the valid play is true
				}
			}

			else { // if its any word after the first

				// essentially any play on the first is a valid play as long as at least one if
				// its tiles is connected to a non in play tile that is alr on board ( has to
				// connect to an alr existing word)

				if (column == 14) { // if its in the last column, check up down and left ( dont check right ) bc its
									// out of bounds

					if ((this.gameBoard.containsLetter(row + 1, column) && !this.gameBoard.inPlay(row + 1, column))
							|| (this.gameBoard.contains(row - 1, column) && !this.gameBoard.inPlay(row - 1, column))
							|| this.gameBoard.containsLetter(row, column - 1)
									&& !this.gameBoard.inPlay(row, column - 1)) {

						isValidPlay = true; // its a valid play if theres a non in play tile that its "connected to"
					}
				}

				else if (column == 0) { // if its in the first column only check up down right sinc left if out of
										// bounds
					if ((this.gameBoard.containsLetter(row + 1, column) && !this.gameBoard.inPlay(row + 1, column))
							|| (this.gameBoard.contains(row - 1, column) && !this.gameBoard.inPlay(row - 1, column))
							|| (this.gameBoard.containsLetter(row, column + 1)
									&& !this.gameBoard.inPlay(row, column + 1))) {

						isValidPlay = true; // its a valid play if theres a non in play tile that its "connected to"
					}

				}

				else if (row == 14) { // if its in the last row only check up right left as down is out of bounds
					if ((this.gameBoard.containsLetter(row - 1, column) && !this.gameBoard.inPlay(row - 1, column))
							|| (this.gameBoard.containsLetter(row, column + 1)
									&& !this.gameBoard.inPlay(row, column + 1))
							|| (this.gameBoard.containsLetter(row, column - 1)
									&& !this.gameBoard.inPlay(row, column - 1))) {
						isValidPlay = true; // its a valid play if theres a non in play tile that its "connected to"
					}
				} else if (row == 0) { // if its in the first row only check down left right as up is out of bounds
					if ((this.gameBoard.containsLetter(row + 1, column) && !this.gameBoard.inPlay(row + 1, column))
							|| (this.gameBoard.containsLetter(row, column + 1)
									&& !this.gameBoard.inPlay(row, column + 1))
							|| this.gameBoard.containsLetter(row, column - 1)
									&& !this.gameBoard.inPlay(row, column - 1)) {

						isValidPlay = true; // its a valid play if theres a non in play tile that its "connected to"
					}
				} else // otherwise if its not on the border check all 4 directions
				{
					if ((this.gameBoard.containsLetter(row + 1, column)
							&& this.gameBoard.inPlay(row + 1, column) == false)
							|| (this.gameBoard.containsLetter(row - 1, column)
									&& this.gameBoard.inPlay(row - 1, column) == false)
							|| (this.gameBoard.containsLetter(row, column + 1)
									&& this.gameBoard.inPlay(row, column + 1) == false)
							|| (this.gameBoard.containsLetter(row, column - 1)
									&& this.gameBoard.inPlay(row, column - 1) == false)) {

						isValidPlay = true; // its a valid play if theres a non in play tile that its "connected to"
					}
				}

			}

		}

		for (int i = 0; i < this.inPlayTiles.length && (this.inPlayTiles[i] != null)
				&& (this.inPlayTiles[i + 1] != null); i++) { // at the end loop thru to check for the case where they
																// play a vaid position word but then also another
																// random tile somewhere else on board

			if ((inPlayTiles[i].getRow() != inPlayTiles[i + 1].getRow())
					&& inPlayTiles[i].getColumn() != inPlayTiles[i + 1].getColumn()) { // loop trhu the array while
																						// tehres two consecutive not
																						// null tiles and then if
																						// neither their row nor their
																						// column is the dame set is
																						// valid play back to false , as
																						// a valid play has all the same
																						// row for in play or all the
																						// same column

				isValidPlay = false;
			}
		}

		return isValidPlay; // return whether or not it is a valid play
	}

	public void collectWords(BoardTile inPlayTile) { // method for collecting not in play words , requires an index and
														// a board tile

		Word w = new Word(); // create a new word

		int c = inPlayTile.getColumn(); // assign the row and column from the tile
		int r = inPlayTile.getRow();

		if (this.inPlayDirection == HORIZONTAL) // if the direction is horizontal
		// then you are going to be collecting vertical
		{
			while (this.gameBoard.containsLetter(r, c)) // loop thru vertically

			{
				w.addToWord(this.gameBoard.getBoardTile(r, c)); // add the letter to the word
				if (r == 14)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
				{
					break;
				} else {
					r++;
				}
			}

			if (w.getString().length() > 1) { // as long as the word is more then one letter
				this.turnWords[this.collectWordsIndex] = w;// add it to the index
				this.collectWordsIndex++; // increase the index count
			}

			else {

				// otherwise if its only one letter dont collect it
			}

		}

		if (this.inPlayDirection == VERTICAL) // if the direction is vertical
		// then you are going to be collecting horizontal
		{
			while (this.gameBoard.containsLetter(r, c)) // while it contains a letter kepp increasing c
			{
				w.addToWord(this.gameBoard.getBoardTile(r, c));
				if (c == 14)// IF U ARE AT THE EDGE OF THE BOARD THEN DONT GO AGAIN
				{
					break;
				} else {
					c++;
				}
			}

			if (w.getString().length() > 1) { /// as long as the word is more then one letter
				this.turnWords[this.collectWordsIndex] = w;
				this.collectWordsIndex++; // add it to the array and increase the index count
			}

			else {
				// if words is less then one in length do nothing

			}

		}
	}

	public void resetRack() { // method for resetting the rack
		// loop thru the in play tiles array
		for (int i = 0; i < this.inPlayTiles.length && (this.inPlayTiles[i] != null); i++) {
			if (this.inPlayTiles[i].getLetter().getValue() == 0) { // if its a blank tile then u need to "reset" it
																	// first before you proceed so reset the character
																	// and the imahe
				this.inPlayTiles[i].getLetter().setChar("blank");
				this.inPlayTiles[i].getLetter().setImage(new ImageIcon("BLANK.jpg"));
				this.inPlayTiles[i].getLetter().setIcon(new ImageIcon("BLANK.jpg"));

			}

			if (turnCount % 2 == 0) { // if its player 1's turn
				this.p1Rack.insertLetter(this.inPlayTiles[i].getLetter()); // insert the letter using method back into
																			// the rack
				this.inPlayTiles[i].resetTile(); // reset the board tile using method
				this.inPlayTiles[i] = null; // set the position in the array to null
			} else { // otherwise if its player 2's turn
				this.p2Rack.insertLetter(this.inPlayTiles[i].getLetter()); // insert the letter using method back into
																			// the rack
				this.inPlayTiles[i].resetTile(); // reset the tile
				this.inPlayTiles[i] = null; // set the position in the array to null
			}
		}
	}

	public void nextTurn() { // method for proceeding to the next turn

		if (!gameOver()) { // call the game over method and if its not gme over then

			for (int i = 0; i < this.inPlayTiles.length && this.inPlayTiles[i] != null; i++) { // set all the in play
																								// tiles to not in play
																								// by looping and
																								// calling method
				this.inPlayTiles[i].setNotInPlay();
				this.inPlayTiles[i] = null;
			}

			for (int i = 0; i < this.startingTiles.length && this.startingTiles[i] != null; i++) { // set the starting
																									// tiles array back
																									// to null
				this.startingTiles[i] = null;
			}

			for (int i = 0; i < this.turnWords.length && this.turnWords[i] != null; i++) {
				this.turnWords[i] = null; // set the turn words array back to null
			}

			if (this.turnCount % 2 == 0) { // if its player 1's turn finishing , refill their rack and thenmake it
											// invisible
				this.player1.fillPlayerRack();
				this.p1Rack.setVisible(false);
				this.p2Rack.setVisible(true); // make player 2's rack visible
				this.playerTurn.setText(this.name2 + "'s Turn"); /// switch the display of whos turn it is
				turnCount++; // increase the turn count

			}

			else { // otherwise if its player 2's turn finishing
				this.player2.fillPlayerRack(); // fill their rack and set it to invisible
				this.p2Rack.setVisible(false);
				this.p1Rack.setVisible(true); // make p1's rack visible
				this.playerTurn.setText(this.name1 + "'s Turn"); // switch the text of whos turn it is
				turnCount++;

			}
			this.scoreLabelP1.setText(this.name1 + "'s Score: " + this.player1.getScore()); // update the score texts
			this.scoreLabelP2.setText(this.name2 + "'s Score: " + this.player2.getScore());

		}

		else { // otherwise if it is game over
			findWinner(); // call the findwinner method
			this.winner.setVisible(true); // set the winner text to visible

			// set the racks and buttons from game to invisible
			this.p1Rack.setVisible(false);
			this.p2Rack.setVisible(false);
			this.addTile.setVisible(false);
			this.playWord.setVisible(false);
			this.backToHand.setVisible(false);
			this.skipTurn.setVisible(false);
			this.playerTurn.setVisible(false);

			// make playagain button visible
			this.playAgain.setVisible(true);
		}
	}

	// method for checking if game is over
	public boolean gameOver() {
		boolean endGame = false; // boolean for if its over is false

		if (this.skipTurnCount == 4) {
			endGame = true; // game ends if there were 4 consecutive skips in a row
		}

		else if (this.gamePile.getNumTiles() == 0 && (this.p1Rack.checkIfEmpty() || this.p2Rack.checkIfEmpty())) {

			endGame = true; // or it ends if the game pile is empty and one of the two players rack is empty
		}

		else {
			// otherwise it remains false
		}

		return endGame; // return end game
	}

	public void findWinner() { // method for finding the winner

		if (player1.getScore() > player2.getScore()) { // if players one score is greater
			this.winner.setText("WINNER: " + this.name1); // set winner text to winner + the name of player 1
		}

		else if (player1.getScore() < player2.getScore()) { // if player 2's score is greater set the winner text to
															// player 2's name

			this.winner.setText("WINNER: " + this.name2);
		} else // otherwise if its a tie then set the winner text to tie game
		{
			this.winner.setText("TIE GAME");
		}

	}

	// method that is called that keeps the gif on the screen then sets everytting
	// else on the menu to visible once the gif is done playing using a thread.sleep
	public void menuActions() throws InterruptedException {
		this.gifLabel.setVisible(true);
		Thread.sleep(7590);
		this.gifLabel.setVisible(false);
		this.getNameP1.setVisible(true);
		this.getNameP2.setVisible(true);
		this.nameFieldP1.setVisible(true);
		this.nameFieldP2.setVisible(true);
		this.userName1Prompt.setVisible(true);
		this.userName2Prompt.setVisible(true);
		this.scrabble.setVisible(true);
	}

	public void blankTile(String l) // method for setting the blank tile based on user inpu that is passed as
									// parameter

	{
		this.blankTile.setVisible(false); // make the text box invisible
		this.getBlankTile.setVisible(false); // make the button invisible
		this.getBlankTile.setText(null); // set the blanktile box back to null
		boolean proper = true; // boolean for itf it was a proper input

		// for each of these its an if statement that if the input was one of the
		// letters a-z
		// to set the character of the in hand letter to that letter and as well set the
		// corresponding image

		if (l.equalsIgnoreCase("a")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("A.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("A.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("A.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("A.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("b")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("B.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("B.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("B.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("B.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("c")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("C.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("C.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("C.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("C.jpg"));
			}

		} else if (l.equalsIgnoreCase("d")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("D.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("D.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("D.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("D.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("e")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("E.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("E.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("E.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("E.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("f")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("F.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("F.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("F.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("F.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("g")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("G.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("G.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("G.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("G.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("h")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("H.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("H.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("H.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("H.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("i")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("I.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("I.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("I.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("I.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("j")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("J.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("J.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("J.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("J.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("k")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("K.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("K.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("K.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("K.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("l")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("L.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("L.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("L.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("L.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("m")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("M.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("M.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("M.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("M.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("n")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("N.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("N.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("N.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("N.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("o")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("O.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("O.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("O.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("O.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("p")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("P.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("P.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("P.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("P.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("q")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("Q.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("Q.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("Q.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("Q.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("r")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("R.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("R.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("R.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("R.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("s")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("S.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("S.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("S.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("S.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("t")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("T.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("T.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("T.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("T.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("u")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("U.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("U.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("U.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("U.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("v")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("V.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("V.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("V.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("V.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("w")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("W.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("W.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("W.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("W.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("x")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("X.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("X.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("X.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("X.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("y")) {

			ImageIcon y = new ImageIcon("Y.jpg");

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("Y.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("Y.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("Y.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("Y.jpg"));
			}

		}

		else if (l.equalsIgnoreCase("z")) {

			if (this.turnCount % 2 == 0) {
				this.p1Rack.getInHand().setChar(l);
				this.p1Rack.getInHand().setImage(new ImageIcon("Z.jpg"));
				this.p1Rack.getInHand().setIcon(new ImageIcon("Z.jpg"));
			} else {
				this.p2Rack.getInHand().setChar(l);
				this.p2Rack.getInHand().setImage(new ImageIcon("Z.jpg"));
				this.p2Rack.getInHand().setIcon(new ImageIcon("Z.jpg"));
			}

		}

		else {
			proper = false; // if their input was not one of the letters a-z then proper input was false
		}

		if (proper) { // if the input was proper

			if (turnCount % 2 == 0) { // if its player 1's turn
				this.gameBoard.getCurrentTile().setLetter(this.p1Rack.getInHand()); // set the current tile letter to
																					// the tile in hand ( which has been
																					// updated accordingly above)
				this.p1Rack.removeLetter(); // remove the letter from the rack
				this.p1Rack.setInHand(null); // set in hand to null
			} else { // otherwise if its player 2's turn

				this.gameBoard.getCurrentTile().setLetter(this.p2Rack.getInHand()); // set the current tile letter to
																					// the tile in hand ( which has been
																					// updated accordingly above)
				this.p2Rack.removeLetter(); // remove the letter from the rack
				this.p2Rack.setInHand(null); // set in hand to null

			}
		}

		else { // if it was not an proper input
			this.blankTile.setVisible(true); // then make the textbox and button visible again so they can retry
												// inputting it
			this.getBlankTile.setVisible(true);

		}

	}

}
