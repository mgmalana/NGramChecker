package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import revised.dao.DaoManager;
import revised.dao.NGramDao;
import revised.dao.POS_NGram_Indexer;
import revised.model.NGram;

public class CandidateNGramsService {

	private static CandidateNGramsService candidateNGramService;

	private CandidateNGramsService() {
	}

	public static CandidateNGramsService getInstance() {
		if (candidateNGramService == null)
			candidateNGramService = new CandidateNGramsService();
		return candidateNGramService;
	}

	public List<NGram> getCandidateNGrams(String[] pos, int nGramSizeOfCandidates) throws SQLException {
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
				candidateNGrams.add(ngramDao.get(id));
			}
		}

		return candidateNGrams;
	}

	private String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);
	}
}
