package edu.cmu.cs.cs214.hw4.core;

import java.util.List;

/**
 * Game class is as a controller, it stores many information and connect players
 * and game attributes
 * 
 * @author zhaoru
 *
 */
public class Game {
	private int numPlayers, orderFlag, countPass, round;
	private Player[] players;
	private Player currentPlayer;
	private Player playerToAddScore;

	private Dictionary dictionary;
	private Board board;
	private CurrentMove move;
	private TileBag tileBag;
	private SpecialBag specialBag;

	private static final int MAX_WORD_RACK = 7;
	private static final int MID_SQUARE_INDEX = 112;
	private static final int NUM_A_LINE = 15;
	private static final int MAX_PLAYER = 4;
	private static final int MIN_PLAYER = 2;

	/**
	 * Constructor, set some attributes to defalut value
	 */
	public Game() {
		playerToAddScore = null;
		dictionary = new Dictionary();
		board = new Board();
		move = new CurrentMove();
		tileBag = new TileBag();
		specialBag = new SpecialBag();
		orderFlag = 1;
		countPass = 0;
		round = 0;
	}

	/**
	 * get all players
	 * 
	 * @return an array of players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * get current player
	 * 
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * set current player
	 * 
	 * @param currentPlayer1
	 *            current player to set
	 */
	public void setCurrentPlayer(Player currentPlayer1) {
		this.currentPlayer = currentPlayer1;
	}

	/**
	 * set the player who should be added the current score, different when
	 * score switch tile is triggered
	 * 
	 * @param playerToAddScore1
	 *            the player who should be added the current score
	 */
	public void setPlayerToAddScore(Player playerToAddScore1) {
		this.playerToAddScore = playerToAddScore1;
	}

	/**
	 * get dictionary
	 * 
	 * @return dictionary
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * get board
	 * 
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * get current move class instance
	 * 
	 * @return current move
	 */
	public CurrentMove getCurrentMove() {
		return move;
	}

	/**
	 * get tile bag
	 * 
	 * @return tile bag
	 */
	public TileBag getTileBag() {
		return tileBag;
	}

	/**
	 * get special bag
	 * 
	 * @return special bag
	 */
	public SpecialBag getSpecialBag() {
		return specialBag;
	}

	/**
	 * get the number of players
	 * 
	 * @return number of players
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * get the flag of order, which control the direction of next player
	 * 
	 * @return order's flag
	 */
	public int getOrderFlag() {
		return orderFlag;
	}

	/**
	 * get number of times of the pass option
	 * 
	 * @return number of times of the pass option
	 */
	public int getCountPass() {
		return countPass;
	}

	/**
	 * add one count pass number, used when swap button or pass button is
	 * pressed.
	 */
	public void addCountPass() {
		countPass += 1;
	}

	/**
	 * start a new game with certain number of players
	 * 
	 * @param num
	 *            number of players
	 */
	public void newGame(int num) {
		if (num >= MIN_PLAYER && num <= MAX_PLAYER) {
			players = new Player[num];
			numPlayers = num;
			for (int i = 0; i < num; i++) {
				players[i] = new Player();
				players[i].setName(String.valueOf(i));
			}
			setCurrentPlayer(players[0]);
			currentPlayer.setTurn(true);
			complementTiles();
			move.clear();
			round++;
		} else {
			System.out.println("Number of Players must be 2-4");
			return;
		}
	}

	/**
	 * after the round, change turn to next player, and do some reset and
	 * complement things
	 */
	public void nextPlayer() {
		currentPlayer.setTurn(false); /* should already be done */
		int currentIndex = Integer.parseInt(currentPlayer.getName());
		int nextIndex = currentIndex + orderFlag;
		if (nextIndex >= numPlayers) {
			nextIndex = 0;
		}
		if (nextIndex < 0) {
			nextIndex = numPlayers - 1;
		}
		setCurrentPlayer(players[nextIndex]);
		currentPlayer.setTurn(true);
		complementTiles();
		round++;
		move.clear();
	}

	/**
	 * check if the game is over, if the player's word rack is empty after
	 * complementing tile, the game is over. Or countPass reaches number of
	 * players.
	 * 
	 * @return true or false if the game is over
	 */
	public boolean isGameOver() {
		if (countPass == numPlayers)
			return true;
		if (currentPlayer.getWordRack().getNum() == 0 && tileBag.getNum() == 0)
			return true;
		return false;
	}

	/**
	 * compare the winner after the game
	 * 
	 * @return the player who win
	 */
	public Player compareWinner() {
		Player tempWinner = null;
		for (int i = 0; i < numPlayers; i++) {
			for (int j = i + 1; j < numPlayers; j++) {
				if (players[i].getScore() > players[j].getScore())
					tempWinner = players[i];
				else
					tempWinner = players[j];
			}
		}
		return tempWinner;
	}

	/**
	 * reverse the order, used for reverse tile
	 */
	public void reverseOrder() {
		orderFlag *= -1;
	}

	/**
	 * purchase a special from the special bag
	 * 
	 * @param special
	 *            special tile to purchase
	 */
	public void purchaseSpecialTile(SpecialTile special) {
		int money = currentPlayer.getScore();
		int num = currentPlayer.getSpecialRack().getNum();
		SpecialTile newSpecial = specialBag.getSpecialTile(money, num, special);
		if (newSpecial != null) {
			currentPlayer.addScore(-newSpecial.getCost());
			currentPlayer.getSpecialRack().addRackSpecial(newSpecial);
			newSpecial.setOwner(currentPlayer);
		}
	}

