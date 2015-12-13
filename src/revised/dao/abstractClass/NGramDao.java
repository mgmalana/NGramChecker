package revised.dao.abstractClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import revised.dao.DatabaseConnector;
import revised.model.NGram;

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

	public List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset, String query, int ngramSize)
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
			int posID = rs.getInt(4);
			ngrams.add(new NGram(id, ngramSize, words, lemmas, posID));
		}
		if (ngrams.size() > 0)
			return ngrams;
		else
			return null;
	}

	public abstract List<NGram> getSimilarNGrams(int frequencyAtLeast, int offset) throws SQLException;
}
