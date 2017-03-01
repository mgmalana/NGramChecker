package v4.dao;

import junit.framework.TestCase;

/**
 * Created by mgmalana on 3/1/17.
 */
public class DatabaseTest extends TestCase {
    public void testGetConnection() throws Exception {
        DatabaseConnector.getConnection();
    }

    public void testClearDatabase() throws Exception {
        NGramDao nGramDao = new NGramDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");
        nGramDao.clearDatabase();
    }

    public void testAdd() throws Exception {
        NGramDao nGramDao = new NGramDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");
        nGramDao.add("lalalala", "lalalal", "lalallala");
    }

    public void testDelete() throws Exception {

    }

    public void testGet() throws Exception {

    }

    public void testIncrementPOSFrequency() throws Exception {
        NGramDao nGramDao = new NGramDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");
        nGramDao.incrementPOSFrequency("123456789");
    }

    public void testCloseConnection() throws Exception {

    }


}