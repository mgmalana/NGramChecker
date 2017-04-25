package morphinas.Stemmer.Model.AffixModules.Suffix.Submodules;

import morphinas.Stemmer.Model.AffixModules.AbstractMorphoChange;
import morphinas.Stemmer.Model.AffixModules.AffixList;
import morphinas.Stemmer.Model.DBHandler;
import morphinas.Stemmer.Model.Stem;
import static morphinas.Utility.print.*;

/**
 * Created by laurenz on 02/03/2017.
 */
public class ConvertPhonemeChanges extends AbstractMorphoChange
{
	/* Database for lookups */
	DBHandler dbHandler;
	/* List of possible phoneme change suffixes */
	String[] phonemeChangeSuffixes = { "ran", "uan" };
	char[] possibleConstReplacements = { 'd' };
	char[] possibleVowelReplacements = { 'o' };

	public ConvertPhonemeChanges()
	{
		dbHandler = new DBHandler();
	}

	/**
	 * This extends RemoveCommonSuffix
	 * @param stem
	 * @return a reduced stem
	 */
	@Override
	public Stem reduceStem(Stem stem)
	{
		/* Original word */
		String word = stem.getStemString();
		/* Word Parts */
		String leftPart, rightPart, combinedPart;
		char origChar, newChar;
		char[] replaceWith = possibleConstReplacements;
		/* start reducing stem */

		for( int k = 0; k < phonemeChangeSuffixes.length; k++ )
		{
			/* Checks if the latter half of the word contains possible phoneme change suffixes */
			if( word.substring( (word.length() / 2) - 1 ).contains( phonemeChangeSuffixes[k] ) )
			{
				/* if the suffix in the list matches the one in the word being stemmed */
				rightPart = word.substring( word.length() - phonemeChangeSuffixes[k].length() );
				if( phonemeChangeSuffixes[k].equalsIgnoreCase( rightPart ) )
				{
					leftPart  = word.substring( 0, ( word.length() - phonemeChangeSuffixes[k].length()) );
					/* check if it's a vowel/consonant phoneme change */
					if( isVowel(rightPart.charAt(0)) )
					{
						replaceWith = possibleVowelReplacements;
					} // else, default is consonants.
					/* replace the suffix with a phoneme change */
					for(int i = 0; i < replaceWith.length; i++ )
					{
						rightPart 	 = "" + replaceWith[i];
						combinedPart = leftPart + rightPart;
						/* Check the modified word in the db. Will update the Stem() if the mod word exists. */
						if( dbHandler.lookup( combinedPart ) )
						{
							stem.setStemString( combinedPart );
//							stem.setSuffixFeatures( stem.getSuffixFeatures() + applyFeature(phonemeChangeSuffixes[k]) );
							stem.addSuffix( phonemeChangeSuffixes[k] );
						}
					}
				}
			}
		}
		/* Unreachable return? Maybe. Hotel? Trivago. */
		return stem;
	}

	public boolean isVowel(char letter)
	{
		char[] vowels = AffixList.getVowels();
		for( int i = 0; i < vowels.length; i++ )
		{
			if( letter == vowels[i] ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String applyFeature(String foundAffix)
	{
		return "+" + foundAffix;
	}

	public static class Test
	{
		public static void main(String[] args)
		{
			ConvertPhonemeChanges cpc 	= new ConvertPhonemeChanges();
			String word 				= "duguan";
			Stem stem 				 	= new Stem( word );

			 stem = cpc.reduceStem( stem );
			 println("Reduced Suffix PhonemeChanges  Stem: " + stem.getStemString() );
		}
	}
}
