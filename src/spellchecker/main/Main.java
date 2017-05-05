package spellchecker.main;

import java.util.HashMap;
import java.util.LinkedHashSet;

import spellchecker.spellCheck.SpellChecker;

public class Main {

	public static void main(String[] args) {

		SpellChecker spellCheck = new SpellChecker();

		String sentence = "Tayo ay mg-texts laro Magelan ay magagandangbabae na magagnda text subtexts.";
		HashMap<String, LinkedHashSet<String>> x = spellCheck.checkSentence(sentence);

		for (String word : x.keySet()) {
			if (x.get(word).size() == 0) {
				System.out.println(word.split("_")[1] + " --- true");
			} else {
				System.out.println(word.split("_")[1] + " ---  suggestions:" + formatSuggestion(x.get(word)));
			}
		}
	}

	private static String formatSuggestion(LinkedHashSet<String> candidateSuggestions) {

		if (candidateSuggestions.size() == 0)
			return "";

		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (String suggestion : candidateSuggestions)
			builder.append(suggestion + ", ");

		builder.append("]");
		return builder.toString();
	}

}
