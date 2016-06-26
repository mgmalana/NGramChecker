package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimization.dao.DaoManager;
import optimization.dao.HybridDao;
import optimization.dao.HybridNGramPosIndexerDao;
import optimization.dao.POSDao;
import optimization.models.HybridNGram;

public class CandidateNGramService {
	static POSDao posDao = new POSDao();
	static HybridNGramPosIndexerDao hybridPosIndexerDao;
	static HybridDao hybridDao;

	// public static void main(String[] args) throws SQLException {
	// CandidateNGramService service = new CandidateNGramService();
	// String[] posTags = { "VBW", "RBW", "NNC", "CCP" };
	// service.getCandidateNGrams(posTags, 4);
	//
	// OldCandidateNGramsService oldService =
	// OldCandidateNGramsService.getInstance();
	// oldService.getCandidateNGrams(posTags, 4);
	//
	// }

	public static List<HybridNGram> getCandidateNGrams(String[] posTags, int ngramSize) throws SQLException {
		long startTime = System.currentTimeMillis();
		hybridPosIndexerDao = DaoManager.getHybridNGramPOSIndexerDao(ngramSize);
		hybridDao = DaoManager.getNGramToHybridDao(ngramSize);
		Integer[] ids = posDao.getPosIDs(posTags);
		// Integer[] ids = posDao.getPosIDs(posTags);

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

		// for (HybridNGram h : hybridNGrams) {
		// System.out.println(ArrayToStringConverter.convert(h.getPosTags()) + "
		// | "
		// + ArrayToStringConverter.convert(h.getPosIDs()) + " | "
		// + ArrayToStringConverter.convert(h.getIsHybrid()) + " | " +
		// h.getBaseNGramFrequency());
		// }

		long endTime = System.currentTimeMillis();

		System.out.println("New Candidate Ngram Fetch Speed: " + (endTime - startTime) + " | Number of Candidates: "
				+ hybridNGrams.size());

		return hybridNGrams;
	}

	// get posIDs of the input pos tags from the pos table
	// get hybrid n-gram ids of the specified size that is indexed to these
	// posIDs
	// get all hybrid n-grams of the specified size "WHERE id IN (2,3,4,5,6)
	private static String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);
	}
}
