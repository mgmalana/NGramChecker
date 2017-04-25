package morphinas.MorphAnalyzer;
/*
 * SWSRule.java
 *
 * Created on May 14, 2005, 12:32 PM
 */

/**
 *
 * @author Solomon See
 */
import java.io.Serializable;
public class SWSRule implements MorphRule, Serializable{
    protected String sMorphedWord;
    protected String sRootWord;
    
    protected String sRootPOP="";
    protected String sRootCommonPrefix="";
    protected String sRootVowelChange="";
    protected String sRootCommonSuffix="";
    protected String sRootPOS="";
    protected String rootPOS="";
    protected WordSemantic rootSemantic = null;
    protected Tense rootTense;
    
    protected String sMorphedPrefix="";
    protected String sMorphedInfix="";
    protected String sMorphedPOP="";
    protected String sMorphedCommonPrefix="";
    protected String sMorphedVowelChange="";
    protected String sMorphedCommonSuffix="";
    protected String sMorphedPOS="";
    protected String sMorphedSuffix="";    
    protected String sPartialRedup="";
    protected String sWholeRedup="";
    protected String morphedPOS="";
    protected WordSemantic morphedSemantic = null;
    protected Tense morphedTense;
    
    protected int commonRootPrefixIndex=0, commonMorphedPrefixIndex=0, commonPrefixLength=0;
    protected int commonRootSuffixIndex=0, commonMorphedSuffixIndex=0, commonSuffixLength=0;
    protected int vowelChangeRootIndex=0, vowelChangeRootLength=0;
    protected int vowelChangeMorphedIndex=0, vowelChangeMorphedLength=0;
    protected int maxCommonLength=0;
    
