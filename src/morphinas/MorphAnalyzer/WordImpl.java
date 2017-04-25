package morphinas.MorphAnalyzer;
/*
 * WordImpl.java
 *
 * Created on May 3, 2005, 12:41 PM
 */

/**
 *
 * @author Solomon See
 */
import java.util.*;
public abstract class WordImpl implements WordInterface {
    
    protected String sWord;
    protected Tense tense;
    
    /** Creates a new instance of WordImpl */    
    public WordImpl(String sWord, Tense tense) {
        this.sWord =  sWord.toUpperCase();
        this.tense = tense;
    }
    public String getWord() {
        return sWord;
    }
    public Tense getTense() {
        return tense;
    }
    public abstract boolean isRoot();    
    public int hashCode() {
        return sWord.hashCode();
    }
    public boolean equals(Object obj) {
        try {
            return ((WordInterface) obj).getWord().equalsIgnoreCase(sWord) && (((WordInterface) obj).getTense() == tense);
        } catch(Exception e) {
            return false;
        }
    }
    public String toString(){
        return sWord;
    }
    public abstract Enumeration getMorphRules();
    public abstract MorphRule getMorphRule();
    public abstract MorphRule getMorphRule(int index);
}
