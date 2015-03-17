package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Check Dictionary class
 * 
 * @author zhaoru
 *
 */
public class DictionaryTest {
	private Dictionary dic;

	@Before
	public void setUp() {
		dic = new Dictionary();
	}

	/**
	 * check if the dictionary can check a word correctly
	 */
	@Test
	public void testIsValidWord() {
		assertFalse(dic.isValidWord(null));
		assertFalse(dic.isValidWord("i am a test"));
		assertFalse(dic.isValidWord("askjhkaj"));
		assertTrue(dic.isValidWord("abasing"));
		assertTrue(dic.isValidWord("renegotiating"));
		assertTrue(dic.isValidWord("taciturnity"));
	}

}
