package Stemmer.Model.AffixModules.Suffix;

import Stemmer.Model.AffixModules.AbstractAffixCommand;
import Stemmer.Model.AffixModules.Suffix.Submodules.ConvertPhonemeChanges;
import Stemmer.Model.AffixModules.Suffix.Submodules.RemoveCommonSuffix;
import Stemmer.Model.Stem;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class SuffixCommand extends AbstractAffixCommand
{

	Stem newStem;

	public SuffixCommand(){}

	public Stem performStemmingModules(Stem stem)
	{
		/* Initialize submodules for stemming */
		RemoveCommonSuffix 		rcp = new RemoveCommonSuffix();
		ConvertPhonemeChanges 	cpc = new ConvertPhonemeChanges();
		/* Initialize new Stem objects for immutability */
		Stem oldStem = stem.cloneThis();
		Stem newStem = stem.cloneThis();
		/*
		 * Perform stemming
		 */
		// CommonSuffix
		newStem = rcp.reduceStem( newStem );
		/* Check for Changes and update boolean changed */
//		if ( !checkForChanges(oldStem, newStem) ) {
//			newStem = cpc.reduceStem( newStem );
//			println("CPC: " + newStem.getStemString() );
//		}
		return newStem;
	}
	public static class test
	{
		public static void main(String[] args)
		{
			RemoveCommonSuffix cs = new RemoveCommonSuffix();
			String word = "tawanan";
			Stem stem = new Stem(word);

			stem = cs.reduceStem(stem);
			println("word: " + word + " -> " + stem.getStemString());
			println("Affix: " + cs.getFoundAffix());
			println("AffixFeatured: " + cs.getFoundAffixFeatured());
		}
	}
}
