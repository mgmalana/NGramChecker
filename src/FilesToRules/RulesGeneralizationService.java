package FilesToRules;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import util.ArrayToStringConverter;
import v4.dao.DaoManager;
import v4.dao.NGramDao;
import v4.models.NGram;

public class RulesGeneralizationService {
	static RulesGeneralizationService rgService = new RulesGeneralizationService();

	public static void main(String[] args) throws SQLException {

		for (int i = 2; i <= 7; i++) {
			System.out.println("Generalizing " + i + "-gram");
			rgService.generalize(i);
		}
	}

	public void generalize(int ngramSize) throws SQLException {

		NGramDao ngramDao = DaoManager.getNGramDao(ngramSize);

		int offset = 0;
		List<NGram> ngrams = ngramDao.getSimilarNGrams(2, offset);
		HashSet<String> rules = new HashSet<>();
		while (ngrams != null) {

			Boolean[] isPOSGeneralized = new Boolean[ngramSize];
			for (int i = 0; i < ngramSize; i++) {
				HashSet<String> set = new HashSet<>();
				for (NGram n : new ArrayList<NGram>(ngrams)) {
					System.out.println(ArrayToStringConverter.convert(n.getWords()));
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
			HashMap<Integer, String> generalizationMap = new HashMap<>();
			for (NGram n : ngrams) {
				n.setIsPOSGeneralized(isPOSGeneralized);
				generalizationMap.put(n.getId(), ArrayToStringConverter.convert(isPOSGeneralized));
				// System.out.println(ArrayToStringConverter.convert(n.getWords()));
			}
			ngramDao.setIsPOSGeneralizedBatch(generalizationMap);
			// System.out.println(ArrayToStringConverter.convert(isPOSGeneralized));
			// System.out.println("---------------------------------------------");

			for (NGram n : ngrams) {
				StringBuilder rule = new StringBuilder();
				for (int i = 0; i < ngramSize; i++) {
					if (n.getIsPOSGeneralized()[i] == true)
						rule.append(n.getPos()[i] + " ");
					else
						rule.append(n.getWords()[i] + " ");
				}
				rules.add(rule.toString().trim());
			}

			offset++;
			ngrams = ngramDao.getSimilarNGrams(2, offset);
		}

		for (String rule : rules)
			System.out.println(rule);
		System.out.println(rules.size());
	}
}
