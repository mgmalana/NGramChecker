package v4.dao;

import junit.framework.TestCase;
import v4.models.NGram;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mgmalana on 3/1/17.
 */
public class DatabaseTest extends TestCase {
    NGramDao nGramDao = new NGramDao(2, "bigram", "bigram_pos_frequency", "pos_bigram_index");


    public void testGetConnection() throws Exception {
        DatabaseConnector.getConnection();
    }

    /**
     *The following are tests are NGramDao
     *
     */

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
        NGram ngram = nGramDao.get(21192);
    }

    public void testIncrementPOSFrequency() throws Exception {
        nGramDao.incrementPOSFrequency("lalallala");
    }

    public void testGetPOSFreqID() throws Exception {
        System.out.println(nGramDao.getPOSFreqID("lalallala"));
    }

    public void testGetSimilarNGrams() throws Exception {
        List ngrams = nGramDao.getSimilarNGrams(0, 1);
    }

    public void testGetGeneralizedNGrams() throws Exception {
        HashMap<Integer, NGram> ngrams = nGramDao.getGeneralizedNGrams();
    }

    public void testSetIsPOSGeneralized() throws Exception {
        nGramDao.setIsPOSGeneralized(155494, "true");
    }

    public void testGetPOS() throws Exception {
        String[] POS = nGramDao.getPOS(38857);
    }

    public void testSetIsPOSGeneralizedBatch() throws Exception {
        //looks like this is working
    }
}