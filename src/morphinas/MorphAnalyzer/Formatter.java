package morphinas.MorphAnalyzer;

import morphinas.DataStructures.Affix;
import morphinas.DataStructures.Word;

import java.util.ArrayList;

/**
 * Created by laurenztolentino on 05/16/16.
 */
public class Formatter
{

	Word word;

	ArrayList<Affix> prefixes;// = word.getPrefixes();
	ArrayList<Affix> suffixes;// = word.getSuffixes();
	ArrayList<Affix> infixes;//  = word.getInfixes();
	String rootWord;// 		  = word.getRootWord();

	String bracketedResult;

	/**
	 * Only does magic on a single word.
	 * @param word
	 */
	public Formatter(Word word)
	{
		this.word 		= word;
		this.prefixes	= word.getPrefixes();
		this.suffixes	= word.getSuffixes();
		this.infixes 	= word.getInfixes();
		this.rootWord 	= word.getRootWord();
	}

	/**
	 * Removes dashes and extra spaces on a single word.
	 * @param input
	 * @return
	 */
	public static String removeNonLetters(String input)
	{
		char[] nonLetters 	= { '-', ' ' };
		String result 		= "";
		boolean willAdd 	= false;

		/* when it's a single hyphen */
		if( input.length() < 2 )
		{
			return input;
		}

		for( int i = 0; i < input.length(); i++ )
		{

			for( int j = 0; j < nonLetters.length; j++)
			{
				if( input.charAt(i) != nonLetters[j]) {
					willAdd = true;
				} else {
					willAdd = false;
					break;
				}
			}

			if( willAdd ) {
				result = result + input.charAt(i);
			}
		}

		return result;
	}

	/**
	 * Unfortunately, just adds an extra space after the end of the rootword.
	 * @return
	 */
	public String generateRootResult()
	{
		String result = "";
		result = result + rootWord + " ";

		return result;
	}

	/**
	 * Somehow returns what a bracketed result would look like.
	 * @return
	 */
	public String generateDashedResult()
	{
		String result = "";

		for( int i = 0; i < this.prefixes.size(); i++ )
		{
			result = result + "" + this.prefixes.get(i).getAffixBrackets();
			if( this.prefixes.get(i).getAffixBrackets() == "" ) {
				println("it's null bruh");
			}
		}

		result = result + "[" + rootWord + "]";

		for( int i = 0; i < this.suffixes.size(); i++ )
		{
			result = result + "" + this.suffixes.get(i).getAffixBrackets();
		}

		this.bracketedResult = result;
		return result;
	}

	/**
	 * Required by Gramatika
	 * @laurenz
	 * @return
	 */
	public String generateFormattedResult()
	{
		this.removeDuplicateAffixes();
		ArrayList<Affix> gPrefix = reverseAffixOrder(this.prefixes);
		ArrayList<Affix> gInfix  = reverseAffixOrder(this.infixes);
		ArrayList<Affix> gSuffix = reverseAffixOrder(this.suffixes);
		AffixBreakdown ab 		 = new AffixBreakdown();
		String rootWithInfix;
		String result	 		 = "";



		if( gInfix.size() > 0)
		{
			try {
				rootWithInfix = this.infixedRootWord(word.getOriginalWord(),word.getRootWord(), gInfix.get(0).getAffix());
			} catch (Exception e) {
				rootWithInfix = word.getRootWord();
			}
		} else {
			rootWithInfix = word.getRootWord();
		}


		if( longestCanonicalPrefixLength() > 4 )
		{
			Affix longPrefix = longestCanonicalPrefix();

			result = result + ab.convertPrefix( longPrefix.getAffix().toString() );
		}
		else {
			// cycle through all the prefixes first
			for( int i = 0; i < this.prefixes.size(); i++)
			{
				result = result + "~" + gPrefix.get(i).getAffix();
			}
		}

		// insert the rootword
		result = result + "#" + rootWithInfix;

		// cycle through all the suffixes as the last
		for( int i = 0; i < gSuffix.size(); i++)
		{
			result = result + "+" + gSuffix.get(i).getAffix();
		}
		return result;
	}

