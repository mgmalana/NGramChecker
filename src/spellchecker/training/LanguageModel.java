package spellchecker.training;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import spellchecker.utility.Configuration;


/**
 * Class that creates the character N-gram model of the system.
 */
public class LanguageModel {

	private IOFile ioFile = new IOFile();
	private String fileName = null;
	private int nGram = 0;
	private Set<String> wordList = null;
	private int modelSize;

	/**
	 * @param fileName
	 *            - the filename of the stemmed words.
	 * @param nGram
	 *            - N-gram size that will be used in the system.
	 */
	public LanguageModel(String fileName, int nGram) {
		this.fileName = fileName;

		if (nGram >= Configuration.MINIMUM_WORD_LENGTH && nGram <= Configuration.MAXIMUM_WORD_LENGTH)
			this.nGram = nGram;
		else {
			System.out.println("ADJUSTING TO MINUMUM/MAXIMUM VALUE");
			if (nGram < Configuration.MINIMUM_WORD_LENGTH)
				this.nGram = Configuration.MINIMUM_WORD_LENGTH;
			else
				this.nGram = Configuration.MAXIMUM_WORD_LENGTH;
		}
	}

	/**
	 * @param stemmedList
	 *            - the list of the stemmed words.
	 * @param nGram
	 *            - N-gram size that will be used in the system.
	 */
	public LanguageModel(Set<String> stemmedList, int nGram) {
		this.wordList = stemmedList;
		if (nGram >= Configuration.MINIMUM_WORD_LENGTH && nGram <= Configuration.MAXIMUM_WORD_LENGTH)
			this.nGram = nGram;
		else {
			System.out.println("ADJUSTING TO MINUMUM/MAXIMUM VALUE");
			if (nGram < Configuration.MINIMUM_WORD_LENGTH)
				this.nGram = Configuration.MINIMUM_WORD_LENGTH;
			else
				this.nGram = Configuration.MAXIMUM_WORD_LENGTH;
		}
	}

	/**
	 * @return generated N-gram from the stemmed words.
	 */
	public LinkedHashSet<String> generateNGram() {
		LinkedHashSet<String> nGramFreq = new LinkedHashSet<>();

		HashMap<String, Integer> keyValue = new HashMap<>();
		Set<String> stemmedList = null;

		if (fileName == null)
			stemmedList = wordList;
		else
			stemmedList = ioFile.readResource(fileName);

		for (String word : stemmedList) {
			if (!(word.length() < Configuration.MINIMUM_WORD_LENGTH)) {
				String cWord = "_" + word + "_";

				for (int counter = nGram; counter < word.length() + 1; counter++) {
					String gram = cWord.substring(counter - nGram, counter);

					if (!keyValue.containsKey(gram))
						keyValue.put(gram, 1);
					else
						keyValue.put(gram, keyValue.get(gram) + 1);
				}
			}
		}
		modelSize = keyValue.size();
		LinkedHashMap<String, Integer> sortedMap = sort(keyValue);

		for (String key : sortedMap.keySet()) {
			nGramFreq.add(key + "\t" + sortedMap.get(key));
		}

		return nGramFreq;
	}

	/**
	 * @return sorted N-gram from the stemmed words.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private LinkedHashMap<String, Integer> sort(HashMap<String, Integer> map) {

		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		Object[] a = map.entrySet().toArray();
		Arrays.sort(a, new Comparator() {

			public int compare(Object o1, Object o2) {
				return ((Map.Entry<String, Integer>) o2).getValue()
						.compareTo(((Map.Entry<String, Integer>) o1).getValue());
			}
		});

		for (Object e : a) {
			String x = ((Map.Entry<String, Integer>) e).getKey();
			int y = ((Map.Entry<String, Integer>) e).getValue();
			sortedMap.put(x, y);
		}

		return sortedMap;
	}

	/**
	 * @return N-gram model.
	 */
	public int getModelSize() {
		return modelSize;
	}

}
