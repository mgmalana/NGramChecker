package optimization.models;

import java.io.Serializable;

public class Input implements Serializable {

	String[] words;
	String[] lemmas;
	String[] pos;
	int ngramSize;

	public Input(String words, String pos, String lemmas) {
		super();
		this.words = words.split(" ");
		this.pos = pos.split(" ");
		this.lemmas = lemmas.split(" ");
		this.ngramSize = this.words.length;
	}

	public Input(String[] words, String[] pos, String[] lemmas, int ngramSize) {
		super();
		this.words = words;
		this.pos = pos;
		this.lemmas = lemmas;
		this.ngramSize = ngramSize;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public void setWord(String word, int index) {
		this.words[index] = word;
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

	public void setPos(String pos, int index) {
		this.pos[index] = pos;
	}

	public int getNgramSize() {
		return ngramSize;
	}

	public void setNgramSize(int ngramSize) {
		this.ngramSize = ngramSize;
	}

}
