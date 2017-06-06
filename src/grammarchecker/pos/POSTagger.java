package grammarchecker.pos;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class POSTagger {

	private String taggerModelFilename;
	private MaxentTagger tagger;

	public POSTagger(String taggerModelFilename) {
		tagger = new MaxentTagger(taggerModelFilename);
	}

	public TaggedSentence tagSentence(String sentence) {
		return TaggedSentenceService.getTaggedSentence(tagger.tagTokenizedString(sentence));
	}
}