	/**
	 * use a special tile to a location
	 * 
	 * @param specialTile
	 *            the special tile to use
	 * @param coordinate
	 *            the coordinate to put
	 */
	public void useSpecialTile(SpecialTile specialTile, Coordinate coordinate) {
		if (coordinate.isInBoard()) {
			int num = coordinate.getRow() * NUM_A_LINE + coordinate.getCol();
			Square s = board.getSquares().get(num);
			if (s.getWord() == null) {
				if (s.getSpecial() != null) {
					s.getSpecial().setOwner(null);
					s.removeSpecial();
				}
				s.setSpecial(specialTile);
				specialTile.setOwner(currentPlayer);
				currentPlayer.getSpecialRack().removeRackSpecial(specialTile);
			}
		}
	}

	/**
	 * place a word tile to a location
	 * 
	 * @param wordTile
	 *            the word tile to place
	 * @param coordinate
	 *            the coordinate to place
	 */
	public void placeWord(WordTile wordTile, Coordinate coordinate) {
		if (coordinate.isInBoard()) {
			int num = coordinate.getRow() * NUM_A_LINE + coordinate.getCol();
			Square s = board.getSquares().get(num);
			if (s.getWord() == null) {
				s.setWord(wordTile);
				move.addSquare(s);
				currentPlayer.getWordRack().removeRackWord(wordTile);
			}
		}
	}

	/**
	 * swap some number of tiles, which is number safe
	 * 
	 * @param wordTiles
	 *            the word tiles to swap
	 */
	public void swapTiles(List<WordTile> wordTiles) {
		if (wordTiles != null && wordTiles.size() > 0) {
			int num = wordTiles.size();
			/* won't swap tiles if the number in bag is not enough */
			if (num <= tileBag.getNum()) {
				List<WordTile> words = tileBag.getWordTiles(num);
				currentPlayer.getWordRack().removeRackWords(wordTiles);
				currentPlayer.getWordRack().addRackWords(words);
				tileBag.addBagWords(wordTiles);
			}
		}
	}

	/**
	 * complement current player's number of tiles in word rack after each turn,
	 * if the number is not enough, complement a part
	 */
	public void complementTiles() {
		List<WordTile> words;
		int numCplmt, numTiles = currentPlayer.getWordRack().getNum();
		if (numTiles >= 0 && numTiles < MAX_WORD_RACK) {
			numCplmt = MAX_WORD_RACK - numTiles;
			if (tileBag.getNum() < numCplmt) {
				numCplmt = tileBag.getNum();
			}
			if (numCplmt != 0) {
				words = tileBag.getWordTiles(numCplmt);
				currentPlayer.getWordRack().addRackWords(words);
			}
		}
	}

	/**
	 * retreat current player's the move if the placements are not valid
	 */
	public void retreat() {
		List<Square> cs = move.getCurrentSquares();
		for (Square s : cs) {
			for (Square square : board.getSquares()) {
				if (square.getCoordinate().equals(s.getCoordinate())) {
					WordTile wt = square.getWord();
					currentPlayer.getWordRack().addRackWord(wt);
					square.removeWord();
				}
			}
		}
		currentPlayer.setTurn(true); // should not need
		move.clear();
	}

	/**
	 * check if the placements are valid and update the CurrentMove used for
	 * later calculation. Details about the check please see CurrentMove class.
	 * 
	 * @return true of false if the placements are valid or not
	 */
	public boolean isValid() {
		if (round == 1) {
			int count = 0;
			for (Square s1 : move.getCurrentSquares()) {
				if (board.getSquares().get(MID_SQUARE_INDEX).getCoordinate()
						.equals(s1.getCoordinate()))
					count++;
			}
			if (count == 0)
				return false;
		}

		for (Square s2 : move.getCurrentSquares()) {
			if (!s2.getCoordinate().isInBoard())
				return false;
		}

		if (board.getNum() != move.getCurrentSquares().size()) {
			if (round > 1 && !move.isAdjacentToOldWords(this))
				return false;
		}

		if (move.getCurrentSquares().size() == 1) {
			if (move.checkFirstWordcol(this) || move.checkFirstWordrow(this))
				return true;
		}

		if (move.rowAllEqual()) {
			move.sortBycol();
			if (move.checkIsLinecol(board)) {
				if (move.checkFirstWordcol(this)) {
					for (Square s3 : move.getCurrentSquares()) {
						if (!move.checkAdjacentWordcol(this, s3))
							return false;
					}
					return true;
				}
			}
		} else if (move.colAllEqual()) {
			move.sortByrow();
			if (move.checkIsLinerow(board)) {
				if (move.checkFirstWordrow(this)) {
					for (Square s4 : move.getCurrentSquares()) {
						if (!move.checkAdjacentWordrow(this, s4))
							return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * calculate current score by doing the letter bonus and then word bonus,
	 * and then the effect of special tiles, both to current move and game board
	 */
	public void move() {
		int currentScore;
		move.addLetterBonus(board);
		move.addWordBonus(board);
		for (Square square : move.getCurrentSquares()) {
			if (square.getSpecial() != null)
				square.getSpecial().effect(this, square.getCoordinate());
		}
		currentScore = move.getCurrentScore();

		/*
		 * this is used for the score switch tile, if the tile is triggered,
		 * playerToAddScore is not null, and should be the owner of that tile
		 */
		if (playerToAddScore == null) {
			playerToAddScore = currentPlayer;
		}
		playerToAddScore.addScore(currentScore);

		complementTiles();
		countPass = 0;
		currentPlayer.setTurn(false);

		setPlayerToAddScore(null);
	}
}
