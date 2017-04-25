/*
 * SWSInfix2.java
 *
 * Created on January 5, 2006, 3:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package MorphAnalyzer;

/**
 *
 * @author Solomon See
 */
import DataStructures.*;
import java.util.*;

public class SWSInfix implements RuleExtractorInterface{
    
    protected int commonRootPrefixIndex=0, commonMorphedPrefixIndex=0, commonPrefixLength=0;
    protected int commonRootSuffixIndex=0, commonMorphedSuffixIndex=0, commonSuffixLength=0;
    protected int rootPOPIndex=0, rootPOPLength=0, rootPOSIndex=0, rootPOSLength=0;        
    protected int morphedPOPIndex=0, morphedPOPLength=0,morphedPOSIndex=0, morphedPOSLength=0;    
    protected int vowelChangeRootIndex=0, vowelChangeRootLength=0;
    protected int vowelChangeMorphedIndex=0, vowelChangeMorphedLength=0;
    protected String prefix, suffix, infix;
    protected int maxCommonLength=-1;
    protected Trie prefixTrie;
    protected SuffixTrie suffixTrie;
    protected Vector<RewriteRule> infixList;
    
    public MorphRule extractRule(Trie prefixTrie, SuffixTrie suffixTrie, Vector<RewriteRule> infixList, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {
        String trimmedMorphed = "";
        init(prefixTrie, suffixTrie, infixList);
        if (infixList != null)
            trimmedMorphed = extractCanonicalInfix(infixList, sMorphed);
        else
            trimmedMorphed = sMorphed;
        if (prefixTrie != null)
            extractCanonicalPrefix(prefixTrie,trimmedMorphed);
        if (suffixTrie != null)
            extractCanonicalSuffix(suffixTrie,trimmedMorphed);        
        if (trimmedMorphed.substring(morphedPOPIndex,trimmedMorphed.length() - suffix.length()).length() == 0) {
            if (prefixTrie != null)
                extractCanonicalPrefix(prefixTrie,sMorphed);
            if (suffixTrie != null)
                extractCanonicalSuffix(suffixTrie,sMorphed);                    
            infix = "";
            trimmedMorphed = sMorphed;
        }
        if (trimmedMorphed.substring(morphedPOPIndex,trimmedMorphed.length() - suffix.length()).equals(""))
            return new SWSRule(sMorphed, sRoot, morphedSemantic, rootSemantic);
        else
            return extractRuleInfixRemoved(trimmedMorphed, sRoot, morphedSemantic, rootSemantic);        
    }
    public MorphRule extractRuleInfixRemoved(String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {       
//        System.out.println(sMorphed + "->" + sMorphed.substring(morphedPOPIndex,sMorphed.length() - suffix.length()));        
        findLargestCommonSubstringRecursive(sMorphed.substring(morphedPOPIndex,sMorphed.length() - suffix.length()), sRoot);
        return createMorphRule(sMorphed,sRoot,morphedSemantic,rootSemantic);        
    }
    public Vector<MorphRule> extractRules(Trie prefixTrie, SuffixTrie suffixTrie, Vector<RewriteRule> infixList, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {
        Vector<MorphRule> v = new Vector();
        init(prefixTrie, suffixTrie, infixList);
        Vector<String> prefixes = extractCanonicalPrefixes(prefixTrie,sMorphed);
        Vector<String> suffixes = extractCanonicalSuffixes(suffixTrie,sMorphed);
        
        int i,j;
        // no prefix and suffix
        initIndex();
        if (prefix.length() + suffix.length() != sMorphed.length()) {
            findLargestCommonSubstringRecursive(sMorphed.substring(morphedPOPIndex,sMorphed.length() - suffix.length()), sRoot);        
            v.add(createMorphRule(sMorphed,sRoot,morphedSemantic,rootSemantic));                    
        }
        // no suffixes
        for(i=0;i<prefixes.size();i++){
            initIndex();
            prefix = prefixes.elementAt(i);
            morphedPOPIndex = prefix.length();
            if (prefix.length() + suffix.length() != sMorphed.length()) {
                findLargestCommonSubstringRecursive(sMorphed.substring(morphedPOPIndex,sMorphed.length() - suffix.length()), sRoot);        
                v.add(createMorphRule(sMorphed,sRoot,morphedSemantic,rootSemantic));                    
            }
        }             
        // no prefixes
        for(j=0;j<suffixes.size();j++) {
            initIndex();
            suffix = suffixes.elementAt(j);
            if (prefix.length() + suffix.length() != sMorphed.length()) {
                findLargestCommonSubstringRecursive(sMorphed.substring(morphedPOPIndex,sMorphed.length() - suffix.length()), sRoot);        
                v.add(createMorphRule(sMorphed,sRoot,morphedSemantic,rootSemantic));                    
            }
        }            
        // complete prefix and suffix
        for(i=0;i<prefixes.size();i++) 
         for(j=0;j<suffixes.size();j++) {
            initIndex();
            prefix = prefixes.elementAt(i);
            morphedPOPIndex = prefix.length();
            suffix = suffixes.elementAt(j);
            if (prefix.length() + suffix.length() != sMorphed.length()) {
                findLargestCommonSubstringRecursive(sMorphed.substring(morphedPOPIndex,sMorphed.length() - suffix.length()), sRoot);        
                v.add(createMorphRule(sMorphed,sRoot,morphedSemantic,rootSemantic));                    
            }
         }        
        return v;
    }    
    public MorphRule extractRule(Trie prefixTrie, SuffixTrie suffixTrie, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {
        return extractRule(prefixTrie, suffixTrie, new Vector(), sMorphed, sRoot,morphedSemantic,rootSemantic);
    }
    public Vector<MorphRule> extractRules(Trie prefixTrie, SuffixTrie suffixTrie, String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {
        return extractRules(prefixTrie, suffixTrie, new Vector(), sMorphed, sRoot,morphedSemantic,rootSemantic);
    }
    protected Vector<String> extractCanonicalPrefixes(Trie trie, String sMorphed) {        
        return trie.getAllPossibleMatch(sMorphed);
    }
    protected Vector<String> extractCanonicalSuffixes(Trie trie, String sMorphed) {
        return trie.getAllPossibleMatch(sMorphed);
    }
    protected void extractCanonicalPrefix(Trie trie, String sMorphed) {
        prefix = trie.getGreatestCommon(sMorphed);
        morphedPOPIndex = prefix.length();
/*        String sMorphedPOP = sMorphed.substring(morphedPOPIndex,morphedPOPIndex + morphedPOPLength);
        prefix = trie.getGreatestCommon(sMorphedPOP);
        morphedPOPIndex = prefix.length();
        morphedPOPLength = morphedPOPLength - prefix.length();*/
    }
    protected String extractCanonicalInfix(Vector<RewriteRule> infixList, String sMorphed) {
        String infix;
        RewriteRule minInfix = new RewriteRule("","");
        int minIndex = 10000;
        int infixIndex;
        for(int i=0;i<infixList.size();i++) {
            infix = infixList.elementAt(i).getOriginal();
            infixIndex = sMorphed.indexOf(infix,1);
            if (infixIndex != -1 && !(infixIndex + infix.length() == sMorphed.length())) {
                if (infixIndex < minIndex) {
                    minIndex = infixIndex;
                    minInfix = infixList.elementAt(i);
                }
            }                
        }
        this.infix = minInfix.getOriginal();
        return minInfix.infixRemove(sMorphed);
    }
    protected void extractCanonicalSuffix(Trie trie, String sMorphed) {
        suffix = trie.getGreatestCommon(sMorphed);
/*        String sMorphedPOS = sMorphed.substring(morphedPOSIndex,morphedPOSIndex + morphedPOSLength);
        suffix = trie.getGreatestCommon(sMorphedPOS);        
        morphedPOSLength = morphedPOSLength - suffix.length(); */
    }
    protected void initIndex() {
        commonRootPrefixIndex=0; commonMorphedPrefixIndex=0; commonPrefixLength=0;
        commonRootSuffixIndex=0; commonMorphedSuffixIndex=0; commonSuffixLength=0;
        rootPOPIndex=0; rootPOPLength=0; rootPOSIndex=0; rootPOSLength=0;        
        morphedPOPIndex=0; morphedPOPLength=0;morphedPOSIndex=0; morphedPOSLength=0;    
        vowelChangeRootIndex=0; vowelChangeRootLength=0;
        vowelChangeMorphedIndex=0; vowelChangeMorphedLength=0;
        prefix = ""; suffix ="";
        maxCommonLength=-1;        
    } 
    protected void init(Trie prefixTrie, SuffixTrie suffixTrie, Vector<RewriteRule> infixList){
        this.prefixTrie = prefixTrie;
        this.suffixTrie = suffixTrie;
        this.infixList = infixList;
        initIndex();
    }
    protected MorphRule createMorphRule(String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {
        SWSRule rule = new SWSRule(sMorphed,sRoot,morphedSemantic,rootSemantic);
        
        rule.setPrefix(prefix);
        rule.setSuffix(suffix);        
        rule.setInfix(infix);

        rule.setMorphedPOP(sMorphed.substring(morphedPOPIndex,morphedPOPIndex + morphedPOPLength));
        rule.setMorphedCommonPrefix(sMorphed.substring(commonMorphedPrefixIndex, commonMorphedPrefixIndex+commonPrefixLength));
        rule.setMorphedVowelChange(sMorphed.substring(vowelChangeMorphedIndex,vowelChangeMorphedIndex+vowelChangeMorphedLength));
        rule.setMorphedCommonSuffix(sMorphed.substring(commonMorphedSuffixIndex,commonMorphedSuffixIndex+commonSuffixLength));
        rule.setMorphedPOS(sMorphed.substring(morphedPOSIndex,morphedPOSIndex + morphedPOSLength));

        rule.setRootPOP(sRoot.substring(rootPOPIndex,rootPOPIndex+rootPOPLength));
        rule.setRootCommonPrefix(sRoot.substring(commonRootPrefixIndex, commonRootPrefixIndex+commonPrefixLength));
        rule.setRootVowelChange(sRoot.substring(vowelChangeRootIndex,vowelChangeRootIndex+vowelChangeRootLength));
        rule.setRootCommonSuffix(sRoot.substring(commonRootSuffixIndex,commonRootSuffixIndex+commonSuffixLength));
        rule.setRootPOS(sRoot.substring(rootPOSIndex,rootPOSIndex+rootPOSLength));

        return rule;
    }    
    private boolean isVowel(char s) {
        int i;
        for(i=0;i<WordInterface.vowels.length;i++)
            if(WordInterface.vowels[i] == s)
                return true;
        return false;
    }    
    private void findLargestCommonSubstring(String sMorphed, String sRoot, int nRootOffset) {
        int tempLength=0, tempIndex, tempIndex2, maxIter, maxLength;
        int tempRootPrefixIndex=0, tempMorphedPrefixIndex=0, tempPrefixLength=0;
        int maxSuffixLength=0, maxRootSuffixIndex=0, maxMorphedSuffixIndex=0;
        int maxRootPrefixIndex=0, maxMorphedPrefixIndex=0, maxPrefixLength=0;
        int tempSuffixLength=0, tempRootSuffixIndex=0, tempMorphedSuffixIndex=0;        
        int tempRootVowelLength=0, tempRootVowelIndex=0;
        int tempMorphedVowelLength=0, tempMorphedVowelIndex=0;
        int maxMorphedVowelLength=0, maxRootVowelLength=0;
        int maxRootVowelIndex=0, maxMorphedVowelIndex=0;
        int startIndex=0;
        int i,j,k;
        int prevMax;
        int tempAdditionalLength=0;
        boolean hasVowelChange=false;

        prevMax = maxCommonLength;
        maxCommonLength = -1;
        startIndex = Math.abs(sMorphed.length() - sRoot.length());        
        tempIndex = 0;
        tempIndex2 = 0;
        maxLength = Math.min(sMorphed.length(), sRoot.length());
        for(i=startIndex;i>=0;i--) {
            k=0;
            hasVowelChange = false;
            tempRootPrefixIndex = 0;
            tempMorphedPrefixIndex = 0;
            tempPrefixLength = 0;
            tempRootSuffixIndex = 0;
            tempMorphedSuffixIndex = 0;
            tempSuffixLength = 0;                                                
            tempMorphedVowelLength=0; tempRootVowelLength=0;                
            tempLength =0;             
            // i + maxLength
            for(j=i;j<sMorphed.length() && k < sRoot.length();j++,k++) {
                if(sMorphed.charAt(j) == sRoot.charAt(k)) {
                    tempLength++;                        
                } else if (isVowel(sMorphed.charAt(j)) && !hasVowelChange && tempLength > 0){
                    tempPrefixLength = tempLength;
                    tempMorphedPrefixIndex = j - 1;
                    tempRootPrefixIndex = k-1;
                    tempLength++;
                    hasVowelChange = true;

                    tempMorphedVowelLength = 0;                                                
/*                    j--;
                    while(j >=i && isVowel(sMorphed.charAt(j))) {
                        j--;
                        tempPrefixLength--;
                        tempMorphedPrefixIndex--;
                        tempRootPrefixIndex--;
                        tempLength--;
                    }
                    j++;*/
                    tempMorphedVowelIndex = j;                        
                    while(j <sMorphed.length() && isVowel(sMorphed.charAt(j))) {
                        tempMorphedVowelLength++;
                        j++;
                    }   
                    j--;

                    tempRootVowelLength=0;
/*                    k--;
                    while(k>=0 && isVowel(sRoot.charAt(k)))
                        k--;
                    k++;*/
                    tempRootVowelIndex=k;
                    while(k <sRoot.length() && isVowel(sRoot.charAt(k))) {
                        tempRootVowelLength++;
                        k++;
                    }
                    k--;
                } else if (isVowel(sRoot.charAt(k)) && !hasVowelChange && tempLength > 0){
                    tempPrefixLength = tempLength;
                    tempMorphedPrefixIndex = j - 1;
                    tempRootPrefixIndex = k-1;
                    tempLength++;
                    hasVowelChange = true;

                    tempRootVowelLength = 0;
/*                    k--;               
                    while(k>=0 && isVowel(sRoot.charAt(k))) {
                        k--;               
                        tempPrefixLength--;
                        tempMorphedPrefixIndex--;
                        tempRootPrefixIndex--;
                        tempLength--;                            
                    }
                    k++;*/
                    tempRootVowelIndex = k;                        
                    while(k<sRoot.length() && isVowel(sRoot.charAt(k))) {
                        tempRootVowelLength++;
                        k++;
                    }
                    k--;

                    tempMorphedVowelLength=0;
/*                    j--;
                    while(j >=i && isVowel(sMorphed.charAt(j)))
                        j--;
                    j++;*/
                    tempMorphedVowelIndex=j;
                    while(j < sMorphed.length() && isVowel(sMorphed.charAt(j))) {
                        tempMorphedVowelLength++;
                        j++;
                    }
                    j--;
                } else if ( (isVowel(sRoot.charAt(k)) ||  isVowel(sMorphed.charAt(j))) && hasVowelChange){
                    if (tempLength-1-tempPrefixLength == 0) {
                        int ix, jx;
                        ix = tempRootVowelIndex;
                        jx = tempMorphedVowelIndex;
                        tempAdditionalLength = 0;
                        while(ix < sRoot.length() && jx < sMorphed.length() && sRoot.charAt(ix) == sMorphed.charAt(jx)) {
                            ix++;
                            jx++;
                            tempAdditionalLength++;
                            tempMorphedPrefixIndex++;
                            tempRootPrefixIndex++;                            
                        }
                        tempPrefixLength += tempAdditionalLength;
                        tempLength = tempLength - 1 + tempAdditionalLength;
                        j = jx;
                        k = ix;
                        tempRootVowelLength = 0;
                        tempMorphedVowelLength = 0;                        
                        hasVowelChange = false;
                        break;
                    }
                    if(tempLength > maxCommonLength) {
                        maxCommonLength = tempLength;
                        tempIndex = j-1;
                        tempIndex2 = k-1;     
                        maxRootSuffixIndex = tempIndex2;
                        maxMorphedSuffixIndex = tempIndex;
                        maxSuffixLength = tempLength-1-tempPrefixLength;
                        maxRootPrefixIndex = tempRootPrefixIndex;
                        maxMorphedPrefixIndex = tempMorphedPrefixIndex;
                        maxPrefixLength = tempPrefixLength;
                        maxRootVowelIndex = tempRootVowelIndex;
                        maxRootVowelLength = tempRootVowelLength;
                        maxMorphedVowelIndex = tempMorphedVowelIndex;
                        maxMorphedVowelLength = tempMorphedVowelLength;
                    }
                    tempLength = tempLength-1-tempPrefixLength;
                    tempMorphedVowelLength=0; tempRootVowelLength=0;
                    hasVowelChange=false;                
                    j--;k--;
                } else if ( ((!isVowel(sMorphed.charAt(j)) && !isVowel(sRoot.charAt(k))) || (isVowel(sMorphed.charAt(j)) && !isVowel(sRoot.charAt(k))) || (!isVowel(sMorphed.charAt(j)) && isVowel(sRoot.charAt(k))) ) && !hasVowelChange){
                    if(tempLength > maxCommonLength) {
                        maxCommonLength = tempLength;
                        tempIndex = j-1;
                        tempIndex2 = k-1;     
                        maxRootPrefixIndex = tempIndex2;
                        maxMorphedPrefixIndex = tempIndex;
                        maxPrefixLength = tempLength;
                        maxSuffixLength=0;
                        maxRootVowelLength=0;
                        maxMorphedVowelLength=0;
                    }
                    tempRootPrefixIndex = 0;
                    tempMorphedPrefixIndex = 0;
                    tempPrefixLength = 0;
                    tempRootSuffixIndex = 0;
                    tempMorphedSuffixIndex = 0;
                    tempSuffixLength = 0;                        
                    tempMorphedVowelLength=0; tempRootVowelLength=0;
                    tempLength =0;
                } else if ( ((!isVowel(sMorphed.charAt(j)) && !isVowel(sRoot.charAt(k))) || (isVowel(sMorphed.charAt(j)) && !isVowel(sRoot.charAt(k))) || (!isVowel(sMorphed.charAt(j)) && isVowel(sRoot.charAt(k))) ) && hasVowelChange) {
                    if (tempLength-1-tempPrefixLength == 0) {
                        int ix, jx;
                        ix = tempRootVowelIndex;
                        jx = tempMorphedVowelIndex;
                        tempAdditionalLength = 0;
                        while(ix < sRoot.length() && jx < sMorphed.length() && sRoot.charAt(ix) == sMorphed.charAt(jx)) {
                            ix++;
                            jx++;
                            tempAdditionalLength++;
                            tempMorphedPrefixIndex++;
                            tempRootPrefixIndex++;                            
                        }
                        tempPrefixLength += tempAdditionalLength;
                        tempLength = tempLength - 1 + tempAdditionalLength;
                        j = jx;
                        k = ix;                        
                        tempRootVowelLength = 0;
                        tempMorphedVowelLength = 0;     
                        hasVowelChange = false;                       
                        break;
                    }                    
                    if(tempLength > maxCommonLength) {
                        maxCommonLength = tempLength;
                        tempIndex = j-1;
                        tempIndex2 = k-1;     
                        maxRootSuffixIndex = tempIndex2;
                        maxMorphedSuffixIndex = tempIndex;
                        maxSuffixLength = tempLength-1-tempPrefixLength;
                        maxRootPrefixIndex = tempRootPrefixIndex;
                        maxMorphedPrefixIndex = tempMorphedPrefixIndex;
                        maxPrefixLength = tempPrefixLength;                            
                        maxRootVowelIndex = tempRootVowelIndex;
                        maxRootVowelLength = tempRootVowelLength;
                        maxMorphedVowelIndex = tempMorphedVowelIndex;
                        maxMorphedVowelLength = tempMorphedVowelLength;
                    }
                    tempRootPrefixIndex = 0;
                    tempMorphedPrefixIndex = 0;
                    tempPrefixLength = 0;
                    tempRootSuffixIndex = 0;
                    tempMorphedSuffixIndex = 0;
                    tempSuffixLength = 0;                                                
                    tempMorphedVowelLength=0; tempRootVowelLength=0;
                    hasVowelChange = false;
                    tempLength =0;                        
                }
            }            
            if (tempLength-1-tempPrefixLength == 0 && hasVowelChange) {
                int ix, jx;
                ix = tempRootVowelIndex;
                jx = tempMorphedVowelIndex;
                tempAdditionalLength = 0;
                while(ix < sRoot.length() && jx < sMorphed.length() && sRoot.charAt(ix) == sMorphed.charAt(jx)) {
                    ix++;
                    jx++;
                    tempAdditionalLength++;
                    tempMorphedPrefixIndex++;
                    tempRootPrefixIndex++;
                }
                tempPrefixLength += tempAdditionalLength;
                tempLength = tempLength - 1 + tempAdditionalLength;
                j = jx;
                k = ix;
                tempRootVowelLength = 0;
                tempMorphedVowelLength = 0;             
                hasVowelChange = false;
            }              
            if(tempLength > maxCommonLength && hasVowelChange) {             
                maxCommonLength = tempLength;
                tempIndex = j-1;
                tempIndex2 = k-1;     
                maxRootSuffixIndex = tempIndex2;
                maxMorphedSuffixIndex = tempIndex;
                maxSuffixLength = tempLength-1-tempPrefixLength;
                maxRootPrefixIndex = tempRootPrefixIndex;
                maxMorphedPrefixIndex = tempMorphedPrefixIndex;
                maxPrefixLength = tempPrefixLength;                            
                maxRootVowelIndex = tempRootVowelIndex;
                maxRootVowelLength = tempRootVowelLength;
                maxMorphedVowelIndex = tempMorphedVowelIndex;
                maxMorphedVowelLength = tempMorphedVowelLength;
            } else if (tempLength > maxCommonLength && !hasVowelChange) {
                maxCommonLength = tempLength;
                tempIndex = j-1;
                tempIndex2 = k-1;     
                maxRootPrefixIndex = tempIndex2;
                maxMorphedPrefixIndex = tempIndex;
                maxPrefixLength = tempLength;
                maxSuffixLength=0;
                maxRootVowelLength=0;
                maxMorphedVowelLength=0;
            }            
        }
        
        if (maxCommonLength > prevMax) {
            commonPrefixLength = maxPrefixLength;
            commonSuffixLength = maxSuffixLength;
            commonRootPrefixIndex = maxRootPrefixIndex - maxPrefixLength + 1 + nRootOffset;
            commonRootSuffixIndex = maxRootSuffixIndex - maxSuffixLength + 1 + nRootOffset;
            commonMorphedPrefixIndex = maxMorphedPrefixIndex - maxPrefixLength + 1 + prefix.length();
            commonMorphedSuffixIndex = maxMorphedSuffixIndex - maxSuffixLength + 1 + prefix.length();
            vowelChangeRootIndex= maxRootVowelIndex + nRootOffset;
            vowelChangeMorphedIndex = maxMorphedVowelIndex + prefix.length();
            vowelChangeRootLength = maxRootVowelLength;
            vowelChangeMorphedLength = maxMorphedVowelLength;
        } else {
            maxCommonLength = prevMax;
        }
        if (commonSuffixLength == 0) {
//            vowelChangeRootLength = Math.min(vowelChangeRootLength, vowelChangeMorphedLength);
//            vowelChangeMorphedLength = vowelChangeRootLength;
        }

//        return maxCommonLength;
    }
    protected void findLargestCommonSubstringRecursive(String sMorphed, String sRoot){
        int temp;
        int i,j;
               
        if (sMorphed.length() >= sRoot.length()) {
//            findLargestCommonSubstring(sMorphed, sRoot.substring(0,sRoot.length()),0);
            for(i=0;i<sRoot.length()-1;i++)
                findLargestCommonSubstring(sMorphed, sRoot.substring(i,sRoot.length()),i);
            for(i=0;i<=sRoot.length();i++)
                findLargestCommonSubstring(sMorphed, sRoot.substring(0,sRoot.length()-i),0);
        } else {
//            findLargestCommonSubstring(sRoot, sMorphed.substring(0,sMorphed.length()),0);
            for(i=0;i<sMorphed.length();i++)
                findLargestCommonSubstring(sRoot, sMorphed.substring(i,sMorphed.length()),i);
            for(i=0;i<sMorphed.length();i++)
                findLargestCommonSubstring(sRoot, sMorphed.substring(0,sMorphed.length()-i),0);

            temp = commonRootPrefixIndex;
            commonRootPrefixIndex = commonMorphedPrefixIndex;
            commonMorphedPrefixIndex = temp;
            
            temp = commonRootSuffixIndex;
            commonRootSuffixIndex = commonMorphedSuffixIndex;
            commonMorphedSuffixIndex = temp;
            
            
            temp = vowelChangeRootIndex;
            vowelChangeRootIndex = vowelChangeMorphedIndex;
            vowelChangeMorphedIndex = temp;
            
            temp = vowelChangeRootLength;
            vowelChangeRootLength = vowelChangeMorphedLength;
            vowelChangeMorphedLength = temp;            
            
            commonRootPrefixIndex -= prefix.length();
            commonMorphedPrefixIndex += prefix.length();
            
            commonRootSuffixIndex -= prefix.length();
            commonMorphedSuffixIndex += prefix.length();
            
            vowelChangeRootIndex -= prefix.length();
            vowelChangeMorphedIndex += prefix.length();
        }
        
        if (vowelChangeMorphedLength == 0) {
            vowelChangeRootLength = 0;
            commonSuffixLength = 0;
        } else if(sMorphed.substring(vowelChangeMorphedIndex - prefix.length(), vowelChangeMorphedIndex - prefix.length() + vowelChangeMorphedLength).startsWith(sMorphed.substring(commonMorphedPrefixIndex - prefix.length(), commonMorphedPrefixIndex- prefix.length()+commonPrefixLength))){
            commonMorphedPrefixIndex = vowelChangeMorphedIndex;
            vowelChangeMorphedIndex += commonPrefixLength;
            vowelChangeMorphedLength -= commonPrefixLength;
            if (vowelChangeMorphedLength == 0) {
                commonPrefixLength += commonSuffixLength;
                commonSuffixLength = 0;
            }                
        }
        
        if(commonSuffixLength != 0 ) {
            morphedPOSIndex = commonMorphedSuffixIndex + commonSuffixLength;            
            rootPOSIndex = commonRootSuffixIndex + commonSuffixLength;            
        } else if (vowelChangeRootLength != 0 || vowelChangeMorphedLength != 0) {
            morphedPOSIndex = vowelChangeMorphedIndex + vowelChangeMorphedLength;
            rootPOSIndex = vowelChangeRootIndex + vowelChangeRootLength;
        } else {
            morphedPOSIndex = commonMorphedPrefixIndex + commonPrefixLength;
            rootPOSIndex = commonRootPrefixIndex + commonPrefixLength;
        }        

        rootPOSLength = sRoot.length() -rootPOSIndex;
        morphedPOSLength = sMorphed.length() + prefix.length() -morphedPOSIndex;
        rootPOPLength = commonRootPrefixIndex - rootPOPIndex;
        morphedPOPLength = commonMorphedPrefixIndex - morphedPOPIndex;
    }
    public String getExtractorName(){
        return "Seven Way Split Modified with reduction to empty vowels";
    }
}

