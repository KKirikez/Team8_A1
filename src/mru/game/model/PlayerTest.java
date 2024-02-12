package mru.game.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void test() {
		Player test = new Player("Name", 10, 90);
		assertEquals(test.getName(),"Name");
		assertEquals(test.getBalance(),10);
		assertEquals(test.getNumOfWins(),90);
		
		test.setName("test");
		test.setBalance(100);
		test.setNumOfWins(9);
		
		assertEquals(test.getName(),"test");
		assertEquals(test.getBalance(),100);
		assertEquals(test.getNumOfWins(),9);
		
		
	}

}
