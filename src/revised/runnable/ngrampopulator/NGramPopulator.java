package revised.runnable.ngrampopulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import revised.dao.DaoManager;
import revised.dao.NGramDao;
import revised.dao.POS_NGram_Indexer;
import revised.util.ArrayToStringConverter;
import revised.util.Constants;

public class NGramPopulator {
	BufferedReader sourceLemmasReader;
	BufferedReader sourceSentencesReader;
	BufferedReader sourceTagsReader;

	NGramDao ngramDao;
	POS_NGram_Indexer indexer;
	int ngramSize;

	public NGramPopulator(int ngramSize) throws FileNotFoundException {
		sourceSentencesReader = new BufferedReader(new FileReader(Constants.TRAINING_SENTENCES));
		sourceLemmasReader = new BufferedReader(new FileReader(Constants.TRAINING_LEMMAS));
		sourceTagsReader = new BufferedReader(new FileReader(Constants.TRAINING_TAGS));
		this.ngramDao = DaoManager.getNGramDao(ngramSize);
		this.indexer = DaoManager.getIndexer(ngramSize);
		this.ngramSize = ngramSize;
	}

	public static void main(String[] args) throws SQLException, IOException {

		for (int i = 1; i <= 1; i++) {
			System.out.println("Populating " + i + "-gram");
			NGramPopulator n = new NGramPopulator(i);
			n.saveNGramsAndPOS();
		}
	}

	public void saveNGramsAndPOS() throws SQLException, IOException {
		ngramDao.clearDatabase();

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

}
