package spellchecker.detector;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import spellchecker.training.IOFile;
import spellchecker.utility.Configuration;

/**
 * 
 * Approach in the spell checking system of detecting whether a word exists in
 * the language or not using N-gram model.
 * 
 **/
public class NGramStatistics {

	IOFile ioFile = new IOFile();
	private int nGram = Configuration.nGram;

	private LinkedHashSet<String> nGramList = null;
	private Map<String, Double> nGramFreq = null;

	/**
	 * @param nGramList
	 *            - N-gram file from the resource.
	 */
	public NGramStatistics(LinkedHashSet<String> nGramList) {

		this.nGramList = nGramList;
		generateNGramFreq();
	}

	/**
	 * Get the top n-gram (Configuration.nGramThreshold) from the generated
	 * stemmed language model using the.
	 */
	private void generateNGramFreq() {

		nGramFreq = new LinkedHashMap<>();

		int counter = 0;
		int topNGram = (int) (Configuration.nGramThreshold * nGramList.size());
		for (String wordFreq : nGramList) {
			if (counter > topNGram)
				break;

			String[] pair = wordFreq.split("\t");
			nGramFreq.put(pair[0], Double.parseDouble(pair[1]));
			counter++;
		}

	}

	/**
	 * @param word
	 *            - word that will be checked whether its n-grams is within the
	 *            language.
	 * @return true if all n-gram of the words is present in the model.
	 */
	public boolean hasHighNGramStatistics(String word) {

		boolean highNGramStat = true;
		if (!(word.length() < Configuration.MINIMUM_WORD_LENGTH)) {

			String cWord = "_" + word + "_";
			for (int counter = nGram; counter < word.length() + 1; counter++) {
				String gram = cWord.substring(counter - nGram, counter);

				if (Configuration.VERBOSE)
					System.out.println("word: " + gram + " in gramFreq " + nGramFreq.containsKey(gram) + " value: "
							+ nGramFreq.get(gram));

				if (!nGramFreq.containsKey(gram)) {
					return false;
				}
			}

		}
		return highNGramStat;
	}

	/**
	 * Display the N-gram that the system used.
	 */
	public void displayNGram() {
		System.out.println("ngramList: " + nGramList.size());
		System.out.println("ngramFreq: " + nGramFreq.size());
		int counter = 0;
		for (String word : nGramFreq.keySet()) {
			counter++;
			System.out.println(counter + " last value: " + word + " -  " + nGramFreq.get(word));
		}

	}
}
