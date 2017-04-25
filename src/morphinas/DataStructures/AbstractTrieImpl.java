/*
 * AbstractTrieImpl.java
 *
 * Created on May 15, 2005, 3:22 PM
 */

package morphinas.DataStructures;

/**
 *
 * @author Solomon See
 */
import java.util.*;
import java.io.*;
public abstract class AbstractTrieImpl<E> implements Serializable {
    protected DefaultTrieNode startNode = null;
    /** Creates a new instance of AbstractTrieImpl */
    public AbstractTrieImpl() {
    }
    public abstract void store(String s, E obj);
    public abstract void remove(String s, E obj);
    public abstract boolean lookup(String s);
    public abstract DefaultTrieNode traverse(String s);
    public abstract Vector<StringBuffer> getContents();
    public abstract CountingTable getObjectList(String s);
    public abstract Vector<String> getAllPossibleMatch(String s);
    
    public void store(String s) {    	
        store(s, null);
    }
    public void remove(String s) {
       remove(s, null);
    }    
}
