package revised.runnable.ngrampopulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import revised.dao.DaoManager;

public class FiveGramRunnable {
	static BufferedReader sourceLemmasReader;
	static BufferedReader sourceSentencesReader;
	static BufferedReader sourceTagsReader;

	static NGramPopulator ngramPopulator;

	public FiveGramRunnable() throws FileNotFoundException {
		sourceLemmasReader = new BufferedReader(new FileReader("train_lemmas.txt"));
		sourceSentencesReader = new BufferedReader(new FileReader("train_sentences.txt"));
		sourceTagsReader = new BufferedReader(new FileReader("train_tags.txt"));

		ngramPopulator = new NGramPopulator(5, DaoManager.getNGramDao(5), DaoManager.getIndexer(5));

	}

	public static void main(String[] args) throws IOException, SQLException {
		new FiveGramRunnable();
		ngramPopulator.saveNGramsAndPOS();
	}
}