	/**
	 * Reverses the order of a selected ArrayList of Affix.
	 * Created because scanning of affixes start from the end of the word.
	 * Used in generated formatted result :)
	 * @return
	 * An ArrayList<Affix> with the same content but in a reversed order.
	 */
	public static ArrayList<Affix> reverseAffixOrder(ArrayList<Affix> reverseMe)
	{
		ArrayList<Affix> result = new ArrayList<Affix>();

		for( int i = reverseMe.size() - 1; i >= 0; i-- )
		{
			Affix tempAffix = reverseMe.get(i);
			result.add(tempAffix);
		}

		return result;
	}

	public String generateFeaturesResult()
	{

//		println("prefixes size: " + this.prefixes.size() );
		this.removeDuplicateAffixes();
		ArrayList<Affix> gPrefix = reverseAffixOrder(this.prefixes);
		ArrayList<Affix> gInfix  = reverseAffixOrder(this.infixes);
		ArrayList<Affix> gSuffix = reverseAffixOrder(this.suffixes);
		AffixBreakdown ab 		 = new AffixBreakdown();
		String rootWithInfix;
		String result	 		 = "";



		if( gInfix.size() > 0 || gInfix != null)
		{
			try {
				rootWithInfix = this.infixedRootWord(word.getOriginalWord(),word.getRootWord(), gInfix.get(0).getAffix());
			} catch (Exception e) {
				rootWithInfix = word.getRootWord();
			}
		} else {
			rootWithInfix = word.getRootWord();
		}


		if( longestCanonicalPrefixLength() > 4 )
		{
			Affix longPrefix = longestCanonicalPrefix();

			result = result + ab.convertPrefix( longPrefix.getAffix().toString() );
		}
		else
		{
			// cycle through all the prefixes first
			for( int i = 0; i < this.prefixes.size(); i++)
			{
				result = result + "~" + gPrefix.get(i).getAffix();
			}
		}

		// cycle through all the suffixes as the last
		for( int i = 0; i < gSuffix.size(); i++)
		{
			result = result + "+" + gSuffix.get(i).getAffix();
		}
		return result;
	}

	public String generateBracketedResult()
	{
		String result = "";

		// Creates "[prefix"
		for( int i = 0; i < this.prefixes.size(); i++ )
		{
			result = result + "" + this.prefixes.get(i).getAffixBrackets();
			// Adds [ before the root to signify the opening bracket of the suffix]
			if(i == (this.suffixes.size() - 1) )
			{
				for( int k = 0; k < this.suffixes.size(); k++ )
				{
					result = result + "[";
				}
			}
		}
		// creates [<rootword>]
		result = result + "[ROOT:" + this.rootWord + "]";
		// Adds ] after the root to signify the closing bracket of the prefix
		for( int k = 0; k < prefixes.size(); k++ )
		{
			result = result + "]";
		}
		// Creates "suffix]"
		for( int i = 0; i < this.suffixes.size(); i++ )
		{
			result = result + "" + this.suffixes.get(i).getAffixBrackets();
		}

		// Creates [infix|infix|..]
		for( int i = 0; i < this.infixes.size(); i++ )
		{
			if ( i == 0 )
			{
				result = result + "[<INF>";
			}
			// Creates "(infix)(infix)(..)"
			result = result + this.infixes.get(i).getAffixBrackets();
			// Adds the final ]
			if (i == (this.infixes.size() - 1) )
			{
				result = result + "]";
			}
		}

		this.bracketedResult = result;
		return result;
	}

	public void printFormattedResult()
	{
		println( this.generateFormattedResult() );
	}

	public void printFeaturesResult()
	{
		println( this.generateFeaturesResult() );
	}

	public void printBracketedResult()
	{
		println("");
		println( generateBracketedResult() );
	}

