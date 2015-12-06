package runnable;

import dao.InvertedPOSFileDao;
import service.PatternFinder;

public class Main {

	static String sentence1 = "<_> Kumain si Mark ng mansanas .";
	static String lemma1 = "<_> kain si Mark ng mansanas .";
	static String pos1 = "<_> VB_TS_AF DTP NNP CCB NNC .";

	static String sentence2 = "<_> Sumayaw ang babae ng cha-cha .";
	static String lemma2 = "<_> sayaw ang babae ng cha-cha .";
	static String pos2 = "<_> VB_TS_AF DTC NNC CCB NNC .";

	static String sentenceI = "<_> Naglaro si Juan nang basketball .";
	static String lemmaI = "<_> laro si Juan nang basketball .";
	static String posI = "<_> VB_TS_AF DTP NNP CCC NNC .";

	static String sentenceI2 = "<_> Nagsayaw ang babae ng tango .";
	static String lemmaI2 = "<_> sayaw ang babae ng tango .";
	static String posI2 = "<_> ? DTC NNC CCB NNC .";

	static String sentenceI3 = "nang tango";
	static String lemmaI3 = "nang tango";
	static String posI3 = "CCC NNC";

	// Make rules for 5-grams.

	static InvertedPOSFileDao ifDao = new InvertedPOSFileDao();
	static PatternFinder patternFinder = new PatternFinder();

	public static void main(String[] args) {
		String[] arrSen = sentence1.split(" ");
		String[] arrLem = lemma1.split(" ");
		String[] arrPOS = pos1.split(" ");
		addToInvertedFile(arrSen, arrLem, arrPOS, 1);

		arrSen = sentence2.split(" ");
		arrLem = lemma2.split(" ");
		arrPOS = pos2.split(" ");

		addToInvertedFile(arrSen, arrLem, arrPOS, 2);

		// ifDao.displayDatabase();

		patternFinder.findSimilarPatternsPOSLevel(sentenceI.split(" "), lemmaI.split(" "), posI.split(" "));
	}

	private static void addToInvertedFile(String[] arrSen, String[] arrLem, String[] arrPOS, int sentenceNumber) {

		for (int i = 0; i < arrLem.length; i++) {
			ifDao.add(arrLem[i], arrSen[i], arrPOS[i], sentenceNumber, i);
		}
		ifDao.saveSentenceLength(sentenceNumber, arrPOS.length);
	}

}
