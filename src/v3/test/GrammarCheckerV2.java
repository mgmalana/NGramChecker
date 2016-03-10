package v3.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.ArrayToStringConverter;
import util.Constants;
import util.FileManager;
import v3.model.Input;
import v3.model.Suggestion;
import v3.model.SuggestionToken;

public class GrammarCheckerV2 {
	TestErrorsProvider testErrorsProvider;

	SubstitutionService substitutionService;
	DeletionService deletionService;
	InsertionService insertionService;
	MergingService mergingService;
	UnmergingService unmergingService;

	public GrammarCheckerV2() {
		substitutionService = new SubstitutionService();
		deletionService = new DeletionService();
		insertionService = new InsertionService();
		mergingService = new MergingService();
		unmergingService = new UnmergingService();

		testErrorsProvider = new TestErrorsProvider();
	}

	public static void main(String[] args) throws SQLException, IOException {
		GrammarCheckerV2 grammarChecker = new GrammarCheckerV2();
		grammarChecker.checkGrammar();
	}

	public void checkGrammar() throws SQLException, IOException {

		runSubstitutionTest();

		// runMergingTest();

		// runUnmergingTest();

		// testSubstitution();
		//
		// testInsertion();
		//
		// testDeletion();
	}

	private void runUnmergingTest() throws IOException, SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(13);
		FileManager fileManager = new FileManager(Constants.RESULTS_UNMERGING);
		fileManager.createFile();
		System.out.println("Writing suggestions to file");

		System.out.println(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);
		fileManager.writeToFile(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);

