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

public class DeletionService {
	static WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	public static List<Suggestion> performTask(Input input, int indexOffset, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService.getCandidateNGramsDeletionPermutation(input.getPos(),
				ngramSize);
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		int highestBaseFreq = 0;
		for (HybridNGram h : candidatesHGrams) {
			Suggestion s = computeDeletionEditDistance(input, indexOffset, h);

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

	private static Suggestion computeDeletionEditDistance(Input input, int indexOffset, HybridNGram h)
			throws SQLException {
		// System.out.println("Input Words: " +
		// ArrayToStringConverter.convert(input.getWords()));
		// System.out.println("Input POS: " +
		// ArrayToStringConverter.convert(input.getPos()));
		// System.out.println("Input Lemmas: " +
		// ArrayToStringConverter.convert(input.getLemmas()));
		// System.out.println("Hybrid POS Tag: " +
		// ArrayToStringConverter.convert(h.getPosTags()));
		// System.out.println("IsHybrid: " +
		// ArrayToStringConverter.convert(h.getIsHybrid()));

		for (int i = 0; i < h.getPosTags().length; i++) {
			boolean isEqualPOS = h.getPosTags()[i].equalsIgnoreCase(input.getPos()[i]);
			// System.out.println(isEqualPOS);
			if (!isEqualPOS) {
				List<String> wordsGivenPOS = wplmDao.getWordsGivenPosID(h.getPosIDs()[i]);
				String[] tokenSuggestions = wordsGivenPOS.toArray(new String[wordsGivenPOS.size()]);
				double editDistance = Constants.EDIT_DISTANCE_UNNECESSARY_WORD;
				return new Suggestion(SuggestionType.DELETION, null, true, input.getPos()[i], indexOffset + i,
						editDistance, h.getBaseNGramFrequency());
			}
		}
		return null;
	}
}
