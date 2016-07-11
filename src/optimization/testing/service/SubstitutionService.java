package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import optimization.dao.WordPOSLemmaMapDao;
import optimization.models.HybridNGram;
import optimization.models.Input;
import optimization.models.Suggestion;
import util.Constants;
import util.EditDistanceService;
import v4.models.SuggestionType;

public class SubstitutionService {

	static WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	public static List<Suggestion> performTask(Input input, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService
				.getCandidateNGramsSubstitutionPermutation(input.getPos(), ngramSize);
		// check if may ka-equal na ito. If meron, stop and return null;
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		boolean isGrammaticallyCorrect = isGrammaticallyCorrect(input, candidatesHGrams, ngramSize);
		if (isGrammaticallyCorrect)
			return null;
		else {
			double min = Integer.MAX_VALUE;
			for (HybridNGram h : candidatesHGrams) {
				Suggestion s = computeSubstitutionEditDistance(input, h);
				if (s != null) {
					// if (s.getEditDistance() < min) {
					// suggestions = new ArrayList<>();
					// min = s.getEditDistance();
					// }
					// if (s.getEditDistance() <= min)
					suggestions.add(s);
				}

			}
			return suggestions;
		}
	}

	private static Suggestion computeSubstitutionEditDistance(Input input, HybridNGram h) throws SQLException {
		double editDistance = 0;
		Suggestion suggestion = null;
		for (int i = 0; i < input.getNgramSize(); i++) {
			if (editDistance > Constants.EDIT_DISTANCE_THRESHOLD) {
				return null;
			}
			boolean isEqualPOS = h.getPosTags()[i].equalsIgnoreCase(input.getPos()[i]);
			if (isEqualPOS && h.getIsHybrid()[i] == true)
				;
			else if (isEqualPOS && h.getIsHybrid()[i] == false) {
				if (input.getWords()[i].equals(h.getNonHybridWords()[i]))
					;
				else {
					editDistance += Constants.EDIT_DISTANCE_WRONG_WORD_SAME_POS;
					String[] tokenSuggestions = { h.getNonHybridWords()[i] };
					suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
							h.getPosTags()[i], i, editDistance, h.getBaseNGramFrequency());
				}
			} else {
				List<String> wordsWithSameLemmaAndPOS = wplmDao.getWordMappingWithLemmaAndPOS(input.getLemmas()[i],
						h.getPosIDs()[i]);
				if (h.getIsHybrid()[i] == true && wordsWithSameLemmaAndPOS.size() > 0) {
					editDistance += Constants.EDIT_DISTANCE_WRONG_WORD_FORM;
					String[] tokenSuggestions = wordsWithSameLemmaAndPOS
							.toArray(new String[wordsWithSameLemmaAndPOS.size()]);
					suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
							h.getPosTags()[i], i, editDistance, h.getBaseNGramFrequency());

				} else {
					List<String> wordsGivenPOS = wplmDao.getWordsGivenPosID(h.getPosIDs()[i]);
					List<String> similarWords = getSimilarWords(input.getWords()[i], wordsGivenPOS);
					if (similarWords.size() > 0) {
						editDistance += Constants.EDIT_DISTANCE_SPELLING_ERROR;
						String[] tokenSuggestions = similarWords.toArray(new String[similarWords.size()]);
						suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
								h.getPosTags()[i], i, editDistance, h.getBaseNGramFrequency());
					} else {
						editDistance += Constants.EDIT_DISTANCE_WRONG_WORD_DIFFERENT_POS;
						String[] tokenSuggestions = wordsGivenPOS.toArray(new String[wordsGivenPOS.size()]);
						suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
								h.getPosTags()[i], i, editDistance, h.getBaseNGramFrequency());
					}
				}
			}
		}

		if (editDistance < Constants.EDIT_DISTANCE_THRESHOLD)
			return suggestion;

		return null;

	}

	private static List<String> getSimilarWords(String input, List<String> wordsWithSamePOS) {
		List<String> suggestions = new ArrayList<>();
		for (String s : wordsWithSamePOS) {
			if (withinSpellingEditDistance(s, input)) {
				suggestions.add(s);
			}
		}
		return suggestions;
	}

	private static boolean isGrammaticallyCorrect(Input input, List<HybridNGram> candidatesHGrams, int ngramSize) {
		for (HybridNGram h : candidatesHGrams) {
			if (Arrays.equals(h.getPosTags(), input.getPos())) {
				// System.out.println(ArrayToStringConverter.convert(input.getWords()));
				// System.out.println(ArrayToStringConverter.convert(h.getIsHybrid()));
				boolean isEqual = true;
				for (int i = 0; i < ngramSize; i++) {
					if (h.getIsHybrid()[i] == false && !input.getWords()[i].equals(h.getNonHybridWords()[i])) {
						isEqual = false;
						break;
					}
				}
				if (isEqual == true) {
					return true;
				} else {
					break;
				}
			}
		}
		return false;
	}

	private static boolean withinSpellingEditDistance(String corpusWord, String input) {

		corpusWord = corpusWord.toLowerCase();
		input = input.toLowerCase();
		if (input.equals(corpusWord))
			return false;

		int distance = EditDistanceService.computeLevenshteinDistance(corpusWord, input);

		if (input.length() <= 3)
			return false;
		else if (distance <= 1 && input.length() <= 4)
			return true;
		else if (distance <= 2 && input.length() > 4 && input.length() <= 12)
			return true;
		else if (distance <= 3 && input.length() > 12)
			return true;
		else
			return false;
	}
}
