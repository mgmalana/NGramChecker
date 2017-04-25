/*
 * WordSemantic.java
 *
 * Created on February 10, 2006, 3:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package MorphAnalyzer;

/**
 *
 * @author Solomon See
 */
import java.util.*;
import java.io.Serializable;
public class WordSemantic implements Serializable {
   private Tense tense;
   private String pos;
   /** Creates a new instance of WordSemantic */
   public WordSemantic(String pos, Tense t) {
       this.pos = pos;
       this.tense = t;
   }    
   public String getPOS() {
       return pos;
   }
   public Tense getTense() {
       return tense;
   }
   public String toString() {
       return pos + ":" + tense;
   }
   public boolean equals(Object obj) {
       return toString().equals(obj.toString());
   }
   public int hashCode() {
       return toString().hashCode();
   }
   public static Vector<WordSemantic> parse(String semantic) {
       Vector<WordSemantic> v = new Vector();
       String arr[] = semantic.split(":");
       String pos = "";
       String tense = "";
       String singlePOS;
       pos = arr[0];
       if (arr.length == 2)       
        tense = arr[1];
       arr = pos.split(",");
       for(int i=0; i<arr.length; i++) {           
           singlePOS = arr[i].toLowerCase().trim();
           if (tense.equalsIgnoreCase("past") && singlePOS.equals("verb"))
            v.add(new WordSemantic(arr[i].toLowerCase().trim(),Tense.past));
           else if(tense.equalsIgnoreCase("present") && singlePOS.equals("verb"))
            v.add(new WordSemantic(arr[i].toLowerCase().trim(),Tense.present));
           else if(tense.equalsIgnoreCase("future") && singlePOS.equals("verb"))
            v.add(new WordSemantic(arr[i].toLowerCase().trim(),Tense.future));
           else
            v.add(new WordSemantic(arr[i].toLowerCase().trim(),Tense.none));
       }
       return v;
   }
   public static void main(String args[]) {
       Vector<WordSemantic> v = WordSemantic.parse(" noun, verb :null");
       for(int i=0;i<v.size();i++)
           System.out.println(v.elementAt(i));
   }
}
enum Tense { past, present, future, none }; 