package v2;

public class SentenceLengthMeasure {

	public static int getSentenceWordLength(String sentence, String lemmas, String posTags) {
		return sentence.split(" ").length;
	}
}
