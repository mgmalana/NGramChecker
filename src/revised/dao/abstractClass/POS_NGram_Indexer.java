package revised.dao.abstractClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public abstract Integer[] getInstances(String pos) throws SQLException;

	protected Integer[] getInstances(String pos, String query) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, pos);
		ResultSet rs = ps.executeQuery();
		List<Integer> instances = new ArrayList<>();
		while (rs.next()) {
			instances.add(rs.getInt(1));
		}
		return instances.toArray(new Integer[instances.size()]);
	}
}
