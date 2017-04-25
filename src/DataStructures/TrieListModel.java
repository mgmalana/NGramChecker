/*
 * TrieListModel.java
 *
 * Created on May 16, 2005, 1:06 PM
 */

package DataStructures;

/**
 *
 * @author Solomon See
 */
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.Serializable;
public class TrieListModel extends AbstractListModel implements TrieListener, Serializable {
    Trie trie;
    Vector v;
    /** Creates a new instance of TrieListModel */
    public TrieListModel(Trie t) {
        trie = t;
        t.addListener(this);
        update();
    }    
    public Object getElementAt(int i){
        return v.elementAt(i);
    }    
    public int getSize() {
        return v.size();
    }
    public void update() {
        v = trie.getContents();
    }
    public void dataChanged(type type, String data, Object obj) {
        int size;
        int index;
        if (type == TrieListener.type.add) {
            v.addElement(data);        
            size = v.size();
            this.fireIntervalAdded(this, size-1, size-1);
        } else {
            index = v.indexOf(data);            
            v.remove(data);         
            this.fireIntervalRemoved(this, index, index);
        }
    }    
    public void addData(String data, Object obj) {    
        trie.store(data,obj);
    }
    public void addData(String data) {
        trie.store(data);
    }
    public void removeData(String data, Object obj) {
        trie.remove(data,obj);
    }
    public void removeData(String data) {    
        trie.remove(data);
    }
}
