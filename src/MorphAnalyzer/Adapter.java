package MorphAnalyzer;
import DataStructures.MAResult;

public class Adapter {
	
	MorphLearnerRedup mpl = new MorphLearnerRedup();
    WordsLoader training = new WordsLoader(WordsLoader.TRAINING);
    MAResult result; 
    
    public Adapter(String input) throws Exception
    {
    	// Make sure input is in lower case
    	input = input.toLowerCase();
    	
    	
    }
    
    
	
}
