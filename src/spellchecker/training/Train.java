package spellchecker.training;

/*STEMMER
 * STEMMER
 * STEMMER
 * STEMMER
 * STEMMER
 * STEMMER
 * STEMMER
 * 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.BoxOfC.MDAG.MDAG;

import spellchecker.corrector.MergeCorrection;
import spellchecker.corrector.SplitCorrection;
import spellchecker.detector.DictLookUp;
import spellchecker.detector.NGramStatistics;
import spellchecker.stemming.Infixes;
import spellchecker.stemming.PartialReduplication;
import spellchecker.stemming.Prefixes;
import spellchecker.stemming.Stemmer;
import spellchecker.stemming.Suffixes;
import spellchecker.utility.Configuration;

public class Train {

	private MDAG englishDict = null;
	private MDAG filipinoDict = null;
	private DictLookUp dictLookUp = null;
	private NGramStatistics nGramStat = null;
	private MergeCorrection mergeCorrection = null;
	private SplitCorrection splitCorrection = null;

	private Connection c = null;
	private Statement stmt = null;
	private ArrayList<String> collection = new ArrayList<>();

	private Stemmer stemmer = null;
	private Prefixes prefixList = new Prefixes();
	private Suffixes suffixList = new Suffixes();
	private Infixes infixList = new Infixes();
	private PartialReduplication redupRuleList = new PartialReduplication();

	public void trainResource() {
		{
			IOFile ioFile = new IOFile();
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite::resource:spellChecker.db");
				c.setAutoCommit(false);
				System.out.println("Opened database successfully");
				stmt = c.createStatement();

				/**
				 * Putting the English dictionary into the database.
				 */
				LinkedHashSet<String> resource = ioFile.readResource(Configuration.ENGLISH_DICT);
				String sql = "DROP TABLE IF EXISTS ENG_DICT";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE ENG_DICT " + "(WORD TEXT KEY NOT NULL)";
				stmt.executeUpdate(sql);
				stmt = c.createStatement();
				// Storing in database.
				for (String word : resource) {
					sql = "INSERT INTO ENG_DICT (WORD) " + "VALUES ('" + word + "');";
					stmt.executeUpdate(sql);
				}
				System.out.println("Done in English");

				/**
				 * Putting the Filipino dictionary into the database.
				 */
				resource = ioFile.readResource(Configuration.FILIPINO_DICT);
				sql = "DROP TABLE IF EXISTS FILI_DICT";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE FILI_DICT " + "(WORD TEXT KEY NOT NULL)";
				stmt.executeUpdate(sql);
				stmt = c.createStatement();
				// Storing in database.
				for (String word : resource) {
					sql = "INSERT INTO FILI_DICT (WORD) " + "VALUES ('" + word + "');";
					stmt.executeUpdate(sql);
				}
				System.out.println("Done in Filipino");

				/**
				 * Creating stemmed language model.
				 */
				sql = "DROP TABLE IF EXISTS LANG_MODEL";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE LANG_MODEL " + "(WORD TEXT KEY NOT NULL," + "FREQUENCY TEXT NOT NULL" + ")";
				stmt.executeUpdate(sql);
				stmt = c.createStatement();

				// Check what stemmer will be used in the system.
				if (Configuration.LIGHT_STEMMER) {
					stemmer = new Stemmer(prefixList, suffixList);
				} else {
					stemmer = new Stemmer(prefixList, suffixList, infixList, redupRuleList);
				}
				// Getting the stem of the Filipino dictionary.
				resource = stemmer.stemWordList(resource);
				// Generating language model of the stemmed words.
				LanguageModel languageModel = new LanguageModel(resource, Configuration.nGram);
				resource = languageModel.generateNGram();
				// Storing in database.
				for (String word : resource) {
					String[] pair = word.split("\t");
					sql = "INSERT INTO LANG_MODEL (WORD,FREQUENCY) " + "VALUES ('" + pair[0] + "','" + pair[1] + "');";
					stmt.executeUpdate(sql);
				}
				System.out.println("Done in Language Model");

				stmt.close();
				closeDatabase(c);
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}
	}

	public void initializeResource() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = openDatabase();
			stmt = c.createStatement();

			// ENGLISH DICTIONARY
			ResultSet rs = stmt.executeQuery("SELECT * FROM ENG_DICT;");
			String word = rs.getString("WORD");
			collection.add(word);
			englishDict = new MDAG(collection);
			while (rs.next()) {
				word = rs.getString("WORD");
				englishDict.addString(word);
			}
			englishDict.simplify();

			// FILIPINO DICTIONARY
			rs = stmt.executeQuery("SELECT * FROM FILI_DICT;");
			filipinoDict = new MDAG(collection);
			while (rs.next()) {
				word = rs.getString("WORD");
				filipinoDict.addString(word);
			}
			filipinoDict.simplify();

			// LANGUAGE MODEL
			rs = stmt.executeQuery("SELECT * FROM LANG_MODEL;");
			String frequency = "";
			LinkedHashSet<String> nGramList = new LinkedHashSet<>();

			while (rs.next()) {
				word = rs.getString("WORD");
				frequency = rs.getString("FREQUENCY");
				nGramList.add(word + "\t" + frequency);
			}

			nGramStat = new NGramStatistics(nGramList);
			dictLookUp = new DictLookUp(englishDict, filipinoDict);

			// Check what stemmer will be used in the system.
			if (Configuration.LIGHT_STEMMER) {
				stemmer = new Stemmer(prefixList, suffixList);
			} else {
				stemmer = new Stemmer(prefixList, suffixList, infixList, redupRuleList);
			}

			mergeCorrection = new MergeCorrection(stemmer);
			splitCorrection = new SplitCorrection(filipinoDict);

			stmt.close();
			closeDatabase(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Stemmer getStemmer() {
		return stemmer;
	}

	public MDAG getFilipinoDict() {
		return filipinoDict;
	}

	public MergeCorrection getMergeCorrection() {
		return mergeCorrection;
	}

	public SplitCorrection getSplitCorrection() {
		return splitCorrection;
	}

	public DictLookUp getDictLookUp() {
		return dictLookUp;
	}

	public NGramStatistics getNGramStat() {
		return nGramStat;
	}

	private Connection openDatabase() throws SQLException {
		c = DriverManager.getConnection("jdbc:sqlite::resource:spellChecker.db");
		c.setAutoCommit(false);
		System.out.println("open");
		return c;
	}

	private void closeDatabase(Connection c) throws SQLException {
		c.commit();
		c.close();
		System.out.println("close");
	}

	public static void main(String[] args) {
		Train t = new Train();
		t.initializeResource();

	}

}
