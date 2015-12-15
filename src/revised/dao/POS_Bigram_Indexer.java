package revised.dao;

import java.sql.Connection;
import java.sql.SQLException;

import revised.dao.abstractClass.POS_NGram_Indexer;

public class POS_Bigram_Indexer extends POS_NGram_Indexer {
	Connection conn;

	public POS_Bigram_Indexer() {
		conn = DatabaseConnector.getConnection();
	}

	@Override
	public void add(String[] pos, int bigramID) throws SQLException {
		String query = "INSERT INTO pos_bigram_index (pos, bigramID) VALUES (?, ?)";
		add(pos, bigramID, query);
	}

	@Override
	public Integer[] getInstances(String pos) throws SQLException {
		String query = "SELECT bigramID FROM pos_bigram_index WHERE pos = ?";
		return getInstances(pos, query);
	}
}
