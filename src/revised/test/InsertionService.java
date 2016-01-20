package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import revised.model.NGram;
import revised.model.Suggestion;
import revised.model.SuggestionToken;
import revised.model.SuggestionType;

public class InsertionService {
	CandidateNGramsService candidateNGramService;

	public InsertionService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeInsertion(String[] inputWords, String[] inputLemmas, String[] inputPOS) throws SQLException {
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
				} else if (ruleIsPOSGeneralized != null && j + 1 != rulePOS.length && ruleIsPOSGeneralized[j + 1] == true
						&& rulePOS[j + 1].equals(inputPOS[i])) {
					suggestionToken = new SuggestionToken(ruleWords[j], i, 1, rulePOS[j]);
					i++;
					j += 2;
					editDistance++;
				} else if (j + 1 != ruleWords.length && ruleWords[j + 1].equals(inputWords[i])) {
					suggestionToken = new SuggestionToken(ruleWords[j], i, 1, rulePOS[j]);
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
				suggestions.add(new Suggestion(new SuggestionToken[] { suggestionToken }, SuggestionType.INSERTION,
						editDistance));
		}
		return suggestions;
	}
}
