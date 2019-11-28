
// -------------------------------------------------------
// Assignment A4
// Written by:  Rodger S. Ragasa
// For COMP 248 Section (FF) – Fall 2019
// --------------------------------------------------------
/**
 * 
 * Description: This is Nancy's 3D 2-player game where a user can create a 3D board. Players take turns rolling 2 dice and moving up the board and levels. It is a race to see who ends up on the last tile of the last level/board. Players gain and lose energy along the way and can decide to challenge the other player for their spot when one player lands on the other player's tile. 
 * 
 * Note: We assume a perfect user who enters correct value type when asked for user input. (i.e.: when asked to enter a value of type integer, the program verifies integers).
 *
 */

import java.util.Scanner;

public class LetUsPlay {
	public final static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		Board board = null;
		int dieRollSum = 0;
		boolean tileEmpty;
		final int MAX_LEVEL = 10;
		final int MAX_SIZE = 10;
		// Driver class for Nancy’s 3D Warrior Game
		System.out.println("\t* * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\t* - - - - - - - - - - - - - - - - - - - - - - - *");
		System.out.println("\t*\t\t\t\t\t\t*");
		System.out.println("\t*\tWELCOME to nancy's 3D Warrior Game!\t*\t");
		System.out.println("\t*\t\t\t\t\t\t*");
		System.out.println("\t* * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\t - - - - - - - - - - - - - - - - - - - - - - - - ");

		// Ask the user if they want to use the default board size which has 3
		// levels with 4x4 boards at each level or if they want to set their own
		// dimensions. The minimum number of levels is 3 and the maximum is 10.
		// The minimum board size is 3x3 and the maximum is 10x10. Be sure if
		// the user decides to set his/her own dimensions that they are within
		// these ranges.

		System.out.print("The default game board has " + Board.MIN_LEVEL + " levels and each leve has a "
				+ Board.MIN_SIZE + "x" + Board.MIN_SIZE
				+ " board.\nYou can use this default board size or change the size\n\t 0 to use the default board size\n\t-1 to enter your own size\n==> What do you want to do? ");
		String useDefaultBoardSize = keyboard.next();
		while (!useDefaultBoardSize.equals("0") & !useDefaultBoardSize.equals("-1")) {
			System.out.println("Sorry but " + useDefaultBoardSize + " is not a legal choice.");
			useDefaultBoardSize = keyboard.next();
		}

		// check if user wants to use default values or set their own dimensions
		// for the board
		if (useDefaultBoardSize.equals("-1")) {
			System.out.print(
					"How many levels would you like? (minimum size " + Board.MIN_LEVEL + ", max " + MAX_LEVEL + ") ");
			int boardLevels = keyboard.nextInt();
			while (boardLevels < Board.MIN_LEVEL | boardLevels > MAX_LEVEL) {
				System.out.println("Sorry but " + boardLevels + " is not a legal choice.");
				boardLevels = keyboard.nextInt();
			}
			System.out.print("What size do you want the nxn boards on each level to be?\nMinimum size is "
					+ Board.MIN_SIZE + "x" + Board.MIN_SIZE + ", max is " + MAX_SIZE + "x" + MAX_SIZE
					+ ".\n==> Enter the value of n: ");

			int boardSize = keyboard.nextInt();
			while (boardSize < Board.MIN_SIZE | boardSize > MAX_SIZE) {
				System.out.println("Sorry but " + boardSize + " is not a legal choice.");
				boardSize = keyboard.nextInt();
			}
			// create the new board with user-defined dimensions
			board = new Board(boardLevels, boardSize);
		} else {
			// use default board sizes
			board = new Board();

		}
		// display the game board
		System.out.println("\nYour 3D board has been set up and looks like this: ");

		for (int i = 0; i < board.getLevel(); i++) {
			System.out.println("\nLevel " + i);
			System.out.println("----------");
			for (int j = 0; j < board.getSize(); j++) {
				for (int k = 0; k < board.getSize(); k++) {
					System.out.print("\t" + board.getEnergyAdj(i, j, k) + "\t");
				}
				System.out.println();
			}
			System.out.println();
		}

