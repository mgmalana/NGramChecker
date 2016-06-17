package optimization.training;

import java.sql.SQLException;

import optimization.training.dao.DaoManager;
import optimization.training.dao.NGramToHybridDao;

public class Test {

	public static void main(String[] args) throws SQLException {
		// Store N-Gram
		// Call Clustering N-grams
		// Add Hybrid
		// Map word-pos-lemma
		NGramToHybridDao d = DaoManager.getNGramToHybridDao(3);
		d.addHybridNGram("", "", "NNC NNC NNP", 20);
	}

}
