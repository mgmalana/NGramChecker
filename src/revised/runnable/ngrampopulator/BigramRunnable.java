package revised.runnable.ngrampopulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import revised.dao.DaoManager;

public class BigramRunnable {
	static BufferedReader sourceLemmasReader;
	static BufferedReader sourceSentencesReader;
	static BufferedReader sourceTagsReader;

	static NGramPopulator ngramPopulator;

	public BigramRunnable() throws FileNotFoundException {
		sourceLemmasReader = new BufferedReader(new FileReader("train_lemmas.txt"));
		sourceSentencesReader = new BufferedReader(new FileReader("train_sentences.txt"));
		sourceTagsReader = new BufferedReader(new FileReader("train_tags.txt"));

		ngramPopulator = new NGramPopulator(2, DaoManager.getNGramDao(2), DaoManager.getIndexer(2));
	}

	public static void main(String[] args) throws IOException, SQLException {
		new BigramRunnable();
		ngramPopulator.saveNGramsAndPOS();
	}
}
