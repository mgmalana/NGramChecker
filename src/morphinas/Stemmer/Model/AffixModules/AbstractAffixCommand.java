package morphinas.Stemmer.Model.AffixModules;

import morphinas.Stemmer.Model.DBHandler;
import morphinas.Stemmer.Model.Stem;

/**
 * Created by laurenz on 09/02/2017.
 */
public abstract class AbstractAffixCommand {
	/* stem and newStem will have the same value. */
	protected boolean changed = false, isRoot = false;

	public AbstractAffixCommand(){}

//	public AbstractAffixCommand(Stem stem)
//	{
//		this.stem = stem;
//		oldStem = new Stem( stem.getStemString() );
//		performStemmingModules();
//		checkForChanges( oldStem, stem );
//	}

	public abstract Stem performStemmingModules(Stem stem);

 	public boolean checkForChanges(Stem oldStem, Stem newStem)
	{
		if( oldStem.getStemString().equalsIgnoreCase( newStem.getStemString() ) ) {
			setChanged( false );
			return false;
		} else {
			setChanged( true );
			return true;
		}
	}

	public boolean checkDB(Stem stem)
	{
		DBHandler db = new DBHandler();
		return db.lookup( stem.getStemString() );
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}


}
