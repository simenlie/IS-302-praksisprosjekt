/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Simen
 */
public class TabCellEditor extends AbstractCellEditor implements TableCellEditor {
    JComponent component = new JTextField();
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
        ((JTextField)component).setText((String)value);
        ((JTextField)component).setFont(new java.awt.Font("Arial Unicode MS", 1, 18));
        return component;
    }

    @Override
    public Object getCellEditorValue() {
        System.out.println("not suport");
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
