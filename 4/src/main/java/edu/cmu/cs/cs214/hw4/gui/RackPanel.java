package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.WordTile;

/**
 * RackPanel stores a word rack and a special rack. I used two ActionListeners
 * (thus no implement ActionListener) because the word rack and special rack
 * have different mechanisms, though they are similiar.
 * 
 * @author zhaoru
 *
 */
public class RackPanel extends JPanel implements UpdateListener {
	private static final long serialVersionUID = 48175393998918927L;

	private static final int MAX_WORD_RACK = 7;
	private static final int MAX_SPECIAL_RACK = 10;
	private static final int BUTTON_WID = 80;
	private static final int BUTTON_HEI = 40;

	private final Game game;
	private final GameUI gameUI;

	private WordListener wordListener = new WordListener();
	private SpecialListener specialListener = new SpecialListener();

	private JButton[] words;
	private JButton[] specials;

	/**
	 * Constructor
	 * 
	 * @param newGame
	 *            game instance to parse in
	 * @param newGameUI
	 *            gameUI instance to parse in
	 */
	public RackPanel(Game newGame, GameUI newGameUI) {
		game = newGame;
		gameUI = newGameUI;

		words = new JButton[MAX_WORD_RACK];
		specials = new JButton[MAX_SPECIAL_RACK];

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel wordRack = new JPanel();
		JPanel specialRack = new JPanel();

		for (int i = 0; i < MAX_WORD_RACK; i++) {
			words[i] = new JButton();
			words[i].setPreferredSize(new Dimension(BUTTON_WID, BUTTON_HEI));
			words[i].setOpaque(true);
			words[i].addActionListener(wordListener);
			wordRack.add(words[i]);
		}

		for (int j = 0; j < MAX_SPECIAL_RACK; j++) {
			specials[j] = new JButton();
			specials[j].setPreferredSize(new Dimension(BUTTON_WID, BUTTON_HEI));
			specials[j].setOpaque(true);
			specials[j].addActionListener(specialListener);
			specialRack.add(specials[j]);
		}

		add(wordRack);
		add(specialRack);
		refresh();
	}

	/**
	 * refresh displaying the racks when being called
	 */
	@Override
	public void refresh() {
		List<WordTile> wordTiles = game.getCurrentPlayer().getWordRack()
				.getWordTiles();
		List<SpecialTile> specialTiles = game.getCurrentPlayer()
				.getSpecialRack().getSpecialTiles();

		for (JButton b : words) {
			b.setText(null);
		}

		for (JButton s : specials) {
			s.setText(null);
		}

		for (int i = 0; i < wordTiles.size(); i++) {
			if (wordTiles != null && wordTiles.size() > 0
					&& wordTiles.get(i) != null) {
				words[i].setText(wordTiles.get(i).getLetter() + " : $"
						+ wordTiles.get(i).getScore());
			}
		}

		for (int i = 0; i < specialTiles.size(); i++) {
			if (specialTiles != null && specialTiles.size() > 0
					&& specialTiles.get(i) != null) {
				specials[i].setText(specialTiles.get(i).getName());
			}
		}
	}

	/**
	 * the listener for word rack
	 * 
	 * @author zhaoru
	 *
	 */
	private class WordListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int num = game.getCurrentPlayer().getWordRack().getNum();
			WordTile wordTile;
			for (int i = 0; i < num; i++) {
				if (e.getSource() == words[i]) {
					wordTile = game.getCurrentPlayer().getWordRack()
							.getWordTiles().get(i);
					gameUI.setWtClick(wordTile);
				}
			}
		}
	}

	/**
	 * the listener for special rack
	 * 
	 * @author zhaoru
	 *
	 */
	private class SpecialListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int num = game.getCurrentPlayer().getSpecialRack().getNum();
			SpecialTile specialTile;
			for (int i = 0; i < num; i++) {
				if (e.getSource() == specials[i]) {
					specialTile = game.getCurrentPlayer().getSpecialRack()
							.getSpecialTiles().get(i);
					gameUI.setStClick(specialTile);
				}
			}
		}
	}
}
