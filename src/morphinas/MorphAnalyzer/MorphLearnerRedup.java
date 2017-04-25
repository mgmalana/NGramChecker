package morphinas.MorphAnalyzer;
import morphinas.DataStructures.*;
import java.util.*;
import java.io.*;
// Solomon's Model
public class MorphLearnerRedup implements Serializable
{
    protected Trie popTrie, posTrie, prefixTrie, vowelChangeTrie;
    protected SuffixTrie suffixTrie;
    protected Vector<RewriteRule> infixList;
    protected RuleExtractorContext rec;
    protected CountingTable vowelChange = new CountingTable();
    protected Hashtable prefixRulesTable = new Hashtable();    
    protected Hashtable suffixRulesTable = new Hashtable();    
    protected Hashtable infixRulesTable = new Hashtable();    
    protected Hashtable popRulesTable = new Hashtable();
    protected Hashtable posRulesTable = new Hashtable();
    protected Hashtable vcRulesTable = new Hashtable();    
    protected SemanticTree semanticTree = new SemanticTree();
    private double posConfidence, popConfidence,vowelConfidence;

	//  Local DB
	public DBLexiconSQL lex;

    // Global Variables
    private ArrayList<String> globalPrefixList = new ArrayList<String>();
    public String globalPrefix = "";
	public String globalSuffix = "";
	public String globalRedup  = "";
    private Affix globalAffix;
	private boolean shouldStop = false;
    
    //Laurenz
    Word word = new Word("");
    
    public MorphLearnerRedup() throws Exception
    {
//        System.out.println("Solomon's Infix and Redup Model");
        lex = new DBLexiconSQL();
        Trie popTrie = new Trie(new DefaultTrieImpl()), prefixTrie = new Trie(new DefaultTrieImpl());
        Trie posTrie = new SuffixTrie(new DefaultTrieImpl()), suffixTrie = new SuffixTrie(new DefaultTrieImpl());                
        Trie vowelChangeTrie = new Trie(new DefaultTrieImpl());
        Vector<RewriteRule> infixList = new Vector();
        prefixTrie.store("nag");
        prefixTrie.store("mag");
        prefixTrie.store("na");
        prefixTrie.store("ma");
        prefixTrie.store("i");
        prefixTrie.store("ipa");
        prefixTrie.store("ipag");
        prefixTrie.store("ipang");
        prefixTrie.store("pag"); 
        prefixTrie.store("pa");
        prefixTrie.store("um");
        prefixTrie.store("in");
        prefixTrie.store("ka");
        prefixTrie.store("ni");
        /* most of these will be processed by AffixBreakdown first */
        prefixTrie.store("napag");
        prefixTrie.store("mapag");
        prefixTrie.store("nakipag");
        prefixTrie.store("nakikipag");
        prefixTrie.store("makipag");
        prefixTrie.store("makikipag");
        prefixTrie.store("nakiki");
        prefixTrie.store("makiki");
        prefixTrie.store("naka");
        prefixTrie.store("nakaka");
        prefixTrie.store("maka");
        prefixTrie.store("makaka");    
        prefixTrie.store("nagka");
        prefixTrie.store("nagkaka");        
//        prefixTrie.store("magka");
//        prefixTrie.store("magkaka");
        prefixTrie.store("napa");
        prefixTrie.store("napaki");
        prefixTrie.store("napakiki");
        prefixTrie.store("mapa");
        prefixTrie.store("mapaki");
        prefixTrie.store("mapakiki");
        prefixTrie.store("paki");
        prefixTrie.store("pakiki");
        prefixTrie.store("pakikipag");
        prefixTrie.store("pagki");
        prefixTrie.store("pagkiki");
        prefixTrie.store("pagkikipag");
        /*
         * Add NEW prefixTrie.store here 
         */
		prefixTrie.store("ina");
		/* 2017 adds */
		prefixTrie.store("ika");
		prefixTrie.store("ika-");
		prefixTrie.store("isa-");
//		prefixTrie.store("ku");
        //prefixTrie.store("pi");
        prefixTrie.store("pinag");
        prefixTrie.store("pinagpa"); // W/ this = pinagpaliban -> liban
		// except pinagpala -> ala is wrong
        //prefixTrie.store("ipinagpa"); // w/ this = ?

		prefixTrie.store("paki");
        /*
         * Start of suffixTrie.stores
         */
        suffixTrie.store("an"); // has a lot of issues 
        suffixTrie.store("in");
//      suffixTrie.store("nan"); // just added ( w/o -> tawan; w/ -> tawa)
        suffixTrie.store("han");
        suffixTrie.store("hin");
		suffixTrie.store("ng");
		suffixTrie.store("g"); // ADDED AUG 5
		infixList.add(new RewriteRule("ni", "")); // ADDED AUG 5
        infixList.add(new RewriteRule("in",""));
        infixList.add(new RewriteRule("um",""));
        init(prefixTrie, (SuffixTrie) suffixTrie, popTrie,posTrie, vowelChangeTrie, infixList);
    }
    
    
    public MorphLearnerRedup(Trie prefixTrie, SuffixTrie suffixTrie, Trie popTrie, Trie posTrie, Trie vowelChangeTrie, Vector<RewriteRule> infixList) 
    {
        init(prefixTrie, suffixTrie, popTrie,posTrie, vowelChangeTrie, infixList);
    }

	/**
	 * Hindi ako gumawa nito - Laurenz
	 * @param filename
	 * @throws Exception
	 */
	public void saveFile(String filename) throws Exception
    {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        lex = null;
        rec = null;
        oos.writeObject(this);
        oos.close();        
    }
    public static MorphLearnerRedup loadFile(String filename) throws Exception{
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        MorphLearnerRedup mlr = (MorphLearnerRedup)ois.readObject();
//        mlr.lex = new DBLexicon();
        mlr.rec = new RuleExtractorContext(RuleExtractorContext.Learners.SWSRedup, mlr.prefixTrie, (SuffixTrie) mlr.suffixTrie, mlr.infixList);        
        ois.close();
        return mlr;
    }

	private void init(Trie prefixTrie, SuffixTrie suffixTrie, Trie popTrie, Trie posTrie, Trie vowelChangeTrie, Vector<RewriteRule> infixList) {
        this.popTrie = popTrie;
        this.posTrie = posTrie;
        this.prefixTrie = prefixTrie;
        this.suffixTrie = suffixTrie;
        this.vowelChangeTrie = vowelChangeTrie;
        this.infixList = infixList;
        rec = new RuleExtractorContext(RuleExtractorContext.Learners.SWSRedup, prefixTrie, (SuffixTrie) suffixTrie, infixList);        
    }

	public void extractRules(String morphed, String orig) {
        Vector<MorphRule> v = rec.extractRules(morphed,orig,  null, null);
        MorphRule r;
        String trimmed;
        for(int i=0;i<v.size(); i++) {
            r = v.elementAt(i);
            posTrie.store(r.getTrimmedMorphedWord(), r.getPOSRule());            
            addToRulesTable(r.getMorphedPOS(), r.getPOSRule(), posRulesTable);
            popTrie.store(r.getTrimmedMorphedWord(), r.getPOPRule());
            addToRulesTable(r.getMorphedPOP(), r.getPOPRule(), popRulesTable);
//            if (!r.getMorphedVowelChange().equals("")) {
//                vowelChange.storeObject(r.getVowelChangeRule());
//                addToRulesCountTable(r.getMorphedVowelChange(), r.getVowelChangeRule(), vcRulesTable);
//            }
//            vowelChangeTrie.store(r.getMorphedVowelChange(),r.getVowelChangeRule());                        
        }
    }

    private void addToRulesTable(String s, RewriteRule r, Hashtable t) {
        HashSet set = (HashSet) t.get(s);
        if (set == null) {
            set = new HashSet();
        }
        set.add(r);
        t.put(s,set);
    }

