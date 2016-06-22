package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimization.models.HybridNGram;
import optimization.training.dao.DaoManager;
import optimization.training.dao.HybridNGramPosIndexerDao;
import optimization.training.dao.NGramToHybridDao;
import optimization.training.dao.POSDao;

public class CandidateNGramService {
	POSDao posDao = new POSDao();
	HybridNGramPosIndexerDao hybridPosIndexerDao;
	NGramToHybridDao hybridDao;

	public static void main(String[] args) throws SQLException {
		CandidateNGramService dao = new CandidateNGramService();
		String[] posTags = { "VBW", "RBW", "NNC", "CCP" };
		dao.getCandidateNGrams(posTags, 4);

	}

	public void getCandidateNGrams(String[] posTags, int ngramSize) throws SQLException {
		long startTime = System.currentTimeMillis();
		hybridPosIndexerDao = DaoManager.getHybridNGramPOSIndexerDao(ngramSize);
		hybridDao = DaoManager.getNGramToHybridDao(ngramSize);
		Integer[] ids = posDao.getPosIDs(posTags);
		// Integer[] ids = posDao.getPosIDs(posTags);
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}

		String[] uniquePOS = getUniquePOS(posTags);

		List<Integer> candidateHybridIDs = new ArrayList<>();
		Map<Integer, Integer> hIdWithCandidateCount = hybridPosIndexerDao.getHybridNgramIdsWithCandidateCount(ids);
		Iterator<Map.Entry<Integer, Integer>> iter = hIdWithCandidateCount.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			if (entry.getValue() >= uniquePOS.length - 2) {
				int id = entry.getKey();
				candidateHybridIDs.add(id);
			}
		}

		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGrams(candidateHybridIDs);

		long endTime = System.currentTimeMillis();

		System.out.println("New Candidate Ngram Fetch Speed: " + (endTime - startTime) + " | Number of Candidates: "
				+ hybridNGrams.size());
	}

	// get posIDs of the input pos tags from the pos table
	// get hybrid n-gram ids of the specified size that is indexed to these
	// posIDs
	// get all hybrid n-grams of the specified size "WHERE id IN (2,3,4,5,6)
	private String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);
	}
}
