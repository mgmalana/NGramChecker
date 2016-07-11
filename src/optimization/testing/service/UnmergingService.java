package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import optimization.dao.WordPOSLemmaMapDao;
import optimization.models.HybridNGram;
import optimization.models.Input;
import optimization.models.Suggestion;
import util.Constants;
import v4.models.SuggestionType;

public class UnmergingService {
	static WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	public static List<Suggestion> performTask(Input input, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService
				.getCandidateNGramsUnmergingPermutation(input.getPos(), ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeUnmergingEditDistance(input, h);
			if (s != null)
				suggestions.add(s);
		}
		return suggestions;
	}

	private static Suggestion computeUnmergingEditDistance(Input input, HybridNGram h) throws SQLException {
		int unmergingIndex = 0;
		int midSize = input.getNgramSize() / 2;
		if (input.getNgramSize() % 2 != 0)
			midSize = input.getNgramSize() / 2 + 1;
		for (int i = 0; i < midSize; i++) {
			if (!input.getPos()[i].equals(h.getPosTags()[i])) {
				unmergingIndex = i;
				break;
			} else if (!input.getPos()[input.getNgramSize() - 1 - i]
					.equals(h.getPosTags()[h.getPosTags().length - 1 - i])) {
				unmergingIndex = input.getNgramSize() - 1 - i;
				break;
			}
		}
		List<String> wordsGivenPOSLeft = wplmDao.getWordsGivenPosID(h.getPosIDs()[unmergingIndex]);
		List<String> wordsGivenPOSRight = wplmDao.getWordsGivenPosID(h.getPosIDs()[unmergingIndex + 1]);
		for (String wordGivenPOSLeft : wordsGivenPOSLeft) {
			for (String wordGivenPOSRight : wordsGivenPOSRight) {
				if (isEqualToUnmerge(input.getWords()[unmergingIndex], wordGivenPOSLeft, wordGivenPOSRight)) {
					String[] tokenSuggestions = { wordGivenPOSLeft, wordGivenPOSRight };
					return new Suggestion(SuggestionType.UNMERGING, tokenSuggestions, h.getIsHybrid()[unmergingIndex],
							h.getPosTags()[unmergingIndex], unmergingIndex,
							Constants.EDIT_DISTANCE_INCORRECTLY_UNMERGED, h.getBaseNGramFrequency());
				}
			}

		}
		return null;
	}

	private static boolean isEqualToUnmerge(String inputWord, String ruleLeft, String ruleRight) {

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
