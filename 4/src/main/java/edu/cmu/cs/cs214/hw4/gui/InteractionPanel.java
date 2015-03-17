package edu.cmu.cs.cs214.hw4.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw4.core.BoomTile;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.NegativeTile;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.RevengeTile;
import edu.cmu.cs.cs214.hw4.core.ReverseTile;
import edu.cmu.cs.cs214.hw4.core.ScoreSwitchTile;
import edu.cmu.cs.cs214.hw4.core.WordTile;

/**
 * InteractionPanel stores all interaction buttons, including pass button, swap
 * button, play button; and purchasing special tiles buttons. Different buttons
 * should have different ActionListeners.
 * 
 * @author zhaoru
 *
 */
public class InteractionPanel extends JPanel {
	private static final long serialVersionUID = -7717780458208041628L;

	private static final int PARA_GAME_BUTTON = 7;
	private static final int PARA_SPECIAL_BUTTON = 6;

	private final Game game;
	private final GameUI gameUI;

	private JTextField swapIndices;

	/**
	 * Constructor
	 * 
	 * @param newGame
	 *            game instance to parse in
	 * @param newGameUI
	 *            gameUI instance to parse in
	 */
	public InteractionPanel(Game newGame, GameUI newGameUI) {
		game = newGame;
		gameUI = newGameUI;

		setLayout(new GridLayout(2, 1));

		JPanel gameButtonPanel = initGameButtonPanel();
		JPanel specialButtonPanel = initSpecialButtonPanel();

		add(gameButtonPanel);
		add(specialButtonPanel);
	}

	/**
	 * helper function to initialize game button panel
	 * 
	 * @return game button panel
	 */
	private JPanel initGameButtonPanel() {
		JPanel gameButton = new JPanel();
		gameButton.setLayout(new GridLayout(PARA_GAME_BUTTON, 1));

		JButton pass = new JButton("Pass Turn");
		pass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameUI.resetClicked();
				game.retreat();
				game.complementTiles();

				gameUI.update();

				game.addCountPass();
				game.nextPlayer();

				gameUI.update();

				if (game.isGameOver()) {
					Player winner = game.compareWinner();
					gameUI.gameOver(winner);
				}
			}
		});

		JButton swap = new JButton("Swap Tiles");
		swap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WordTile wordTile;
				List<WordTile> swaps = new ArrayList<WordTile>();
				int index;
				String[] indices = swapIndices.getText().split("");

				try {
					for (String s : indices) {
						index = Integer.parseInt(s) - 1;
						wordTile = game.getCurrentPlayer().getWordRack()
								.getWordTiles().get(index);
						swaps.add(wordTile);
					}
				} catch (NumberFormatException error1) {
					swapIndices.setText(null);
					return;
				} catch (IndexOutOfBoundsException error2) {
					swapIndices.setText(null);
					return;
				}

				game.swapTiles(swaps);
				swapIndices.setText(null);

				gameUI.resetClicked();
				game.retreat();
				game.complementTiles();

				gameUI.update();

				game.addCountPass();
				game.nextPlayer();

				gameUI.update();

				if (game.isGameOver()) {
					Player winner = game.compareWinner();
					gameUI.gameOver(winner);
				}
			}
		});

		JButton retreat = new JButton("Retreat Tiles");
		retreat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.retreat();
				gameUI.resetClicked();
				gameUI.update();
			}
		});

		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (game.isValid()) {
					game.move();

					gameUI.resetClicked();
					game.complementTiles();

					gameUI.update();

					game.nextPlayer();

					gameUI.update();

					if (game.isGameOver()) {
						Player winner = game.compareWinner();
						gameUI.gameOver(winner);
					}

				} else {
					game.retreat();

					gameUI.resetClicked();
					gameUI.update();
				}
			}
		});

		JPanel swapPane = new JPanel();
		swapPane.setLayout(new GridLayout(1, 2));
		swapIndices = new JTextField();
		swapPane.add(swap);
		swapPane.add(swapIndices);

		gameButton.add(new JLabel());
		gameButton.add(pass);
		gameButton.add(swapPane);
		gameButton.add(retreat);
		gameButton.add(play);

		return gameButton;
	}

	/**
	 * helper function to initialize special button panel for purchasing
	 * 
	 * @return special button panel
	 */
	private JPanel initSpecialButtonPanel() {
		JPanel specialButton = new JPanel();
		specialButton.setLayout(new GridLayout(PARA_SPECIAL_BUTTON, 1));

		JLabel purchase = new JLabel("Purchasing by clicking:");

		JButton negative = new JButton("$25 NegativeTile");
		negative.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.purchaseSpecialTile(new NegativeTile());
				gameUI.updateNoBoard();
			}
		});

		JButton reverse = new JButton("$10 ReverseTile");
		reverse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.purchaseSpecialTile(new ReverseTile());
				gameUI.updateNoBoard();
			}
		});

		JButton boom = new JButton("$15 BoomTile");
		boom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.purchaseSpecialTile(new BoomTile());
				gameUI.updateNoBoard();
			}
		});

		JButton revenge = new JButton("$20 RevengeTile");
		revenge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.purchaseSpecialTile(new RevengeTile());
				gameUI.updateNoBoard();
			}
		});

		JButton scoreSwitch = new JButton("$15 ScoreSwitchTile");
		scoreSwitch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.purchaseSpecialTile(new ScoreSwitchTile());
				gameUI.updateNoBoard();
			}
		});

		specialButton.add(purchase);
		specialButton.add(negative);
		specialButton.add(reverse);
		specialButton.add(boom);
		specialButton.add(revenge);
		specialButton.add(scoreSwitch);

		return specialButton;
	}
}
