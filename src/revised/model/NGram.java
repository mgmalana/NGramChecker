package revised.model;

public class NGram {

	int id;
	int ngramSize;
	String[] words;
	String[] lemmas;
	int posID;

	public NGram(int id, int ngramSize, String[] words, String[] lemmas, int posID) {
		super();
		this.id = id;
		this.ngramSize = ngramSize;
		this.words = words;
		this.lemmas = lemmas;
		this.posID = posID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNgramSize() {
		return ngramSize;
	}

	public void setNgramSize(int ngramSize) {
		this.ngramSize = ngramSize;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public String[] getLemmas() {
		return lemmas;
	}

	public void setLemmas(String[] lemmas) {
		this.lemmas = lemmas;
	}

	public int getPosID() {
		return posID;
	}

	public void setPosID(int posID) {
		this.posID = posID;
	}

}
