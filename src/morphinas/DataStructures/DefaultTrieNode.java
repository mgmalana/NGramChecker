/*
 * DefaultTrieNode.java
 *
 * Created on May 15, 2005, 3:58 PM
 */

package morphinas.DataStructures;

/**
 *
 * @author Solomon See
 */
import java.util.*;
import java.io.Serializable;
public class DefaultTrieNode implements Serializable{
    protected char value;
    protected DefaultTrieNode parent;
    protected Hashtable<Character, DefaultTrieNode> childList;
//    protected Hashtable<Object, Integer> objectList;
    protected CountingTable objectList;
    protected boolean end = false;
    protected int depth;
    
    /** Creates a new instance of DefaultTrieNode */
    public DefaultTrieNode(DefaultTrieNode parent, char value, Object obj) {
        init(parent,value, obj);
    }

    public DefaultTrieNode(DefaultTrieNode parent, char value) {
        init(parent,value, null);
    }
    
    public DefaultTrieNode(char value, Object obj) {
        init(null,value,obj);
    }
    
    public DefaultTrieNode(char value){
        init(null,value, null);
    }    
    public void init(DefaultTrieNode parent, char value, Object obj) {
        this.parent = parent;
        this.value=value;        
        
        storeObject(obj);
              
        childList = new Hashtable();
        if (parent == null)
            depth = 0;
        else
            depth = parent.depth + 1;        
    }
    public void computeProbabilities() {
        Enumeration e;
        if (objectList != null) {
            objectList.computeProbabilites();
        }
        e = childList.keys();
        while(e.hasMoreElements())
            childList.get(e.nextElement()).computeProbabilities();
    }
    protected void storeObject(Object obj) {
        if (obj != null) {
            if (objectList == null)
                objectList = new CountingTable();
            objectList.storeObject(obj);
        }
    }
    public CountingTable getObjectList() {
        return objectList;
    }
    public DefaultTrieNode addChild(char value) {
        DefaultTrieNode child = childList.get(value);
        if(child==null) {
            child = new DefaultTrieNode(this,value);
            childList.put(value, child);
        }
        return child;
    }
    
    public DefaultTrieNode addChild(char value, Object obj) {
        DefaultTrieNode child = addChild(value);
        child.storeObject(obj);
        return child;
    }    
    
    protected void removeObject(Object obj) {       
        if (obj != null) {
            if(objectList == null)
                objectList = new CountingTable();
            objectList.removeObject(obj);
        }
    }
   
    public DefaultTrieNode getChild(char value) {
        return childList.get(value);
    }
    public DefaultTrieNode getChild(DefaultTrieNode node) {
        return childList.get(node);
    }
    public DefaultTrieNode getParent(){
        return parent;
    }
    public boolean isEnd() {
        return end;
    }
    public void setEnd(){
        end = true;
    }
    public boolean isLeaf() {
        return childList.size()==0;
    }
    public int getDepth() {
        return depth;
    }
    public char getValue(){
        return value;
    }
    public Vector<StringBuffer> getContents() {
        int i;
        Vector<StringBuffer> v = new Vector<StringBuffer>();
        Vector<StringBuffer> returnVector = new Vector<StringBuffer>();
        String s;
        if (childList.size() != 0) {
            Enumeration<DefaultTrieNode> e = childList.elements();
            while(e.hasMoreElements()){
                v.addAll(e.nextElement().getContents());
            }
            if (parent != null) {
                for(i=0;i<v.size();i++) {
                    returnVector.add( v.elementAt(i).insert(0,value) );
                }            
            } else 
                returnVector = v;        
        }
        if (end) {
            returnVector.add(new StringBuffer(""+value));
        }
        return returnVector;
    }
}
