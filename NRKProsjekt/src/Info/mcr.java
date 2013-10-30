/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Info;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Simen
 */
class mcr extends DefaultTableCellRenderer {

    public Class getColumnClass(int c) {
        return ImageIcon.class;
    }
}
