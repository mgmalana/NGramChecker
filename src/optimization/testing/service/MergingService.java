package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import optimization.dao.WordPOSLemmaMapDao;
import optimization.models.HybridNGram;
import optimization.models.Input;
import optimization.models.Suggestion;
import util.Constants;
import v4.models.SuggestionType;

public class MergingService {
	WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	Set<Integer> hasMergeSuggestionAlready;

	Input input;
	int indexOffset;
	int ngramSize;

	public MergingService(Input input, int indexOffset, int ngramSize) {
		this.input = input;
		this.ngramSize = ngramSize;
		this.hasMergeSuggestionAlready = new LinkedHashSet<>();
	}

	public List<Suggestion> performTask() throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService.getCandidateNGramsMergingPermutation(input.getPos(),
				ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeMergingEditDistance(h);
			if (s != null)
				suggestions.add(s);
		}
		return suggestions;
	}

	private Suggestion computeMergingEditDistance(HybridNGram h) throws SQLException {
		// System.out.println(ArrayToStringConverter.convert(h.getPosTags()));
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
		Suggestion sugg = getMergingSuggestion(h, mergingIndex);
		if (sugg != null)
			return sugg;
		else if (mergingIndex - 1 >= 0 && input.getPos()[mergingIndex].equals(input.getPos()[mergingIndex - 1])) {
			sugg = getMergingSuggestion(h, mergingIndex - 1);
			if (sugg != null)
				return sugg;
		} else if (mergingIndex + 2 < input.getPos().length
				&& input.getPos()[mergingIndex + 2].equals(h.getPosTags()[mergingIndex])) {
			sugg = getMergingSuggestion(h, mergingIndex + 1);
			if (sugg != null)
				return sugg;
		}
		if (mergingIndex - 1 >= 0) {
			return getMergingSuggestion(h, mergingIndex - 1);
		}
		return sugg;

	}

	private Suggestion getMergingSuggestion(HybridNGram h, int mergingIndex) throws SQLException {
		if (hasMergeSuggestionAlready.contains(mergingIndex))
			return null;

		String concatNoSpace = input.getWords()[mergingIndex].toLowerCase()
				+ input.getWords()[mergingIndex + 1].toLowerCase();

		String concatWithHyphen = input.getWords()[mergingIndex].toLowerCase() + "-"
				+ input.getWords()[mergingIndex + 1].toLowerCase();
		String equalWordMapping = wplmDao.getEqualWordMapping(concatNoSpace, concatWithHyphen,
				h.getPosIDs()[mergingIndex]);
		if (equalWordMapping != null) {
			String[] tokenSuggestions = { equalWordMapping };

			hasMergeSuggestionAlready.add(mergingIndex);
			return new Suggestion(SuggestionType.MERGING, tokenSuggestions, h.getIsHybrid()[mergingIndex],
					h.getPosTags()[mergingIndex], indexOffset + mergingIndex, mergingIndex,
					Constants.EDIT_DISTANCE_INCORRECTLY_UNMERGED, h.getBaseNGramFrequency());
		}

		return null;
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
