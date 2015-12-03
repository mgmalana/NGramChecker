package models;

import java.util.ArrayList;
import java.util.List;

public class InvertedFile {

	String lemma;
	List<InvertedFileEntry> ifEntries;

	public InvertedFile(String lemma) {
		this.lemma = lemma;
		ifEntries = new ArrayList<>();
	}

	public InvertedFile(String lemma, List<InvertedFileEntry> ifEntries) {
		this.lemma = lemma;
		this.ifEntries = ifEntries;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public void addIFEntry(InvertedFileEntry ifEntry) {
		ifEntries.add(ifEntry);
	}

	public List<InvertedFileEntry> getIfEntries() {
		return ifEntries;
	}

	public void setIfEntries(List<InvertedFileEntry> ifEntries) {
		this.ifEntries = ifEntries;
	}
}
