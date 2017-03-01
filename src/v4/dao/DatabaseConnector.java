package v4.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:ngramchecker.db");
			System.out.println("Connection to SQLite has been established.");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
