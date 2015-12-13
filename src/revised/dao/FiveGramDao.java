package revised.dao;

import java.sql.SQLException;
import java.util.List;

import revised.dao.abstractClass.NGramDao;
import revised.model.NGram;

public class FiveGramDao extends NGramDao {

	public FiveGramDao() {
		super();
	}

	@Override
	public int add(String words, String lemmas, String pos) throws SQLException {

		String query = "INSERT INTO fivegram (words, lemmas, posID) VALUES (?, ?, ?)";
		return add(query, words, lemmas, pos);
	}

	@Override
	protected int incrementPOSFrequency(String pos) throws SQLException {
		String updateQuery = "UPDATE fivegram_pos_frequency SET frequency = frequency + 1 WHERE id = ?";
		String insertQuery = "INSERT INTO fivegram_pos_frequency (pos) VALUES (?)";
		return incrementPOSFrequency(pos, updateQuery, insertQuery);
	}

	@Override
	protected int getID(String pos) throws SQLException {
		String query = "SELECT id FROM fivegram_pos_frequency WHERE pos = ?";
		return getID(pos, query);
	}

	@Override
	public List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset) throws SQLException {
		String query = "SELECT F.* FROM fivegram F INNER JOIN "
				+ "(SELECT id FROM fivegram_pos_frequency WHERE frequency >= ? LIMIT 1 OFFSET ?) B "
				+ "ON F.posID = B.id";
		int ngramSize = 5;
		return getSimilarNGrams(frequencyAtLeast, offset, query, ngramSize);
	}
}
