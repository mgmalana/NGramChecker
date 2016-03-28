package v4.grammarchecking.threaded;

import java.io.IOException;
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
import v3.test.TestErrorsProvider;

public class GrammarChecker {

	static TestErrorsProvider testErrorsProvider = new TestErrorsProvider();
	static SubstitutionService substitutionService = new SubstitutionService();

	public static void main(String[] args) throws IOException, InterruptedException {

		Input testError = testErrorsProvider.getTestErrors().get(3);
		checkGrammar(testError);
		// TODO Auto-generated method stub

		// create threads for each service / n-gram size
		// In each service, there can be threads that handle each n-gram
		// separately. ex. Kumain si Juan (bigram 1 handled by thread 1, and
		// bigram 2 handled by thread 2)
	}

	public static Suggestion[] checkGrammar(Input testError) throws InterruptedException, IOException {
		FileManager fileManager = new FileManager(Constants.RESULTS_SUBSTITUTION);
		fileManager.createFile();
		System.out.println("Writing suggestions to file");

		System.out.println("Full: " + ArrayToStringConverter.convert(testError.getWords()) + " \n"
				+ ArrayToStringConverter.convert(testError.getPos()) + "\n"
				+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);
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
				// List<Suggestion> suggestionIns = null;
				List<Suggestion> suggestionsSub = null;
				// List<Suggestion> suggestionsDel = null;
				// if (ngramSize <= 6)
				// suggestionIns = insertionService.computeInsertion(wArr, lArr,
				// pArr);
				substitutionService.setInputValues(wArr, lArr, pArr, ngramSize);
				substitutionService.start();
				substitutionService.join();
				System.out.println("Done" + ngramSize);
				suggestionsSub = sortSuggestions(substitutionService.getSuggestions());
				// if (ngramSize >= 3)
				// suggestionsDel = deletionService.computeDeletion(wArr, lArr,
				// pArr);

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
		// for all threads...use thread.join() to wait
		return null;
	}

	private static List<Suggestion> sortSuggestions(List<Suggestion> suggestions) {
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
