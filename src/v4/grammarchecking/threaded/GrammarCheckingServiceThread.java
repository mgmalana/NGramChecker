package v4.grammarchecking.threaded;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import v4.models.Suggestion;

public abstract class GrammarCheckingServiceThread extends Thread {
	static CandidateNGramsService candidateNGramService = CandidateNGramsService.getInstance();

	String[] inputWords;
	String[] inputLemmas;
	String[] inputPOS;
	int ngramSize;

	List<Suggestion> outputSuggestions = new ArrayList<>();
	boolean hasExceptionEncountered = false;

	public void setInputValues(String[] inputWords, String[] inputLemmas, String[] inputPOS, int ngramSize) {
		this.inputWords = inputWords;
		this.inputLemmas = inputLemmas;
		this.inputPOS = inputPOS;
		this.ngramSize = ngramSize;
	}

	@Override
	public void run() {
		try {
			performTask();
		} catch (SQLException e) {
			hasExceptionEncountered = true;
		}
	}

	protected abstract void performTask() throws SQLException;

	public List<Suggestion> getSuggestions() {
		return outputSuggestions;
	}
}
