package v4.dao;

import junit.framework.TestCase;
import v4.models.NGram;

/**
 * Created by mgmalana on 3/1/17.
 */
public class DatabaseTest extends TestCase {
    NGramDao nGramDao = new NGramDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");


    public void testGetConnection() throws Exception {
        DatabaseConnector.getConnection();
    }

    public void testClearDatabase() throws Exception {
        nGramDao.clearDatabase();
    }

    public void testAdd() throws Exception {
        nGramDao.add("lalalala", "lalalal", "lalallala");
    }

    public void testDelete() throws Exception {
        NGram ngram = new NGram(155500, 1, new String[]{"lalalala"}, null,  38866);
        nGramDao.delete(ngram);
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