package morphinas.Morphinas;

import morphinas.MorphAnalyzer.WordSemantic;

import java.util.Vector;

/**
 * Created by laurenztolentino on 05/31/2016.
 */
public class WordPair {
	public String morphed;
	public Vector<WordSemantic> semanticInformations;
	public String root;
	public WordPair(String morphed, String root)
	{
		this.morphed = morphed;
		this.root = root;
		this.semanticInformations = null;
	}
	public WordPair(String morphed, String root, Vector<WordSemantic> semanticInformations) {
		this.morphed = morphed;
		this.root = root;
		this.semanticInformations = semanticInformations;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (semanticInformations != null) {
			for(int i=0;i<semanticInformations.size()-1;i++)
				sb.append(semanticInformations.elementAt(i).toString() + ",");
			sb.append(semanticInformations.elementAt(semanticInformations.size()-1).toString());
		}
		return morphed + "-->" + root + ":" + sb.toString();
	}
}
