package edu.cmu.cs.cs214.hw4.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Dictionary that is responsible for building and check each word
 * 
 * @author zhaoru
 *
 */
public class Dictionary {
	private static final String DIC_FILE = "src/main/resources/words.txt";
	private Set<String> dictionary;

	/**
	 * Constructor to build the dictionary and prompt
	 */
	public Dictionary() {
		System.out.println("Building the dictionary, please wait...");
		dictionary = new HashSet<String>();
		build(DIC_FILE);
	}

	/**
	 * helper function to build the dictionary
	 * 
	 * @param filename
	 *            filename to load the dictionary
	 */
	private void build(String filename) {
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = bf.readLine()) != null) {
				dictionary.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Name Error!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO Error!");
			e.printStackTrace();
		}

	}

	/**
	 * check if a word is inside the dictionary
	 * 
	 * @param word
	 *            the word to check
	 * @return true or false
	 */
	public boolean isValidWord(String word) {
		if (dictionary.contains(word))
			return true;
		return false;
	}
}
