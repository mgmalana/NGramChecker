package grammarchecker.models;

public class Suggestion {

	private SuggestionToken[] suggestions;
	private double editDistance;
	private int frequency;

	public Suggestion(double editDistance) {
		this.editDistance = editDistance;
	}

	public Suggestion(SuggestionToken[] suggestions, double editDistance) {
		super();
		this.suggestions = suggestions;
		this.editDistance = editDistance;
		this.frequency = 1;
	}

	public void incrementFrequency() {
		this.frequency++;
	}

	public SuggestionToken[] getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(SuggestionToken[] suggestions) {
		this.suggestions = suggestions;
	}

	public double getEditDistance() {
		return editDistance;
	}

	public void setEditDistance(double editDistance) {
		this.editDistance = editDistance;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
