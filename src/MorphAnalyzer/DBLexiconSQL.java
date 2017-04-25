 package MorphAnalyzer;

import java.sql.*;
import java.util.Vector;

import DataStructures.DefaultTrieImpl;
import DataStructures.Trie;

public class DBLexiconSQL {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/morphinas?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "dlsu1234";
	// SQL Returns
	ResultSet rs;   
    PreparedStatement psFindRoot = null;
    Trie t = new Trie(new DefaultTrieImpl());


	public DBLexiconSQL()
	{
		Connection conn = null;
		Statement  stmt = null;
		
		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			psFindRoot = conn.prepareStatement("select * from rootWords");
			rs = psFindRoot.executeQuery();
//	        System.out.println("Loading Lexicon...");
	        while(rs.next())
	            t.store(rs.getString(1));
	        conn.close();
	        conn = null;
	        psFindRoot = null;
	        rs = null;
			
		} catch(SQLException sqlE) {
			
			sqlE.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * Checks if the word exists in the Database
     * @param word
     * @return
     * True if the word is inside the database. False if otherwise. 
     * If the return is True then the word might really be a root word
     * @throws Exception
     */
    public boolean lookup(String word) throws Exception 
    {
//    	System.out.println("lookup result " + t.lookup(word));
        return t.lookup(word);
    }

    public Vector getAllPossibleMatches(String word) throws Exception
	{
		return t.getAllPossibleMatch(word);
	}

    public String getJdbcDriver()
	{
		return this.JDBC_DRIVER;
	}

	public String getDbUrl()
	{
		return this.DB_URL;
	}

	public String getUser()
	{
		return this.USER;
	}

	public String getPass()
	{
		return this.PASS;
	}

	public static boolean staticLookup(String word) throws Exception
	{
		DBLexiconSQL lex = new DBLexiconSQL();
		return lex.lookup(word);
	}

}
