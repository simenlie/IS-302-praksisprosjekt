/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Entities.Artist;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Simen
 */
public class NewSearch extends javax.swing.JPanel {

    /**
     * Creates new form NewSearch
     */
    SearchPanel searchPanel;

    public NewSearch() throws IOException, SQLException {
        searchPanel = new SearchPanel();
        initComponents();
        setLayout(new BorderLayout());
        jPanel1.setPreferredSize(new Dimension(100, 50));
        jPanel1.setMinimumSize(new Dimension(100, 50));
        add(jPanel1, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
    }
    
    
 public void setContent(ContentPanel content) {
        searchPanel.setContent(content);
    }
    public boolean isLink() {
        return searchPanel.isLink();
    }

    public JTable getTable() {
        return searchPanel.getTable();
    }
    
    public Artist getArtist() {
        return searchPanel.getCurArtist();
    }

    public void setShowResult(String text) {
        searchText.setText(text);
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
        searchText = new javax.swing.JLabel();

        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(39, 46, 48));
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/searchPage.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 11, 30, 30);

        jLabel2.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Showing results for");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(60, 13, 136, 30);

        searchText.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        searchText.setForeground(new java.awt.Color(255, 255, 255));
        searchText.setText("SearchText");
        jPanel1.add(searchText);
        searchText.setBounds(200, 13, 400, 30);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 570, 70);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel searchText;
    // End of variables declaration//GEN-END:variables
}
