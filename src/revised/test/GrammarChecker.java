package revised.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import revised.dao.FiveGramDao;
import revised.dao.POS_FiveGram_Indexer;
import revised.model.NGram;
import revised.model.Suggestion;
import revised.util.ArrayToStringConverter;

public class GrammarChecker {

	static String word = "QOT sabi nang hari ,";
	static String lemma = "QOT sabi nang hari ,";
	static String pos = "QOT VBW CCP NNC PMC";

	static POS_FiveGram_Indexer fiveGramIndexer = new POS_FiveGram_Indexer();
	static FiveGramDao fivegramDao = new FiveGramDao();

	// Correct is QOT VBW CCB NNC PMC = 'ng' instead of 'nang'
	public static void main(String[] args) throws SQLException {
		String[] wArr = word.split(" ");
		String[] lArr = lemma.split(" ");
		String[] pArr = pos.split(" ");

		List<NGram> candidateSameSizeNGrams = getCandidateSameSizeNGrams(pArr);
		System.out.println(ArrayToStringConverter.convert(wArr));
		System.out.println("Candidates for Substitution");
		List<Suggestion> suggestionsSub = computeSubstitution(wArr, lArr, pArr, candidateSameSizeNGrams);
		for (Suggestion s : suggestionsSub)
			System.out.println(s.getSuggestion());
	}

	private static List<Suggestion> computeSubstitution(String[] wArr, String[] lArr, String[] pArr,
			List<NGram> candidateNGrams) {
		List<Suggestion> suggestions = new ArrayList<>();
		for (NGram n : candidateNGrams) {
			String[] nPOS = n.getPos();
			String[] nWords = n.getWords();
			String[] nLemmas = n.getLemmas();
			Boolean[] isPOSGeneralized = n.getIsPOSGeneralized();

			double editDistance = nPOS.length;
			int notSameIndex = -1;
			for (int i = 0; i < nPOS.length; i++) {
				if (isPOSGeneralized != null && isPOSGeneralized[i] == true && nPOS[i].equals(pArr[i]))
					editDistance--;
				else if (isPOSGeneralized != null && isPOSGeneralized[i] == false && nWords[i].equals(wArr[i]))
					editDistance--;
				else
					notSameIndex = i;
			}
			if (editDistance == 1) {
				if (nLemmas[notSameIndex].equals(lArr[notSameIndex]))
					editDistance = 0.1;
				else if (withinSpellingEditDistance(nWords[notSameIndex], wArr[notSameIndex]))
					editDistance = 0.2;
				System.out.println(ArrayToStringConverter.convert(nWords));
				int[] tokensAffected = { notSameIndex };
				String suggestion = "Change '" + wArr[notSameIndex] + "' to '" + nWords[notSameIndex] + "'.";
				suggestions.add(new Suggestion(tokensAffected, suggestion, editDistance));
			}
		}
		return suggestions;
	}

	private static boolean withinSpellingEditDistance(String string, String string2) {
		// TODO Auto-generated method stub
		return false;
	}

	private static List<NGram> getCandidateSameSizeNGrams(String[] pArr) throws SQLException {
		String[] uniquePOS = getUniquePOS(pArr);

		HashMap<Integer, Integer> instancesFrequency = new HashMap<>();
		for (String p : uniquePOS) {
			Integer[] instances = fiveGramIndexer.getInstances(p);
			for (int i : instances) {
				if (instancesFrequency.get(i) == null)
					instancesFrequency.put(i, 0);
				instancesFrequency.put(i, instancesFrequency.get(i) + 1);
			}
		}

		List<NGram> candidateNGrams = new ArrayList<>();
		Iterator<Map.Entry<Integer, Integer>> iter = instancesFrequency.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			if (entry.getValue() == uniquePOS.length - 1) {
				int id = entry.getKey();
				candidateNGrams.add(fivegramDao.get(id));
			}
		}

		// System.out.println("Word : " + word);
		// System.out.println("Lemma : " + lemma);
		// System.out.println("POS : " + pos);
		// for (NGram n : candidateNGrams) {
		// System.out.println("CWord: " +
		// ArrayToStringConverter.convert(n.getWords()));
		// System.out.println("CLemma: " +
		// ArrayToStringConverter.convert(n.getLemmas()));
		// System.out.println("CPOS: " +
		// ArrayToStringConverter.convert(n.getPos()));
		// if (n.getIsPOSGeneralized() != null)
		// System.out.println("CGen: " +
		// ArrayToStringConverter.convert(n.getIsPOSGeneralized()));
		// else
		// System.out.println("CGen: NULL");
		// System.out.println();
		// }

		return candidateNGrams;
	}

	private static String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);

	}
}
