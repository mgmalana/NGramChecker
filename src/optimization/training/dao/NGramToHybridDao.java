package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import v4.dao.DatabaseConnector;
import v4.models.NGram;

public class NGramToHybridDao {

	Connection conn;
	private int ngramSize;
	private String ngramTable;
	private String ngramFrequencyTable;
	private String ngramIndexTable;
	private POSDao posDao;

	public NGramToHybridDao(int ngramSize, String ngramTable, String ngramFrequencyTable, String ngramIndexTable) {
		conn = DatabaseConnector.getConnection();
		this.ngramSize = ngramSize;
		this.ngramTable = ngramTable;
		this.ngramFrequencyTable = ngramFrequencyTable;
		this.ngramIndexTable = ngramIndexTable;
		this.posDao = new POSDao();
	}

	public void addHybridNGram(String hybridNgram, String isHybrid, String posTags) throws SQLException {

		int[] posIDs = posDao.addPOSTags(posTags);

		// add to database, index the pos tag - hybrid n-gram
		// use first hybrid as root, succeeding s nodes with computed edit
		// distance
	}

	public List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset) throws SQLException {
		String query = "SELECT F.id, words, lemmas, pos FROM " + ngramTable + " F INNER JOIN " + "(SELECT id, pos FROM "
				+ ngramFrequencyTable + " WHERE frequency >= ? LIMIT 1 OFFSET ?) B " + "ON F.posID = B.id";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, frequencyAtLeast);
		ps.setInt(2, offset);
		ResultSet rs = ps.executeQuery();

		List<NGram> ngrams = new ArrayList<>();
		while (rs.next()) {
			int id = rs.getInt(1);
			String[] words = rs.getString(2).split(" ");
			String[] lemmas = rs.getString(3).split(" ");
			String[] pos = rs.getString(4).split(" ");
			ngrams.add(new NGram(id, ngramSize, words, lemmas, pos));
		}
		if (ngrams.size() > 0)
			return ngrams;
		else
			return null;
	}
}
