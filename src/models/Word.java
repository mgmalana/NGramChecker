package models;

public class Word {

	String surfaceWord;
	String posTag;
	long sentenceIndex;
	long wordInSentenceIndex;
	
	Lemma prevLemma;
	Lemma nextLemma;
	
	public Word(String surfaceWord, String posTag, long sentenceIndex, long wordInSentenceIndex, Lemma prevLemma, Lemma nextLemma) {
		super();
		this.surfaceWord = surfaceWord;
		this.posTag = posTag;
		this.sentenceIndex = sentenceIndex;
		this.wordInSentenceIndex = wordInSentenceIndex;
		this.prevLemma = prevLemma;
		this.nextLemma = nextLemma;
	}

	public String getSurfaceWord() {
		return surfaceWord;
	}

	public void setSurfaceWord(String surfaceWord) {
		this.surfaceWord = surfaceWord;
	}

	public String getPosTag() {
		return posTag;
	}

	public void setPosTag(String posTag) {
		this.posTag = posTag;
	}

	public long getSentenceIndex() {
		return sentenceIndex;
	}

	public void setSentenceIndex(long sentenceIndex) {
		this.sentenceIndex = sentenceIndex;
	}

	public long getWordInSentenceIndex() {
		return wordInSentenceIndex;
	}

	public void setWordInSentenceIndex(long wordInSentenceIndex) {
		this.wordInSentenceIndex = wordInSentenceIndex;
	}

	public Lemma getPrevLemma() {
		return prevLemma;
	}

	public void setPrevLemma(Lemma prevLemma) {
		this.prevLemma = prevLemma;
	}

	public Lemma getNextLemma() {
		return nextLemma;
	}

	public void setNextLemma(Lemma nextLemma) {
		this.nextLemma = nextLemma;
	}
	
}
