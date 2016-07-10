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

public class MergingService {
	static WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	public static List<Suggestion> performTask(Input input, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService.getCandidateNGramsMergingPermutation(input.getPos(),
				ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		int highestBaseFreq = 0;
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeMergingEditDistance(input, h);
			if (s != null)
				suggestions.add(s);
		}
		return suggestions;
	}

	private static Suggestion computeMergingEditDistance(Input input, HybridNGram h) throws SQLException {
		int mergingIndex = 0;

		int midSize = input.getNgramSize() / 2;
		if (input.getNgramSize() % 2 != 0)
			midSize = input.getNgramSize() / 2 + 1;
		for (int i = 0; i < midSize; i++) {
			if (!input.getPos()[i].equals(h.getPosTags()[i])) {
				mergingIndex = i;
				break;
			} else if (!input.getPos()[input.getNgramSize() - 1 - i]
					.equals(h.getPosTags()[h.getPosTags().length - 1 - i])) {
				mergingIndex = input.getNgramSize() - 2 - i;
				break;
			}
		}

		List<String> wordsGivenPOS = wplmDao.getWordsGivenPosID(h.getPosIDs()[mergingIndex]);
		for (String wordGivenPOS : wordsGivenPOS) {
			if (isEqualWhenMerged(input.getWords()[mergingIndex], input.getWords()[mergingIndex + 1], wordGivenPOS)) {
				String[] tokenSuggestions = { wordGivenPOS };
				return new Suggestion(SuggestionType.MERGING, tokenSuggestions, h.getIsHybrid()[mergingIndex],
						h.getPosTags()[mergingIndex], mergingIndex, Constants.EDIT_DISTANCE_INCORRECTLY_UNMERGED,
						h.getBaseNGramFrequency());
			}
		}
		return null;
	}

	private static boolean isEqualWhenMerged(String inputLeft, String inputRight, String ruleWord) {

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
