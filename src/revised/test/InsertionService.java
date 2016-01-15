package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import revised.model.NGram;
import revised.model.Suggestion;
import revised.model.SuggestionToken;
import revised.model.SuggestionType;

public class InsertionService {
	CandidateNGramsService candidateNGramService;

	public InsertionService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeInsertion(String[] wArr, String[] lArr, String[] pArr) throws SQLException {
		List<NGram> candidateInsNGrams = candidateNGramService.getCandidateNGrams(pArr, pArr.length + 1);

		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram n : candidateInsNGrams) {

			int editDistance = 0;
			int i = 0, j = 0;
			String[] nPOS = n.getPos();
			String[] nWords = n.getWords();
			Boolean[] isPOSGeneralized = n.getIsPOSGeneralized();

			SuggestionToken suggestionToken = null;

			while (i != pArr.length && j != nPOS.length) {
				if (isPOSGeneralized != null && isPOSGeneralized[j] == true && nPOS[j].equals(pArr[i])) {
					i++;
					j++;
				} else if (nWords[j].equals(wArr[i])) {
					i++;
					j++;
				} else if (isPOSGeneralized != null && j + 1 != nPOS.length && isPOSGeneralized[j + 1] == true
						&& nPOS[j + 1].equals(pArr[i])) {
					suggestionToken = new SuggestionToken(nWords[j], i, 1, nPOS[j]);
					i++;
					j += 2;
					editDistance++;
				} else if (j + 1 != nWords.length && nWords[j + 1].equals(wArr[i])) {
					suggestionToken = new SuggestionToken(nWords[j], i, 1, nPOS[j]);
					i++;
					j += 2;
					editDistance++;
				} else {
					i++;
					j++;
					editDistance++;
				}
			}
			if (i != pArr.length || j != nPOS.length)
				editDistance++;
			if (suggestionToken != null)
				suggestions.add(new Suggestion(new SuggestionToken[] { suggestionToken }, SuggestionType.INSERTION,
						editDistance));
		}
		return suggestions;
	}
}
