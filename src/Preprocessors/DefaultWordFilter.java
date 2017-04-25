/*
 * DefaultWordFilter.java
 *
 * Created on September 6, 2005, 3:59 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Preprocessors;
import DataStructures.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Solomon See
 */
public class DefaultWordFilter implements WordFilterInterface {
    private final static String DEFAULT_DELIMS = ",.?!\"();: ";
    private Trie t;
    private String tempEntity = "";
    private static int ctr=0;
    /** Creates a new instance of DefaultWordFilter */
    public DefaultWordFilter() {
        t = new Trie(new DefaultTrieImpl());
    }
    
    private void addToTrie() {
        t.store(tempEntity.trim());
        tempEntity = "";
    }
    
    public boolean accept(String word) {
        if (DEFAULT_DELIMS.contains(word)) {
            if(!(word.equals(" ") || word.equals(""+((char)160))))
                if (!tempEntity.equals(""))
                    addToTrie();
            return false;
        } else {
            if (Character.getType(word.charAt(0)) == Character.UPPERCASE_LETTER)
                tempEntity += word + " ";
            else if (!tempEntity.equals(""))
                addToTrie();
            return true;
        }
    }
    
    public void writeToFile(String filename) throws Exception {
        PrintStream ps = new PrintStream(new FileOutputStream(filename));
        
        Vector<String> v = t.getContents();
        for(int i=0;i<v.size();i++)
            ps.println(v.elementAt(i));
        
        ps.close();
    }
}
