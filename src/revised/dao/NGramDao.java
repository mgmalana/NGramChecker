package revised.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import revised.model.NGram;
import revised.util.ArrayToStringConverter;

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
		String query = "INSERT INTO " + ngramTable + " (words, lemmas, posID) VALUES (?, ?, ?)";

		int posID = incrementPOSFrequency(pos);

		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, words);
		ps.setString(2, lemmas);
		ps.setInt(3, posID);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

	public NGram get(int id) throws SQLException {
		String query = "SELECT words, lemmas, pos, isPOSGeneralized FROM " + ngramTable + " f INNER JOIN "
				+ ngramFrequencyTable + " p ON f.posID = p.id WHERE f.id = ?";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String[] words = rs.getString(1).split(" ");
			String[] lemmas = rs.getString(2).split(" ");
			String[] pos = rs.getString(3).split(" ");
			Boolean[] isPOSGeneralized = null;
			if (rs.getString(4) != null)
				isPOSGeneralized = ArrayToStringConverter.stringToBoolArr(rs.getString(4));
			return new NGram(id, ngramSize, words, lemmas, pos, isPOSGeneralized);
		}
		return null;
	}

	public int incrementPOSFrequency(String pos) throws SQLException {
		String updateQuery = "UPDATE " + ngramFrequencyTable + " SET frequency = frequency + 1 WHERE id = ?";
		String insertQuery = "INSERT INTO " + ngramFrequencyTable + " (pos) VALUES (?)";

		int id = getID(pos);

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

	public int getID(String pos) throws SQLException {
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

	public HashMap<Integer, NGram> getGeneralizedNGrams() throws SQLException {
		String query = "SELECT F.id, words, lemmas, pos, isPOSGeneralized, posID FROM " + ngramTable + " F INNER JOIN "
				+ "(SELECT id, pos FROM " + ngramFrequencyTable + ") A WHERE isPOSGeneralized LIKE '%true%'";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		HashMap<Integer, NGram> ngrams = new HashMap<>();
		while (rs.next()) {
			if (ngrams.get(rs.getInt(6)) != null)
				continue;
			int id = rs.getInt(1);
			String[] words = rs.getString(2).split(" ");
			String[] lemmas = rs.getString(3).split(" ");
			String[] pos = rs.getString(4).split(" ");
			Boolean[] isPOSGeneralized = null;
			if (rs.getString(5) != null)
				isPOSGeneralized = ArrayToStringConverter.stringToBoolArr(rs.getString(5));
			ngrams.put(rs.getInt(6), new NGram(id, ngramSize, words, lemmas, pos, isPOSGeneralized));
		}

		return ngrams;
	}

	public void setIsPOSGeneralized(int ngramID, String isPOSGeneralized) throws SQLException {
		String query = "UPDATE " + ngramTable + " SET isPOSGeneralized = ? WHERE id = ?";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, isPOSGeneralized);
		ps.setInt(2, ngramID);
		ps.executeUpdate();
	}

	public String[] getPOS(int posID) throws SQLException {
		String query = "SELECT pos FROM " + ngramFrequencyTable + " WHERE id = ?";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, posID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getString(1).split(" ");
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public void setIsPOSGeneralizedBatch(HashMap<Integer, String> generalizationMap) throws SQLException {
		String query = "UPDATE " + ngramTable + " SET isPOSGeneralized = ? WHERE id = ?";

		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(query);
		Iterator it = generalizationMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			ps.setString(1, (String) pair.getValue());
			ps.setInt(2, (int) pair.getKey());
			ps.addBatch();
			it.remove(); // avoids a ConcurrentModificationException
		}
		ps.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}
}
