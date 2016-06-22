package optimization.models;

public class HybridNGram {

	String[] posTags;
	String[] isHybrid;
	int baseNGramFrequency;

	public HybridNGram(String posTagsAsString, String isHybridAsString, int baseNGramFrequency) {
		this.posTags = posTagsAsString.split(" ");
		this.isHybrid = isHybridAsString.split(" ");
	}

	public String[] getPosTags() {
		return posTags;
	}

	public void setPosTags(String[] posTags) {
		this.posTags = posTags;
	}

	public String[] getIsHybrid() {
		return isHybrid;
	}

	public void setIsHybrid(String[] isHybrid) {
		this.isHybrid = isHybrid;
	}

	public int getBaseNGramFrequency() {
		return baseNGramFrequency;
	}

	public void setBaseNGramFrequency(int baseNGramFrequency) {
		this.baseNGramFrequency = baseNGramFrequency;
	}
}
