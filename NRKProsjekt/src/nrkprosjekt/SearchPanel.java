/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Entities.Album;
import Entities.Artist;
import Handlers.Songhandler;
import Handlers.TableHandler;
import Info.Dictionary;
import Info.PriorityTags;
import Info.Tags;

import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;

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
    JPopupMenu artistPop;
    JPopupMenu albumPop;
    int artistID;
    HashMap<String, JLabel> tags;
    Songhandler songhandler;
    Thread thread;
    boolean flag;
    TableHandler tableHandler;
    Processing processing;
    Artist curArtist;
    Album curAlbum;
    boolean artistClicked;
    ContentPanel content;
    int curId;

    /**
     * Creates new form SearchPanel
     */
    public SearchPanel() throws IOException, SQLException {

        initComponents();
        setLayout(new BorderLayout());

        songhandler = new Songhandler();
        tableHandler = new TableHandler();
        tableHandler.getTableModel(searchTable);

        JLabel iconS = new JLabel();
        iconS.setBounds(10, 11, 30, 30);
        iconS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/searchPage.png")));

        add(pane, BorderLayout.CENTER);

        initPopup();
        initMetaPopup();
    }

    public void setTags(HashMap<String, JLabel> tags) {
        this.tags = tags;
    }

    public void setContent(ContentPanel content) {
        this.content = content;
    }

    public void updateTable() {
        System.out.println("HE NA");
        tableHandler.getTableModel(searchTable);
    }

    public void searchTable(String words) {
        tableHandler.getSearchModel(searchTable, words);
    }

    public void searchAdvancedTable(HashMap<String, JTextField> tags) {
        tableHandler.searchAdvanced(searchTable, tags);
    }

    public int getArtistID() {
        return artistID;
    }

    public void setShowResult(String text) {
        //showingResult.setText(text);
    }

    public void initMetaPopup() throws IOException, SQLException {
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
                    try {
                        deleteActionPerformed(evt);
                    } catch (SQLException ex) {
                        Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
        searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        final SearchPanel s = this;
        Thread metaThread = new Thread(new Runnable() {
            public void run() {
                try {
                    try {
                        metaEdit = new MetaEdit(new javax.swing.JFrame(), true);
                        metaEdit.setCurRow(searchTable.getSelectedRow());
                        metaEdit.setSearchPanel(s);
                        metaEdit.setUpdating(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Tags s : Tags.values()) {
                    if (songhandler.getTrack().getValues().containsKey(s.toString())) {
                        if (songhandler.getTrack().getValues().get(s.toString()) == null) {
                            // do nothing
                        } else {
                            metaEdit.setText(s.toString(), songhandler.getTrack().getValues().get(s.toString()));
                        }
                    }
                }
                metaEdit.dos(songhandler.getTrack().getArtists());
                metaEdit.dos2(songhandler.getTrack().getAlbums());
                metaEdit.setExImage(songhandler.getTrack().getPic());
                metaEdit.setVisible(true);
                searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
        metaThread.start();
    }

    private void infoActionPerformed(java.awt.event.ActionEvent evt) {
        songInfo.setVisible(true);
    }

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
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

    private void setColumnNames() {
        PriorityTags p = new PriorityTags();

        for (String s : PriorityTags.getPriority2().keySet()) {
            for (Tags sa : Tags.values()) {
                if (s.equals(sa.toString())) {
                    searchTable.getColumn(s).setHeaderValue(Dictionary.getDictionary().get(sa).name);
                }
            }

        }


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
        searchTable.setFocusable(false);
        searchTable.setIntercellSpacing(new java.awt.Dimension(5, 1));
        searchTable.setRowHeight(28);
        searchTable.setShowHorizontalLines(false);
        searchTable.setShowVerticalLines(false);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchTableMouseReleased(evt);
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
            int id = Integer.parseInt((String) searchTable.getModel().getValueAt(searchTable.getSelectedRow(), searchTable.getColumn("idSONG").getModelIndex()));

            if (curId != id) {
                try {
                    songhandler.loadSongInfo(id);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                curId = id;
            }
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
        if (selected != null && searchTable.getValueAt(selectedRow, selectedCol) != null && selectedCol == searchTable.getColumn("Artist").getModelIndex()) {
            backToNormal();
        }

        selectedRow = searchTable.rowAtPoint(evt.getPoint());
        selectedCol = searchTable.columnAtPoint(evt.getPoint());

        //searchTable.setRowSelectionInterval(selectedRow, selectedRow);
        //System.out.println("rad " + searchTable.getValueAt(selectedRow, selectedCol));
        if (searchTable.getValueAt(selectedRow, selectedCol) != null && selectedCol == searchTable.getColumn("Artist").getModelIndex()) {

            String t = parse((String) searchTable.getValueAt(selectedRow, selectedCol));
            searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            //searchTable.getColumnModel().setColumnSelectionAllowed(false);
            if (songhandler.getTrack().getArtists().size() == 1) {
                curArtist = songhandler.getTrack().getArtists().get(0);
                link = true;
            }


            searchTable.setValueAt(t, selectedRow, selectedCol);

        } else {
            searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            link = false;
        }


        //searchTable.getSelectedRow();

    }//GEN-LAST:event_searchTableMouseMoved
    public void checkOneArt(java.awt.event.MouseEvent evt) {
        if (songhandler.getTrack().getArtists().size() > 1) {
            link = false;
        }
        if (evt.getButton() == 1 && link) {
            try {
                //content.getArtistPanel().setArtist(content.getArtist());
                content.showPanel("artist");
                System.out.println("djal 1");
            } catch (SQLException ex) {
                Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("djal 2");

            try {
                content.artistPanel(content.getArtist());
            } catch (SQLException ex) {
                Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            link = false;
        }
    }

    public void checkArtistAlbum(java.awt.event.MouseEvent evt) throws FileNotFoundException, SQLException, IOException {
        int id = Integer.parseInt((String) searchTable.getModel().getValueAt(searchTable.getSelectedRow(), searchTable.getColumn("idSONG").getModelIndex()));
        curId = id;
        System.out.println("IDEN ER SOM FØLGER:  " + id);
        songhandler.loadSongInfo(id);
        checkOneArt(evt);
        selectedRow = searchTable.rowAtPoint(evt.getPoint());
        selectedCol = searchTable.columnAtPoint(evt.getPoint());

        if (selectedCol == searchTable.getColumn("Album").getModelIndex()) {
//idLOader


            albumPop = new JPopupMenu();
            for (final Album a : songhandler.getTrack().getAlbums()) {
                JMenuItem temps = new JMenuItem(a.getIALB());
                temps.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jMenuItem1ActionPerformed2(evt, a.getIALB(), a.getId());
                    }
                });
                albumPop.add(temps);
            }

            if (albumPop.isVisible()) {
                albumPop.setVisible(false);
            } else {
                if (searchTable.getSelectedColumn() == searchTable.getColumn("Album").getModelIndex() && songhandler.getTrack().getAlbums().size() > 1) {
                    albumPop.show(evt.getComponent(), evt.getX(), evt.getY());
                } else {
                    //Jump to album page
                }
            }

        } else if (selectedCol == searchTable.getColumn("Artist").getModelIndex()) {



            artistPop = new JPopupMenu();

            for (final Artist a : songhandler.getTrack().getArtists()) {
                JMenuItem temps = new JMenuItem(a.getIART());
                temps.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        try {
                            jMenuItem1ActionPerformed(evt, a.getIART(), a.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
                artistPop.add(temps);
            }
            if (artistPop.isVisible()) {
                artistPop.setVisible(false);
            } else {
                if (searchTable.getSelectedColumn() == searchTable.getColumn("Artist").getModelIndex() && songhandler.getTrack().getArtists().size() > 1) {
                    artistPop.show(evt.getComponent(), evt.getX(), evt.getY());
                } else {
                    //Jump to artist page
                }
            }
        }

        //searchTable.setColumnSelectionInterval(selectedCol, selectedCol);
        //searchTable.setRowSelectionInterval(selectedRow, selectedRow);
        if (selectedCol != searchTable.getColumn("Album").getModelIndex()) {
            if (albumPop != null) {
                albumPop.setVisible(false);
            }

        }
        if (selectedCol != searchTable.getColumn("Artist").getModelIndex()) {
            if (artistPop != null) {
                artistPop.setVisible(false);
            }
        }

    }

    public Artist getCurArtist() {
        return curArtist;
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt, String s, int id) throws SQLException {
        //System.out.println(d.getAlbums().getId(s));
        //System.out.println(searchTable.getColumn("IART").getModelIndex());
        System.out.println("her " + id);
        for (Artist a : songhandler.getTrack().getArtists()) {
            if (a.getId() == id) {
                //riktig artist
                curArtist = a;
            }

        }

        content.showPanel("artist");
        content.artistPanel(curArtist);
        //vise artist

    }

    private void jMenuItem1ActionPerformed2(java.awt.event.ActionEvent evt, String s, int id) {
        //System.out.println(d.getAlbums().getId(s));
        //System.out.println(searchTable.getColumn("IART").getModelIndex());

        for (Album a : songhandler.getTrack().getAlbums()) {
            if (a.getId() == id) {
                //riktig artist
                curAlbum = a;
            }

        }

    }

    public void load() throws SQLException {
        int rowCount = 2;
        for (int i = 0; i < rowCount; i++) {
            DBConnection dba = new DBConnection();
            System.out.println("hei");
            int ida = Integer.parseInt((String) searchTable.getModel().getValueAt(i, searchTable.getColumn("idSONG").getModelIndex()));

            //dba.getManyRelation(ida, 1);
            //dba.getManyRelation(ida, 0);
            System.out.println(ida);
            //searchTable.getModel().setValueAt(dba.getAlbums().getCollectionInString(), i, searchTable.getColumn("IALB").getModelIndex());
            //searchTable.getModel().setValueAt(dba.getArtists().getCollectionInString(), i, searchTable.getColumn("IART").getModelIndex());
        }


    }

    private void searchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMouseClicked
        if (thread != null) {
            if (thread.isAlive()) {
            }

        }


        final MouseEvent e = evt;
        thread = new Thread(new Runnable() {
            public void run() {
                try {
                    checkArtistAlbum(e);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                songhandler.getTopInfo();
            }
        });

        thread.start();


        //db.getSongInfo(ArtistPage.songID, tags);


    }//GEN-LAST:event_searchTableMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane pane;
    private javax.swing.JTable searchTable;
    // End of variables declaration//GEN-END:variables
}
