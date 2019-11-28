import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DiceTest {

	@Parameterized.Parameters
	public static Object[][] data() {
		return new Object[200000][0]; // [i][j], where i is the number of times
										// you want it to run
	}

	// this is the Test you want to run i times
	@Test
	public void testRollDice() {
		int sumDiceValues;
		Dice rollDiceTest = new Dice();
		sumDiceValues = rollDiceTest.rollDice();
		assertTrue(sumDiceValues > 0 & sumDiceValues < 13);
	}

	// @RunWith(Parameterized.class)
	// public class RunTenTimes {
	//
	// @Parameterized.Parameters
	// public static Object[][] data() {
	// return new Object[10][0];
	// }
	//
	// public RunTenTimes() {
	// }
	//
	// @Test
	// public void runsTenTimes() {
	// System.out.println("run");
	// }
	// }
}
