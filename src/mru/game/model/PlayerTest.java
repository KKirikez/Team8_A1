package mru.game.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the Player class.
 */
class PlayerTest {

	/**
	 * Test method to verify the behavior of the Player class.
	 */
	@Test
	void test() {
		Player test = new Player("Name", 10, 90);
		assertEquals("Name", test.getName());
		assertEquals(10, test.getBalance());
		assertEquals(90, test.getNumOfWins());

		test.setName("test");
		test.setBalance(100);
		test.setNumOfWins(9);

		assertEquals("test", test.getName());
		assertEquals(100, test.getBalance());
		assertEquals(9, test.getNumOfWins());
	}

}
