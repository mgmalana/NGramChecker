package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import revised.model.NGram;
import revised.model.Suggestion;
import revised.model.SuggestionToken;
import revised.model.SuggestionType;

public class DeletionService {
	CandidateNGramsService candidateNGramService;

	public DeletionService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeDeletion(String[] wArr, String[] lArr, String[] pArr) throws SQLException {

		List<NGram> candidateDelNGrams = candidateNGramService.getCandidateNGrams(pArr, pArr.length - 1);

		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram n : candidateDelNGrams) {

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
				} else if (isPOSGeneralized != null && isPOSGeneralized[j] == true && i + 1 != pArr.length
						&& nPOS[j].equals(pArr[i + 1])) {
					suggestionToken = new SuggestionToken(wArr[i], i, 1, pArr[i]);
					i++;
					editDistance++;
				} else if (i + 1 != pArr.length && nWords[j].equals(wArr[i + 1])) {
					suggestionToken = new SuggestionToken(wArr[i], i, 1);
					i++;
					editDistance++;
				} else {
					i++;
					j++;
					editDistance++;
				}
			}
			if (i != pArr.length || j != nPOS.length)
				editDistance++;

			suggestions.add(
					new Suggestion(new SuggestionToken[] { suggestionToken }, SuggestionType.DELETION, editDistance));
		}
		return suggestions;
	}
}
