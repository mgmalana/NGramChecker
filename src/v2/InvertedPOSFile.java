package v2;

import java.util.ArrayList;
import java.util.List;

public class InvertedPOSFile {

	String pos;
	List<InvertedPOSFileEntry> ifEntries;

	public InvertedPOSFile(String pos) {
		this.pos = pos;
		ifEntries = new ArrayList<>();
	}

	public InvertedPOSFile(String pos, List<InvertedPOSFileEntry> ifEntries) {
		this.pos = pos;
		this.ifEntries = ifEntries;
	}

	public String getPOS() {
		return pos;
	}

	public void setPOS(String pos) {
		this.pos = pos;
	}

	public void addIFEntry(InvertedPOSFileEntry ifEntry) {
		ifEntries.add(ifEntry);
	}

	public List<InvertedPOSFileEntry> getIfEntries() {
		return ifEntries;
	}

	public void setIfEntries(List<InvertedPOSFileEntry> ifEntries) {
		this.ifEntries = ifEntries;
	}
}