		// Get the player’s names (only one word, there is input validation
		// here)and create the Player array.
		Player[] playerArr = new Player[2];
		System.out.print("What is player 0's name (one word only): ");
		String tempStr = keyboard.nextLine();
		tempStr = keyboard.nextLine(); // do not consider the new line character
		// from the previous .nextInt()
		// we want to store only the first word (in case the user enters more
		// than one word)
		if (tempStr.indexOf(" ") > -1)
			tempStr = tempStr.substring(0, tempStr.indexOf(" "));

		Player p1 = new Player(tempStr);
		playerArr[0] = p1;
		System.out.print("What is player 1's name (one word only): ");
		tempStr = keyboard.nextLine();
		if (tempStr.indexOf(" ") > -1)
			tempStr = tempStr.substring(0, tempStr.indexOf(" "));

		Player p2 = new Player(tempStr);
		playerArr[1] = p2;

		// decide which player goes first
		int currentPlayer, otherPlayer;
		if (Math.random() * 100 > 50) {
			otherPlayer = 0;
			currentPlayer = 1;
			System.out.println("\nThe game has started " + playerArr[currentPlayer].getName()
					+ " goes first\n============================\n");

		} else {
			otherPlayer = 1;
			currentPlayer = 0;
			System.out.println("\nThe game has started " + playerArr[currentPlayer].getName()
					+ " goes first\n============================\n");
		}

		Dice die = new Dice();

		/**
		 * -----------------------------------------------------------
		 * 
		 * Start of the round
		 * 
		 * -----------------------------------------------------------
		 */
		do {
			// temporary storage for calculated Level, X, Y and Energy for the
			// currentPlayer
			int[] tmpLXYEnergy = new int[4];

			System.out.println("It is " + playerArr[currentPlayer].getName() + "'s turn");

			// check if player has enough energy to move first
			if (playerArr[currentPlayer].getEnergy() <= 0) {
				// If so toss dice 3 times and increase player 1 energy by 2 for
				// every double rolled.
				System.out.println("!!! Sorry you do not have enough energy to move.");
				for (int i = 0; i < 3; i++) {
					die.rollDice();
					if (die.isDouble()) {
						System.out.println("\tCongratulations you rolled double " + die.getDice1Roll()
								+ ". Your energy went up by 2 units.");
						playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy() + 2);
					}
				}
			} else {
				// Step 6b. Current player rolls the die
				dieRollSum = die.rollDice();
				System.out.println("\t" + playerArr[currentPlayer].getName() + " you rolled Die 1: "
						+ die.getDice1Roll() + " Die 2: " + die.getDice2Roll());
				if (die.isDouble()) {
					System.out.println("\tCongratulations you rolled double " + die.getDice1Roll()
							+ ". Your energy went up by 2 units.");
					playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy() + 2);
				}
				/*
				 * First move of the round
				 */
				tmpLXYEnergy = calcLXYEnergy(playerArr[currentPlayer].getLevel(), playerArr[currentPlayer].getX(),
						playerArr[currentPlayer].getY(), dieRollSum, board);

