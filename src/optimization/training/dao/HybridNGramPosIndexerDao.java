package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import v4.dao.DatabaseConnector;

public class HybridNGramPosIndexerDao {

	Connection conn;
	int ngramSize;
	String hybridPosIndexerTable;

	public HybridNGramPosIndexerDao(int ngramSize, String hybridPosIndexerTable) {
		conn = DatabaseConnector.getConnection();
		this.ngramSize = ngramSize;
		this.hybridPosIndexerTable = hybridPosIndexerTable;
	}

	public void index(int hybridNGramID, Integer[] posIDs) throws SQLException {
		StringBuilder s = new StringBuilder(
				"INSERT IGNORE INTO " + hybridPosIndexerTable + " (hybrid_id, pos_id) VALUES ");
		for (int i = 0; i < posIDs.length; i++) {
			if (posIDs[i] != null) {
				s.append("(" + hybridNGramID + ", " + posIDs[i] + "),");
			}
		}
		PreparedStatement ps = conn.prepareStatement(s.toString().substring(0, s.toString().length() - 1));
		ps.executeUpdate();
	}

	public Map<Integer, Integer> getHybridNgramIdsWithCandidateCount(Integer[] posIDs) {
		Map<Integer, Integer> hIdsToCountMap = new HashMap<>();
		String template = "SELECT hybrid_id, COUNT(*) FROM hybrid_pos_index_bigram WHERE pos_id IN (2,3) GROUP BY hybrid_id";
		// Select from the indexing table, group by and count where posIDs IN
		// (2,3,4,5...)

		return null;
	}
}
