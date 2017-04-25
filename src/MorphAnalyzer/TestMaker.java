package MorphAnalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by laurenztolentino on 05/19/2016.
 */
public class TestMaker
{
	String fileDirectory = "/Users/laurenztolentino/Eclipse/workspace/Morphinas/src/";
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
