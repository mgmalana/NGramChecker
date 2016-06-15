package optimization.training.dao;

public class DaoManager {
	public static NGramStorageDao getNGramStorageDao(int ngramSize) {

		switch (ngramSize) {
		case 1:
			return new NGramStorageDao(1, "unigram", "unigram_pos_frequency", "pos_unigram_index");
		case 2:
			return new NGramStorageDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");
		case 3:
			return new NGramStorageDao(2, "trigram", "trigram_pos_frequency", "pos_trigram_index");
		case 4:
			return new NGramStorageDao(2, "fourgram", "fourgram_pos_frequency", "pos_fourgram_index");
		case 5:
			return new NGramStorageDao(2, "fivegram", "fivegram_pos_frequency", "pos_fivegram_index");
		case 6:
			return new NGramStorageDao(2, "sixgram", "sixgram_pos_frequency", "pos_sixgram_index");
		case 7:
			return new NGramStorageDao(2, "sevengram", "sevengram_pos_frequency", "pos_sevengram_index");
		default:
			return null;
		}
	}

	public static NGramToHybridDao getNGramToHybridDao(int ngramSize) {

		switch (ngramSize) {
		case 2:
			return new NGramToHybridDao(2, "bigram", "bigram_pos_frequency", "hybrid_pos_index_bigram");
		case 3:
			return new NGramToHybridDao(2, "trigram", "trigram_pos_frequency", "hybrid_pos_index_trigram");
		case 4:
			return new NGramToHybridDao(2, "fourgram", "fourgram_pos_frequency", "hybrid_pos_index_fourgram");
		case 5:
			return new NGramToHybridDao(2, "fivegram", "fivegram_pos_frequency", "hybrid_pos_index_fivegram");
		case 6:
			return new NGramToHybridDao(2, "sixgram", "sixgram_pos_frequency", "hybrid_pos_index_sixgram");
		case 7:
			return new NGramToHybridDao(2, "sevengram", "sevengram_pos_frequency", "hybrid_pos_index_sevengram");
		default:
			return null;
		}
	}

}
