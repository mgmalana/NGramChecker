package optimization.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import v4.dao.DatabaseConnector;
import v4.models.NGram;

public class NGramDao {
	Connection conn;
	private int ngramSize;
	private String ngramTable;
	private String ngramFrequencyTable;
	private String ngramIndexTable;

	public NGramDao(int ngramSize, String ngramTable, String ngramFrequencyTable, String ngramIndexTable) {
		conn = DatabaseConnector.getConnection();
		this.ngramSize = ngramSize;
		this.ngramTable = ngramTable;
		this.ngramFrequencyTable = ngramFrequencyTable;
		this.ngramIndexTable = ngramIndexTable;
	}

	public void clearDatabase() throws SQLException {
		String query1 = "DELETE FROM " + ngramTable;
		String query2 = "DELETE FROM " + ngramFrequencyTable;
		String query3 = "DELETE FROM " + ngramIndexTable;

		PreparedStatement ps = conn.prepareStatement(query1);
		ps.executeUpdate();

		ps = conn.prepareStatement(query2);
		ps.executeUpdate();

		ps = conn.prepareStatement(query3);
		ps.executeUpdate();
	}

	public int add(String words, String lemmas, String pos) throws SQLException {
		String insertQuery = "INSERT INTO " + ngramTable + " (words, lemmas, posID) VALUES (?, ?, ?)";

		int posID = incrementPOSFrequency(pos);

		PreparedStatement ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, words);
		ps.setString(2, lemmas);
		ps.setInt(3, posID);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

	public void delete(NGram ngram) throws SQLException {
		String deletePOSQuery = "DELETE FROM " + ngramFrequencyTable + " WHERE id =" + ngram.getPosID();
		String deleteWordQuery = "DELETE FROM " + ngramTable + " WHERE id =" + ngram.getId();

		PreparedStatement ps = conn.prepareStatement(deletePOSQuery);
		int psVal = ps.executeUpdate();
		PreparedStatement ps1 = conn.prepareStatement(deleteWordQuery);
		int ps1Val = ps1.executeUpdate();
		System.out.println("PSVAL: " + psVal + " PS1VAL: " + ps1Val);
	}

	public int incrementPOSFrequency(String pos) throws SQLException {
		String updateQuery = "UPDATE " + ngramFrequencyTable + " SET frequency = frequency + 1 WHERE id = ?";
		String insertQuery = "INSERT INTO " + ngramFrequencyTable + " (pos) VALUES (?)";

		int id = getPOSSequenceFreqID(pos);

		PreparedStatement ps;
		if (id != -1) {
			ps = conn.prepareStatement(updateQuery);
			ps.setInt(1, id);
			ps.executeUpdate();
		} else {
			ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pos);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		}
		return id;
	}

	public int getPOSSequenceFreqID(String pos) throws SQLException {
		String query = "SELECT id FROM " + ngramFrequencyTable + " WHERE pos = ?";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, pos);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	public List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset) throws SQLException {
		String query = "SELECT F.id, words, lemmas, pos FROM " + ngramTable + " F INNER JOIN " + "(SELECT id, pos FROM "
				+ ngramFrequencyTable + " WHERE frequency >= ? LIMIT 1 OFFSET ?) B " + "ON F.posID = B.id";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, frequencyAtLeast);
		ps.setInt(2, offset);
		ResultSet rs = ps.executeQuery();

		List<NGram> ngrams = new ArrayList<>();
		while (rs.next()) {
			int id = rs.getInt(1);
			String[] words = rs.getString(2).split(" ");
			String[] lemmas = rs.getString(3).split(" ");
			String[] pos = rs.getString(4).split(" ");
			ngrams.add(new NGram(id, ngramSize, words, lemmas, pos));
		}
		if (ngrams.size() > 0)
			return ngrams;
		else
			return null;
	}
}
