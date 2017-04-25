package Morphinas.Testing.Experiments;

import MorphAnalyzer.DBLexiconSQL;

/**
 * Created by laurenztolentino on 09/21/2016.
 */
public class Reduplication_Exp
{

	public Reduplication_Exp(){}

	public void runMe(String word) throws Exception
	{
		/* Variables */
		String left, right;
		int leftLength, rightLength;
		WordPart wp 	= new WordPart();
		int wordLength 	= word.length();

		leftLength = 1;


		for( int i = 0; i < wordLength; i++ )
		{
			rightLength = leftLength;
			left  		= word.substring(0, leftLength);
			right 		= word.substring(rightLength);

			wp.left 	= left;
			wp.right 	= right;

			println("Left: " + left);
			println("Right: " + right);

			wp.findCommonSubstring();

			println("");
			leftLength++;
		}
	}

	public String wordSplitter()
	{
		return "";
	}

	public static void main(String[] args) throws Exception
	{
		/* Word to be tested */
		String word = "haluhalong";

		Reduplication_Exp re = new Reduplication_Exp();
		/* run me yey */
		re.runMe(word);

//		println(word.substring(0, 2));
//		println(word.substring(2));
	}

	public static void println(Object output)
	{
		System.out.println(output.toString() + "" );
	}

	class WordPart
	{
		public String left;
		public String right;
		public String rootWord;

		public WordPart(){}

		public WordPart(String left, String right)
		{
			this.left  = left;
			this.right = right;
		}

		public String findCommonSubstring()
		{
			String result  	= "";
			String left   	= this.left;
			String right  	= this.right;
			int leftLength  = left.length();
			int rightLength = right.length();
			/* Used for comparing */
			String newRight;

			if(leftLength <= rightLength)
			{
				newRight = right.substring(0, leftLength);

				println(left + " <> " + newRight);

				try
				{
					if( newRight.charAt(0) == left.charAt(0) )
					{
						boolean isRoot = DBLexiconSQL.staticLookup(newRight);
						if( isRoot )
						{
							this.rootWord = newRight;
						}

					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			return result;
		}

		public void compareByLetter(String left, String right)
		{
			String commonSubstring  = "";
			char changePoint;
			int length = left.length();

			if( left.length() != right.length() )
			{
				println("Left & Right part are not of the same length");
			}
			else
			{

			}


		}

		public String getRootWord() {
			return rootWord;
		}

		public String getLeft() {
			return left;
		}

		public void setLeft(String left) {
			this.left = left;
		}

		public String getRight() {
			return right;
		}

		public void setRight(String right) {
			this.right = right;
		}
	}

}
