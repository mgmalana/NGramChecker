package revised.model;

public class Suggestion {

	private int[] tokensAffected;
	private String suggestion;
	private double editDistance;

	public Suggestion(int[] tokensAffected, String suggestion, double editDistance) {
		this.tokensAffected = tokensAffected;
		this.suggestion = suggestion;
		this.editDistance = editDistance;
	}

	public int[] getTokensAffected() {
		return tokensAffected;
	}

	public void setTokensAffected(int[] tokensAffected) {
		this.tokensAffected = tokensAffected;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public double getEditDistance() {
		return editDistance;
	}

	public void setEditDistance(double editDistance) {
		this.editDistance = editDistance;
	}

}
