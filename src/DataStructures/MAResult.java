package DataStructures;

import MorphAnalyzer.RewriteRule;
import MorphAnalyzer.WordSemantic;

/**
 * Created by laurenztolentino on 05/31/2016.
 */
public class MAResult {
	public String result;
	public double confidence;
	public WordSemantic semantic;
	public String prefix, suffix, infix, redup;
	public RewriteRule popRule, posRule, vowelRule;
	public MAResult(String result, double confidence) {
		this.result = result;
		this.confidence = confidence;
		this.semantic = null;
	}
	public MAResult(String result, WordSemantic semantic, double confidence) {
		this.result = result;
		this.confidence = confidence;
		this.semantic = semantic;
	}
	public String toString() {
		if (semantic == null)
			return result + ",null,: " + confidence +
					"," + prefix + "," + suffix + "," + infix + "," + redup +
					"," + popRule + "," + posRule + "," + vowelRule;
		else
			return result + "," + semantic.toString() + "," + confidence +
					"," + prefix + "," + suffix + "," + infix + "," + redup +
					"," + popRule + "," + posRule + "," + vowelRule;
	}
}