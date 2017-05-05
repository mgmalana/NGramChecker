package spellchecker.stemming;

import spellchecker.utility.Configuration;

/**
 * 
 * The class that contains the rule in spellchecker.stemming partial reduplication.
 * 
 **/
public class PartialReduplication {

	/** Word to be stemmed by the stemmer */
	private String word;
	/** Regular expression of vowels */
	private final String vowel = "[aeiou]";
	/** Regular expression of consonants */
	private final String consonant = "[^aeiou]";
	private String tempWord;

	/**
	 * @param word
	 *            that will be stemmed.
	 */
	public void testPartialRedup(String word) {
		try {
			this.word = word;
			this.tempWord = word;

			if (!(word.length() <= Configuration.MINIMUM_WORD_LENGTH)) {
				if (word.contains("-")) {
					String[] split = word.split("-");

					// splitting reduplication and hyphenation of the word.
					// ex. pasa-pasahan = pasahan
					if (split[0].length() != split[1].length() && split[1].contains(split[0])) {
						this.word = split[1];
					}
				}

				// ex. iisa = isa
				else if (word.charAt(0) == word.charAt(1)) {
					redupRule1();
				}
				// ex. babasa = basa
				else if (word.matches(consonant + vowel + ".*")
						&& (word.substring(0, 2).equals(word.substring(2, 4)))) {
					redupRule2();
				}
				// TO-DO example
				else if (word.matches(consonant + vowel + consonant + consonant + vowel + ".*")
						&& (word.substring(0, 2).equals(word.charAt(2) + "" + word.charAt(4)))) {
					redupRule3();
				}
				// TO-DO example
				else if (word.matches(consonant + consonant + vowel + consonant + consonant + vowel + ".*")
						&& (word.substring(0, 3).equals(word.substring(3, 6)))) {
					redupRule4();
				}
			}
		} catch (IndexOutOfBoundsException exception) {
			this.word = tempWord;
		}
	}

	/**
	 * Reduplicates the cluster of consonants including the succeeding vowel of
	 * the stem.
	 */
	private void redupRule4() {
		word = word.substring(3);
	}

	/** If the first syllable of the root has a cluster of consonants, */
	private void redupRule3() {
		word = word.substring(2);
	}

	/**
	 * In a two-syllable root, if the first syllable of the stem starts with a
	 * consonant- vowel, the consonant and the succeeding vowel is reduplicated.
	 */
	private void redupRule2() {
		word = word.substring(2);
	}

	/**
	 * If the root of a two-syllable word begins with a vowel, the initial
	 * letter is repeated.
	 */
	private void redupRule1() {
		word = word.substring(1);
	}

	/**
	 * @return tempword if word length < Configuration.MINIMUM_WORD_LENGTH
	 *         otherwise the word.
	 * 
	 */
	public String getPartialRedup() {
		if (word.length() < Configuration.MINIMUM_WORD_LENGTH)
			return tempWord;
		else
			return word;
	}

}
