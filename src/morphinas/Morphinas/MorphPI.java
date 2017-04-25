package morphinas.Morphinas;

import morphinas.DataStructures.MAResult;
import morphinas.DataStructures.Sentence;
import morphinas.DataStructures.Word;
import morphinas.MorphAnalyzer.*;
import morphinas.MorphAnalyzer.MorphLearnerRedup;

import java.util.ArrayList;

/**
 * Created by laurenztolentino on 05/30/2016.
 */
public class MorphPI
{
	MorphLearnerRedup mpl = new MorphLearnerRedup();
//	WordsLoader training = new WordsLoader(WordsLoader.TRAINING);
	IOHandler gPush;
//	Enter filename here
	String address;
	String fileName;


	String inputSentence = "";
	String input = "";

	public MorphPI() throws Exception
	{
		println("Please don't forget to set file address (complete address and folder name) and file name");
		println("Address: /Users/morphinasdev/Eclipse/workspace/morphinas.Morphinas/src/");
		println("fileName: testHPOST.words");
	}

	public MorphPI(String address, String fileName) throws Exception
	{
		this.address  = address;
		this.fileName = fileName;
		IOHandler ioh = new IOHandler(address, fileName);
	}

	/**
	 * Starts file reading given an address and filename
	 * @param address
	 * Ex: /Users/.../something/
	 * (Please do not forget the '/' at the end
	 * @param fileName
	 * Ex: file.txt
	 * (Please include filename)
	 */
	public void readFromFile(String address, String fileName)
	{
		this.address  = address;
		this.fileName = fileName;

		readFromFile();
	}

	public void readFromFile()
	{
		if( this.fileName != null & this.address != null )
		{
			println("Reading from " + fileName);
			IOHandler ioh 	= new IOHandler(this.address, this.fileName);
			this.gPush 		= ioh;
		} else {
			println("File name and/or address is null.");
		}

	}

	public String[] pullContent() throws Exception
	{
		return gPush.readFromFile();
	}

	public void pushLine(String input) throws Exception
	{

	}

	public void analyzeWord(String sWord) throws Exception
	{
		MorphLearnerRedup mpl = new MorphLearnerRedup();
		String input = Formatter.removeNonLetters(sWord);


		Formatter fm;
		WordPair wp;
		input = input.toLowerCase();
//		println("Finding root of: " + input);
		// String ng result only
		String root = "";
		root = mpl.analyzeMultipleMod(input).result;
		// Result using MAResult
		MAResult maresult = mpl.analyzeMultipleMod(input); // Not working properly #why
		Word word = mpl.getWordObject();


		fm = new Formatter(word);
		fm.printWordContentDetailed();
		// fm.printBracketedResult();
		fm.printFormattedResult();
		fm.printFeaturesResult();
		println("");
//		AffixBreakdown ab = new AffixBreakdown();

		try {
			fm.printLongestOnly();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			println("whoopsies. Didn't find the longest ");
		}
	}

	public String generateRootResults(String[] wordsList) throws Exception
	{
		String result = "";

		Formatter fm;
		Word word;
//		Open up the database
		DBLexiconSQL t 		= new DBLexiconSQL();

		for( int i = 0; i < wordsList.length; i++ )
		{
			String single = wordsList[i];
			single = single.toLowerCase();
			println("analyzing: " + single);
			mpl.analyzeMultipleMod(single);
			word = mpl.getWordObject();
			fm = new Formatter(word);
			result = result + fm.generateRootResult() + "\n";
		}

		IOHandler ioh = new IOHandler();
		ioh.printToTxtFileRoot("manoLemma", result);

		return result;
	}

	public ArrayList<Sentence> createSentences( String[] sentenceStringList ) throws Exception
	{
		/* result to be returned */
		ArrayList<Sentence> sentences = new ArrayList<>();
		/* morphinas data structures */
		ArrayList<Word> words = new ArrayList<>();
		/* temp variables */
		Sentence sentence;
		String replaceUnderscore;
		String[] sWords;
		Word word;
		/* iterate the entire sentence array */
		for( String sSentence: sentenceStringList)
		{
			/* removes '_' in a sentence and splits them */
			replaceUnderscore = sSentence.replace("_", " ");
			/* split it kapag may espasyo */
			sWords = replaceUnderscore.split(" ");
			/* iterate ang panibagong listahan ng mga salita */
			for( String sWord: sWords)
			{
				word = new Word(sWord);
				words.add(word);
			}
			/* i-updeyt ang panibagong sentence */
			sentence = new Sentence();
			sentence.setWords( words );
			sentences.add(sentence);
			words = new ArrayList<>();
		}

		/* finally return it with love */
		return sentences;
	}

