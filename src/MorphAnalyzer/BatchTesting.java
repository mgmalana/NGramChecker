/*
 * BatchTesting.java
 *
 * Created on March 7, 2006, 11:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package MorphAnalyzer;

import Preprocessors.*;

/**
 *
 * @author Solomon See
 */
public class BatchTesting {
    
    /** Creates a new instance of BatchTesting */
    public BatchTesting() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        
        for(int i=0; i< 30; i++) {
            System.out.print((i+1));
            MADataCreator.main(args);
            System.out.print(",");
            MorphLearnerOld.main(args);
            System.out.print(",");
            MorphLearnerInfix.main(args);
            System.out.print(",");
            MorphLearnerRedup.main(args);
            System.out.println();
            if ((i+1)%10 == 0)
                System.gc();
        }
    }
}
