package DataStructures;

import java.util.ArrayList;

public class Word {

	private MAResult maresult;
	private String originalWord;
	private String rootWord;
	private int affixCount = 0;
	private boolean isRoot = false;
	private int frequencyCount = 0;
	//	private Affix affixes;
	private ArrayList<Affix> prefixes;
	private ArrayList<Affix> suffixes;
	private ArrayList<Affix> infixes;

	private ArrayList<Affix> redups;

	private String bracketedResult = "";
	private String dashedResult    = "";
	

	/**
	 * Creates a new Word object with complete parameters
	 * @param maresult
	 * @param originalWord
	 * @param rootWord
	 * @param affixCount
	 * @param prefixes
	 * @param infixes
	 * @param suffixes
	 */
	public Word(MAResult maresult, String originalWord, String rootWord, int affixCount, ArrayList<Affix> prefixes, ArrayList<Affix> infixes, ArrayList<Affix> suffixes) 
	{
		super();
		this.maresult 		= maresult;
		this.originalWord 	= originalWord;
		this.rootWord 		= rootWord;
		this.affixCount 	= affixCount;		
		this.prefixes 		= prefixes;
		this.infixes 		= infixes;
		this.suffixes 		= suffixes;
	}
	
	/**
	 * Creates a new Word object when other information has not been generated yet.
	 * @param originalWord
	 */
	public Word(String originalWord)
	{
		this.originalWord 	= originalWord;		
	}

	/**
	 * Tells a lost soul that this Word has all of the contents a word needs.
	 * @param willContinue
	 * will still return true
	 * @return
	 * True if all of the Word's content exist and false if not.
	 */
	public boolean finalContentsReady(boolean willContinue)
	{
		int contentCount = 0;
		boolean decision = false;
		
		//if root and input are already the same, the rootword might be blank and there's no need 
		if( originalWord.equals(rootWord) == true) {
			decision = true;
		}		
		else 
		{
			if( this.maresult == null ) {
//				println("[WORD] maresult is null");
				decision = false;
			}
			if( this.originalWord == null ) {
//				println("[WORD] originalWord is null");
				decision = false;
			}
			if( this.rootWord == null ) {
//				println("[WORD] rootWord is null");
				decision = false;
			}
			if( this.affixCount == 0 ) {
//				println("[WORD] affixCount is null");
				decision = false;
			}
			if( this.prefixes.isEmpty() == true) {
//				println("[WORD] prefixes is null");
				decision = false;
			}
		}
		// 
		if( willContinue == true ) {
			return true;
		}
		
		return decision;
	}


	/**
	 * Gets the MAResult object coming from the MorphLearnerInfix
	 * @return
	 */
	public MAResult getMaresult()
	{
		return maresult;
	}

	/**
	 * Sets the MAResult object from MorphLearnerInfix
	 * @param maresult
	 */
	public void setMaresult(MAResult maresult) 
	{
		this.maresult = maresult;
	}

	/**
	 * Returns the original inflicted word
	 * @return
	 */
	public String getOriginalWord() 
	{
		return originalWord;
	}

	/**
	 * Set the original inflicted word
	 * @param originalWord
	 */
	public void setOriginalWord(String originalWord) 
	{
		this.originalWord = originalWord;
	}

	
	public String getRootWord() {
		return rootWord;
	}


	public void setRootWord(String rootWord) {
		this.rootWord = rootWord;
	}


	public int getAffixCount() {
		return affixCount;
	}


	public void setAffixCount(int affixCount) {
		this.affixCount = affixCount;
	}
 
	public ArrayList<Affix> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(ArrayList<Affix> prefixes) {
		this.prefixes = prefixes;
	}

	public ArrayList<Affix> getSuffixes() {
		return suffixes;
	}

	public void setSuffixes(ArrayList<Affix> suffixes) {
		this.suffixes = suffixes;
	}

	public ArrayList<Affix> getInfixes() {
		return infixes;
	}

	public void setInfixes(ArrayList<Affix> infixes) {
		this.infixes = infixes;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public ArrayList<Affix> getRedups() {
		return redups;
	}

	public void setRedups(ArrayList<Affix> redups) {
		this.redups = redups;
	}
	
	private static void println(String input)
	{
		System.out.println("" + input);
	}
	
	private static void print(String input)
	{
		System.out.print("" + input);
	}

	/**
	 * Gets a formatted affix String
	 * @return
	 * It looks like this -> [pinagpa[pag[pa[liban]
	 */
	public String getBracketedResult() 
	{
		return bracketedResult;
	}

	public String getDashedResult() 
	{
		return dashedResult;
	}
	
}
