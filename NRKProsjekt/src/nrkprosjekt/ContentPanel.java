/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Entities.Artist;
import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Simen
 */
public class ContentPanel extends javax.swing.JPanel {

    Load loader;
    NewSearch searchPanel2;
    NewLibrary lib;
    RecentlyAddedPanel recentlyA;
    LibraryOverviewPanel libraryPanel;
    ArtistPanel artistPanel;
    AdvancedSearchPanel advancedS;
    HashMap<String, JPanel> dictionary;
    WelcomePanel welcomePanel;
    ArrayList<JPanel> navigation;
    int currentPage = 0;
    boolean canGoBack = false;
    boolean libIsShowing;
    Queue<JPanel> waitingToShow;
    JPanel current;

    /**
     * Creates new form ContentPanel
     */
    public ContentPanel() throws IOException {
        loader = new Load();
        welcomePanel = new WelcomePanel();
        navigation = new ArrayList<>();
        waitingToShow = new LinkedList<>();

        dictionary = new HashMap<>();

        initComponents();
        pane.setViewportView(welcomePanel);
        setName("Content");
        setLayout(new BorderLayout());

        add(pane, BorderLayout.CENTER);
        dictionary.put("welcome", welcomePanel);
        navigation.add(welcomePanel);
        currentPage = navigation.size() - 1;
        pane.getVerticalScrollBar().setUnitIncrement(16);
    }

    public void initPanel(String panel) throws IOException, SQLException {

        addPanels(panel);

    }

    boolean isLink() {
        return searchPanel2.isLink();
    }

    boolean isLink2() {
        return lib.isLink();
    }

    public ArtistPanel getArtistPanel() {
        return artistPanel;
    }

    public void fillDict() {
        dictionary.put("search", searchPanel2);
        dictionary.put("library", lib);
        dictionary.put("artist", artistPanel);
        dictionary.put("recently", recentlyA);

        dictionary.put("advancedS", advancedS);
        dictionary.put("welcome", welcomePanel);
    }

    public boolean goBack() {
        pane.setViewportView(navigation.get(currentPage - 1));
        currentPage -= 1;
        if (currentPage == 0) {
            //first page
            return false;
        }
        return true;
    }

    public boolean goForward() {
        pane.setViewportView(navigation.get(currentPage + 1));
        currentPage += 1;
        if (currentPage == navigation.size() - 1) {
            //last page
            return false;
        }
        return true;
    }

    private void addPanels(String panel) throws IOException, SQLException {
        switch (panel) {
            case "library":
                libraryPanel = new LibraryOverviewPanel();

                break;
            case "recently":

                recentlyA = new RecentlyAddedPanel();
                dictionary.put("recently", recentlyA);

                break;
            case "artist":
                artistPanel = new ArtistPanel();
                dictionary.put("artist", artistPanel);
                break;
            case "advancedS":
                advancedS = new AdvancedSearchPanel();
                dictionary.put("advancedS", advancedS);
                break;
            case "search":
                if (searchPanel2 == null) {
                    searchPanel2 = new NewSearch();
                    dictionary.put("search", searchPanel2);
                }

                break;
            case "lib":
                lib = new NewLibrary();
                dictionary.put("library", lib);
                break;

            case "welcome":
                welcomePanel = new WelcomePanel();
                dictionary.put("welcome", welcomePanel);
                break;
        }

    }

    public JPanel getCurrentPage() {
        return navigation.get(currentPage);
    }

    public void showPanel(String name) throws SQLException {

        if (name.equals("artist")) {
            artistPanel = new ArtistPanel();
            dictionary.put("artist", artistPanel);
            //artistPanel.doIT();
        }
        if (name.equals("lib")) {
            libIsShowing = true;
        } else {
            libIsShowing = false;
        }
        JPanel temp = dictionary.get(name);
        pane.setViewportView(temp);
        System.out.println(currentPage + " " + canGoBack);
        navigation.add(temp);
        currentPage = navigation.size() - 1;
        if (navigation.size() > 0) {
            canGoBack = true;
        }
        if (current != null) {
            pane.setViewportView(current);
        }

        

    }

    public JPanel getCurrent() {
        return current;
    }

    public void setCurrent(String name) {
        this.current = dictionary.get(name);
    }

    public void load() {
        pane.setViewportView(loader);
    }

    public JTable getTable() {
        return searchPanel2.getTable();
    }

    public JTable getTable2() {
        return lib.getTable();
    }

    public NewSearch getSearchPanel() {
        return searchPanel2;
    }

    public Artist getArtist() {
        if (libIsShowing) {

            return lib.getArtist();

        }


        return searchPanel2.getArtist();
    }

    public void setConta() {
        lib.setContent(this);
    }

    public void setConta2() {
        searchPanel2.setContent(this);
    }

    public void artistPanel(Artist artist) throws SQLException {
        artistPanel.setArtist(artist);
    }

    public void sok(String words) {
        searchPanel2.searchPanel.searchTable(words);
    }

    public void sok2(HashMap<String, JTextField> tags) {
        searchPanel2.searchPanel.searchAdvancedTable(tags);
    }

    public AdvancedSearchPanel getAdvancedS() {
        return advancedS;
    }

    public JPanel getPanel(String panel) {
        switch (panel) {
            case "library":
                return libraryPanel;
            case "recently":
                return recentlyA;
            case "artist":
                return artistPanel;
            case "advancedS":
                return advancedS;
            case "search":
                if (searchPanel2 == null) {
                    return searchPanel2;
                }
            case "lib":
                return lib;
            case "welcome":
                return welcomePanel;
        }
        return null;
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
        jPanel3 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 204));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(null);

        pane.setBorder(null);

        jPanel3.setBackground(new java.awt.Color(236, 235, 232));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        pane.setViewportView(jPanel3);

        add(pane);
        pane.setBounds(0, 0, 580, 420);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane pane;
    // End of variables declaration//GEN-END:variables
}
