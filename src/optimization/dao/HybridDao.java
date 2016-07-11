package optimization.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import optimization.models.HybridNGram;
import util.ArrayToStringConverter;
import v4.dao.DatabaseConnector;

public class HybridDao {

	Connection conn;
	private int ngramSize;
	private String hybridNGramTable;
	private POSDao posDao;

	public HybridDao(int ngramSize, String hybridNGramTable) {
		this.ngramSize = ngramSize;
		this.hybridNGramTable = hybridNGramTable;
		this.posDao = new POSDao();
	}

	public void addHybridNGram(String[] posTags, Boolean[] isHybrid, String[] nonHybridWords, int frequency)
			throws SQLException {
		conn = DatabaseConnector.getConnection();
		String posTagsAsString = ArrayToStringConverter.convert(posTags);
		String isHybridAsString = ArrayToStringConverter.convert(isHybrid);
		String nonHybridWordsAsString = ArrayToStringConverter.convert(nonHybridWords);

		Integer[] posIDs = posDao.addPOSTags(posTags);

		String insertQuery = "INSERT INTO " + hybridNGramTable
				+ " (posTags, posIDs, nonhybrid_words, isHybrid, frequency) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE isHybrid = ?, posIDs = ?, nonhybrid_words = ?, frequency = ?";
		String selectQuery = "SELECT id FROM " + hybridNGramTable + " WHERE posTags = '" + posTagsAsString + "'";
		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.setString(1, posTagsAsString);
		ps.setString(2, ArrayToStringConverter.convert(posIDs));
		ps.setString(3, nonHybridWordsAsString);
		ps.setString(4, isHybridAsString);
		ps.setInt(5, frequency);
		ps.setString(6, isHybridAsString);
		ps.setString(7, ArrayToStringConverter.convert(posIDs));
		ps.setString(8, nonHybridWordsAsString);
		ps.setInt(9, frequency);
		ps.executeUpdate();
		ps = conn.prepareStatement(selectQuery);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int hybridNGramID = rs.getInt(1);
		conn.close();
		HybridNGramPosIndexerDao hybridPosIndexer = DaoManager.getHybridNGramPOSIndexerDao(ngramSize);
		hybridPosIndexer.index(hybridNGramID, posIDs);

	}

	public List<HybridNGram> getCandidateHybridNGrams(List<Integer> candidateHybridIDs) throws SQLException {
		conn = DatabaseConnector.getConnection();
		List<HybridNGram> hybridNGrams = new ArrayList<>();
		StringBuilder s = new StringBuilder("SELECT posTags, posIDs, isHybrid, nonhybrid_words, frequency FROM "
				+ hybridNGramTable + " WHERE id IN (");
		for (int i = 0; i < candidateHybridIDs.size(); i++) {
			if (candidateHybridIDs.get(i) != null) {
				s.append(candidateHybridIDs.get(i));
				if (i < candidateHybridIDs.size() - 1)
					s.append(",");
			}
		}
		s.append(")");
		PreparedStatement ps = conn.prepareStatement(s.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String[] posIDsString = rs.getString(2).split(" ");
			Integer[] posIDs = new Integer[posIDsString.length];
			for (int i = 0; i < posIDsString.length; i++)
				posIDs[i] = Integer.parseInt(posIDsString[i].trim());
			hybridNGrams.add(new HybridNGram(rs.getString(1), posIDs, rs.getString(3), rs.getString(4), rs.getInt(5)));
		}
		conn.close();
		return hybridNGrams;
	}

	public List<HybridNGram> getCandidateHybridNGramsPermutation(List<String> posPatterns) throws SQLException {
		conn = DatabaseConnector.getConnection();
		List<HybridNGram> hybridNGrams = new ArrayList<>();
		StringBuilder s = new StringBuilder(
				"SELECT posTags, posIDs, isHybrid, nonhybrid_words, frequency FROM " + hybridNGramTable + " WHERE ");
		for (int i = 0; i < posPatterns.size(); i++) {
			s.append("posTags LIKE '" + posPatterns.get(i) + "'");
			if (i < posPatterns.size() - 1)
				s.append(" OR ");
		}
		// System.out.println(s.toString());
		PreparedStatement ps = conn.prepareStatement(s.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String[] posIDsString = rs.getString(2).split(" ");
			Integer[] posIDs = new Integer[posIDsString.length];
			for (int i = 0; i < posIDsString.length; i++)
				posIDs[i] = Integer.parseInt(posIDsString[i].trim());
			hybridNGrams.add(new HybridNGram(rs.getString(1), posIDs, rs.getString(3), rs.getString(4), rs.getInt(5)));
		}
		conn.close();
		return hybridNGrams;
	}

}
