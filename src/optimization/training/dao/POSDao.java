package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import v4.dao.DatabaseConnector;

public class POSDao {

	String posTable = "pos";
	Connection conn = DatabaseConnector.getConnection();

	public Integer[] addPOSTags(String posTags) throws SQLException {
		String insertQuery = "INSERT INTO pos(pos) VALUES ";
		String selectQuery = "SELECT id FROM pos WHERE pos IN (";
		String[] poss = posTags.split(" ");
		Integer[] posIDs = new Integer[poss.length];
		for (int i = 0; i < poss.length; i++) {
			insertQuery += "('" + poss[i] + "')";
			selectQuery += "'" + poss[i] + "'";
			if (i != poss.length - 1) {
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

}
