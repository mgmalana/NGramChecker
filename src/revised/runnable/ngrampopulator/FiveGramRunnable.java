package revised.runnable.ngrampopulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import revised.dao.FiveGramDao;
import revised.dao.POS_FiveGram_Indexer;
import revised.util.ArrayToStringConverter;

public class FiveGramRunnable {
	static BufferedReader sourceLemmasReader;
	static BufferedReader sourceSentencesReader;
	static BufferedReader sourceTagsReader;

	static FiveGramDao fivegramDao = new FiveGramDao();
	static POS_FiveGram_Indexer fivegramIndexer = new POS_FiveGram_Indexer();

	public FiveGramRunnable() throws FileNotFoundException {
		sourceLemmasReader = new BufferedReader(new FileReader("train_lemmas.txt"));
		sourceSentencesReader = new BufferedReader(new FileReader("train_sentences.txt"));
		sourceTagsReader = new BufferedReader(new FileReader("train_tags.txt"));
	}

	public static void main(String[] args) throws IOException, SQLException {
		new FiveGramRunnable();

		System.out.println("Populating Database...");
		saveFiveGramsAndPOS();

	}

	private static void saveFiveGramsAndPOS() throws IOException, SQLException {
		String l, s, p;

		int ngramSize = 5;

		while ((l = sourceLemmasReader.readLine()) != null) {
			s = sourceSentencesReader.readLine();
			p = sourceTagsReader.readLine();

			String[] sArr = s.split(" ");

			String[] lArr = l.split(" ");

			String[] pArr = p.split(" ");

			for (int i = 0; i + ngramSize < sArr.length; i++) {
				String words = ArrayToStringConverter.convert(Arrays.copyOfRange(sArr, i, i + ngramSize));
				String lemmas = ArrayToStringConverter.convert(Arrays.copyOfRange(lArr, i, i + ngramSize));
				String[] ngramPos = Arrays.copyOfRange(pArr, i, i + ngramSize);
				String pos = ArrayToStringConverter.convert(ngramPos);

				int fivegramID = fivegramDao.add(words, lemmas, pos);
				for (String nPos : ngramPos)
					fivegramIndexer.add(nPos, fivegramID);
			}
		}
	}
}
