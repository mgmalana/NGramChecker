package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimization.dao.DaoManager;
import optimization.dao.HybridDao;
import optimization.dao.HybridNGramPosIndexerDao;
import optimization.dao.POSDao;
import optimization.models.HybridNGram;
import util.Constants;

public class CandidateNGramService {
	static POSDao posDao = new POSDao();
	static HybridNGramPosIndexerDao hybridPosIndexerDao;
	static HybridDao hybridDao;

	public static void main(String[] args) throws SQLException {
		CandidateNGramService service = new CandidateNGramService();
		String[] posTags = { "NNP", "PMC", "NNC", "NNC" };
		// service.getCandidateNGrams(posTags, 3);
		service.getCandidateNGramsMergingPermutation(posTags, posTags.length);

		// OldCandidateNGramsService oldService =
		// OldCandidateNGramsService.getInstance();
		// oldService.getCandidateNGrams(posTags, 3);
		//
	}

	public static List<HybridNGram> getCandidateNGramsSubstitutionPermutation(String[] posTags, int ngramSize)
			throws SQLException {
		long startTime = System.currentTimeMillis();

		hybridDao = DaoManager.getNGramToHybridDao(ngramSize);
		List<String> posPatterns = new ArrayList<>();
		for (int i = 0; i < ngramSize; i++) {
			StringBuilder posPattern = new StringBuilder();
			for (int j = 0; j < ngramSize; j++) {
				if (i == j)
					posPattern.append("%");
				else
					posPattern.append(posTags[j]);
				if (j < ngramSize - 1)
					posPattern.append(" ");
			}
			posPatterns.add(posPattern.toString());
		}
		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGramsPermutation(posPatterns);
		long endTime = System.currentTimeMillis();
		// System.out.println("New Candidate Ngram Fetch Speed: " + (endTime -
		// startTime) + " | Number of Candidates: "
		// + hybridNGrams.size());
		return hybridNGrams;
	}

	public static List<HybridNGram> getCandidateNGramsInsertionPermutation(String[] posTags, int ngramSize)
			throws SQLException {
		if (ngramSize + 1 > Constants.NGRAM_SIZE_UPPER)
			return null;
		long startTime = System.currentTimeMillis();

		hybridDao = DaoManager.getNGramToHybridDao(ngramSize + 1);
		List<String> posPatterns = new ArrayList<>();
		for (int i = 0; i < ngramSize; i++) {
			StringBuilder posPattern = new StringBuilder();
			for (int j = 0; j < ngramSize; j++) {
				if (j != 0 && i == j)
					posPattern.append("% ");
				posPattern.append(posTags[j]);
				if (j < ngramSize - 1)
					posPattern.append(" ");
			}
			posPatterns.add(posPattern.toString());
		}
		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGramsPermutation(posPatterns);
		long endTime = System.currentTimeMillis();
		// System.out.println("New Candidate Ngram Fetch Speed: " + (endTime -
		// startTime) + " | Number of Candidates: "
		// + hybridNGrams.size());
		return hybridNGrams;
	}

	public static List<HybridNGram> getCandidateNGramsDeletionPermutation(String[] posTags, int ngramSize)
			throws SQLException {
		if (ngramSize - 1 < Constants.NGRAM_SIZE_LOWER)
			return null;
		long startTime = System.currentTimeMillis();

		hybridDao = DaoManager.getNGramToHybridDao(ngramSize - 1);
		List<String> posPatterns = new ArrayList<>();
		for (int i = 0; i < ngramSize; i++) {
			StringBuilder posPattern = new StringBuilder();
			for (int j = 0; j < ngramSize; j++) {
				if (i != j) {
					posPattern.append(posTags[j]);
					if (j < ngramSize - 1)
						posPattern.append(" ");
				}
			}
			// System.out.println(posPattern.toString());
			posPatterns.add(posPattern.toString());
		}
		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGramsPermutation(posPatterns);
		long endTime = System.currentTimeMillis();
		// System.out.println("New Candidate Ngram Fetch Speed: " + (endTime -
		// startTime) + " | Number of Candidates: "
		// + hybridNGrams.size());
		return hybridNGrams;
	}

