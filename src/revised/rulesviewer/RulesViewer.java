package revised.rulesviewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import revised.dao.DaoManager;
import revised.dao.NGramDao;
import revised.model.NGram;
import revised.util.ArrayToStringConverter;

public class RulesViewer {

	public static void main(String[] args) throws SQLException {
		for (int i = 5; i <= 7; i++) {
			NGramDao ngramDao = DaoManager.getNGramDao(i);

			HashMap<Integer, NGram> generalizedNGrams = ngramDao.getGeneralizedNGrams();

			Iterator it = generalizedNGrams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				NGram n = (NGram) pair.getValue();
				System.out.println(getRule(n) + " ||| " + ArrayToStringConverter.convert(n.getWords()) + " | "
						+ ArrayToStringConverter.convert(n.getIsPOSGeneralized()));
			}
		}
	}

	private static String getRule(NGram n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n.getWords().length; i++) {
			if (n.getIsPOSGeneralized()[i] == true)
				s.append(n.getPos()[i]);
			else
				s.append(n.getWords()[i]);
			s.append(" ");
		}
		return s.toString();
	}
}
