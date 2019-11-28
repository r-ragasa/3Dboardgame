// -------------------------------------------------------
// Assignment A4
// Written by:  Rodger S. Ragasa
// For COMP 248 Section (FF) – Fall 2019
// --------------------------------------------------------
/**
 * 
 * Class to define the 2 player objects.
 *
 */
public class Player {
	// A Player object has 5 attributes:
	// i. a String name that stores a player`s name
	// ii. Four integers level, x, y and energy which represent the location of
	// a player and the energy units that the player has.
	String name;
	int level, x, y, energy;

	public Player() {
		name = "";
		energy = 10;
		level = 0;
		x = 0;
		y = 0;
	}

	public Player(String playerName) {
		name = playerName;
		energy = 10;
		level = 0;
		x = 0;
		y = 0;
	}

	// A constructor which takes 3 integer parameters, represent a location and
	// assigns these values to the new object, 10 to energy and an empty string
	// ("") to name.

	public Player(int lvl, int xPos, int yPos) {
		energy = 10;
		name = "";
		level = lvl;
		x = xPos;
		y = yPos;
	}

	// copy constructor
	public Player(Player copyOfPlayer) {
		name = copyOfPlayer.name;
		energy = copyOfPlayer.energy;
		level = copyOfPlayer.level;
		x = copyOfPlayer.x;
		y = copyOfPlayer.y;
	}

	// mutator methods. I use this. to know which attribute of the particular
	// player
	// I want to change
	public void setName(String newName) {
		this.name = newName;
	}

	public void setEnergy(int newEnergy) {
		this.energy = newEnergy;
	}

	public void setLevel(int newLevel) {
		this.level = newLevel;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	// accessor methods
	public String getName() {
		return this.name;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int getLevel() {
		return this.level;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	// moveTo(Player p) whose purpose is to move the calling Player object to
	// the passed Player object’s location (the level, x, and y of the calling
	// object are changed to the level, x, and y of the passed object). The name
	// and energy level of the calling object are not changed. (Useful when
	// swapping locations).

	public void moveTo(Player p) {
		int tmpL = this.level;
		int tmpX = this.x;
		int tmpY = this.y;
		this.level = p.level;
		this.x = p.x;
		this.y = p.y;
		p.level = tmpL;
		p.x = tmpX;
		p.y = tmpY;

	}

	// won(Board b) a Boolean method which return true if the calling object is
	// at the last location of the board and false otherwise (top level and
	// largest x and y possible). See Board object for details.
	public boolean won(Board b) {
		if (this.getLevel() == b.getLevel() - 1 & this.getX() == b.getSize() - 1 & this.getY() == b.getSize() - 1)
			return true;
		else
			return false;
	}

	// equals(Player p) which returns true if the location of the calling object
	// is the same as the location of the passed object. So just compare the
	// level, x, and y of both objects.
	public boolean equals(Player p) {
		if (this.getLevel() == p.getLevel() & this.getX() == p.getX() & this.getY() == p.getY())
			return true;
		else
			return false;
	}

	// toString() which returns a String containing the values of all attributes
	// of the calling Player object in a descriptive message.
	public String toString() {
		return this.getName() + " is on level " + this.getLevel() + " at location (" + this.getX() + "," + this.getY()
				+ ") and has " + this.getEnergy() + " units of energy.";
	}

}// end of Player class
