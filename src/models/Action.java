package models;

public class Action {

	private int[] tokensAffected;
	private String possibleChange;

	public Action(int[] tokensAffected, String possibleChange) {
		super();
		this.tokensAffected = tokensAffected;
		this.possibleChange = possibleChange;
	}

	public int[] getTokensAffected() {
		return tokensAffected;
	}

	public void setTokensAffected(int[] tokensAffected) {
		this.tokensAffected = tokensAffected;
	}

	public String getPossibleChange() {
		return possibleChange;
	}

	public void setPossibleChange(String possibleChange) {
		this.possibleChange = possibleChange;
	}

}
