package morphinas.MorphAnalyzer;

/**
 * Created by laurenztolentino on 05/19/2016.
 */
public class TestMaker
{
	String fileDirectory = "/Users/laurenztolentino/Eclipse/workspace/morphinas.Morphinas/src/";
	String fileName		 = "words.txt";


	public TestMaker()
	{}

	/**
	 * When you have a custom fileDirectory and fileName
	 * @param fileDirectory
	 * @param fileName
	 */
	public TestMaker(String fileDirectory, String fileName)
	{
		this.fileDirectory 	= fileDirectory;
		this.fileName 		= fileName;
	}


	public static void println(String input)
	{
		System.out.println("" + input);
	}

}
