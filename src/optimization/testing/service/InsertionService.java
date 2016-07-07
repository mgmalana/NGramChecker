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

	public static List<Suggestion> performTask(Input input, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService
				.getCandidateNGramsInsertionPermutation(input.getPos(), ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();

		int highestBaseFreq = 0;
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeInsertionEditDistance(input, h);

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

	private static Suggestion computeInsertionEditDistance(Input input, HybridNGram h) throws SQLException {
		for (int i = 0; i < input.getNgramSize(); i++) {
			boolean isEqualPOS = h.getPosTags()[i].equalsIgnoreCase(input.getPos()[i]);
			if (!isEqualPOS) {
				List<String> wordsGivenPOS = wplmDao.getWordsGivenPosID(h.getPosIDs()[i]);
				String[] tokenSuggestions = wordsGivenPOS.toArray(new String[wordsGivenPOS.size()]);
				double editDistance = Constants.EDIT_DISTANCE_MISSING_WORD;
				return new Suggestion(SuggestionType.INSERTION, tokenSuggestions, true, h.getPosTags()[i], i,
						editDistance, h.getBaseNGramFrequency());
			}
		}
		return null;
	}
}
