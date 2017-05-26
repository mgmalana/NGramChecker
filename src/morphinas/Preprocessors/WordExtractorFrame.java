/*
 * WordExtractorFrame.java
 *
 * Created on September 5, 2005, 1:30 PM
 */

package morphinas.Preprocessors;

/**
 *
 * @author  Solomon See
 */
import java.io.*;
public class WordExtractorFrame extends javax.swing.JFrame {

    /** Creates new form WordExtractorFrame */
    public WordExtractorFrame() {
        initComponents();
        setSize(500, 150);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtSource = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTarget = new javax.swing.JTextField();
        btnProcess = new javax.swing.JButton();
        cbIncludeSubdirectories = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        txtNETarget = new javax.swing.JTextField();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Word Extractor");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Source Directory:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 120, 20);

        getContentPane().add(txtSource);
        txtSource.setBounds(140, 20, 150, 19);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Words  File:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 50, 120, 20);

        getContentPane().add(txtTarget);
        txtTarget.setBounds(140, 50, 150, 19);

        btnProcess.setText("Extract Words");
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });
        btnProcess.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProcessMouseClicked(evt);
            }
        });

        getContentPane().add(btnProcess);
        btnProcess.setBounds(320, 20, 130, 23);

        cbIncludeSubdirectories.setSelected(true);
        cbIncludeSubdirectories.setText("Include Subdirectories?");
        getContentPane().add(cbIncludeSubdirectories);
        cbIncludeSubdirectories.setBounds(310, 60, 170, 23);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Named Entity  File:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 80, 120, 20);

        getContentPane().add(txtNETarget);
        txtNETarget.setBounds(140, 80, 150, 20);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void btnProcessMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProcessMouseClicked
// TODO add your handling code here:
        try {
            WordExtractor.wordFilter = new DefaultWordFilter();
            WordExtractor.dashedOutput = new PrintStream(new FileOutputStream("C:\\filestoreview.txt"));
            WordExtractor.extractWords(txtSource.getText(),txtTarget.getText(),cbIncludeSubdirectories.isSelected());            
            if (WordExtractor.dashedOutput == null)
                System.out.println("Null");
            if (WordExtractor.wordFilter != null)
                ((DefaultWordFilter) WordExtractor.wordFilter).writeToFile(txtNETarget.getText());
            javax.swing.JOptionPane.showMessageDialog(this,"Extraction completed!","Success",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            WordExtractor.dashedOutput.flush();
            WordExtractor.dashedOutput.close();
        } catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,e.getMessage(),"Error Encountered",javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProcessMouseClicked

    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_btnProcessActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WordExtractorFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProcess;
    private javax.swing.JCheckBox cbIncludeSubdirectories;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtNETarget;
    private javax.swing.JTextField txtSource;
    private javax.swing.JTextField txtTarget;
    // End of variables declaration//GEN-END:variables

}