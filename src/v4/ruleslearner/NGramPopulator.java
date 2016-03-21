package v4.ruleslearner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ArrayToStringConverter;
import util.Constants;
import v3.dao.DaoManager;
import v3.dao.NGramDao;
import v3.dao.POS_NGram_Indexer;

public class NGramPopulator {

	public void populateNGrams() {

		File folder = new File(Constants.JOEY_TRAINING);
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

		for (int i = 0; i < lemmaFiles.size(); i++) {
			File lem = lemmaFiles.get(i);
			File pos = posFiles.get(i);
			File word = wordFiles.get(i);

			NGramPopulatorThread t = new NGramPopulatorThread(word, pos, lem);
			t.start();
		}
	}
}

class NGramPopulatorThread extends Thread {

	private File word;
	private File pos;
	private File lem;

	public NGramPopulatorThread(File word, File pos, File lem) {
		this.word = word;
		this.pos = pos;
		this.lem = lem;
	}

	@Override
	public void run() {
		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;

		try {
			sourceSentencesReader = new BufferedReader(new FileReader(word.getAbsolutePath()));
			sourceLemmasReader = new BufferedReader(new FileReader(lem.getAbsolutePath()));
			sourceTagsReader = new BufferedReader(new FileReader(pos.getAbsolutePath()));

			System.out.println(word.getName() + " " + pos.getName() + " " + lem.getName());

			NGramDao ngramDao;
			POS_NGram_Indexer indexer;

			for (int ngramSize = 1; ngramSize <= 7; ngramSize++) {
				ngramDao = DaoManager.getNGramDao(ngramSize);
				indexer = DaoManager.getIndexer(ngramSize);

				String l, s, p;

				while ((l = sourceLemmasReader.readLine()) != null) {
					s = sourceSentencesReader.readLine();
					p = sourceTagsReader.readLine();

					String[] sArr = s.split(" ");

					String[] lArr = l.split(" ");

					String[] pArr = p.split(" ");

					for (int i = 0; i + ngramSize - 1 < sArr.length; i++) {
						String words = ArrayToStringConverter.convert(Arrays.copyOfRange(sArr, i, i + ngramSize));
						String lemmas = ArrayToStringConverter.convert(Arrays.copyOfRange(lArr, i, i + ngramSize));
						String[] ngramPos = Arrays.copyOfRange(pArr, i, i + ngramSize);
						String pos = ArrayToStringConverter.convert(ngramPos);

						int id = ngramDao.add(words, lemmas, pos);
						indexer.add(ngramPos, id);
					}
				}

			}
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