    private void addToRulesCountTable(String s, RewriteRule r, Hashtable t) {
        CountingTable ct = (CountingTable) t.get(s);
        if (ct == null)
            ct = new CountingTable();
        ct.storeObject(r);
        t.put(s,ct);
    }
    public void extractRule(String morphed, String orig) {        
        MorphRule r = rec.extractRule(morphed,orig,  null, null);
        popTrie.store(r.getTrimmedMorphedWord(), r.getPOPRule());
        addToRulesTable(r.getMorphedPOP(), r.getPOPRule(), popRulesTable);                
        posTrie.store(r.getTrimmedMorphedWord(), r.getPOSRule());
        addToRulesTable(r.getMorphedPOS(), r.getPOSRule(), posRulesTable);
        if (!r.getMorphedVowelChange().equals("")) {
//            vowelChange.storeObject(r.getVowelChangeRule());
            addToRulesCountTable(r.getMorphedVowelChange(), r.getVowelChangeRule(), vcRulesTable);
        }
//        System.out.println(morphed + "," + orig + "," + r.getPrefix() + "," + r.getSuffix() + "," + r.getInfix() + "," + r.getPartialRedup() + "," + r.getPOPRule() + "," + r.getPOSRule() + "," + r.getVowelChangeRule());
//        if (!r.getPrefix().equals("") && !r.getSuffix().equals("") && !r.getInfix().equals("") && !r.getPOPRule().isEmpty() && !r.getPOSRule().isEmpty() && !r.getPartialRedup().equals(""))
//            System.out.println(morphed + "->" + orig + "//" + r.getMorphedPOS() + "->" + r.getRootPOS());
    }    
    public void extractRuleSemantics2(String morphed, String orig, WordSemantic morphedSemantic) {        
        MorphRule r = rec.extractRule(morphed,orig, morphedSemantic, null);
        semanticTree.addRule(r);
        popTrie.store(r.getTrimmedMorphedWord(), r.getPOPRuleNoSemantic());
        addToRulesTable(r.getMorphedPOP(), r.getPOPRuleNoSemantic(), popRulesTable);                
        posTrie.store(r.getTrimmedMorphedWord(), r.getPOSRuleNoSemantic());
        addToRulesTable(r.getMorphedPOS(), r.getPOSRuleNoSemantic(), posRulesTable);
        if (!r.getMorphedVowelChange().equals("")) {
//            vowelChange.storeObject(r.getVowelChangeRule());
            addToRulesCountTable(r.getMorphedVowelChange(), r.getVowelChangeRuleNoSemantic(), vcRulesTable);
        }
//        if (r.getMorphedPOP().startsWith("sipa"))
//            System.out.println(morphed + "->" + orig + "//" + r.getMorphedPOP() + "->" + r.getRootPOP());
    }    
    public void extractRuleSemantics1(String morphed, String orig, WordSemantic morphedSemantic) {        
        MorphRule r = rec.extractRule(morphed,orig, morphedSemantic, null);
        addToRulesCountTable(r.getPrefix(), r.getPrefixRule(), prefixRulesTable);
        addToRulesCountTable(r.getSuffix(), r.getSuffixRule(), suffixRulesTable);
        addToRulesCountTable(r.getInfix(), r.getInfixRule(), infixRulesTable);
        popTrie.store(r.getTrimmedMorphedWord(), r.getPOPRule());
        addToRulesTable(r.getMorphedPOP(), r.getPOPRule(), popRulesTable);                
        posTrie.store(r.getTrimmedMorphedWord(), r.getPOSRule());
        addToRulesTable(r.getMorphedPOS(), r.getPOSRule(), posRulesTable);
        if (!r.getMorphedVowelChange().equals("")) {
//            vowelChange.storeObject(r.getVowelChangeRule());
            addToRulesCountTable(r.getMorphedVowelChange(), r.getVowelChangeRule(), vcRulesTable);
        }
//        if (r.getMorphedPOP().startsWith("sipa"))
//            System.out.println(morphed + "->" + orig + "//" + r.getMorphedPOP() + "->" + r.getRootPOP());
    }    
    protected void smoothTrie() {
        Vector v = popTrie.getContents();
        CountingTable ct;
        Enumeration e;
        popTrie.computeProbabilities();    
        posTrie.computeProbabilities();
        e = vcRulesTable.elements();
        while(e.hasMoreElements()) {
            ct = (CountingTable) e.nextElement();
            ct.computeProbabilites();
        }
        e = prefixRulesTable.elements();
        while(e.hasMoreElements()) {
            ct = (CountingTable) e.nextElement();
            ct.computeProbabilites();
        }        
        e = suffixRulesTable.elements();
        while(e.hasMoreElements()) {
            ct = (CountingTable) e.nextElement();
            ct.computeProbabilites();
        }
        e = infixRulesTable.elements();
        while(e.hasMoreElements()) {
            ct = (CountingTable) e.nextElement();
            ct.computeProbabilites();
        }        
        semanticTree.computeProbabilities();
/*        for(int i=0;i<v.size();i++) {
            ct = vowelChangeTrie.getObjectList((String) v.elementAt(i));
            ct.adjustCounts();
        }        */
    }

    public MAResult analyzeMultipleCanonicals(String orig) {
        int i,j;
        Vector<String> prefixes = prefixTrie.getAllPossibleMatch(orig);
        Vector<String> suffixes = suffixTrie.getAllPossibleMatch(orig);
        String prefix; 
        String suffix;
        MAResult tempResult = null, maxResult = null;
        // no prefix and suffix
        prefix="";
        suffix = "";
        maxResult = rewrite(orig,prefix,suffix);       
       // no prefix
        for(j=0;j<suffixes.size();j++) {
            prefix="";
            suffix = suffixes.elementAt(j);
            tempResult = rewrite(orig,prefix,suffix);
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;                
        }          
       // no suffix
        for(i=0;i<prefixes.size();i++){
            prefix = prefixes.elementAt(i);
            suffix = "";
            tempResult = rewrite(orig,prefix,suffix);
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;                    
        }          
       //complete prefix and suffix
        for(i=0;i<prefixes.size();i++)
         for(j=0;j<suffixes.size();j++) {
            prefix = prefixes.elementAt(i);
            suffix = suffixes.elementAt(j);
            tempResult = rewrite(orig,prefix,suffix);
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;
        }                  
        return maxResult;
    }    
    
