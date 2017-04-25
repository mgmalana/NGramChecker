package Stemmer.Model;

import java.util.ArrayList;

/**
 * Created by laurenz on 22/03/2017.
 */
public class Sentence
{
	ArrayList<RootSet> rootSets;
	ArrayList<String> words;

	public Sentence()
	{
		rootSets = new ArrayList<>();
	}

	public ArrayList<RootSet> getRootSets() {
		return rootSets;
	}

	public void setRootSets(ArrayList<RootSet> rootSets) {
		this.rootSets = rootSets;
	}

	/**
	 * Adds a single rootSet into the list of rootSets in this sentence
	 * @param rootSet
	 * A single rootset (single combo of lemma + features).
	 */
	public void addRootSet(RootSet rootSet)
	{
		if( rootSets.size() == 0 || rootSets.isEmpty() || rootSets == null)
		{
			rootSets = new ArrayList<>();
		}
		else
		{
			rootSets.add( rootSet );
		}
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
}
