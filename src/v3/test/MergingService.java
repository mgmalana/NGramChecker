package v3.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import v3.model.NGram;
import v3.model.Suggestion;
import v3.model.SuggestionToken;
import v3.model.SuggestionType;

public class MergingService {
	CandidateNGramsService candidateNGramService;

	public MergingService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeMerging(String[] inputWords, String[] inputLemmas, String[] inputPOS)
			throws SQLException {

		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length - 1);

		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram rule : candidateRuleNGrams) {

			int editDistance = 0;
			int i = 0, j = 0;
			String[] rulePOS = rule.getPos();
			String[] ruleWords = rule.getWords();
			Boolean[] ruleIsPOSGeneralized = rule.getIsPOSGeneralized();

			SuggestionToken suggestionToken = null;

			while (i != inputPOS.length && j != rulePOS.length) {
				if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[j] == true && rulePOS[j].equals(inputPOS[i])) {
					i++;
					j++;
				} else if (ruleWords[j].equals(inputWords[i])) {
					i++;
					j++;
				} else if (i + 1 != inputPOS.length
						&& isEqualWhenMerged(inputWords[i], inputWords[i + 1], ruleWords[j])) {
					suggestionToken = new SuggestionToken(ruleWords[j], i, 1);
					i++;
					editDistance++;
				} else {
					i++;
					j++;
					editDistance++;
				}
			}
			if (i != inputPOS.length || j != rulePOS.length)
				editDistance++;

			if (suggestionToken != null)
				suggestions.add(new Suggestion(new SuggestionToken[] { suggestionToken }, SuggestionType.MERGING,
						editDistance));
		}
		return suggestions;
	}

	private boolean isEqualWhenMerged(String inputLeft, String inputRight, String ruleWord) {
		inputLeft = inputLeft.toLowerCase();
		inputRight = inputRight.toLowerCase();
		ruleWord = ruleWord.toLowerCase();
		if (ruleWord.contains(inputLeft) && ruleWord.contains(inputRight)) {
			StringBuilder sb = new StringBuilder();
			sb.append(inputLeft);
			sb.append(inputRight);
			if (sb.toString().equals(ruleWord))
				return true;
			else {
				sb = new StringBuilder();
				sb.append(inputLeft);
				sb.append("-");
				sb.append(inputRight);
				if (sb.toString().equals(ruleWord))
					return true;
			}
		}
		return false;

	}
}
