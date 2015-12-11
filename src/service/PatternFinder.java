package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.InvertedFilesDao;
import dao.SentenceDao;
import models.Action;
import models.ErrorWeight;
import models.InvertedPOSFile;
import models.InvertedPOSFileEntry;
import models.NGram;
import models.Sentence;

public class PatternFinder {

	InvertedFilesDao ifDao = new InvertedFilesDao();
	SentenceDao sentenceDao = new SentenceDao();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void findSimilarPatternsPOSLevel(String[] arrSen, String[] arrLem, String[] arrPOS) throws SQLException {
		List<NGram> candidateNGrams = getCandidateNGrams(arrPOS);

		System.out.println("Input to Correct: ");
		for (int i = 0; i < arrSen.length; i++)
			System.out.print(arrSen[i] + " " + arrPOS[i] + " ");
		System.out.println();
		System.out.println("Candidate NGrams: ");
		for (NGram n : candidateNGrams) {
			System.out.println(n.toString());
		}
		System.out.println();
		Map<Integer, ErrorWeight> errorWeights = new HashMap<>();
		for (NGram n : candidateNGrams) {
			ErrorWeight ew = computeErrorWeight(n.getTokens(), arrSen, arrLem, arrPOS);
			if (ew.getActionsForSubstituteDiffLemma().size() <= 1) {
				System.out.println("Diff Lemma Possible Change: ");
				System.out.println(ew.getActionsForSubstituteDiffLemma().size());
				for (Action a : ew.getActionsForSubstituteDiffLemma()) {
					String wordToBeChanged = arrSen[a.getTokensAffected()[0]];
					System.out.println(wordToBeChanged + " ->  " + a.getPossibleChange());
				}
			}
		}
		// FIX ERROR WEIGHT LATER

	}

	private ErrorWeight computeErrorWeight(InvertedPOSFileEntry[] ngram, String[] arrSen, String[] arrLem,
			String[] arrPOS) {
		ErrorWeight errorWeight = new ErrorWeight();

		int i = 0, j = 0;
		while (i < ngram.length && j < arrPOS.length) {
			if (ngram[i].getPos().equals(arrPOS[j])) {
				i += 1;
				j += 1;
			} else if (ngram[i].getLemma().equals(arrLem[j])) {
				errorWeight.addNSubstituteSameLemma(new Action(new int[] { i }, ngram[i].getSurfaceWord()));
				i += 1;
				j += 1;
			} else if (!ngram[i].getLemma().equals(arrLem[j])) {
				errorWeight.addNSubstituteDiffLemma(new Action(new int[] { i }, ngram[i].getPos()));
				i += 1;
				j += 1;
			}
			// add others
			// how to represent "words followed by commas, periods and other
			// symbols", "words preceded by quotations", "words with -ng
			// suffix", "tagging of the phrase 'mas masaya'"
			// use of regex pre-POS tagging stage to correct unnecessary spaces
			// between symbols. Use of underscore to tag 'masayang' or 'masaya
			// na'
		}

		// substitution
		// addition
		// deletion
		// unmerging (ex. masmataas)
		// merging (ex. nagbibigay daan -> nagbibigay-daan)
		// TODO Auto-generated method stub
		return errorWeight;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<NGram> getCandidateNGrams(String[] arrPOS) throws SQLException {
		Map<Integer, LinkedList<InvertedPOSFileEntry>> possibleMatches = new HashMap<>();
		for (String pos : arrPOS) {
			InvertedPOSFile f = ifDao.get(pos);
			if (f != null)
				for (InvertedPOSFileEntry e : f.getIfEntries()) {
					if (possibleMatches.get(e.getSentenceNumber()) == null)
						possibleMatches.put(e.getSentenceNumber(), new LinkedList<>());
					possibleMatches.get(e.getSentenceNumber()).add(e);
				}
		}

		List<NGram> candidateNGrams = new ArrayList<>();

		Iterator it = possibleMatches.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			List<InvertedPOSFileEntry> list = (List<InvertedPOSFileEntry>) pair.getValue();
			int numFound = 0;
			for (int i = 0; i < arrPOS.length; i++) {
				String pos = arrPOS[i];
				for (InvertedPOSFileEntry e : list) {
					if (pos.equals(e.getPos())) {
						numFound++;
						break;
					}
				}
			}
			if (numFound * 1.0 / arrPOS.length >= 0.5) {
				int ngramSize = arrPOS.length;
				List<NGram> ngrams = getCompleteNGrams(list, ngramSize);
				ngrams = filterNGrams(ngrams, arrPOS);
				candidateNGrams.addAll(ngrams);
			}
		}
		return candidateNGrams;
	}

