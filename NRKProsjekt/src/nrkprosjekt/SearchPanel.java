/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import database.Artist;
import database.ArtistPage;
import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author Simen
 */
public class SearchPanel extends javax.swing.JPanel {

    OptionDialog dialog;
    MetaEdit metaEdit;
    SongInformation songInfo;
    JPopupMenu popupMenu;
    String selected;
    int selectedRow;
    int selectedCol;
    boolean link = false;
    DBConnection db;
    JPopupMenu artistPop;
    JPopupMenu albumPop;
    DBConnection d;
    int artistID;
    HashMap<String, JLabel> tags;

    /**
     * Creates new form SearchPanel
     */
    public SearchPanel() throws IOException {
        db = new DBConnection();
        initComponents();
        setLayout(new BorderLayout());
        //jPanel1.setPreferredSize(new Dimension(100, 50));
        searchTable.setModel(db.getTable());


        searchTable.getColumnModel().getColumn(0).setMinWidth(20);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        searchTable.getColumnModel().getColumn(0).setMaxWidth(20);
        load();
        //add(jPanel1, BorderLayout.NORTH);
        JLabel iconS = new JLabel();
        iconS.setBounds(10, 11, 30, 30);
        iconS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/searchPage.png")));
        //jPanel1.add(iconS);

        add(pane, BorderLayout.CENTER);
        //jPanel1.add(showingResult, BorderLayout.CENTER);

        //searchTable.setShowGrid(false);
        searchTable.setIntercellSpacing(new Dimension(0, 0));
        initPopup();
        initMetaPopup();
        searchTable.getTableHeader().setReorderingAllowed(false);
        // pane.setBorder(BorderFactory.createEmptyBorder());

        searchTable.getTableHeader().setFont(new Font("Calibri Light", Font.PLAIN, 14));
        TableColumn c = searchTable.getColumnModel().getColumn(1);

        c.setMaxWidth(0);
        c.setMinWidth(0);
        c.setPreferredWidth(0);
        //searchTable.removeColumn(searchTable.getColumnModel().getColumn(0));

    }

    public void setTags(HashMap<String, JLabel> tags) {
        this.tags = tags;
    }

    public void updateTable() {
        searchTable.setModel(db.getTable());
        load();
        searchTable.getColumnModel().getColumn(0).setMinWidth(20);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        searchTable.getColumnModel().getColumn(0).setMaxWidth(20);
        TableColumn c = searchTable.getColumnModel().getColumn(1);

        c.setMaxWidth(0);
        c.setMinWidth(0);
        c.setPreferredWidth(0);
    }

    public void chn() {
        searchTable.setModel(db.getTable());

        searchTable.isCellEditable(WIDTH, WIDTH);
    }

    public int getArtistID() {
        return artistID;
    }

    public void setShowResult(String text) {
        //showingResult.setText(text);
    }

    public void initMetaPopup() throws IOException {
        metaEdit = new MetaEdit(new javax.swing.JFrame(), true);
        songInfo = new SongInformation(new javax.swing.JFrame(), true);
    }

