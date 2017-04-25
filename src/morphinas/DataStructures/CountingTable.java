/*
 * CountingTable.java
 *
 * Created on October 5, 2005, 8:57 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package morphinas.DataStructures;
import java.util.*;
import morphinas.MorphAnalyzer.*;
import java.io.Serializable;
/**
 *
 * @author Solomon See
 */
public class CountingTable<E> implements Serializable {
    protected Hashtable<E, Double> objectList;
    /** Creates a new instance of CountingTable */
    public CountingTable() {
    }
    public void storeObject(E obj) {
        Double count;
                   
        if (obj != null) {
            if (objectList == null)
                objectList = new Hashtable();
            count = objectList.get(obj);
            if ( count == null)
                objectList.put(obj, 1.0);
            else
                objectList.put(obj, count+1);
        }
    }
    public void removeObject(E obj) {
        Double count;
        
        if (obj != null) {
            if (objectList != null) {
                count = objectList.get(obj);
                if (count != null) {
                    count = count - 1;
                    if (count != 0)
                        objectList.put(obj, count);
                    else
                        objectList.remove(obj);
                }
            }
        }
    }
    public void computeProbabilites() {
        double total = getTotalCount();
        Enumeration rules = objectList.keys();
        E rule;
        while(rules.hasMoreElements())  {
            rule = (E) rules.nextElement();
            objectList.put(rule, objectList.get(rule) / total);
        }
    }
    public void adjustCounts() {
        Enumeration rules = objectList.keys();
        Hashtable ht = new Hashtable();
        RewriteRule r;
        Double count;
        while(rules.hasMoreElements()) {
            r = (RewriteRule) rules.nextElement();
            count = (Double) ht.get(r.getOriginal());
            if (count == null)
                ht.put(r.getOriginal(), objectList.get(r));
            else
                ht.put(r.getOriginal(), count + (Double) objectList.get(r));
        }
        rules = objectList.keys();
        while(rules.hasMoreElements()) {
            r = (RewriteRule) rules.nextElement();
            count = (Double) ht.get(r.getOriginal());
            objectList.put((E) r, objectList.get(r)/count);
        }
    }
    public double getTotalCount() {
        double total = 0;
        Enumeration<Double> values = objectList.elements();
        while(values.hasMoreElements()) {
            total += values.nextElement();
        }        
        return total;
    }
    public double getHighestRatio() {
        double max = -1;
        double nTemp;
        double nTotal=0.0;
        E tmpObj, maxObj = null;        
        Enumeration<E> keys = objectList.keys();
        while(keys.hasMoreElements()) {
            tmpObj= keys.nextElement();
            nTemp = objectList.get(tmpObj);
            nTotal += nTemp;
            if (nTemp > max) {
                max = nTemp;
                maxObj = tmpObj;
            }
        }
        return max/nTotal;
    }
    public E getHighestObjectCount() {
        double max = -1;
        double nTemp;
        E tmpObj, maxObj = null;        
        Enumeration<E> keys = objectList.keys();
        while(keys.hasMoreElements()) {
            tmpObj= keys.nextElement();
            nTemp = objectList.get(tmpObj);
            if (nTemp > max) {
                max = nTemp;
                maxObj = tmpObj;
            }
        }
        return maxObj;
    }            
    public Enumeration<E> getKeys() {
        if (objectList != null)
            return objectList.keys();
        else
            return null;
    }
    public double getObjectCount(E obj) {
        if (objectList != null) {
            Double i = objectList.get(obj);          
            if (i!=null)
                return i.doubleValue();
            else
                return 0;
        }
        else
            return 0;
    }
    public String displayCount() {
        Enumeration e = objectList.keys();
        RewriteRule r;
        while(e.hasMoreElements()) {
            r = (RewriteRule) e.nextElement();
            System.out.println(r + "->" + objectList.get(r));
        }
        return "";
    }
}
