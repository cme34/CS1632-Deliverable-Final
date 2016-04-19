import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class CardTest {
	Random rand;
	
	/**
	 * Before each test, initialize Random
	 */
	@Before
	public void init() {
		rand = new Random();
	}
	
	/**
	 * Test if getting the cards face works properly. 
	 */
	@Test
	public void testCardIDReturnsRightFace() {
		int r = rand.nextInt(Card.AMOUNT_OF_CARDS);
		Card c = new Card(r, false, false, null);
		assertEquals(Card.computeFace(r), c.getFace());
	}
	
	/**
	 * Test if getting the cards suit works properly. 
	 */
	@Test
	public void testCardIDReturnsRightSuit() {
		int r = rand.nextInt(Card.AMOUNT_OF_CARDS);
		Card c = new Card(r, false, false, null);
		assertEquals(Card.computeSuit(r), c.getSuit());
	}
	
	/**
	 * Test if computing a card's id from its face and suit works properly. 
	 */
	@Test
	public void testComputeCardID() {
		int r = rand.nextInt(Card.AMOUNT_OF_CARDS);
		Card c = new Card(r, false, false, null);
		assertEquals(r, Card.computeCardID(c.getFace(), c.getSuit()));
	}
	
	/**
	 * Test if equals works properly. 
	 */
	@Test
	public void testEquals() {
		int r = rand.nextInt(Card.AMOUNT_OF_CARDS);
		Card c1 = new Card(r, false, false, null);
		Card c2 = new Card(r, false, false, null);
		assertEquals(c1, c2);
	}
	
	/**
	 * Test to see if rip image properly rips an image. 
	 * WARNING: This does not guarantee the actual correct image for a card, 
	 * but rather that an image is create to the right size. 
	 */
	@Test
	public void testRipImage() {
		int r = rand.nextInt(Card.AMOUNT_OF_CARDS);
		Card c = new Card(r, false, false, null);
		c.setImage(Card.ripImage(c.getFace(), c.getSuit()));
		Dimension d = new Dimension(Card.CARD_WIDTH, Card.CARD_HEIGHT);
		assertEquals(d, c.getSize());
	}
	
	/**
	 * Test to see if giving bad input to rip image returns null. 
	 */
	@Test
	public void testRipImageBadInput() {
		int r = rand.nextInt(Card.AMOUNT_OF_CARDS);
		Card c = new Card(r, false, false, null);
		c.setImage(Card.ripImage(-1, -1));
		assertNull(c.getImage());
	}
}
