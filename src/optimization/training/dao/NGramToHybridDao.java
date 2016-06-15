package optimization.training.dao;

import java.sql.Connection;
import java.sql.SQLException;

import v4.dao.DatabaseConnector;

public class NGramToHybridDao {

	Connection conn;
	private int ngramSize;
	private String hybridNGramTable;
	private String ngramFrequencyTable;
	private String hybridPosIndexTable;
	private POSDao posDao;

	public NGramToHybridDao(int ngramSize, String hybridNGramTable, String ngramFrequencyTable,
			String hybridPosIndexTable) {
		conn = DatabaseConnector.getConnection();
		this.ngramSize = ngramSize;
		this.hybridNGramTable = hybridNGramTable;
		this.ngramFrequencyTable = ngramFrequencyTable;
		this.hybridPosIndexTable = hybridPosIndexTable;
		this.posDao = new POSDao();
	}

	public void addHybridNGram(String hybridNgram, String isHybrid, String posTags) throws SQLException {

		int[] posIDs = posDao.addPOSTags(posTags);

		String query = "";
		// insert hybrid n-gram

		// index hybrid n-gram with pos

		// add to database, index the pos tag - hybrid n-gram
		// use first hybrid as root, succeeding s nodes with computed edit
		// distance
	}

}
