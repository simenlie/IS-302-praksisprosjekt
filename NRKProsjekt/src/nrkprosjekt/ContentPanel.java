/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JTable;

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

    /**
     * Creates new form ContentPanel
     */
    public ContentPanel() throws IOException {
        loader = new Load();
        welcomePanel = new WelcomePanel();
        navigation = new ArrayList<>();


        dictionary = new HashMap<>();

        initComponents();
        pane.setViewportView(welcomePanel);
        setName("Content");
        setLayout(new BorderLayout());

        add(pane, BorderLayout.CENTER);
        dictionary.put("welcome", welcomePanel);
        navigation.add(welcomePanel);
        currentPage = navigation.size() - 1;
    }

    public void initPanel(String panel) throws IOException {

        addPanels(panel);

    }

    boolean isLink() {
        return searchPanel2.isLink();
    }

    boolean isLink2() {
        return lib.isLink();
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

    private void addPanels(String panel) throws IOException {
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
            case "advanced":
                advancedS = new AdvancedSearchPanel();
                dictionary.put("advancedS", advancedS);
                break;
            case "search":
                searchPanel2 = new NewSearch();
                dictionary.put("search", searchPanel2);
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

    public void showPanel(String name) {
        if (name.equals("artist")) {
            artistPanel = new ArtistPanel();
            dictionary.put("artist", artistPanel);
            artistPanel.doIT();
        }
        JPanel temp = dictionary.get(name);
        pane.setViewportView(temp);
        System.out.println(currentPage + " " + canGoBack);
        navigation.add(temp);
        currentPage = navigation.size() - 1;
        if (navigation.size() > 0) {
            canGoBack = true;
        }


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
