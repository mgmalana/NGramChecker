package MorphAnalyzer;
/*
 * RuleExtractorFrame.java
 *
 * Created on May 14, 2005, 3:57 PM
 */

/**
 *
 * @author  Solomon See
 */
import DataStructures.*;
import java.util.*;
import javax.swing.*;
public class RuleExtractorFrame extends JFrame {
    private Trie prefixTrie = new Trie(new DefaultTrieImpl());
    private Trie infixTrie=null;
    private SuffixTrie suffixTrie = new SuffixTrie(new DefaultTrieImpl());
    private Vector<RewriteRule> infixList = new Vector();
    private RuleExtractorContext rec = new RuleExtractorContext(RuleExtractorContext.Learners.SWSRedup,prefixTrie,suffixTrie, infixList);
    private MorphRuleTableModel rtbl = new MorphRuleTableModel(new SWSRule("","",null,null));
    private TrieListFrame prefixFrame, suffixFrame;
    /** Creates new form RuleExtractorFrame */
    public RuleExtractorFrame() {
        initComponents();
        setSize(750,250);
        ruleDisplayTable.setModel(rtbl);       
        prefixFrame = new TrieListFrame(prefixTrie, "List of prefixes");
        suffixFrame = new TrieListFrame(suffixTrie, "List of suffixes");
        prefixFrame.setVisible(true);
        suffixFrame.setVisible(true);
               
        prefixTrie.store("nag");
        prefixTrie.store("mag");
        prefixTrie.store("na");
        prefixTrie.store("ma");
        prefixTrie.store("i");
        prefixTrie.store("pag"); 
        prefixTrie.store("pa");
        prefixTrie.store("um");
        prefixTrie.store("in");
        prefixTrie.store("ka");
        prefixTrie.store("ni");
        prefixTrie.store("napag");
        prefixTrie.store("mapag");
        prefixTrie.store("nakipag");
        prefixTrie.store("nakikipag");
        prefixTrie.store("makipag");
        prefixTrie.store("makikipag");
        prefixTrie.store("nakiki");
        prefixTrie.store("makiki");
        prefixTrie.store("naka");
        prefixTrie.store("nakaka");
        prefixTrie.store("maka");
        prefixTrie.store("makaka");    
        prefixTrie.store("nagka");
        prefixTrie.store("nagkaka");        
        prefixTrie.store("magka");
        prefixTrie.store("magkaka"); 
        prefixTrie.store("napa");
        prefixTrie.store("napaki");
        prefixTrie.store("napakiki");
        prefixTrie.store("mapa");
        prefixTrie.store("mapaki");
        prefixTrie.store("mapakiki");
        prefixTrie.store("paki");
        prefixTrie.store("pakiki");
        prefixTrie.store("pakikipag");
        prefixTrie.store("pagki");
        prefixTrie.store("pagkiki");
        prefixTrie.store("pagkikipag");        
        suffixTrie.store("an");
        suffixTrie.store("in");
        infixList.add(new RewriteRule("in",""));
        infixList.add(new RewriteRule("um",""));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new JLabel();
        txtRoot = new JTextField();
        jLabel2 = new JLabel();
        txtMorphed = new JTextField();
        jScrollPane1 = new JScrollPane();
        ruleDisplayTable = new JTable();
        extractRule = new JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rule Extractor");
        setName("ruleExtractorFrame");
        jLabel1.setText("Root:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 40, 40, 14);

        getContentPane().add(txtRoot);
        txtRoot.setBounds(100, 40, 480, 19);

        jLabel2.setText("Morphed:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 10, 60, 14);

        getContentPane().add(txtMorphed);
        txtMorphed.setBounds(100, 10, 480, 19);

        ruleDisplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ruleDisplayTable.setFocusable(false);
        jScrollPane1.setViewportView(ruleDisplayTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 80, 670, 100);

        extractRule.setText("Extract Rule");
        extractRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extractRuleActionPerformed(evt);
            }
        });

        getContentPane().add(extractRule);
        extractRule.setBounds(590, 40, 110, 23);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void extractRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extractRuleActionPerformed
        // TODO add your handling code here:
        MorphRule r = rec.extractRule(txtMorphed.getText(), txtRoot.getText());        
        rtbl.setMorphRule(r);
    }//GEN-LAST:event_extractRuleActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RuleExtractorFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton extractRule;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable ruleDisplayTable;
    private JTextField txtMorphed;
    private JTextField txtRoot;
    // End of variables declaration//GEN-END:variables
    
}
