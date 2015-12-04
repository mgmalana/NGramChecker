package models;

public class InvertedPOSFileEntry {

	String pos;
	String lemma;
	String surfaceWord;
	int sentenceNumber;
	int wordNumber;

	public InvertedPOSFileEntry(String pos, String lemma, String surfaceWord, int sentenceNumber, int wordNumber) {
		super();
		this.pos = pos;
		this.lemma = lemma;
		this.surfaceWord = surfaceWord;
		this.sentenceNumber = sentenceNumber;
		this.wordNumber = wordNumber;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public String getSurfaceWord() {
		return surfaceWord;
	}

	public void setSurfaceWord(String surfaceWord) {
		this.surfaceWord = surfaceWord;
	}

	public int getSentenceNumber() {
		return sentenceNumber;
	}

	public void setSentenceNumber(int sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}

	public int getWordNumber() {
		return wordNumber;
	}

	public void setWordNumber(int wordNumber) {
		this.wordNumber = wordNumber;
	}

}
