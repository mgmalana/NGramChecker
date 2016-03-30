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

		for (int ngramSize = 2; ngramSize <= 7; ngramSize++) {

			NGramPopulatorThread t = new NGramPopulatorThread(wordFiles, posFiles, lemmaFiles, ngramSize);
			t.start();
		}
	}
}

class NGramPopulatorThread extends Thread {

	List<File> wordFiles;
	List<File> posFiles;
	List<File> lemmaFiles;
	private int ngramSize;

	public NGramPopulatorThread(List<File> wordFiles, List<File> posFiles, List<File> lemmaFiles, int ngramSize) {
		this.wordFiles = wordFiles;
		this.posFiles = posFiles;
		this.lemmaFiles = lemmaFiles;
		this.ngramSize = ngramSize;
	}

	@Override
	public void run() {
		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;

		NGramDao ngramDao = DaoManager.getNGramDao(ngramSize);
		POS_NGram_Indexer indexer = DaoManager.getIndexer(ngramSize);

		try {
			for (int n = 0; n < lemmaFiles.size(); n++) {
				File wordFile = wordFiles.get(n);
				File lemmaFile = lemmaFiles.get(n);
				File posFile = posFiles.get(n);

				sourceSentencesReader = new BufferedReader(new FileReader(wordFile.getAbsolutePath()));
				sourceLemmasReader = new BufferedReader(new FileReader(lemmaFile.getAbsolutePath()));
				sourceTagsReader = new BufferedReader(new FileReader(posFile.getAbsolutePath()));

				System.out.println(
						ngramSize + " " + wordFile.getName() + " " + lemmaFile.getName() + " " + posFile.getName());

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

		} catch (IOException | SQLException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
