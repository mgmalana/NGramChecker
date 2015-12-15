package revised.dao;

import java.sql.Connection;
import java.sql.SQLException;

import revised.dao.abstractClass.POS_NGram_Indexer;

public class POS_FiveGram_Indexer extends POS_NGram_Indexer {

	Connection conn;

	public POS_FiveGram_Indexer() {
		conn = DatabaseConnector.getConnection();
	}

	@Override
	public void add(String[] pos, int fivegramID) throws SQLException {
		String query = "INSERT INTO pos_fivegram_index (pos, fivegramID) VALUES (?, ?)";
		add(pos, fivegramID, query);
	}

	@Override
	public Integer[] getInstances(String pos) throws SQLException {
		String query = "SELECT fivegramID FROM pos_fivegram_index WHERE pos = ?";
		return getInstances(pos, query);
	}
}
