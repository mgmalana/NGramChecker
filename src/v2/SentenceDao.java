package v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import revised.dao.DatabaseConnector;

public class SentenceDao {
	Connection conn;

	public SentenceDao() {
		conn = DatabaseConnector.getConnection();
	}

	public Integer add(String sentence, String lemmas, String posTags) throws SQLException {

		// int idIfExists = exists(sentence, posTags);

		// if (idIfExists == -1) {
		String query = "INSERT INTO sentence (sentence, lemmas, posTags, sentenceWordLength) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, sentence);
		ps.setString(2, lemmas);
		ps.setString(3, posTags);
		ps.setInt(4, SentenceLengthMeasure.getSentenceWordLength(sentence, lemmas, posTags));
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
		// } else
		// return idIfExists;
	}

	public int exists(String sentence, String posTags) throws SQLException {
		String query = "SELECT id FROM sentence WHERE sentence = ? AND posTags = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, sentence);
		ps.setString(2, posTags);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	public Sentence get(int sentenceNumber) throws SQLException {
		String query = "SELECT sentence, lemmas ,posTags FROM sentence WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, sentenceNumber);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String sentence = rs.getString(1);
			String lemmas = rs.getString(2);
			String posTags = rs.getString(3);
			Sentence s = new Sentence(sentenceNumber, sentence, lemmas, posTags);
			return s;
		}
		return null;
	}

	public Sentence getFirst() throws SQLException {
		String query = "SELECT id, sentence, lemmas ,posTags FROM sentence ORDER BY id LIMIT 1";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String sentence = rs.getString(2);
			String lemmas = rs.getString(3);
			String posTags = rs.getString(4);
			Sentence s = new Sentence(id, sentence, lemmas, posTags);
			return s;
		}
		return null;
	}

	public Sentence getNextAfter(int sentenceNumber) throws SQLException {
		String query = "SELECT id, sentence, lemmas ,posTags FROM sentence WHERE id > ? ORDER BY id LIMIT 1";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, sentenceNumber);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String sentence = rs.getString(2);
			String lemmas = rs.getString(3);
			String posTags = rs.getString(4);
			Sentence s = new Sentence(id, sentence, lemmas, posTags);
			return s;
		}
		return null;
	}
}
