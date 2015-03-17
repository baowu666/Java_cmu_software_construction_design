package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Coordinate;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Square;

/**
 * BoardPanel stores all squares on the board. I implement the ActionListener
 * because all the buttons (squares) on the board have the same mechanism. I
 * used one listener to hold all.
 * 
 * @author zhaoru
 *
 */
public class BoardPanel extends JPanel implements ActionListener, UpdateListener {
	private static final long serialVersionUID = 2274869219936427299L;

	private static final int MAX_A_LINE = 15;
	private static final int SIZE_SQUARE = 35;
	private static final int BONUS_TWO = 2;
	private static final int BONUS_THREE = 3;

	private final Game game;
	private final GameUI gameUI;

	private JButton[] squares;

	/**
	 * Constructor
	 * 
	 * @param newGame
	 *            the game instance to parse in
	 * @param newGameUI
	 *            the gameUI instance to parse in
	 */
	public BoardPanel(Game newGame, GameUI newGameUI) {
		game = newGame;
		gameUI = newGameUI;

		int num;
		Square square;

		squares = new JButton[MAX_A_LINE * MAX_A_LINE];

		setLayout(new GridLayout(MAX_A_LINE, MAX_A_LINE));
		for (int row = 0; row < MAX_A_LINE; row++) {
			for (int col = 0; col < MAX_A_LINE; col++) {
				num = row * MAX_A_LINE + col;
				square = game.getBoard().getSquares().get(num);
				int letterBonus = square.getLetterBonus();
				int wordBonus = square.getWordBonus();

				squares[num] = new JButton();
				squares[num].setPreferredSize(new Dimension(SIZE_SQUARE,
						SIZE_SQUARE));
				squares[num].setOpaque(true);

				if (letterBonus == BONUS_TWO) {
					squares[num].setBackground(Color.YELLOW);
				}
				if (letterBonus == BONUS_THREE) {
					squares[num].setBackground(Color.CYAN);
				}
				if (wordBonus == BONUS_TWO) {
					squares[num].setBackground(Color.RED);
				}
				if (wordBonus == BONUS_THREE) {
					squares[num].setBackground(Color.GREEN);
				}

				add(squares[num]);
				squares[num].addActionListener(this);
			}
		}
	}

	/**
	 * refresh displaying the board when being called
	 */
	@Override
	public void refresh() {
		Square square;

		for (int i = 0; i < MAX_A_LINE * MAX_A_LINE; i++) {

			squares[i].setText(null);

			square = game.getBoard().getSquares().get(i);

			if (square.getSpecial() != null) {
				if (game.getCurrentPlayer() == square.getSpecial().getOwner()) {
					squares[i].setText(square.getSpecial().getName());
				}
			}

			if (square.getWord() != null) {
				squares[i].setText(square.getWord().getLetter());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row, col;
		String letter, specialName;

		for (int i = 0; i < MAX_A_LINE * MAX_A_LINE; i++) {
			if (e.getSource() == squares[i]) {
				row = i / MAX_A_LINE;
				col = i % MAX_A_LINE;

				/*
				 * ensure current player cannot place a word on the square
				 * already has a word. cannot override
				 */
				if (game.getBoard().getSquares().get(i).getWord() == null) {
					gameUI.setCoordClick(new Coordinate(row, col));

					if (gameUI.getWtClick() != null) {
						letter = gameUI.getWtClick().getLetter();
						squares[i].setText(letter);

						game.placeWord(gameUI.getWtClick(),
								gameUI.getCoordClick());

						gameUI.updateNoBoard();

						gameUI.resetClicked();
					}
					if (gameUI.getStClick() != null) {
						specialName = gameUI.getStClick().getName();
						squares[i].setText(specialName);

						game.useSpecialTile(gameUI.getStClick(),
								gameUI.getCoordClick());

						gameUI.updateNoBoard();

						gameUI.resetClicked();
					}
				}
			}
		}
	}
}
