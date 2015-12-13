package revised.dao;

import java.sql.Connection;

public class POSDao {

	Connection conn;

	public POSDao() {
		conn = DatabaseConnector.getConnection();
	}
}
