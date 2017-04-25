/*
 * Trie.java
 *
 * Created on May 15, 2005, 3:20 PM
 */

package morphinas.DataStructures;

/**
 *
 * @author Solomon See
 */
import java.util.*;
import morphinas.MorphAnalyzer.*;
import java.io.Serializable;


public class Trie<E> implements Serializable
{
    protected AbstractTrieImpl<E> impl = null;
    protected Vector<TrieListener> v = null;

    /** Creates a new instance of Trie */
    public Trie(AbstractTrieImpl<E> impl) {
        this.impl = impl;
    }
    public void addListener(TrieListener listener) {
        if (v == null)
            v = new Vector();
        v.add(listener);
    }
    public void store(String s, E obj) {
        impl.store(s, obj);
        dataChanged(TrieListener.type.add, s, obj);
    }
    public void store(String s){
        impl.store(s);
        dataChanged(TrieListener.type.add, s, null);
    }
    public void remove(String s, E obj) {
        impl.remove(s,obj);
        dataChanged(TrieListener.type.remove, s, obj);
    }
    public void remove(String s){
        impl.remove(s);
        dataChanged(TrieListener.type.remove, s, null);
    }
    public boolean lookup(String s){
        return impl.lookup(s);
    }
    
    /**
     * Still don't know what this thing does.
     * @param s
     * Minsan normal yung s at minsan naka reverse.
     * @return
     * A Vector<String> getAllPossibleMatch(String s) is also returned. So it returns a vector of strings-ish?
     */
    public Vector<String> getAllPossibleMatch(String s) 
    {
//    	println("getAllPossibleMatch("+ s +")");
    	
//    	if (impl.getAllPossibleMatch(s).size() > 0 ) {
//    		println("impl: " + impl.getAllPossibleMatch(s).firstElement());
//    	}
    	
        return impl.getAllPossibleMatch(s);
    }
    
    /**
     * Looks for the longest common substring w/ at most 1 vowel.
     * Looks for the longest canonical prefix/suffix
     * @param s
     * Original input string
     * @return
     * A the longest common substring in String s
     */
    public String getGreatestCommon(String s) 
    {
//    	println("Input s for getGreatestCommon: " + s);	
    	
        DefaultTrieNode node = impl.traverse(s);
        if (node.isEnd())
        {
//        	println("getGreatestCommon s.substring[1]: " + s.substring(0,node.getDepth( )));
            return s.substring(0,node.getDepth());
        }
        else {
            DefaultTrieNode parent;
            parent = node.getParent();
            while (parent != null) {
                if (parent.isEnd())
                    break;
                else
                    parent = parent.getParent();
            }
            if (parent != null)
            {
//            	println("getGreatestCommon s.substring[2]: " + s.substring(0,parent.getDepth()));
                return s.substring(0,parent.getDepth());
            }
            else
            {
                return "";
            }
        }
    }
    public CountingTable getObjectList(String s) {
        return impl.getObjectList(s);
    }
    public Vector<String> getContents() {
        Vector<StringBuffer> vSB = impl.getContents();
        Vector<String> v = new Vector<String>(vSB.size());
        int i;

        for(i=0;i<vSB.size();i++)
            v.addElement(vSB.elementAt(i).toString());
        return v;
    }
    protected void dataChanged(TrieListener.type t, String s, Object obj){
        int i;
        if (v != null)
            for(i=0;i<v.size();i++)
                v.elementAt(i).dataChanged(t,s,obj);
    }
    public void computeProbabilities() {
        DefaultTrieNode currentNode = impl.startNode;
        if (impl.startNode != null)
            currentNode.computeProbabilities();
    }
    public Hashtable possibleMatchList(String word, Hashtable masterRuleList) {
        Hashtable filteredList = new Hashtable();
        DefaultTrieNode currentNode = impl.startNode;
        String partialMatch = "";
        HashSet s;
        Iterator iter;
        Enumeration e;
        RewriteRule r;
        Double count;        
        filteredList.put(new RewriteRule("",""), 1.0);
        if (currentNode != null) {
            s = (HashSet) masterRuleList.get(partialMatch);
            if (s != null) {
                iter = s.iterator();
                while(iter.hasNext()) {                    
                    r = (RewriteRule) iter.next();      
                    count = currentNode.getObjectList().getObjectCount(r);
                    if (count != 0)
                        filteredList.put(r, count);
                }
            }
            for(int i=0;i<word.length();i++) {
                currentNode = (DefaultTrieNode) currentNode.getChild(word.charAt(i));
                if (currentNode != null) {
                    e = filteredList.keys();
                    while(e.hasMoreElements()) {
                        r = (RewriteRule) e.nextElement();
                        count = currentNode.getObjectList().getObjectCount(r);
                        if (count != 0)
                            filteredList.put(r, count);
                    }
                    partialMatch = appendPartialMatch(partialMatch, word.charAt(i));
                    s = (HashSet) masterRuleList.get(partialMatch);
                    if (s != null) {
                        iter = s.iterator();
                        while(iter.hasNext()) {
                            r = (RewriteRule) iter.next();
                            count = currentNode.getObjectList().getObjectCount(r);
                            if (count != 0)
                                filteredList.put(r, count);
                        }
                    }                    
                } else break;
            }
        }
        count = 0.0;
        e = filteredList.elements();
        while(e.hasMoreElements()) {
            count = count + (Double) e.nextElement();
        }
//        System.out.println("Total:" + count);        
        e = filteredList.keys();
        while(e.hasMoreElements()) {
            r = (RewriteRule) e.nextElement();
//            System.out.println("--->"+filteredList.get(r));
            filteredList.put(r, ((Double) filteredList.get(r))/count);
        }
        return filteredList;
    }     
    protected String appendPartialMatch(String s, char c) {
        return s + c;
    }
    
    /**
     * When you are too lazy to type System.out.println()
     * @param input
     */
    public static void println(String input)
    {
    	System.out.println("" + input);
    }
}