    /**
     * The highest (in terms of abstraction) method for finding the root of the word (I think).
     * The operations in this method includes finding the infixes/suffix/affix and then removing them.
     * Log: Method is not anymore near the original. Performed atomicity to make code abstracted.
     * @param orig
     * The string 'passed' in this variable has been reduced to contain only prefixes and suffixes.
	 * Reduplication and infix reduction has already been performed before arriving at this step.
     * @return
     * A string that consists of the root of the input string
     * @laurenz
     * yes nandito ka
     * 
     */
    public MAResult analyzeMultipleMod(String orig)
    {
    	int i,j,k,l;
        Vector<String> prefixes;
        Vector<String> suffixes;
        Vector<RewriteRule> infixes;
        String reducedWord, prefix, suffix;
        MAResult tempResult = null, maxResult = null;


        // Laurenz here
        Word tempWord = new Word(orig);
        ArrayList<Affix> affixes = new ArrayList<Affix>();
        ArrayList<Affix> wordPrefixes = new ArrayList<Affix>();
        ArrayList<Affix> wordSuffixes = new ArrayList<Affix>();
        ArrayList<Affix> wordInfixes = new ArrayList<Affix>();
        Affix wordInfix, wordSuffix, wordPrefix;
        int affixCount = 0;

		this.globalPrefix = "";
		this.globalSuffix = "";

    	infixes = reduceInfix(orig);
    	
    	for( l = 0; l < infixes.size(); l++ )
    	{
    		reducedWord = infixes.elementAt(l).infixRemove(orig);
//    		println("infixes.elementAt("+l+"): " + infixes.elementAt(l));
//    		println("infix to be removed: " + infixes.elementAt(l).infixRemove(orig));
    		
    		
    		prefixes	= prefixTrie.getAllPossibleMatch(reducedWord);    		
    		suffixes	= suffixTrie.getAllPossibleMatch(reducedWord);
    		
    		wordInfix 	= new Affix(infixes.elementAt(l).toString(), "infix");    	
    		wordInfixes.add(wordInfix);
    		    		
    		// Initialize tempResult
    		prefix		= "";
    		suffix		= "";
    		tempResult 	= rewriteMultipleNoSemantic( reducedWord, prefix, suffix );
			// insert global shouldStop result here @laurenz
    		if ( maxResult == null )
    		{
    			maxResult = tempResult;
    		}
			else if ( tempResult.confidence >= maxResult.confidence )
    		{
    			maxResult = tempResult;
    		}

//			println("maxResult: " + maxResult.prefix + " and " + maxResult.suffix );

			// Start performRewriteActions on prefix then suffix with alternating rules
    		
			/*
			* NO PREFIX:
			* When the word only contains suffixes
			* Ex: Tira+in, Lupa+in
			*/
    		for( j = 0; j < suffixes.size(); j++ )
    		{
    			maxResult = rewriteNoAntiAffix(0,j, suffixes, reducedWord, tempResult, maxResult);

				if( maxResult.suffix != null)
    			{
    				affixCount++;
    				wordSuffix = addToSuffix(maxResult.suffix);
    				wordSuffixes.add(wordSuffix);
        			affixes.add(wordSuffix);
    			}

    		}

    		/*
    		 * NO SUFFIX:
    		 * When the word only contains prefixes.
    		 * Ex: Pag+ipon, pag+patay, Pag+pasa
    		*/
    		for( i = 0; i < prefixes.size(); i++ )
    		{
    			maxResult = rewriteNoAntiAffix(1, i, prefixes, reducedWord, tempResult, maxResult);
    			
    			if( maxResult.prefix != null ) 
    			{
    				affixCount++;
    				wordPrefix = addToPrefix(maxResult.prefix);
    				wordPrefixes.add(wordPrefix);
        			affixes.add(wordPrefix);
    			}    			
    		}

			/*
			* BOTH PREFIX AND SUFFIX ARE PRESENT
			* Ex: Pag+pasa+han, [Pi~nag]+
			*/
    		for( i = 0; i < prefixes.size(); i++ )
    		{
    			maxResult = rewriteBothAffix(prefixes, suffixes, reducedWord, tempResult, maxResult);
//				println("maxREsult 2222: " + maxResult.prefix);
    		}

			for(i=0;i<prefixes.size();i++)
			{
				prefix = prefixes.elementAt(i);
				for(j=0;j<suffixes.size();j++)
				{
					suffix = suffixes.elementAt(j);

					tempResult = rewriteMultipleNoSemantic(reducedWord,prefix,suffix);

					if (maxResult == null)
					{
						maxResult = tempResult;
					}
					else if(tempResult.confidence >= maxResult.confidence)
					{
						maxResult = tempResult;
					}
				}
			}
    	}

		if( globalSuffix.equalsIgnoreCase("") == false )
		{
//			println("globalSuffix: " + this.globalPrefix);
			wordSuffix = new Affix(this.globalSuffix, "suffix");
			wordSuffixes.add(wordSuffix);
		}

		if( globalPrefix != null)
		{
			wordPrefix = new Affix(this.globalPrefix, "prefix");
			wordPrefixes.add(wordPrefix);
		}

		/*
		FIX THIS PART @LAURENZ
		 */

    	// Set the number of affixes found
    	tempWord.setAffixCount(infixes.size() + affixCount);
    	// Store collected affixes by pre, inf, and suf
    	ArrayList<Affix> tempInfixes = wordInfixes;    	
    	tempWord.setInfixes((tempInfixes));
    	tempWord.setPrefixes(wordPrefixes);
    	tempWord.setSuffixes(wordSuffixes);
    	// Store MAResult with root information
    	tempWord.setMaresult(maxResult);

//		Formatter.printAllAffixes(tempWord.getPrefixes());

		// There's a problem here around maxResult == null
    	if( maxResult == null )
    	{    		
    		maxResult = tempResult;
    		tempWord.setMaresult(maxResult);
    		tempWord.setRootWord(maxResult.result);
    	} 
    	else 
    	{
    		tempWord.setRootWord(maxResult.result);	
    	}

    	// check if tempWord contents are ok!
    	tempWord.finalContentsReady(false);
    	setWordObject(tempWord);


//    	println("All from global : " + this.globalPrefixList.toString());
    	
    	return maxResult;
    }
    

    /**
     * Applied atomicity since ang gulo ng previous code :v
     * Inside the code, if you see AntiAffix = suffix (if your input is prefix) and prefix (if your input is suffix)
     * order = 0 when traversing suffixes
     * order = 1 when traversing prefixes
     * @param i
     * @param affixes
     * This is the original vector<string> that is either suffix or prefix
     * @param reducedWord
     * @param tempResult
     * @param maxResult
     * @return
     * A MAResult maxResult that kept on being passed around
     * 
     */
    public MAResult rewriteNoAntiAffix(int order, int i, Vector<String> affixes, String reducedWord, MAResult tempResult, MAResult maxResult)
    {
        String affix, anteFix;

        switch( order ) 
        {
            case 0: 
                affix   = "";
                anteFix = affixes.elementAt(i);
                tempResult = rewriteMultipleNoSemantic(reducedWord,affix,anteFix);
                break;
            case 1: 
                affix   = affixes.elementAt(i);
                anteFix = "";
                tempResult = rewriteMultipleNoSemantic(reducedWord,affix,anteFix);
                break;
            default:
                println("Invalid order number at rewriteNoAntiAffix(). Exiting program because simpleng call na nga lang mali pa :< ");
                System.exit(1);
        } 

        if ( maxResult == null )
        {
            maxResult = tempResult;
        }           
        else if ( tempResult.confidence >= maxResult.confidence )
        {
            maxResult = tempResult;
        }
        
        return maxResult;
    }

    /**
     * When the found affixes are both suffix and prefix.
     * Found => when it matches with the stored trie (suffix and prefix)
     * @param prefixes
     * @param suffixes
     * @param reducedWord
     * @param tempResult
     * @param maxResult
     * @return
     */
    public MAResult rewriteBothAffix(Vector<String> prefixes, Vector<String> suffixes, String reducedWord, MAResult tempResult, MAResult maxResult)
    {
    	int i, j;
    	//complete prefix and suffix
    	for(i=0;i<prefixes.size();i++) 
    	{
    	    String prefix = prefixes.elementAt(i);
    	    for(j=0;j<suffixes.size();j++) 
    	    {
    	        String suffix = suffixes.elementAt(j);    	        
    	        tempResult = rewriteMultipleNoSemantic(reducedWord,prefix,suffix);
    	        if (maxResult == null)
				{
					maxResult = tempResult;
//					println("maxREsult 1 : " + maxResult.prefix);
				}
    	        else if(tempResult.confidence >= maxResult.confidence)
				{
					maxResult = tempResult;
//					println("maxREsult 2 : " + maxResult.prefix);
				}
    	    }
    	}
//		println("maxREsult: " + maxResult.prefix);
    	return maxResult;
    }
    
    private Affix addToPrefix(String input)
    {
    	Affix affix = new Affix(input, "prefix");
    	return affix;
    }
    
    private Affix addToSuffix(String input)
    {
    	Affix affix = new Affix(input, "suffix");
    	return affix;
    }
    
    private Affix addToInfix(String input)
    {
    	Affix affix = new Affix( input, "infix");
    	return affix;
    }
    
    public Word getWordObject() 
    {
    	return this.word;
    }
    
    public void setWordObject(Word word)
    {
    	this.word = word;    	
    }
    /**
     * You are lost. Go to analyzeMultipleMod(String orig)
     * @param orig
     * @return
     */
    public MAResult analyzeMultipleModWithSemantic2(String orig) {
        int i,j,k,l;
        Vector<String> prefixes;
        Vector<String> suffixes;
        Vector<RewriteRule> infixes;
        String prefix; 
        String suffix;
        String reducedWord;
        MAResult tempResult = null, maxResult = null;
        
        infixes = reduceInfix(orig);
        
        for(l=0;l<infixes.size();l++) {
            reducedWord = infixes.elementAt(l).infixRemove(orig);
            prefixes = prefixTrie.getAllPossibleMatch(reducedWord);
            suffixes = suffixTrie.getAllPossibleMatch(reducedWord);
            // no prefix and suffix
            prefix="";
            suffix = "";            
            tempResult = rewriteMultiple2(reducedWord,prefix,suffix, infixes.elementAt(l).getOriginal());
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;                            
           // no prefix
            for(j=0;j<suffixes.size();j++) {
                prefix="";
                suffix = suffixes.elementAt(j);
                tempResult = rewriteMultiple2(reducedWord,prefix,suffix, infixes.elementAt(l).getOriginal());
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;
            }
           // no suffix
            for(i=0;i<prefixes.size();i++){
                prefix = prefixes.elementAt(i);
                suffix = "";
                tempResult = rewriteMultiple2(reducedWord,prefix,suffix, infixes.elementAt(l).getOriginal());
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;                            
            }

           //complete prefix and suffix
            for(i=0;i<prefixes.size();i++) {
                prefix = prefixes.elementAt(i);
             for(j=0;j<suffixes.size();j++) {                
                suffix = suffixes.elementAt(j);
                tempResult = rewriteMultiple2(reducedWord,prefix,suffix, infixes.elementAt(l).getOriginal());
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;                                                    
             }
            }
        }
        return maxResult;
    }    
    
