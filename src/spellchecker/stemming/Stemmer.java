package spellchecker.stemming;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import spellchecker.utility.Configuration;

/**
 * 
 * The spellchecker.stemming system of the spell checker.
 * 
 **/
public class Stemmer {

	File file = null;
	// FOR CODE-SWITCHING PART
	private Collection<String> prefixListValues = null;

	private CustomizedRule customRule = new CustomizedRule();

	private LinkedHashMap<String, String> prefixList = null;
	private LinkedHashMap<String, String> suffixList = null;
	private LinkedHashMap<String, String> infixList = null;
	private PartialReduplication partialRedup = null;

	public Stemmer(Prefixes prefixList, Suffixes suffixList) {
		this.prefixList = prefixList.getPrefixes();
		this.suffixList = suffixList.getSuffixes();
		this.prefixListValues = this.prefixList.values();
	}

	public Stemmer(Prefixes prefixList, Suffixes suffixList, Infixes infixList, PartialReduplication partialRedup) {
		this(prefixList, suffixList);
		this.infixList = infixList.getInfixes();
		this.partialRedup = partialRedup;
	}

	public LinkedHashSet<String> stemWordList(Set<String> wordList) {
		LinkedHashSet<String> stemmedList = new LinkedHashSet<>();
		// int counter = 0;
		for (String inflected : wordList) {
			// counter++;
			String stemmed = stemming(inflected);
			stemmedList.add(stemmed);
			/*
			 * if (counter < 1000) System.out.println(inflected + " --- " +
			 * stemmed);
			 */
		}
		return stemmedList;
	}

	/**
	 * @param input
	 *            - word in the text.
	 * 
	 * @return stem of the word.
	 */
	public String stemming(String input) {

		// Stripping of affixes using the customized rules.
		customRule.testCustomeRules(input);
		input = customRule.getWord();

		if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
			return input;

		// Stripping of suffixes
		for (String pattern : suffixList.keySet()) {

			if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
				return input;

			if (input.matches((pattern))) {
				String temp = input;
				String suffixValue = suffixList.get(pattern);
				String[] findReplace = null;

				if (suffixValue.contains("_")) {
					findReplace = new String[2];
					findReplace = suffixValue.split("_");
				} else {
					findReplace = new String[1];
					findReplace[0] = suffixValue;
				}
				switch (findReplace.length) {
				case 2:
					// input = input.replaceFirst(findReplace[0],
					// findReplace[1]);
					input = replaceLast(input, findReplace[0], findReplace[1]);
					break;
				case 1:
					// input = input.replaceFirst(findReplace[0], "");
					input = replaceLast(input, findReplace[0], "");
					break;
				}

				// Return 'yung OLD STRING kapag AFTER STEMMING ay naging less
				// than 4 ito or FIRST TWO LETTERS are CONSONANT
				if (input.length() <= Configuration.MINIMUM_WORD_LENGTH
						|| ((input.matches("[^aeiou][^aeiou].*")) && input.length() <= 5)) {
					input = temp;
				}
			}
		}

		// Stripping of prefixes
		for (String pattern : prefixList.keySet()) {

			if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
				return input;

			if (input.matches((pattern))) {
				String temp = input;
				input = input.replaceFirst(prefixList.get(pattern), "");

				// Return 'yung OLD STRING kapag AFTER STEMMING ay naging less
				// than 4 ito or FIRST TWO LETTERS are CONSONANT
				if (input.length() <= Configuration.MINIMUM_WORD_LENGTH
						|| ((input.matches("[^aeiou][^aeiou].*")) && input.length() <= 5)) {
					input = temp;
				}
			}
		}

		// Configuration.LIGHT_STEMMER == FALSE
		if (!Configuration.LIGHT_STEMMER) {

			// Stripping of infixes.
			for (String pattern : infixList.keySet()) {
				if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
					return input;

				if (input.matches((pattern))) {
					String temp1 = input;
					String infixValue = infixList.get(pattern);
					input = input.replaceFirst(pattern, infixValue);

					// Return 'yung OLD STRING kapag AFTER STEMMING ay naging
					// less
					// than 4 ito or FIRST TWO LETTERS are CONSONANT
					if (input.length() <= Configuration.MINIMUM_WORD_LENGTH
							|| ((input.matches("[^aeiou][^aeiou].*")) && input.length() <= 5)) {
						input = temp1;
					}
				}
			}

			// Stripping of partial reduplication.
			if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
				return input;
			partialRedup.testPartialRedup(input);
			input = partialRedup.getPartialRedup();
		}

		// changing the 'u' to 'o'.
		String clean = customRule.changeU2O(input);
		if (!clean.equals("")) {
			return clean;
		}
		return input;
	}

	/**
	 * @param prefix
	 *            - substring of a word.
	 * @return true if the substring is a prefix.
	 */
	public boolean isPrefix(String prefix) {

		for (String prefixWord : prefixListValues)
			if (prefix.equalsIgnoreCase(prefixWord))
				return true;

		return false;
	}

	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
	}
}
