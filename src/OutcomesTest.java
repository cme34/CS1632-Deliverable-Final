import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * ***********************************************************<br />
 * ***WARNING: THESE TESTS ASSUME HAND_SIZE = 5***<br />
 * ***********************************************************<br />
 * 
 * @author Cory
 */
public class OutcomesTest {
	Card[] cards;
	Card[] hand;
	
	/**
	 * Before each test, Initialize cards and hand.
	 */
	@Before
	public void init() {
		cards = new Card[Card.AMOUNT_OF_CARDS];
		for (int i = 0; i < Card.AMOUNT_OF_CARDS; i++)
			cards[i] = new Card(i, false, false, null);
		hand = new Card[Game.HAND_SIZE];
		for (int i = 0; i < Game.HAND_SIZE; i++)
			hand[i] = null;
	}

	@Test
	public void testIsRoyalFlushValid() {
		hand[0] = cards[0];
		hand[1] = cards[9];
		hand[2] = cards[10];
		hand[3] = cards[11];
		hand[4] = cards[12];
		assertTrue(Outcomes.isRoyalFlush(hand));
	}

	@Test
	public void testIsNotRoyalFlushOnlyStraight() {
		hand[0] = cards[13];
		hand[1] = cards[9];
		hand[2] = cards[10];
		hand[3] = cards[11];
		hand[4] = cards[12];
		assertFalse(Outcomes.isRoyalFlush(hand));
	}

	@Test
	public void testIsNotRoyalFlushOnlyFlush() {
		hand[0] = cards[1];
		hand[1] = cards[9];
		hand[2] = cards[10];
		hand[3] = cards[11];
		hand[4] = cards[12];
		assertFalse(Outcomes.isRoyalFlush(hand));
	}

	@Test
	public void testIsStraightFlush() {
		hand[0] = cards[3];
		hand[1] = cards[4];
		hand[2] = cards[5];
		hand[3] = cards[6];
		hand[4] = cards[7];
		assertTrue(Outcomes.isStraightFlush(hand));
	}

	@Test
	public void testIsStraightFlushAceLow() {
		hand[0] = cards[0];
		hand[1] = cards[1];
		hand[2] = cards[2];
		hand[3] = cards[3];
		hand[4] = cards[4];
		assertTrue(Outcomes.isStraightFlush(hand));
	}

	@Test
	public void testIsNotStraightFlushOnlyStraight() {
		hand[0] = cards[14];
		hand[1] = cards[2];
		hand[2] = cards[3];
		hand[3] = cards[4];
		hand[4] = cards[5];
		assertFalse(Outcomes.isStraightFlush(hand));
	}
	
	@Test
	public void testIsNotStraightFlushOnlyFlush() {
		hand[0] = cards[1];
		hand[1] = cards[3];
		hand[2] = cards[4];
		hand[3] = cards[5];
		hand[4] = cards[6];
		assertFalse(Outcomes.isStraightFlush(hand));
	}

	@Test
	public void testIsFourOfAKind() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[26];
		hand[3] = cards[39];
		hand[4] = cards[1];
		assertTrue(Outcomes.isFourOfAKind(hand));
	}

	@Test
	public void testIsNotFourOfAKind() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[26];
		hand[3] = cards[1];
		hand[4] = cards[2];
		assertFalse(Outcomes.isFourOfAKind(hand));
	}

	@Test
	public void testIsFullHouse() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[26];
		hand[3] = cards[1];
		hand[4] = cards[14];
		assertTrue(Outcomes.isFullHouse(hand));
	}

	@Test
	public void testIsNotFullHouseOnlyThreeOfAKind() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[26];
		hand[3] = cards[1];
		hand[4] = cards[2];
		assertFalse(Outcomes.isFullHouse(hand));
	}

	@Test
	public void testIsNotFullHouseOnlyTwoPair() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[1];
		hand[3] = cards[2];
		hand[4] = cards[3];
		assertFalse(Outcomes.isFullHouse(hand));
	}

	@Test
	public void testIsFlush() {
		hand[0] = cards[1];
		hand[1] = cards[2];
		hand[2] = cards[3];
		hand[3] = cards[4];
		hand[4] = cards[5];
		assertTrue(Outcomes.isFlush(hand));
	}

	@Test
	public void testIsNotFlush() {
		hand[0] = cards[0];
		hand[1] = cards[1];
		hand[2] = cards[2];
		hand[3] = cards[3];
		hand[4] = cards[16];
		assertFalse(Outcomes.isFlush(hand));
	}

	@Test
	public void testIsStraight() {
		hand[0] = cards[1];
		hand[1] = cards[2];
		hand[2] = cards[3];
		hand[3] = cards[4];
		hand[4] = cards[18];
		assertTrue(Outcomes.isStraight(hand));
	}

	@Test
	public void testIsStraightAceLow() {
		hand[0] = cards[13];
		hand[1] = cards[1];
		hand[2] = cards[2];
		hand[3] = cards[3];
		hand[4] = cards[4];
		assertTrue(Outcomes.isStraight(hand));
	}

	@Test
	public void testIsStraightAceHigh() {
		hand[0] = cards[9];
		hand[1] = cards[10];
		hand[2] = cards[11];
		hand[3] = cards[12];
		hand[4] = cards[13];
		assertTrue(Outcomes.isStraight(hand));
	}

	@Test
	public void testIsNotStraight() {
		hand[0] = cards[0];
		hand[1] = cards[2];
		hand[2] = cards[4];
		hand[3] = cards[6];
		hand[4] = cards[8];
		assertFalse(Outcomes.isStraight(hand));
	}

	@Test
	public void testIsThreeOfAKind() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[26];
		hand[3] = cards[1];
		hand[4] = cards[2];
		assertTrue(Outcomes.isThreeOfAKind(hand));
	}

	@Test
	public void testIsNotThreeOfAKind() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[1];
		hand[3] = cards[14];
		hand[4] = cards[2];
		assertFalse(Outcomes.isThreeOfAKind(hand));
	}

	@Test
	public void testIsTwoPair() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[1];
		hand[3] = cards[14];
		hand[4] = cards[2];
		assertTrue(Outcomes.isTwoPair(hand));
	}

	@Test
	public void testIsNotTwoPair() {
		hand[0] = cards[0];
		hand[1] = cards[13];
		hand[2] = cards[1];
		hand[3] = cards[2];
		hand[4] = cards[3];
		assertFalse(Outcomes.isTwoPair(hand));
	}

	@Test
	public void testIsPairJacksOrBetter() {
		hand[0] = cards[10];
		hand[1] = cards[23];
		hand[2] = cards[0];
		hand[3] = cards[1];
		hand[4] = cards[2];
		assertTrue(Outcomes.isPairJacksOrHigher(hand));
	}

	@Test
	public void testIsNotPairJacksOrBetter() {
		hand[0] = cards[1];
		hand[1] = cards[14];
		hand[2] = cards[2];
		hand[3] = cards[3];
		hand[4] = cards[4];
		assertFalse(Outcomes.isPairJacksOrHigher(hand));
	}
}
