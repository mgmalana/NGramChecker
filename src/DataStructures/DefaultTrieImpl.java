/*
 * DefaultTrieImpl.java
 *
 * Created on May 15, 2005, 3:56 PM
 */

package DataStructures;

/**
 *
 * @author Solomon See
 */
import java.util.*;
import java.io.Serializable;
public class DefaultTrieImpl<E> extends AbstractTrieImpl<E> implements Serializable{
    protected DefaultTrieNode currentNode = null;
    /** Creates a new instance of DefaultTrieImpl */
    public DefaultTrieImpl() {
        super();
    }    
    public void store(String s, E obj){
        int i;
        if (startNode == null) {
            startNode = new DefaultTrieNode('X', obj);
        } else {
            startNode.storeObject(obj);
        }
        currentNode = startNode; 
        for(i=0;i<s.length();i++)
            currentNode = currentNode.addChild(s.charAt(i), obj);
        currentNode.setEnd();
    }
    public boolean lookup(String s){        
        if(traverse(s) != null)
            return currentNode.isEnd() && currentNode.getDepth() == s.length();
        else
            return false;
    }
    public void remove(String s, E obj){
        DefaultTrieNode node = traverse(s), parent = null;
        if (node != null && currentNode.isEnd() && currentNode.getDepth() == s.length()) {
            node.end = false;
            parent = node.parent;            
            while(node != null){    
                node.removeObject(obj);
                if (node.isLeaf() && !node.end)
                    parent.childList.remove(node.value);
                node = parent;
                if (node != null)
                    parent = node.parent;
            }
        }
        currentNode = parent;
    }
    public Vector<String> getAllPossibleMatch(String s) {
        Vector<String> v = new Vector();
        int i = 0;
        DefaultTrieNode parent = null;
        if (startNode == null)
            return v;
        currentNode = startNode;        
        parent = currentNode.parent;
        for(i=0;i<s.length();i++) {            
            parent = currentNode;
            currentNode = currentNode.getChild(s.charAt(i));                        
            if(currentNode == null) {
                currentNode = parent;
                break;
            }
            if (currentNode.isEnd())
                v.add(s.substring(0, currentNode.getDepth()));
        }        
        return v;
    }
    public DefaultTrieNode traverse(String s) {
        int i = 0;
        DefaultTrieNode parent = null;
        if (startNode == null)
            return null;
        currentNode = startNode;        
        parent = currentNode.parent;
        for(i=0;i<s.length();i++) {            
            parent = currentNode;
            currentNode = currentNode.getChild(s.charAt(i));            
            if(currentNode == null) {
                currentNode = parent;
                return currentNode;
            }
        }        
        return currentNode;
    }    
    public CountingTable getObjectList(String s) {
        return traverse(s).getObjectList();
    }
    public Vector<StringBuffer> getContents(){
        if (startNode != null)
            return startNode.getContents();
        else
            return new Vector<StringBuffer>();
    }
}
