package optimization.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import v4.dao.DatabaseConnector;

public class WordPOSLemmaMapDao {

	Connection conn;
	String wplMapTable = "wordposlemmamap";
	POSDao posDao;

	public WordPOSLemmaMapDao() {
		conn = DatabaseConnector.getConnection();
		posDao = new POSDao();
	}

	public void addWPLMapping(String word, String pos, String lemma) throws SQLException {
		int posID = posDao.addPOSTag(pos);
		String insertQuery = "INSERT IGNORE INTO " + wplMapTable + " (word, posID, lemma) VALUES (?, ?, ?)";

		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.setString(1, word);
		ps.setInt(2, posID);
		ps.setString(3, lemma);
		ps.executeUpdate();
	}
}
