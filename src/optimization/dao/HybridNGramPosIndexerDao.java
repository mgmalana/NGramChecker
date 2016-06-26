package optimization.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import v4.dao.DatabaseConnector;

public class HybridNGramPosIndexerDao {

	Connection conn;
	int ngramSize;
	String hybridPosIndexerTable;

	public HybridNGramPosIndexerDao(int ngramSize, String hybridPosIndexerTable) {
		this.ngramSize = ngramSize;
		this.hybridPosIndexerTable = hybridPosIndexerTable;
	}

	public void index(int hybridNGramID, Integer[] posIDs) throws SQLException {
		conn = DatabaseConnector.getConnection();
		StringBuilder s = new StringBuilder(
				"INSERT IGNORE INTO " + hybridPosIndexerTable + " (hybrid_id, pos_id) VALUES ");
		for (int i = 0; i < posIDs.length; i++) {
			if (posIDs[i] != null) {
				s.append("(" + hybridNGramID + ", " + posIDs[i] + "),");
			}
		}
		PreparedStatement ps = conn.prepareStatement(s.toString().substring(0, s.toString().length() - 1));
		// System.out.println(s.toString().substring(0, s.toString().length() -
		// 1));
		ps.executeUpdate();
		conn.close();
	}

	public Map<Integer, Integer> getHybridNgramIdsWithCandidateCount(Integer[] posIDs) throws SQLException {
		conn = DatabaseConnector.getConnection();
		Map<Integer, Integer> hIdsToCountMap = new HashMap<>();
		String template = "SELECT hybrid_id, COUNT(*) FROM hybrid_pos_index_bigram WHERE pos_id IN (2,3) GROUP BY hybrid_id";
		StringBuilder s = new StringBuilder(
				"SELECT hybrid_id, COUNT(*) FROM " + hybridPosIndexerTable + " WHERE pos_id IN (");
		for (int i = 0; i < posIDs.length; i++) {
			if (posIDs[i] != null) {
				s.append(posIDs[i]);
				if (i < posIDs.length - 1)
					s.append(",");
			}
		}
		s.append(") GROUP BY hybrid_id");

		PreparedStatement ps = conn.prepareStatement(s.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			hIdsToCountMap.put(rs.getInt(1), rs.getInt(2));
		}
		conn.close();
		return hIdsToCountMap;
	}
}
