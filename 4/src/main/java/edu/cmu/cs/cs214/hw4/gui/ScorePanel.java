package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Game;

/**
 * ScorePanel stores all players and their score information. There is no
 * ActionListener needed for this.
 * 
 * @author zhaoru
 *
 */
public class ScorePanel extends JPanel implements UpdateListener {
	private static final long serialVersionUID = -7207101677272359198L;

	private final Game game;
	private JButton[] playerButtons;
	private JLabel tileBag;

	/**
	 * Constructor
	 * 
	 * @param newGame
	 *            game instance to parse in
	 */
	public ScorePanel(Game newGame) {
		game = newGame;
		int numPlayers = game.getNumPlayers();

		setLayout(new GridLayout(1, numPlayers + 1));

		playerButtons = new JButton[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			//Test
			//game.getPlayers()[i].setScore(100);
			
			String playerInfo = "Player " + (i + 1) + ": $"
					+ game.getPlayers()[i].getScore();
			
			playerButtons[i] = new JButton(playerInfo);
			playerButtons[i].setOpaque(true);
			playerButtons[i].setBackground(null);
			add(playerButtons[i]);
		}

		playerButtons[Integer.parseInt(game.getCurrentPlayer().getName())]
				.setBackground(Color.RED);
		
		tileBag = new JLabel();
		tileBag.setHorizontalAlignment(JLabel.CENTER);
		tileBag.setText("Tiles Left: " + String.valueOf(game.getTileBag().getNum()));
		add(tileBag);
	}

	/**
	 * refresh displaying the score panel when being called
	 */
	@Override
	public void refresh() {
		int currentIndex;
		for (int i = 0; i < playerButtons.length; i++) {
			playerButtons[i].setBackground(null);
			playerButtons[i].setText(null);
			
			currentIndex = Integer.parseInt(game.getCurrentPlayer().getName());
			if (currentIndex == i) {
				playerButtons[i].setBackground(Color.RED);
			}
			String playerInfo = "Player " + (i + 1) + ": $"
					+ game.getPlayers()[i].getScore();
			playerButtons[i].setText(playerInfo);
		}
		tileBag.setText("Tiles Left: " + String.valueOf(game.getTileBag().getNum()));
	}
}
