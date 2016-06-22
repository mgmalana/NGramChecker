package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import optimization.models.HybridNGram;
import util.ArrayToStringConverter;
import v4.dao.DatabaseConnector;

public class NGramToHybridDao {

	Connection conn;
	private int ngramSize;
	private String hybridNGramTable;
	private POSDao posDao;

	public NGramToHybridDao(int ngramSize, String hybridNGramTable) {
		this.ngramSize = ngramSize;
		this.hybridNGramTable = hybridNGramTable;
		this.posDao = new POSDao();
	}

	public void addHybridNGram(String[] posTags, Boolean[] isHybrid, int frequency) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String posTagsAsString = ArrayToStringConverter.convert(posTags);
		String isHybridAsString = ArrayToStringConverter.convert(isHybrid);

		Integer[] posIDs = posDao.addPOSTags(posTags);

		String insertQuery = "INSERT INTO " + hybridNGramTable
				+ " (posTags, isHybrid, frequency) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE isHybrid = ?";
		String selectQuery = "SELECT id FROM " + hybridNGramTable + " WHERE posTags = '" + posTagsAsString + "'";
		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.setString(1, posTagsAsString);
		ps.setString(2, isHybridAsString);
		ps.setInt(3, frequency);
		ps.setString(4, isHybridAsString);
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
		StringBuilder s = new StringBuilder(
				"SELECT posTags, isHybrid, frequency FROM " + hybridNGramTable + " WHERE id IN (");
		for (int i = 0; i < candidateHybridIDs.size(); i++) {
			if (candidateHybridIDs.get(i) != null) {
				s.append(candidateHybridIDs.get(i));
				if (i < candidateHybridIDs.size() - 1)
					s.append(",");
			}
		}
		s.append(")");
		System.out.println(s.toString());

		PreparedStatement ps = conn.prepareStatement(s.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hybridNGrams.add(new HybridNGram(rs.getString(1), rs.getString(2), rs.getInt(3)));
		}
		conn.close();
		return hybridNGrams;
	}
}
