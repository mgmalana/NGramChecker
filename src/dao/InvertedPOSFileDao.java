package dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.InvertedPOSFile;
import models.InvertedPOSFileEntry;

public class InvertedPOSFileDao {

	private static HashMap<String, InvertedPOSFile> invertedFiles;
	private static HashMap<Integer, Integer> sentenceLength;

	public InvertedPOSFileDao() {
		invertedFiles = new HashMap<>();
		sentenceLength = new HashMap<>();
	}

	public void add(String lemma, String word, String pos, int sentenceNumber, int wordNumber) {
		if (invertedFiles.get(pos) == null)
			invertedFiles.put(pos, new InvertedPOSFile(pos));

		InvertedPOSFile invertedFile = invertedFiles.get(pos);
		invertedFile.addIFEntry(new InvertedPOSFileEntry(pos, lemma, word, sentenceNumber, wordNumber));
	}

	@SuppressWarnings("rawtypes")
	public void displayDatabase() {
		Iterator it = invertedFiles.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			InvertedPOSFile invertedFile = (InvertedPOSFile) pair.getValue();

			String pos = invertedFile.getPOS();
			List<InvertedPOSFileEntry> entries = invertedFile.getIfEntries();

			System.out.println("pos: " + pos);
			for (InvertedPOSFileEntry ife : entries) {
				System.out.println(ife.getSurfaceWord() + " " + ife.getLemma() + " " + ife.getSentenceNumber() + " "
						+ ife.getWordNumber());
			}
			System.out.println();
		}
	}

	public InvertedPOSFile get(String pos) {
		return invertedFiles.get(pos);
	}

	public InvertedPOSFileEntry get(int sentenceNumber, int wordNumber) {
		Iterator it = invertedFiles.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			InvertedPOSFile invertedFile = (InvertedPOSFile) pair.getValue();
			for (InvertedPOSFileEntry e : invertedFile.getIfEntries()) {
				if (e.getSentenceNumber() == sentenceNumber && e.getWordNumber() == wordNumber)
					return e;
			}
		}
		return null;
	}

	public void saveSentenceLength(int sentenceNumber, int length) {
		sentenceLength.put(sentenceNumber, length);
	}

	public int getLength(int sentenceNumber) {
		return sentenceLength.get(sentenceNumber);
	}

}
