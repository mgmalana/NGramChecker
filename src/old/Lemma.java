package old;

import java.util.LinkedHashSet;

public class Lemma {

	String lemma;
	LinkedHashSet<Word> words;
	public Lemma(String lemma) {
		super();
		this.lemma = lemma;
	}
	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public LinkedHashSet<Word> getWords() {
		return words;
	}
	public void setWords(LinkedHashSet<Word> words) {
		this.words = words;
	}
	
	public boolean addWord(Word word){
		if(words == null)
			words = new LinkedHashSet<>();
		return words.add(word);
	}
	
}
