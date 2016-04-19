import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class keep all the necessary data about a card. 
 * It also contains some global functions as well as a lot of constants related to cards.
 * @author Cory
 */
public class Card extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	public static final int AMOUNT_OF_FACES = 13;
	public static final int AMOUNT_OF_SUITS = 4;
	public static final int AMOUNT_OF_CARDS = AMOUNT_OF_FACES * AMOUNT_OF_SUITS;
	
	public static final int CARD_WIDTH	 = 72;
	public static final int CARD_HEIGHT	 = 96;
	
	public static final int FACE_ACE	 = 0;
	public static final int FACE_TWO	 = 1;
	public static final int FACE_THREE	 = 2;
	public static final int FACE_FOUR	 = 3;
	public static final int FACE_FIVE	 = 4;
	public static final int FACE_SIX	 = 5;
	public static final int FACE_SEVEN	 = 6;
	public static final int FACE_EIGHT	 = 7;
	public static final int FACE_NINE	 = 8;
	public static final int FACE_TEN	 = 9;
	public static final int FACE_JACK	 = 10;
	public static final int FACE_QUEEN	 = 11;
	public static final int FACE_KING	 = 12;
	
	public static final int SUIT_CLUB	 = 0;
	public static final int SUIT_SPADE	 = 1;
	public static final int SUIT_HEART	 = 2;
	public static final int SUIT_DIAMOND = 3;
	
	private int cardID;
	private boolean cardDrawn;
	private boolean cardHeld;
	private boolean clickValid;
	private BufferedImage image;
	
	/**
	 * Initialize a Card. 
	 * The face and suit is determined by the CardID. 
	 * @param id
	 * @param drawn
	 * @param held
	 * @param img
	 */
	public Card(int id, boolean drawn, boolean held, BufferedImage img) {
		setSize(CARD_WIDTH, CARD_HEIGHT);
		addMouseListener(this);
		setCardID(id);
		setCardDrawn(drawn);
		setCardHeld(false);
		setImage(img);
		clickValid = false;
	}
	
	/**
	 * Makes the card drawn when a repaint event is called. 
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, CARD_WIDTH, CARD_HEIGHT, null);
	}
	
	/**
	 * Not used. Added for interface. 
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * When mouse is inside of the boundaries of the card, the mouseReleased event is valid. 
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		clickValid = true;
	}

	/**
	 * When mouse leaves the boundaries of the card, the mouseReleased event is no longer valid. 
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		clickValid = false;
	}

	/**
	 * Not used. Added for interface. 
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * When the mouse is released, if the click is valid, its held state will toggle. 
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (clickValid)	cardHeld = !cardHeld;
	}
	
	/**
	 * Sets the id of the Card. 
	 * CardID will determine the face and suit of the card. 
	 * @param id
	 */
	public void setCardID(int id) {
		cardID = id;
	}
	
	/**
	 * Sets if the card is drawn or not. 
	 * @param drawn
	 */
	public void setCardDrawn(boolean drawn) {
		cardDrawn = drawn;
	}
	
	/**
	 * Sets if the card is held or not. 
	 * @param held
	 */
	public void setCardHeld(boolean held) {
		cardHeld = held;
	}
	
	/**
	 * Sets the image of the card. 
	 * @param img
	 */
	public void setImage(BufferedImage img) {
		image = img;
	}
	
	/**
	 * Gets the face of the card. 
	 * Determined by CardID. 
	 * @return face
	 */
	public int getFace() {
		return computeFace(getCardID());
	}
	
	/**
	 * Gets the suit of the card. 
	 * Determined by CardID. 
	 * @return suit
	 */
	public int getSuit() {
		return computeSuit(getCardID());
	}
	
	/**
	 * Gets the cardID. 
	 * @return cardID
	 */
	public int getCardID() {
		return cardID;
	}
	
	/**
	 * Gets if the card is drawn or not (Drawn as in drawn from deck, not drawn on screen).
	 * @return drawn
	 */
	public boolean getDrawn() {
		return cardDrawn;
	}
	
	/**
	 * Gets if the card is held or not. 
	 * @return held
	 */
	public boolean getCardHeld() {
		return cardHeld;
	}
	
	/**
	 * Gets image of card. 
	 * @return image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (getClass() == obj.getClass()) {
			Card card = (Card) obj;
			return getCardID() == card.getCardID() && getDrawn() == card.getDrawn() 
					&& getCardHeld() == card.getCardHeld() && getImage() == card.getImage();
		}
		return false;
	}
	
	/**
	 * Using a face and suit, it rips (gets) the corresponding image from the sheet of cards. 
	 * @param f - face
	 * @param s - suit
	 * @return image
	 */
	public static BufferedImage ripImage(int f, int s) {
		//Error Check
		if (f < 0 || f >= AMOUNT_OF_FACES) return null;
		if (s < 0 || s >= AMOUNT_OF_SUITS) return null;
		
		BufferedImage img = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		try {
			BufferedImage sheet = ImageIO.read(Card.class.getResource("/cards.png"));
			for (int i = 0; i < CARD_WIDTH; i++)
				for (int j = 0; j < CARD_HEIGHT; j++)
					img.setRGB(i, j, sheet.getRGB((f * CARD_WIDTH) + i, (s *  CARD_HEIGHT) + j));
		}
		catch (IOException e) {
			System.err.println("Error loading file cards.png");
		}
		return img;
	}
	
	/**
	 * Using a face and suit, it gets the corresponding cardID.
	 * @param f - face
	 * @param s - suit
	 * @return cardID
	 */
	public static int computeCardID(int f, int s) {
		return (s * AMOUNT_OF_FACES) + f;
	}
	
	/**
	 * Using a cardID, it returns the corresponding face. 
	 * @param id
	 * @return face
	 */
	public static int computeFace(int id) {
		return id % AMOUNT_OF_FACES;
	}
	
	/**
	 * Using a cardID, it returns the corresponding suit. 
	 */
	public static int computeSuit(int id) {
		return id / AMOUNT_OF_FACES;
	}
}
