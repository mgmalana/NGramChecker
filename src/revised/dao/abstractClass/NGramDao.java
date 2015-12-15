package revised.dao.abstractClass;

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

import revised.dao.DatabaseConnector;
import revised.model.NGram;
import revised.util.ArrayToStringConverter;

public abstract class NGramDao {

	Connection conn;

	public NGramDao() {
		conn = DatabaseConnector.getConnection();
	}

	protected int add(String query, String words, String lemmas, String pos) throws SQLException {
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

	public abstract int add(String words, String lemmas, String pos) throws SQLException;

	public abstract NGram get(int id) throws SQLException;

	protected NGram get(int ngramSize, int id, String query) throws SQLException {
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

	protected int incrementPOSFrequency(String pos, String updateQuery, String insertQuery) throws SQLException {
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

	protected abstract int incrementPOSFrequency(String pos) throws SQLException;

	protected int getID(String pos, String query) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, pos);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	protected abstract int getID(String pos) throws SQLException;

	protected List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset, String query, int ngramSize)
			throws SQLException {
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

	public abstract List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset) throws SQLException;

	public abstract void setIsPOSGeneralized(int ngramID, String isPOSGeneralized) throws SQLException;

	protected void setIsPOSGeneralized(int ngramID, String isPOSGeneralized, String query) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, isPOSGeneralized);
		ps.setInt(2, ngramID);
		ps.executeUpdate();
	}

	public abstract String[] getPOS(int posID) throws SQLException;

	protected String[] getPOS(int posID, String query) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, posID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getString(1).split(" ");
		}
		return null;
	}

	public abstract void setIsPOSGeneralizedBatch(HashMap<Integer, String> generalizationMap) throws SQLException;

	@SuppressWarnings("rawtypes")
	protected void setIsPOSGeneralizedBatch(HashMap<Integer, String> generalizationMap, String query)
			throws SQLException {
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
	}
}
