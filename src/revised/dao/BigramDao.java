package revised.dao;

import java.sql.SQLException;
import java.util.List;

import revised.dao.abstractClass.NGramDao;
import revised.model.NGram;

public class BigramDao extends NGramDao {

	public BigramDao() {
		super();
	}

	@Override
	public int add(String words, String lemmas, String pos) throws SQLException {

		String query = "INSERT INTO bigram (words, lemmas, posID) VALUES (?, ?, ?)";
		return add(query, words, lemmas, pos);
	}

	@Override
	protected int incrementPOSFrequency(String pos) throws SQLException {
		String updateQuery = "UPDATE bigram_pos_frequency SET frequency = frequency + 1 WHERE id = ?";
		String insertQuery = "INSERT INTO bigram_pos_frequency (pos) VALUES (?)";
		return incrementPOSFrequency(pos, updateQuery, insertQuery);
	}

	@Override
	protected int getID(String pos) throws SQLException {
		String query = "SELECT id FROM bigram_pos_frequency WHERE pos = ?";
		return getID(pos, query);
	}

	@Override
	public List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset) throws SQLException {
		String query = "SELECT F.* FROM bigram F INNER JOIN "
				+ "(SELECT id FROM bigram_pos_frequency WHERE frequency >= ? LIMIT 1 OFFSET ?) B "
				+ "ON F.posID = B.id";
		int ngramSize = 5;
		return getSimilarNGrams(frequencyAtLeast, offset, query, ngramSize);
	}

}
