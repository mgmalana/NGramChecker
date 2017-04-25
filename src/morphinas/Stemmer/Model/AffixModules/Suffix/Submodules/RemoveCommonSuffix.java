package morphinas.Stemmer.Model.AffixModules.Suffix.Submodules;

import morphinas.Stemmer.Model.AffixModules.AbstractMorphoChange;
import morphinas.Stemmer.Model.AffixModules.AffixList;
import morphinas.Stemmer.Model.Stem;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class RemoveCommonSuffix extends AbstractMorphoChange
{
	String[] commonSuffixes = AffixList.getCommonSuffixes();

	@Override
	public Stem reduceStem(Stem stem)
	{
		String word = stem.getStemString();
		int suffixLength;

		for( String suffix: commonSuffixes )
		{
			suffixLength = suffix.length();
			rightStem  	 = word.substring( word.length() - suffixLength );
			/* Will execute when a suffix is found */
			if( suffix.equalsIgnoreCase(rightStem) )
			{
				/* Must check if suffix found belongs to the suffixes with phoneme changes */
				if (suffix.equalsIgnoreCase("in") || suffix.equalsIgnoreCase("an"))
				{
					ConvertPhonemeChanges cpc = new ConvertPhonemeChanges();
					Stem cpcStem = cpc.reduceStem( stem );
					if ( !cpcStem.getStemString().equalsIgnoreCase( stem.getStemString() ) )
					{
						// update the stem string
						stem.setStemString(cpcStem.getStemString());
						// update the stem suffix features
//						stem.setSuffixFeatures(stem.getSuffixFeatures() + cpcStem.getSuffixFeatures());
						stem.addSuffix( suffix );
					}
					else
					{
						this.foundAffix 	= suffix;
						leftStem  	= word.substring(0, word.length()-suffixLength);
						/* Update or Set stem properties */
						stem.setStemString( leftStem );
//						stem.setFeature( stem.getFeature() + "" + applyFeature( suffix ) );
						stem.addSuffix( suffix );
						return stem;
					}
				}
				else
				{
					this.foundAffix 	= suffix;
					leftStem  	= word.substring(0, word.length()-suffixLength);
					/* Update or Set stem properties */
					stem.setStemString(leftStem);
//					stem.setFeature( stem.getFeature() + "" + applyFeature( suffix ) );
					stem.addSuffix( suffix );
					return stem;
				}

			}
		}
		return stem;
	}


	@Override
	public String applyFeature(String foundAffix)
	{
		foundAffixFeatured = "+" + foundAffix;
		return foundAffixFeatured;
	}
}
