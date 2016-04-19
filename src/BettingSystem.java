import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class handles the betting part of the game. 
 * It is a JPanel that contains the tokens and bet, as well as components to manage these. 
 * @author Cory
 */
public class BettingSystem extends JPanel {
	private static final long serialVersionUID = 1L;

	private int tokens;
	private int bet;
	private boolean bettingState;
	private JTextField feild;
	private JButton betButton;
	
	/**
	 * Initializes all of the components of the BettingSystem. 
	 */
	public BettingSystem() {
		tokens = 0;
		bet = 0;
		bettingState = false;
		
		//Initialize Betting System Panel
		setSize(240, 80);
		setBackground(new Color(128, 128, 128));
		setLayout(null);
		
		//Add text feild
		feild = new JTextField();
		feild.setBounds(8, getHeight() - 72, 128, 32);
		add(feild);
		
		//Add button
		betButton = new JButton();
		betButton.setText("Bet");
		betButton.setBounds(144, getHeight() - 72, 80, 32);
		betButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionBetButton(e);
			}
		});
		add(betButton);
	}
	
	/**
	 * This is called when the bet button is pressed. 
	 * It validates the input for the provided bet, and if it is valid sets the bet. 
	 * Only works if bettingState is true. 
	 * It is outside of the button for testing purposes. 
	 * @param e
	 */
	public void actionBetButton(ActionEvent e) {
		if (bettingState) {
			int betAmount = 0;
			String betStr = feild.getText();
			try {
				betAmount = Integer.parseInt(betStr);
			}
			catch (Exception ex) {
			}
			
			feild.setText("");
			
			if (betAmount > 0 && betAmount <= tokens) {
				bet = betAmount;
			}
			else {
				bet = 0;
			}
		}
	}
	
	/**
	 * Sets the amount of tokens the player has. 
	 * @param t
	 */
	public void setTokens(int t) {
		tokens = t;
	}
	
	/**
	 * Sets the player's bet. 
	 * @param b
	 */
	public void setBet(int b) {
		bet = b;
	}
	
	/**
	 * Sets if it is the betting state or not.
	 * @param b
	 */
	public void setBettingState(boolean b) {
		bettingState = b;
	}
	
	/**
	 * Get tokens. 
	 * @return tokens
	 */
	public int getTokens() {
		return tokens;
	}
	
	/**
	 * Get bet. 
	 * @return bet
	 */
	public int getBet() {
		return bet;
	}
	
	/**
	 * Get if it is the bettingState or not
	 * @return bettingState
	 */
	public boolean getBettingState() {
		return bettingState;
	}
	
	/**
	 * Get the text field. 
	 * For testing purposes only. 
	 * @return textFeild
	 */
	public JTextField getTextFeild() {
		return feild;
	}
}
