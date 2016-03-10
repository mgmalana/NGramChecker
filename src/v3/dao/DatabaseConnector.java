package v3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ngramchecker", "root", "");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
