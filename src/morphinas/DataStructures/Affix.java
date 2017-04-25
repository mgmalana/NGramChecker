package morphinas.DataStructures;

public class Affix {

	private String affix;
	private String affixType;
	private String affixDashed;
	private String affixBrackets;
	
	private String affixBreakdown;

	/**
	 * Creates a new Affix object/class which contains (in string) affix (-um, -an) and the affix type (suffix, prefix)
	 * Also updates affixDashed and affixBrackets with associated markings. 
	 * @param affix
	 * The affix of the word: -um, -an, etc.
	 * @param affixType
	 * The affix type of the word: infix, prefix, suffix
	 */
	public Affix(String affix, String affixType)
	{
		this.affix 		= affix;
		this.affixType 	= affixType;
		// Populate the affixDashed and affixBrackets variables
		this.generateAffixVariations(affix, affixType);
	}
	
	/** 
	 * 
	 * @return
	 * The affix of the word in String
	 */
	public String getAffix()
	{
		return this.affix;
	}
	
	/**
	 * 
	 * @return
	 * Affix type of the word in String
	 */
	public String getAffixType()
	{
		return this.affixType;
	}
	
	/**
	 * Sets the affix value for the Word.affix
	 * Also generates affixDashed ( -an) and affixBrackets( an]) info. for everytime the setAffix is called.
	 * @param inputAffix
	 * Sets affix in String for the Word object
	 */
	public void setAffix(String affix)
	{
		this.affix = affix;		
		generateAffixVariations(affix, this.affixType);
	}
	
	/**
	 * Does not include INFIX
	 * @param affix
	 * @param affixType
	 */
	public void generateAffixVariations(String affix, String affixType)
	{
		switch(affixType)
		{
			case "infix": 
				affixDashed 	= "(" + affix + ")";
				affixBrackets 	= "(" + affix + ")"; 
				break;
			case "prefix":
				affixDashed 	= affix + "-" ;
				affixBrackets 	= "[PFX:" + affix;
				break;
			case "suffix":
				affixDashed 	= "-" + affix;
				affixBrackets 	= affix + "]";
				break;
			default: 
				affixDashed 	= "";
				affixBrackets 	= "";
		}
	}
		
	
	/**
	 * Sets affix type in String
	 * @param inputAffixType
	 */
	public void setAffixType(String inputAffixType)
	{
		this.affixType = inputAffixType;
	}

	public String getAffixDashed() {
		return affixDashed;
	}

	public void setAffixDashed(String affixDashed) {
		this.affixDashed = affixDashed;
	}
	
	public String getAffixBrackets() {
		return affixBrackets;
	}

	public void setAffixBrackets(String affixBrackets) {
		this.affixBrackets = affixBrackets;
	}
	
}
