/*
 * TrieListener.java
 *
 * Created on May 16, 2005, 3:58 PM
 */

package morphinas.DataStructures;

/**
 *
 * @author Solomon See
 */
public interface TrieListener {
    public enum type { add, remove };
    public void dataChanged(type t, String data, Object obj);
}
