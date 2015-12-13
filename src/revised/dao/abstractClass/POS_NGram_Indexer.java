package revised.dao.abstractClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import revised.dao.DatabaseConnector;

public abstract class POS_NGram_Indexer {
	Connection conn;

	public POS_NGram_Indexer() {
		conn = DatabaseConnector.getConnection();
	}

	public void add(String pos, int ngramID, String query) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, pos);
		ps.setInt(2, ngramID);
		ps.executeUpdate();
	}
}
