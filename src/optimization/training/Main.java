package optimization.training;

import java.sql.SQLException;

import optimization.training.service.NGramPopulator;
import optimization.training.service.NGramToHybridService;

public class Main {

	public static void main(String[] args) throws SQLException, InterruptedException {

		NGramPopulator ngramPopulator = new NGramPopulator();
		ngramPopulator.populateNGrams();

		NGramToHybridService nthService = new NGramToHybridService();

		// Store N-Gram
		// Call Clustering N-grams
		// Add Hybrid - also adds pos tags of each token
		// Map word-pos-lemma
		// Add non-hybrid to Hybrids with all isHybrid = false
		// Map word pos-lemma

	}

}