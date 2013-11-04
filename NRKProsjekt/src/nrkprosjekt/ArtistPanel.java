/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Entities.Album;
import Entities.Artist;
import Handlers.Songhandler;
import Info.ImageRenderer;
import Info.Style;
import database.DBConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTable;

/**
 *
 * @author Simen
 */
public class ArtistPanel extends javax.swing.JPanel {

    /**
     * Creates new form ArtistPanel
     */
    DBConnection d;
    HashMap<String, String> artist;
    ArrayList<Album> albums;
    ArrayList<AlbumPanel> albPanels;
    Songhandler songhandler;
    Artist curArtist;
    int sizeOfTracks;
    boolean naExists;
    JTable selected;

    public ArtistPanel() throws SQLException {
        initComponents();
        albPanels = new ArrayList<>();
        jLabel7.setVisible(false);
        jLabel7.setForeground(Style.getSuccessColor());
        songhandler = new Songhandler();
        //JLabel background = new JLabel();
        //background.setBounds(0, 0, 1024, 172);
        //background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/AlbumBack.png")));
        //jPanel1.add(background);

        jPanel2.setLayout(new BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));


        //jScrollPane1.setViewportView(panele);
    }

    public void setArtist(Artist artist) throws SQLException {
        curArtist = artist;
        jLabel2.setText(curArtist.getIART());

        jLabel3.setText(curArtist.getILAN());
        jLabel8.setText(curArtist.getIPLA() + ", " + curArtist.getICON());
        songhandler.getAlbums(artist);

        doITa();
        if (naExists) {
            if (curArtist.getAlbums().size() - 1 == 1) {
                jLabel4.setText(curArtist.getAlbums().size() - 1 + " Album");
            } else {
                jLabel4.setText(curArtist.getAlbums().size() - 1 + " Albums");
            }

        } else {
            if (curArtist.getAlbums().size() == 1) {
                jLabel4.setText(curArtist.getAlbums().size() + " Album");
            } else {
                jLabel4.setText(curArtist.getAlbums().size() + " Albums");
            }
        }
    }

    public void doITa() throws SQLException {

        for (Album a : curArtist.getAlbums()) {
            if (!a.getIALB().equals("N/A")) {
                ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/show2.png"));

                AlbumPanel p = new AlbumPanel(a.getIALB());
                p.getAlbumTable().setModel(songhandler.getTracks(a.getId()));



                p.getAlbumTable().getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
                int rowCount = p.getAlbumTable().getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    p.getAlbumTable().setValueAt(icon, i, 0);
                }
                p.getAlbumTable().getColumnModel().getColumn(0).setMinWidth(40);
                p.getAlbumTable().getColumnModel().getColumn(0).setPreferredWidth(40);
                p.getAlbumTable().getColumnModel().getColumn(0).setMaxWidth(40);

                p.getAlbumTable().getColumnModel().getColumn(1).setMinWidth(0);
                p.getAlbumTable().getColumnModel().getColumn(1).setPreferredWidth(0);
                p.getAlbumTable().getColumnModel().getColumn(1).setMaxWidth(0);

                p.getAlbumTable().getColumnModel().getColumn(3).setMinWidth(70);
                p.getAlbumTable().getColumnModel().getColumn(3).setPreferredWidth(70);
                p.getAlbumTable().getColumnModel().getColumn(3).setMaxWidth(70);
                p.setBounds(11, 245, 800, 300);
                albPanels.add(p);
                jPanel2.add(p);
                sizeOfTracks += p.getAlbumTable().getRowCount();
            } else {
                naExists = true;
            }
        }
        if (sizeOfTracks > 1) {
            jLabel5.setText(sizeOfTracks + " songs");
        } else {
            jLabel5.setText(sizeOfTracks + " song");
        }


        initAlbumListeneres();
    }

    public void initAlbumListeneres() {
        for (final AlbumPanel a : albPanels) {
            a.getAlbumTable().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt, a.getAlbumTable());
                }
            });
        }

    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt, JTable t) {
        selected = t;
        int rowIndex2 = t.getSelectedRow();

        for (AlbumPanel a : albPanels) {
            int rowIndex = a.getAlbumTable().getSelectedRow();

            if (!a.getAlbumTable().equals(t)) {

                if (rowIndex != -1) {
                    a.getAlbumTable().clearSelection();
                }
            }

        }
        t.setRowSelectionInterval(rowIndex2, rowIndex2);
        jLabel7.setVisible(true);
        jLabel7.setText((String) t.getValueAt(t.getSelectedRow(), t.getSelectedColumn()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(53, 53, 53));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(171, 174, 180)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/artist.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Hot Fingers");

        jLabel3.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(71, 170, 221));
        jLabel3.setText("malavisk");

        jLabel4.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("5 Albums");

        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("66 songs");

        jLabel7.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("jLabel7");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("England");

        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Sings in");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(153, 153, 153))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel5)
                            .addGap(4, 4, 4))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Calibri Light", 0, 16)); // NOI18N
        jLabel6.setText("Albums");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
