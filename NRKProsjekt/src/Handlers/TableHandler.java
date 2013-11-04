/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Info.Dictionary;
import Info.ImageRenderer;
import Info.PriorityTags;
import Info.Tags;
import database.DBConnection;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Simen
 */
public class TableHandler {

    DBConnection database;

    public TableHandler() throws SQLException {
        database = new DBConnection();
    }

    public DefaultTableModel getTableModel(JTable table) {
        DefaultTableModel dm = database.getTable();
        table.setModel(dm);
        System.out.println("HER");
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/show2.png"));
        ImageIcon icon2 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/Information/Info0.png"));
        ImageIcon icon3 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info4.png"));
        ImageIcon icon4 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info7.png"));
        //dm.addColumn("Information");
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            table.setValueAt(icon, i, 0);

            //table.setValueAt(table.getValueAt(i, 8) + "simen", i, 8);

            int nullAmount = Integer.parseInt((String)table.getValueAt(i, 8)) ;
            ImageIcon iconIt = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/Information/Info" + nullAmount + ".png"));
            table.setValueAt(iconIt, i, 8);
        }
        table.getColumnModel().getColumn(0).setMinWidth(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(20);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Calibri Light", Font.PLAIN, 14));
        TableColumn c = table.getColumnModel().getColumn(1);

        c.setMaxWidth(0);
        c.setMinWidth(0);
        c.setPreferredWidth(0);
        setColumnNames(table);
        System.out.println("JS");
        return dm;

    }

    public DefaultTableModel getSearchModel(JTable table, String searchWords) {
        DefaultTableModel dm = database.simpleSearch(searchWords, Dictionary.getDictionaryString());
        table.setModel(dm);
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/show2.png"));
        ImageIcon icon2 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info.png"));
        ImageIcon icon3 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info4.png"));
        ImageIcon icon4 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info7.png"));
        dm.addColumn("Information");
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            table.setValueAt(icon, i, 0);
            table.setValueAt(icon2, i, 8);
        }

        table.getColumnModel().getColumn(0).setMinWidth(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(20);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Calibri Light", Font.PLAIN, 14));
        TableColumn c = table.getColumnModel().getColumn(1);

        c.setMaxWidth(0);
        c.setMinWidth(0);
        c.setPreferredWidth(0);
        setColumnNames(table);
        return dm;

    }

    public DefaultTableModel searchAdvanced(JTable table, HashMap<String, JTextField> tags) {
        DefaultTableModel dm = database.advancedSearch(tags);
        table.setModel(dm);
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/show.png"));
        ImageIcon icon2 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info.png"));
        ImageIcon icon3 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info4.png"));
        ImageIcon icon4 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info7.png"));
        dm.addColumn("Information");
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            table.setValueAt(icon, i, 0);
            table.setValueAt(icon2, i, 8);
        }

        table.getColumnModel().getColumn(0).setMinWidth(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(20);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Calibri Light", Font.PLAIN, 14));
        TableColumn c = table.getColumnModel().getColumn(1);

        c.setMaxWidth(0);
        c.setMinWidth(0);
        c.setPreferredWidth(0);
        setColumnNames(table);
        return dm;
    }

    private void setColumnNames(JTable table) {
        PriorityTags p = new PriorityTags();
        System.out.println("her");
        for (String s : PriorityTags.getPriority2().keySet()) {
            for (Tags sa : Tags.values()) {
                if (s.equals(sa.toString())) {
                    table.getColumn(s).setHeaderValue(Dictionary.getDictionary().get(sa).name);
                }
            }

        }
table.getColumn("sum_null").setHeaderValue("Information");

    }
}
