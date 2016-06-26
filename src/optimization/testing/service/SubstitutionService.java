package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import optimization.dao.WordPOSLemmaMapDao;
import optimization.models.HybridNGram;
import optimization.models.Input;
import optimization.models.Suggestion;
import util.ArrayToStringConverter;

public class SubstitutionService {

	static WordPOSLemmaMapDao wplmDao = new WordPOSLemmaMapDao();

	public static List<Suggestion> performTask(Input input, int ngramSize) throws SQLException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService.getCandidateNGrams(input.getPos(), ngramSize);
		// check if may ka-equal na ito. If meron, stop and return null;
		List<Suggestion> suggestions = new ArrayList<>();

		boolean isGrammaticallyCorrect = isGrammaticallyCorrect(input, candidatesHGrams, ngramSize);
		if (isGrammaticallyCorrect)
			return null;
		else {
			System.out.println(candidatesHGrams.size());
			for (HybridNGram h : candidatesHGrams) {
				Suggestion s = computeEditDistance(input, h, ngramSize);
			}
			return suggestions;
		}
	}

	private static Suggestion computeEditDistance(Input input, HybridNGram h, int ngramSize) throws SQLException {
		for (int i = 0; i < ngramSize; i++) {
			boolean isEqualPOS = h.getPosTags()[i].equals(input.getPos()[i]);
			if (isEqualPOS && h.getIsHybrid()[i] == true)
				;
			else if (isEqualPOS && h.getIsHybrid()[i] == false) {
				;// System.out.println("Same POS Wrong Word");// same pos wrong
					// word
			} else {
				String wordWithLemmaAndPOS = wplmDao.getWordWithLemmaAndPOS(input.getLemmas()[i], h.getPosIDs()[i]);
				if (wordWithLemmaAndPOS != null && input.getWords()[i].equals(wordWithLemmaAndPOS)) {
					// System.out.println("Wrong Word Form");
					// wrong word form
				} else if (true) {
					; // misspelling
				} else {
					; // wrong word different pos
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean isGrammaticallyCorrect(Input input, List<HybridNGram> candidatesHGrams, int ngramSize) {
		for (HybridNGram h : candidatesHGrams) {
			if (Arrays.equals(h.getPosTags(), input.getPos())) {
				System.out.println(ArrayToStringConverter.convert(input.getWords()));
				System.out.println(ArrayToStringConverter.convert(h.getIsHybrid()));
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
}
