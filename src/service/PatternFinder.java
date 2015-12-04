package service;

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

public class PatternFinder {

	InvertedPOSFileDao ifDao = new InvertedPOSFileDao();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void findSimilarPatternsPOSLevel(String[] arrSen, String[] arrLem, String[] arrPOS) {
		Map<Integer, LinkedList<InvertedPOSFileEntry>> possibleMatches = getPossibleMatches(arrPOS);

		printPossibleMatches(arrSen, possibleMatches);

		Map<Integer, ErrorWeight> errorWeights = new HashMap<>();
		Iterator it = possibleMatches.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			List<InvertedPOSFileEntry> ngramMatches = (List<InvertedPOSFileEntry>) pair.getValue();
			ErrorWeight ew = computeErrorWeight(ngramMatches, arrSen, arrLem, arrPOS);
			errorWeights.put((Integer) pair.getKey(), ew);
			System.out.println(ew.getActionsForSubstituteDiffLemma().get(0).getPossibleChange());
		}

	}

	private ErrorWeight computeErrorWeight(List<InvertedPOSFileEntry> ngramMatches, String[] arrSen, String[] arrLem,
			String[] arrPOS) {
		ErrorWeight errorWeight = new ErrorWeight();

		int i = 0, j = 0;
		while (i < ngramMatches.size() && j < arrPOS.length) {
			System.out.println(ngramMatches.get(i).getPos() + " " + arrPOS[j]);
			if (ngramMatches.get(i).getPos().equals(arrPOS[j])) {
				System.out.println("Equal POS");
				i += 1;
				j += 1;
			} else if (ngramMatches.get(i).getLemma().equals(arrLem[j])) {
				// substitution same lemma
				System.out.println("Diff POS Same Lemma");
				i += 1;
				j += 1;
				errorWeight.addNSubstituteSameLemma(new Action(new int[] { i }, arrSen[j]));
			} else {
				System.out.println("Diff POS Diff Lemma");
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

	private void printPossibleMatches(String[] arrSen, Map<Integer, LinkedList<InvertedPOSFileEntry>> possibleMatches) {
		System.out.println("Input: ");
		for (String s : arrSen)
			System.out.print(s + " ");
		System.out.println("\n");
		Iterator it = possibleMatches.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			List<InvertedPOSFileEntry> list = (List<InvertedPOSFileEntry>) pair.getValue();
			System.out.println("Sentence #" + pair.getKey());
			for (InvertedPOSFileEntry e : list)
				System.out.println(e.getSentenceNumber() + " " + e.getWordNumber() + " " + e.getLemma() + " "
						+ e.getSurfaceWord());
			System.out.println();
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	private Map<Integer, LinkedList<InvertedPOSFileEntry>> getPossibleMatches(String[] arrPOS) {
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
		return possibleMatches;
	}

}
