package spellchecker.detector;

import com.BoxOfC.MDAG.MDAG;

import spellchecker.utility.Configuration;

/**
 * 
 * Approach in the spell checking system of detecting whether a word exists
 * in the language or not using dictionary look-up.
 * 
 **/
public class DictLookUp {

	private MDAG engDict = null;
	private MDAG filiDict = null;

	public DictLookUp(MDAG engDict, MDAG filiDict) {
		this.engDict = engDict;
		this.filiDict = filiDict;
	}

	/**
	 * @param word
	 *            - the word to be checked in English dictionary
	 * 
	 * @return whether word exists in English dictionary or not
	 */
	public boolean checkEngDict(String word) {
		return engDict.contains(word);
	}

	/**
	 * @param word
	 *            - the word to be checked in Filipino dictionary
	 *
	 * @return whether word exists in English dictionary or not
	 */
	public boolean checkFiliDict(String word) {
		return filiDict.contains(word);
	}

	/**
	 * @param word
	 *            - the word to be checked in Filipino and English
	 *            dictionary,respectively.
	 * 
	 * @return whether word exists in either dictionaries or not
	 */
	public boolean checkDict(String word) {

		// All words with length < Configuration.MINIMUM_WORD_LENGTH are considered correct
		if ((word.length() < Configuration.MINIMUM_WORD_LENGTH))
			return true;

		boolean inDictionary = false;
		inDictionary = checkFiliDict(word);
		if (!inDictionary)
			inDictionary = checkEngDict(word);

		return inDictionary;
	}
}
