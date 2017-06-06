package morphinas.Stemmer;

import morphinas.Stemmer.Controller.MainController;

import static morphinas.Utility.print.*;
/**
 * Created by laurenz on 20/04/2017.
 */
public class Stemmer
{

	/**
	 * Will perform lemmatization process on a array of Strings (including non-alphabet)
	 * @param words
	 * String[] of words
	 * @return
	 * An array (String) of lemmas
	 */
	public String[] lemmatizeMultiple( String[] words )
	{
		MainController mc = new MainController( words );
		return mc.getLemmasArray();
	}

	/**
	 * Will perform lemmatization process on words in a string(including non-alphabet)
	 * @param words
	 * String of words
	 * @return
	 * A string of lemmas
	 */
	public String lemmatizeSentence(String words)
	{
		String[] arrayWords = words.split(" ");
		MainController mc = new MainController( arrayWords );
		String[] arrayResults = mc.getLemmasArray();
		return arrayToString(arrayResults);
	}

	private String arrayToString(String[] list){
		String temp = "";

		for (String word: list){
			temp += " " + word;
		}

		return temp;
	}


	/**
	 * Will perform featurization acquired during lemmatization process on an array of strings (including non-alphabets)
	 * @param words
	 * String of words
	 * @return
	 * An array (String) of features
	 */
	public String[] featurizeMultiple(String[] words)
	{
		MainController mc = new MainController( words );
		return mc.getFeaturesArray();
	}


	/**
	 * Perform lemmatization on a single word
	 * @param word
	 * Suspected inflected word
	 * @return
	 * Lemma of the input word.
	 * Will return the original input if the word is either a root word or unknown.
	 */
	public String lemmatizeSingle( String word )
	{
		MainController mc = new MainController( word );
		return mc.getLemma();
	}

	/**
	 * Acquire the features generated during lemmatization on a single word
	 * @param word
	 * Suspected inflected word
	 * @return
	 * Features of the input word.
	 * Will return the original input if the word is either a root word or unknown.
	 */
	public String featurizeSingle( String word )
	{
		MainController mc = new MainController( word );
		return mc.getFeatures();
	}

	/**
	 * For testing only.
	 * Runs test on a single word and an array of words
	 */
	public static class Test
	{
		public static void main( String[] args )
		{
			println("testing single word");
			testSingleWord();
			println("testing multiple words using String[] words: { sumama, sumpa, pagluto, pinagluluto, . }");
			testMultipleWords();
			println("testing multiple words using String words: Nasagi ng aso ang ulam na luto ko .");
			testOneStringMultipleWords();
		}

		private static void testSingleWord()
		{
			Stemmer stemmer = new Stemmer();

			String word = "makasaysayang";

			String result = stemmer.lemmatizeSingle( word );

			println("result: " + result);

		}

		private static void testMultipleWords()
		{
			Stemmer stemmer = new Stemmer();

			String[] words = { "sumama", "sumpa", "pagluto", "pinagluluto","."};
			String[] features;

			String[] result = stemmer.lemmatizeMultiple( words );

			for(int i = 0; i < result.length; i++)
			{
				println( result[i] );
			}
		}

		private static void testOneStringMultipleWords()
		{
			Stemmer stemmer = new Stemmer();
			String words = "Nasagi ng aso ang ulam na luto ko .";
			String result = stemmer.lemmatizeSentence(words);

			println(result);
		}
	}
}
