package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * CurrentMove class stores all the temp info about the move within this round,
 * with a ScoreWord class in it
 * 
 * @author zhaoru
 *
 */
public class CurrentMove {
	private List<Square> currentSquares;
	private List<List<ScoreWord>> scoreWordSquares;
	private List<String> newWords;
	private static final int NUM_A_LINE = 15;

	/**
	 * ScoreWord class provide a couple of square to real score format, which is
	 * one pair inide the scoreWordSquares list
	 * 
	 * @author zhaoru
	 *
	 */
	class ScoreWord {
		private int score;
		private Square square;

		/**
		 * Constructor
		 * 
		 * @param newSquare
		 *            a square as one part of the ScoreWord
		 */
		public ScoreWord(Square newSquare) {
			square = newSquare;
			score = square.getWord().getScore();
		}

		/**
		 * get score
		 * 
		 * @return score
		 */
		public int getScore() {
			return this.score;
		}

		/**
		 * get square
		 * 
		 * @return square
		 */
		public Square getSquare() {
			return this.square;
		}

		/**
		 * turn the score to negative, which is used for negative tile
		 */
		public void negativeScore() {
			score = -score;
		}

		/**
		 * time a score by flag, used for letter bonus and word bonus
		 * 
		 * @param flag
		 *            the multiplier
		 */
		public void multiplyScore(int flag) {
			score *= flag;
		}
	}

	/**
	 * Constructor, set the instance variables to default
	 */
	public CurrentMove() {
		clear();
	}

	/**
	 * set the instance variables to default
	 */
	public void clear() {
		currentSquares = new ArrayList<Square>();
		scoreWordSquares = new ArrayList<List<ScoreWord>>();
		newWords = new ArrayList<String>();
	}

	/**
	 * get newWord, which is a word list for the new generated word
	 * 
	 * @return a word list for the new generated word
	 */
	public List<String> getNewWords() {
		return newWords;
	}

	/**
	 * get the squares that are put in within this round
	 * 
	 * @return the squares that are put in within this round
	 */
	public List<Square> getCurrentSquares() {
		return currentSquares;
	}

	/**
	 * get ScoreWordSquares
	 * 
	 * @return the list of ScoreWord list that consists of all things that can
	 *         be added to score
	 */
	public List<List<ScoreWord>> getScoreWordSquares() {
		return scoreWordSquares;
	}

	/**
	 * add a square to current squares
	 * 
	 * @param square
	 *            a square to add
	 */
	public void addSquare(Square square) {
		currentSquares.add(square);
	}

	/**
	 * Check if the new tiles are adjacent to old words. Eg: you cannot place a
	 * word far away which is not adjacent to the old words.
	 * 
	 * @param game
	 *            the game instance to parse in
	 * 
	 * @return true if valid or false of not
	 */
	public boolean isAdjacentToOldWords(Game game) {
		int count = 0;
		Coordinate cur;
		Square up, down, left, right;
		for (Square square : currentSquares) {
			cur = square.getCoordinate();
			up = game.getBoard().getSquareByCoord(
					new Coordinate(cur.getRow() - 1, cur.getCol()));
			down = game.getBoard().getSquareByCoord(
					new Coordinate(cur.getRow() + 1, cur.getCol()));
			left = game.getBoard().getSquareByCoord(
					new Coordinate(cur.getRow(), cur.getCol() - 1));
			right = game.getBoard().getSquareByCoord(
					new Coordinate(cur.getRow(), cur.getCol() + 1));

			if (up != null && up.getWord() != null) {
				for (Square s : currentSquares) {
					if (up.getCoordinate().equals(s.getCoordinate())) {
						count++;
					}
				}
				if (count == 0) {
					return true;
				}
				count = 0;
			}

			if (down != null && down.getWord() != null) {
				for (Square s : currentSquares) {
					if (down.getCoordinate().equals(s.getCoordinate())) {
						count++;
					}
				}
				if (count == 0) {
					return true;
				}
				count = 0;
			}

			if (left != null && left.getWord() != null) {
				for (Square s : currentSquares) {
					if (left.getCoordinate().equals(s.getCoordinate())) {
						count++;
					}
				}
				if (count == 0) {
					return true;
				}
				count = 0;
			}

			if (right != null && right.getWord() != null) {
				for (Square s : currentSquares) {
					if (right.getCoordinate().equals(s.getCoordinate())) {
						count++;
					}
				}
				if (count == 0) {
					return true;
				}
				count = 0;
			}
		}
		return false;
	}