	private List<NGram> filterNGrams(List<NGram> ngrams, String[] arrPOS) {
		List<NGram> newList = new ArrayList<>();

		for (NGram n : ngrams) {
			int numFound = 0;
			for (int i = 0; i < arrPOS.length; i++) {
				String pos = arrPOS[i];
				for (InvertedPOSFileEntry e : n.getTokens()) {
					if (pos.equals(e.getPos())) {
						numFound++;
						break;
					}
				}
			}
			if (numFound * 1.0 / arrPOS.length >= 0.5) {
				newList.add(n);
			}
		}
		return newList;
	}

	private List<NGram> getCompleteNGrams(List<InvertedPOSFileEntry> list, int ngramSize) throws SQLException {
		List<NGram> ngrams = new ArrayList<>();

		if (list.size() == 0) {
			;
		} else if (list.size() == 1) {
			InvertedPOSFileEntry e = list.get(0);
			getNGrams(ngrams, e.getSentenceNumber(), e.getWordNumber(), e.getWordNumber(), ngramSize);
		} else
			for (InvertedPOSFileEntry e : list) {
				for (InvertedPOSFileEntry i : list) {
					int distance = e.getWordNumber() - i.getWordNumber();
					if (e != i && distance <= ngramSize && distance > 0) {
						getNGrams(ngrams, e.getSentenceNumber(), e.getWordNumber(), i.getWordNumber(), ngramSize);
					}
				}
			}
		return ngrams;
	}

	// better way to do this is to save the sentence in the database as is.
	// fetch and split them to get the n-grams rather than fetching each token
	// from the inverted file.
	private List<NGram> getNGrams(List<NGram> ngrams, int sentenceNumber, int wordNumber, int wordNumber2,
			int ngramSize) throws SQLException {
		Sentence sentence = sentenceDao.get(sentenceNumber);
		String[] words = sentence.getSentence().split(" ");
		String[] lemmas = sentence.getLemmas().split(" ");
		String[] tags = sentence.getPosTags().split(" ");

		for (int i = 0; i + ngramSize - 1 < words.length; i++) {
			if (wordNumber >= i && wordNumber2 >= i && (wordNumber < i + ngramSize || wordNumber2 < i + ngramSize)) {
				List<InvertedPOSFileEntry> ngram = new ArrayList<>();
				for (int j = i; j < i + ngramSize; j++) {
					InvertedPOSFileEntry e = new InvertedPOSFileEntry(tags[j], lemmas[j], words[j], sentenceNumber, j);
					ngram.add(e);
				}
				if (!contains(ngrams, ngram)) {
					ngrams.add(new NGram(ngram.toArray(new InvertedPOSFileEntry[ngram.size()])));
				}
			}
		}

		return ngrams;

	}

	private boolean contains(List<NGram> ngrams, List<InvertedPOSFileEntry> ngram) {
		NGram x = new NGram(ngram.toArray(new InvertedPOSFileEntry[ngram.size()]));
		for (NGram n : ngrams) {
			if (n.toString().equals(x.toString()))
				return true;
		}
		return false;
	}

}
