package optimization.models;

import v4.models.SuggestionType;

public class Suggestion {

	private SuggestionType suggType;
	private String[] tokenSuggestions;
	private Integer[] affectedIndexes;
	private double editDistance;
	private int frequency;

	public Suggestion(SuggestionType suggType, String[] tokenSuggestions, Integer[] affectedIndexes,
			double editDistance, int frequency) {
		super();
		this.suggType = suggType;
		this.tokenSuggestions = tokenSuggestions;
		this.affectedIndexes = affectedIndexes;
		this.editDistance = editDistance;
		this.frequency = frequency;
	}

	public SuggestionType getSuggType() {
		return suggType;
	}

	public void setSuggType(SuggestionType suggType) {
		this.suggType = suggType;
	}

	public String[] getTokenSuggestions() {
		return tokenSuggestions;
	}

	public void setTokenSuggestions(String[] tokenSuggestions) {
		this.tokenSuggestions = tokenSuggestions;
	}

	public Integer[] getAffectedIndexes() {
		return affectedIndexes;
	}

	public void setAffectedIndexes(Integer[] affectedIndexes) {
		this.affectedIndexes = affectedIndexes;
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