	/**
	 * check if currentSquares all coordinate can form a line with same row
	 * number
	 * 
	 * @return true or false
	 */
	public boolean rowAllEqual() {
		if (currentSquares != null) {
			if (currentSquares.size() > 1) {
				for (int i = 1; i < currentSquares.size(); i++) {
					if (currentSquares.get(0).getCoordinate().getRow() != currentSquares
							.get(i).getCoordinate().getRow())
						return false;
				}
				return true;
			} else if (currentSquares.size() == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * check if currentSquares all coordinate can form a line with same col
	 * number
	 * 
	 * @return true or false
	 */
	public boolean colAllEqual() {
		if (currentSquares != null) {
			if (currentSquares.size() > 1) {
				for (int i = 1; i < currentSquares.size(); i++) {
					if (currentSquares.get(0).getCoordinate().getCol() != currentSquares
							.get(i).getCoordinate().getCol())
						return false;
				}
				return true;
			} else if (currentSquares.size() == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * sort the currentSquares as the ascending order of col coordinate
	 */
	public void sortBycol() {
		if (currentSquares != null && currentSquares.size() > 1) {
			Collections.sort(currentSquares, new Comparator<Square>() {
				@Override
				public int compare(Square s1, Square s2) {
					return s1.getCoordinate().getCol()
							- s2.getCoordinate().getCol();
				}
			});
		}
	}

	/**
	 * sort the currentSquares as the ascending order of row coordinate
	 */
	public void sortByrow() {
		if (currentSquares != null && currentSquares.size() > 1) {
			Collections.sort(currentSquares, new Comparator<Square>() {
				@Override
				public int compare(Square s1, Square s2) {
					return s1.getCoordinate().getRow()
							- s2.getCoordinate().getRow();
				}
			});
		}
	}

	/**
	 * check if all col are one bigger than former, also consider when current
	 * square and old squares on board can together form a line
	 * 
	 * @param board
	 *            parse in the board to check if current square and old squares
	 *            on board can together form a line
	 * @return true or false
	 */
	public boolean checkIsLinecol(Board board) {
		if (currentSquares != null) {
			if (currentSquares.size() > 1) {
				for (int i = 0; i < currentSquares.size() - 1; i++) {
					if ((currentSquares.get(i).getCoordinate().getCol() + 1) != currentSquares
							.get(i + 1).getCoordinate().getCol()) {
						// check if gap squares on board has word tiles
						int row = currentSquares.get(i).getCoordinate()
								.getRow();
						int colNext = currentSquares.get(i).getCoordinate()
								.getCol() + 1;
						int colGapEnd = currentSquares.get(i + 1)
								.getCoordinate().getCol();
						for (int j = colNext; j < colGapEnd; j++) {
							if (board.getSquareByCoord(new Coordinate(row, j))
									.getWord() == null)
								return false;
						}
					}
				}
				return true;
			} else if (currentSquares.size() == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * check if all row are one bigger than former, also consider when current
	 * square and old squares on board can together form a line
	 * 
	 * @param board
	 *            parse in the board to check if current square and old squares
	 *            on board can together form a line
	 * @return true or false
	 */
	public boolean checkIsLinerow(Board board) {
		if (currentSquares != null) {
			if (currentSquares.size() > 1) {
				for (int i = 0; i < currentSquares.size() - 1; i++) {
					if ((currentSquares.get(i).getCoordinate().getRow() + 1) != currentSquares
							.get(i + 1).getCoordinate().getRow()) { // check if
																	// gap
																	// squares
																	// on board
																	// has word
																	// tiles
						int col = currentSquares.get(i).getCoordinate()
								.getCol();
						int rowNext = currentSquares.get(i).getCoordinate()
								.getRow() + 1;
						int rowGapEnd = currentSquares.get(i + 1)
								.getCoordinate().getRow();
						for (int j = rowNext; j < rowGapEnd; j++) {
							if (board.getSquareByCoord(new Coordinate(j, col))
									.getWord() == null)
								return false;
						}
					}
				}
				return true;
			} else if (currentSquares.size() == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * check if the line with same row can form a word, and store info into
	 * scoreWordSquares and newWords
	 * 
	 * @param game
	 *            we need the board and dictionary to check
	 * @return true or false
	 */
	public boolean checkFirstWordcol(Game game) {
		StringBuilder sb = new StringBuilder();
		List<ScoreWord> firstWord = new ArrayList<ScoreWord>();
		int row = currentSquares.get(0).getCoordinate().getRow();
		int colLeft = currentSquares.get(0).getCoordinate().getCol();
		int colRight = currentSquares.get(currentSquares.size() - 1)
				.getCoordinate().getCol();
		while (colLeft > 0
				&& game.getBoard().getSquares()
						.get(row * NUM_A_LINE + colLeft - 1).getWord() != null) {
			colLeft--;
		}
		while (colRight < (NUM_A_LINE - 1)
				&& game.getBoard().getSquares()
						.get(row * NUM_A_LINE + colRight + 1).getWord() != null) {
			colRight++;
		}
		for (int i = colLeft; i < colRight + 1; i++) {
			Square s = game.getBoard().getSquares().get(row * NUM_A_LINE + i);
			firstWord.add(new ScoreWord(s));
			sb.append(s.getWord().getLetter());
		}
		scoreWordSquares.add(firstWord);

		if (game.getDictionary().isValidWord(sb.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if the line with same col can form a word, and store info into
	 * scoreWordSquares and newWords
	 * 
	 * @param game
	 *            we need the board and dictionary to check
	 * @return true or false
	 */
	public boolean checkFirstWordrow(Game game) {
		StringBuilder sb = new StringBuilder();
		List<ScoreWord> firstWord = new ArrayList<ScoreWord>();
		int col = currentSquares.get(0).getCoordinate().getCol();
		int rowUp = currentSquares.get(0).getCoordinate().getRow();
		int rowDown = currentSquares.get(currentSquares.size() - 1)
				.getCoordinate().getRow();
		while (rowUp > 0
				&& game.getBoard().getSquares()
						.get((rowUp - 1) * NUM_A_LINE + col).getWord() != null) {
			rowUp--;
		}
		while (rowDown < (NUM_A_LINE - 1)
				&& game.getBoard().getSquares()
						.get((rowDown + 1) * NUM_A_LINE + col).getWord() != null) {
			rowDown++;
		}
		for (int j = rowUp; j < rowDown + 1; j++) {
			Square s = game.getBoard().getSquares().get(j * NUM_A_LINE + col);
			firstWord.add(new ScoreWord(s));
			sb.append(s.getWord().getLetter());
		}
		scoreWordSquares.add(firstWord);

		if (game.getDictionary().isValidWord(sb.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check the square's vertical adjacent word, and update the attributes of
	 * CurrentMove
	 * 
	 * @param game
	 *            game we need the board and dictionary to check
	 * @param square
	 *            the certain square to check
	 * @return true or false
	 */
	public boolean checkAdjacentWordcol(Game game, Square square) {
		StringBuilder sb = new StringBuilder();
		List<ScoreWord> adjacentWord = new ArrayList<ScoreWord>();
		int col = square.getCoordinate().getCol();
		int rowUp = square.getCoordinate().getRow();
		int rowDown = square.getCoordinate().getRow();
		while (rowUp > 0
				&& game.getBoard().getSquares()
						.get((rowUp - 1) * NUM_A_LINE + col).getWord() != null) {
			rowUp--;
		}
		while (rowDown < (NUM_A_LINE - 1)
				&& game.getBoard().getSquares()
						.get((rowDown + 1) * NUM_A_LINE + col).getWord() != null) {
			rowDown++;
		}
		if (rowUp != rowDown) {
			for (int j = rowUp; j < rowDown + 1; j++) {
				Square s = game.getBoard().getSquares()
						.get(j * NUM_A_LINE + col);
				adjacentWord.add(new ScoreWord(s));
				sb.append(s.getWord().getLetter());
			}
			if (game.getDictionary().isValidWord(sb.toString())) {
				newWords.add(sb.toString());
				scoreWordSquares.add(adjacentWord);
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * check the square's horizontal adjacent word, and update the attributes of
	 * CurrentMove
	 * 
	 * @param game
	 *            game we need the board and dictionary to check
	 * @param square
	 *            the certain square to check
	 * @return true or false
	 */
	public boolean checkAdjacentWordrow(Game game, Square square) {
		StringBuilder sb = new StringBuilder();
		List<ScoreWord> adjacentWord = new ArrayList<ScoreWord>();
		int row = square.getCoordinate().getRow();
		int colLeft = square.getCoordinate().getCol();
		int colRight = square.getCoordinate().getCol();
		while (colLeft > 0
				&& game.getBoard().getSquares()
						.get(row * NUM_A_LINE + colLeft - 1).getWord() != null) {
			colLeft--;
		}
		while (colRight < (NUM_A_LINE - 1)
				&& game.getBoard().getSquares()
						.get(row * NUM_A_LINE + colRight + 1).getWord() != null) {
			colRight++;
		}
		if (colLeft != colRight) {
			for (int i = colLeft; i < colRight + 1; i++) {
				Square s = game.getBoard().getSquares()
						.get(row * NUM_A_LINE + i);
				adjacentWord.add(new ScoreWord(s));
				sb.append(s.getWord().getLetter());
			}
			if (game.getDictionary().isValidWord(sb.toString())) {
				newWords.add(sb.toString());
				scoreWordSquares.add(adjacentWord);
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * Update letter bonus to all scoreWordSquares. Any letter bonus can be used
	 * once, which is used for the first word on it. Then this letter bonus
	 * would become 1
	 * 
	 * @param board
	 *            board to parse in, used to find the letter bonus square and
	 *            set the multiplier to 1
	 */
	public void addLetterBonus(Board board) {
		int letterBonus;
		if (scoreWordSquares != null) {
			for (List<ScoreWord> word : scoreWordSquares) {
				for (ScoreWord scoreWord : word) {
					letterBonus = scoreWord.getSquare().getLetterBonus();
					scoreWord.multiplyScore(letterBonus);

					Coordinate coord = scoreWord.getSquare().getCoordinate();
					board.getSquareByCoord(coord).setLetterBonus(1); // letter
																		// bonus
																		// cannot
																		// use
																		// twice
				}
			}
		}
	}

	/**
	 * Update word bonus to all scoreWordSquares. Any word bonus can be used
	 * once, which is used for the first word on it. Then this word bonus would
	 * become 1
	 * 
	 * @param board
	 *            board to parse in, used to find the word bonus squares and set
	 *            the multiplier to 1
	 */
	public void addWordBonus(Board board) {
		if (scoreWordSquares != null) {
			for (List<ScoreWord> word : scoreWordSquares) {
				Iterator<ScoreWord> iterator = word.iterator();
				while (iterator.hasNext()) {
					ScoreWord sw = iterator.next();
					int wordBonus = sw.getSquare().getWordBonus();
					if (wordBonus > 1) {
						for (ScoreWord scoreWord : word) {
							scoreWord.multiplyScore(wordBonus);
						}

						Coordinate coord = sw.getSquare().getCoordinate();
						board.getSquareByCoord(coord).setWordBonus(1); // word
																		// bonus
																		// cannot
																		// use
																		// twice
					}
				}
			}
		}
	}

	/**
	 * the effect that a boom tile can affect to the current squares
	 * 
	 * @param removeSquares
	 *            the boomed tiles that need to be removed
	 */
	public void boomedTiles(List<Square> removeSquares) {
		if (removeSquares != null && removeSquares.size() > 0) {
			List<ScoreWord> removeList;
			for (Square rs : removeSquares) {
				for (List<ScoreWord> word : scoreWordSquares) {

					removeList = new ArrayList<ScoreWord>();
					for (ScoreWord scoreWord : word) {
						if (rs.getCoordinate().equals(
								scoreWord.getSquare().getCoordinate()))
							removeList.add(scoreWord);
					}
					word.removeAll(removeList);
				}
			}
		}
	}

	/**
	 * the effect that a negative tile can affect to the current squares
	 */
	public void negativeTiles() {
		for (List<ScoreWord> word : scoreWordSquares) {
			for (ScoreWord scoreWord : word) {
				scoreWord.negativeScore();
			}
		}
	}

	/**
	 * get current score, should execute at the end of move method
	 * 
	 * @return the score influenced by bonus square and special tiles
	 */
	public int getCurrentScore() {
		int currentScore = 0;
		for (List<ScoreWord> word : scoreWordSquares) {
			for (ScoreWord scoreWord : word) {
				currentScore += scoreWord.getScore();
			}
		}
		return currentScore;
	}
}
