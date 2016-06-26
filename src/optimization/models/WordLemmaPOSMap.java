package optimization.models;

public class WordLemmaPOSMap {

	String word;
	String posTag;
	int posID;
	String lemma;

	public WordLemmaPOSMap(String word, String posTag, int posID, String lemma) {
		super();
		this.word = word;
		this.posTag = posTag;
		this.posID = posID;
		this.lemma = lemma;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPosTag() {
		return posTag;
	}

	public void setPosTag(String posTag) {
		this.posTag = posTag;
	}

	public int getPosID() {
		return posID;
	}

	public void setPosID(int posID) {
		this.posID = posID;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
}
