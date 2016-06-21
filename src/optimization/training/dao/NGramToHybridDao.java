package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
