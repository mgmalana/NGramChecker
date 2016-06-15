package optimization.training;

import java.sql.SQLException;

import optimization.training.dao.DaoManager;
import optimization.training.dao.NGramToHybridDao;

public class Test {

	public static void main(String[] args) throws SQLException {
		NGramToHybridDao d = DaoManager.getNGramToHybridDao(3);
		d.addHybridNGram("", "", "PRS PRP CCS");
	}

}
