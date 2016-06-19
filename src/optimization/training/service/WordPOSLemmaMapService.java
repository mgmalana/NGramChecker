package optimization.training.service;

import java.sql.SQLException;

import optimization.training.dao.WordPOSLemmaMapDao;

public class WordPOSLemmaMapService {

	WordPOSLemmaMapDao wplMapDao = new WordPOSLemmaMapDao();

	public void add(String word, String pos, String lemma) throws SQLException {
		wplMapDao.addWPLMapping(word, pos, lemma);
	}

	public void addSequence(String[] words, String[] pos, String[] lemmas) throws SQLException {
		for (int i = 0; i < words.length; i++) {
			add(words[i], pos[i], lemmas[i]);
		}
	}
}
