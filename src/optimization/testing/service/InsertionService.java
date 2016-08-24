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

public class InsertionService {
	static WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	public static List<Suggestion> performTask(Input input, int indexOffset, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService
				.getCandidateNGramsInsertionPermutation(input.getPos(), ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		int highestBaseFreq = 0;
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeInsertionEditDistance(input, indexOffset, h);

			if (s != null) {
				// if (s.getFrequency() > highestBaseFreq) {
				// suggestions = new ArrayList<>();
				// highestBaseFreq = s.getFrequency();
				// }
				// if (s.getFrequency() >= highestBaseFreq)
				suggestions.add(s);
			}

		}
		return suggestions;
	}

	private static Suggestion computeInsertionEditDistance(Input input, int indexOffset, HybridNGram h)
			throws SQLException {
		int insertionIndex = 0;
		int midSize = input.getNgramSize() / 2;
		if (input.getNgramSize() % 2 != 0)
			midSize = input.getNgramSize() / 2 + 1;
		for (int i = 0; i < midSize; i++) {
			if (!input.getPos()[i].equals(h.getPosTags()[i])) {
				insertionIndex = i;
				break;
			} else if (!input.getPos()[input.getNgramSize() - 1 - i]
					.equals(h.getPosTags()[h.getPosTags().length - 1 - i])) {
				insertionIndex = h.getPosTags().length - 1 - i;
				break;
			}
		}
		// System.out.println(ArrayToStringConverter.convert(input.getPos()) + "
		// | "
		// + ArrayToStringConverter.convert(h.getPosTags()) + " | " +
		// insertionIndex);

		for (int i = 0; i < input.getNgramSize(); i++) {
			boolean isEqualPOS = h.getPosTags()[i].equalsIgnoreCase(input.getPos()[i]);
			if (!isEqualPOS) {
				List<String> wordsGivenPOS = wplmDao.getWordsGivenPosID(h.getPosIDs()[i]);
				String[] tokenSuggestions = wordsGivenPOS.toArray(new String[wordsGivenPOS.size()]);
				double editDistance = Constants.EDIT_DISTANCE_MISSING_WORD;
				return new Suggestion(SuggestionType.INSERTION, tokenSuggestions, true, h.getPosTags()[i],
						indexOffset + i, editDistance, h.getBaseNGramFrequency());
			}
		}
		return null;
	}
}
