package v1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LemmaCorpusBuilder {

	static HashMap<String, Lemma> lemmaLibrary = new HashMap<>();
	
	public static void addLemmas(long sentenceNumber, String[] tokens, String[] tags, String[] lemmas){
		for(int i = 0 ; i < tokens.length ; i ++){
			Lemma currLemma = getOrAddLemmaFromHashMap(lemmas[i]);
			
			String surfaceWord = tokens[i];
			String pos = tags[i];
			String lem = lemmas[i];
			long sentenceLocation = 0;
			long wordSentenceLocation = i;
			
			Lemma prevLemma = null;
			Lemma nextLemma = null;
			
			if(i != 0)
				prevLemma = getOrAddLemmaFromHashMap(lemmas[i-1]);
			
			if(i != tokens.length-1)
				nextLemma = getOrAddLemmaFromHashMap(lemmas[i+1]);
			
			currLemma.addWord(new Word(surfaceWord, pos, sentenceLocation, wordSentenceLocation,
					prevLemma, nextLemma));
		}
	}
	
	private static Lemma getOrAddLemmaFromHashMap(String lemmaKey){
		Lemma l = lemmaLibrary.get(lemmaKey);
		if(l == null){
			l = new Lemma(lemmaKey);
			lemmaLibrary.put(lemmaKey, l);
		}
		return l;		
	}
	
	@SuppressWarnings("rawtypes")
	public static void printLemmas(){
		Iterator it = lemmaLibrary.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Lemma l = (Lemma) pair.getValue();
	        System.out.println("Lemma: " + pair.getKey() + " = " + l.getLemma());
	        
	        for(Word w : l.getWords()){
	        	System.out.println("Desc: " + w.getPosTag() + " " + w.getSurfaceWord() + " " +
	        			w.getSentenceIndex() + " " + w.getWordInSentenceIndex());
	        	if(w.getPrevLemma()!= null)
	        		System.out.println("Prev: " + w.getPrevLemma().getLemma());
	        	if(w.getNextLemma() != null)
	        		System.out.println("Next: " + w.getNextLemma().getLemma());
	        }
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
}
