package revised.test;

import java.sql.SQLException;
import java.util.List;

import revised.model.Suggestion;
import revised.model.SuggestionToken;

public class GrammarChecker {
	// Correct is QOT VBW CCB NNC PMC = 'ng' instead of 'nang'
	// Substitution
	String word = "QOT sabi nang hari ,";
	String lemma = "QOT sabi nang hari ,";
	String pos = "QOT VBW CCP NNC PMC";

	// Correct is QOT VBW NNC PMC = add 'ng'
	// Addition
	String word2 = "QOT sabi hari ,";
	String lemma2 = "QOT sabi hari ,";
	String pos2 = "QOT VBW NNC PMC";

	// Correct is QOT VBW CCB NNC PMC = delete 'na'
	// Substitution
	String word3 = "QOT sabi na ng hari ,";
	String lemma3 = "QOT sabi na ng hari ,";
	String pos3 = "QOT VBW CCP CCB NNC PMC";

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
		String[] wArr = word2.split(" ");
		String[] lArr = lemma2.split(" ");
		String[] pArr = pos2.split(" ");
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
		String[] wArr = word.split(" ");
		String[] lArr = lemma.split(" ");
		String[] pArr = pos.split(" ");

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
		String[] wArr = word3.split(" ");
		String[] lArr = lemma3.split(" ");
		String[] pArr = pos3.split(" ");
		List<Suggestion> suggestionsDel = deletionService.computeDeletion(wArr, lArr, pArr);
		// System.out.println("Candidates for Deletion ");
		// for (NGram n : candidateDelNGrams)
		// System.out.println(
		// ArrayToStringConverter.convert(n.getWords()) + " " +
		// ArrayToStringConverter.convert(n.getPos()));
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
