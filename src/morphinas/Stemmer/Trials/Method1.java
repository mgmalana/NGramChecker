package morphinas.Stemmer.Trials;


import java.util.ArrayList;
import static morphinas.Utility.print.*;
/**
 * Created by laurenz on 27/01/2017.
 */
public class Method1 implements Runnable
{

	/* Stemming Variables */
	private final static String _LEFT = "left";
	private final static String _RIGHT = "right";
	/* canonical affixes */
	private String[] storedPrefixes = { "pinag", "ka" };
	private String[] storedInfixes 	= { "in", "um" };
	private String[] storedSuffixes = { "in", "an" };
	private String[] storedRoots 	= { "kain", "ka"};
	/* found roots through stemming */
	public static ArrayList<String> foundRoots = new ArrayList<>();
	/* controlling variables */
	private boolean mustStop		= false;
	private boolean donePrefix		= false;
	private boolean doneSuffix		= false;
	private boolean doneInfix 		= false;
	private int repeats 			= 0;
	/* Thread variables */
	private Thread thread;
	private String threadName;

	/* constructor variables and direction */
	String direction;
	Stem stem;

	public Method1()
	{
		println("Running Method1...");
	}

	public Method1(String name, Stem stem, String direction)
	{
		println("Running Method1 with threadName declared");
		this.threadName = name;
		this.stem 		= stem;
		this.direction 	= direction;
	}

	/**
	 * Stemming without direction (default left)
	 * @param stem
	 * word to be stemmed
	 * @return
	 * stemmed word that is found in the dictionary
	 */
	public String performBasicStemming(String stem)
	{
		println("current stem: " + stem);

		if( isRoot(stem) )
		{
			foundRoots.add(stem);
			return stem;
		}
		else
		{
			if( donePrefix == false )
			{
				donePrefix = true;
				performBasicStemming( rmvPrefix(stem));
			}
			else if( doneSuffix == false )
			{
				doneSuffix = true;
				performBasicStemming( rmvSuffix(stem));
			}
			else
			{
				donePrefix = false;
				doneSuffix = false;

				if( repeats < 5 ) {
					repeats++;
					performBasicStemming( stem );
				}
				println("Did not succeed");
			}

		}

		return stem;
	}

	public String performDirectedStemming(Stem stem, String direction)
	{
		println("current stem: " + stem.getStem() );

		if( isRoot(stem.getStem()) )
		{
			this.foundRoots.add( stem.getStem() );
			println("Updating foundRoots with " + stem.getStem() );
			return stem.getStem();
		}
		else
		{
			if( direction.equalsIgnoreCase(_LEFT) )
			{
				if( stem.isChkPrefix() == false )
				{
					stem.setChkPrefix(true);
					stem.setStem( rmvPrefix( stem.getStem() ) );
					println("1: " + stem.getStem());
					performDirectedStemming( stem, _LEFT );
				}
				else if( stem.isChkSuffix() == false )
				{
					stem.setChkSuffix(true);
					stem.setStem( rmvSuffix(stem.getStem()));
					println("2: " + stem.getStem());
					performDirectedStemming( stem, _LEFT );
				}
			}
			else if( direction.equals(_RIGHT) )
			{
				if( stem.isChkSuffix() == false )
				{
					stem.setChkSuffix(true);
					stem.setStem( rmvSuffix(stem.getStem()));
					performDirectedStemming( stem, _RIGHT );
				}
				else if( stem.isChkPrefix() == false )
				{
					stem.setChkPrefix(true);
					stem.setStem( rmvPrefix(stem.getStem()));
					performDirectedStemming( stem, _RIGHT );
				}
			}
			else {
				stem.setChkPrefix(false);
				stem.setChkSuffix(false);

				if( repeats < 5 ) {
					println("repeats: " + repeats);
					repeats++;
					performDirectedStemming( stem, direction );
				}
				println("Did not succeed");
			}

		}

		return "NONE FOUND";
	}

	public String rmvPrefix(String stem)
	{
		String result = stem;
		String pfxCompare;
		int pfxLen;

		println("stem: " + stem);
		for ( String prefix: storedPrefixes)
		{
			pfxLen = prefix.length();
			println("pfxLen: " + pfxLen);
			pfxCompare = stem.substring(0, pfxLen);

			if( pfxCompare.equalsIgnoreCase(prefix) )
			{
				println("removed " + prefix);
				return stem.substring(pfxLen);
			}
		}

		return result;
	}

	public String rmvSuffix(String stem)
	{
		String result = stem;
		String sfxCompare;
		int sfxLen;

		for( String suffix: storedSuffixes)
		{

			sfxLen = suffix.length();
			sfxCompare = stem.substring( stem.length() - sfxLen );
			if( sfxCompare.equalsIgnoreCase(suffix) )
			{
				println("removed " + suffix);
				return stem.substring(0, stem.length()-sfxLen);
			}
		}

		return result;
	}

