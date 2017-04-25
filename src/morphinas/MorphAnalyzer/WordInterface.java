package morphinas.MorphAnalyzer;
public interface WordInterface {   
    public static final char vowels[] = {'a','e','i','o','u'};
    
    public String getWord();
    public Tense getTense();
    public int hashCode();
    public boolean equals(Object obj);
    public String toString();
}