package revised.runnable.rulesextractor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import revised.dao.abstractClass.NGramDao;
import revised.model.NGram;
import revised.util.ArrayToStringConverter;

public class RulesGeneralizationService {

	public void generalize(int ngramSize, NGramDao ngramDao) throws SQLException {

		int offset = 0;
		List<NGram> ngrams = ngramDao.getSimilarNGrams(2, offset);
		HashSet<String> rules = new HashSet<>();
		while (ngrams != null) {
			Boolean[] isPOSGeneralized = new Boolean[ngramSize];
			for (int i = 0; i < ngramSize; i++) {
				HashSet<String> set = new HashSet<>();
				for (NGram n : ngrams)
					set.add(n.getWords()[i].toLowerCase());
				if (set.size() > 1)
					isPOSGeneralized[i] = true;
				else
					isPOSGeneralized[i] = false;
			}
			HashMap<Integer, String> generalizationMap = new HashMap<>();
			for (NGram n : ngrams) {
				n.setIsPOSGeneralized(isPOSGeneralized);
				generalizationMap.put(n.getId(), ArrayToStringConverter.convert(isPOSGeneralized));
				System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}
			ngramDao.setIsPOSGeneralizedBatch(generalizationMap);
			System.out.println(ArrayToStringConverter.convert(isPOSGeneralized));
			System.out.println("---------------------------------------------");

			for (NGram n : ngrams) {
				StringBuilder rule = new StringBuilder();
				for (int i = 0; i < ngramSize; i++) {
					if (n.getIsPOSGeneralized()[i] == true)
						rule.append(n.getPos()[i] + " ");
					else
						rule.append(n.getWords()[i] + " ");
				}
				rules.add(rule.toString());
			}

			offset++;
			ngrams = ngramDao.getSimilarNGrams(2, offset);
		}

		for (String rule : rules)
			System.out.println(rule);
	}
}
