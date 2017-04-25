/*
 * RewriteRule.java
 *
 * Created on June 26, 2005, 1:24 PM
 */

package MorphAnalyzer;

/**
 *
 * @author Solomon See
 */
import java.io.Serializable;

public class RewriteRule implements Serializable
{
    protected String original;
    protected String rewrite;
    protected WordSemantic semantic;
    public static final int PRIORITY_LEFT = 0;
    public static final int PRIORITY_RIGHT = 1;
    /** Creates a new instance of RewriteRule */
    public RewriteRule(String original, String rewrite) {
        this.original = original;
        this.rewrite = rewrite;
        this.semantic = null;
    }
    
    public RewriteRule(String original, String rewrite, WordSemantic semantic) {
        this.original = original;
        this.rewrite = rewrite;
        this.semantic = semantic;
    }
    
    public String getOriginal() {
        return original;
    }
    
    public String getRewrite() {
        return rewrite;
    }
    public boolean isEmpty() {
        return original.equals("") && rewrite.equals("");
    }
    public WordSemantic getSemantic() {
        return semantic;
    }
    
    public String toString() {
        if (semantic != null)
            return original + "->" + rewrite + ":" + semantic.toString();
        else
            return original + "->" + rewrite + ":null";
    }
    
    public String prefixRewrite(String word) {
        if (word.startsWith(original))
            return rewrite + word.substring(original.length());
        else
            return word;
    }
    
    public String prefixRemove(String word) {
        if (word.startsWith(original))
            return word.substring(original.length());
        else
            return word;
    }    
    
    public String prefixAdd(String word) {
        return rewrite + word;
    }
    
    public String suffixRewrite(String word) {
        if (word.endsWith(original))
            return word.substring(0, word.length()-original.length()) + rewrite;
        else
            return word;
    }    
    
    public String suffixRemove(String word) {
        if (word.endsWith(original))
            return word.substring(0, word.length()-original.length());
        else
            return word;        
    }
    
    public String suffixAdd(String word) {
        return word + rewrite;
    }
    
    public String middleRewrite(String word, int priority) {
        int i;
        int origIndex = 0;
        int returnIndex = -1;
        int origLength = original.length();
        StringBuffer tempOrig = new StringBuffer(original), tempRewrite = new StringBuffer(rewrite);
        StringBuffer tempWord = new StringBuffer(word);
        if(priority == PRIORITY_RIGHT) {
            tempWord = tempWord.reverse();
            tempOrig = tempOrig.reverse();
            tempRewrite = tempRewrite.reverse();
        }
        tempWord = new StringBuffer(tempWord.toString().replaceFirst(tempOrig.toString(),tempRewrite.toString()));
        if (priority == PRIORITY_RIGHT)
            return tempWord.reverse().toString();
        else
            return tempWord.toString();
    }
    
    public String middleRemove(String word, int priority) {
        int i;
        int origIndex = 0;
        int returnIndex = -1;
        int origLength = original.length();
        StringBuffer tempOrig = new StringBuffer(original), tempRewrite = new StringBuffer("");
        StringBuffer tempWord = new StringBuffer(word);
        if(priority == PRIORITY_RIGHT) {
            tempWord = tempWord.reverse();
            tempOrig = tempOrig.reverse();
            tempRewrite = tempRewrite.reverse();
        }
        tempWord = new StringBuffer(tempWord.toString().replaceFirst(tempOrig.toString(),tempRewrite.toString()));
        if (priority == PRIORITY_RIGHT)
            return tempWord.reverse().toString();
        else
            return tempWord.toString();
    }    
    
    public String infixRemove(String word) {
        if (word.length() > 2) {
            String tempWord = word.substring(1,word.length()-1);
            if (tempWord.contains(original))
                return word.charAt(0) + middleRemove(tempWord, RewriteRule.PRIORITY_LEFT) + word.charAt(word.length()-1);
        }
        return word;        
    }
    
    public String infixRewrite(String word) {
        String tempWord = word.substring(1,word.length()-1);
        if (tempWord.contains(original))
            return word.charAt(0) + middleRewrite(tempWord, RewriteRule.PRIORITY_LEFT) + word.charAt(word.length()-1);
        return word;
    }
    
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
    
    public int hashCode() {
        return toString().hashCode();
    }
}