package revised.runnable.ngrampopulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import revised.dao.abstractClass.NGramDao;
import revised.dao.abstractClass.POS_NGram_Indexer;
import revised.util.ArrayToStringConverter;

public class NGramPopulator {
	BufferedReader sourceLemmasReader;
	BufferedReader sourceSentencesReader;
	BufferedReader sourceTagsReader;

	NGramDao ngramDao;
	POS_NGram_Indexer indexer;
	int ngramSize;

	public NGramPopulator(int ngramSize, NGramDao ngramDao, POS_NGram_Indexer indexer) throws FileNotFoundException {
		sourceLemmasReader = new BufferedReader(new FileReader("train_lemmas.txt"));
		sourceSentencesReader = new BufferedReader(new FileReader("train_sentences.txt"));
		sourceTagsReader = new BufferedReader(new FileReader("train_tags.txt"));
		this.ngramDao = ngramDao;
		this.indexer = indexer;
		this.ngramSize = ngramSize;
	}

	public void saveNGramsAndPOS() throws SQLException, IOException {
		String l, s, p;

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

				int id = ngramDao.add(words, lemmas, pos);
				indexer.add(ngramPos, id);
			}
		}
	}

}
