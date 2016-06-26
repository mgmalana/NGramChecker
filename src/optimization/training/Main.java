package optimization.training;

import java.sql.SQLException;

import optimization.training.service.NGramPopulator;
import optimization.training.service.NGramToHybridService;
import optimization.training.service.WordPOSLemmaMapService;

public class Main {

	public static void main(String[] args) throws SQLException, InterruptedException {

		NGramPopulator ngramPopulator = new NGramPopulator();
		// ngramPopulator.populateNGrams();

		WordPOSLemmaMapService wplMapService = new WordPOSLemmaMapService();
		// wplMapService.populate();

		NGramToHybridService nthService = new NGramToHybridService();
		for (int i = 2; i <= 7; i++)
			nthService.hybridizeRules(i);

		// Store N-Gram
		// Call Clustering N-grams
		// Add Hybrid - also adds pos tags of each token
		// Map word-pos-lemma
		// Add non-hybrid to Hybrids with all isHybrid = false
		// Map word pos-lemma

	}

}
