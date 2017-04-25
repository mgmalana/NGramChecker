package DataStructures;

import java.util.ArrayList;

/**
 * Created by laurenztolentino on 06/16/2016.
 */
public class Sentence
{
	int newCount;
	int origCount = 0;
	public ArrayList<Word> words;

	public Sentence()
	{}

	public Sentence(ArrayList<Word> words)
	{
		this.words = words;
	}

	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}

	public void addWords(Word word)
	{
		if( isEmpty() > -1 )
		{
			this.words.add(word);
		}
		else {
			println("WARNING: Will not add words. Sentence.words is NULL!!!");
		}
	}

	/**
	 * Checks if the words in this sentence is null.
	 * @return
	 * -1 if the words is null <br>
	 * 0 if words exists but size is zero <br>
	 * 1 if there are existing words
	 */
	public int isEmpty()
	{
		if( this.words == null )
		{
			return -1;
		}
		else if( this.words.size() == 0 )
		{
			return 0;
		}

		return 1;
	}

	/**
	 * Set the original number of words (before being processed/formatted)
	 * @param origCount
	 */
	public void setOrigCount(int origCount)
	{
		this.origCount = origCount;
	}

	/**
	 *
	 * @return
	 * Integer of the word.length of the original unedited word count
	 */
	public int getOrigCount()
	{
		return this.origCount;
	}

	/**
	 * Word count of the
	 * @return
	 */
	public int getWordCount()
	{
		newCount = words.size();
		return words.size();
	}

	public ArrayList<Word> getWords()
	{
		return this.words;
	}

	public void printString()
	{
		for( Word word : words )
		{
			print( word.getOriginalWord() + " ");
		}
	}

	public String stringSentence()
	{
		String result = "";
		for( Word word : words )
		{
			result = result + word.getOriginalWord() + " ";
		}

		return result;
	}

	/*
	*  UTILITY CODE
	* */
	public static void print(Object in)
	{
		System.out.print("" + in.toString() );
	}

	public static void println(Object input)
	{
		System.out.println("" + input.toString() );
	}
}
