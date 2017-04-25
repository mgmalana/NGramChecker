package Stemmer.Model.AffixModules.Prefix.Submodules;

import Stemmer.Model.AffixModules.AbstractMorphoChange;
import Stemmer.Model.AffixModules.AffixList;
import Stemmer.Model.DBHandler;
import Stemmer.Model.Stem;
import static Utility.print.*;
/**
 * Created by laurenz on 02/03/2017.
 */
public class ConvertPhonemeChanges extends AbstractMorphoChange
{
	/* Database for lookups */
	DBHandler dbHandler;
	/* list of possible phoneme change prefixes */
	String[] phonemeChangePrefixes  = AffixList.getPhonemeChangePrefixes();
	char[] possibleCharReplacements = AffixList.getPossiblePhonemeCharReplacements();


	public ConvertPhonemeChanges()
	{
		dbHandler = new DBHandler();
	}

	@Override
	public Stem reduceStem(Stem stem)
	{
		/* Original word */
		String word = stem.getStemString();
		/* Word Parts */
		String leftPart, rightPart, combinedPart;
		/* r -> d */
		char origChar, newChar;

		/* iterate for every existing phoneme change prefixes */
		for( int k = 0; k < phonemeChangePrefixes.length; k++ )
		{
			/* Checks if the first half of the word contains possible phoneme change prefixes */
			if( word.substring(0,  word.length()/2).contains( phonemeChangePrefixes[k] ) )
			{
				/* if the prefix in the list matches the one in the word being stemmed */
				leftPart = word.substring( 0, phonemeChangePrefixes[k].length() );
				if( phonemeChangePrefixes[k].equalsIgnoreCase(leftPart) )
				{
					rightPart 		= word.substring( phonemeChangePrefixes[k].length() );
					/* replace the prefix with a phoneme change */
					for( int charsChoices = 0; charsChoices < possibleCharReplacements.length; charsChoices++ )
					{
						leftPart 		= possibleCharReplacements[ charsChoices ] + "";
						combinedPart 	= leftPart + rightPart;
						/* Check the modified word in the db. If it exists, update the Stem() */
						if ( dbHandler.lookup( combinedPart ) )
						{
							stem.setStemString( combinedPart );
							stem.setPrefixFeatures( stem.getPrefixFeatures() + applyFeature(phonemeChangePrefixes[k]) );
						}
//						else break; // redundant? Maybe. Hotel? Trivago.
					}

				}
			}
			/* if it's just a simple "ma" prefix */
			else
			{

			}
		}

		return stem;
	}

	@Override
	public String applyFeature(String foundAffix)
	{
		return "~" + foundAffix;
	}

	/**
	 * Will test ma + word.
	 */
	public static class Test
	{
		ConvertPhonemeChanges cpc = new ConvertPhonemeChanges();

		public static void main(String[] args)
		{
			ConvertPhonemeChanges convertPhonemeChanges = new ConvertPhonemeChanges();
			String word = "malapot";
			Stem stem 	= new Stem( word );
			stem = convertPhonemeChanges.reduceStem(stem);
			println( "Reduced Stem: " + stem.getStemString() );
		}
	}
}
