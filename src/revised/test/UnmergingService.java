package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import revised.model.NGram;
import revised.model.Suggestion;
import revised.model.SuggestionToken;

public class UnmergingService {
	CandidateNGramsService candidateNGramService;

	public UnmergingService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeUnmerging(String[] wArr, String[] lArr, String[] pArr) throws SQLException {
		List<NGram> candidateInsNGrams = candidateNGramService.getCandidateNGrams(pArr, pArr.length + 1);

		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram n : candidateInsNGrams) {
			int editDistance = 0;
			String[] nPOS = n.getPos();
			String[] nWords = n.getWords();
			Boolean[] isPOSGeneralized = n.getIsPOSGeneralized();

			SuggestionToken suggestionToken = null;

			int i = 0;
			int j = 0;
			int notEqualOnWArrIndex = -1;
			boolean isLeftSideEqual = true;
			boolean isRightSideEqual = true;
			while (isLeftSideEqual && i < wArr.length) {
				if (isPOSGeneralized != null && isPOSGeneralized[i] == true && nPOS[i].equals(pArr[i]))
					;
				else if (nWords[i].equals(wArr[i]))
					;
				else {
					isLeftSideEqual = false;
					notEqualOnWArrIndex = i;
				}
				i++;
				j++;
			}

		}
		return suggestions;
	}

}