	public String rmvInfix(String stem)
	{
		String result = stem;

		return result;
	}

	public boolean isRoot(String stem)
	{
		for(String roots: storedRoots )
		{
			if( roots.equalsIgnoreCase(stem) )
			{
				println("found match");
				return true;
			}

		}
		return false;
	}



	/* Threading area */

	public void run()
	{
		println("Running " + this.threadName);
		try
		{
			println("performDirectedStemming: " + this.direction);
			performDirectedStemming(stem, direction);
			for(String el: foundRoots )
			{
				println("Found: " + el);
			}
			thread.wait();
		} catch (Exception e)
		{
			println(this.threadName + " stopped working.");
		}
		println(this.threadName + " has exited.");
	}

	public void start()
	{
		println("Starting " + this.threadName);
		if ( thread == null )
		{
			thread = new Thread(this, threadName);
			thread.start();
		}
	}

	public static class Stem
	{
		String stem;
		ArrayList<String> prefixes;
		ArrayList<String> infixes;
		ArrayList<String> suffixes;
		boolean chkPrefix = false;
		boolean chkSuffix = false;

		public Stem(String stem)
		{
			this.stem = stem;
		}

		/* generated getters and setters */

		public String getStem() {
			return stem;
		}

		public void setStem(String stem) {
			this.stem = stem;
		}

		public ArrayList<String> getPrefixes() {
			return prefixes;
		}

		public void setPrefixes(ArrayList<String> prefixes) {
			this.prefixes = prefixes;
		}

		public ArrayList<String> getInfixes() {
			return infixes;
		}

		public void setInfixes(ArrayList<String> infixes) {
			this.infixes = infixes;
		}

		public ArrayList<String> getSuffixes() {
			return suffixes;
		}

		public void setSuffixes(ArrayList<String> suffixes) {
			this.suffixes = suffixes;
		}

		public boolean isChkPrefix() {
			return chkPrefix;
		}

		public void setChkPrefix(boolean chkPrefix) {
			this.chkPrefix = chkPrefix;
		}

		public boolean isChkSuffix() {
			return chkSuffix;
		}

		public void setChkSuffix(boolean chkSuffix) {
			this.chkSuffix = chkSuffix;
		}

	}

	public static class TestMethod1
	{

		Method1 m1;

		TestMethod1(Method1 m1)
		{
			this.m1 = m1;
		}

		public static void main(String[] args)
		{
			Method1 m1 		= new Method1();
			TestMethod1 tm 	= new TestMethod1(m1);
			String test = "pinagkainan";

			tm.test2(test);
//			tm.testWordObject();
		}

		/**
		 * Runs a Test using directionless stemming (default left)
		 * @param test
		 * Word to be stemmed
		 */
		public void test0(String test)
		{
			m1.performBasicStemming(test);

			println("Printing found rootwords: ");
			try
			{
				for(String found: foundRoots )
				{
					println(found);
				}
			} catch ( Exception e)
			{
				println("There were no found roots to be printed. ");
			}
		}

		/**
		 * Runs 2 separate directed stemming: performs left-side stemming then right-side stemming
		 * @param test
		 * Word to be stemmed
		 */
		public void test1(String test)
		{
			Method1 m1 	= new Method1();
			Stem stem	= new Stem(test);

			println("Starting Left");
			m1.performDirectedStemming(stem, _LEFT);

			println("Starting Right");
			m1.performDirectedStemming(stem, _RIGHT);
			println("Found roots: " + foundRoots.size());
			try
			{
				for(String found: foundRoots )
				{
					println(found);
				}
			} catch ( Exception e)
			{
				println("There were no found roots to be printed. ");
			}

		}

		/**
		 * Runs a Test wherein threading is implemented.
		 * With first thread running P-first and second thread running S-first
		 * @param test
		 */
		public void test2(String test)
		{
			Stem stem = new Stem(test);
			Method1 m1 = new Method1("Thread-1", stem, _LEFT);
			m1.start();
			Method1 m2 = new Method1("Thread-2", stem, _RIGHT);
			m2.start();

		}

		/**
		 * Runs a single thread only (left directed)
		 * @param test
		 */
		public void test3(String test)
		{
			Stem stem = new Stem(test);
			Method1 m1 = new Method1("Thread-1", stem, _LEFT);
			m1.start();

			println("");
			for(String e: m1.foundRoots )
			{
				println("Found: " + e);
			}
		}

		public void testWordObject()
		{
			Stem stem = new Stem("pinagkainan");
			println( "testingWordObject: " + stem.getStem() );
		}
	}
}