    /**
     * Go AWAY
     * @param orig
     * @return
     */
    public MAResult analyzeMultipleModWithSemantic1(String orig) {
        int i,j,k,l;
        Vector<String> prefixes;
        Vector<String> suffixes;
        Vector<RewriteRule> infixes;
        String prefix; 
        String suffix;
        String reducedWord;
        CountingTable prefixSet, suffixSet, infixSet;
        RewriteRule prefixRule, suffixRule, infixRule;
        Enumeration prefixEnum, suffixEnum, infixEnum;
        MAResult tempResult = null, maxResult = null;
        WordSemantic infixSemantic;
        double infixConfidence=1.0;
        
        infixes = reduceInfix(orig);
        
        for(l=0;l<infixes.size();l++) {
            if (!infixes.elementAt(l).getOriginal().equals("")) {
                infixSet = (CountingTable) infixRulesTable.get(infixes.elementAt(l).getOriginal());
                if (infixSet == null)
                    continue;
                infixEnum = infixSet.getKeys();
                while(infixEnum.hasMoreElements()) {
                    infixRule = (RewriteRule) infixEnum.nextElement();
                    infixConfidence = infixSet.getObjectCount(infixRule);
                    reducedWord = infixes.elementAt(l).infixRemove(orig);
                    prefixes = prefixTrie.getAllPossibleMatch(reducedWord);
                    suffixes = suffixTrie.getAllPossibleMatch(reducedWord);
                    // no prefix and suffix
                    prefix="";
                    suffix = "";            
                    tempResult = rewriteMultiple(reducedWord,prefix,suffix);
                    if (maxResult == null)
                        maxResult = tempResult;
                    else if(tempResult.confidence >= maxResult.confidence)
                        maxResult = tempResult;                            
                   // no prefix
                    for(j=0;j<suffixes.size();j++) {
                        prefix="";
                        suffix = suffixes.elementAt(j);
                        suffixSet = (CountingTable) suffixRulesTable.get(suffix);
                        if (suffixSet == null)
                            continue;
                        suffixEnum = suffixSet.getKeys();
                        while(suffixEnum.hasMoreElements()) {
                            suffixRule = (RewriteRule) suffixEnum.nextElement();                    
                            tempResult = rewriteMultiple(reducedWord,prefix,suffix, suffixRule.getSemantic(), suffixSet.getObjectCount(suffixRule) * infixConfidence);
                            if (maxResult == null)
                                maxResult = tempResult;
                            else if(tempResult.confidence >= maxResult.confidence)
                                maxResult = tempResult;                            
                        }
                    }          
                   // no suffix
                    for(i=0;i<prefixes.size();i++){
                        prefix = prefixes.elementAt(i);
                        suffix = "";
                        prefixSet = (CountingTable) prefixRulesTable.get(prefix);
                        if (prefixSet == null)
                            continue;
                        prefixEnum = prefixSet.getKeys();
                        while(prefixEnum.hasMoreElements()) {
                            prefixRule = (RewriteRule) prefixEnum.nextElement();
                            tempResult = rewriteMultiple(reducedWord,prefix,suffix, prefixRule.getSemantic(), prefixSet.getObjectCount(prefixRule) * infixConfidence);
                            if (maxResult == null)
                                maxResult = tempResult;
                            else if(tempResult.confidence >= maxResult.confidence)
                                maxResult = tempResult;                            
                        }
                    }          
                   //complete prefix and suffix
                    for(i=0;i<prefixes.size();i++) {
                        prefix = prefixes.elementAt(i);
                        prefixSet = (CountingTable) prefixRulesTable.get(prefix);
                        if (prefixSet == null)
                            continue;
                     for(j=0;j<suffixes.size();j++) {                
                        suffix = suffixes.elementAt(j);
                        suffixSet = (CountingTable) suffixRulesTable.get(suffix);
                        if (suffixSet == null)
                            continue;

                        prefixEnum = prefixSet.getKeys();                
                        while(prefixEnum.hasMoreElements()) {
                            prefixRule = (RewriteRule) prefixEnum.nextElement();
                            suffixEnum = suffixSet.getKeys();
                            while(suffixEnum.hasMoreElements()) {
                                suffixRule = (RewriteRule) suffixEnum.nextElement();
                                if (suffixRule.getSemantic().equals(prefixRule.getSemantic())) {
                                    tempResult = rewriteMultiple(reducedWord,prefix,suffix, prefixRule.getSemantic(), prefixSet.getObjectCount(prefixRule) * suffixSet.getObjectCount(suffix) * infixConfidence);
                                    if (maxResult == null)
                                        maxResult = tempResult;
                                    else if(tempResult.confidence >= maxResult.confidence)
                                        maxResult = tempResult;                                                    
                                }
                            }
                        }
                     }                    
                    }
                }
            } else {
                reducedWord = infixes.elementAt(l).infixRemove(orig);
                prefixes = prefixTrie.getAllPossibleMatch(reducedWord);
                suffixes = suffixTrie.getAllPossibleMatch(reducedWord);
                // no prefix and suffix
                prefix="";
                suffix = "";            
                tempResult = rewriteMultiple(reducedWord,prefix,suffix);
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;                            
               // no prefix
                for(j=0;j<suffixes.size();j++) {
                    prefix="";
                    suffix = suffixes.elementAt(j);
                    suffixSet = (CountingTable) suffixRulesTable.get(suffix);
                    if (suffixSet == null)
                        continue;
                    suffixEnum = suffixSet.getKeys();
                    while(suffixEnum.hasMoreElements()) {
                        suffixRule = (RewriteRule) suffixEnum.nextElement();                    
                        tempResult = rewriteMultiple(reducedWord,prefix,suffix, suffixRule.getSemantic(), suffixSet.getObjectCount(suffixRule));
                        if (maxResult == null)
                            maxResult = tempResult;
                        else if(tempResult.confidence >= maxResult.confidence)
                            maxResult = tempResult;                            
                    }
                }          
               // no suffix
                for(i=0;i<prefixes.size();i++){
                    prefix = prefixes.elementAt(i);
                    suffix = "";
                    prefixSet = (CountingTable) prefixRulesTable.get(prefix);
                    if (prefixSet == null)
                        continue;
                    prefixEnum = prefixSet.getKeys();
                    while(prefixEnum.hasMoreElements()) {
                        prefixRule = (RewriteRule) prefixEnum.nextElement();
                        tempResult = rewriteMultiple(reducedWord,prefix,suffix, prefixRule.getSemantic(), prefixSet.getObjectCount(prefixRule));
                        if (maxResult == null)
                            maxResult = tempResult;
                        else if(tempResult.confidence >= maxResult.confidence)
                            maxResult = tempResult;                            
                    }
                }          
               //complete prefix and suffix
                for(i=0;i<prefixes.size();i++) {
                    prefix = prefixes.elementAt(i);
                    prefixSet = (CountingTable) prefixRulesTable.get(prefix);
                    if (prefixSet == null)
                        continue;
                 for(j=0;j<suffixes.size();j++) {                
                    suffix = suffixes.elementAt(j);
                    suffixSet = (CountingTable) suffixRulesTable.get(suffix);
                    if (suffixSet == null)
                        continue;

                    prefixEnum = prefixSet.getKeys();                
                    while(prefixEnum.hasMoreElements()) {
                        prefixRule = (RewriteRule) prefixEnum.nextElement();
                        suffixEnum = suffixSet.getKeys();
                        while(suffixEnum.hasMoreElements()) {
                            suffixRule = (RewriteRule) suffixEnum.nextElement();
                            if (suffixRule.getSemantic().equals(prefixRule.getSemantic())) {
                                tempResult = rewriteMultiple(reducedWord,prefix,suffix, prefixRule.getSemantic(), prefixSet.getObjectCount(prefixRule) * suffixSet.getObjectCount(suffix));
                                if (maxResult == null)
                                    maxResult = tempResult;
                                else if(tempResult.confidence >= maxResult.confidence)
                                    maxResult = tempResult;                                                    
                            }
                        }
                    }
                 }                    
                }               
            }
        }
        return maxResult;
    }
    public MAResult analyzeMultiple(String orig) {
        int i,j,k;
        Vector<String> prefixes = prefixTrie.getAllPossibleMatch(orig);
        Vector<String> suffixes = suffixTrie.getAllPossibleMatch(orig);
        Vector<RewriteRule> infixes;
        String prefix; 
        String suffix;
        MAResult tempResult = null, maxResult = null;
        // no prefix and suffix
        prefix="";
        suffix = "";
        infixes = reduceInfix(orig);
        for(k=0;k<infixes.size();k++) {
            tempResult = rewriteMultiple(orig,prefix,suffix, infixes.elementAt(k));
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;                            
        }            
       // no prefix
        for(j=0;j<suffixes.size();j++) {
            prefix="";
            suffix = suffixes.elementAt(j);
            for(k=0;k<infixes.size();k++) {
                tempResult = rewriteMultiple(orig,prefix,suffix, infixes.elementAt(k));
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;                            
            }                            
        }          
       // no suffix
        for(i=0;i<prefixes.size();i++){
            prefix = prefixes.elementAt(i);
            suffix = "";
            for(k=0;k<infixes.size();k++) {
                tempResult = rewriteMultiple(orig,prefix,suffix, infixes.elementAt(k));
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;                            
            }     
        }          
       //complete prefix and suffix
        for(i=0;i<prefixes.size();i++)
         for(j=0;j<suffixes.size();j++) {
            prefix = prefixes.elementAt(i);
            suffix = suffixes.elementAt(j);
            for(k=0;k<infixes.size();k++) {
                tempResult = rewriteMultiple(orig,prefix,suffix, infixes.elementAt(k));
                if (maxResult == null)
                    maxResult = tempResult;
                else if(tempResult.confidence >= maxResult.confidence)
                    maxResult = tempResult;                            
            }     
        }                  
        return maxResult;
    }