		for (int ngramSize = 7; ngramSize >= 2; ngramSize--) {
			System.out.println("N-gram = " + ngramSize);
			fileManager.writeToFile("N-gram = " + ngramSize);
			for (int i = 0; i + ngramSize - 1 < testError.getWords().length; i++) {
				String[] wArr = Arrays.copyOfRange(testError.getWords(), i, i + ngramSize);
				String[] pArr = Arrays.copyOfRange(testError.getPos(), i, i + ngramSize);
				String[] lArr = Arrays.copyOfRange(testError.getLemmas(), i, i + ngramSize);
				System.out.println(ArrayToStringConverter.convert(wArr));
				fileManager.writeToFile(ArrayToStringConverter.convert(wArr));
				List<Suggestion> suggestionsMerging = sortSuggestions(
						unmergingService.computeUnmerging(wArr, lArr, pArr));

				for (Suggestion s : suggestionsMerging) {
					String[] arrSugg = new String[wArr.length];
					System.arraycopy(wArr, 0, arrSugg, 0, wArr.length);
					fileManager.writeToFile(Double.toString(s.getEditDistance()));
					if (s.getEditDistance() > 0) {
						System.out.println(s.getSuggestions().length);
						for (SuggestionToken ts : s.getSuggestions()) {
							System.out.println("Unmerge as: " + ts.getWord() + " the word in index " + ts.getIndex());
							fileManager
									.writeToFile("Unmerge as: " + ts.getWord() + " the word in index " + ts.getIndex());
						}
					}
					fileManager.writeToFile(ArrayToStringConverter.convert(arrSugg));
				}
				fileManager.writeToFile("------------------------------------------");
			}
		}
		fileManager.close();
	}

	private void runMergingTest() throws IOException, SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(12);
		FileManager fileManager = new FileManager(Constants.RESULTS_MERGING);
		fileManager.createFile();
		System.out.println("Writing suggestions to file");

		System.out.println(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);
		fileManager.writeToFile(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);

		for (int ngramSize = 7; ngramSize >= 2; ngramSize--) {
			System.out.println("N-gram = " + ngramSize);
			fileManager.writeToFile("N-gram = " + ngramSize);
			for (int i = 0; i + ngramSize - 1 < testError.getWords().length; i++) {
				String[] wArr = Arrays.copyOfRange(testError.getWords(), i, i + ngramSize);
				String[] pArr = Arrays.copyOfRange(testError.getPos(), i, i + ngramSize);
				String[] lArr = Arrays.copyOfRange(testError.getLemmas(), i, i + ngramSize);
				System.out.println(ArrayToStringConverter.convert(wArr));
				fileManager.writeToFile(ArrayToStringConverter.convert(wArr));
				List<Suggestion> suggestionsMerging = sortSuggestions(mergingService.computeMerging(wArr, lArr, pArr));

				for (Suggestion s : suggestionsMerging) {
					String[] arrSugg = new String[wArr.length];
					System.arraycopy(wArr, 0, arrSugg, 0, wArr.length);
					fileManager.writeToFile(Double.toString(s.getEditDistance()));
					if (s.getEditDistance() > 0) {
						System.out.println(s.getSuggestions().length);
						for (SuggestionToken ts : s.getSuggestions()) {
							System.out.println("Merge as: " + ts.getWord() + " the words in index " + ts.getIndex()
									+ " and " + (ts.getIndex() + 1));
							fileManager.writeToFile("Merge as: " + ts.getWord() + " the words in index " + ts.getIndex()
									+ " and " + (ts.getIndex() + 1));
						}
					}
					fileManager.writeToFile(ArrayToStringConverter.convert(arrSugg));
				}
				fileManager.writeToFile("------------------------------------------");
			}
		}
		fileManager.close();
	}

	public void runSubstitutionTest() throws IOException, SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(3);
		FileManager fileManager = new FileManager(Constants.RESULTS_SUBSTITUTION);
		fileManager.createFile();
		System.out.println("Writing suggestions to file");

		System.out.println(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);
		fileManager.writeToFile(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);
		long startTime = System.currentTimeMillis();
		for (int ngramSize = 7; ngramSize >= 2; ngramSize--) {
			System.out.println("N-gram = " + ngramSize);
			fileManager.writeToFile("N-gram = " + ngramSize);
			for (int i = 0; i + ngramSize - 1 < testError.getWords().length; i++) {
				String[] wArr = Arrays.copyOfRange(testError.getWords(), i, i + ngramSize);
				String[] pArr = Arrays.copyOfRange(testError.getPos(), i, i + ngramSize);
				String[] lArr = Arrays.copyOfRange(testError.getLemmas(), i, i + ngramSize);
				System.out.println(ArrayToStringConverter.convert(wArr));
				fileManager.writeToFile(ArrayToStringConverter.convert(wArr));
				List<Suggestion> suggestionIns = null;
				List<Suggestion> suggestionsSub = null;
				List<Suggestion> suggestionsDel = null;
				if (ngramSize <= 6)
					suggestionIns = insertionService.computeInsertion(wArr, lArr, pArr);
				suggestionsSub = sortSuggestions(substitutionService.computeSubstitution(wArr, lArr, pArr));
				if (ngramSize >= 3)
					suggestionsDel = deletionService.computeDeletion(wArr, lArr, pArr);

				for (Suggestion s : suggestionsSub) {
					String[] arrSugg = new String[wArr.length];
					System.arraycopy(wArr, 0, arrSugg, 0, wArr.length);
					fileManager.writeToFile(Double.toString(s.getEditDistance()));
					if (s.getEditDistance() > 0)
						for (SuggestionToken sugg : s.getSuggestions()) {
							if (sugg.isPOSGeneralized() == false) {
								fileManager.writeToFile("Replace " + sugg.getWord() + " in " + arrSugg[sugg.getIndex()]
										+ ". " + " Edit Distance: " + sugg.getEditDistance());
								arrSugg[sugg.getIndex()] = sugg.getWord();
							} else {
								fileManager.writeToFile("TReplace " + sugg.getPos() + "(" + sugg.getWord() + ")"
										+ " in " + arrSugg[sugg.getIndex()] + ". " + " Edit Distance:"
										+ sugg.getEditDistance());
								arrSugg[sugg.getIndex()] = sugg.getPos();
							}
						}
					fileManager.writeToFile(ArrayToStringConverter.convert(arrSugg));

				}
				fileManager.writeToFile("------------------------------------------");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + "ms");
		fileManager.close();
	}

	public void runInsertionTest() {

	}

	public void runDeletionTest() {

	}

	private List<Suggestion> sortSuggestions(List<Suggestion> suggestions) {
		if (suggestions.size() > 0) {
			Collections.sort(suggestions, new Comparator<Suggestion>() {
				@Override
				public int compare(final Suggestion object1, final Suggestion object2) {
					return object1.getEditDistance() < object2.getEditDistance() ? -1
							: object1.getEditDistance() > object2.getEditDistance() ? 1 : 0;
				}
			});
		}
		return suggestions;
	}
}
