package v2;

import java.sql.SQLException;
import java.util.Arrays;

public class TestMain {

	// Validating each rule against the corpus to check if you should
	// create a more general rule.

	static String sentenceI = "<_> Naglaro si Juan nang basketball .";
	static String lemmaI = "<_> laro si Juan nang basketball .";
	static String posI = "<_> VB_TS_AF DTP NNP CCC NNC PMP";

	static String sentenceI2 = "<_> Nagsayaw ang babae ng tango .";
	static String lemmaI2 = "<_> sayaw ang babae ng tango .";
	static String posI2 = "<_> ? DTC NNC CCB NNC PMP";

	static String sentenceI3 = "Seryosong tao ng .";
	static String lemmaI3 = "Seryosong tao ng .";
	static String posI3 = "JJD NNC PRS CCB";

	static int ngramSize = 3;

	static PatternFinder patternFinder = new PatternFinder();

	public static void main(String[] args) throws SQLException {

		String[] words = sentenceI3.split(" ");

		String[] lemmas = lemmaI3.split(" ");

		String[] tags = posI3.split(" ");

		for (int i = 0; i + ngramSize < words.length; i++) {
			String[] subWords = Arrays.copyOfRange(words, i, i + ngramSize);
			String[] subLemmas = Arrays.copyOfRange(lemmas, i, i + ngramSize);
			String[] subTags = Arrays.copyOfRange(tags, i, i + ngramSize);

			patternFinder.findSimilarPatternsPOSLevel(subWords, subLemmas, subTags);
			System.out.println("--------------------------------------------------");
		}

	}

}
