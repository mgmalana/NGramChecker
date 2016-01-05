package revised.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class POS_NGram_Indexer {

	Connection conn;
	private String ngramIndexTable;

	public POS_NGram_Indexer(String ngramIndexTable) {
		conn = DatabaseConnector.getConnection();
		this.ngramIndexTable = ngramIndexTable;
	}

	public void add(String[] pos, int ngramID) throws SQLException {
		String query = "INSERT INTO " + ngramIndexTable + " (pos, ngramID) VALUES (?, ?)";
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		for (String p : pos) {
			ps.setString(1, p);
			ps.setInt(2, ngramID);
			ps.addBatch();
		}
		ps.executeBatch();
		ps.executeUpdate();
	}

	public Integer[] getInstances(String pos) throws SQLException {
		String query = "SELECT ngramID FROM " + ngramIndexTable + " WHERE pos = ?";
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
