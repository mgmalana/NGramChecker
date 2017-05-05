package spellchecker.stemming;

/**
 * Class that is called to remove affixes in spellchecker.stemming. This class is used to
 * check the inflected word before the other (prefix,suffix, and infix)
 */
public class CustomizedRule {

	private String word = "";
	private String vowel = "[aeiou]";
	private String consonant = "[^aeiou]";

	// D-R alternation
	private String pattern = "(.*d" + vowel + ")r(" + vowel + ".*)";

	public void testCustomeRules(String word) {
		this.word = word;
		testCustomizedRule();
	}

	private void testCustomizedRule() {

		// ex. mapansin = pansin
		if (word.matches("^mapa.*") && word.matches(".*[an|in]$")) {
			this.word = word.substring(2);
		}

		// ex. mainom = inom; nainit = init
		else if ((word.startsWith("mai") || word.startsWith("nai"))
				&& word.matches("..." + consonant + vowel + consonant)) {

			this.word = word.substring(2);
		}

		else if (word.matches("^mai" + consonant + consonant + ".*")) {
			this.word = word.substring(2);
		}

		// ex. makain = kain
		else if (word.startsWith("maka") && word.substring(4).length() <= 3)
			this.word = word.substring(2);

		// ex. iakyat = akyat; ibihis = bihis; ipon = ipon
		else if ((word.matches("^i" + vowel + ".*"))
				|| (word.matches("^i" + consonant + ".*") && !(word.substring(1).length() <= 3))) {
			this.word = word.substring(1);

		}

		// ex. darasal = dadasal
		else if (word.matches(pattern)) {
			this.word = word.replaceFirst(pattern, "$1d$2");
		}

		// ex. umubo = ubo
		else if (word.matches("^um" + vowel + consonant + vowel))
			this.word = word.substring(2);

		else if (word.matches(".*ng"))
			this.word = word.substring(0, word.length());
	}

	/**
	 * @param word
	 *            - inflected word.
	 * @return replaced the u to o (ex. tauhan => tau => tao).
	 */
	public String changeU2O(String word) {
		String pattern = "(.*)u(" + consonant + ")$";
		if (word.matches(pattern)) {
			return word.replaceFirst(pattern, "$1o$2");

		}
		return "";
	}

	/**
	 * @return return the current word inside the class.*/
	public String getWord() {
		return word;
	}
}
