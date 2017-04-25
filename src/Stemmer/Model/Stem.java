package Stemmer.Model;

import java.util.ArrayList;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/08/2017.
 */
public final class Stem implements Cloneable
{

	private String stemString;
	private String affix;
	private String feature;
	private String rootWord;
	private String pathTaken;
	/* features per affix */
	private String prefixFeatures;
	private String infixFeatures;
	private String suffixFeatures;
	private String redupFeatures;
	private String combinedFeatures;

	private ArrayList<String> prefixes, infixes, suffixes;

	/**
	 * Create only once.
	 * (Usually before stemming procedures)
	 * @param stem
	 */
	public Stem(String stem)
	{
		this.stemString = stem;
		this.prefixes 	= new ArrayList<>();
		this.infixes 	= new ArrayList<>();
		this.suffixes	= new ArrayList<>();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                       FOR CLONING ONLY                        *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Stem cloneThis()
	{
		try
		{
			return (Stem) Stem.super.clone();
		} catch (CloneNotSupportedException e)
		{
			println(" ERROR: STEM CLONING FAILED HUHUBELLS ");
			e.printStackTrace();
		}
		return this;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *              Used to update the current ArrayList             *
	 *               of Prefixes, Infixes, and Suffixes              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public void addPrefix(String prefix)
	{
		if( this.prefixes == null || this.prefixes.size() == 0 ) {
			this.prefixes = new ArrayList<>();
		}
		this.prefixes.add( prefix );
	}

	public void addInfix(String infix)
	{
		if( this.infixes == null || this.infixes.size() == 0 ) {
			this.infixes = new ArrayList<>();
		}
		this.infixes.add( infix );
	}

	public void addSuffix(String suffix)
	{
		if( this.suffixes == null || this.suffixes.size() == 0 ) {
			this.infixes = new ArrayList<>();
		}
		this.suffixes.add( suffix );
	}

	public String combineAllFeatures()
	{
		String result = "";
		prefixes = removeDuplicateAffixesFromList( prefixes );
		infixes  = removeDuplicateAffixesFromList( infixes );
		suffixes = removeDuplicateAffixesFromList( suffixes );

		/* Add all prefixes first */
		for( String prefix: prefixes)
		{
			if( prefix.charAt(0) == '$' )
			{
				result += prefix;
			}
			else
			{
				result += "~" + prefix;
			}
		}
		/* Add all infixes next */
		for( String infix: infixes )
		{
			result += "@" + infix;
		}
		/* And finally, all suffixes */
		for( String suffix: suffixes )
		{
			result += "+" + suffix;
		}

		return result;
	}

	public ArrayList<String> removeDuplicateAffixesFromList(ArrayList<String> affixes)
	{
		ArrayList<String> reducedList 	= new ArrayList<>();
		Boolean exists 					= false;

		for( String affix : affixes )
		{
			if( reducedList.size() == 0 )
			{
				reducedList.add( affix );
			}
			else
			{
				for ( String newAffix : reducedList )
				{
					if ( affix.equalsIgnoreCase( newAffix ) )
					{
						exists = true;
						break;
					} else
					{
						exists = false;
					}
				}
				if ( !exists )
				{
					exists = false;
					reducedList.add( affix );
				}
			}
		}

		return reducedList;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *              Getters and Setters below this point             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public String getStemString() {
		return stemString;
	}

	public void setStemString(String stemString) {
		this.stemString = stemString;
	}

	public String getAffix() {
		return affix;
	}

	public void setAffix(String affix) {
		this.affix = affix;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getRootWord() {
		return rootWord;
	}

	public void setRootWord(String rootWord) {
		this.rootWord = rootWord;
	}

	public String getPathTaken() {
		return pathTaken;
	}

	public void setPathTaken(String pathTaken) {
		this.pathTaken = pathTaken;
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

	public String getPrefixFeatures() {
		return prefixFeatures;
	}

	public void setPrefixFeatures(String prefixFeatures) {
		this.prefixFeatures = prefixFeatures;
	}

	public String getInfixFeatures() {
		return infixFeatures;
	}

	public void setInfixFeatures(String infixFeatures) {
		this.infixFeatures = infixFeatures;
	}

	public String getSuffixFeatures() {
		return suffixFeatures;
	}

	public void setSuffixFeatures(String suffixFeatures) {
		this.suffixFeatures = suffixFeatures;
	}

	public String getRedupFeatures() {
		return redupFeatures;
	}

	public void setRedupFeatures(String redupFeatures) {
		this.redupFeatures = redupFeatures;
	}

	public String getCombinedFeatures() {
		return combineAllFeatures();
	}

	public void setCombinedFeatures(String combinedFeatures) {
		this.combinedFeatures = combinedFeatures;
	}
}
