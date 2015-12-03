package models;

public class InvertedFileEntry {

	String pos;
	String surfaceWord;
	String sentenceNumber;
	String wordNumber;

	public InvertedFileEntry(String pos, String surfaceWord, String sentenceNumber, String wordNumber) {
		super();
		this.pos = pos;
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

	public String getSurfaceWord() {
		return surfaceWord;
	}

	public void setSurfaceWord(String surfaceWord) {
		this.surfaceWord = surfaceWord;
	}

	public String getSentenceNumber() {
		return sentenceNumber;
	}

	public void setSentenceNumber(String sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}

	public String getWordNumber() {
		return wordNumber;
	}

	public void setWordNumber(String wordNumber) {
		this.wordNumber = wordNumber;
	}

}
