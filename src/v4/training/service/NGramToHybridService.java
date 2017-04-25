package v4.training.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.ArrayToStringConverter;
import v4.models.NGram;
import v4.training.dao.quicktemp.DaoManager;
import v4.training.dao.quicktemp.HybridDao;
import v4.training.dao.quicktemp.NGramDao;

public class NGramToHybridService {

	public void hybridizeRules80Percent(int ngramSize) throws SQLException {
		HybridDao nthDao = DaoManager.getNGramToHybridDao(ngramSize);
		NGramDao ngramDao = DaoManager.getNGramStorageDao(ngramSize);
		int offset = 0;
		int groupSize = 4;
		List<NGram> ngrams = ngramDao.getSimilarNGrams(groupSize, offset);
		HashMap<String[], Boolean[]> rules = new HashMap<>();

		while (ngrams != null) {

			Boolean[] isPOSGeneralized = new Boolean[ngramSize];
			String[] nonHybridWords = new String[ngramSize];
			for (int i = 0; i < ngramSize; i++) {
				HashMap<String, Integer> set = new HashMap<>();
				for (NGram n : new ArrayList<NGram>(ngrams)) {
					try {
						String lc = n.getWords()[i].toLowerCase();
						if (set.containsKey(lc))
							set.put(lc, set.get(lc) + 1);
						else
							set.put(lc, 1);
					} catch (ArrayIndexOutOfBoundsException e) {
						ngramDao.delete(n);
						ngrams.remove(n);
					}
				}
				boolean shouldBeHybridized = true;
				String nonHybrid = null;
				Iterator it = set.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					if ((Integer) pair.getValue() >= 0.75 * ngrams.size()) {
						shouldBeHybridized = false;
						nonHybrid = (String) pair.getKey();
					}
					it.remove(); // avoids a ConcurrentModificationException
				}

				if (shouldBeHybridized) {
					isPOSGeneralized[i] = true;
					nonHybridWords[i] = "*";
				} else {
					isPOSGeneralized[i] = false;
					nonHybridWords[i] = nonHybrid;
				}
			}
			System.out.println(ArrayToStringConverter.convert(ngrams.get(0).getPos()) + " | "
					+ ArrayToStringConverter.convert(isPOSGeneralized) + " | "
					+ ArrayToStringConverter.convert(nonHybridWords));
			nthDao.addHybridNGram(ngrams.get(0).getPos(), isPOSGeneralized, nonHybridWords, ngrams.size());
			rules.put(ngrams.get(0).getPos(), isPOSGeneralized);

			offset++;
			System.out.println(offset);
			ngrams = ngramDao.getSimilarNGrams(groupSize, offset);
		}
		Iterator it = rules.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(ArrayToStringConverter.convert((String[]) pair.getKey()) + " = "
					+ ArrayToStringConverter.convert((Boolean[]) pair.getValue()));
			it.remove(); // avoids a ConcurrentModificationException
		}

	}

	public void hybridizeRules(int ngramSize) throws SQLException {
		HybridDao nthDao = DaoManager.getNGramToHybridDao(ngramSize);
		NGramDao ngramDao = DaoManager.getNGramStorageDao(ngramSize);
		int offset = 0;
		int groupSize = 2;
		List<NGram> ngrams = ngramDao.getSimilarNGrams(groupSize, offset);
		HashMap<String[], Boolean[]> rules = new HashMap<>();

		while (ngrams != null) {

			Boolean[] isPOSGeneralized = new Boolean[ngramSize];
			String[] nonHybridWords = new String[ngramSize];
			for (int i = 0; i < ngramSize; i++) {
				HashSet<String> set = new HashSet<>();
				for (NGram n : new ArrayList<NGram>(ngrams)) {
					try {
						set.add(n.getWords()[i].toLowerCase());
					} catch (ArrayIndexOutOfBoundsException e) {
						ngramDao.delete(n);
						ngrams.remove(n);
					}
				}

				if (set.size() > 1) {
					isPOSGeneralized[i] = true;
					nonHybridWords[i] = "*";
				} else {
					isPOSGeneralized[i] = false;
					nonHybridWords[i] = set.iterator().next();
				}
			}
			for (NGram n : new ArrayList<NGram>(ngrams)) {
				System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}

			nthDao.addHybridNGram(ngrams.get(0).getPos(), isPOSGeneralized, nonHybridWords, ngrams.size());
			rules.put(ngrams.get(0).getPos(), isPOSGeneralized);

			offset++;
			System.out.println(offset);
			ngrams = ngramDao.getSimilarNGrams(groupSize, offset);
		}
		Iterator it = rules.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(ArrayToStringConverter.convert((String[]) pair.getKey()) + " = "
					+ ArrayToStringConverter.convert((Boolean[]) pair.getValue()));
			it.remove(); // avoids a ConcurrentModificationException
		}

	}
}
