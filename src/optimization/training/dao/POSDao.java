package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import v4.dao.DatabaseConnector;

public class POSDao {

	String posTable = "pos";
	Connection conn = DatabaseConnector.getConnection();

	public int addPOSTag(String pos) throws SQLException {
		String[] posTags = { pos };
		Integer[] posIDs = addPOSTags(posTags);
		return posIDs[0];
	}

	public Integer[] addPOSTags(String[] posTags) throws SQLException {
		String insertQuery = "INSERT INTO pos(pos) VALUES ";
		String selectQuery = "SELECT id FROM pos WHERE pos IN (";
		Integer[] posIDs = new Integer[posTags.length];
		for (int i = 0; i < posTags.length; i++) {
			insertQuery += "('" + posTags[i] + "')";
			selectQuery += "'" + posTags[i] + "'";
			if (i != posTags.length - 1) {
				insertQuery += ",";
				selectQuery += ",";
			} else
				selectQuery += ");";
		}
		insertQuery += " ON DUPLICATE KEY UPDATE pos=pos; ";
		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.executeUpdate();
		ps = conn.prepareStatement(selectQuery);
		ResultSet rs = ps.executeQuery();
		int index = 0;
		while (rs.next()) {
			posIDs[index] = rs.getInt(1);
			index++;
		}

		// TODO Auto-generated method stub
		return posIDs;
	}

	public Integer[] getPosIDs(String[] posTags) throws SQLException {
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
		return posIDs;
	}

}