				tileEmpty = checkTileEmpty(tmpLXYEnergy[0], tmpLXYEnergy[1], tmpLXYEnergy[2], playerArr[otherPlayer]);
				if (tileEmpty) {

					movePlayer(tmpLXYEnergy[0], tmpLXYEnergy[1], tmpLXYEnergy[2], playerArr[currentPlayer]);
					playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy()
							+ board.getEnergyAdj(tmpLXYEnergy[0], tmpLXYEnergy[1], tmpLXYEnergy[2]));
					// player rolled past last level
					if (tmpLXYEnergy[3] == 1)
						playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy() - 2);
				} else {
					// forfeit challenge()
					challengeForfeit(board, playerArr[currentPlayer], playerArr[otherPlayer]);
				}
				System.out.println("\t" + board.toString(playerArr[currentPlayer]) + "\n");
			}

			// it is the otherPlayer's turn now
			switch (currentPlayer) {
			case 0: {
				currentPlayer = 1;
				otherPlayer = 0;
				break;
			}
			case 1: {
				currentPlayer = 0;
				otherPlayer = 1;
				break;
			}
			}// end of switch currentPlayer statement

			System.out.println("It is " + playerArr[currentPlayer].getName() + "'s turn");

			// check if player has enough energy to move first
			if (playerArr[currentPlayer].getEnergy() <= 0) {
				// If so toss dice 3 times and increase player 1 energy by 2 for
				// every double rolled.
				System.out.println("!!! Sorry you do not have enough energy to move.");
				for (int i = 0; i < 3; i++) {
					die.rollDice();
					if (die.isDouble()) {
						System.out.println("\tCongratulations you rolled double " + die.getDice1Roll()
								+ ". Your energy went up by 2 units.");
						playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy() + 2);
					}
				}
			} else {
				// Step 6b. Current player rolls the die
				dieRollSum = die.rollDice();
				System.out.println("\t" + playerArr[currentPlayer].getName() + " you rolled Die 1: "
						+ die.getDice1Roll() + " Die 2: " + die.getDice2Roll());
				if (die.isDouble()) {
					System.out.println("\tCongratulations you rolled double " + die.getDice1Roll()
							+ ". Your energy went up by 2 units.");
					playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy() + 2);
				}

				/*
				 * Second movement of the round.
				 */

				tmpLXYEnergy = calcLXYEnergy(playerArr[currentPlayer].getLevel(), playerArr[currentPlayer].getX(),
						playerArr[currentPlayer].getY(), dieRollSum, board);

				tileEmpty = checkTileEmpty(tmpLXYEnergy[0], tmpLXYEnergy[1], tmpLXYEnergy[2], playerArr[otherPlayer]);
				if (tileEmpty) {

					movePlayer(tmpLXYEnergy[0], tmpLXYEnergy[1], tmpLXYEnergy[2], playerArr[currentPlayer]);
					playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy()
							+ board.getEnergyAdj(tmpLXYEnergy[0], tmpLXYEnergy[1], tmpLXYEnergy[2]));
					// player rolled past last level (1)
					if (tmpLXYEnergy[3] == 1)
						playerArr[currentPlayer].setEnergy(playerArr[currentPlayer].getEnergy() - 2);
				} else {
					// forfeit or challenge()
					challengeForfeit(board, playerArr[currentPlayer], playerArr[otherPlayer]);
				}
				System.out.println("\t" + board.toString(playerArr[currentPlayer]) + "\n");

			}

			System.out.println("At the end of this round:\n\t" + playerArr[0].getName() + " is on level "
					+ playerArr[0].getLevel() + " at location (" + playerArr[0].getX() + "," + playerArr[0].getY()
					+ ") and has " + playerArr[0].getEnergy() + " units of energy.\n\t" + playerArr[1].getName()
					+ " is on level " + playerArr[1].getLevel() + " at location (" + playerArr[1].getX() + ","
					+ playerArr[1].getY() + ") and has " + playerArr[1].getEnergy() + " units of energy.");

			if (playerArr[currentPlayer].won(board)) {
				System.out.println("\nThe winner is player " + playerArr[currentPlayer].getName() + ". Well done!!!");
			} else if (playerArr[otherPlayer].won(board)) {
				System.out.println("\nThe winner is player " + playerArr[otherPlayer].getName() + ". Well done!!!");
			} else {

				// Wait for player to press a key to continue the match
				System.out.print("Any key to continue to next round ...");
				keyboard.nextLine();

				// change the currentPlayer for the next round
				switch (currentPlayer) {
				case 0: {
					currentPlayer = 1;
					otherPlayer = 0;
					break;
				}
				case 1: {
					currentPlayer = 0;
					otherPlayer = 1;
					break;
				}
				}// end of switch currentPlayer statement
			}

		} while (!p1.won(board) & !p2.won(board));
		// at the end of each round check if there is a winner

		keyboard.close();
	}// end of main()

	/**
	 * -----------------------------------------------------------------------------------
	 * Below are all the methods used in this driver class:
	 * -----------------------------------------------------------------------------------
	 * - movePlayer() actually sets the new level, x and y coordinates of the
	 * currentPlayer only - calcLXYEnergy() will calculate and return 3 integers
	 * for level, x and y for the currentPlayer based on the numbers of the
	 * rolled die. A 4th integer is returned to indicate whether the calculated
	 * coordinates went past the last level (integer of 1) or not (integer of 0)
	 * - checkTileEmpty() will check if the calculated level, x and y
	 * coordinates are occupied (returns True) or not (returns False). -
	 * challengeForfeit() is to perform challenge or forfeit actions based on
	 * user input
	 */
	public static void movePlayer(int finalLevel, int finalX, int finalY, Player currentPlayer) {
		currentPlayer.setLevel(finalLevel);
		currentPlayer.setX(finalX);
		currentPlayer.setY(finalY);
	}// end of movePlayer()

	public static int[] calcLXYEnergy(int level, int initX, int initY, int dieRollSum, Board board) {

		int newX = initX + dieRollSum / board.getSize();
		int newY = initY + dieRollSum % board.getSize();

		int finalX = initX, finalY = initY;
		boolean xSet = false;
		boolean ySet = false;
		int wentPastLastLevel = 0;
		int finalLevel = level;
		int totalTiles = board.getSize() * board.getSize(); // total number of
		// tiles per level
		int currentTileAmount = initY + 1 + initX * board.getSize();
		int dieRollSum_newLvl;
		/**
		 * Case 4
		 */

		if (newX > (board.getSize() - 1) && newY > (board.getSize() - 1)) {

			// recalculate X and Y from (0,0) of new level: If it actually goes
			// to a new level else It is still in the same level:
			dieRollSum_newLvl = dieRollSum + currentTileAmount - board.getSize() * board.getSize();
			if (dieRollSum_newLvl >= 0) {
				finalX = (dieRollSum_newLvl - 1) / board.getSize();
				finalY = (dieRollSum_newLvl - 1) % board.getSize();
			} else {
				dieRollSum_newLvl = dieRollSum + currentTileAmount;
				finalX = (dieRollSum_newLvl - 1) / board.getSize();
				finalY = (dieRollSum_newLvl - 1) % board.getSize();
			}

			// if the currentPlayer is on the last level of the board and they
			// are supposed to move to a level PAST the last level
			if ((level + 1) >= board.getLevel() && (currentTileAmount + dieRollSum) > totalTiles) {

				wentPastLastLevel = 1;
				finalLevel = level;
				finalX = initX;
				finalY = initY;
				System.out.println(
						"!!! Sorry you need to stay where you are = that throw takes you off the grid and you loose 2 units of energy.");

			} else if ((currentTileAmount + dieRollSum) <= totalTiles) {
				// it is still in the same level

				finalLevel = level;

			} else {

				// move the player up 1 level and to the new place
				finalLevel = level + 1;

			}

		} // end of Case 4 if statement

		/**
		 * Case 2
		 */
		if (newX <= (board.getSize() - 1) && newY > (board.getSize() - 1)) {

			// recalculate X and Y from (0,0) of new level: If it actually goes
			// to a new level else It is still in the same level:
			dieRollSum_newLvl = dieRollSum + currentTileAmount - board.getSize() * board.getSize();
			if (dieRollSum_newLvl >= 0) {
				finalX = (dieRollSum_newLvl - 1) / board.getSize();
				finalY = (dieRollSum_newLvl - 1) % board.getSize();
			} else {
				dieRollSum_newLvl = dieRollSum + currentTileAmount;
				finalX = (dieRollSum_newLvl - 1) / board.getSize();
				finalY = (dieRollSum_newLvl - 1) % board.getSize();
			}
			xSet = true;
			// player can potentially move past the last level
			if ((level + 1) >= board.getLevel() && (currentTileAmount + dieRollSum) > totalTiles) {
				// don't move the play but make them lose 2 energy units if
				// they are already on the last level

				wentPastLastLevel = 1;
				finalLevel = level;
				finalX = initX;
				finalY = initY;
				System.out.println(
						"!!! Sorry you need to stay where you are = that throw takes you off the grid and you loose 2 units of energy.");

			} else if ((currentTileAmount + dieRollSum) <= totalTiles) {
				// if the new position is still in the same level

				finalLevel = level;

			} else {

				// move the player up 1 level and to the new place
				finalLevel = level + 1;

			}
		} // end of case 2 if statement

		/**
		 * Case 3
		 */
		if (newX > (board.getSize() - 1) && newY <= (board.getSize() - 1)) {

			// recalculate X and Y from (0,0) of new level: If it actually goes
			// to a new level else It is still in the same level:
			dieRollSum_newLvl = dieRollSum + currentTileAmount - board.getSize() * board.getSize();
			if (dieRollSum_newLvl >= 0) {
				finalX = (dieRollSum_newLvl - 1) / board.getSize();
				finalY = (dieRollSum_newLvl - 1) % board.getSize();
			} else {
				dieRollSum_newLvl = dieRollSum + currentTileAmount;
				finalX = (dieRollSum_newLvl - 1) / board.getSize();
				finalY = (dieRollSum_newLvl - 1) % board.getSize();
			}
			ySet = true;

			if ((level + 1) >= board.getLevel() && (currentTileAmount + dieRollSum) > totalTiles) {

				// don't move the play but make them lose 2 energy units if
				// they are already on the last level
				wentPastLastLevel = 1;
				finalLevel = level;
				finalX = initX;
				finalY = initY;
				System.out.println(
						"!!! Sorry you need to stay where you are - that throw takes you off the grid and you loose 2 units of energy.");
			} else if ((currentTileAmount + dieRollSum) <= totalTiles) {
				// it is still in the same level

				finalLevel = level;

			} else {

				// move the player up 1 level and to the new place
				finalLevel = level + 1;

			}
		} // end of Case 3 if statement

		/**
		 * Case 1 where both x and y do not exceed board size
		 */
		if (newX <= (board.getSize() - 1) && !xSet) {

			finalX = newX;
			finalLevel = level;

		}

		if (newY <= (board.getSize() - 1) && !ySet) {

			finalY = newY;
			finalLevel = level;

		}
		int[] res = new int[4];
		res[0] = finalLevel;
		res[1] = finalX;
		res[2] = finalY;
		res[3] = wentPastLastLevel; // integer

		return res;
	}// end of calcLXYEnergy()

	public static boolean checkTileEmpty(int tmpLevel, int tmpX, int tmpY, Player otherPlayer) {
		// if a player occupies the new tile and this is NOT the firstMove of
		// the game
		if (tmpLevel == otherPlayer.getLevel() & tmpX == otherPlayer.getX() & tmpY == otherPlayer.getY()) {

			return false;
		} else {
			return true;
		}

	}// end of checkTileEmpty()

	public static void challengeForfeit(Board board, Player currentPlayer, Player otherPlayer) {

		System.out.println("Player " + otherPlayer.getName()
				+ " is at your new location\nWhat do you want to do?\n\t0 - Challenge and risk loosing 50% of your energy units if you loose\n\t\tor move to new location and get 50% of the other player's energy units\n\t1 - Move down one level or move to (0,0) if at level 0 and lose 2 energy\n\t\tunits");
		String challengeChoice = keyboard.next();
		while (!challengeChoice.equals("0") & !challengeChoice.equals("1")) {
			System.out.println("Sorry but " + challengeChoice + " is not a legal choice.");
			challengeChoice = keyboard.next();
		}
		int challengeChoiceInt = Integer.parseInt(challengeChoice);
		switch (challengeChoiceInt) {
		case 0:
			// Choice 0: challenge the spot
			if (Math.random() * 10 < 6) {
				// currentPlayer loses challenge.
				// Therefore they do not move AND they
				// lose half
				// of their energy
				currentPlayer.setEnergy(currentPlayer.getEnergy() / 2);
				System.out.println("Uh oh!! You lost the challenge.");

				break;
			} else {
				// currentPlayer wins challenge and
				// swaps positions with otherPlayer
				System.out.println("Bravo!! You won the challenge.");
				currentPlayer.moveTo(otherPlayer);
				// otherPlayer gives half their energy
				// to currentPlayer who won the
				// challenge
				otherPlayer.setEnergy(otherPlayer.getEnergy() / 2);
				currentPlayer.setEnergy(currentPlayer.getEnergy() + otherPlayer.getEnergy()
						+ board.getEnergyAdj(currentPlayer.getLevel(), currentPlayer.getX(), currentPlayer.getY()));
				break;
			} // end of challenge if-else statement

		case 1:

			// Choice 1: currentPlayer forfeits
			if ((currentPlayer.getLevel() - 1) < 0) {
				// move to (0,0) level 0
				currentPlayer.setX(0);
				currentPlayer.setY(0);
				currentPlayer.setLevel(0);
				currentPlayer.setEnergy(currentPlayer.getEnergy() - 2);
				break;
			} else {
				// move down 1 level but to your same spot where
				// you
				// were
				currentPlayer.setLevel(currentPlayer.getLevel() - 1);
				currentPlayer.setEnergy(currentPlayer.getEnergy() - 2
						+ board.getEnergyAdj(currentPlayer.getLevel(), currentPlayer.getX(), currentPlayer.getY()));
				break;
			}

		}// end of the challenge Switch statement
	}// end of challengeForfeit()

}// end of LetUsPlay driver class
