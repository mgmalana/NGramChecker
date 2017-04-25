package v4.training.dao.quicktemp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import v4.dao.DatabaseConnector;

public class POSDao {

	String posTable = "pos";
	Connection conn;

	public int addPOSTag(String pos) throws SQLException {
		String[] posTags = { pos };
		Integer[] posIDs = addPOSTags(posTags);
		return posIDs[0];
	}

	public Integer[] addPOSTags(String[] posTags) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String insertQuery = "INSERT INTO pos(pos) VALUES (?) ON DUPLICATE KEY UPDATE pos=pos;";
		String selectQuery = "SELECT id FROM pos WHERE pos = ?";

		Integer[] posIDs = new Integer[posTags.length];
		for (int i = 0; i < posTags.length; i++) {
			PreparedStatement ps = conn.prepareStatement(insertQuery);
			ps.setString(1, posTags[i]);
			ps.executeUpdate();
			ps = conn.prepareStatement(selectQuery);
			ps.setString(1, posTags[i]);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				posIDs[i] = rs.getInt(1);
		}
		conn.close();
		return posIDs;
	}

	public Integer[] getPosIDs(String[] posTags) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT id FROM pos WHERE pos = ?";
		Integer[] posIDs = new Integer[posTags.length];

		PreparedStatement ps = conn.prepareStatement(selectQuery);
		for (int i = 0; i < posTags.length; i++) {
			ps = conn.prepareStatement(selectQuery);
			ps.setString(1, posTags[i]);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				posIDs[i] = rs.getInt(1);
		}
		conn.close();
		return posIDs;
	}

}