	public void printLongestOnly()
	{
		println("");
		println( "[" + this.longestCanonicalPrefix().getAffix() + "[" + this.rootWord + "]");
	}

	public void printWordContentDetailed()
	{

		// Initiate removing duplicate affixes
		this.removeDuplicateAffixes();


		println("Printing contents of the Word object/class.");
		if( word.finalContentsReady(true) == true)
		{
			println("Original: " + word.getOriginalWord() + "| Root: " + this.rootWord);
			println("Number of Affixes in the original word: " + word.getAffixCount());
			println("Prefixes: " + this.prefixes.size() + " | Infixes: " + this.infixes.size() + " | Suffixes: " + this.suffixes.size());
			println("Printing Prefixes:");
			for(int i = 0; i < this.prefixes.size(); i++)
			{
				println( "PFX: " + this.prefixes.get(i).getAffix() );
			}

			println("Printing Infixes:");
			for(int i = 1; i < this.infixes.size(); i++)
			{
				println( "IFX: " + this.infixes.get(i).getAffix() );
			}

			println("Printing Suffixes:");
			for(int i = 0; i < this.suffixes.size(); i++)
			{
				println( "SFX: " + this.suffixes.get(i).getAffix() );
			}
		}
	}

	/**
	 * Keeps the code shorter.
	 * Simply removes all duplicates from all types of affixes.
	 */
	public void removeDuplicateAffixes()
	{
		infixes = cleanInfix(infixes);
		this.prefixes = removeDuplicateAffix(prefixes);
		this.infixes  = removeDuplicateAffix(infixes);
		this.suffixes = removeDuplicateAffix(suffixes);
	}

	/**
	 * Removes duplicates on the selected affix by comparing affix.getAffix();
	 *
	 * @param affix
	 * Could be prefixes, suffixes, or infixes [vbt]
	 * @return
	 * An ArrayList<Affix> of affixes with duplicates removed
	 */
	public ArrayList<Affix> removeDuplicateAffix(ArrayList<Affix> affix)
	{
		ArrayList<Affix> tempAffix = affix;
		ArrayList<Affix> resultAffix = new ArrayList<Affix>();
		boolean skip = false;
		int appearances = 0;

//		for( int i = 0; i < affix.size(); i++)
//		{
//			skip = false;
//			for(int j = 0; j < affix.size(); j++)
//			{
//				println("    Comparing " + tempAffix.get(i).getAffix() + " and " + affix.get(j).getAffix() );
//				if( skip == true )
//				{
//					break;
//				}
//				if( tempAffix.get(i).getAffix().equalsIgnoreCase("") )
//				{
//					skip = true;
//				}
//				if( tempAffix.get(i).getAffix().compareTo( affix.get(j).getAffix() ) != 0 && !skip)
//				{
//					println("    Adding2   " + tempAffix.get(i).getAffix() + " and " + affix.get(j).getAffix() );
//					skip = true;
//					resultAffix.add(tempAffix.get(i));
//				}
//			}
//		}



		for ( int i = 0; i < affix.size(); i++ )
		{
			skip = false;
			for( int j = 0; j < tempAffix.size(); j++ )
			{
				if( skip == true ) {
					break;
				}
				else if( tempAffix.get(i).getAffix().equalsIgnoreCase("") && !skip )
				{
					skip = true;
				}
				if( tempAffix.get(i).getAffix().equalsIgnoreCase( affix.get(j).getAffix() ) && !skip)
				{
					skip = true;
					appearances++;
				}
			}
			if( appearances == 1) {
				resultAffix.add( tempAffix.get(i) );
			}
		}

		return resultAffix;
	}

