/*
 * SemanticTree.java
 *
 * Created on February 17, 2006, 9:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package morphinas.MorphAnalyzer;
import java.util.*;
import morphinas.DataStructures.*;
import java.io.Serializable;
/**
 *
 * @author Solomon See
 */
public class SemanticTree implements Serializable{
    private Hashtable<RewriteRule, Hashtable> prefixTable = new Hashtable();
    private double semanticFactor = 1.0;
    private double discourageFactor = 0.0;
    /** Creates a new instance of SemanticTree */
    public SemanticTree() {
    }
    private void countSemantic(Hashtable ht, RewriteRule r, WordSemantic s) {
        // Count the semantic
        CountingTable ct = (CountingTable) ht.get(r);
        if (ct == null)
            ct = new CountingTable();
        ct.storeObject(s);        
        ht.put(r, ct);
    }
    private Hashtable addRule(Hashtable sourceTable, RewriteRule rewrite) {
        Hashtable ht = (Hashtable) sourceTable.get(rewrite);
        if (ht == null) {
            ht = new Hashtable();
            sourceTable.put(rewrite,ht);
        }
        return ht;
    }
    public SemanticResult getProbability(String prefix, String suffix, String infix, RewriteRule popRule, RewriteRule posRule, RewriteRule vcRule, boolean hasRedup) {
        return getProbability(new RewriteRule(prefix, ""), new RewriteRule(suffix,""), new RewriteRule(infix,""), popRule, posRule, vcRule, hasRedup);
    }    
    public SemanticResult getProbability(RewriteRule prefixRule, RewriteRule suffixRule, RewriteRule infixRule, RewriteRule popRule, RewriteRule posRule, RewriteRule vcRule, boolean hasRedup) {
        RewriteRule[] rr = new RewriteRule[7];
        Hashtable ht = prefixTable;
        CountingTable ct;
        rr[0] = prefixRule;
        rr[1] = suffixRule;
        rr[2] = infixRule;
        if (hasRedup)
            rr[3] = new RewriteRule("yes","");
        else
            rr[3] = new RewriteRule("","");
        rr[4] = popRule;
        rr[5] = posRule;
        rr[6] = vcRule;
        for(int i=0;i<rr.length-1;i++) {
            ht = (Hashtable) ht.get(rr[i]);
            if (ht == null)
                return new SemanticResult(null, semanticFactor * discourageFactor);
        }
        ct = (CountingTable) ht.get(rr[6]);
        if (ct != null)
            return new SemanticResult((WordSemantic)ct.getHighestObjectCount(), semanticFactor * ct.getHighestRatio());
        else
            return new SemanticResult(null, semanticFactor * discourageFactor);
    }
    public void computeProbabilities() {
        Enumeration prefixEnum, suffixEnum, infixEnum, popEnum, posEnum, vcEnum, redupEnum;
        Hashtable<RewriteRule, Hashtable> suffixTable, infixTable, popTable, posTable, redupTable;
        Hashtable vcTable;
        CountingTable ct;
        prefixEnum = prefixTable.keys();
        while(prefixEnum.hasMoreElements()) {
            suffixTable = prefixTable.get(prefixEnum.nextElement());
            suffixEnum = suffixTable.keys();
            while(suffixEnum.hasMoreElements()) {
                infixTable = suffixTable.get(suffixEnum.nextElement());
                infixEnum = infixTable.keys();
                while(infixEnum.hasMoreElements()) {
                    redupTable = infixTable.get(infixEnum.nextElement());
                    redupEnum = redupTable.keys();
                    while(redupEnum.hasMoreElements()) {
                        popTable = redupTable.get(redupEnum.nextElement());
                        popEnum = popTable.keys();
                        while(popEnum.hasMoreElements()) {
                            posTable = popTable.get(popEnum.nextElement());
                            posEnum = posTable.keys();
                            while(posEnum.hasMoreElements()) {
                                vcTable = posTable.get(posEnum.nextElement());
                                vcEnum = vcTable.keys();
                                while(vcEnum.hasMoreElements()) {
                                    ct = (CountingTable) vcTable.get(vcEnum.nextElement());
                                    ct.computeProbabilites();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void addRule(MorphRule r) {
        RewriteRule redupRewrite;
        if (r.getPartialRedup().trim().equals(""))
            redupRewrite = new RewriteRule("","");
        else
            redupRewrite = new RewriteRule("yes","");
        Hashtable suffixTable = addRule(prefixTable, r.getPrefixRuleNoSemantic());
        Hashtable infixTable = addRule(suffixTable, r.getSuffixRuleNoSemantic());
        Hashtable redupTable = addRule(infixTable, r.getInfixRuleNoSemantic());
        Hashtable popTable = addRule(redupTable, redupRewrite);
        Hashtable posTable = addRule(popTable, r.getPOPRuleNoSemantic());
        Hashtable vcTable = addRule(posTable, r.getPOSRuleNoSemantic());        
        countSemantic(vcTable,r.getVowelChangeRuleNoSemantic(),r.getSemantic());
    }
    public static void main(String args[]) {
        SemanticTree st = new SemanticTree();
        SWSRule r = new SWSRule("pagkamulat", "mulat",new WordSemantic("verb", Tense.future), null);
        SWSRule r2 = new SWSRule("pagkamulat", "mulat",new WordSemantic("verb", Tense.present), null);
        r.setPrefix("pagka");
        r2.setPrefix("pagka");
        st.addRule(r);
        st.addRule(r2);
        st.addRule(r);
        st.addRule(r);        
        st.computeProbabilities();
        System.out.print(st.getProbability(new RewriteRule("pagka",""), new RewriteRule("",""), new RewriteRule("",""), new RewriteRule("",""),new RewriteRule("",""),new RewriteRule("",""),true));
        System.out.println();
    }    
}
class SemanticResult {
    WordSemantic sem;
    double probability;
    public SemanticResult(WordSemantic sem, double probability) {
        this.sem = sem;
        this.probability = probability;
    }    
    public String toString() {
        if (sem == null)
            return "null:0";
        return sem.toString() + ":" + probability;
    }
}