	/**
	 * Breaks down wordsList into sentences. <br>
	 * Ex: ["hello", "this", "is", "a", "sentence", "."], [ "this", "is", "another", "one". "."]
	 * WordsList
	 * @param wordsList
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Sentence> createSentencesDONOTUSE(String[] wordsList) throws Exception
	{
		ArrayList<Sentence> sentences = new ArrayList<>();
		ArrayList<Word> tempWords 	  = new ArrayList<>();
		Sentence sentence;
		Word word;


		/* temp variable */
		String single;
		for (String aWordsList : wordsList)
		{
			println("ayaw :( ");

//			single = aWordsList;
//			word = new Word(single);
//			tempWords.add(word);
//
//			sentence = new Sentence(tempWords);
//			sentence.setOrigCount(sentence.getWordCount());
//			sentences.add(sentence);


//			if( i < wordsList.length - 1)
//			{
//				nextWord =  wordsList[i+1].equals(wordsList[i+1].toLowerCase());
//			}
//			else
//			{
//				nextWord = false;
//			}
//			if( single.equalsIgnoreCase(".\n") || single.matches(".\n") || single.equalsIgnoreCase(".\n ") )
//			{
//				println("there has been a split done. ");
//				single = ".";
//			}
//			/* Will create a sentence once a '.' is found. */
//			if( ( single.equalsIgnoreCase(".") || single.equalsIgnoreCase("?") || single.equalsIgnoreCase("!") )
//					&& nextWord )
//			{
//				println("hello world");
//				sentence = new Sentence(tempWords);
//				sentence.setOrigCount( sentence.getWordCount() );
//				sentences.add(sentence);
//				/* Clears the tempWords object. */
//				tempWords = new ArrayList<>();
//			}
		}
		return sentences;
	}

	public void printSentencesTest(ArrayList<Sentence> sentences)
	{
		for( Sentence sentence: sentences )
		{
			for(Word word: sentence.getWords() )
			{
				print( word.getOriginalWord() + " ");
			}
			println("");
		}
	}

	/*mpl.analyzeMultipleMod( sentence.getWords().get(w).getOriginalWord() );
	word  	= mpl.getWordObject();
	fm 		= new Formatter( word );
	fm.generateFeaturesResult();
	fm.printFeaturesResult();*/

	/**
	 * USE THIS!!
	 * This is how this method works:
	 * Will process every sentence (in an ArrayList format).
	 * For each sentence, the sentence's words are checked (including punctuation marks).
	 * The checking includes: punctuation marks, *capitalized first-letter-of-the-word, and non-alphanumericals.
	 *
	 * *All capitalized first-letter-of-the-word are considered as nouns (except for the first word in the sentence).
	 * @param sentences
	 * @return
	 * @throws Exception
	 */
	public String featuredResultString(ArrayList<Sentence> sentences) throws Exception
	{
		/* Result to be returned */
		String result = "";
		/* Basic data structures objects */
//		Sentence sentence;
		Formatter fm;
		Word word;
		/* Call the database (also opens a connection) */
		DBLexiconSQL db = new DBLexiconSQL();
		/* Temp variables */
		ArrayList<Word> words;
		String single, tempSingle;

		ArrayList<Sentence> tempSentences = sentences;



		/* Iterate all existing sentences */
		for( Sentence sentence: sentences )
		{
//			sentence  = sentences.get(s);
			words     = sentence.getWords();
			/* Iterate all words in a sentence */
			for( int w = 0; w < words.size(); w++ )
			{
				single 				= words.get(w).getOriginalWord();
				single				= Formatter.removeNonLetters(single);
				tempSingle 			= single;
				boolean hasNonAlpha = single.matches("^.*[^a-zA-Z].*$");
				boolean isNumbers 	= single.matches("^.*[0-9].*$");
				/* For the first word in the sentence */
				if( w == 0 )
				{
					result = result + ":FS";
				}
				/*
					Start processing the features.
				*/
				/* When the current element is a non-alphabetical character */
				if( single.equals(" ") || single.equals("") || single.charAt(0) == ' ')
				{
					//do nothing
				}
				else if( isNumbers )
				{
					result = result + "*" + single + " ";
				}
				/*
				 * Fix me
				 * - not all '.' means a new line
				 *
				 */
				else if( hasNonAlpha && !isNumbers )
				{
					/* If it is a punctuation mark */
					if( w == (words.size()-1) )
					{
						result = result +"#" + single + " \n";
					}
					else if ( single.length() > 1 )
					{
						result = result + "*" + single + " ";
					}
					else
					{
						result = result + "#" + single + " ";
					}
				}
				/* When the first letter is capital (except for the first word in the sentence. */
				else if( w > 1 && !single.equals(single.toLowerCase()) )
				{
					single = single.toLowerCase();
					if( db.lookup(single) ) {
						result = result + ":F#" + single + " ";
					}
					else {
						result = result + ":F*" + single + " ";
					}
				}
				/* All words with 3 chars or less is already a root word (Bonus, 2003) */
				else if( single.length() <= 3 && !single.equals(""))
				{
					if( single.charAt(0) == '.')
					{
						single = single.toLowerCase();
						result = result + "*" + single + " ";
					}
					/* if the word starts with a '.' such as '.s'*/
					else if( single.length() > 1 && single.charAt(0) == '.')
					{
						single = single.toLowerCase();
						result = result + "*" + single + " ";
					}
					else if( single.length() == 1 && words.get( w + 1 ).getOriginalWord().charAt(0) == '.' &&  words.get( w + 1 ).getOriginalWord().length() > 1 )
					{
						if( !single.equals( single.toLowerCase() ) )
						{
							single = single.toLowerCase();
							if( w > 1)
							{
								result = result + ":F*" + single + " ";
							}
							else
							{
								result = result + "*" + single + " ";
							}
						}
						else
						{
							single = single.toLowerCase();
							result = result + "*" + single + " ";
						}
					}
					else {
						single = single.toLowerCase();
						result = result + "#" + single + " ";
					}

				}
				/* If not mofo */
				else
				{
					single = Formatter.removeNonLetters(single);
					single = single.toLowerCase();
					/* When the first letter is capital (except for the first word in the sentence. */
					/*if( w > 1 && !single.equals(single.toLowerCase()) )
					{
						single = single.toLowerCase();
						*//*result = result + ":F";*//*
						if( db.lookup( single ) )
						{
							result = result + ":F#" + single + " ";
						}
						else {
							result = result + ":F*" + single + " ";
						}
					}*/
					/* If the word is already a root word */
					if( db.lookup( single ) )
					{
						result = result + "#" + single + " ";
					}
					/* When the word does not belong inside the database */
					else
					{
						single = single.toLowerCase();
						single = Formatter.removeNonLetters(single);

						mpl.globalPrefix = "";
						mpl.globalSuffix = "";
						mpl.analyzeMultipleMod(single.toLowerCase());

						word = mpl.getWordObject();
						fm   = new Formatter(word);

						tempSingle = fm.generateFeaturesResult();

						if( !fm.generateFeaturesResult().equalsIgnoreCase(""))
						{
							result = result + tempSingle + " ";
							if( tempSingle.equalsIgnoreCase("") || tempSingle.equalsIgnoreCase(" ") || tempSingle == null )
							{
								result = result + "*" + single + " ";
							}
						}
						else
						{
							result = result + "*" + single + " ";
						}
					}
				}
			}
		}

		println(result);
		IOHandler ioh = new IOHandler();
		ioh.printToTxtFile(result);
		return result;
	}

	/**
	 * USE THIS to get only lemmas/roots
	 * @param sentences
	 * @return
	 * @throws Exception
	 */
	public String lemmaResultString(ArrayList<Sentence> sentences) throws Exception
	{
		/* Result to be returned */
		String result = "";

		/* Basic data structures objects */
		Formatter fm;
		Word word;
		/* Call the database (also opens a connection) */
		DBLexiconSQL db = new DBLexiconSQL();
		/* Temp variables */
		ArrayList<Word> words;
		String single, tempSingle;

		/* Iterate all existing sentences */
		for( Sentence sentence: sentences )
		{
			//sentence  = sentences.get(s);
			words     = sentence.getWords();
			/* Iterate all words in a sentence */
			for( int w = 0; w < words.size(); w++ )
			{
				single 				= words.get(w).getOriginalWord();
				single				= Formatter.removeNonLetters(single);
				tempSingle 			= single;
				boolean hasNonAlpha = single.matches("^.*[^a-zA-Z].*$");
				boolean isNumbers 	= single.matches("^.*[0-9].*$");

				/*
					Start processing the features.
				*/
				/* When the current element is a non-alphabetical character */
				if( single.equals(" ") || single.equals("") || single.charAt(0) == ' ')
				{
					//do nothing
				}
				else if( isNumbers )
				{
					result = result + single + " ";
				}
				/*
				 * Fix me
				 * - not all '.' means a new line
				 *
				 */
				else if( hasNonAlpha && !isNumbers )
				{
					/* If it is a punctuation mark */
					if( w == (words.size()-1) )
					{
						result = result + single + " \n";
					}
					else if ( single.length() > 1 )
					{
						result = result + single + " ";
					}
					else
					{
						result = result + single + " ";
					}
				}
				/* When the first letter is capital (except for the first word in the sentence. */
				else if( w > 1 && !single.equals(single.toLowerCase()) )
				{
					single = single.toLowerCase();
					result = result + single + " ";
				}
				/* All words with 3 chars or less is already a root word (Bonus, 2003) */
				else if( single.length() <= 3 && !single.equals(""))
				{
					if( single.charAt(0) == '.')
					{
						single = single.toLowerCase();
						result = result + single + " ";
					}
					/* if the word starts with a '.' such as '.s'*/
					else if( single.length() > 1 && single.charAt(0) == '.')
					{
						single = single.toLowerCase();
						result = result + single + " ";
					}
					else if( single.length() == 1 && words.get( w + 1 ).getOriginalWord().charAt(0) == '.' &&  words.get( w + 1 ).getOriginalWord().length() > 1 )
					{
						if( !single.equals( single.toLowerCase() ) )
						{
							single = single.toLowerCase();
							result = result + single + " ";
						}
						else
						{
							single = single.toLowerCase();
							result = result + single + " ";
						}
					}
					else {
						single = single.toLowerCase();
						result = result + single + " ";
					}

				}
				/* If not mofo */
				else
				{
					single = Formatter.removeNonLetters(single);
					single = single.toLowerCase();
					/* If the word is already a root word */
					if( db.lookup( single ) )
					{
						result = result + single + " ";
					}
					/* When the word does not belong inside the database */
					else
					{
						single = single.toLowerCase();
						single = Formatter.removeNonLetters(single);

						mpl.globalPrefix = "";
						mpl.globalSuffix = "";
						mpl.analyzeMultipleMod(single.toLowerCase());

						word = mpl.getWordObject();
						fm   = new Formatter(word);
						tempSingle = word.getRootWord();
//						tempSingle = fm.generateRootResult();

						if( !fm.generateFeaturesResult().equalsIgnoreCase(""))
						{
							result = result + tempSingle + " ";
							if( tempSingle.equalsIgnoreCase("") || tempSingle.equalsIgnoreCase(" ") || tempSingle == null )
							{
								result = result + single + " ";
							}
						}
						else
						{
							result = result + single + " ";
						}
					}
				}
			}
		}

		println(result);
		IOHandler ioh = new IOHandler();
		ioh.printToTxtFile(result);
		return result;
	}
	public String generateFeaturedResult(String[] wordsList) throws Exception
	{
		String result = "";

		boolean skip 		= false;
		Formatter fm;
		Word word;
//		Open up the database
		DBLexiconSQL t 		= new DBLexiconSQL();

		for( int i = 0; i < wordsList.length; i++ )
		{
//			println("wordList"+"[" + i + "]: " + wordsList[i]);
			String single = wordsList[i];
			single = single.toLowerCase();

//			Checks if the word is either the first word in the entire input or first word of the sentence.
			if( i == 0)
			{
				result  = result + ":FS";
				skip	= true;
			}
			if( i == wordsList.length - 1) {
				skip = true;
			}
//			If the word's first letter is capital
			if( !single.equals(wordsList[i]) && !skip)
			{
				result 	= result + ":F*" + single + " ";
				skip 	= true;
			}
//			Start
			if( !skip )
			{
				if( single.equalsIgnoreCase(".") && !skip)
				{
					result = result + "#" + single + "\n" + ":FS";
					skip   = true;
				}
				else if( t.lookup(single) && !skip )
				{
					result 	= result + "#" + single + " ";
					skip 	= true;
				}
				else {
					single = Formatter.removeNonLetters(single);
					skip = false;

					// because all tagalog words are already root when <= 3
					if( single.length() > 3 && !skip)
					{
						mpl.globalPrefix = "";
						mpl.globalSuffix = "";
						mpl.analyzeMultipleMod(single);
						word = mpl.getWordObject();
						fm   = new Formatter(word);

						if( !fm.generateFeaturesResult().equalsIgnoreCase(""))
						{
							result = result + fm.generateFeaturesResult() + " ";
						} else {
							result = result + "*" + single + " ";
						}

					} else {
						result = result + "#" + single + " ";
					}
				}
			}
			skip = false;
		}

		println("");
//		println(result);

		IOHandler ioh = new IOHandler();
		ioh.printToTxtFile(result);
		return result;
	}

	public String pullRootResultsFromFile() throws Exception
	{
		String result = "";

		if( address.equalsIgnoreCase("") || fileName.equalsIgnoreCase("") )
		{
			println("Will not pull until address and fileName is not blank.");
			System.exit(0);
		}

//		Read from the file sent to IOHandler through gPush
		String[] wordsList 	= gPush.readFromFile();

		result = generateRootResults(wordsList);

		return result;
	}


	public String pullFeaturedResultsFromFile() throws Exception
	{
		String result = "";

		if( address.equalsIgnoreCase("") || fileName.equalsIgnoreCase("") )
		{
			println("Will not pull until address and fileName is not blank.");
			 System.exit(0);
		}

//		Read from the file sent to IOHandler through gPush
		String[] wordsList 	= gPush.readFromFile();

		result = generateFeaturedResult(wordsList);

		return result;
	}