    protected MAResult rewriteMultiple(String orig, String prefix, String suffix, RewriteRule infixRule) {
       String trimmed;
       String trimmedCanonicals;
       String result;
       RewriteRule popRewrite, posRewrite, vowelRewrite;              
       CountingTable tempTable;
       Hashtable rulesApplicables = new Hashtable();
       double inLexicon = 0.0, finalProb=0.0, maxProb=0.0;
       Hashtable posTable,popTable, vcTable;
       Enumeration posEnum, popEnum, vcEnum;
       String maxResult = null;       
       WordSemantic sem = null;
       
       result = orig.substring(prefix.length(), orig.length() - suffix.length());       
       result = infixRule.infixRemove(result);
       
       trimmed = result;
       trimmedCanonicals = result;
       
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
//       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable); // not used eh
//       posEnum = posTable.keys(); // not used din eh
       /*
       while(posEnum.hasMoreElements()) 
       {
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);
           popEnum = popTable.keys();
           
           while(popEnum.hasMoreElements()) 
           {
               popRewrite = (RewriteRule) popEnum.nextElement();
               popConfidence = (Double) popTable.get(popRewrite);
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               
               while(vcEnum.hasMoreElements()) 
               {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   
                   try
                   {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }
                   catch(Exception e) 
                   {
                       e.printStackTrace();
                   }                  
                   
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon;
                   
                   if (maxResult == null) 
                   {
                       maxProb = finalProb;
                       maxResult = result;
                   } 
                   else if (maxProb < finalProb) 
                   {
                       maxProb = finalProb;
                       maxResult = result;
                   }
               }
           }
       }
       */
       
       result = orig.substring(prefix.length(), orig.length() - suffix.length());       
       result = infixRule.infixRemove(result);
       trimmed = result;
       trimmedCanonicals = result;
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
               popConfidence = (Double) popTable.get(popRewrite);
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                   }
               }
           }
       }             
       return new MAResult(maxResult, maxProb);        
    }
    // Rewrites with Bayesian Semantics
    protected MAResult rewriteMultiple2(String orig, String prefix, String suffix, String infix) {
       String withRedup;
       String trimmed;
       String trimmedCanonicals;
       String result;
       RewriteRule popRewrite, posRewrite, vowelRewrite;              
       CountingTable tempTable;
       Hashtable rulesApplicables = new Hashtable();
       double inLexicon = 0.0, finalProb=0.0, maxProb=0.0;
       Hashtable posTable,popTable, vcTable;
       Enumeration posEnum, popEnum, vcEnum;
       String maxResult = null;       
       SemanticResult semResult;
       WordSemantic maxSem = null;
       boolean hasRedup;
       
       result = orig.substring(prefix.length(), orig.length() - suffix.length());
       if (result.trim().equals(""))
           return new MAResult("",0.0);
       trimmed = result;
       trimmedCanonicals = result;
       
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);           
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
               popConfidence = (Double) popTable.get(popRewrite);               
