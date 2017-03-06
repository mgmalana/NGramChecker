package v4.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:sqlite:ngramchecker.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
