package Morphinas.Testing;

/**
 * Created by laurenz on 23/08/2016.
 */
public class Sentence_Test
{

	public static void main(String[] args)
	{
		String sentence = "Nasaan ang_mga tao sa_mga barangay . ";
		String sentence2= sentence.replace("_", " ");
		String[] split1 = sentence2.split(" ");

		for(String word: split1)
		{
			System.out.println(word+ " ");
		}

	}
}
