package revised.test;

import java.sql.SQLException;
import java.util.List;

import revised.model.Input;
import revised.model.Suggestion;
import revised.model.SuggestionToken;

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

	public static void main(String[] args) throws SQLException {
		GrammarChecker grammarChecker = new GrammarChecker();
		grammarChecker.checkGrammar();
	}

	public void checkGrammar() throws SQLException {
		testSubstitution();

		testInsertion();

		testDeletion();
	}

	private void testInsertion() throws SQLException {
		Input testError = testErrorsProvider.getTestErrors().get(0);
		String[] wArr = testError.getWords();
		String[] lArr = testError.getLemmas();
		String[] pArr = testError.getPos();

		List<Suggestion> suggestionsIns = insertionService.computeInsertion(wArr, lArr, pArr);
		for (Suggestion s : suggestionsIns) {
			if (s.getEditDistance() == 1) {
				for (int i = 0; i < s.getSuggestions().length; i++) {

					SuggestionToken sugg = s.getSuggestions()[i];
					if (sugg.isWord() == true)
						System.out.println("Insert " + sugg.getWord() + " in before " + wArr[sugg.getIndex()] + ". ");
					else
						System.out.println("Insert " + sugg.getPos() + " in before " + wArr[sugg.getIndex()] + ". ");
				}
			}
		}
		System.out.println("--------------------------");
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
		Input testError = testErrorsProvider.getTestErrors().get(2);
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
