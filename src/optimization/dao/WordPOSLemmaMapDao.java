package optimization.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import optimization.models.WordLemmaPOSMap;
import v4.dao.DatabaseConnector;

public class WordPOSLemmaMapDao {

	Connection conn;
	String wplMapTable = "wordposlemmamap";
	POSDao posDao;

	public WordPOSLemmaMapDao() {
		posDao = new POSDao();
	}

	public void addWPLMapping(String word, String pos, String lemma) throws SQLException {
		conn = DatabaseConnector.getConnection();
		int posID = posDao.addPOSTag(pos);
		String insertQuery = "INSERT INTO " + wplMapTable
				+ " (word, posID, lemma) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE frequency = frequency + 1";

		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.setString(1, word);
		ps.setInt(2, posID);
		ps.setString(3, lemma);
		ps.executeUpdate();
		conn.close();
	}

	public List<WordLemmaPOSMap> getWordsAndLemmas(int posID, String posTag) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT word, lemma FROM wordposlemmamap WHERE posID = ?";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setInt(1, posID);
		ResultSet rs = ps.executeQuery();
		List<WordLemmaPOSMap> wplMap = new ArrayList<>();
		while (rs.next()) {
			String word = rs.getString(1);
			String lemma = rs.getString(2);
			wplMap.add(new WordLemmaPOSMap(word, posTag, posID, lemma));
		}
		conn.close();
		return wplMap;
	}

	public boolean wplMappingExists(String word, String lemma, int posID) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT id FROM wordposlemmamap WHERE posID = ? AND word = ? AND lemma = ?";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setInt(1, posID);
		ps.setString(2, word);
		ps.setString(3, lemma);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return true;
		}
		conn.close();
		return false;
	}

	public List<String> getWordsGivenPosID(int posID) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT word FROM wordposlemmamap WHERE posID = ?";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setInt(1, posID);
		ResultSet rs = ps.executeQuery();
		List<String> words = new ArrayList<>();
		while (rs.next()) {
			words.add(rs.getString(1));
		}
		conn.close();
		return words;
	}

	public List<String> getWordMappingWithLemmaAndPOS(String lemma, int posID) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT word FROM wordposlemmamap WHERE lemma = ? AND posID = ?";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setString(1, lemma);
		ps.setInt(2, posID);
		ResultSet rs = ps.executeQuery();
		List<String> words = new ArrayList<>();
		while (rs.next()) {
			words.add(rs.getString(1));
		}
		conn.close();
		return words;
	}
}
