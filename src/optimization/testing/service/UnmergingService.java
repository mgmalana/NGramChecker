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

	public static List<Suggestion> performTask(Input input, int indexOffset, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService
				.getCandidateNGramsUnmergingPermutation(input.getPos(), ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		System.out.println("Candidate N-gram Count: " + candidatesHGrams.size());
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeUnmergingEditDistance(input, indexOffset, h);
			if (s != null)
				suggestions.add(s);
		}
		return suggestions;
	}

	private static Suggestion computeUnmergingEditDistance(Input input, int indexOffset, HybridNGram h)
			throws SQLException {

		// System.out.println(ArrayToStringConverter.convert(h.getPosTags()));
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
		String wordToUnmerge = input.getWords()[unmergingIndex];

		if (wordToUnmerge.length() < 2)
			return null;
		// List<String[]> unmergedWords =
		// wplmDao.tryUnmerge(h.getPosIDs()[unmergingIndex],
		// h.getPosIDs()[unmergingIndex + 1], wordToUnmerge);
		// if (unmergedWords != null)
		// for (String[] s : unmergedWords)
		// System.out.println(s[0] + " " + s[1]);
		List<String> wordsGivenPOSLeft = wplmDao.getWordsGivenPosIDGivenPrefix(h.getPosIDs()[unmergingIndex],
				wordToUnmerge.substring(0, 2), wordToUnmerge.length() - 1);
		List<String> wordsGivenPOSRight = wplmDao.getWordsGivenPosIDGivenSuffix(h.getPosIDs()[unmergingIndex + 1],
				wordToUnmerge.substring(wordToUnmerge.length() - 2, wordToUnmerge.length()),
				wordToUnmerge.length() - 1);
		for (String wordGivenPOSLeft : wordsGivenPOSLeft) {
			for (String wordGivenPOSRight : wordsGivenPOSRight) {
				// System.out.println(wordGivenPOSLeft + " " + wordGivenPOSRight
				// + " " + wordToUnmerge);
				if (isEqualToUnmerge(input.getWords()[unmergingIndex], wordGivenPOSLeft, wordGivenPOSRight)) {
					String[] tokenSuggestions = { wordGivenPOSLeft, wordGivenPOSRight };
					return new Suggestion(SuggestionType.UNMERGING, tokenSuggestions, h.getIsHybrid()[unmergingIndex],
							h.getPosTags()[unmergingIndex], indexOffset + unmergingIndex,
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
