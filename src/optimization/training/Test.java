package optimization.training;

import java.sql.SQLException;

import optimization.training.dao.WordPOSLemmaMapDao;

public class Test {

	public static void main(String[] args) throws SQLException {
		// Store N-Gram
		// Call Clustering N-grams
		// Add Hybrid - also adds pos tags of each token
		// Map word-pos-lemma
		// Add non-hybrid to Hybrids with all isHybrid = false
		// Map word pos-lemma

		WordPOSLemmaMapDao wplMapDao = new WordPOSLemmaMapDao();
		wplMapDao.addWPLMapping("Mark", "NNP", "Mark");

		// NGramToHybridDao d = DaoManager.getNGramToHybridDao(3);
		// d.addHybridNGram("", "", "NNC NNC NNP", 20);
	}

}
