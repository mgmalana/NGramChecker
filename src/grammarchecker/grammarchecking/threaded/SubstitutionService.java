package grammarchecker.grammarchecking.threaded;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.EditDistanceService;
import grammarchecker.models.NGram;
import grammarchecker.models.Suggestion;
import grammarchecker.models.SuggestionToken;
import grammarchecker.models.SuggestionType;

public class SubstitutionService extends GrammarCheckingServiceThread {

	@Override
	protected void performTask() throws SQLException {
		long startTime = System.currentTimeMillis();
		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length);
//		System.out.println("Candidate N-grams Size: " + candidateRuleNGrams.size());
		long endTime = System.currentTimeMillis();
//		System.out.println("Fetching candidate n-grams: " + (endTime - startTime) + "ms");

		for (NGram rule : candidateRuleNGrams) {
			String[] rulePOS = rule.getPos();
			String[] ruleWords = rule.getWords();
			String[] ruleLemmas = rule.getLemmas();
			Boolean[] ruleIsPOSGeneralized = rule.getIsPOSGeneralized();

			// System.out.println(ArrayToStringConverter.convert(rule.getWords())
			// + " ||| "
			// + ArrayToStringConverter.convert(inputWords) + " " +
			// rule.getWords().length + " "
			// + inputWords.length);
			double editDistance = 0;
			List<SuggestionToken> replacements = new ArrayList<>();
			for (int i = 0; i < rulePOS.length; i++) {
				// REORDER SPELL CHECK BEFORE CHECKING POS RULE SEQUENCES

				if (ruleLemmas[i].equals(inputLemmas[i]) && !ruleWords[i].equals(inputWords[i])
						&& rulePOS[i].equals(inputPOS[i])) {
					editDistance += 0.6;
					if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i] == true)
						replacements.add(new SuggestionToken(ruleWords[i], i, editDistance, rulePOS[i],
								SuggestionType.SUBSTITUTION));
					else
						replacements
								.add(new SuggestionToken(ruleWords[i], i, editDistance, SuggestionType.SUBSTITUTION));
				} else if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i] == true
						&& rulePOS[i].equals(inputPOS[i]))
					;
				else if (ruleWords[i].equals(inputWords[i]))
					;
				else {
					if (ruleLemmas[i].equals(inputLemmas[i]))
						editDistance += 0.6;
					else if (withinSpellingEditDistance(ruleWords[i], inputWords[i])) {
						editDistance += 0.65;
					} else if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i]
							&& hasCloseWordFromDictionary(inputWords[i], rulePOS[i])) {
						editDistance += 0.65;
					} else if (rulePOS[i].equals(inputPOS[i]))
						editDistance += 0.8;
					else
						editDistance += 1;

					if (ruleIsPOSGeneralized != null && ruleIsPOSGeneralized[i] == true)
						replacements.add(new SuggestionToken(ruleWords[i], i, editDistance, rulePOS[i],
								SuggestionType.SUBSTITUTION));
					else
						replacements
								.add(new SuggestionToken(ruleWords[i], i, editDistance, SuggestionType.SUBSTITUTION));
				}

			}

			if (editDistance == 0) {
				outputSuggestions = new ArrayList<>();
				outputSuggestions.add(new Suggestion(0));
				break;
			} else if (editDistance <= Constants.EDIT_DISTANCE_THRESHOLD) {
				SuggestionToken[] replacementWords = replacements.toArray(new SuggestionToken[replacements.size()]);

				boolean hasSimilar = false;
				for (Suggestion s : outputSuggestions) {
					if (s.getSuggestions() != null) {
						if (s.getEditDistance() == editDistance && s.getSuggestions()[0].isPOSGeneralized() == true
								&& s.getSuggestions()[0].getPos().equals(replacementWords[0].getPos())) {
							s.incrementFrequency();
							hasSimilar = true;
						} else if (s.getEditDistance() == editDistance
								&& s.getSuggestions()[0].getWord().equals(replacementWords[0].getWord())) {
							s.incrementFrequency();
							hasSimilar = true;
						}

					}
				}
				if (hasSimilar == false) {
					outputSuggestions.add(new Suggestion(replacementWords, editDistance));
				}
			}

			/*
			 * if (editDistance < 1) {
			 * System.out.println(ArrayToStringConverter.convert(inputWords) +
			 * " + " + ArrayToStringConverter.convert(inputPOS) + "\n" +
			 * ArrayToStringConverter.convert(ruleWords) + " + " +
			 * ArrayToStringConverter.convert(rulePOS)); }
			 */
		}
	}

	private boolean hasCloseWordFromDictionary(String mispelledWord, String expectedPOS) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean withinSpellingEditDistance(String corpusWord, String input) {

		corpusWord = corpusWord.toLowerCase();
		input = input.toLowerCase();
		if (input.equals(corpusWord))
			return false;

		int distance = EditDistanceService.computeLevenshteinDistance(corpusWord, input);

		if (distance <= 1 && input.length() <= 4)
			return true;
		else if (distance <= 2 && input.length() > 4 && input.length() <= 12)
			return true;
		else if (distance <= 3 && input.length() > 12)
			return true;
		else
			return false;
	}

}
