import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class contains the bulk of the code for the game. 
 * It handles the state of the game, and what should be displayed on the screen or not. 
 * Also, It handles the deck of cards and player's hand and there basic operations such as shuffling and dealing. 
 * @author Cory
 */
public class Game extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static final int HAND_SIZE = 5;
	public static final int STATE_BETTING = 0;
	public static final int STATE_HOLDING = 1;
	public static final int STATE_RESULTS = 2;
	
	public static final int PAYOUT_ROYAL_FLUSH = 250;
	public static final int PAYOUT_STRAIGHT_FLUSH = 150;
	public static final int PAYOUT_FOUR_OF_A_KIND = 50;
	public static final int PAYOUT_FULL_HOUSE = 25;
	public static final int PAYOUT_FLUSH = 15;
	public static final int PAYOUT_STRAIGHT = 10;
	public static final int PAYOUT_THREE_OF_A_KIND = 5;
	public static final int PAYOUT_TWO_PAIR = 2;
	public static final int PAYOUT_PAIR_JACKS_OR_HIGHER = 1;
	
	public static final int CARD_MARGIN = 12;
	public static final int OFFSET = (Window.WIDTH - ((Card.CARD_WIDTH * HAND_SIZE) + (CARD_MARGIN * (HAND_SIZE - 1)))) / 2;
	
	private int state;
	private int lastState;
	private int drawnPoint;
	private JButton drawButton;
	private JButton resultButton;
	private JLabel tokensLabel;
	private JLabel resultsMessage;
	private JLabel[] heldLabel;
	private Random rand;
	private BettingSystem bettingSystem;
	private Card[] cards;
	private Card[] hand;
	
	/**
	 * This initializes all of the components that will appear in the frame. 
	 * It sets the games default state to STATE_BETTING
	 */
	public Game() {
		//Initialize game
		state = STATE_BETTING;
		lastState = STATE_BETTING;
		drawnPoint = Card.AMOUNT_OF_CARDS;
		rand = new Random();
		bettingSystem = new BettingSystem();
		setSize(Window.WIDTH, Window.HEIGHT);
		setLayout(null);
		setBackground(new Color(0, 160, 0));
		
		//Initialize all the cards
		cards = new Card[Card.AMOUNT_OF_CARDS];
		for (int i = 0; i < Card.AMOUNT_OF_CARDS; i++) {
			cards[i] = new Card(i, false, false, Card.ripImage(Card.computeFace(i), Card.computeSuit(i)));		
			add(cards[i]);
			cards[i].setVisible(false);
		}
		
		//Initialize player's hand
		hand = new Card[HAND_SIZE];
		for (int i = 0; i < HAND_SIZE; i++) {
			hand[i] = null;
		}
		
		//Initialize held labels
		heldLabel = new JLabel[HAND_SIZE];
		for (int i = 0; i < HAND_SIZE; i++) {
			heldLabel[i] = new JLabel();
			heldLabel[i].setBounds((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET + 24, 64 + Card.CARD_HEIGHT, Card.CARD_WIDTH, 48);
			heldLabel[i].setForeground(new Color(224, 224, 224));
			heldLabel[i].setText("Hold");
			add(heldLabel[i]);
			heldLabel[i].setVisible(false);
		}
		
		//Set up betting system
		add(bettingSystem);
		bettingSystem.setLocation(-bettingSystem.getWidth(), getHeight() - bettingSystem.getHeight());
		bettingSystem.setTokens(100);
		bettingSystem.setBet(0);
		bettingSystem.setBettingState(true);
		
		//Display tokens
		tokensLabel = new JLabel("Tokens: " + bettingSystem);
		tokensLabel.setForeground(new Color(224, 224, 224));
		add(tokensLabel);
		tokensLabel.setBounds(8, getHeight() - 120, 224, 48);
		
		//Setup results message
		resultsMessage = new JLabel();
		resultsMessage.setBounds(0, 224, getWidth(), 48);
		resultsMessage.setText("HELLO");
		resultsMessage.setForeground(new Color(224, 224, 224));
		add(resultsMessage);
		resultsMessage.setVisible(false);
		
		//Setup draw card button
		drawButton = new JButton();
		drawButton.setBounds((getWidth() - 128) / 2, 360, 128, 32);
		drawButton.setText("Draw");
		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionDrawButton(e);
			}
		});
		add(drawButton);
		drawButton.setVisible(false);
		
		//Setup result button
		resultButton = new JButton();
		resultButton.setBounds((getWidth() - 128) / 2, 360, 128, 32);
		resultButton.setText("Okay");
		resultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionResultButton(e);
			}
		});
		add(resultButton);
		resultButton.setVisible(false);
		
		//Initialize Timer
		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionTimer(e);
			}
		});
		timer.start();
	}
	
	/**
	 * Called when the draw button is clicked to get new cards and go to the result screen. 
	 * It is outside of the button for testing purposes. 
	 * @param e
	 */
	public void actionDrawButton(ActionEvent e) {
		state = STATE_RESULTS;
	}
	
	/**
	 * Called when the result (okay) button is clicked to go to the betting screen. 
	 * It is outside of the button for testing purposes. 
	 * @param e
	 */
	public void actionResultButton(ActionEvent e) {
		state = STATE_BETTING;
	}
	
	/**
	 * Called on every tick of the timer which is initialized in the Game constructor. 
	 * It handles switching states and what to do in each state.  
	 * It is outside of the timer for testing purposes. 
	 * @param e
	 */
	public void actionTimer(ActionEvent e) {
		tokensLabel.setText("Tokens: " + bettingSystem.getTokens());
		if (state == STATE_BETTING) {
			//If state changed initialize it
			if (lastState != STATE_BETTING) {
				initStateBetting();
				lastState = state;
			}
			
			//This code animates the bet box to slide on and off the screen
			//If the player places a valid bet
			if (bettingSystem.getBet() > 0 && bettingSystem.getBet() <= bettingSystem.getTokens()) {
				//If betting box is on screen, move it off
				if (bettingSystem.getX() > -bettingSystem.getWidth()) {
					bettingSystem.setLocation(bettingSystem.getX() - 8, bettingSystem.getY());
				}
				//When betting box is off screen, switch state
				else {
					state = STATE_HOLDING;
				}
			}
			else {
				if (bettingSystem.getX() < 0) {
					bettingSystem.setLocation(bettingSystem.getX() + 8, bettingSystem.getY());
				}
			}
		}
		else if (state == STATE_HOLDING) {
			//If state changed initialize it
			if (lastState != STATE_HOLDING) {
				initStateHolding();
				lastState = state;
			}
			
			for (int i = 0; i < HAND_SIZE; i++) {
				if (hand[i].getCardHeld()) {
					heldLabel[i].setText("Release");
					heldLabel[i].setLocation((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET + 15, 64 + Card.CARD_HEIGHT);
					hand[i].setLocation((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET, 40);
				}
				else {
					heldLabel[i].setText("Hold");
					heldLabel[i].setLocation((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET + 24, 64 + Card.CARD_HEIGHT);
					hand[i].setLocation((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET, 64);
				}
			}
		}
		else if (state == STATE_RESULTS) {
			//If state changed initialize it
			if (lastState != STATE_RESULTS) {
				initStateResults();
				lastState = state;
			}
			for (int i = 0; i < HAND_SIZE; i++) {
				hand[i].setLocation((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET, 40);
			}
		}
	}
	
	/**
	 * Gets the appropriate payout depending on the cards in hand. 
	 * @return the appropriate payout
	 */
	public int getPayout() {
		int payout = 0;
		if (Outcomes.isRoyalFlush(hand)) payout = PAYOUT_ROYAL_FLUSH;
		else if (Outcomes.isStraightFlush(hand)) payout = PAYOUT_STRAIGHT_FLUSH;
		else if (Outcomes.isFourOfAKind(hand)) payout = PAYOUT_FOUR_OF_A_KIND;
		else if (Outcomes.isFullHouse(hand)) payout = PAYOUT_FULL_HOUSE;
		else if (Outcomes.isFlush(hand)) payout = PAYOUT_FLUSH;
		else if (Outcomes.isStraight(hand)) payout = PAYOUT_STRAIGHT;
		else if (Outcomes.isThreeOfAKind(hand)) payout = PAYOUT_THREE_OF_A_KIND;
		else if (Outcomes.isTwoPair(hand)) payout = PAYOUT_TWO_PAIR;
		else if (Outcomes.isPairJacksOrHigher(hand)) payout = PAYOUT_PAIR_JACKS_OR_HIGHER;
		return payout;
	}
	
	/**
	 * Displays the appropriate message for the payout provided. 
	 * @param payout
	 */
	public void displayResultsMessage(int payout) {
		if (payout == PAYOUT_ROYAL_FLUSH) {
			resultsMessage.setText("You got a Royal Flush! Your payout is " + PAYOUT_ROYAL_FLUSH * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_STRAIGHT_FLUSH) {
			resultsMessage.setText("You got a Straight Flush! Your payout is " + PAYOUT_STRAIGHT_FLUSH * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_FOUR_OF_A_KIND) {
			resultsMessage.setText("You got a Four of a Kind! Your payout is " + PAYOUT_FOUR_OF_A_KIND * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_FULL_HOUSE) {
			resultsMessage.setText("You got a Full House! Your payout is " + PAYOUT_FULL_HOUSE * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_FLUSH) {
			resultsMessage.setText("You got a Flush! Your payout is " + PAYOUT_FLUSH * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_STRAIGHT) {
			resultsMessage.setText("You got a Straight! Your payout is " + PAYOUT_STRAIGHT* bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_THREE_OF_A_KIND) {
			resultsMessage.setText("You got a Three of a Kind! Your payout is " + PAYOUT_THREE_OF_A_KIND * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_TWO_PAIR) {
			resultsMessage.setText("You got a Two Pair! Your payout is " + PAYOUT_TWO_PAIR * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else if (payout == PAYOUT_PAIR_JACKS_OR_HIGHER) {
			resultsMessage.setText("You got a Pair of Jacks or Higher! Your payout is " + PAYOUT_PAIR_JACKS_OR_HIGHER * bettingSystem.getBet() + " tokens.");
			resultsMessage.setLocation(180, 224);
		}
		else {
			resultsMessage.setText("You got Junk!");
			resultsMessage.setLocation(276, 224);
		}
	}
	
	/**
	 * Randomizes the cards in the deck. 
	 */
	public void shuffleCards() {
		for (int i = Card.AMOUNT_OF_CARDS - 1; i > 0; i--)
			cardSwap(i, rand.nextInt(Card.AMOUNT_OF_CARDS));
	}
	
	/**
	 * Sets all the cards to not drawn and not held, and it resets the drawn point. 
	 * More on drawn point at the getDrawnPoint() method. 
	 */
	public void putAllCardsBackInDeck() {
		drawnPoint = Card.AMOUNT_OF_CARDS;
		for (int i = 0; i < Card.AMOUNT_OF_CARDS; i++) {
			cards[i].setCardDrawn(false);
			cards[i].setCardHeld(false);
		}
	}
	
	/**
	 * Sets hand's elements to null
	 */
	public void clearHand() {
		for (int i = 0; i < HAND_SIZE; i++) {
			hand[i] = null;
		}
	}
	
	/**
	 * Draws cards for each element of hand if that index is empty or not held. 
	 * @return if all cards are drawn successfully then it returns true, if it runs out of cards to draw, it returns false 
	 */
	public boolean drawHand() {
		//if there are not enough cards to draw, then don't
		if (drawnPoint < HAND_SIZE) return false;
		for (int i = 0; i < HAND_SIZE; i++) {
			//When drawing new cards, check if was a card that was held or not, if not then draw a new one
			if (hand[i] == null || !hand[i].getCardHeld()) {
				Card c = drawCard();
				//If there were no cards to draw, then return
				if (c == null) return false;
				if (hand[i] != null) hand[i].setVisible(false);
				hand[i] = c;
			}
		}
		return true;
	}
	
	/**
	 * Gets the card at drawn point (top of the deck). 
	 * @return returns the Card at the top of the deck, and returns null if no cards are left
	 */
	public Card drawCard() {
		//If all cards drawn
		if (drawnPoint == 0) return null;
		
		//Draw a card
		cardSwap(drawnPoint - 1, rand.nextInt(drawnPoint));
		drawnPoint--;
		return cards[drawnPoint];
	}
	
	/**
	 * Swap Cards position in cards array
	 * @param index1 Card 1
	 * @param index2 Card 2
	 */
	public void cardSwap(int index1, int index2) {
		Card t = cards[index1];
		cards[index1] = cards[index2];
		cards[index2] = t;
	}
	
	/**
	 * Called by the timer to initialize betting state. 
	 * Outside of the timer for readability. 
	 */
	private void initStateBetting() {
		resultButton.setVisible(false);
		resultsMessage.setVisible(false);
		bettingSystem.setBet(0);
		bettingSystem.setBettingState(true);
		for (int i = 0; i < HAND_SIZE; i++) {
			hand[i].setVisible(false);
		}
	}
	
	/**
	 * Called by the timer to initialize holding state. 
	 * Outside of the timer for readability. 
	 */
	private void initStateHolding() {
		bettingSystem.setBettingState(false);
		bettingSystem.setTokens(bettingSystem.getTokens() - bettingSystem.getBet());
		clearHand();
		putAllCardsBackInDeck();
		shuffleCards();
		drawHand();
		drawButton.setVisible(true);
		for (int i = 0; i < HAND_SIZE; i++) {
			heldLabel[i].setVisible(true);
			hand[i].setVisible(true);
			hand[i].setLocation((i * (Card.CARD_WIDTH + CARD_MARGIN)) + OFFSET, 64);
		}
	}
	
	/**
	 * Called by the timer to initialize results state. 
	 * Outside of the timer for readability. 
	 */
	private void initStateResults() {
		drawHand();
		int payout = getPayout();
		displayResultsMessage(payout);
		payout *= bettingSystem.getBet();
		bettingSystem.setTokens(bettingSystem.getTokens() + payout);
		drawButton.setVisible(false);
		resultButton.setVisible(true);
		resultsMessage.setVisible(true);
		for (int i = 0; i < HAND_SIZE; i++) {
			hand[i].setVisible(true);
			heldLabel[i].setVisible(false);
			heldLabel[i].setText("Hold");
		}
	}
	
	//////////Getters are only for testing purposes//////////
	
	/**
	 * Get state of the game. 
	 * Can be any of the following: <br />
	 * <ul>
	 *  <li>STATE_BETTING</li>
	 *  <li>STATE_HOLDING</li>
	 *  <li>STATE_RESULTS</li>
	 * </ul>
	 * @return state
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Get the last state of the game (since last tick). 
	 * Used to tell the timer whether or not it needs to initialize a state or not. 
	 * @return lastState
	 */
	public int getLastState() {
		return lastState;
	}
	
	/**
	 * Get the drawn point. 
	 * The drawn point is used to symbolize the top of the deck. 
	 * When it is equal to the amount of cards, then it is pointing to the top of the deck. 
	 * When it is zero, it is symbolizing there are no more cards in the deck. 
	 * Any card before this point is not drawn. 
	 * Any card after and on this point is drawn. 
	 * @return drawnPoint
	 */
	public int getDrawnPoint() {
		return drawnPoint;
	}
	
	/**
	 * Get the instance of BettingSystem in this instance of Game. 
	 * More on BettingSystem in the Class. 
	 * @return bettingSystem
	 */
	public BettingSystem getBettingSystem() {
		return bettingSystem;
	}
	
	/**
	 * Get the array of cards in the deck. 
	 * @return cards[]
	 */
	public Card[] getCards() {
		return cards;
	}
	
	/**
	 * Get the array of cards in the hand. 
	 * @return hand[]
	 */
	public Card[] getHand() {
		return hand;
	}
}
