package morphinas.MorphAnalyzer.Testing;

import morphinas.MorphAnalyzer.DBLexiconSQL;

import java.util.Vector;

/**
 * Created by laurenz on 23/08/2016.
 */
public class DBLexiconSQL_Test
{
	public DBLexiconSQL_Test(){}

	public void testGetAllPosibleMatches() throws Exception
	{
		String word = "malimit";
		Vector result;
		DBLexiconSQL db = new DBLexiconSQL();
		result = db.getAllPossibleMatches(word);
		println(result);
	}

	public static void main(String[] args)
	{
		DBLexiconSQL_Test dbLexiconSQL_test = new DBLexiconSQL_Test();
		try {
			dbLexiconSQL_test.testGetAllPosibleMatches();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void println(Object output)
	{
		System.out.println("" + output.toString());
	}

}
