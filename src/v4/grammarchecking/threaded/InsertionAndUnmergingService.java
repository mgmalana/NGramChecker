package v4.grammarchecking.threaded;

import java.sql.SQLException;
import java.util.List;

import util.Constants;
import v4.models.NGram;
import v4.models.Suggestion;
import v4.models.SuggestionToken;
import v4.models.SuggestionType;

public class InsertionAndUnmergingService extends GrammarCheckingServiceThread {

	@Override
	protected void performTask() throws SQLException {
		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length + 1);

		for (NGram rule : candidateRuleNGrams) {

			double editDistance = 0;
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
				} else if (ruleIsPOSGeneralized != null && j + 1 != rulePOS.length
						&& ruleIsPOSGeneralized[j + 1] == true && rulePOS[j + 1].equals(inputPOS[i])) {
					suggestionToken = new SuggestionToken(ruleWords[j], i, 1, rulePOS[j], SuggestionType.INSERTION);
					i++;
					j += 2;
					editDistance++;
				} else if (j + 1 != ruleWords.length && ruleWords[j + 1].equals(inputWords[i])) {
					suggestionToken = new SuggestionToken(ruleWords[j], i, 1, rulePOS[j], SuggestionType.INSERTION);
					i++;
					j += 2;
					editDistance++;
				} else if (j + 1 != ruleWords.length
						&& isEqualToUnmerge(inputWords[i], ruleWords[j], ruleWords[j + 1])) {
					StringBuilder sb = new StringBuilder();
					sb.append(ruleWords[j]).append(" ").append(ruleWords[j + 1]);
					suggestionToken = new SuggestionToken(sb.toString(), i, 0.7, SuggestionType.UNMERGING);
					i++;
					j += 2;
					editDistance += 0.7;
				} else {
					i++;
					j++;
					editDistance++;
				}
			}
			if (i != inputPOS.length || j != rulePOS.length)
				editDistance++;

			if (suggestionToken != null && editDistance <= Constants.EDIT_DISTANCE_THRESHOLD) {
				boolean hasSimilar = false;
				for (Suggestion s : outputSuggestions) {
					if (s.getSuggestions() != null) {
						if (s.getEditDistance() == editDistance
								&& s.getSuggestions()[0].getSuggType().equals(suggestionToken.getSuggType())
								&& s.getSuggestions()[0].isPOSGeneralized() == true
								&& s.getSuggestions()[0].getPos().equals(suggestionToken.getPos())) {
							s.incrementFrequency();
							hasSimilar = true;
						} else if (s.getEditDistance() == editDistance
								&& s.getSuggestions()[0].getSuggType().equals(suggestionToken.getSuggType())
								&& s.getSuggestions()[0].getWord().equals(suggestionToken.getWord())) {
							s.incrementFrequency();
							hasSimilar = true;
						}

					}
				}
				if (hasSimilar == false)
					outputSuggestions.add(new Suggestion(new SuggestionToken[] { suggestionToken }, editDistance));
			}

		}

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
