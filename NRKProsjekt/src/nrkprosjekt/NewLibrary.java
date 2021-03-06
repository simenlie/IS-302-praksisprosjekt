/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Entities.Artist;
import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Simen
 */
public class NewLibrary extends javax.swing.JPanel {

    /**
     * Creates new form NewLibrary
     */
    TrackPanel songPanel;
    SearchPanel searchPanel;

    public NewLibrary() throws IOException, SQLException {
        initComponents();
        System.out.println("1");
        songPanel = new TrackPanel();
        System.out.println("1");
        searchPanel = new SearchPanel();
        System.out.println("1");
        setLayout(new BorderLayout());
        add(songPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        System.out.println("1");
        searchPanel.setTags(songPanel.getTags());
    }

    public JTable getTable() {
        return searchPanel.getTable();
    }

    boolean isLink() {
        return searchPanel.isLink();
    }

    public Artist getArtist() {
        return searchPanel.getCurArtist();
    }

    public void setContent(ContentPanel content) {
        searchPanel.setContent(content);
    }

    public void updateTable() {
        searchPanel.updateTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