//               if (popRewrite.getOriginal().equals(""))
//                System.out.println("Using " + popRewrite);
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }            
                   semResult = semanticTree.getProbability(prefix,suffix,infix,popRewrite,posRewrite,vowelRewrite,false);
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon * semResult.probability;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = semResult.sem;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = semResult.sem;
                   }
               }
           }
       }

       result = orig.substring(prefix.length(), orig.length() - suffix.length());
       withRedup = result;
       result = reduceRedup(withRedup);
       if (result.trim().equals(""))
           return new MAResult("",0.0);
       hasRedup = !result.equals(withRedup);
       trimmed = result;
       trimmedCanonicals = result;
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
//               if (popRewrite.getOriginal().equals(""))
//                System.out.println("Using " + popRewrite);
               popConfidence = (Double) popTable.get(popRewrite);
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   semResult = semanticTree.getProbability(prefix,suffix,infix,popRewrite,posRewrite,vowelRewrite, hasRedup);
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon * semResult.probability;                   
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = semResult.sem;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = semResult.sem;
                   }
               }
           }
       }       
       return new MAResult(maxResult, maxSem, maxProb);        
    }    

	/**
	 * This one actually removes the suffixes and prefixes in the original word.
	 * @laurenz go here to dive inside the rewriting process
	 *
	 * @param orig
	 * Original word
	 * @param prefix
	 * Prefix to be removed
	 * @param suffix
	 * Suffix to be removed
	 * @return
	 * MAResult with sadness in it
	 */
	protected MAResult rewriteMultipleNoSemantic(String orig, String prefix, String suffix)
	{
		// Variables that are used in the result
		MAResult ma;
		MaxResultObjects mro;
		String maxResult, trimmed;
		double maxProb = 0.0;
		// Used only in this method
		String trimmedCanonicals, result;
		// for computing something
		// no idea
		Hashtable posTable, popTable;
		Enumeration posEnum;

		// Print this shit
//		println("orig: " + orig + " | prefix: " + prefix + " | suffix: " + suffix);

		/*
		 * Error when
		 * orig: man
		 * prefix: ma
		 * suffix: an
		 * orig.substring( 2, 3 - 2)
		 * *	where 2 = starting point and 3-2 is ending point
		 * *	thus -> start at 2 and end at 1  -------> ERROR
		 */
		if( orig.length() <= 3 )
		{
			return new MAResult( orig, 1.0);
		}

//		println("prefix.length(): " + prefix.length() + " | orig.length: " + orig.length() + "| suffix.length(): " + suffix.length());
		if( prefix.length() < ( orig.length() - prefix.length() ) )
		{
			result = orig.substring( prefix.length(), orig.length() - suffix.length() );
		}
		else {
			return new MAResult( orig, 1.0 );
		}

//		println("=-=-=-=-= result: " + result);


//		If simple substring manipulation returns a rootword already
		try {
//			println("checking lookup by substring manip");
			if( lex.lookup(result) )
			{
//				println("\n    well it's: " + result);
				// instantiate the MAResult
				ma = new MAResult (result, 1.0);
				// get the prefix in the word
				ma.prefix = orig.substring( 0, prefix.length() );

//				println(" ma.prefix: " + ma.prefix);
				ma.suffix = orig.substring( orig.length() - suffix.length(), orig.length());

				this.globalPrefix = prefix;
				this.globalSuffix = suffix;
				// to force the system in trying to overstem
				this.shouldStop   = true;
//				when the substring is already a root word
				return ma;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		when the whitespaces are removed and the resulting string is a ""
		if( result.trim().equals("") )
		{
			return new MAResult( "", 0.0 );
		}

		trimmedCanonicals = result;
//		trimmed 		  = result; // not used tho

//		The substring wherein the prefix is removed as well as the substring
		result = orig.substring( prefix.length(), orig.length() - suffix.length() );
//
// 		println("First Result: " + result);

//		After the prefix and suffixes were removed, reduplicated areas of the string are planned to be removed.
		result = reduceRedup( result );
//		println("Second Result: " + result);

		posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
		popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
		posEnum = posTable.keys();

		mro = performRuleRewrite( posTable, popTable, posEnum, trimmedCanonicals, result);

		maxResult = mro.maxResult;
		maxProb   = mro.maxProb;

		ma = new MAResult (maxResult, maxProb);

		try {
//			println("\n    Third result: " + result);
			if( lex.lookup(result) )
			{
				ma.prefix = prefix;
				ma.suffix = suffix;
				this.globalSuffix = suffix;
//				println("I have been loaded with the prefix: " + ma.prefix);
//				println("        Suffix from third: " + this.globalSuffix + "\n");
				//println("Added P and S are: " + prefix + " | " + suffix);
			} else {
				ma.suffix = "";
				this.globalSuffix = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		println("ma.suffix: " + ma.suffix);

		return ma;
	}

	/**
	 * Generates the best result and (double) probability
	 * @param posTable
	 * @param popTable
	 * @param poxEnum
	 * @param trimmedCanonicals
	 * @param result
	 * @return
	 */
	protected MaxResultObjects performRuleRewrite(Hashtable posTable, Hashtable popTable, Enumeration poxEnum, String trimmedCanonicals, String result )
	{
		MaxResultObjects MRO = new MaxResultObjects();
		RewriteRule posRewrite, popRewrite, vowelRewrite;
		Enumeration vcEnum, poyEnum;
		Hashtable vcTable;

		String maxResult = null, trimmed;
		double maxProb = 0.0, inLexicon = 0.0, finalProb;

		while(poxEnum.hasMoreElements())
		{
			posRewrite = (RewriteRule) poxEnum.nextElement();
			posConfidence = (Double) posTable.get(posRewrite);
			poyEnum = popTable.keys();

			while(poyEnum.hasMoreElements())
			{
				popRewrite = (RewriteRule) poyEnum.nextElement();
				//               if (popRewrite.getOriginal().equals(""))
				//                System.out.println("Using " + popRewrite);
				popConfidence = (Double) popTable.get(popRewrite);

				trimmed = posRewrite.suffixRemove(trimmedCanonicals);
				trimmed = popRewrite.prefixRemove(trimmed);
				vcTable = getPossibleVowelRewrite(trimmed);
				vcEnum = vcTable.keys();

				while(vcEnum.hasMoreElements())
				{
					vowelRewrite = (RewriteRule) vcEnum.nextElement();
					vowelConfidence = (Double) vcTable.get(vowelRewrite);

					result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
					result = posRewrite.suffixAdd(result);
					result = popRewrite.prefixAdd(result);

					try
					{
						// If the word exists in the database of root words.
						if (lex.lookup(result))
						{
							inLexicon = 1.0;
						}
						// If it is not on the list of root words.
						else
						{
							inLexicon = 0.0000001;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}

					finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon;

					if ( maxResult == null )
					{
						maxProb   = finalProb;
						maxResult = result;
						// using a class object
						if( inLexicon == 1.0 )
						{
							return MRO = new MaxResultObjects(maxProb, maxResult, true);
						} else {
							return MRO = new MaxResultObjects(maxProb, maxResult, false);
						}
					}
					else if ( maxProb < finalProb )
					{
						maxProb   = finalProb;
						maxResult = result;
						// using a class object
						if( inLexicon == 1.0 ) {
							return MRO = new MaxResultObjects(maxProb, maxResult, true);
						} else {
							return MRO = new MaxResultObjects(maxProb, maxResult, false);
						}
					}
				}
			}
		}

		return MRO;
	}


	/*
	 *	Please try to rewrite method above
	 */



    protected MAResult rewriteMultiple(String orig, String prefix, String suffix) {
       String noRedup;
       String trimmed;
       String trimmedCanonicals;
       String result;
       RewriteRule popRewrite, posRewrite, vowelRewrite;              
       CountingTable tempTable;
       Hashtable rulesApplicables = new Hashtable();
       double inLexicon = 0.0, finalProb=0.0, maxProb=0.0;
       Hashtable posTable,popTable, vcTable;
       Enumeration posEnum, popEnum, vcEnum;
       String maxResult = null;       
       WordSemantic sem = null, maxSem=null; 
       
       result = orig.substring(prefix.length(), orig.length() - suffix.length());
       if (result.trim().equals(""))
           return new MAResult("",0.0);
       trimmed = result;
       trimmedCanonicals = result;
       
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           sem = null;
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);           
           if (posRewrite.getSemantic() != null && !posRewrite.isEmpty())
               sem = posRewrite.getSemantic();
           else if (posRewrite.isEmpty())
               posConfidence = 1.0;
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
               popConfidence = (Double) popTable.get(popRewrite);               
               if (popRewrite.getSemantic() != null && posRewrite.getSemantic() != null && !popRewrite.isEmpty() && !posRewrite.isEmpty())
                if (!popRewrite.getSemantic().equals(posRewrite.getSemantic()))
                   continue;
               if (popRewrite.getSemantic() != null && !popRewrite.isEmpty())
                   sem = popRewrite.getSemantic();
//               if (popRewrite.getOriginal().equals(""))
//                System.out.println("Using " + popRewrite);
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   if (vowelRewrite.getSemantic() != null && popRewrite.getSemantic() != null && !vowelRewrite.isEmpty() && !popRewrite.isEmpty())
                    if (!vowelRewrite.equals(popRewrite.getSemantic()))
                       continue;
                   if (vowelRewrite.getSemantic() != null && !vowelRewrite.isEmpty())
                       sem = vowelRewrite.getSemantic();
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   }
               }
           }
       }

       result = orig.substring(prefix.length(), orig.length() - suffix.length());
       result = reduceRedup(result);
       if (result.trim().equals(""))
           return new MAResult("",0.0);
       trimmed = result;
       trimmedCanonicals = result;
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);
           if (posRewrite.getSemantic() != null && !posRewrite.isEmpty())
               sem = posRewrite.getSemantic();
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
//               if (popRewrite.getOriginal().equals(""))
//                System.out.println("Using " + popRewrite);
               popConfidence = (Double) popTable.get(popRewrite);
               if (popRewrite.getSemantic() != null && posRewrite.getSemantic() != null && !popRewrite.isEmpty() && !posRewrite.isEmpty())
                if (!popRewrite.getSemantic().equals(posRewrite.getSemantic()))
                   continue;
               if (popRewrite.getSemantic() != null && !popRewrite.isEmpty())
                   sem = popRewrite.getSemantic();

               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   if (vowelRewrite.getSemantic() != null && popRewrite.getSemantic() != null && !vowelRewrite.isEmpty() && !popRewrite.isEmpty())
                    if (!vowelRewrite.equals(popRewrite.getSemantic()))
                       continue;
                   if (vowelRewrite.getSemantic() != null && !vowelRewrite.isEmpty())
                       sem = vowelRewrite.getSemantic();

                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   }
               }
           }
       }       
       return new MAResult(maxResult, maxSem, maxProb);        
    }    
    protected MAResult rewriteMultiple(String orig, String prefix, String suffix, WordSemantic semantic, double psConfidence) {
       String noRedup;
       String trimmed;
       String trimmedCanonicals;
       String result;
       RewriteRule popRewrite, posRewrite, vowelRewrite;              
       CountingTable tempTable;
       Hashtable rulesApplicables = new Hashtable();
       double inLexicon = 0.0, finalProb=0.0, maxProb=0.0;
       Hashtable posTable,popTable, vcTable;
       Enumeration posEnum, popEnum, vcEnum;
       String maxResult = null;       
       WordSemantic sem = null, maxSem=null; 
       
       result = orig.substring(prefix.length(), orig.length() - suffix.length());
       if (result.trim().equals(""))
           return new MAResult("",0.0);
       trimmed = result;
       trimmedCanonicals = result;
       
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           sem = semantic;
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);           
           if (posRewrite.getSemantic() != null && !posRewrite.isEmpty())
            if (semantic.equals(posRewrite.getSemantic()))
               continue;           
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
               popConfidence = (Double) popTable.get(popRewrite);               
               if (popRewrite.getSemantic() != null && !popRewrite.isEmpty())
                if (!popRewrite.getSemantic().equals(semantic))
                   continue;
