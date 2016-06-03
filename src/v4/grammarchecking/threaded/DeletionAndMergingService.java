package v4.grammarchecking.threaded;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Constants;
import v4.models.NGram;
import v4.models.Suggestion;
import v4.models.SuggestionToken;
import v4.models.SuggestionType;

public class DeletionAndMergingService extends GrammarCheckingServiceThread {

	@Override
	protected void performTask() throws SQLException {
		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length - 1);

		for (NGram rule : candidateRuleNGrams) {

			double editDistance = 0;
			int i = 0, j = 0;
			String[] rulePOS = rule.getPos();
			String[] ruleWords = rule.getWords();
			Boolean[] ruleIsPOSGeneralized = rule.getIsPOSGeneralized();

			List<SuggestionToken> suggestionTokensDel = new ArrayList<>();
			List<SuggestionToken> suggestionTokensMer = new ArrayList<>();

			// System.out.println("Input sa Deletion: " +
			// ArrayToStringConverter.convert(inputWords));

			while (i != inputPOS.length && j != rulePOS.length) {
				if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[j] == true && rulePOS[j].equals(inputPOS[i])) {
					i++;
					j++;
				} else if (ruleWords[j].equals(inputWords[i])) {
					i++;
					j++;
				} else if (i + 1 != inputPOS.length
						&& isEqualWhenMerged(inputWords[i], inputWords[i + 1], ruleWords[j])) {
					// System.out.println("Equal::: " + inputWords[i] + " " +
					// inputWords[i + 1]);
					suggestionTokensMer.add(new SuggestionToken(ruleWords[j], i, 0.7, SuggestionType.MERGING));
					i++;
					editDistance += 0.7;
				} else if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[j] == true && i + 1 != inputPOS.length
						&& rulePOS[j].equals(inputPOS[i + 1])) {
					suggestionTokensDel
							.add(new SuggestionToken(inputWords[i], i, 1, inputPOS[i], SuggestionType.DELETION));
					i++;
					editDistance++;
				} else if (i + 1 != inputPOS.length && ruleWords[j].equals(inputWords[i + 1])) {
					suggestionTokensDel.add(new SuggestionToken(inputWords[i], i, 1, SuggestionType.DELETION));
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
			if (suggestionTokensDel.size() >= 1 && editDistance <= Constants.EDIT_DISTANCE_THRESHOLD)
				outputSuggestions.add(new Suggestion(
						suggestionTokensDel.toArray(new SuggestionToken[suggestionTokensDel.size()]), editDistance));
			if (suggestionTokensMer.size() >= 1) {
				outputSuggestions.add(new Suggestion(
						suggestionTokensMer.toArray(new SuggestionToken[suggestionTokensMer.size()]), editDistance));
			}
		}
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
