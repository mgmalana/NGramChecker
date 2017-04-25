package MorphAnalyzer;
/*
 * RuleLearnerContext.java
 *
 * Created on May 14, 2005, 2:23 PM
 */

/**
 *
 * @author Solomon See
 */
import DataStructures.*;
import java.util.*;
public class RuleExtractorContext {
    public enum Learners { SevenWaySplit, SevenWaySplitMod,SevenWaySplitMod2, SWSVowelMod, AffixModel, SWSInfix, SWSInfix2, SWSRedup };
    private RuleExtractorInterface rei = null;
    private SevenWaySplit sws = null;
    private SWSInfix swsinfix = null;
    private SWSInfix2 swsinfix2 = null;
    private SevenWaySplitModified swsmod = null;
    private SWSRedup swsredup = null;
    private AffixModel affix = null;
    private SevenWaySplitModified2 swsmod2 = null;
    private SWSVowelMod swsvowelmod = null;
    private Trie prefixTrie;
    private SuffixTrie suffixTrie;    
    private Vector<RewriteRule> infixList;
    
    public RuleExtractorContext(Learners l, Trie prefixTrie, SuffixTrie suffixTrie, Vector<RewriteRule> infixList) {
        this.prefixTrie = prefixTrie;
        this.suffixTrie = suffixTrie;
        this.infixList = infixList;
        setExtractor(l);
    }       
    public void setExtractor(Learners l) {
        if(l == Learners.SevenWaySplit) {
            setSevenWaySplit();
        } else if (l == Learners.SevenWaySplitMod) {
            setSevenWaySplitMod();
        } else if (l == Learners.AffixModel) {
            setAffixModel();
        } else if (l == Learners.SevenWaySplitMod2) {
            setSevenWaySplitMod2();
        } else if (l == Learners.SWSVowelMod) {
            setSWSVowelMod();
        } else if (l == Learners.SWSInfix) {
            setSWSInfix();
        } else if (l == Learners.SWSInfix2) {
            setSWSInfix2();
        } else if (l == Learners.SWSRedup) {
            setSWSRedup();
        }
    }
    public String getExtractorName(){
        return rei.getExtractorName();
    }
    public RuleExtractorInterface getExtractor() {
        return rei;
    }
    public MorphRule extractRule(String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic){
        return rei.extractRule(prefixTrie, suffixTrie, infixList, sMorphed, sRoot, morphedSemantic, rootSemantic);
    }
    public MorphRule extractRule(String sMorphed, String sRoot){
        return rei.extractRule(prefixTrie, suffixTrie, infixList, sMorphed, sRoot, null, null);
    }
    public Vector<MorphRule> extractRules(String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic){
        return rei.extractRules(prefixTrie, suffixTrie, infixList, sMorphed,  sRoot, morphedSemantic, rootSemantic);
    }    
    public Vector<MorphRule> extractRules(String sMorphed, String sRoot){
        return rei.extractRules(prefixTrie, suffixTrie, infixList, sMorphed, sRoot, null, null);
    }
    private void setSevenWaySplit(){
        if (sws == null)
            sws = new SevenWaySplit();
        rei = sws;
    }    
    private void setSevenWaySplitMod() {     
        if (swsmod == null)
            swsmod = new SevenWaySplitModified();
        rei = swsmod;
    }
    private void setSevenWaySplitMod2() {
        if (swsmod2 == null)
            swsmod2 = new SevenWaySplitModified2();
        rei = swsmod2;        
    }    
    private void setAffixModel() {
        if (affix == null)
            affix = new AffixModel();
        rei = affix;
    }
    private void setSWSVowelMod() {
        if (swsvowelmod == null)
            swsvowelmod = new SWSVowelMod();
        rei = swsvowelmod;
    }
    private void setSWSInfix() {
        if (swsinfix == null)
            swsinfix = new SWSInfix();
        rei = swsinfix;        
    }        
    private void setSWSInfix2() {
        if (swsinfix2 == null)
            swsinfix2 = new SWSInfix2();
        rei = swsinfix2;        
    }        
    private void setSWSRedup() {
        if (swsredup == null)
            swsredup = new SWSRedup();
        rei = swsredup;
    }
}

