package v2;

public class NGram {

	InvertedPOSFileEntry[] tokens;

	public NGram(InvertedPOSFileEntry[] tokens) {
		this.tokens = tokens;
	}

	public InvertedPOSFileEntry[] getTokens() {
		return tokens;
	}

	public void setTokens(InvertedPOSFileEntry[] tokens) {
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (InvertedPOSFileEntry e : tokens) {
			b.append(e.getLemma() + " " + e.getPos() + " ");
		}
		return b.toString();
	}

	@Override
	public boolean equals(Object n) {
		if (n instanceof NGram) {
			NGram toCompare = (NGram) n;
			if (this.toString().equals(toCompare.toString()))
				return true;
		}
		return false;
	}

}
