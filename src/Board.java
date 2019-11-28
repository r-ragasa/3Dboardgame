
// -------------------------------------------------------
// Assignment A4
// Written by:  Rodger S. Ragasa
// For COMP 248 Section (FF) – Fall 2019
// --------------------------------------------------------
/**
 * Class to define the board
 *
 */

public class Board {
	// A Board object has 5 attributes:
	// i. A 3D array called board
	// ii. A static integer constant MIN_LEVEL which is set to 3
	// iii. A static integer constant MIN_SIZE which is set to 3
	// iv. An integer level which records how many levels this board has.
	// v. An integer size which represents the dimensions (size x size) of the
	// game board at each level.

	static final int MIN_LEVEL = 3;
	static final int MIN_SIZE = 3;
	int level;
	int size;
	int[][][] board;

	// A default constructor which sets the level to 3, the size to 4 and calls
	// the method createBoard() to create board using the values of level and
	// size assigned in the constructor.

	public Board() {
		level = 3;
		size = 4;

		createBoard(level, size);
	}

	// A constructor that has 2 integer parameters l and x which represents the
	// number of levels and the number of rows and columns the boards at each
	// level have. Just like the default constructor, it calls the method
	// createBoard() to create the board but
	// uses the passed values of l and x. (See below)

	public Board(int l, int s) {
		level = l;
		size = s;
		createBoard(l, s);
	}

	// A private method createBoard() which has 2 arguments: level and size. It
	// creates the3D -array board using the passed dimensions. It then
	// initialized each location to the following values. If the sum of level, x
	// and y:
	// i. is a multiple of 3, assign -3 to the location board[l][x][y]
	// ii. Is a multiple of 5, assign -2 to the location board[l][x][y]
	// iii. Is a multiple of 7, assign 2 to the location board[l][x][y]
	// iv. Otherwise assign 0 to the location board[l][x][y]
	// v. Note: Only one of these can be applied and it is the 1st condition
	// that is satisfied in the order listed above.

	private void createBoard(int level, int size) {
		// initialize board size
		board = new int[level][size][size];
		int sum;

		// initialize values for each tile on the board for each level
		for (int l = 0; l < level; l++) {
			for (int sx = 0; sx < size; sx++) {
				for (int sy = 0; sy < size; sy++) {
					sum = l + sx + sy;

					if (sum % 3 == 0)
						board[l][sx][sy] = -3;
					else if (sum % 5 == 0)
						board[l][sx][sy] = -2;
					else if (sum % 7 == 0)
						board[l][sx][sy] = 2;
					else
						board[l][sx][sy] = 0;
				}
			}
		}
		// set Level 0 tile (0,0) to a zero value because we assume players
		// cannot lose energy at the very beginning of the game
		board[0][0][0] = 0;
	}

	// Accessor methods for level and size
	public int getLevel() {
		return level;
	}

	public int getSize() {
		return size;
	}

	// getEnergyAdj(int l, int x, int y) which returns the energy adjustment
	// value stored in that location in the array board.

	public int getEnergyAdj(int l, int x, int y) {
		return board[l][x][y];
	}

	// toString() which returns a string showing the energy adjustments
	// values for each board at each level. (Refer to sample output)

	public String toString(Player player) {
		return "Your energy is adjusted by " + getEnergyAdj(player.getLevel(), player.getX(), player.getY())
				+ " for landing at (" + player.getX() + "," + player.getY() + ") at level " + player.getLevel();
	}

}// end of Board class
