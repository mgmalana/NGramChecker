package old;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
	
	static String correctText = "Narito ang kopya ng drowing .";
	static String correctPOS = "PRL   DTC   NNC   CCB   NNC   PMP";
	static String correctLemma = "dito ang kopya ng drowing . ";
	// PRL - Location  (Panlunan)
	// DTC - Determiner for Common Noun
	// NNC - Common Noun
	// CCB - Conjunction
	// PMP - Period
	static String incorrectText = "Nadito ang kopya ng drowing . ";
	static String incorrectPOS = "?   DTC   NNC   CCB   NNC   PMP";
	static String incorrectLemma = "dito ang kopya ng drowing . ";
	public static void main(String[] args){
		String[] correctTokens = correctText.split(" ");
		String[] correctTags = correctPOS.split("   ");
		String[] correctLemmas = correctLemma.split(" ");
		
		LemmaCorpusBuilder.addLemmas(0, correctTokens, correctTags, correctLemmas);
		
		String[] incorrectTokens = incorrectText.split(" ");
		String[] incorrectTags = incorrectPOS.split("   ");
		String[] incorrectLemmas = incorrectLemma.split(" ");
		
		HashMap<String, Lemma> lemmas = new HashMap<>();
		
		for(int i = 0 ; i < incorrectTokens.length; i++){
			Lemma currLemma = getOrAddLemmaFromHashMap(lemmas, incorrectLemmas[i]);
			
			String surfaceWord = incorrectTokens[i];
			String pos = incorrectTags[i];	
			long sentenceLocation = 0;
			long wordSentenceLocation = i;
			
			Lemma prevLemma = null;
			Lemma nextLemma = null;
			
			if(i != 0)
				prevLemma = getOrAddLemmaFromHashMap(lemmas, incorrectLemmas[i-1]);
			
			if(i != incorrectTokens.length-1)
				nextLemma = getOrAddLemmaFromHashMap(lemmas, incorrectLemmas[i+1]);
			
			currLemma.addWord(new Word(surfaceWord, pos, sentenceLocation, wordSentenceLocation,
					prevLemma, nextLemma));
		}
		
		printLemmas(lemmas);
	}
	private static Lemma getOrAddLemmaFromHashMap(HashMap<String, Lemma> lemmas, String lemmaKey){
		Lemma l = lemmas.get(lemmaKey);
		if(l == null){
			l = new Lemma(lemmaKey);
			lemmas.put(lemmaKey, l);
		}
		return l;		
	}
	
	@SuppressWarnings("rawtypes")
	public static void printLemmas(HashMap<String, Lemma> lemmas){
		Iterator it = lemmas.entrySet().iterator();
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
