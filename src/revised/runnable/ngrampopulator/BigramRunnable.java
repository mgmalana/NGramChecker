package revised.runnable.ngrampopulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import revised.dao.BigramDao;
import revised.dao.POS_Bigram_Indexer;
import revised.util.ArrayToStringConverter;

public class BigramRunnable {
	static BufferedReader sourceLemmasReader;
	static BufferedReader sourceSentencesReader;
	static BufferedReader sourceTagsReader;

	static BigramDao bigramDao = new BigramDao();
	static POS_Bigram_Indexer bigramIndexer = new POS_Bigram_Indexer();

	public BigramRunnable() throws FileNotFoundException {
		sourceLemmasReader = new BufferedReader(new FileReader("train_lemmas.txt"));
		sourceSentencesReader = new BufferedReader(new FileReader("train_sentences.txt"));
		sourceTagsReader = new BufferedReader(new FileReader("train_tags.txt"));
	}

	public static void main(String[] args) throws IOException, SQLException {
		new BigramRunnable();

		System.out.println("Populating Database...");
		saveBigramsAndPOS();

	}

	private static void saveBigramsAndPOS() throws IOException, SQLException {
		String l, s, p;

		int ngramSize = 2;

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

				int fivegramID = bigramDao.add(words, lemmas, pos);
				for (String nPos : ngramPos)
					bigramIndexer.add(nPos, fivegramID);
			}
		}
	}
}
