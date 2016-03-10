package v3.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import v3.model.NGram;
import v3.model.Suggestion;
import v3.model.SuggestionToken;
import v3.model.SuggestionType;

public class UnmergingService {
	CandidateNGramsService candidateNGramService;

	public UnmergingService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeUnmerging(String[] inputWords, String[] inputLemmas, String[] inputPOS)
			throws SQLException {
		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length + 1);

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
				} else if (j + 1 != ruleWords.length
						&& isEqualToUnmerge(inputWords[i], ruleWords[j], ruleWords[j + 1])) {
					StringBuilder sb = new StringBuilder();
					sb.append(ruleWords[j]).append(" ").append(ruleWords[j + 1]);
					suggestionToken = new SuggestionToken(sb.toString(), i, 1);
					i++;
					j += 2;
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
				suggestions.add(new Suggestion(new SuggestionToken[] { suggestionToken }, SuggestionType.UNMERGING,
						editDistance));
		}
		return suggestions;
	}

	private boolean isEqualToUnmerge(String inputWord, String ruleLeft, String ruleRight) {
		ruleLeft = ruleLeft.toLowerCase();
		ruleRight = ruleRight.toLowerCase();
		inputWord = inputWord.toLowerCase();
		if (inputWord.contains(ruleLeft) && inputWord.contains(ruleRight)) {
			StringBuilder sb = new StringBuilder();
			sb.append(ruleLeft);
			sb.append(ruleRight);
			if (sb.toString().equals(inputWord))
				return true;
			else {
				sb = new StringBuilder();
				sb.append(ruleLeft);
				sb.append("-");
				sb.append(ruleRight);
				if (sb.toString().equals(inputWord))
					return true;
			}
		}
		return false;

	}

}