//	public String startIt() throws Exception
//	{

//		String[] wordsList = tm.readFromFile();
//		boolean skip = false;
//		String result = "";
//		MAResult maresult;
//		Formatter fm;
//		Word word;
//
//		DBLexiconSQL t = new DBLexiconSQL();
//
//		for( int i = 0; i < wordsList.length; i++ )
//		{
////			println("wordList"+"[" + i + "]: " + wordsList[i]);
//			String single = wordsList[i];
//			single = single.toLowerCase();
//			single = Formatter.removeNonLetters(single);
//
//			if( single.equalsIgnoreCase(".") )
//			{
//				result = result + "#" + single + "\n";
//				skip   = true;
//			}
//
//			else if( t.lookup(single) && !skip )
//			{
//				result 	= result + "#" + single + " ";
//				skip 	= true;
//			} else {
//				skip = false;
//
//				// because all tagalog words are already root when <= 3
//				if( single.length() > 3 && !skip)
//				{
//					mpl.globalPrefix = "";
//					mpl.globalSuffix = "";
//					mpl.analyzeMultipleMod(single);
//					word = mpl.getWordObject();
//					fm   = new Formatter(word);
//
//					if( !fm.generateFeaturesResult().equalsIgnoreCase(""))
//					{
//						result = result + fm.generateFeaturesResult() + " ";
//					} else {
//						result = result + "*" + single + " ";
//					}
//
//				} else {
//					result = result + "*" + single + " ";
//				}
//			}
//
//
//
//
//		}
//
//		println("");
//		println(result);
//
//
//
//		return result;
//	}



	public static void main(String[] args) throws Exception
	{
//
		ArrayList<Sentence> sentences;
//		String testMe = "Ito ay isang halimbawa ng isang pangungusap . Ito ay pangalawang pangugusap . Leche , gumagana ba talaga ito ?";
		MorphPI mpi = new MorphPI();
//
//		String[] splitMe = testMe.split(" ");
//		sentences = mpi.createSentences(splitMe);
//		mpi.featuredResultString(sentences);
//
//		String Test = ".s";
//		boolean hasNonAlpha = Test.matches("^.*[^a-zA-Z].*$");
//		boolean isNumbers 	= Test.matches("^.*[0-9].*$");

//		println("hasNonAlpha: " + hasNonAlpha);
//		println("isNumbers: " + isNumbers);

		String test = "abc .\n talo . \n";
		String[] testList = test.split(" ");
		sentences = mpi.createSentences(testList);

		for( int i = 0; i < sentences.size(); i++ )
		{
			Sentence sentence = sentences.get(i);
			for( int k = 0; k < sentence.getWordCount(); k++ )
			{
				Word word   = sentence.getWords().get(k);
				String temp = word.getOriginalWord();
				if( temp.equals(".\n") ) {
					temp = ".";
				}
				print(temp + " ");
			}


		}

	}

	public static void print(Object input) { System.out.print("" + input.toString()); }
	public static void println(Object input)
	{
		System.out.println("" + input.toString());
	}

}
