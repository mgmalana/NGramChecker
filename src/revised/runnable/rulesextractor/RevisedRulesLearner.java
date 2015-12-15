package revised.runnable.rulesextractor;

import java.sql.SQLException;

import revised.dao.DaoManager;

public class RevisedRulesLearner {
	static RulesGeneralizationService rgService = new RulesGeneralizationService();

	public static void main(String[] args) throws SQLException {

		// getSimilarFiveGrams();
		System.out.println("--------------------------------\n");
		getSimilarBigrams();
	}

	private static void getSimilarBigrams() throws SQLException {
		int ngramSize = 2;
		rgService.generalize(ngramSize, DaoManager.getNGramDao(ngramSize));
	}

	private static void getSimilarFiveGrams() throws SQLException {
		int ngramSize = 5;
		rgService.generalize(ngramSize, DaoManager.getNGramDao(ngramSize));
	}
}
