package revised.rulesviewer;

import java.sql.SQLException;
import java.util.List;

import revised.dao.DaoManager;
import revised.dao.NGramDao;
import revised.model.NGram;
import revised.util.ArrayToStringConverter;

public class RulesViewer {

	public static void main(String[] args) throws SQLException {
		for (int i = 5; i <= 7; i++) {
			NGramDao ngramDao = DaoManager.getNGramDao(i);

			List<NGram> generalizedNGrams = ngramDao.getGeneralizedNGrams();

			for (NGram n : generalizedNGrams) {
				System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}
		}
	}
}
