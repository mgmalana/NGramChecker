package spellchecker.stemming;

import java.util.LinkedHashMap;

/**
 * Class that is called to remove infixes in spellchecker.stemming. The class will only be
 * called if Configuration.LIGHT_STEMMER == FALSE.
 */
public class Infixes {

	private LinkedHashMap<String, String> patternReplace;
	private final String consonant = "(^[^aeiou])";
	private final String vowel = "[aeiou]";

	/**
	 * List of pre-defined infixes that will be used by the stemmer
	 */
	public Infixes() {
		/**
		 * key - prefix that will be matched in the inflected word. value -
		 * pattern that will be removed in the inflected word if the key
		 * matched.
		 */
		patternReplace = new LinkedHashMap<String, String>();

		patternReplace.put(consonant + "um" + "(" + vowel + ".*)", "$1$2");
		patternReplace.put(consonant + "in" + "(" + vowel + ".*)", "$1$2");
	}

	public LinkedHashMap<String, String> getInfixes() {
		return patternReplace;
	}
}
