package v4.training.dao.quicktemp;

import util.NgramMapping;

public class DaoManager {
	public static NGramDao getNGramStorageDao(int ngramSize) {

		if (ngramSize >= 1 && ngramSize <= NgramMapping.text.length) {
			String ngramText = NgramMapping.text[ngramSize - 1];
			return new NGramDao(ngramSize, ngramText, ngramText + "_pos_frequency", "pos_" + ngramText + "_index");
		} else
			return null;

	}

	public static HybridDao getNGramToHybridDao(int ngramSize) {
		if (ngramSize >= 1 && ngramSize <= NgramMapping.text.length) {
			String ngramText = NgramMapping.text[ngramSize - 1];
			return new HybridDao(ngramSize, "hybrid_ngram_" + ngramText);
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
