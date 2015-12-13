package revised.runnable.rulesextractor;

import java.sql.SQLException;
import java.util.List;

import revised.dao.BigramDao;
import revised.dao.FiveGramDao;
import revised.model.NGram;
import revised.util.ArrayToStringConverter;

public class RevisedRulesLearner {
	static FiveGramDao fivegramDao = new FiveGramDao();
	static BigramDao bigramDao = new BigramDao();

	public static void main(String[] args) throws SQLException {

		getSimilarFiveGrams();
		System.out.println("--------------------------------\n");
		getSimilarBigrams();
	}

	private static void getSimilarBigrams() throws SQLException {
		int offset = 0;
		List<NGram> ngrams = bigramDao.getSimilarNGrams(2, offset);
		while (ngrams != null) {

			for (NGram n : ngrams) {
				System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}
			System.out.println("-----------------");

			offset++;
			ngrams = bigramDao.getSimilarNGrams(2, offset);
		}
	}

	private static void getSimilarFiveGrams() throws SQLException {
		int offset = 0;
		List<NGram> ngrams = fivegramDao.getSimilarNGrams(2, offset);
		while (ngrams != null) {

			for (NGram n : ngrams) {
				System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}
			System.out.println("-----------------");

			offset++;
			ngrams = fivegramDao.getSimilarNGrams(2, offset);
		}

	}
}