	/**
	 * Since infixes generated by the original system are rules and not the actual infix itself,
	 * this method cleans the generated result and just outputs the infix.
	 * @param origInfix
	 * @return
	 */
	private ArrayList<Affix> cleanInfix(ArrayList<Affix> origInfix)
	{

		ArrayList<Affix> resultAffixes = new ArrayList<Affix>();
		Affix tempAffix, resultAffix;
		String resultingAffix = "";
		String singleAffix;

		for( int i = 0; i < origInfix.size(); i++ )
		{
			tempAffix = origInfix.get(i);
			singleAffix = tempAffix.getAffix();

			for( int k = 0; k < singleAffix.length(); k++ )
			{
				if( singleAffix.charAt(k) == '-' )
				{
					break;
				} else {
					resultingAffix = resultingAffix + singleAffix.charAt(k);
				}
			}

			if( !resultingAffix.equalsIgnoreCase("") )
			{
//				println("---------- resultingAffix: " + resultingAffix);
				resultAffix = new Affix(resultingAffix, "infix");
				resultAffixes.add(resultAffix);
			}

		}


		return resultAffixes;
	}

	/**
	 * Puts the @<infix>@ inside the parameter originalWord.
	 * Use this for showing the breakdown that includes an infix in it.
	 * @param originalWord
	 * @param rootWord
	 * @param infix
	 * @return
	 */
	private String infixedRootWord(String originalWord, String rootWord, String infix)
	{
		String left;         // left side before the infix
		String right;		 // right side after the infix
		String result = "";	 // resulting string
		int cutAt 	  = 1; 	 // start of cuting

		// pinuntahan
		for( int i = 0; i < rootWord.length(); i++ )
		{
			left  = rootWord.substring(0,i);
			right = rootWord.substring(i);

//			println("--------left side: " + left);
//			println("--------right side: " + right);

			result = left + infix + right;
//			println("------------resulting: " + result);
			if( originalWord.contains(result) )
			{
//				println("----------------- exists in the original word: " + result);
				result = left + "@" + infix + "@" + right;
				break;
			}
		}

		return result;
	}

	/**
	 * Finds the longest prefix on the Word's list of prefixes;
	 * Does not find the most
	 * @return
	 * The longest prefix by comparing length
	 */
	private Affix longestCanonicalPrefix()
	{
		Affix currLong 		= this.prefixes.get(0); // longest found affix
		int currLongLength  = currLong.getAffix().length();

		for( int i = 1; i < prefixes.size(); i++ )
		{
			Affix temp = this.prefixes.get(i);
			int prefixLength	= temp.getAffix().length();

			if( currLongLength < prefixLength )
			{
				currLong = temp;
			}
		}

		return currLong;
	}

	/**
	 * Looks for the longest existing canonical prefix and returns it's length for you.
	 * Because you know, we got it all for you.
	 * @return
	 * integer containing the longest canonical prefix length.
	 */
	private int longestCanonicalPrefixLength()
	{
		Affix currLong; //= this.longestCanonicalPrefix();

		int length;

		if( prefixes.size() < 1 ) {
			length = 0;
		} else {
			currLong = this.longestCanonicalPrefix();
			length   = currLong.getAffix().length();
		}

		return length;
	}

	/**
	 * Method used to check manually a word if it exists in the local Database.
	 * @param word
	 * @return
	 * True if it is part of the database. False if it belongs in outer space.
	 */
	public static boolean checkIfRootViaDB(String word)
	{
		DBLexiconSQL lex 	= new DBLexiconSQL();
		boolean isRoot 		= false;

		try {
			if( lex.lookup(word) )
			{
				isRoot = true;
			}
		} catch (Exception e) {
			println("checkIfRootViaDB encountered a MySQL Problem. #DBisNotLoveAnymore");
			e.printStackTrace();
		}

		return isRoot;
	}

	/**
	 * Prints all found affixes. Used for debugging only and not for actual production.
	 * @param affixes
	 * The Array List of affixes you want to print.
	 */
	public static void printAllAffixes(ArrayList<Affix> affixes)
	{
		println("Printing all affixes: ");
		for(int i = 0; i < affixes.size(); i++)
		{
			print(affixes.get(i).getAffix() + " | ");
		}
		println("");
	}

	public static void print(String in) { System.out.print("" + in); }

	public static void println(String in)
	{
		System.out.println("" + in);
	}
}
