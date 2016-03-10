package v3.model;

public class Input {

	String[] words;
	String[] lemmas;
	String[] pos;

	public Input(String words, String lemmas, String pos) {
		super();
		this.words = words.split(" ");
		this.lemmas = lemmas.split(" ");
		this.pos = pos.split(" ");
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

	public String[] getPos() {
		return pos;
	}

	public void setPos(String[] pos) {
		this.pos = pos;
	}

}
