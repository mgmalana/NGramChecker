package MorphAnalyzer;
/*
 * RuleExtractorInterface.java
 *
 * Created on May 14, 2005, 12:17 PM
 */

/**
 *
 * @author Solomon See
 */
import DataStructures.*;
import java.util.*;
public interface RuleExtractorInterface {
    public MorphRule extractRule(Trie prefixTrie, SuffixTrie suffixTrie, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic);
    public Vector<MorphRule> extractRules(Trie prefixTrie, SuffixTrie suffixTrie, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic);
    public MorphRule extractRule(Trie prefixTrie, SuffixTrie suffixTrie, Vector<RewriteRule> infixList, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic);
    public Vector<MorphRule> extractRules(Trie prefixTrie, SuffixTrie suffixTrie, Vector<RewriteRule> infixList, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic);    
    public String getExtractorName();
}
