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
		for (int i = 0; i < input.getNgramSize(); i++) {
			if (!input.getPos()[i].equals(h.getPosTags()[i])
					&& ((i + 2 < input.getNgramSize() && input.getPos()[i + 2].equals(h.getPosTags()[i + 1]))
							|| i + 2 >= input.getNgramSize())) {
				mergingIndex = i;
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
