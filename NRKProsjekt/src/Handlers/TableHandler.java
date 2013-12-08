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
import java.util.ArrayList;
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
    ArrayList<Song> popular;

    public TableHandler() throws SQLException {
        database = new DBConnection();
    }

    public ArrayList<Song> getPopular() {
        return popular;
    }

    public class Song {

        public String track;
        public int popularity;
        public int raw;
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

            int nullAmount = Integer.parseInt((String) table.getValueAt(i, 8));
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

    public DefaultTableModel getMostPopular(JTable table) throws SQLException {
        int totalSeaches = database.getTotalSearches();
        //antall * 100 /antall total
        popular = new ArrayList<>();
        DefaultTableModel dm = database.getMostPopular();
        table.setModel(dm);
        System.out.println("HER");
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/show2.png"));
        ImageIcon icon2 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/Information/Info0.png"));
        ImageIcon icon3 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info4.png"));
        ImageIcon icon4 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info7.png"));
        //dm.addColumn("Information");
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        //table.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            table.setValueAt(icon, i, 0);

            int amount = Integer.parseInt((String) table.getValueAt(i, 4));
            // ImageIcon iconIt = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/Information/Info" + nullAmount + ".png"));
            int percentage = (amount * 100) / totalSeaches;
            Song s = new Song();
            s.popularity = percentage;
            s.track = table.getValueAt(i, 2).toString();
            s.raw = amount;
            popular.add(s);
            String perc = percentage + " %";
            table.setValueAt(perc, i, 4);
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
        table.getColumn("INAM").setHeaderValue("Track");
        table.getColumn("IART").setHeaderValue("Artist");
        table.getColumn("count").setHeaderValue("Popularity");
        return dm;

    }

    public DefaultTableModel getRecentlyModel(JTable table, int limit, String sorting, String column) {
        DefaultTableModel dm = database.getTableLimit(column, limit, sorting);
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

            int nullAmount = Integer.parseInt((String) table.getValueAt(i, 8));
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
        System.out.println("HER NA Al");
        ArrayList<Integer> searched = new ArrayList<>();
        DefaultTableModel dm = database.simpleSearch(searchWords, Dictionary.getDictionaryString());
        table.setModel(dm);
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/show2.png"));
        ImageIcon icon2 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info.png"));
        ImageIcon icon3 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info4.png"));
        ImageIcon icon4 = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/info7.png"));
        //dm.addColumn("Information");
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());
        System.out.println("HER NA Al");
        int rowCount = table.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            searched.add(Integer.parseInt(table.getValueAt(i, 1).toString()));
            table.setValueAt(icon, i, 0);
            table.setValueAt(icon2, i, 8);
        }
        updateSearchCount(searched);
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

    public void updateSearchCount(ArrayList<Integer> list) {
        database.updateSongSearch(list);
    }

    public DefaultTableModel searchAdvanced(JTable table, HashMap<String, JTextField> tags) {
        ArrayList<Integer> searched = new ArrayList<>();
        System.out.println("HER NA AL");
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
            searched.add(Integer.parseInt(table.getValueAt(i, 1).toString()));
            table.setValueAt(icon, i, 0);
            table.setValueAt(icon2, i, 8);
        }
        updateSearchCount(searched);
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
        if (table.getColumn("sum_null") != null) {
            table.getColumn("sum_null").setHeaderValue("Information");
        }


    }
}
