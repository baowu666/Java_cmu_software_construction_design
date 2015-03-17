package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import edu.cmu.cs.cs214.hw4.core.Coordinate;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.WordTile;

/**
 * The GUI for the game playing
 * 
 * @author zhaoru
 *
 */
public class GameUI {
	private final Game game;
	private final JFrame frame;
	private final JFrame winner;
	private final ScorePanel scorePanel;
	private final BoardPanel boardPanel;
	private final InteractionPanel interactionPanel;
	private final RackPanel rackPanel;
	private final InstructionPanel instructionPanel;

	// interaction of board and rack when placing tiles. reset after each round
	private WordTile wtClick;
	private SpecialTile stClick;
	private Coordinate coordClick;

	private static final int END_WID = 400;
	private static final int END_HEI = 100;

	/**
	 * Constructor
	 * 
	 * @param numPlayers
	 *            number of players parsed in from startUI
	 */
	public GameUI(int numPlayers) {
		game = new Game();
		game.newGame(numPlayers);

		scorePanel = new ScorePanel(game);
		boardPanel = new BoardPanel(game, this);
		interactionPanel = new InteractionPanel(game, this);
		rackPanel = new RackPanel(game, this);
		instructionPanel = new InstructionPanel();

		resetClicked();

		frame = new JFrame("Ru Zhao's Scrabble Game");
		frame.setResizable(true);
		addElements(frame.getContentPane()); // Adds all of the panels
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		winner = new JFrame("Scrabble Game - Winner");
		winner.setSize(END_WID, END_HEI);
		winner.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void addElements(Container container) {
		container.setLayout(new BorderLayout());
		container.add(scorePanel, BorderLayout.NORTH);
		container.add(boardPanel, BorderLayout.CENTER);
		container.add(interactionPanel, BorderLayout.EAST);
		container.add(instructionPanel, BorderLayout.WEST);
		container.add(rackPanel, BorderLayout.SOUTH);
	}

	/**
	 * set number of players
	 * 
	 * @param num
	 *            number of players
	 */
	public void setNumPlayers(int num) {
		game.newGame(num);
	}

	/**
	 * get the clicked and stored word tile from word rack
	 * 
	 * @return the clicked and stored word tile from word rack
	 */
	public WordTile getWtClick() {
		return wtClick;
	}

	/**
	 * set the clicked and stored word tile from word rack
	 * 
	 * @param newWtClick
	 *            the clicked and stored word tile from word rack
	 */
	public void setWtClick(WordTile newWtClick) {
		wtClick = newWtClick;
	}

	/**
	 * get the clicked and stored special tile from special rack
	 * 
	 * @return the clicked and stored special tile from special rack
	 */
	public SpecialTile getStClick() {
		return stClick;
	}

	/**
	 * set the clicked and stored special tile from special rack
	 * 
	 * @param newStClick
	 *            the clicked and stored special tile from special rack
	 */
	public void setStClick(SpecialTile newStClick) {
		stClick = newStClick;
	}

	/**
	 * get the clicked and stored coordinate from board
	 * 
	 * @return the clicked and stored coordinate from board
	 */
	public Coordinate getCoordClick() {
		return coordClick;
	}

	/**
	 * set the clicked and stored coordinate from board
	 * 
	 * @param newCoordClick
	 *            the clicked and stored coordinate from board
	 */
	public void setCoordClick(Coordinate newCoordClick) {
		coordClick = newCoordClick;
	}

	/**
	 * reset these interactions of board and rack when placing tiles
	 */
	public void resetClicked() {
		wtClick = null;
		stClick = null;
		coordClick = null;
	}
	
	/**
	 * update or refresh the display
	 */
	public void update() {
		scorePanel.refresh();
		boardPanel.refresh();
		rackPanel.refresh();
	}
	
	/**
	 * update or refresh the display except for the board
	 */
	public void updateNoBoard() {
		scorePanel.refresh();
		rackPanel.refresh();
	}
	
	/**
	 * show the game frame
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * show the game over frame, not show the game frame
	 * 
	 * @param player
	 *            the winner
	 */
	public void gameOver(Player player) {
		frame.setVisible(false);

		int index = Integer.parseInt(player.getName()) + 1;
		int score = player.getScore();

		JPanel winnerPane = new JPanel();
		JLabel winnerLabel = new JLabel("Congradulations! Player "
				+ String.valueOf(index) + " wins! With total score: "
				+ String.valueOf(score));
		winnerPane.add(winnerLabel);
		winner.getContentPane().add(winnerPane, BorderLayout.CENTER);

		winner.setVisible(true);
	}

	/**
	 * Create and show GUI
	 */
	public static void createAndShowGUI() {
		StartUI start = new StartUI();
		start.show();
	}

	/**
	 * Main method of the game
	 * 
	 * @param args
	 *            arguments of main method
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
