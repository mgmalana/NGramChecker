package v3.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import v3.model.NGram;
import v3.model.Suggestion;
import v3.model.SuggestionToken;
import v3.model.SuggestionType;

public class DeletionService {
	CandidateNGramsService candidateNGramService;

	public DeletionService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeDeletion(String[] inputWords, String[] inputLemmas, String[] inputPOS) throws SQLException {

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
				} else if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[j] == true && i + 1 != inputPOS.length
						&& rulePOS[j].equals(inputPOS[i + 1])) {
					suggestionToken = new SuggestionToken(inputWords[i], i, 1, inputPOS[i]);
					i++;
					editDistance++;
				} else if (i + 1 != inputPOS.length && ruleWords[j].equals(inputWords[i + 1])) {
					suggestionToken = new SuggestionToken(inputWords[i], i, 1);
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

			suggestions.add(
					new Suggestion(new SuggestionToken[] { suggestionToken }, SuggestionType.DELETION, editDistance));
		}
		return suggestions;
	}
}
