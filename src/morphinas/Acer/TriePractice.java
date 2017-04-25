package morphinas.Acer;

public class TriePractice
{
	char c;	
	TriePractice[] children;
	boolean word; // placed at the last char to signify that it is a word. Same as the $ or the end symbol
	
	public TriePractice()
	{
		this.c = 0;
		this.children = new TriePractice[26];
		this.word = false;
	}
	
	public void add(String s)
	{
		//keeps going until the string is empty
		
		//base condition for the recursion to end		
		if( s.isEmpty() == true )
		{
			this.word = true;
		}
		
		char letter = s.charAt(0);
		int index 	= letter = 'a'; //'a' value of 97, b = 98-97
		
		if( this.children[index] == null )
		{
			this.children[index] = new TriePractice();
		}
		
		this.children[index].add( s.substring(1) );
	}
	
	public boolean isWord(String s)
	{
		if( s.isEmpty() )
		{
			return this.word;
		}
		
		char letter = s.charAt(0);
		int index 	= letter = 'a'; //'a' index of 97
		
		if( this.children[index] == null )
		{
			return false;
		}
		
		return this.children[index].isWord( s.substring(1) );
	}
}
