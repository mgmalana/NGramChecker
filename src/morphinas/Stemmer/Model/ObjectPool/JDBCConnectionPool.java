package morphinas.Stemmer.Model.ObjectPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by laurenz on 07/02/2017.
 */
public class JDBCConnectionPool extends ObjectPool<Connection>
{

	private String dsn;

	public JDBCConnectionPool(String dsn) {
		this.dsn = dsn;
	}

	@Override
	protected Connection create() {
		try {
			return (DriverManager.getConnection(dsn));
		} catch (SQLException e) {
			e.printStackTrace();
			return (null);
		}
	}

	@Override
	public void expire(Connection o) {
		try {
			((Connection) o).close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate(Connection o) {
		try {
			return (!((Connection) o).isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
			return (false);
		}
	}
}