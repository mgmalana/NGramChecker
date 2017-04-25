package MorphAnalyzer;
import java.util.*;
public class RootWord extends WordImpl {

    protected Vector<MorphRule> vMorphRules = new Vector();
    
    public RootWord(String sWord, Tense tense) {
        super(sWord, tense);
    }
    
    public boolean isRoot(){
        return true;
    }
    public Enumeration getMorphRules(){
        return vMorphRules.elements();
    }
    public MorphRule getMorphRule(){
        if (vMorphRules.size() > 0)
            return vMorphRules.elementAt(0);
        else
            return null;
    }
    public MorphRule getMorphRule(int index) {
        if(vMorphRules.size() > index)
            return vMorphRules.elementAt(index);
        else
            return null;
    }
    public void addMorphRule(MorphRule morphRule) {
        if (!vMorphRules.contains(morphRule))
            vMorphRules.add(morphRule);
    }
    public void removeMorphRule(MorphRule morphRule) {
        if(!vMorphRules.contains(morphRule))
            vMorphRules.remove(morphRule);
    }
    public void removeMorphRule(int index) {
        if (vMorphRules.size() > index)
            vMorphRules.removeElementAt(index);        
    }
    public MorphedWord applyMorphRule(MorphRule morphRule) throws RuntimeException{
        throw new RuntimeException("Feature not implemented yet.");
    }
}