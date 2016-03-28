package v4.grammarchecking.threaded;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ArrayToStringConverter;
import v3.model.NGram;
import v3.model.Suggestion;
import v3.model.SuggestionToken;
import v3.model.SuggestionType;
import v3.test.EditDistanceService;

public class SubstitutionService extends GrammarCheckingServiceThread {

	@Override
	protected void performTask() throws SQLException {
		long startTime = System.currentTimeMillis();
		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length);
		System.out.println("Candidate N-grams Size: " + candidateRuleNGrams.size());
		long endTime = System.currentTimeMillis();
		System.out.println("Fetching candidate n-grams: " + (endTime - startTime) + "ms");

		for (NGram rule : candidateRuleNGrams) {
			String[] rulePOS = rule.getPos();
			String[] ruleWords = rule.getWords();
			String[] ruleLemmas = rule.getLemmas();
			Boolean[] ruleIsPOSGeneralized = rule.getIsPOSGeneralized();
			System.out.println(ArrayToStringConverter.convert(rule.getWords()) + " ||| "
					+ ArrayToStringConverter.convert(inputWords) + " " + rule.getWords().length + " "
					+ inputWords.length);
			double editDistance = 0;
			List<SuggestionToken> replacements = new ArrayList<>();
			for (int i = 0; i < rulePOS.length; i++) {

				if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i] == true && rulePOS[i].equals(inputPOS[i]))
					;
				else if (ruleWords[i].equals(inputWords[i]))
					;
				else {
					if (ruleLemmas[i].equals(inputLemmas[i]))
						editDistance += 0.4;
					else if (withinSpellingEditDistance(ruleWords[i], inputWords[i]))
						editDistance += 0.45;
					else if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i]
							&& hasCloseWordFromDictionary(inputWords[i], rulePOS[i])) {
						editDistance += 0.5;
					} else if (rulePOS[i].equals(inputPOS[i]))
						editDistance += 0.8;
					else
						editDistance += 1;

					if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i] == true)
						replacements.add(new SuggestionToken(ruleWords[i], i, editDistance, rulePOS[i]));
					else
						replacements.add(new SuggestionToken(ruleWords[i], i, editDistance));
				}
			}
			SuggestionToken[] replacementWords = replacements.toArray(new SuggestionToken[replacements.size()]);
			outputSuggestions.add(new Suggestion(replacementWords, SuggestionType.SUBSTITUTION, editDistance));

			if (editDistance == 0) {
				outputSuggestions.add(new Suggestion(0));
			}
		}
	}

	private boolean hasCloseWordFromDictionary(String mispelledWord, String expectedPOS) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean withinSpellingEditDistance(String corpusWord, String input) {
		int distance = EditDistanceService.computeLevenshteinDistance(corpusWord, input);
		if (distance <= 1 && input.length() <= 4)
			return true;
		else if (distance <= 2 && input.length() <= 12)
			return true;
		else if (distance <= 3 && input.length() > 12)
			return true;
		else
			return false;
	}

}
