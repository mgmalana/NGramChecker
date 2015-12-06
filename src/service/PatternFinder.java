package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.InvertedPOSFileDao;
import models.Action;
import models.ErrorWeight;
import models.InvertedPOSFile;
import models.InvertedPOSFileEntry;
import models.NGram;

public class PatternFinder {

	InvertedPOSFileDao ifDao = new InvertedPOSFileDao();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void findSimilarPatternsPOSLevel(String[] arrSen, String[] arrLem, String[] arrPOS) {
		List<NGram> candidateNGrams = getCandidateNGrams(arrPOS);

		System.out.println("Input to Correct: ");
		for (String s : arrSen)
			System.out.print(s + " ");
		System.out.println();
		System.out.println("Candidate NGrams: ");
		for (NGram n : candidateNGrams) {
			System.out.println(n.toString());
		}
		System.out.println();
		Map<Integer, ErrorWeight> errorWeights = new HashMap<>();
		for (NGram n : candidateNGrams) {
			ErrorWeight ew = computeErrorWeight(n.getTokens(), arrSen, arrLem, arrPOS);
			System.out.println("Diff Lemma Possible Change: ");
			System.out.println(ew.getActionsForSubstituteDiffLemma().size());
			for (Action a : ew.getActionsForSubstituteDiffLemma())
				System.out.println(a.getPossibleChange());
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
				i += 1;
				j += 1;
				errorWeight.addNSubstituteSameLemma(new Action(new int[] { i }, arrSen[j]));
			} else if (!ngram[i].getLemma().equals(arrLem[j])) {
				errorWeight.addNSubstituteDiffLemma(new Action(new int[] { i }, arrSen[j]));
				i += 1;
				j += 1;
			}
			// add others
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
	private List<NGram> getCandidateNGrams(String[] arrPOS) {
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
		int numFound = 0;
		for (NGram n : ngrams) {
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

	private List<NGram> getCompleteNGrams(List<InvertedPOSFileEntry> list, int ngramSize) {
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
			int ngramSize) {
		int end = ifDao.getLength(sentenceNumber);
		for (int i = 0; i + ngramSize - 1 < end; i++) {
			if (wordNumber >= i && wordNumber2 >= i && (wordNumber < i + ngramSize || wordNumber2 < i + ngramSize)) {
				List<InvertedPOSFileEntry> ngram = new ArrayList<>();
				for (int j = i; j < i + ngramSize; j++)
					ngram.add(ifDao.get(sentenceNumber, j));
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
