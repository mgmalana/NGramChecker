package models;

public class Sentence {

	int sentenceNumber;
	String sentence;
	String lemmas;
	String posTags;

	public Sentence(String sentence, String lemmas, String posTags) {
		this.sentence = sentence;
		this.lemmas = lemmas;
		this.posTags = posTags;
	}

	public Sentence(int sentenceNumber, String sentence, String lemmas, String posTags) {
		super();
		this.sentenceNumber = sentenceNumber;
		this.sentence = sentence;
		this.lemmas = lemmas;
		this.posTags = posTags;
	}

	public int getSentenceNumber() {
		return sentenceNumber;
	}

	public void setSentenceNumber(int sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getLemmas() {
		return lemmas;
	}

	public void setLemmas(String lemmas) {
		this.lemmas = lemmas;
	}

	public String getPosTags() {
		return posTags;
	}

	public void setPosTags(String posTags) {
		this.posTags = posTags;
	}
}
