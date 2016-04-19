import java.util.Arrays;

/**
 * This class contains all global functions that check for the different outcomes of poker. 
 * @author Cory
 */
public class Outcomes {
	/**
	 * Checks if the cards in the hand are a Royal Flush
	 * @param hand
	 * @return true or false
	 */
	public static boolean isRoyalFlush(Card[] hand) {
		//Check for flush
		if (!isFlush(hand)) return false;
		//Do a specific straight check
		int[] order = new int[Game.HAND_SIZE];
		for (int i = 0; i < order.length; i++) {
			order[i] = hand[i].getFace();
			if (order[i] == 0) {
				order[i] = Card.AMOUNT_OF_FACES;
			}
		}
		Arrays.sort(order);
		boolean thisIsRF = true;
		for (int i = Game.HAND_SIZE - 1; i > 0; i--) {
			if (order[i] - 1 != order[i - 1]) {
				thisIsRF = false;
				break;
			}
		}
		return thisIsRF;
	}
	
	/**
	 * Checks if the cards in the hand are a Straight Flush
	 * @param hand
	 * @return true or false
	 */
	public static boolean isStraightFlush(Card[] hand) {
		if (!isStraight(hand)) return false;
		if (!isFlush(hand)) return false;
		return true;
	}
	
	/**
	 * Checks if the cards in the hand are a Four of a Kind
	 * @param hand
	 * @return true or false
	 */
	public static boolean isFourOfAKind(Card[] hand) {
		int matches = 0;
		for (int i = 0; i < Game.HAND_SIZE; i++) {
			matches = 0;
			for (int j = 0; j < Game.HAND_SIZE; j++) {
				if (hand[i].getFace() == hand[j].getFace())	matches++;
			}
			if (matches == 4) return true;
		}
		return false;
	}
	
	/**
	 * Checks if the cards in the hand are a Full House
	 * @param hand
	 * @return true or false
	 */
	public static boolean isFullHouse(Card[] hand) {
		int[] order = new int[Game.HAND_SIZE];
		for (int i = 0; i < order.length; i++) {
			order[i] = hand[i].getFace();
			if (order[i] == 0) {
				order[i] = Card.AMOUNT_OF_FACES;
			}
		}
		Arrays.sort(order);
		if (hand[0].getFace() == hand[1].getFace() && hand[0].getFace() == hand[2].getFace() && hand[3].getFace() == hand[4].getFace())
			return true;
		else if (hand[0].getFace() == hand[1].getFace() && hand[2].getFace() == hand[3].getFace() && hand[2].getFace() == hand[4].getFace())
			return true;
		return false;
	}
	
	/**
	 * Checks if the cards in the hand are a Flush
	 * @param hand
	 * @return true or false
	 */
	public static boolean isFlush(Card[] hand) {
		int suit = hand[0].getSuit();
		boolean thisIsFlush = true;
		for (int i = 0; i < Game.HAND_SIZE; i++) {
			if (hand[i].getSuit() != suit) {
				thisIsFlush = false;
			}
		}
		return thisIsFlush;
	}
	
	/**
	 * Checks if the cards in the hand are a Straight
	 * @param hand
	 * @return true or false
	 */
	public static boolean isStraight(Card[] hand) {
		//Check for straight aces low
		int[] order = new int[Game.HAND_SIZE];
		for (int i = 0; i < order.length; i++) {
			order[i] = hand[i].getFace();
		}
		Arrays.sort(order);
		boolean thisIsStraight = true;
		for (int i = Game.HAND_SIZE - 1; i > 0; i--) {
			if (order[i] - 1 != order[i - 1]) {
				thisIsStraight = false;
				break;
			}
		}
		
		if (thisIsStraight) return true;
		
		//Check for straight aces high
		order = new int[Game.HAND_SIZE];
		for (int i = 0; i < order.length; i++) {
			order[i] = hand[i].getFace();
			if (order[i] == 0) {
				order[i] = Card.AMOUNT_OF_FACES;
			}
		}
		Arrays.sort(order);
		thisIsStraight = true;
		for (int i = Game.HAND_SIZE - 1; i > 0; i--) {
			if (order[i] - 1 != order[i - 1]) {
				thisIsStraight = false;
				break;
			}
		}
		
		return thisIsStraight;
	}
	
	/**
	 * Checks if the cards in the hand are a Three of a Kind
	 * @param hand
	 * @return true or false
	 */
	public static boolean isThreeOfAKind(Card[] hand) {
		int matches = 0;
		for (int i = 0; i < Game.HAND_SIZE; i++) {
			matches = 0;
			for (int j = 0; j < Game.HAND_SIZE; j++) {
				if (hand[i].getFace() == hand[j].getFace())	matches++;
			}
			if (matches == 3) return true;
		}
		return false;
	}
	
	/**
	 * Checks if the cards in the hand are a Two Pair
	 * @param hand
	 * @return true or false
	 */
	public static boolean isTwoPair(Card[] hand) {
		int matches = 0;
		for (int i = 0; i < Game.HAND_SIZE; i++) {
			for (int j = 0; j < Game.HAND_SIZE; j++) {
				if (hand[i].getFace() == hand[j].getFace()) matches++;
			}
		}
		//9 because each pair counts itself and its counter part so 2 + 2 + 2 + 2 and plus 1 for the lone card counting itself
		if (matches == 9) return true;
		return false;
	}
	
	/**
	 * Checks if the cards in the hand are a Jack or Higher
	 * @param hand
	 * @return true or false
	 */
	public static boolean isPairJacksOrHigher(Card[] hand) {
		int matches = 0;
		for (int i = 0; i < Game.HAND_SIZE; i++) {
			matches = 0;
			for (int j = 0; j < Game.HAND_SIZE; j++) {
				if (hand[i].getFace() == hand[j].getFace())	matches++;
			}
			if (matches == 2 && (hand[i].getFace() == 0 || hand[i].getFace() > 9)) return true;
		}
		return false;
	}
}
