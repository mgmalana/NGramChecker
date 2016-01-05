package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import revised.model.NGram;
import revised.model.Suggestion;

public class MergingService {
	CandidateNGramsService candidateNGramService;

	public MergingService() {
		candidateNGramService = CandidateNGramsService.getInstance();
	}

	public List<Suggestion> computeMerging(String[] wArr, String[] lArr, String[] pArr) throws SQLException {
		List<NGram> candidateDelNGrams = candidateNGramService.getCandidateNGrams(pArr, pArr.length - 1);

		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram n : candidateDelNGrams) {

		}

		return suggestions;
	}
}
