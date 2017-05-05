package spellchecker.stemming;

import java.util.LinkedHashMap;

/**
 * Class that is called to remove suffixes in spellchecker.stemming.
 */
public class Suffixes {

	private final String consonant_r = "[^aeiour]";
	private final String vowel = "[aeiou]";

	LinkedHashMap<String, String> patternReplace = null;

	/**
	 * List of pre-defined suffixes that will be used by the stemmer
	 */
	public Suffixes() {
		/**
		 * key - prefix that will be matched in the inflected word. value -
		 * pattern that will be removed in the inflected word if the key
		 * matched.
		 */
		patternReplace = new LinkedHashMap<>();

		// assimilation
		patternReplace.put(".*uhin$", "uhin_o");
		patternReplace.put(".*uhan$", "uhan_o");
		patternReplace.put(".*uan$", "uan_o");
		patternReplace.put(".*uin$", "uin_o");

		patternReplace.put(".*[aeio]hin$", "hin");
		patternReplace.put(".*[aeio]han$", "han");

		// assimilation
		patternReplace.put(".*rin$", "rin_d");
		patternReplace.put(".*ran$", "ran_d");

		patternReplace.put(".*" + vowel + consonant_r + "in$", "in");
		patternReplace.put(".*" + vowel + consonant_r + "an$", "an");
		patternReplace.put(".*" + "ngin$", "in");
		patternReplace.put(".*" + "ngan$", "an");
	}

	/**
	 * Return the hashmap that contains the pre-defined suffixes
	 */
	public LinkedHashMap<String, String> getSuffixes() {
		return patternReplace;
	}
}
