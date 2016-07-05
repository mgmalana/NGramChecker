package optimization.models;

import v4.models.SuggestionType;

public class Suggestion {

	private SuggestionType suggType;
	private String[] tokenSuggestions;
	// can be a list of possible tokens to be inserted/substitute or a pair of
	// words for unmerging/merging
	private String posSuggestion;
	private int affectedIndex;
	private double editDistance;
	private int frequency;

	public Suggestion(SuggestionType suggType, String[] tokenSuggestions, String posSuggestion, int affectedIndex,
			double editDistance, int frequency) {
		super();
		this.suggType = suggType;
		this.tokenSuggestions = tokenSuggestions;
		this.posSuggestion = posSuggestion;
		this.affectedIndex = affectedIndex;
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

	public int getAffectedIndex() {
		return affectedIndex;
	}

	public void setAffectedIndex(int affectedIndex) {
		this.affectedIndex = affectedIndex;
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

	public String getPosSuggestion() {
		return posSuggestion;
	}

	public void setPosSuggestion(String posSuggestion) {
		this.posSuggestion = posSuggestion;
	}
}
