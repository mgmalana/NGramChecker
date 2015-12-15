package revised.dao;

import revised.dao.abstractClass.NGramDao;
import revised.dao.abstractClass.POS_NGram_Indexer;

public class DaoManager {

	public static NGramDao getNGramDao(int ngramSize) {
		switch (ngramSize) {
		case 2:
			return new BigramDao();
		case 5:
			return new FiveGramDao();
		default:
			return null;
		}
	}

	public static POS_NGram_Indexer getIndexer(int ngramSize) {
		switch (ngramSize) {
		case 2:
			return new POS_Bigram_Indexer();
		case 5:
			return new POS_FiveGram_Indexer();
		default:
			return null;
		}
	}
}
