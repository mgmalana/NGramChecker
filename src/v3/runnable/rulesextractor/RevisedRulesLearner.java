package v3.runnable.rulesextractor;

import java.sql.SQLException;

public class RevisedRulesLearner {
	static RulesGeneralizationService rgService = new RulesGeneralizationService();

	public static void main(String[] args) throws SQLException {

		for (int i = 2; i <= 7; i++) {
			System.out.println("Generalizing " + i + "-gram");
			rgService.generalize(i);
		}
	}

}
