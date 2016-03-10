package v3.model;

public class Suggestion {

	private SuggestionToken[] suggestions;
	private SuggestionType suggestionType;
	private double editDistance;

	public Suggestion(double editDistance) {
		this.editDistance = editDistance;
	}

	public Suggestion(SuggestionToken[] suggestions, SuggestionType suggestionType, double editDistance) {
		super();
		this.suggestions = suggestions;
		this.suggestionType = suggestionType;
		this.editDistance = editDistance;
	}

	public SuggestionToken[] getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(SuggestionToken[] suggestions) {
		this.suggestions = suggestions;
	}

	public SuggestionType getSuggestionType() {
		return suggestionType;
	}

	public void setSuggestionType(SuggestionType suggestionType) {
		this.suggestionType = suggestionType;
	}

	public double getEditDistance() {
		return editDistance;
	}

	public void setEditDistance(double editDistance) {
		this.editDistance = editDistance;
	}

}
