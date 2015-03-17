package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Word rack class to hold player's word tiles
 * 
 * @author zhaoru
 *
 */
public class WordRack {
	private List<WordTile> wordTiles;

	/**
	 * Constructor
	 */
	public WordRack() {
		wordTiles = new ArrayList<WordTile>();
	}

	/**
	 * get all word tiles of a player
	 * 
	 * @return all word tiles of a player
	 */
	public List<WordTile> getWordTiles() {
		return wordTiles;
	}

	/**
	 * get number of word tiles of a player
	 * 
	 * @return number of word tiles of a player
	 */
	public int getNum() {
		return wordTiles.size();
	}

	/**
	 * remove a word tile form the rack
	 * 
	 * @param word
	 *            the word tile to remove
	 */
	public void removeRackWord(WordTile word) {
		if (word != null && getNum() != 0)
			wordTiles.remove(word);
	}

	/**
	 * remove many words tile form the rack
	 * 
	 * @param words
	 *            a list of the word tiles to remove
	 */
	public void removeRackWords(List<WordTile> words) {
		if (words != null && words.size() > 0)
			wordTiles.removeAll(words);
	}

	/**
	 * add a word tile form the rack
	 * 
	 * @param word
	 *            the word tile to add
	 */
	public void addRackWord(WordTile word) {
		if (word != null)
			wordTiles.add(word);
	}

	/**
	 * add many words tile form the rack
	 * 
	 * @param words
	 *            a list of the word tiles to add
	 */
	public void addRackWords(List<WordTile> words) {
		if (words != null && words.size() > 0)
			wordTiles.addAll(words);
	}
}
