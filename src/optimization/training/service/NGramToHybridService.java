package optimization.training.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimization.training.dao.DaoManager;
import optimization.training.dao.NGramStorageDao;
import optimization.training.dao.NGramToHybridDao;
import util.ArrayToStringConverter;
import v4.models.NGram;

public class NGramToHybridService {

	public void hybridizeRules(int ngramSize) throws SQLException {
		NGramToHybridDao nthDao = DaoManager.getNGramToHybridDao(ngramSize);
		NGramStorageDao ngramDao = DaoManager.getNGramStorageDao(ngramSize);
		int offset = 0;
		List<NGram> ngrams = ngramDao.getSimilarNGrams(2, offset);
		HashMap<String[], Boolean[]> rules = new HashMap<>();

		while (ngrams != null) {

			Boolean[] isPOSGeneralized = new Boolean[ngramSize];

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
				if (set.size() > 1)
					isPOSGeneralized[i] = true;
				else
					isPOSGeneralized[i] = false;
			}
			for (NGram n : new ArrayList<NGram>(ngrams)) {
				System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}

			nthDao.addHybridNGram(ngrams.get(0).getPos(), isPOSGeneralized, ngrams.size());
			rules.put(ngrams.get(0).getPos(), isPOSGeneralized);

			offset++;
			System.out.println(offset);
			ngrams = ngramDao.getSimilarNGrams(2, offset);
		}
		Iterator it = rules.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}

	}
}
