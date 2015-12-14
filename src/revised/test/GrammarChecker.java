package revised.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import revised.dao.POS_FiveGram_Indexer;

public class GrammarChecker {

	static String word = "QOT sabi nang hari ,";
	static String lemma = "QOT sabi nang hari ,";
	static String pos = "QOT VBW CCP NNC PMC";

	static POS_FiveGram_Indexer fiveGramIndexer = new POS_FiveGram_Indexer();

	// Correct is QOT VBW CCB NNC PMC = 'ng' instead of 'nang'
	public static void main(String[] args) throws SQLException {
		String[] wArr = word.split(" ");
		String[] lArr = lemma.split(" ");
		String[] pArr = pos.split(" ");

		HashMap<Integer, Integer> instancesFrequency = new HashMap<>();
		for (String p : pArr) {
			Integer[] instances = fiveGramIndexer.getInstances(p);
			for (int i : instances) {
				if (instancesFrequency.get(i) == null)
					instancesFrequency.put(i, 0);
				instancesFrequency.put(i, instancesFrequency.get(i) + 1);
			}
		}

		Iterator it = instancesFrequency.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if ((int) pair.getValue() >= 4)
				System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}

	}
}
