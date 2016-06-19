package optimization.training.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import optimization.training.dao.WordPOSLemmaMapDao;
import util.Constants;

public class WordPOSLemmaMapService {

	WordPOSLemmaMapDao wplMapDao = new WordPOSLemmaMapDao();

	public void populate() {
		File folder = new File(Constants.FEEDING_TO_SQL);
		File[] files = folder.listFiles();

		List<File> lemmaFiles = new ArrayList<>();
		List<File> posFiles = new ArrayList<>();
		List<File> wordFiles = new ArrayList<>();

		for (File f : files) {
			if (f.getName().contains("lemmas"))
				lemmaFiles.add(f);
			else if (f.getName().contains("tags"))
				posFiles.add(f);
			else if (f.getName().contains("words"))
				wordFiles.add(f);
		}

		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;
		try {
			for (int n = 0; n < lemmaFiles.size(); n++) {
				File wordFile = wordFiles.get(n);
				File lemmaFile = lemmaFiles.get(n);
				File posFile = posFiles.get(n);

				sourceSentencesReader = new BufferedReader(new FileReader(wordFile.getAbsolutePath()));
				sourceLemmasReader = new BufferedReader(new FileReader(lemmaFile.getAbsolutePath()));
				sourceTagsReader = new BufferedReader(new FileReader(posFile.getAbsolutePath()));

				System.out.println(wordFile.getName() + " " + lemmaFile.getName() + " " + posFile.getName());

				String l, s, p;

				while ((l = sourceLemmasReader.readLine()) != null) {
					s = sourceSentencesReader.readLine();
					p = sourceTagsReader.readLine();

					s = s.trim();
					p = p.trim();
					l = l.trim();

					String[] sArr = s.split(" ");

					String[] lArr = l.split(" ");

					String[] pArr = p.split(" ");

					for (int i = 0; i < sArr.length; i++) {
						add(sArr[i], pArr[i], lArr[i]);
					}
				}
			}
			System.out.println("Done");
		} catch (IOException | SQLException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(String word, String pos, String lemma) throws SQLException {
		wplMapDao.addWPLMapping(word, pos, lemma);
	}

	public void addSequence(String[] words, String[] pos, String[] lemmas) throws SQLException {
		for (int i = 0; i < words.length; i++) {
			add(words[i], pos[i], lemmas[i]);
		}
	}
}
