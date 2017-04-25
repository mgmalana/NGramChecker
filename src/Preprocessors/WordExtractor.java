/*
 * WordExtractor.java
 *
 * Created on September 4, 2005, 11:57 AM
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
public final class WordExtractor {
    private final static boolean DEFAULT_INCLUDE_SUBDIRECTORIES = true;
    private final static String DEFAULT_DELIMS = ",.?!\"();: /\\[]<>\n\r+=_'\t"+ ((char) 160)+ ((char) 8212)+ ((char) 8230);
    private static String currFile;
    public static WordFilterInterface wordFilter = null;
    public static PrintStream dashedOutput = null;
 
    public static Trie extractWords(String sourceDirectory) throws Exception {
        return extractWords(sourceDirectory, new Trie(new DefaultTrieImpl()), DEFAULT_INCLUDE_SUBDIRECTORIES);        
    }
    
    public static Trie extractWords(String sourceDirectory, boolean include_subdirectories) throws Exception {
        return extractWords(sourceDirectory, new Trie(new DefaultTrieImpl()), include_subdirectories);        
    }    
    
    public static Trie extractWords(String sourceDirectory, Trie t) throws Exception {
        return extractWords(sourceDirectory,t,DEFAULT_INCLUDE_SUBDIRECTORIES);
    }
    
    public static Trie extractWords(String sourceDirectory, Trie t, boolean include_subdirectories) throws Exception {
        File[] files = getFileList(sourceDirectory);
        FileInputStream fis;
        if (files != null) {
            for(int i=0;i<files.length;i++)
                if (files[i].isFile()) {
                    fis = new FileInputStream(files[i]);
                    currFile = files[i].getAbsolutePath();
                    extractWords(fis, t);
                    fis.close();
                } else if(files[i].isDirectory() && include_subdirectories)
                    extractWords(files[i].getAbsolutePath() , t, include_subdirectories);
        } else 
            throw new RuntimeException(sourceDirectory+" is an invalid directory or is a file.");
            
        return t;
    }
    
    public static void extractWords(String sourceDirectory, String targetFile, Trie t) throws Exception {
        extractWords(sourceDirectory,targetFile,t,DEFAULT_INCLUDE_SUBDIRECTORIES);
    }
    
    public static void extractWords(String sourceDirectory, String targetFile) throws Exception {
        extractWords(sourceDirectory,targetFile,DEFAULT_INCLUDE_SUBDIRECTORIES);
    }    

    public static void extractWords(String sourceDirectory, String targetFile, Trie t, boolean include_subdirectories) throws Exception {
       Vector<String> v = extractWords(sourceDirectory,t, include_subdirectories).getContents();
       writeVectorToFile(v, targetFile);
    }
    
    public static void extractWords(String sourceDirectory, String targetFile, boolean include_subdirectories) throws Exception {
       Vector<String> v = extractWords(sourceDirectory,include_subdirectories).getContents();
       writeVectorToFile(v, targetFile);
    }
    
    private static void writeVectorToFile(Vector<String> v, String targetFile) throws Exception{
       PrintStream ps = new PrintStream(new FileOutputStream(targetFile));
       
       for(int i=0;i<v.size();i++)
            ps.println(v.elementAt(i));
//           ps.println(toCharArray(v.elementAt(i)));
       ps.close();        
    }
    
    private static String toCharArray(String s) {
        String returnString = "";
        for(int i=0;i<s.length();i++)
            returnString += ((int) s.charAt(i)) + " ";
        return returnString;
    }
       
    private static void extractWords(FileInputStream fis, Trie t) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line;
        String word;
        while( (line=reader.readLine()) != null ) {
            StringTokenizer stok = new StringTokenizer(line,DEFAULT_DELIMS, false);
            while(stok.hasMoreTokens()) {                
                word = stok.nextToken();   
                if (word.contains("-")  && dashedOutput != null) {
                    dashedOutput.println(currFile);
                    System.out.println("X");
                }
                if (wordFilter != null) {
                    if (wordFilter.accept(word)) {                        
                        t.store(word.toLowerCase());
                    }
                } else 
                    t.store(word.toLowerCase());
            }
        }
    }
    
    private static File[] getFileList(String sourceDirectory) throws Exception {
        File f = new File(sourceDirectory);
        return f.listFiles();
    } 
    
    public static void main(String args[]) throws Exception {
        File f = new File("C:\\Corpus\\sample.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        System.out.println(toCharArray(br.readLine()));
        System.out.println(toCharArray(br.readLine()));
        System.out.println((char) 8211);
    }
}