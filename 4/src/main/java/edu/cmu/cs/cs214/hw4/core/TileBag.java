package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The tile bag class hold all tile of a game
 * 
 * @author zhaoru
 *
 */
public class TileBag {
	private List<WordTile> wordTiles;

	/**
	 * Constructor, initialize all word tiles, with letter and score, totally 98
	 * tiles
	 */
	public TileBag() {
		wordTiles = new ArrayList<WordTile>();
		int wordCount;
		// CHECKSTYLE:OFF
		for (wordCount = 0; wordCount < 12; wordCount++) {
			wordTiles.add(new WordTile("e", 1));
		}
		wordCount = 0;
		for (wordCount = 0; wordCount < 9; wordCount++) {
			wordTiles.add(new WordTile("a", 1));
			wordTiles.add(new WordTile("i", 1));
		}
		wordCount = 0;
		for (wordCount = 0; wordCount < 8; wordCount++) {
			wordTiles.add(new WordTile("o", 1));
		}
		wordCount = 0;
		for (wordCount = 0; wordCount < 6; wordCount++) {
			wordTiles.add(new WordTile("n", 1));
			wordTiles.add(new WordTile("r", 1));
			wordTiles.add(new WordTile("t", 1));
		}
		wordCount = 0;
		for (wordCount = 0; wordCount < 4; wordCount++) {
			wordTiles.add(new WordTile("l", 1));
			wordTiles.add(new WordTile("s", 1));
			wordTiles.add(new WordTile("u", 1));
			wordTiles.add(new WordTile("d", 2));
		}
		wordCount = 0;
		for (wordCount = 0; wordCount < 3; wordCount++) {
			wordTiles.add(new WordTile("g", 2));
		}
		wordCount = 0;
		for (wordCount = 0; wordCount < 2; wordCount++) {
			wordTiles.add(new WordTile("b", 3));
			wordTiles.add(new WordTile("c", 3));
			wordTiles.add(new WordTile("m", 3));
			wordTiles.add(new WordTile("p", 3));
			wordTiles.add(new WordTile("f", 4));
			wordTiles.add(new WordTile("h", 4));
			wordTiles.add(new WordTile("v", 4));
			wordTiles.add(new WordTile("w", 4));
			wordTiles.add(new WordTile("y", 4));
		}
		wordCount = 0;
		wordTiles.add(new WordTile("k", 5));
		wordTiles.add(new WordTile("j", 8));
		wordTiles.add(new WordTile("x", 8));
		wordTiles.add(new WordTile("q", 10));
		wordTiles.add(new WordTile("z", 10));
		// CHECKSTYLE:ON
	}

	/**
	 * shuffle all tiles inside the tile bag
	 */
	public void shuffle() {
		Collections.shuffle(wordTiles, new Random(System.currentTimeMillis()));
	}

	/**
	 * check if the tile bag is empty
	 * 
	 * @return true or false
	 */
	public boolean isEmpty() {
		return wordTiles.size() == 0;
	}

	/**
	 * get the number of tiles remained inside the tile bag
	 * 
	 * @return the number of tiles remained inside the tile bag
	 */
	public int getNum() {
		return wordTiles.size();
	}

	/**
	 * remove a word from the bag
	 * 
	 * @param word
	 *            the word tile to remove
	 */
	public void removeBagWord(WordTile word) {
		if (word != null && !isEmpty())
			wordTiles.remove(word);
	}

	/**
	 * remove many words from the bag
	 * 
	 * @param words
	 *            a list of word tiles to remove
	 */
	public void removeBagWords(List<WordTile> words) {
		if (words != null && words.size() > 0)
			wordTiles.removeAll(words);
	}

	/**
	 * add a word from the bag
	 * 
	 * @param word
	 *            the word tile to add
	 */
	public void addBagWord(WordTile word) {
		if (word != null)
			wordTiles.add(word);
	}

	/**
	 * add many words from the bag
	 * 
	 * @param words
	 *            a list of word tiles to add
	 */
	public void addBagWords(List<WordTile> words) {
		if (words != null && words.size() > 0)
			wordTiles.addAll(words);
	}

	/**
	 * get certain number of tiles if available and remove those tiles from bag
	 * 
	 * @param num
	 *            number of tiles to get
	 * @return certain number of random tiles
	 */
	public List<WordTile> getWordTiles(int num) {
		List<WordTile> gotTiles = new ArrayList<WordTile>();
		if (!isEmpty()) {
			if (num > 0 && num <= getNum()) {
				shuffle();
				for (int i = 0; i < num; i++) {
					gotTiles.add(wordTiles.get(0));
					wordTiles.remove(0);
				}
			}
		}
		return gotTiles;
	}
}
