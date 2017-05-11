package v4.grammarchecking.threaded;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import v4.dao.DaoManager;
import v4.dao.NGramDao;
import v4.dao.POS_NGram_Indexer;
import v4.models.NGram;

public class OldCandidateNGramsService {

	private static OldCandidateNGramsService candidateNGramService;

	private OldCandidateNGramsService() {
	}

	public static OldCandidateNGramsService getInstance() {
		if (candidateNGramService == null)
			candidateNGramService = new OldCandidateNGramsService();
		return candidateNGramService;
	}

	public List<NGram> getCandidateNGrams(String[] pos, int nGramSizeOfCandidates) throws SQLException {

		long startTime = System.currentTimeMillis();

		NGramDao ngramDao = DaoManager.getNGramDao(nGramSizeOfCandidates);
		POS_NGram_Indexer indexer = DaoManager.getIndexer(nGramSizeOfCandidates);
		String[] uniquePOS = getUniquePOS(pos);
		HashMap<Integer, Integer> instancesFrequency = new HashMap<>();
		for (String p : uniquePOS) {
			Integer[] instances = indexer.getInstances(p);
			for (int i : instances) {
				if (instancesFrequency.get(i) == null)
					instancesFrequency.put(i, 0);
				instancesFrequency.put(i, instancesFrequency.get(i) + 1);
			}
		}

		List<NGram> candidateNGrams = new ArrayList<>();
		Iterator<Map.Entry<Integer, Integer>> iter = instancesFrequency.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			if (entry.getValue() >= uniquePOS.length - 2) {
				int id = entry.getKey();
				NGram n = ngramDao.get(id);
				if (n != null)
					candidateNGrams.add(n);
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Old Candidate Ngram Fetch Speed: " + (endTime - startTime) + " | Number of Candidates "
				+ candidateNGrams.size());

		return candidateNGrams;
	}

	private String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);
	}
}
