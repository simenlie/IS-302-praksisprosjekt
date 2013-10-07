/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Simen
 */
public class ContentPanel extends javax.swing.JPanel {

    SearchPanel searchPanel;
    RecentlyAddedPanel recentlyA;
    LibraryPanel libraryPanel;
    ArtistPanel artistPanel;
    
    Stack<JPanel> navigation;
    Stack<JPanel> navigationBack;
    HashMap<JPanel, Navigation> bol;
    HashMap<String, JPanel> dictionary;
    boolean hei;
    boolean visit = false;
    boolean visit2 = false;

    /**
     * Creates new form ContentPanel
     */
    public ContentPanel() {
        navigation = new Stack();
        navigationBack = new Stack<>();
        bol = new HashMap<>();
        dictionary = new HashMap<>();

        initComponents();
        setName("Content");
        setLayout(new BorderLayout());
        addPanels();
        fillDict();
    }

    boolean isLink() {
        return searchPanel.isLink();
    }

    public void fillDict() {
        dictionary.put("search", searchPanel);
        dictionary.put("library", libraryPanel);
        dictionary.put("artist", artistPanel);
    }

    private void addNavigation(JPanel panel) {
        //navigation.add(panel);
        navigationBack.add(panel);
    }

    public boolean navBack() {
        boolean returning = true;
        if (navigation.size() == 0) {
            returning = false;
        }

        if (!navigationBack.isEmpty()) {
            if (navigation.size() != 1) {
                navigation.add(navigationBack.pop());

                pane.setViewportView(navigationBack.peek());
            } else {
                returning = false;
            }

        }

        return returning;
    }

    public boolean navForward() {
        boolean returning = true;
        System.out.println(navigationBack.size());
        if (navigationBack.size() == 1) {
            returning = false;
        }
        if (!navigation.isEmpty()) {
            navigationBack.add(navigation.peek());

            pane.setViewportView(navigation.pop());
        } else {
            returning = false;
        }
        return returning;

    }

    public boolean isLastPage() {
        return visit;
    }

    private void addPanels() {
        searchPanel = new SearchPanel();
        libraryPanel = new LibraryPanel();
        recentlyA = new RecentlyAddedPanel();
        artistPanel = new ArtistPanel();
        add(pane, BorderLayout.CENTER);

    }

    public void showSearchContent() {
        pane.setViewportView(searchPanel);
        if (!visit) {
            addNavigation(searchPanel);
        }

        visit = true;


    }

    public void showLibraryContent() {
        pane.setViewportView(libraryPanel);
        addNavigation(libraryPanel);

    }
    
     public void showRecContent() {
        pane.setViewportView(recentlyA);
        //addNavigation(libraryPanel);

    }

    public void showArtistContent() {
        pane.setViewportView(artistPanel);
        if (!visit2) {
            addNavigation(artistPanel);
            bol.put(artistPanel, new Navigation());
        }
        visit2 = true;
        navForward();


    }

    public void showPanel(String name) {
        JPanel temp = dictionary.get(name);
        pane.setViewportView(temp);
        if (!bol.containsKey(temp)) {
            bol.put(temp, new Navigation());
        }
        if (!bol.get(temp).isVisited()) {
            addNavigation(temp);
        } else {
            visit = false;
        }
        bol.get(temp).setVisited(true);
        if (!name.equals("search")) {
            navForward();
        }


    }

    public JTable getTable() {
        return searchPanel.getTable();
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public void showRecentlyAddedContent() {
        //code here
    }

    public void showAdvancedSearchContent() {
        //code here
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
        setLayout(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        pane.setViewportView(jPanel3);

        add(pane);
        pane.setBounds(0, 0, 400, 300);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane pane;
    // End of variables declaration//GEN-END:variables
}
