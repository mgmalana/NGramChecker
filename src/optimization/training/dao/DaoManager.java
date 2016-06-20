package optimization.training.dao;

import util.NgramMapping;

public class DaoManager {
	public static NGramStorageDao getNGramStorageDao(int ngramSize) {

		if (ngramSize >= 1 && ngramSize <= NgramMapping.text.length) {
			String ngramText = NgramMapping.text[ngramSize - 1];
			return new NGramStorageDao(ngramSize, ngramText, ngramText + "_pos_frequency",
					"pos_" + ngramText + "_index");
		} else
			return null;

	}

	public static NGramToHybridDao getNGramToHybridDao(int ngramSize) {
		if (ngramSize >= 1 && ngramSize <= NgramMapping.text.length) {
			String ngramText = NgramMapping.text[ngramSize - 1];
			return new NGramToHybridDao(ngramSize, "hybrid_ngram_" + ngramText);
		} else
			return null;
	}

	public static HybridNGramPosIndexerDao getHybridNGramPOSIndexerDao(int ngramSize) {
		if (ngramSize >= 1 && ngramSize <= NgramMapping.text.length) {
			String ngramText = NgramMapping.text[ngramSize - 1];
			return new HybridNGramPosIndexerDao(ngramSize, "hybrid_pos_index_" + ngramText);
		} else
			return null;
	}
}
