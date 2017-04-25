/*
 * MADataCreator.java
 *
 * Created on January 1, 2006, 10:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package morphinas.Preprocessors;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Solomon See
 */
public class MADataCreator {
    public static void main(String args[]) throws Exception {
        Connection con;
        ResultSet rs;        
        PreparedStatement ps;
        Statement s;
        Vector<Integer> v = new Vector();
        Random r = new Random(System.currentTimeMillis());
        int id;
        int wordcount;
        int testcount;
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	con = DriverManager.getConnection("jdbc:odbc:Words","sa","masterkey");
        s = con.createStatement();
        s.executeUpdate("delete from TestData");
        s.executeUpdate("delete from TrainingData");
        rs = s.executeQuery("select count(*) As wordCount from wordsFiltered");   
        rs.next();
        wordcount = rs.getInt(1);
        testcount = (int) (wordcount * 0.08);
        ps = con.prepareStatement("insert into testdata select * from wordsFiltered where id = ?");
        
        for(int i=0;i<testcount;i++) {   
//            System.out.println(i);
            id = r.nextInt(wordcount) + 1;
            while(v.contains(id)) 
                id = (id % wordcount) + 1;
            v.add(id);
            ps.setInt(1,id);
            ps.addBatch();
        }                 
        ps.executeBatch();
//        con.createStatement().executeUpdate("insert into trainingdata select * from wordsFiltered where not(id in (select id from testdata))");
        con.createStatement().executeUpdate("delete from trainingdata");
        con.createStatement().executeUpdate("insert into trainingdata select * from testdata");
        con.createStatement().executeUpdate("delete from testdata");
        con.createStatement().executeUpdate("insert into testdata select * from testdata7");
        con.createStatement().executeUpdate("delete from trainingdata where words in (select words from testdata)");
        con.createStatement().executeUpdate("delete from trainingdata where words is null");
//        System.out.println("Randomize Training Data Done!");     
        con.close();
    }
}
