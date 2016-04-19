import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	Game game;
	
	/**
	 * Before Each Test, Initialize Game. 
	 */
	@Before
	public void init() {
		game = new Game();
	}
	
	/**
	 * Test to see if Game's constructor initializes the game properly. 
	 */
	@Test
	public void testIfInitializesInBettingState() {
		boolean pass = true;
		if (game.getState() != Game.STATE_BETTING) pass = false;
		//Is betting box on screen
		if (game.getBettingSystem().getX() == 0) pass = false;
		for (int i = 0; i < Game.HAND_SIZE; i++)
			if (game.getHand()[i] != null) pass = false;
		//Nothing failed, then it should pass
		assertTrue(pass);
	}
	
	/**
	 * Test to see if drawCard draws from the top of the deck. 
	 */
	@Test
	public void testDrawCard() {
		game.shuffleCards();
		Card c = game.drawCard();
		assertSame(game.getCards()[game.getDrawnPoint()], c);
	}
	
	/**
	 * Test to see if drawCard returns null like it is supposed to when. 
	 */
	@Test
	public void testDrawAllCards() {
		game.shuffleCards();
		for (int i = 0; i < Card.AMOUNT_OF_CARDS; i++)
			game.drawCard();
		Card c = game.drawCard();
		assertNull(c);
	}
	
	/**
	 * Test drawHand when each element of hand is null. All elements should be filled afterwards. 
	 */
	@Test
	public void testDrawHandWithEmptyHand() {
		game.shuffleCards();
		game.drawHand();
		boolean pass = true;
		for (int i = 0; i < Game.HAND_SIZE; i++)
			if (game.getHand()[i] == null) pass = false;
		assertTrue(pass);
	}
	
	/**
	 * Test drawHand when the first card is held. The card should not change afterwards. 
	 */
	@Test
	public void testDrawHandWithCardsHeld() {
		game.shuffleCards();
		game.drawHand();
		Card c = game.getHand()[0];
		game.getHand()[0].setCardHeld(true);
		game.drawHand();
		assertSame(c, game.getHand()[0]);
	}
	
	/**
	 * Test drawHand when the first card is not held. The card should change afterwards. 
	 */
	@Test
	public void testDrawHandWithoutCardsHeld() {
		game.shuffleCards();
		game.drawHand();
		Card c = game.getHand()[0];
		game.getHand()[0].setCardHeld(false);
		game.drawHand();
		assertNotSame(c, game.getHand()[0]);
	}
	
	/**
	 * Test reseting the deck after a round. Drawn point should be reset back to equal the Amount of Cards. 
	 */
	@Test
	public void testPutAllCardsBackInDeck() {
		game.clearHand();
		game.putAllCardsBackInDeck();
		assertEquals(Card.AMOUNT_OF_CARDS, game.getDrawnPoint());
	}
	
	/**
	 * Test to see if you get the appropriate payout from having a certain combination. 
	 * WARNING: Only tests Royal Flush. This is because the OutcomesTest basically tests most of this code. 
	 * If it works for one case, it is with high probability that it works with all since it is a simple if statement.  
	 */
	@Test
	public void testGetPayouts() {
		game.getBettingSystem().setBet(1);
		game.getHand()[0] = game.getCards()[0];
		game.getHand()[1] = game.getCards()[9];
		game.getHand()[2] = game.getCards()[10];
		game.getHand()[3] = game.getCards()[11];
		game.getHand()[4] = game.getCards()[12];
		assertEquals(Game.PAYOUT_ROYAL_FLUSH, game.getPayout());
	}
	
	/**
	 * This test simulates a round of the game and checks if it makes it back to the starting state.
	 */
	@Test
	public void testRoundOfGame() {
		game.getBettingSystem().getTextFeild().setText("1");
		game.getBettingSystem().actionBetButton(null);
		game.actionTimer(null);
		game.actionDrawButton(null);
		game.actionTimer(null);
		game.actionResultButton(null);
		game.actionTimer(null);
		assertEquals(Game.STATE_BETTING, game.getState());
	}
}
