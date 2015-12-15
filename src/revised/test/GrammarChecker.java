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

		getCandidateNGrams(pArr);
	}

	private static void getCandidateNGrams(String[] pArr) throws SQLException {
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
			if (entry.getValue() >= uniquePOS.length - 2) {
				int id = entry.getKey();
				candidateNGrams.add(fivegramDao.get(id));
			}
		}

		System.out.println("Word : " + word);
		System.out.println("Lemma : " + lemma);
		System.out.println("POS : " + pos);
		for (NGram n : candidateNGrams) {
			System.out.println("CWord: " + ArrayToStringConverter.convert(n.getWords()));
			System.out.println("CLemma: " + ArrayToStringConverter.convert(n.getLemmas()));
			System.out.println("CPOS: " + ArrayToStringConverter.convert(n.getPos()));
			System.out.println("CGen: " + ArrayToStringConverter.convert(n.getIsPOSGeneralized()));
		}

	}

	private static String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);

	}
}
