package MorphAnalyzer;
import java.util.*;
import java.io.Serializable;
public class MorphedWord extends WordImpl implements Serializable {
    
    protected MorphRule morphRule = null;
    protected RootWord rootWord = null;
    protected RuleExtractorContext rec = null;
    
    public MorphedWord(String sMorphedWord, Tense morphedTense, RootWord rootWord, RuleExtractorContext rec) {
        super(sMorphedWord, morphedTense);
        this.rootWord = rootWord;
        this.rec = rec;
        learnRule();
    }
    public MorphedWord(String sMorphedWord, Tense morphedTense, String sRootWord, Tense rootTense, RuleExtractorContext rec){
        super(sMorphedWord,morphedTense);
        this.rootWord = new RootWord(sRootWord, rootTense);
        this.rec = rec;
        learnRule();
    }

    public boolean isRoot(){
        return false;
    }
    public Enumeration getMorphRules(){
        Vector v = new Vector();
        v.add(morphRule);
        
        return v.elements();
    }
    public MorphRule getMorphRule(){
        return morphRule;
    }
    public MorphRule getMorphRule(int index){
        return morphRule;
    }
    public void learnRule() {
        if (rec != null) {
            morphRule = rec.extractRule(sWord, rootWord.getWord(), null, null);
            rootWord.addMorphRule(morphRule);
        }
    }
}