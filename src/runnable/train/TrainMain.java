package runnable.train;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import dao.InvertedFilesDao;
import dao.SentenceDao;

public class TrainMain {

	static SentenceDao sentenceDao = new SentenceDao();
	static InvertedFilesDao invertedFileDao = new InvertedFilesDao();

	static BufferedReader sourceLemmasReader;
	static BufferedReader sourceSentencesReader;
	static BufferedReader sourceTagsReader;

	public TrainMain() throws FileNotFoundException {
		sourceLemmasReader = new BufferedReader(new FileReader("train_lemmas.txt"));
		sourceSentencesReader = new BufferedReader(new FileReader("train_sentences.txt"));
		sourceTagsReader = new BufferedReader(new FileReader("train_tags.txt"));
	}

	public static void main(String[] args) throws IOException, SQLException {

		TrainMain main = new TrainMain();

		String lemmas, sentence, tags;

		while ((lemmas = sourceLemmasReader.readLine()) != null) {
			sentence = sourceSentencesReader.readLine();
			tags = sourceTagsReader.readLine();

			splitAndStore(sentence, lemmas, tags);
		}
	}

	private static void splitAndStore(String sentence, String lemmaLine, String tagLine) throws SQLException {
		sentence = "<_> " + sentence;
		lemmaLine = "<_> " + lemmaLine;
		tagLine = "<_> " + tagLine;

		int sentenceNumber = sentenceDao.add(sentence, lemmaLine, tagLine);

		String[] words = sentence.split(" ");

		String[] lemmas = lemmaLine.split(" ");

		String[] tags = tagLine.split(" ");

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String lemma = lemmas[i];
			String pos = tags[i];
			invertedFileDao.add(lemma, word, pos, sentenceNumber, i);
		}

	}

}
