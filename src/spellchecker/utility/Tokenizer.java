package spellchecker.utility;

public class Tokenizer {

	String[] delimiters = { ".", ",", "!" };

	public String[] tokenize(String input) {

		for (String mark : delimiters)
			input = input.replace(mark, " " + mark + " *#");

		return input.split(" ");
	}

	public boolean isDelimeters(String word) {

		for (String delimiter : delimiters) {
			if (delimiter.equals(word))
				return true;
		}
		return false;
	}

	public boolean isProperNoun(String word) {
		String smallAlpha = "^[a-z]+$";

		// Capital letter
		if (word.charAt(0) != word.toLowerCase().charAt(0))
			return true;

		for (int counter = 0; counter < word.length(); counter++)
			if (!Character.isAlphabetic(word.charAt(counter)) && (word.charAt(counter) != '-'))
				return true;

		return false;
	}

}
