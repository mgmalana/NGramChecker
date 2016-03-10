package v3.dao;

public class DaoManager {

	public static NGramDao getNGramDao(int ngramSize) {

		switch (ngramSize) {
		case 1:
			return new NGramDao(1, "unigram", "unigram_pos_frequency", "pos_unigram_index");
		case 2:
			return new NGramDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");
		case 3:
			return new NGramDao(2, "trigram", "trigram_pos_frequency", "pos_trigram_index");
		case 4:
			return new NGramDao(2, "fourgram", "fourgram_pos_frequency", "pos_fourgram_index");
		case 5:
			return new NGramDao(2, "fivegram", "fivegram_pos_frequency", "pos_fivegram_index");
		case 6:
			return new NGramDao(2, "sixgram", "sixgram_pos_frequency", "pos_sixgram_index");
		case 7:
			return new NGramDao(2, "sevengram", "sevengram_pos_frequency", "pos_sevengram_index");
		default:
			return null;
		}
	}

	public static POS_NGram_Indexer getIndexer(int ngramSize) {
		switch (ngramSize) {
		case 1:
			return new POS_NGram_Indexer("pos_unigram_index");
		case 2:
			return new POS_NGram_Indexer("pos_bigram_index");
		case 3:
			return new POS_NGram_Indexer("pos_trigram_index");
		case 4:
			return new POS_NGram_Indexer("pos_fourgram_index");
		case 5:
			return new POS_NGram_Indexer("pos_fivegram_index");
		case 6:
			return new POS_NGram_Indexer("pos_sixgram_index");
		case 7:
			return new POS_NGram_Indexer("pos_sevengram_index");
		default:
			return null;
		}
	}
}
