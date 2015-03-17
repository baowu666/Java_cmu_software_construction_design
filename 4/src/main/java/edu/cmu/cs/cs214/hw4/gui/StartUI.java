package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * the class to control selecting number of players before the game, when input
 * is valid, generate the game instance and show.
 * 
 * @author zhaoru
 *
 */
public class StartUI {
	private final JFrame select;

	private static final int START_WID = 300;
	private static final int START_HEI = 150;
	private static final int TEXT_LEN = 20;
	private static final int MAX_PLAYER = 4;
	private static final int MIN_PLAYER = 2;

	/**
	 * Constructor
	 */
	public StartUI() {
		select = new JFrame("Scrabble Game - Choose Number of Players");
		select.setSize(START_WID, START_HEI);
		select.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel welcome = new JLabel("Welcome to Ru's Scrabble Game");
		JLabel label = new JLabel("Enter number of players (2-4):");
		JTextField numText = new JTextField(TEXT_LEN);
		JButton newGame = new JButton("Start Game");
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int num;
				if (numText.getText() != null) {

					try {
						num = Integer.parseInt(numText.getText());
					} catch (NumberFormatException error) {
						numText.setText(null);
						return;
					}

					if (num >= MIN_PLAYER && num <= MAX_PLAYER) {
						select.setVisible(false);

						GameUI main = new GameUI(num);
						main.show();
					}
				}
			}
		});

		JPanel startGamePane = new JPanel();
		startGamePane.add(welcome);
		startGamePane.add(label);
		startGamePane.add(numText);
		startGamePane.add(newGame);

		select.getContentPane().setLayout(new BorderLayout());
		select.getContentPane().add(startGamePane, BorderLayout.CENTER);
	}

	/**
	 * show the startUI
	 */
	public void show() {
		select.setVisible(true);
	}

	/**
	 * creat and show GUI
	 */
	public static void createAndShowGUI() {
		StartUI start = new StartUI();
		start.show();
	}
}
