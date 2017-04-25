package Stemmer.Model.AffixModules.Prefix.Submodules;

import Stemmer.Model.AffixModules.AbstractMorphoChange;
import Stemmer.Model.Stem;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class RemoveReduplication extends AbstractMorphoChange
{
	public Stem reduceStem(Stem stem)
	{
		Stem newStem = stem.cloneThis();
		String word = newStem.getStemString();
		if( word.contains("-") )
		{
			stem = wholeReduplication( newStem );
		} else {
			stem = partialReduplication( newStem );
		}
		return stem;
	}

	@Override
	public String applyFeature(String foundAffix) {
		return null;
	}

	public Stem wholeReduplication(Stem stem)
	{
		String word = stem.getStemString();
		String leftPart = "", rightPart = "";

		for( int i = 0; i < word.length(); i++ )
		{
			if( word.charAt(i) == '-' )
			{
				leftPart 	= word.substring(0, i);
				rightPart 	= word.substring(i + 1);
			}
		}
//		println("LP: " + leftPart + " RP: " + rightPart);
		/*
		 * Please transfer to applyFeature()
		 */
//		stem.setFeature( stem.getFeature() + "$" + leftPart);
		stem.addPrefix( "$" + leftPart );
		stem.setStemString( rightPart );
		return stem;
	}


	public Stem partialReduplication(Stem stem)
	{
		String word = stem.getStemString();
		String leftPart, rightPart, possibleRedup;

		for( int i = 2; i < ( word.length()/2 ); i++)
		{
			possibleRedup 	= word.substring(0, i);
			for( int j = i; j < word.length()/2; j++ )
			{
				leftPart 	= word.substring( j, j + possibleRedup.length() );
				if( possibleRedup.equalsIgnoreCase(leftPart) )
				{
					rightPart = word.substring(j);
//					stem.setFeature( ""+ stem.getFeature() + "$" + possibleRedup);
					stem.addPrefix( "$" + possibleRedup);
					stem.setStemString(rightPart);
					return stem;
				}
			}
		}
		// do something
		return stem;
	}

	/**
	 * Removes adjacent similar characters such as "aa" from "aalis", "uu" from "uunlad", etc.
	 * @param stem
	 * @return
	 */
	public Stem characterReduplication(Stem stem)
	{
		String word = stem.getStemString();
		String rightPart;
		Stem newStem = stem.cloneThis();

		return newStem;
	}

	public static class TestMe
	{
		public static void main(String[] args)
		{
			/* Create stem first */
			Stem stem = new Stem("hahatiin");
			RemoveReduplication redup = new RemoveReduplication();
			redup.reduceStem(stem);
		}
	}
}

///*
//	 * Reduplicates the cluster of consonants including the succeeding vowel of
//	 * the stem.
//	 */
//private void redupRule4() {
//	word = word.substring(3);
//}
//
//	/* If the first syllable of the root has a cluster of consonants, */
//	private void redupRule3() {
//		word = word.substring(2);
//	}
//
//	/*
//	 * In a two-syllable root, if the first syllable of the stem starts with a
//	 * consonant- vowel, the consonant and the succeeding vowel is reduplicated.
//	 */
//	private void redupRule2() {
//		word = word.substring(2);
//	}
//
//	/*
//	 * If the root of a two-syllable word begins with a vowel, the initial
//	 * letter is repeated.
//	 */
//	private void redupRule1() {
//		word = word.substring(1);
//	}
// */