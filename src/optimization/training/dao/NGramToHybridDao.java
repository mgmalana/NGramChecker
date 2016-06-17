package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import v4.dao.DatabaseConnector;

public class NGramToHybridDao {

	Connection conn;
	private int ngramSize;
	private String hybridNGramTable;
	private String hybridPosIndexTable;
	private POSDao posDao;

	public NGramToHybridDao(int ngramSize, String hybridNGramTable, String hybridPosIndexTable) {
		conn = DatabaseConnector.getConnection();
		this.ngramSize = ngramSize;
		this.hybridNGramTable = hybridNGramTable;
		this.hybridPosIndexTable = hybridPosIndexTable;
		this.posDao = new POSDao();
	}

	public void addHybridNGram(String hybridNgram, String isHybrid, String posTags, int frequency) throws SQLException {

		Integer[] posIDs = posDao.addPOSTags(posTags);

		String insertQuery = "INSERT IGNORE INTO " + hybridNGramTable
				+ " (hybridngram, isHybrid, frequency) VALUES (?, ?, ?)";
		String selectQuery = "SELECT id FROM " + hybridNGramTable + " WHERE hybridngram = '" + hybridNgram + "'";
		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.setString(1, hybridNgram);
		ps.setString(2, isHybrid);
		ps.setInt(3, frequency);
		ps.executeUpdate();
		ps = conn.prepareStatement(selectQuery);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int hybridNGramID = rs.getInt(1);

		StringBuilder s = new StringBuilder(
				"INSERT IGNORE INTO " + hybridPosIndexTable + " (hybrid_id, pos_id) VALUES ");
		for (int i = 0; i < posIDs.length; i++) {
			if (posIDs[i] != null) {
				s.append("(" + hybridNGramID + ", " + posIDs[i] + "),");
			}
		}

		ps = conn.prepareStatement(s.toString().substring(0, s.toString().length() - 1),
				Statement.RETURN_GENERATED_KEYS);
		ps.executeUpdate();
	}

}
