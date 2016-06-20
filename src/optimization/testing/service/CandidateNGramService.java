package optimization.testing.service;

import java.sql.SQLException;
import java.util.Map;

import optimization.training.dao.DaoManager;
import optimization.training.dao.HybridNGramPosIndexerDao;
import optimization.training.dao.POSDao;

public class CandidateNGramService {
	POSDao posDao = new POSDao();
	HybridNGramPosIndexerDao hybridPosIndexerDao;

	public static void main(String[] args) throws SQLException {
		CandidateNGramService dao = new CandidateNGramService();
		dao.getCandidateNGrams(null, 2);
	}

	public void getCandidateNGrams(String[] posTags, int ngramSize) throws SQLException {
		hybridPosIndexerDao = DaoManager.getHybridNGramPOSIndexerDao(ngramSize);
		String[] temp = { "NNC", "NNP", "DTC" };
		Integer[] ids = posDao.getPosIDs(temp);
		// Integer[] ids = posDao.getPosIDs(posTags);
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}

		Map<Integer, Integer> hIdWithCandidateCount = hybridPosIndexerDao.getHybridNgramIdsWithCandidateCount(ids);
	}
	// get posIDs of the input pos tags from the pos table
	// get hybrid n-gram ids of the specified size that is indexed to these
	// posIDs
	// get all hybrid n-grams of the specified size "WHERE id IN (2,3,4,5,6)

}
