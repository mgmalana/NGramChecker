package morphinas.Morphinas;

import morphinas.DataStructures.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by laurenztolentino on 05/31/2016.
 */
public class Main {

	// for printing running time
	long startTime, endTime;
	String addressPrefix = "/Users/laurenz/Developer/morphinas/morphinas.Morphinas/ReadFiles/";

	/* testHPOST Variations to load */
	final String testHPOSTuncleaned = "testHPOST-uncleaned.words";
	final String testHPOST 			= "testHPOST.words";
	final String morphRead 			= "morphRead.pinas";
	final String minitext			= "minitext.txt";
	final String readThisFile		= "correct_words.txt";

	public Main() throws Exception
	{
		this.startTime  = System.currentTimeMillis();
	}

	public void sampleLongRun() throws Exception
	{
		MorphPI mpi = new MorphPI(addressPrefix,"testHPOST.words");
		mpi.readFromFile();
		mpi.pullFeaturedResultsFromFile();
		endTime = System.currentTimeMillis();

		printElapsedTime(startTime, endTime);
	}

	/**
	 * Runs the program using updated methods.
	 * Updated methods include:
	 * - reading the file and breaking them into new lines as sentences.
	 * @throws Exception
	 */
	public void sampleLongRunSentences() throws Exception
	{
		ArrayList<Sentence> sentences;
//		MorphPI mpi = new MorphPI(addressPrefix, minitext );
		MorphPI mpi = new MorphPI(addressPrefix, testHPOST);
		mpi.readFromFile();
		sentences   = mpi.createSentences( mpi.pullContent() );
		mpi.featuredResultString( sentences );

//		/* for computing the elapsed time */
		endTime = System.currentTimeMillis();
		/* Print the elapsed time */
		printElapsedTime(startTime, endTime);
	}

	public void sampleLongRunSentencesLemma() throws Exception
	{
		ArrayList<Sentence> sentences;
		//testHPOST2.words
		MorphPI mpi = new MorphPI(addressPrefix,"minitext.txt");
		mpi.readFromFile();
		sentences   = mpi.createSentences(mpi.pullContent());
		mpi.lemmaResultString(sentences);

		endTime = System.currentTimeMillis();
		/* Print the elapsed time */
		printElapsedTime(startTime, endTime);
	}

	public void manoLongRun() throws Exception
	{
		/*
		MorphPI mpi = new MorphPI();
		* */
		MorphPI mpi = new MorphPI(addressPrefix, readThisFile);
		mpi.readFromFile();
		mpi.pullRootResultsFromFile();
		println("reached here.");
		endTime = System.currentTimeMillis();

		printElapsedTime(startTime, endTime);

	}

	public void
	sampleSingleRun(String sWord) throws Exception
	{
		MorphPI mpi = new MorphPI();
		mpi.analyzeWord(sWord);
	}

	/*
	 *
	 * MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN
	 *
	 */
	public static void main(String[] args) throws Exception
	{
		println("Hello world");

		Main m = new Main();
//		m.sampleLongRun();
//		m.sampleSingleRun("pinalalaro");
//		m.manoLongRun();
//		m.sampleLongRunSentencesLemma();
		m.sampleLongRunSentences();
	}

	/*
	* UTILITY
	* */

	public void printElapsedTime(long startTime, long endTime)
	{
		NumberFormat formatter = new DecimalFormat("#0.00000");
		println("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
	}

	public static void println(String input)
	{
		System.out.println("" + input);
	}

}
