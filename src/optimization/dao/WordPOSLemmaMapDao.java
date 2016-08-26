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

	public List<WordLemmaPOSMap> getWords() throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT DISTINCT(word), pos FROM wordposlemmamap as a INNER JOIN pos as b ON a.posID = b.id";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ResultSet rs = ps.executeQuery();
		List<WordLemmaPOSMap> words = new ArrayList<>();
		while (rs.next()) {
			words.add(new WordLemmaPOSMap(rs.getString(1), rs.getString(2)));
		}
		conn.close();
		return words;
	}

	public List<String> getWordsGivenPosID(int posID) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT DISTINCT(word) FROM wordposlemmamap WHERE posID = ? ORDER BY frequency DESC";
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

	public List<String> getWordsGivenPosIDGivenPrefix(int posID, String prefix, int maxLength) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT word FROM wordposlemmamap WHERE posID = ? AND lower(word) LIKE ? AND char_length(word) <= ? ORDER BY frequency DESC";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setInt(1, posID);
		ps.setString(2, prefix.toLowerCase() + "%");
		ps.setInt(3, maxLength);
		ResultSet rs = ps.executeQuery();
		List<String> words = new ArrayList<>();
		while (rs.next()) {
			words.add(rs.getString(1));
		}
		conn.close();
		return words;
	}

	public List<String> getWordsGivenPosIDGivenSuffix(int posID, String suffix, int maxLength) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT word FROM wordposlemmamap WHERE posID = ? AND lower(word) LIKE ? AND char_length(word) <= ? ORDER BY frequency DESC";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setInt(1, posID);
		ps.setString(2, "%" + suffix.toLowerCase());
		ps.setInt(3, maxLength);
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
		String selectQuery = "SELECT word FROM wordposlemmamap WHERE lemma = ? AND posID = ? ORDER BY frequency DESC";
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

	public String getEqualWordMapping(String word1, String word2, int posID) throws SQLException {
		conn = DatabaseConnector.getConnection();
		String selectQuery = "SELECT word FROM wordposlemmamap WHERE (LOWER(word) = LOWER(?) OR LOWER(word) = LOWER(?) ) AND posID = ?";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setString(1, word1);
		ps.setString(2, word2);
		ps.setInt(3, posID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String equalWord = rs.getString(1);
			conn.close();
			return equalWord;
		}
		conn.close();
		return null;

	}

	public List<String[]> tryUnmerge(int posIDLeft, int posIDRight, String wordToUnmerge) throws SQLException {
		conn = DatabaseConnector.getConnection();
		System.out.println(posIDLeft + " " + posIDRight + " " + wordToUnmerge);
		String selectQuery = "SELECT a.word, b.word FROM ngramchecker.wordposlemmamap as a, ngramchecker.wordposlemmamap as b "
				+ " where a.posID = ? and b.posID = ? and a.word!= '' and a.word != '%' and b.word != '' and b.word != '%' "
				+ " and ? LIKE concat(lower(a.word), lower(b.word))";
		PreparedStatement ps = conn.prepareStatement(selectQuery);
		ps.setInt(1, posIDLeft);
		ps.setInt(2, posIDRight);
		ps.setString(3, wordToUnmerge);
		List<String[]> unmergedWords = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String[] arr = new String[2];
			arr[0] = rs.getString(1);
			arr[1] = rs.getString(2);
			unmergedWords.add(arr);
		}
		conn.close();
		if (unmergedWords.size() > 0)
			return unmergedWords;
		else
			return null;
	}

}
