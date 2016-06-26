package optimization.models;

public class HybridNGram {

	String[] posTags;
	Integer[] posIDs;
	Boolean[] isHybrid;
	String[] nonHybridWords;
	int baseNGramFrequency;

	public HybridNGram(String posTagsAsString, Integer[] posIDs, String isHybridAsString, String nonHybridWordsAsString,
			int baseNGramFrequency) {
		this.posTags = posTagsAsString.split(" ");
		this.posIDs = posIDs;
		this.nonHybridWords = nonHybridWordsAsString.split(" ");
		String[] isHybridStringArr = isHybridAsString.split(" ");
		isHybrid = new Boolean[isHybridStringArr.length];
		for (int i = 0; i < isHybridStringArr.length; i++)
			isHybrid[i] = Boolean.parseBoolean(isHybridStringArr[i]);

		this.baseNGramFrequency = baseNGramFrequency;
	}

	public String[] getPosTags() {
		return posTags;
	}

	public void setPosTags(String[] posTags) {
		this.posTags = posTags;
	}

	public Integer[] getPosIDs() {
		return posIDs;
	}

	public void setPosIDs(Integer[] posIDs) {
		this.posIDs = posIDs;
	}

	public Boolean[] getIsHybrid() {
		return isHybrid;
	}

	public void setIsHybrid(Boolean[] isHybrid) {
		this.isHybrid = isHybrid;
	}

	public int getBaseNGramFrequency() {
		return baseNGramFrequency;
	}

	public void setBaseNGramFrequency(int baseNGramFrequency) {
		this.baseNGramFrequency = baseNGramFrequency;
	}

	public String[] getNonHybridWords() {
		return nonHybridWords;
	}

	public void setNonHybridWords(String[] nonHybridWords) {
		this.nonHybridWords = nonHybridWords;
	}
}
