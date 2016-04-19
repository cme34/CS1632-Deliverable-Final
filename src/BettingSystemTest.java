import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BettingSystemTest {
	BettingSystem bs;
	
	/**
	 * Before each test, Initialize. 
	 */
	@Before
	public void init() {
		bs = new BettingSystem();
		bs.setBettingState(true);
		bs.setTokens(100);
	}
	
	/**
	 * Test bet with the valid input of 1 (lowest valid value). 
	 */
	@Test
	public void testBetWithValidInputLowest() {
		bs.getTextFeild().setText("1");
		bs.actionBetButton(null);
		assertEquals(1, bs.getBet());
	}
	
	/**
	 * Test bet with the valid input of amount of tokens (highest valid value). 
	 */
	@Test
	public void testBetWithValidInputHighest() {
		bs.getTextFeild().setText("" + bs.getTokens());
		bs.actionBetButton(null);
		assertEquals(bs.getTokens(), bs.getBet());
	}
	
	/**
	 * Test bet with the invalid input of nothing.  
	 */
	@Test
	public void testBetWithInvalidInputEmpty() {
		bs.getTextFeild().setText("");
		bs.actionBetButton(null);
		assertEquals(0, bs.getBet());
	}
	
	/**
	 * Test bet with the invalid input of a string. 
	 */
	@Test
	public void testBetWithInvalidInputString() {
		bs.getTextFeild().setText("test");
		bs.actionBetButton(null);
		assertEquals(0, bs.getBet());
	}
	
	/**
	 * 
	 */
	@Test
	public void testBetWithInvalidInputZero() {
		bs.getTextFeild().setText("0");
		bs.actionBetButton(null);
		assertEquals(0, bs.getBet());
	}
	
	/**
	 * Test bet with the invalid input of 0. 
	 */
	@Test
	public void testBetWithInvalidInputNegNumber() {
		bs.getTextFeild().setText("-1");
		bs.actionBetButton(null);
		assertEquals(0, bs.getBet());
	}
	
	/**
	 * Test bet with the invalid input of amount of tokens + 1. 
	 */
	@Test
	public void testBetWithInvalidInputToHigh() {
		bs.getTextFeild().setText("" + bs.getTokens() + 1);
		bs.actionBetButton(null);
		assertEquals(0, bs.getBet());
	}
}
