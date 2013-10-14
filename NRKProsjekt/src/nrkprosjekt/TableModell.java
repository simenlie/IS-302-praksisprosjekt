/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Simen
 */
public class TableModell extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