    public SWSRule(String sMorphed, String sRoot) {
        init(sMorphed, sRoot,null,null);
    }
    public SWSRule(String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic) {
        init(sMorphed, sRoot, morphedSemantic, rootSemantic);
    }
    public SWSRule(MorphedWord morphed, RootWord root) {
        init(morphed.getWord(), root.getWord(), null, null);
    }
    protected void init(String sMorphed, String sRoot, WordSemantic morphedSemantic, WordSemantic rootSemantic){
        sMorphedWord = sMorphed;
        sRootWord = sRoot;
        this.rootSemantic = rootSemantic;
        this.morphedSemantic = morphedSemantic;
        if (rootSemantic != null) {
            this.rootTense = rootSemantic.getTense();
            this.rootPOS = rootSemantic.getPOS();
        }
        else
            this.rootTense = Tense.none;
        if (morphedTense != null) {
            this.morphedTense = morphedSemantic.getTense();        
            this.morphedPOS = morphedSemantic.getPOS();
        }
        else
            this.morphedTense = Tense.none;
    }
    public boolean equals(Object obj) {
        try {
            return ((SWSRule) obj).sRootPOP.equalsIgnoreCase(sRootPOP) &&
                   ((SWSRule) obj).sRootCommonPrefix.equalsIgnoreCase(sRootCommonPrefix) &&
                   ((SWSRule) obj).sRootVowelChange.equalsIgnoreCase(sRootVowelChange) &&
                   ((SWSRule) obj).sRootCommonSuffix.equalsIgnoreCase(sRootCommonSuffix) &&
                   ((SWSRule) obj).sRootPOS.equalsIgnoreCase(sRootPOS) && 
                   ((SWSRule) obj).sMorphedPrefix.equalsIgnoreCase(sMorphedPrefix) &&
                   ((SWSRule) obj).sMorphedPOP.equalsIgnoreCase(sMorphedPOP) &&
                   ((SWSRule) obj).sMorphedCommonPrefix.equalsIgnoreCase(sMorphedCommonPrefix) &&
                   ((SWSRule) obj).sMorphedVowelChange.equalsIgnoreCase(sMorphedVowelChange) &&
                   ((SWSRule) obj).sMorphedCommonSuffix.equalsIgnoreCase(sMorphedCommonSuffix) &&
                   ((SWSRule) obj).sMorphedPOS.equalsIgnoreCase(sMorphedPOS) && 
                   ((SWSRule) obj).sMorphedSuffix.equalsIgnoreCase(sMorphedSuffix) &&
                   (((SWSRule) obj).getInfix().equals(getInfix())) &&
                   (((SWSRule) obj).sPartialRedup.equals(sPartialRedup)) &&
                   (((SWSRule) obj).morphedTense == morphedTense) &&
                   (((SWSRule) obj).rootTense == rootTense);
        }catch(Exception e){
            return false;
        }
    }
    public WordSemantic getSemantic() {
        return morphedSemantic;
    }
    public RewriteRule getPOPRule() {
        return new RewriteRule(getMorphedPOP(), getRootPOP(), morphedSemantic);
    }
    public RewriteRule getPOSRule() {
        return new RewriteRule(getMorphedPOS(), getRootPOS(), morphedSemantic);
    }
    public RewriteRule getVowelChangeRule() {
        return new RewriteRule(getMorphedVowelChange(), getRootVowelChange(), morphedSemantic);
    }
    public RewriteRule getPrefixRule() {
        return new RewriteRule(getPrefix(), "", morphedSemantic);
    }
    public RewriteRule getSuffixRule() {
        return new RewriteRule(getSuffix(), "", morphedSemantic);
    }    
    public RewriteRule getInfixRule() {
        return new RewriteRule(getInfix(),"", morphedSemantic);
    }
    public RewriteRule getPOPRuleNoSemantic() {
        return new RewriteRule(getMorphedPOP(), getRootPOP());
    }
    public RewriteRule getPOSRuleNoSemantic() {
        return new RewriteRule(getMorphedPOS(), getRootPOS());
    }
    public RewriteRule getVowelChangeRuleNoSemantic() {
        return new RewriteRule(getMorphedVowelChange(), getRootVowelChange());
    }
    public RewriteRule getPrefixRuleNoSemantic() {
        return new RewriteRule(getPrefix(), "");
    }
    public RewriteRule getSuffixRuleNoSemantic() {
        return new RewriteRule(getSuffix(), "");
    }    
    public RewriteRule getInfixRuleNoSemantic() {
        return new RewriteRule(getInfix(),"");
    }    
    public RewriteRule getTenseRule() {
        return new RewriteRule(getMorphedTense().toString(), getRootTense().toString());
    }
    public String getMorphedCommonPrefix(){
        return sMorphedCommonPrefix;
    }
    public String getMorphedCommonSuffix(){
        return sMorphedCommonSuffix;
    }    
    public String getMorphedVowelChange() {
        return sMorphedVowelChange;
    }
    public String getPrefix() {
        return sMorphedPrefix;
    }
    public String getMorphedPOP() {
        return sMorphedPOP;
    }
    public String getMorphedPOS() {
        return sMorphedPOS;
    }
    public String getSuffix() {
        return sMorphedSuffix;
    }
    public Tense getMorphedTense() {
        return morphedTense;
    }        
    public String getRootCommonPrefix(){
        return sRootCommonPrefix;
    }
    public String getRootCommonSuffix(){
        return sRootCommonSuffix;
    }    
    public String getRootVowelChange(){
        return sRootVowelChange;
    }
    public String getRootPOP() {
        return sRootPOP;
    }
    public String getRootPOS() {
        return sRootPOS;
    }
    public Tense getRootTense() {
        return rootTense;
    }
    public String getInfix() {
        return sMorphedInfix;
    }
    public String getPartialRedup() {
        return sPartialRedup;
    }
    public String getWholeRedup() {
        return sWholeRedup;
    }
    public String getTrimmedMorphedWord() {
        return sMorphedPOP + sMorphedCommonPrefix + sMorphedVowelChange + sMorphedCommonSuffix + sMorphedPOS;
    }    
    public void setMorphedSemantic(WordSemantic sem) {
        this.morphedSemantic = sem;
    }
    public void setMorphedCommonPrefix(String s){
        sMorphedCommonPrefix = s;
    }
    public void setMorphedCommonSuffix(String s){
        sMorphedCommonSuffix = s;
    }    
    public void setMorphedVowelChange(String s) {
        sMorphedVowelChange = s;
    }
    public void setPrefix(String s) {
        sMorphedPrefix = s;
    }
    public void setInfix(String s) {
        sMorphedInfix = s;
    }
    public void setMorphedPOP(String s) {
        sMorphedPOP = s;
    }
    public void setMorphedPOS(String s) {
        sMorphedPOS = s;
    }
    public void setSuffix(String s) {
        sMorphedSuffix = s;
    }
    public void setMorphedTense(Tense t) {
        morphedTense = t;
    }        
    public void setRootCommonPrefix(String s){
        sRootCommonPrefix = s;
    }
    public void setRootCommonSuffix(String s){
        sRootCommonSuffix = s;
    }    
    public void setRootVowelChange(String s){
        sRootVowelChange = s;
    }
    public void setRootPOP(String s) {
        sRootPOP = s;
    }
    public void setRootPOS(String s) {
        sRootPOS = s;
    }
    public void setRootTense(Tense t) {
        rootTense = t;
    }
    public void setPartialRedup(String s) {
        sPartialRedup = s;
    }
    public void setWholeRedup(String s) {
        sWholeRedup = s;
    }
    public int getCommonLength() {
        if (sMorphedVowelChange.length() > 0)
            return sMorphedCommonPrefix.length() + sMorphedCommonSuffix.length() + 1;
        else
            return sMorphedCommonPrefix.length() + sMorphedCommonSuffix.length();
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Morphed:" + sMorphedPrefix + "/"+ sMorphedPOP + "/" + sMorphedCommonPrefix + "/" + sMorphedVowelChange + "/" + sMorphedCommonSuffix + "/" + sMorphedPOS + "/" + sMorphedSuffix + "/" + morphedTense);
        sb.append("\nRoot   : /" + sRootPOP + "/" + sRootCommonPrefix + "/" + sRootVowelChange + "/" + sRootCommonSuffix + "/" + sRootPOS + "/" + rootTense);
        
        return sb.toString();
    }    
}