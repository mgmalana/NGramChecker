package revised.model;

public class SuggestionToken {

	private String word;
	private String pos;
	private int index; // for addition this will be the index of where the token
						// should be inserted
	private boolean isWord;

	public SuggestionToken(String text, int index, boolean isWord) {
		this.index = index;
		if (isWord)
			this.word = text;
		else
			this.pos = text;
		this.isWord = isWord;
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

	public boolean isWord() {
		return isWord;
	}

	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}
}
