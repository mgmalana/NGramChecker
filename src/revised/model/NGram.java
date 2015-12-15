package revised.model;

public class NGram {

	int id;
	int ngramSize;
	String[] words;
	String[] lemmas;
	String[] pos;
	int posID;
	Boolean[] isPOSGeneralized;

	public NGram(int id, int ngramSize, String[] words, String[] lemmas, int posID) {
		super();
		this.id = id;
		this.ngramSize = ngramSize;
		this.words = words;
		this.lemmas = lemmas;
		this.posID = posID;
	}

	public NGram(int id, int ngramSize, String[] words, String[] lemmas, String[] pos) {
		super();
		this.id = id;
		this.ngramSize = ngramSize;
		this.words = words;
		this.lemmas = lemmas;
		this.pos = pos;
	}

	public NGram(int id, int ngramSize, String[] words, String[] lemmas, String[] pos, Boolean[] isPOSGeneralized) {
		super();
		this.id = id;
		this.ngramSize = ngramSize;
		this.words = words;
		this.lemmas = lemmas;
		this.pos = pos;
		this.isPOSGeneralized = isPOSGeneralized;
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

	public String[] getPos() {
		return pos;
	}

	public void setPos(String[] pos) {
		this.pos = pos;
	}

	public Boolean[] getIsPOSGeneralized() {
		return isPOSGeneralized;
	}

	public void setIsPOSGeneralized(Boolean[] isPOSGeneralized) {
		this.isPOSGeneralized = isPOSGeneralized;
	}

}
