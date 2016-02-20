package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import revised.model.NGram;
import revised.model.Suggestion;
import revised.model.SuggestionToken;
import revised.model.SuggestionType;

public class SubstitutionService {

	CandidateNGramsService candidateNGramService;

	public SubstitutionService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeSubstitution(String[] inputWords, String[] inputLemmas, String[] inputPOS)
			throws SQLException {
		List<NGram> candidateRuleNGrams = candidateNGramService.getCandidateNGrams(inputPOS, inputPOS.length);
		System.out.println("Candidate N-grams Size: " + candidateRuleNGrams.size());
		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram rule : candidateRuleNGrams) {
			String[] rulePOS = rule.getPos();
			String[] ruleWords = rule.getWords();
			String[] ruleLemmas = rule.getLemmas();
			Boolean[] ruleIsPOSGeneralized = rule.getIsPOSGeneralized();

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
			suggestions.add(new Suggestion(replacementWords, SuggestionType.SUBSTITUTION, editDistance));

			if (editDistance == 0) {
				suggestions.add(new Suggestion(0));
				return suggestions;
			}
		}
		return suggestions;

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