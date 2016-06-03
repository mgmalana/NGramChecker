package v4.models;

public class SuggestionToken {

	private String word;
	private String pos;
	private int index; // for addition this will be the index of where the token
						// should be inserted
	private boolean isPOSGeneralized;
	private double editDistance;
	private SuggestionType suggType;

	public SuggestionToken(String word, int index, double editDistance, SuggestionType suggType) {
		this.index = index;
		this.word = word;
		this.isPOSGeneralized = false;
		this.editDistance = editDistance;
		this.suggType = suggType;
	}

	public SuggestionToken(String word, int index, double editDistance, String pos, SuggestionType suggType) {
		this.index = index;
		this.word = word;
		this.pos = pos;
		this.isPOSGeneralized = true;
		this.editDistance = editDistance;
		this.suggType = suggType;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isPOSGeneralized() {
		return isPOSGeneralized;
	}

	public void setPOSGeneralized(boolean isPOSGeneralized) {
		this.isPOSGeneralized = isPOSGeneralized;
	}

	public double getEditDistance() {
		return editDistance;
	}

	public void setEditDistance(double editDistance) {
		this.editDistance = editDistance;
	}

	public SuggestionType getSuggType() {
		return suggType;
	}

	public void setSuggType(SuggestionType suggType) {
		this.suggType = suggType;
	}
}
