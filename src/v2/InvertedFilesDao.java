package v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import v3.dao.DatabaseConnector;

public class InvertedFilesDao {
	Connection conn;

	public InvertedFilesDao() {
		conn = DatabaseConnector.getConnection();
	}

	public void add(String lemma, String word, String pos, int sentenceNumber, int wordNumber) throws SQLException {
		// if (get(lemma, word, pos, sentenceNumber, wordNumber) == null) {
		String query = "INSERT INTO tokens (surfaceWord, lemma, pos, sentenceNumber, wordNumber) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, word);
		ps.setString(2, lemma);
		ps.setString(3, pos);
		ps.setInt(4, sentenceNumber);
		ps.setInt(5, wordNumber);
		ps.executeUpdate();
		// }
	}

	public InvertedPOSFileEntry get(String lemma, String word, String pos, int sentenceNumber, int wordNumber)
			throws SQLException {
		String query = "SELECT id FROM tokens WHERE lemma = ? AND surfaceWord = ? AND pos = ? AND sentenceNumber = ? AND wordNumber = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, lemma);
		ps.setString(2, word);
		ps.setString(3, pos);
		ps.setInt(4, sentenceNumber);
		ps.setInt(5, wordNumber);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			InvertedPOSFileEntry ifEntry = new InvertedPOSFileEntry(pos, lemma, word, sentenceNumber, wordNumber);
			return ifEntry;
		}
		return null;
	}

	public InvertedPOSFileEntry get(int sentenceNumber, int wordNumber) throws SQLException {
		String query = "SELECT surfaceWord, lemma, pos FROM tokens WHERE sentenceNumber = ? AND wordNumber = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, sentenceNumber);
		ps.setInt(2, wordNumber);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String surfaceWord = rs.getString(1);
			String lemma = rs.getString(2);
			String pos = rs.getString(3);
			InvertedPOSFileEntry ifEntry = new InvertedPOSFileEntry(pos, lemma, surfaceWord, sentenceNumber,
					wordNumber);
			return ifEntry;
		}
		return null;
	}

	public InvertedPOSFile get(String pos) throws SQLException {
		String query = "SELECT surfaceWord, lemma,sentenceNumber, wordNumber FROM tokens WHERE pos = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, pos);
		ResultSet rs = ps.executeQuery();

		InvertedPOSFile ifFile = null;

		while (rs.next()) {
			String surfaceWord = rs.getString(1);
			String lemma = rs.getString(2);
			int sentenceNumber = rs.getInt(3);
			int wordNumber = rs.getInt(4);

			InvertedPOSFileEntry ifEntry = new InvertedPOSFileEntry(pos, lemma, surfaceWord, sentenceNumber,
					wordNumber);

			if (ifFile == null)
				ifFile = new InvertedPOSFile(pos);
			ifFile.addIFEntry(ifEntry);

		}
		return ifFile;
	}
}
