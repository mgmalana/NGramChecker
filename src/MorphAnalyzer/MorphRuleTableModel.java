package MorphAnalyzer;
/*
 * MorphRuleTableModel.java
 *
 * Created on May 14, 2005, 3:19 PM
 */

/**
 *
 * @author Solomon See
 */
import javax.swing.table.*;

public class MorphRuleTableModel extends AbstractTableModel{
    MorphRule rule;
    /** Creates a new instance of MorphRuleTableModel */
    public MorphRuleTableModel(MorphRule rule) {
        super();
        this.rule = rule;
    }
    public String getColumnName(int column){
        switch(column) {
            case 0: return "Prefix";
            case 1: return "Dup";
            case 2: return "Infix";
            case 3: return "POP";
            case 4: return "Common";
            case 5: return "VC";
            case 6: return "Common";
            case 7: return "POS";
            case 8: return "Suffix";
            case 9: return "Tense";
            default: return "Unknown";
        }
    }
    public int getRowCount() {
        return 2;
    }
    public int getColumnCount() {
        return 10;
    }
    public Object getValueAt(int row, int column){
        switch(row) {
            case 0: 
                switch(column){
                    case 0: return rule.getPrefix();
                    case 1: return rule.getPartialRedup() + "/" + rule.getWholeRedup();
                    case 2: return rule.getInfix();
                    case 3: return rule.getMorphedPOP();
                    case 4: return rule.getMorphedCommonPrefix();
                    case 5: return rule.getMorphedVowelChange();
                    case 6: return rule.getMorphedCommonSuffix();
                    case 7: return rule.getMorphedPOS();
                    case 8: return rule.getSuffix();
                    case 9: return rule.getMorphedTense();
                }
            case 1: 
                switch(column){
                    case 0: return "";
                    case 1: return "";
                    case 2: return "";
                    case 3: return rule.getRootPOP();
                    case 4: return rule.getRootCommonPrefix();
                    case 5: return rule.getRootVowelChange();
                    case 6: return rule.getRootCommonSuffix();
                    case 7: return rule.getRootPOS();
                    case 8: return "";
                    case 9: return rule.getRootTense();
                }
            default: return "Unknown";
        }
    }
    public void setMorphRule(MorphRule r) {
        rule = r;
        fireTableDataChanged();
    }
}