package revised.runnable.rulesextractor;

import java.sql.SQLException;

import revised.dao.BigramDao;
import revised.dao.FiveGramDao;

public class RevisedRulesLearner {
	static FiveGramDao fivegramDao = new FiveGramDao();
	static BigramDao bigramDao = new BigramDao();
	static RulesGeneralizationService rgService = new RulesGeneralizationService();

	public static void main(String[] args) throws SQLException {

		getSimilarFiveGrams();
		System.out.println("--------------------------------\n");
		// getSimilarBigrams();
	}

	private static void getSimilarBigrams() throws SQLException {
		int ngramSize = 2;
		rgService.generalize(ngramSize, bigramDao);
	}

	private static void getSimilarFiveGrams() throws SQLException {
		int ngramSize = 5;
		rgService.generalize(ngramSize, fivegramDao);
	}
}
