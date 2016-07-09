package optimization.testing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import optimization.models.Input;
import optimization.models.Suggestion;
import optimization.testing.service.DeletionService;
import optimization.testing.service.InsertionService;
import optimization.testing.service.MergingService;
import optimization.testing.service.SubstitutionService;
import optimization.testing.service.TestErrorsProvider;
import util.ArrayToStringConverter;
import util.Constants;
import util.FileManager;

public class TestMain {
	static TestErrorsProvider testErrorsProvider = new TestErrorsProvider();
	static SubstitutionService subService;

	public static void main(String[] args) throws IOException, SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(7); // 0 or 50
		checkGrammar(testError);
	}

	private static void checkGrammar(Input testError) throws IOException, SQLException {
		FileManager fm = new FileManager(Constants.RESULTS_ALL);
		fm.createFile();
		System.out.println("Writing Suggestions to Files");
		fm.writeToFile("Words: " + ArrayToStringConverter.convert(testError.getWords()) + " \nPOS: "
				+ ArrayToStringConverter.convert(testError.getPos()) + "\nLemmas: "
				+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);
		System.out.println("Words: " + ArrayToStringConverter.convert(testError.getWords()) + " \nPOS: "
				+ ArrayToStringConverter.convert(testError.getPos()) + "\nLemmas: "
				+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);
		long startTime = System.currentTimeMillis();
		List<Suggestion> suggestions = checkGrammarRecursive(testError, Constants.NGRAM_SIZE_UPPER, fm);
		fm.close();
		long endTime = System.currentTimeMillis();
		System.out.println("Total Grammar Checking Time Elapsed: " + (endTime - startTime));

	}

	private static List<Suggestion> checkGrammarRecursive(Input input, int ngramSize, FileManager fm)
			throws SQLException {

		List<Suggestion> allSuggestions = new ArrayList<>();

		if (ngramSize < 2)
			return null;
		if (ngramSize > input.getWords().length)
			ngramSize = input.getWords().length;

		for (int i = 0; i + ngramSize - 1 < input.getWords().length; i++) {
			String[] wArr = Arrays.copyOfRange(input.getWords(), i, i + ngramSize);
			String[] pArr = Arrays.copyOfRange(input.getPos(), i, i + ngramSize);
			String[] lArr = Arrays.copyOfRange(input.getLemmas(), i, i + ngramSize);
			Input subInput = new Input(wArr, pArr, lArr, ngramSize);
			List<Suggestion> suggestions = new ArrayList<>();
			List<Suggestion> subSuggestions = SubstitutionService.performTask(subInput, ngramSize);
			List<Suggestion> insSuggestions = InsertionService.performTask(subInput, ngramSize);
			List<Suggestion> delSuggestions = DeletionService.performTask(subInput, ngramSize);
			List<Suggestion> merSuggestions = MergingService.performTask(subInput, ngramSize);
			if (subSuggestions == null) // ngram is grammatically correct
				System.out.println("Grammatically Correct");
			else {
				if (subSuggestions.size() > 0) {
					for (Suggestion s : subSuggestions)
						System.out.println("Subs: " + s.getEditDistance() + " " + s.getPosSuggestion() + " baseFreq: "
								+ s.getFrequency() + " " + s.isHybrid() + " "
								+ ArrayToStringConverter.convert(s.getTokenSuggestions()) + " index: "
								+ s.getAffectedIndex());
				}
				if (subSuggestions != null && insSuggestions.size() > 0) {
					for (Suggestion s : insSuggestions)
						System.out.println("Ins: " + s.getEditDistance() + " " + s.getPosSuggestion() + " baseFreq: "
								+ s.getFrequency() + " " + s.isHybrid() + " "
								+ ArrayToStringConverter.convert(s.getTokenSuggestions()) + " index:"
								+ s.getAffectedIndex());
				}
				if (subSuggestions != null && delSuggestions.size() > 0) {
					for (Suggestion s : delSuggestions)
						System.out.println("Del: " + s.getEditDistance() + " " + s.getPosSuggestion() + " baseFreq: "
								+ s.getFrequency() + " " + s.isHybrid() + " "
								+ ArrayToStringConverter.convert(s.getTokenSuggestions()) + " index:"
								+ s.getAffectedIndex());
				}
				if (subSuggestions != null && merSuggestions.size() > 0) {
					for (Suggestion s : merSuggestions)
						System.out.println("Mer: " + s.getEditDistance() + " " + s.getPosSuggestion() + " baseFreq: "
								+ s.getFrequency() + " " + s.isHybrid() + " "
								+ ArrayToStringConverter.convert(s.getTokenSuggestions()) + " index: "
								+ s.getAffectedIndex());
				}
				if (subSuggestions != null && subSuggestions.size() == 0 && insSuggestions.size() == 0
						&& delSuggestions.size() == 0 && merSuggestions.size() == 0) {
					System.out.println("Recurse to " + (ngramSize - 1));
					suggestions = checkGrammarRecursive(subInput, ngramSize - 1, fm);
					if (suggestions != null)
						allSuggestions.addAll(suggestions);
				}
			}
		}

		return allSuggestions;
	}

}
