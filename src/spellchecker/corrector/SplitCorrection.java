package spellchecker.corrector;

import java.util.LinkedHashSet;

import com.BoxOfC.MDAG.MDAG;

public class SplitCorrection {

	private MDAG filiDict;
	private LinkedHashSet<String> suggestionList;

	public SplitCorrection(MDAG filiDict) {
		this.filiDict = filiDict;
	}

	public LinkedHashSet<String> splitSuggestion(String misspelled) {

		suggestionList = new LinkedHashSet<String>();

		for (int counter = 2; counter < misspelled.length() - 1; counter++) {
			String first = misspelled.substring(0, counter);
			String second = misspelled.substring(counter);

			if (filiDict.contains(first) && filiDict.contains(second))
				suggestionList.add(first + " " + second);
		}
		return suggestionList;
	}
}
