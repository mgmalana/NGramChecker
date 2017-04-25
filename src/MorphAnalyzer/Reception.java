package MorphAnalyzer;

public class Reception 
{
	MorphLearnerRedup mpl = new MorphLearnerRedup();
    WordsLoader training = new WordsLoader(WordsLoader.TRAINING);
	
    public Reception() throws Exception
    {
    	
    }
    
	public static void main(String[] args)
	{
		try 
		{
			Reception r = new Reception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
