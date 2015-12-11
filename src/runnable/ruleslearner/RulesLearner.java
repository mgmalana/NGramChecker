package runnable.ruleslearner;

import java.sql.SQLException;
import java.util.Arrays;

import dao.InvertedFilesDao;
import dao.SentenceDao;
import models.Sentence;

public class RulesLearner {

	static InvertedFilesDao ifDao = new InvertedFilesDao();
	static SentenceDao sentenceDao = new SentenceDao();

	public static void main(String[] args) throws SQLException {
		getTrigramsOfSamePOS();
	}

	private static void getTrigramsOfSamePOS() throws SQLException {
		// TODO Auto-generated method stub
		Sentence s = sentenceDao.getFirst();
		System.out.println(s.getSentence());

		int ngramSize = 3;
		int numberOfTrigrams = 0;
		while (s != null) {
			String[] words = s.getSentence().split(" ");

			String[] lemmas = s.getLemmas().split(" ");

			String[] tags = s.getPosTags().split(" ");

			for (int i = 0; i + ngramSize < words.length; i++) {
				String[] subWords = Arrays.copyOfRange(words, i, i + ngramSize);
				String[] subLemmas = Arrays.copyOfRange(lemmas, i, i + ngramSize);
				String[] subTags = Arrays.copyOfRange(tags, i, i + ngramSize);
				numberOfTrigrams++;
				for (String word : subWords)
					System.out.print(word + " ");
				System.out.println();
				System.out.println("--------------------------------------------------");
			}

			s = sentenceDao.getNextAfter(s.getSentenceNumber());
		}
		System.out.println(numberOfTrigrams);

		// rank POS Trigrams In DB In Terms Of Frequencies
		// For all trigrams, check what is the maximum possible generalization
		// save in db
	}
}
