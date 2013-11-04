/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Info;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Simen
 */
public class ImageRenderer extends DefaultTableCellRenderer {

    JLabel lbl = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        if (value instanceof String) {
            return lbl;
        }
        if (row % 2 == 0) {
            lbl.setBackground(Color.WHITE);
        } else {
            lbl.setBackground(new Color(236, 235, 232));
        }
        if (isSelected) {
            lbl.setBackground(new Color(51, 153, 255));
        }
        lbl.setIcon((ImageIcon) value);
        if (column != 8) {
            lbl.setHorizontalAlignment(JLabel.CENTER);
        } 



        // lbl.setForeground(Color.red);

        lbl.setOpaque(true);
        return lbl;
    }
}
