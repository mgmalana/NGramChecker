package revised.dao;

import java.sql.Connection;
import java.sql.SQLException;

import revised.dao.abstractClass.POS_NGram_Indexer;

public class POS_Bigram_Indexer extends POS_NGram_Indexer {
	Connection conn;

	public POS_Bigram_Indexer() {
		conn = DatabaseConnector.getConnection();
	}

	public void add(String pos, int bigramID) throws SQLException {
		String query = "INSERT INTO pos_bigram_index (pos, bigramID) VALUES (?, ?)";
		add(pos, bigramID, query);
	}
}