	public static List<HybridNGram> getCandidateNGramsMergingPermutation(String[] posTags, int ngramSize)
			throws SQLException {
		if (ngramSize - 1 < Constants.NGRAM_SIZE_LOWER)
			return null;
		long startTime = System.currentTimeMillis();

		hybridDao = DaoManager.getNGramToHybridDao(ngramSize - 1);
		List<String> posPatterns = new ArrayList<>();
		for (int i = 0; i < ngramSize - 1; i++) {
			StringBuilder posPattern = new StringBuilder();
			for (int j = 0; j < ngramSize; j++) {
				if (i == j) {
					posPattern.append("%");
					if (j < ngramSize - 2)
						posPattern.append(" ");
				} else if (j >= i + 2 || j < i) {
					posPattern.append(posTags[j]);
					if (j < ngramSize - 1)
						posPattern.append(" ");
				}
			}
			// System.out.println(posPattern.toString());
			posPatterns.add(posPattern.toString());
		}
		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGramsPermutation(posPatterns);
		// for (HybridNGram h : hybridNGrams)
		// System.out.println(ArrayToStringConverter.convert(h.getPosTags()));
		long endTime = System.currentTimeMillis();
		// System.out.println("New Candidate Ngram Fetch Speed: " + (endTime -
		// startTime) + " | Number of Candidates: "
		// + hybridNGrams.size());
		return hybridNGrams;
	}

	public static List<HybridNGram> getCandidateNGramsUnmergingPermutation(String[] posTags, int ngramSize)
			throws SQLException {
		if (ngramSize + 1 > 7)
			return null;
		long startTime = System.currentTimeMillis();

		hybridDao = DaoManager.getNGramToHybridDao(ngramSize + 1);
		List<String> posPatterns = new ArrayList<>();
		for (int i = 0; i < ngramSize; i++) {
			StringBuilder posPattern = new StringBuilder();
			for (int j = 0; j < ngramSize; j++) {
				if (i == j)
					posPattern.append("%");
				else
					posPattern.append(posTags[j].replaceAll("_", "\\\\_"));
				if (j < ngramSize - 1)
					posPattern.append(" ");
			}
			// System.out.println(posPattern.toString());
			System.out.println(posPattern);
			posPatterns.add(posPattern.toString());
		}
		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGramsPermutation(posPatterns);
		// for (HybridNGram h : hybridNGrams)
		// System.out.println(ArrayToStringConverter.convert(h.getPosTags()));
		long endTime = System.currentTimeMillis();
		// System.out.println("New Candidate Ngram Fetch Speed: " + (endTime -
		// startTime) + " | Number of Candidates: "
		// + hybridNGrams.size());
		return hybridNGrams;
	}

	public static List<HybridNGram> getCandidateNGrams(String[] posTags, int ngramSize) throws SQLException {
		long startTime = System.currentTimeMillis();
		hybridPosIndexerDao = DaoManager.getHybridNGramPOSIndexerDao(ngramSize);
		hybridDao = DaoManager.getNGramToHybridDao(ngramSize);
		Integer[] ids = posDao.getPosIDs(posTags);
		// Integer[] ids = posDao.getPosIDs(posTags);

		String[] uniquePOS = getUniquePOS(posTags);

		List<Integer> candidateHybridIDs = new ArrayList<>();
		Map<Integer, Integer> hIdWithCandidateCount = hybridPosIndexerDao.getHybridNgramIdsWithCandidateCount(ids);
		Iterator<Map.Entry<Integer, Integer>> iter = hIdWithCandidateCount.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			if (entry.getValue() >= uniquePOS.length - 2) {
				int id = entry.getKey();
				candidateHybridIDs.add(id);
			}
		}

		List<HybridNGram> hybridNGrams = hybridDao.getCandidateHybridNGrams(candidateHybridIDs);

		// for (HybridNGram h : hybridNGrams) {
		// System.out.println(ArrayToStringConverter.convert(h.getPosTags()) + "
		// | "
		// + ArrayToStringConverter.convert(h.getPosIDs()) + " | "
		// + ArrayToStringConverter.convert(h.getIsHybrid()) + " | " +
		// h.getBaseNGramFrequency());
		// }

		long endTime = System.currentTimeMillis();

		System.out.println("New Candidate Ngram Fetch Speed: " + (endTime - startTime) + " | Number of Candidates: "
				+ hybridNGrams.size());

		return hybridNGrams;
	}

	private static String[] getUniquePOS(String[] pArr) {
		HashSet<String> uniquePOS = new HashSet<>(Arrays.asList(pArr));
		return uniquePOS.toArray(new String[uniquePOS.size()]);
	}
}
