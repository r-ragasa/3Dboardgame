
// -------------------------------------------------------
// Assignment A4
// Written by:  Rodger S. Ragasa
// For COMP 248 Section (FF) – Fall 2019
// --------------------------------------------------------
/**
 * Class to define the 2 dices
 *
 */
public class Dice {

	// A Dice object has 2 attributes:
	// i. an integer die1 that stores the role of a 1st die.
	// ii. an integer die2 that stores the role of a 2nd die.
	private static int die1, die2;

	// constructor
	public Dice() {
	}

	// Accessor methods for the die1 and die2 attribute.

	public int getDice1Roll() {
		return die1;
	}

	public int getDice2Roll() {
		return die2;
	}

	// Simulate the rolling of 2 dice and assigns a value to
	// die1 and die2. You can use the random number generator of your choice.
	// Remember a roll is between 1 and 6. This method returns the sum of die1
	// and die2

	public int rollDice() {

		// assign values to each die between 1 and 6
		do {
			die1 = (int) (Math.random() * 6);
			die2 = (int) (Math.random() * 6);
		} while ((die1 < 1 || die1 > 6) || (die2 < 1 || die2 > 6));
		// return sum of die1 and
		return die1 + die2;
	}// end of rollDice()

	// isDouble() which returns the Boolean value true if die1 is equal to die2
	// and the Boolean false otherwise.
	public boolean isDouble() {
		return getDice1Roll() == getDice2Roll();
	}

	// toString() which returns a String containing the values of both
	// attributes in a descriptive message.
	public String toString() {
		return "Die 1: " + getDice1Roll() + " Die 2: " + getDice2Roll();
	}

}// end of Dice class