    public void initPopup() {
        popupMenu = new JPopupMenu();


        JMenuItem info = new JMenuItem("Info");

        popupMenu.add(info);
        if (!Login.restricted) {
            JMenuItem delete = new JMenuItem("Delete");
            JMenuItem edit = new JMenuItem("Edit");
            popupMenu.add(edit);
            popupMenu.add(delete);
            edit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    editActionPerformed(evt);
                }
            });
            delete.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    deleteActionPerformed(evt);
                }
            });
        }







        info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoActionPerformed(evt);
            }
        });

    }

    private void editActionPerformed(java.awt.event.ActionEvent evt) {
        metaEdit.setVisible(true);
    }

    private void infoActionPerformed(java.awt.event.ActionEvent evt) {
        songInfo.setVisible(true);
    }

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {
        dialog = new OptionDialog(new javax.swing.JFrame(), true, MouseInfo.getPointerInfo().getLocation());
        dialog.setVisible(true);
    }

    private String parse(String string) {
        selected = string;

        return "<html><u><font color=\"#000000\">" + string + "</font></u></html>";
    }

    private void backToNormal() {
        searchTable.setValueAt(selected, selectedRow, selectedCol);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(236, 235, 232));
        setLayout(null);

        pane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(153, 153, 153)));
        pane.setMinimumSize(new java.awt.Dimension(1000, 500));
        pane.setPreferredSize(new java.awt.Dimension(1000, 402));

        searchTable.setAutoCreateRowSorter(true);
        searchTable.setForeground(new java.awt.Color(51, 51, 51));
        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0", null, "Mabvuto", "The Borthers", null, null, null, null, null, null, null, null},
                {"1", null, "La chule", "This is", null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Hidden", "", "Track", "Artist", "Album", "Duration", "Year", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchTable.setAlignmentX(0.6F);
        searchTable.setAlignmentY(0.6F);
        searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        searchTable.setIntercellSpacing(new java.awt.Dimension(5, 1));
        searchTable.setRowHeight(28);
        searchTable.setShowHorizontalLines(false);
        searchTable.setShowVerticalLines(false);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchTableMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTableMouseClicked(evt);
            }
        });
        searchTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                searchTableMouseMoved(evt);
            }
        });
        pane.setViewportView(searchTable);
        searchTable.getColumnModel().getColumn(0).setMinWidth(20);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        searchTable.getColumnModel().getColumn(0).setMaxWidth(20);
        searchTable.getColumnModel().getColumn(1).setMinWidth(20);
        searchTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        searchTable.getColumnModel().getColumn(1).setMaxWidth(20);

        add(pane);
        pane.setBounds(80, 50, 1000, 220);
    }// </editor-fold>//GEN-END:initComponents

    private void searchTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMouseReleased

        searchTable.setRowSelectionInterval(selectedRow, selectedRow);
        //System.out.println(" row: " + searchTable.getSelectedRow() + " col: " + searchTable.getSelectedColumn());
        //System.out.println(searchTable.getValueAt(searchTable.getSelectedRow(), 0));
        if (evt.getButton() == 3) {
            System.out.println("righ clicked");

        }

        if (evt.isPopupTrigger()) {

            popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());

        }

    }//GEN-LAST:event_searchTableMouseReleased

    public JTable getTable() {
        return searchTable;
    }

    public boolean isLink() {
        return link;
    }

    private void searchTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMouseMoved
        if (selected != null && searchTable.getValueAt(selectedRow, selectedCol) != null && selectedCol == searchTable.getColumn("IART").getModelIndex()) {
            backToNormal();
        }

        selectedRow = searchTable.rowAtPoint(evt.getPoint());
        selectedCol = searchTable.columnAtPoint(evt.getPoint());

        //searchTable.setRowSelectionInterval(selectedRow, selectedRow);
        //System.out.println("rad " + searchTable.getValueAt(selectedRow, selectedCol));
        if (searchTable.getValueAt(selectedRow, selectedCol) != null && selectedCol == searchTable.getColumn("IART").getModelIndex()) {

            String t = parse((String) searchTable.getValueAt(selectedRow, selectedCol));
            searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            //searchTable.getColumnModel().setColumnSelectionAllowed(false);
            link = true;

            searchTable.setValueAt(t, selectedRow, selectedCol);

        } else {
            searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            link = false;
        }


        //searchTable.getSelectedRow();

    }//GEN-LAST:event_searchTableMouseMoved
    public void checkArtistAlbum(java.awt.event.MouseEvent evt) {
        selectedRow = searchTable.rowAtPoint(evt.getPoint());
        selectedCol = searchTable.columnAtPoint(evt.getPoint());
        int id = Integer.parseInt((String) searchTable.getModel().getValueAt(selectedRow, searchTable.getColumn("idSONG").getModelIndex()));
        d = new DBConnection();
        ArtistPage.songID = Integer.parseInt((String) searchTable.getValueAt(selectedRow, 1));
        if (selectedCol == searchTable.getColumn("IALB").getModelIndex()) {


            d.getManyRelation(id, 1);
            albumPop = new JPopupMenu();
            for (final Artist a : d.getAlbums().getCollection()) {
                JMenuItem temps = new JMenuItem(a.IART);
                temps.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jMenuItem1ActionPerformed(evt, a.IART);
                    }
                });
                albumPop.add(temps);
            }

            if (albumPop.isVisible()) {
                albumPop.setVisible(false);
            } else {
                if (searchTable.getSelectedColumn() == searchTable.getColumn("IALB").getModelIndex() && d.getAlbums().getSize() > 1) {
                    albumPop.show(evt.getComponent(), evt.getX(), evt.getY());
                } else {
                    //Jump to album page
                }
            }

        } else if (selectedCol == searchTable.getColumn("IART").getModelIndex()) {





            d.getManyRelation(id, 0);


            ArtistPage.artistID = d.getArtists().getId(selected);

            artistPop = new JPopupMenu();
            for (final Artist a : d.getArtists().getCollection()) {
                JMenuItem temps = new JMenuItem(a.IART);
                temps.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jMenuItem1ActionPerformed(evt, a.IART);
                    }
                });
                artistPop.add(temps);
            }
            if (artistPop.isVisible()) {
                artistPop.setVisible(false);
            } else {
                if (searchTable.getSelectedColumn() == searchTable.getColumn("IART").getModelIndex() && d.getArtists().getSize() > 1) {
                    artistPop.show(evt.getComponent(), evt.getX(), evt.getY());
                } else {
                    //Jump to artist page
                }
            }
        }

        searchTable.setColumnSelectionInterval(selectedCol, selectedCol);
        searchTable.setRowSelectionInterval(selectedRow, selectedRow);
        if (selectedCol != searchTable.getColumn("IALB").getModelIndex()) {
            if (albumPop != null) {
                albumPop.setVisible(false);
            }

        }
        if (selectedCol != searchTable.getColumn("IART").getModelIndex()) {
            if (artistPop != null) {
                artistPop.setVisible(false);
            }
        }

    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt, String s) {
        System.out.println(d.getAlbums().getId(s));
        System.out.println(searchTable.getColumn("IART").getModelIndex());
    }

    public void load() {
        int rowCount = 2;
        for (int i = 0; i < rowCount; i++) {
            DBConnection dba = new DBConnection();
            System.out.println("hei");
            int ida = Integer.parseInt((String) searchTable.getModel().getValueAt(i, searchTable.getColumn("idSONG").getModelIndex()));

            dba.getManyRelation(ida, 1);
            dba.getManyRelation(ida, 0);
            System.out.println(ida);
            searchTable.getModel().setValueAt(dba.getAlbums().getCollectionInString(), i, searchTable.getColumn("IALB").getModelIndex());
            searchTable.getModel().setValueAt(dba.getArtists().getCollectionInString(), i, searchTable.getColumn("IART").getModelIndex());
        }


    }
    private void searchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMouseClicked
        checkArtistAlbum(evt);
        db.getSongInfo(ArtistPage.songID, tags);

    }//GEN-LAST:event_searchTableMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane pane;
    private javax.swing.JTable searchTable;
    // End of variables declaration//GEN-END:variables
}
