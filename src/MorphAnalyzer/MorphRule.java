package MorphAnalyzer;
public interface MorphRule {
    public String getPrefix();
    public String getSuffix();    
    public String getInfix();    
    public String getPartialRedup();
    public String getWholeRedup();
    
    public String getRootPOP();
    public String getRootCommonPrefix();
    public String getRootVowelChange();    
    public String getRootPOS();    
    public String getRootCommonSuffix();
    public Tense getRootTense();    
    
    public String getMorphedPOP();
    public String getMorphedCommonPrefix();   
    public String getMorphedVowelChange();    
    public String getMorphedCommonSuffix();    
    public String getMorphedPOS();
    public Tense getMorphedTense();
    
    public String getTrimmedMorphedWord();
    public int getCommonLength();
    public WordSemantic getSemantic();
    
    public RewriteRule getPrefixRule();
    public RewriteRule getSuffixRule();
    public RewriteRule getInfixRule();
    public RewriteRule getPOPRule();
    public RewriteRule getPOSRule();
    public RewriteRule getVowelChangeRule();
    public RewriteRule getPrefixRuleNoSemantic();
    public RewriteRule getSuffixRuleNoSemantic();
    public RewriteRule getInfixRuleNoSemantic();
    public RewriteRule getPOPRuleNoSemantic();
    public RewriteRule getPOSRuleNoSemantic();
    public RewriteRule getVowelChangeRuleNoSemantic();    
    public RewriteRule getTenseRule();
}