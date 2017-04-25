package morphinas.MorphAnalyzer;
import morphinas.DataStructures.*;
import java.util.*;

// Wicentowski's Approach
public class MorphLearnerOld {
    protected Trie popTrie,posTrie,prefixTrie, vowelChangeTrie;
    protected SuffixTrie suffixTrie;
    protected RuleExtractorContext rec;
    protected CountingTable vowelChange = new CountingTable();
    protected Hashtable popRulesTable = new Hashtable();
    protected Hashtable posRulesTable = new Hashtable();
    protected Hashtable vcRulesTable = new Hashtable();
    private double posConfidence, popConfidence,vowelConfidence;
    protected SemanticTree semanticTree = new SemanticTree();
    private DBLexicon lex;
    
    public MorphLearnerOld() throws Exception{
//        System.out.println("Wicentowski's Basic WF Model");
        lex = new DBLexicon();
        Trie popTrie = new Trie(new DefaultTrieImpl()), prefixTrie = new Trie(new DefaultTrieImpl());
        Trie posTrie = new SuffixTrie(new DefaultTrieImpl()), suffixTrie = new SuffixTrie(new DefaultTrieImpl());                
        Trie vowelChangeTrie = new Trie(new DefaultTrieImpl());
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
        prefixTrie.store("magka");
        prefixTrie.store("magkaka"); 
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
        suffixTrie.store("an");
        suffixTrie.store("in");
        init(prefixTrie, (SuffixTrie) suffixTrie, popTrie,posTrie, vowelChangeTrie);
    }
    public MorphLearnerOld(Trie prefixTrie, SuffixTrie suffixTrie, Trie popTrie, Trie posTrie, Trie vowelChangeTrie) {
        init(prefixTrie, suffixTrie, popTrie,posTrie, vowelChangeTrie);
    }
    private void init(Trie prefixTrie, SuffixTrie suffixTrie, Trie popTrie, Trie posTrie, Trie vowelChangeTrie) {
        this.popTrie = popTrie;
        this.posTrie = posTrie;
        this.prefixTrie = prefixTrie;
        this.suffixTrie = suffixTrie;
        this.vowelChangeTrie = vowelChangeTrie;
        rec = new RuleExtractorContext(RuleExtractorContext.Learners.SWSVowelMod, prefixTrie, (SuffixTrie) suffixTrie, null);        
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
    public void extractRuleSemantic2(String morphed, String orig, WordSemantic morphedSemantic) {        
        MorphRule r = rec.extractRule(morphed,orig,  morphedSemantic, null);
        semanticTree.addRule(r);
        popTrie.store(r.getTrimmedMorphedWord(), r.getPOPRuleNoSemantic());
        addToRulesTable(r.getMorphedPOP(), r.getPOPRuleNoSemantic(), popRulesTable);                
        posTrie.store(r.getTrimmedMorphedWord(), r.getPOSRuleNoSemantic());
        addToRulesTable(r.getMorphedPOS(), r.getPOSRuleNoSemantic(), posRulesTable);
        if (!r.getMorphedVowelChange().equals("")) {
//            vowelChange.storeObject(r.getVowelChangeRule());
            addToRulesCountTable(r.getMorphedVowelChange(), r.getVowelChangeRuleNoSemantic(), vcRulesTable);
        }
        
//        if (r.getMorphedPOP().equals("sasalu-") && r.getRootPOP().equals(""))
//            System.out.println(morphed + "->" + orig);
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
//        if (r.getMorphedPOP().equals("sasalu-") && r.getRootPOP().equals(""))
//            System.out.println(morphed + "->" + orig);
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
    public MAResult analyzeMultipleModSemantic2(String orig) {
        int i,j;
        Vector<String> prefixes = prefixTrie.getAllPossibleMatch(orig);
        Vector<String> suffixes = suffixTrie.getAllPossibleMatch(orig);
        String prefix; 
        String suffix;
        MAResult tempResult = null, maxResult = null;
        // no prefix and suffix
        prefix="";
        suffix = "";
        maxResult = rewriteMultiple2(orig,prefix,suffix,"");       
       // no prefix
        for(j=0;j<suffixes.size();j++) {
            prefix="";
            suffix = suffixes.elementAt(j);
            tempResult = rewriteMultiple2(orig,prefix,suffix,"");
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;                
        }          
       // no suffix
        for(i=0;i<prefixes.size();i++){
            prefix = prefixes.elementAt(i);
            suffix = "";
            tempResult = rewriteMultiple2(orig,prefix,suffix,"");
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
            tempResult = rewriteMultiple2(orig,prefix,suffix,"");
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;
        }                  
        return maxResult;
    }    
    public MAResult analyzeMultiple(String orig) {
        int i,j;
        Vector<String> prefixes = prefixTrie.getAllPossibleMatch(orig);
        Vector<String> suffixes = suffixTrie.getAllPossibleMatch(orig);
        String prefix; 
        String suffix;
        MAResult tempResult = null, maxResult = null;
        // no prefix and suffix
        prefix="";
        suffix = "";
        maxResult = rewriteMultiple(orig,prefix,suffix);       
       // no prefix
        for(j=0;j<suffixes.size();j++) {
            prefix="";
            suffix = suffixes.elementAt(j);
            tempResult = rewriteMultiple(orig,prefix,suffix);
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;                
        }          
       // no suffix
        for(i=0;i<prefixes.size();i++){
            prefix = prefixes.elementAt(i);
            suffix = "";
            tempResult = rewriteMultiple(orig,prefix,suffix);
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
            tempResult = rewriteMultiple(orig,prefix,suffix);
            if (maxResult == null)
                maxResult = tempResult;
            else if(tempResult.confidence >= maxResult.confidence)
                maxResult = tempResult;
        }                  
        return maxResult;
    }
    protected MAResult rewriteMultiple2(String orig, String prefix, String suffix, String infix) {
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
       return new MAResult(maxResult, maxSem, maxProb);        
    }          
    protected MAResult rewriteMultiple(String orig, String prefix, String suffix) {
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
       String maxPrefix= "", maxSuffix="", maxInfix="", maxRedup="";
       RewriteRule maxPOPRule = null, maxPOSRule=null, maxVowelRule=null;
       
       result = orig.substring(prefix.length(), orig.length() - suffix.length());       
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
/*                   System.out.println("Original: " + orig);
                   System.out.println("Trimmed: " + trimmed);
                   System.out.println("Trimmed Canonical: " + trimmedCanonicals);
                   System.out.println("POP Rewrite: " + popRewrite);
                   System.out.println("POS Rewrite: " + posRewrite);
                   System.out.println("VC Rewrite: " + vowelRewrite);          */
                   result = vowelRewrite.middleRewrite(trimmed, RewriteRule.PRIORITY_RIGHT);
                   result = posRewrite.suffixAdd(result);
                   result = popRewrite.prefixAdd(result);
/*                   System.out.println("Result: " + result);
                   System.out.println("--------------------------------");                  */
//                   System.out.print(".");
                   try {
                       if (lex.lookup(result))
                           inLexicon = 1.0;
                       else
                           inLexicon = 0.0001;
                   }catch(Exception e) {
                       e.printStackTrace();
                   }                  
                   finalProb = posConfidence * popConfidence * vowelConfidence * inLexicon;
                   if (maxResult == null) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxPrefix = prefix;
                       maxSuffix = suffix;
                       maxInfix = "";
                       maxRedup = "";
                       maxPOPRule = popRewrite;
                       maxPOSRule = posRewrite;
                       maxVowelRule = vowelRewrite;
                   } else if (maxProb < finalProb) {
                       maxProb = finalProb;
                       maxResult = result;
                       maxProb = finalProb;
                       maxResult = result;
                       maxPrefix = prefix;
                       maxSuffix = suffix;
                       maxInfix = "";
                       maxRedup = "";
                       maxPOPRule = popRewrite;
                       maxPOSRule = posRewrite;
                       maxVowelRule = vowelRewrite;                       
                   }
               }
           }
       }      
       MAResult maResult = new MAResult(maxResult, maxProb);
       maResult.prefix = maxPrefix;
       maResult.suffix = maxSuffix;
       maResult.infix = maxInfix;
       maResult.redup = maxRedup;
       maResult.popRule = maxPOPRule;
       maResult.posRule = maxPOSRule;
       maResult.vowelRule = maxVowelRule;
       return maResult;        
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
       }catch(Exception e) {
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
    public static String reduceRedup(String word) {
        String reducedWord = word;
        int prefixLength=1, maxPrefixLength=0, maxCharOffset=0;
        int i,charOffset=0,j;
        boolean exited = false;
        boolean hasVowel = false;
        for(prefixLength=1;prefixLength<word.length();prefixLength++) {
/*            charOffset = 0;
            while (word.charAt(prefixLength+charOffset) == '-' || word.charAt(prefixLength+charOffset) == ' ')
                charOffset++;*/
            for(charOffset =0;charOffset<word.length()-prefixLength;charOffset++) {
                exited = false;
                hasVowel = false;
                j=0;
                for(i=0;i<prefixLength;i++){                        
                    if (i+prefixLength+charOffset+j >= word.length()) {
                        exited= true;
                        break;                
                    }
                    if (!(word.charAt(i) == word.charAt(i+prefixLength+charOffset+j)) && isVowel(word.charAt(i)) && isVowel(word.charAt(i+prefixLength+charOffset+j)) && !hasVowel && i != 0) {
                        hasVowel = true;
                        while(isVowel(word.charAt(i)))
                            i++;
                        while(isVowel(word.charAt(i+prefixLength+charOffset+j)))
                            j++;
                        i--;
                        j--;                        
                        continue;
                    }
                    
                    if (!(word.charAt(i) == word.charAt(i+prefixLength+charOffset+j))) {
                        exited= true;
                        break;                
                    }
                }
                if (!exited) {
                    maxPrefixLength = prefixLength;
                    maxCharOffset = charOffset;
                    break;
                }
            }
        }
        return word.substring(maxPrefixLength);
    }
    public void extractRuleSemantic2(WordPair wp) {
        Vector<WordSemantic> v = wp.semanticInformations;
        for(int i=0;i<v.size();i++) {
            extractRuleSemantic2(wp.morphed, wp.root, v.elementAt(i));
        }
    }            
    public static void main(String args[]) throws Exception{
 //       System.setOut(new PrintStream(new FileOutputStream("C:/origRules.txt")));
        MorphLearnerOld mpl = new MorphLearnerOld();
        WordPair wp;
        WordsLoader training = new WordsLoader(WordsLoader.TRAINING);
        HashSet hs;
        int count=0;
        double correctCount = 0.0;
        double totalCount = 0.0;
        MAResult maResult;
        while( (wp=training.nextPair()) != null) {
//            System.out.println("Learning: "+ wp.morphed + "-> " + wp.root);
            mpl.extractRule(wp.morphed,wp.root);
//            mpl.extractRuleSemantic2(wp);
        }
        Enumeration e;
//        System.out.println("Done Learning...\nSmoothing tries");
        mpl.smoothTrie();
//        System.out.println("Done smoothing...\nAnalyzing");
/*        System.setOut(new PrintStream(new FileOutputStream("C:/origModels.txt")));
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
//       System.out.println(mpl.analyzeMultiple("pananalita"));
//        System.setOut(new PrintStream(new FileOutputStream("C:/origModelErrors.txt")));
        WordsLoader eval = new WordsLoader(WordsLoader.TEST);
        String result;
        while( (wp=eval.nextPair()) != null) {
            totalCount++;
//            System.out.println(wp.morphed);            
            maResult = mpl.analyzeMultiple(wp.morphed);
            result = maResult.result;
            if (wp.root.equalsIgnoreCase(result)) {
                correctCount++;
//                System.out.println(wp.morphed+"("+wp.root + ")->" + result);                
            }
/*            else
                System.out.println(wp.morphed+","+wp.root + "," + result);*/
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
}