//               if (popRewrite.getOriginal().equals(""))
//                System.out.println("Using " + popRewrite);
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   if (vowelRewrite.getSemantic() != null && !vowelRewrite.isEmpty())
                    if (!vowelRewrite.equals(semantic))
                       continue;
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon * psConfidence;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   }
               }
           }
       }

       result = orig.substring(prefix.length(), orig.length() - suffix.length());
       result = reduceRedup(result);
       if (result.trim().equals(""))
           return new MAResult("",0.0);
       trimmed = result;
       trimmedCanonicals = result;
       posTable = posTrie.possibleMatchList(trimmedCanonicals, posRulesTable);
       popTable = popTrie.possibleMatchList(trimmedCanonicals, popRulesTable);
       posEnum = posTable.keys();       
       while(posEnum.hasMoreElements()) {
           posRewrite = (RewriteRule) posEnum.nextElement();
           posConfidence = (Double) posTable.get(posRewrite);
           if (posRewrite.getSemantic() != null && !posRewrite.isEmpty())
            if (semantic.equals(posRewrite.getSemantic()))
               continue;           
           popEnum = popTable.keys();
           while(popEnum.hasMoreElements()) {
               popRewrite = (RewriteRule) popEnum.nextElement();
//               if (popRewrite.getOriginal().equals(""))
//                System.out.println("Using " + popRewrite);
               popConfidence = (Double) popTable.get(popRewrite);
               if (popRewrite.getSemantic() != null && !popRewrite.isEmpty())
                if (!popRewrite.getSemantic().equals(semantic))
                   continue;
               trimmed = posRewrite.suffixRemove(trimmedCanonicals);
               trimmed = popRewrite.prefixRemove(trimmed);
               vcTable = getPossibleVowelRewrite(trimmed);
               vcEnum = vcTable.keys();
               while(vcEnum.hasMoreElements()) {
                   vowelRewrite = (RewriteRule) vcEnum.nextElement();
                   vowelConfidence = (Double) vcTable.get(vowelRewrite);
                   if (vowelRewrite.getSemantic() != null && !vowelRewrite.isEmpty())
                    if (!vowelRewrite.equals(semantic))
                       continue;
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0000001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon * psConfidence;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxSem = sem;
                   }
               }
           }
       }       
       return new MAResult(maxResult, maxSem, maxProb);        
    }        
    protected MAResult rewrite(String orig, String prefix, String suffix) {
       String trimmed;
       String result;
       RewriteRule popRewrite, posRewrite, vowelRewrite;              
       CountingTable tempTable;
       double inLexicon = 0.0;
       
//       System.out.println("Original word: " + orig);
       result = orig.substring(prefix.length(), orig.length() - suffix.length());       
       trimmed = result;
//       System.out.println("Trimmed result: " + trimmed);
       tempTable = posTrie.getObjectList(trimmed);
       posRewrite = (RewriteRule) tempTable.getHighestObjectCount();       
       posConfidence = tempTable.getHighestRatio();       
       if (posRewrite != null) {
           trimmed = posRewrite.suffixRemove(trimmed);
           result = posRewrite.suffixRewrite(result);       
       }
       tempTable = popTrie.getObjectList(trimmed);
       popRewrite = (RewriteRule) tempTable.getHighestObjectCount();
       popConfidence = tempTable.getHighestRatio();
       if (popRewrite != null) {
           trimmed = popRewrite.prefixRemove(trimmed);
           result = popRewrite.prefixRewrite(result);
       }
       vowelRewrite = rewriteVowel(trimmed);              
       if (vowelRewrite != null)
        result = vowelRewrite.middleRewrite(result, vowelRewrite.PRIORITY_RIGHT);
       try {
           if (lex.lookup(result))
               inLexicon = 1.0;
           else
               inLexicon = 0.0001;
       } catch(Exception e) {
           e.printStackTrace();
       }
       return new MAResult(result, posConfidence * popConfidence * vowelConfidence * inLexicon);        
    }
    public String analyze(String orig) {
       String prefix = prefixTrie.getGreatestCommon(orig);
       String suffix = suffixTrie.getGreatestCommon(orig);
       String trimmed;
       String result;
       RewriteRule popRewrite, posRewrite, vowelRewrite;       
       
//       System.out.println("Original word: " + orig);
       result = orig.substring(prefix.length(), orig.length() - suffix.length());       
       trimmed = result;
//       System.out.println("Trimmed result: " + trimmed);
       posRewrite = (RewriteRule) posTrie.getObjectList(trimmed).getHighestObjectCount();       
       if (posRewrite != null) {
           trimmed = posRewrite.suffixRemove(trimmed);
           result = posRewrite.suffixRewrite(result);       
       }
       popRewrite = (RewriteRule) popTrie.getObjectList(trimmed).getHighestObjectCount();
       if (popRewrite != null) {
           trimmed = popRewrite.prefixRemove(trimmed);
           result = popRewrite.prefixRewrite(result);
       }
       vowelRewrite = rewriteVowel(trimmed);              
       if (vowelRewrite != null)
        result = vowelRewrite.middleRewrite(result, vowelRewrite.PRIORITY_RIGHT);
//       System.out.println("Root word: " + result);
       return result;
    }    
    private RewriteRule rewriteVowel(String morphed) {
       RewriteRule rwr, maxRWR = null;
       double max = -1, nTemp;
       double total=0;
       Enumeration e = vowelChange.getKeys();  
       if (e != null)  {
           while(e.hasMoreElements()){
               rwr = (RewriteRule) e.nextElement();
               nTemp = vowelChange.getObjectCount(rwr);
               total += nTemp;
               if (nTemp > max) {
                    if(morphed.contains(rwr.getOriginal()) && !morphed.startsWith(rwr.getOriginal()) && !morphed.endsWith(rwr.getOriginal())) {
                        max = nTemp;
                        maxRWR = rwr;
                    }
               }               
           }
       }
       if (max == -1)
           vowelConfidence = 1.0;
       else 
           vowelConfidence = max/total;
       return maxRWR;
    }
    private Hashtable getPossibleVowelRewrite(String word) {
        Hashtable filtered = new Hashtable();
        Enumeration e = vcRulesTable.keys();
        Enumeration ruleEnum;
        CountingTable ct;
        String vowelCluster;
        RewriteRule r;
        filtered.put(new RewriteRule("",""),1.0);
        while(e.hasMoreElements()) {
            vowelCluster = (String) e.nextElement();
            if (word.contains(vowelCluster)){
                ct = (CountingTable) vcRulesTable.get(vowelCluster);
                ruleEnum = ct.getKeys();
                while(ruleEnum.hasMoreElements()) {
                    r = (RewriteRule) ruleEnum.nextElement();
                    filtered.put(r, ct.getObjectCount(r));
                }
            }                
        }
        return filtered;
    }
    public Vector reduceInfix(String word) {
        Vector v = new Vector();
        String infix;
        RewriteRule minInfix = new RewriteRule("","");
        int minIndex = 10000;
        int infixIndex;
        for(int i=0;i<infixList.size();i++) {
            infix = infixList.elementAt(i).getOriginal();
            infixIndex = word.indexOf(infix,1);
            if (infixIndex != -1 && !(infixIndex + infix.length() == word.length())) {
                if (infixIndex < minIndex) {
                    minIndex = infixIndex;
                    minInfix = infixList.elementAt(i);
                }
            }                
        }
        v.add(new RewriteRule("",""));
        if (minIndex != 10000)
            v.add(minInfix);
        return v;        
//        Vector v = new Vector();
//        v.add(new RewriteRule("",""));
//        if (word.length() > 2) {
//            String tempWord = word.substring(1,word.length()-1);
//            for (int i=0;i<infixList.size();i++) {
//                if (tempWord.contains(infixList.elementAt(i).getOriginal()))
//                    v.add(infixList.elementAt(i));
//            }
//        }
//        return v;
    }
    
    /**
     * This makes 'pinagpaliban' into 'pagpaliban'.
     * 'in' was deleted here.
     * It basically hunts for vowels.
     * 
     * Makes use of substrings where word.substring(startingChar, endingChar);
     * @param word
     * Yung word na mababawasan ng something. 
     * @return
     * Examples are 'pi', 'pa'
     * <br>
     * Returns 
     */
    public String reduceRedup(String word) 
    {
        String reducedWord = word;
        int prefixLength=1, maxPrefixLength=0, maxCharOffset=0;
        int i,charOffset=0,j;
        int tempI;
        boolean exited = false;
        boolean hasVowel = false;
       // Laurenz 
        Word redupWord;
        Affix redupAffix;
        
        for ( prefixLength = 1; prefixLength < word.length(); prefixLength++ ) 
        {
            //println("PrefixLength: " + prefixLength + " < " + word.length());
            for( charOffset = 0; charOffset < word.length() - prefixLength; charOffset++ ) 
            {
//              println("    CharOffset: " + charOffset + " < " + (word.length() - prefixLength) );
                exited 		= false;
                hasVowel 	= false;
                j			= 0;

                for( i = 0; i < prefixLength; i++ ) 
                {
                	/*
                	 * During start
                	 *      left = with the value of 1 as prefixLength is instantiated as 1
                	 * 		right = starts with the length of the word
                	 */
                	int left = i + prefixLength + charOffset + j;
//					println("left: " + left);
                	int right = word.length();


//                     println("      " + charAtLeft + " vs " + charAtI);

                	if ( left >= right ) 
                    {
//                        println("      left >= right = true");
                        exited = true;
                        break;                
                    }

					char charAtLeft = word.charAt( left );
					char charAtI  	= word.charAt(i);
                    /*
                     * True when
                     * 		chart at both i and left are both vowels.
                     */
	                if (    charAtI != charAtLeft // if both characters are not the same
                		 && isVowel( charAtI ) == true // if charI is a vowel
                	   	 && isVowel( charAtLeft ) == true // if charLeft is a vowel
	                	 && hasVowel == false // has been set to FALSE at the start
	                	 && i != 0 // has to be more than 1 letter
	                	 )
                    {
                        hasVowel = true; // because i and left characters are both vowels
                        tempI = i;

                        int tempLeft = tempI + prefixLength + charOffset + j;
//                        println("      both vowels true");
                        // while all the letters are vowel
                        while( isVowel( word.charAt(i) ) == true )
                        {
                            i++;
                            if (i >= word.length())
                                break;
                        }
                        while( isVowel( word.charAt( tempLeft ) ) )
                        {
                            j++;
                            tempLeft++;
                            if( tempLeft >= word.length() )
                                break;
                        }
                        i--;
                        j--;
                        continue;
                    }
                    if ( !( word.charAt(i) == word.charAt(i + prefixLength + charOffset + j) ) )
                    {
//                        println("      charAtI != charAtLeft");
						exited= true;
						break;
                    }
                }

                if ( exited == false)
                {
                    maxPrefixLength = prefixLength;
                    maxCharOffset = charOffset;
                    break;
                }
            }
        }
//        println("Removed in reduceRedup(): " + word.substring(0, maxPrefixLength));
//        println("ReduceRedup: " + word.substring(maxPrefixLength));
        String cutWord = word.substring(0, maxPrefixLength);

//		println("=-=-=-=-=-=-=-=-=-=- cutWord: " + cutWord);

        redupAffix = new Affix(cutWord, "prefix");
        if( !cutWord.equals("")) 
        {
        	this.globalPrefix = cutWord;
        	this.globalPrefixList.add(this.globalPrefix);        	
        }

        return word.substring(maxPrefixLength);
    }


    
    
    
    public void extractRuleSemantic1(WordPair wp) {
        Vector<WordSemantic> v = wp.semanticInformations;
        for(int i=0;i<v.size();i++) {
            extractRuleSemantics1(wp.morphed, wp.root, v.elementAt(i));
        }
    }
    public void extractRuleSemantic2(WordPair wp) {
        Vector<WordSemantic> v = wp.semanticInformations;
        for(int i=0;i<v.size();i++) {
            extractRuleSemantics2(wp.morphed, wp.root, v.elementAt(i));
        }
    }    
    public static void main(String args[]) throws Exception{
/*        MorphLearnerRedup mpl = new MorphLearnerRedup();
        System.out.println(mpl.reduceRedup("bibeso"));*/
//        System.setOut(new PrintStream(new FileOutputStream("C:/redupRules.txt")));
        MorphLearnerRedup mpl = new MorphLearnerRedup();
        WordPair wp;
        WordsLoader training = new WordsLoader(WordsLoader.TRAINING);
        double correctCount = 0.0;
        double totalCount = 0.0;
        int count=0;
        HashSet hs;
        while( (wp=training.nextPair()) != null) {
//            System.out.println("Learning: "+ wp.morphed + "-> " + wp.root);
            mpl.extractRule(wp.morphed,wp.root);
//            mpl.extractRuleSemantic2(wp);
        }
        Enumeration e;
//        System.out.println("Done Learning...\nSmoothing tries");
        mpl.smoothTrie();
//        System.out.println("Done smoothing...\nAnalyzing");
//        System.out.println(mpl.popRulesTable.get("mag"));
//        CountingTable ct = mpl.popTrie.getObjectList("mag");
//        e = ct.getKeys();
//        while(e.hasMoreElements()) {
//            RewriteRule r = (RewriteRule) e.nextElement();
//            if (r.getOriginal().equals("mag"))
//                System.out.println(r + "---" + ct.getObjectCount(r));
//        }
/*        System.setOut(new PrintStream(new FileOutputStream("C:/redupModel.txt")));
        System.out.println("----POP----");
        e = mpl.popRulesTable.elements();
        count = 0;
        while(e.hasMoreElements()) {
            hs = (HashSet) e.nextElement();
            count += hs.size();
            System.out.print(hs.size()+ " ");
            System.out.println(hs);
        }
        System.out.println("----POP Count " + count + "----");
        System.out.println("----POS----");
        e = mpl.posRulesTable.elements();
        count = 0;
        while(e.hasMoreElements()) {
            hs = (HashSet) e.nextElement();
            count += hs.size();
            System.out.print(hs.size()+ " ");
            System.out.println(hs);
        }
        System.out.println("----POS Count " + count + "----");
        System.out.println("----Vowel Change----");
        e = mpl.vcRulesTable.elements();
        CountingTable ct;
        count =0;
        while(e.hasMoreElements()) {
            ct = (CountingTable) e.nextElement();
            Enumeration e2 = ct.getKeys();
            while(e2.hasMoreElements()) {
                count++;
                System.out.println(e2.nextElement());
            }
        }
        System.out.println("----Vowel Change Count " + count + "----");*/
//        System.setOut(new PrintStream(new FileOutputStream("C:/redupModelErrors.txt")));        
        WordsLoader eval = new WordsLoader(WordsLoader.TEST);
        MAResult maResult;
        while( (wp=eval.nextPair()) != null) {
            totalCount++;
//            System.out.println(totalCount + ".  " +wp.morphed);            
            try {
                maResult = mpl.analyzeMultipleMod(wp.morphed);
                if (wp.root.equalsIgnoreCase(maResult.result)) {
                    correctCount++;
    //                System.out.println(wp.morphed+"("+wp.root + ")->" + result);                
                }
//                else
//                    System.out.println(wp.morphed+","+wp.root + "," + maResult.result);
            } catch (Exception ex) {
                System.out.println("Error in analyzing word: " + wp.morphed);
                System.out.println(ex.toString());
                ex.printStackTrace();
            }
        }
        System.out.print(correctCount);
//        System.out.println("Result: " + correctCount + "/" + totalCount);
//        System.out.println("Performance: " + correctCount/totalCount);
    }
    
    private static boolean isVowel(char s) {
        int i;
        for(i=0;i<WordInterface.vowels.length;i++)
            if(WordInterface.vowels[i] == s)
                return true;
        return false;
    }       


	public void testHere()
	{

	}
    public static void println(String word)
    {
    	System.out.println("" + word);
    }

    /**
     * Try lang
     */
    class ij
    {
        public String x = "hello";
        public int iji;
        public int ijj;

        public ij(int i, int j)
        {
            iji = i;
            ijj = j;
        }
    }


	class MaxResultObjects
	{
		public boolean lookup;
		public double maxProb;
		public String maxResult;

		public MaxResultObjects() {}

		public MaxResultObjects(double maxProb, String maxResult, boolean lookup)
		{
			this.lookup  	= lookup;
			this.maxProb 	= maxProb;
			this.maxResult  = maxResult;
		}


	}
}