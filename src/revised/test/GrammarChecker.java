package revised.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import revised.model.Input;
import revised.model.Suggestion;
import revised.model.SuggestionToken;
import revised.util.ArrayToStringConverter;
import revised.util.FileManager;

public class GrammarChecker {

	TestErrorsProvider testErrorsProvider = new TestErrorsProvider();

	SubstitutionService substitutionService;
	DeletionService deletionService;
	InsertionService insertionService;

	public GrammarChecker() {
		substitutionService = new SubstitutionService();
		deletionService = new DeletionService();
		insertionService = new InsertionService();
	}

	public static void main(String[] args) throws SQLException, IOException {
		GrammarChecker grammarChecker = new GrammarChecker();
		grammarChecker.checkGrammar();
	}

	public void checkGrammar() throws SQLException, IOException {

		// testGrammarCheckSub();

		// testSubstitution();
		//
		testInsertion();
		//
		// testDeletion();
	}

	private void testGrammarCheckSub() throws SQLException, IOException {
		Input testError = testErrorsProvider.getTestErrors().get(11);
		FileManager fileManager = new FileManager("results- distance less than 3.txt");
		System.out.println("Writing suggestions to file");
		fileManager.createFile();
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
					if (s.getEditDistance() <= 3) {
						fileManager.writeToFile(Double.toString(s.getEditDistance()));
						if (s.getEditDistance() > 0)
							for (SuggestionToken sugg : s.getSuggestions()) {
								if (sugg.isPOSGeneralized() == false) {
									fileManager
											.writeToFile("Replace " + sugg.getWord() + " in " + arrSugg[sugg.getIndex()]
													+ ". " + " Edit Distance: " + sugg.getEditDistance());
									arrSugg[sugg.getIndex()] = sugg.getWord();
								} else {
									fileManager.writeToFile("TReplace " + sugg.getPos() + "(" + sugg.getWord() + ")"
											+ " in " + arrSugg[sugg.getIndex()] + ". " + " Edit Distance: "
											+ sugg.getEditDistance());
									arrSugg[sugg.getIndex()] = sugg.getPos();
								}
							}
						fileManager.writeToFile(ArrayToStringConverter.convert(arrSugg));
					}
				}
				fileManager.writeToFile("------------------------------------------");
			}
		}
		fileManager.close();
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

	private void testInsertion() throws SQLException, IOException {
		Input testError = testErrorsProvider.getTestErrors().get(8);
		String[] wArr = testError.getWords();
		String[] lArr = testError.getLemmas();
		String[] pArr = testError.getPos();

		FileManager fileManager = new FileManager("results- distance less than 3.txt");
		System.out.println("Writing suggestions to file");
		fileManager.createFile();
		fileManager.writeToFile(
				"Full: " + ArrayToStringConverter.convert(testError.getWords()) + " " + testError.getWords().length);
		fileManager.writeToFile(ArrayToStringConverter.convert(wArr));
		List<Suggestion> suggestionsIns = insertionService.computeInsertion(wArr, lArr, pArr);
		for (Suggestion s : suggestionsIns) {
			if (s.getEditDistance() == 1) {
				for (int i = 0; i < s.getSuggestions().length; i++) {

					SuggestionToken sugg = s.getSuggestions()[i];
					if (sugg.isPOSGeneralized() == false) {
						System.out.println("Insert " + sugg.getWord() + " in before " + wArr[sugg.getIndex()] + ". ");
						fileManager
								.writeToFile("Insert " + sugg.getWord() + " in before " + wArr[sugg.getIndex()] + ". ");
					} else {
						System.out.println("Insert " + sugg.getPos() + " in before " + wArr[sugg.getIndex()] + ". ");
						fileManager.writeToFile("Insert " + sugg.getPos() + "(" + sugg.getWord() + ")" + " in before "
								+ wArr[sugg.getIndex()] + ". ");
					}
				}
			}
		}
		System.out.println("--------------------------");
		fileManager.close();
	}

	private void testSubstitution() throws SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(0);
		String[] wArr = testError.getWords();
		String[] lArr = testError.getLemmas();
		String[] pArr = testError.getPos();

		List<Suggestion> suggestionsSub = substitutionService.computeSubstitution(wArr, lArr, pArr);
		for (Suggestion s : suggestionsSub) {
			for (int i = 0; i < s.getSuggestions().length; i++) {
				SuggestionToken sugg = s.getSuggestions()[i];
				if (s.getEditDistance() <= 1)
					System.out.println("Change '" + wArr[sugg.getIndex()] + "' to '" + sugg.getWord() + "' "
							+ "Distance: " + s.getEditDistance() + ". ");
			}
		}
		System.out.println("--------------------------");
	}

	public void testDeletion() throws SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(9);
		String[] wArr = testError.getWords();
		String[] lArr = testError.getLemmas();
		String[] pArr = testError.getPos();

		List<Suggestion> suggestionsDel = deletionService.computeDeletion(wArr, lArr, pArr);
		for (Suggestion s : suggestionsDel) {
			if (s.getEditDistance() == 1) {
				for (int i = 0; i < s.getSuggestions().length; i++) {
					SuggestionToken sugg = s.getSuggestions()[i];
					if (sugg != null)
						System.out.println("Delete word at index " + sugg.getIndex());
				}
			}
		}
	}

}
