/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Simen
 */
public class LeftPanel extends javax.swing.JPanel {

    JLabel selected;
    ArrayList<JButton> buttons;

    /**
     * Creates new form LeftPanel
     */
    public LeftPanel() {
        buttons = new ArrayList<>();
        initComponents();
        initButtons();
        setSize(100, 100);
        setName("Left");
        initSelectedComp();
    }

    private void initButtons() {
        buttons.add(adBut);
        buttons.add(libraryBut);
        buttons.add(recBut);
        buttons.add(searchBut);
    }

    private void defaultButtons() {
        for (JButton button : buttons) {
            button.setFont(new java.awt.Font("Tahoma", 0, 11));
            button.setForeground(new java.awt.Color(0, 0, 0));
            if (button.getText().equals("Library")) {
                button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/library.png")));
            }
            if (button.getText().equals("Recently added")) {
                button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/recently.png")));
            }
            if (button.getText().equals("Advanced search")) {
                button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_search.png")));
            }

            if (button.getText().equals("Search results")) {
                button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_search.png")));
            }


        }
    }

    private void initSelectedComp() {
        selected = new JLabel();
        selected.setBounds(0, 16, 202, 41);
        selected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/backBut.png")));
        add(selected);
        selected.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adBut = new javax.swing.JButton();
        libraryBut = new javax.swing.JButton();
        recBut = new javax.swing.JButton();
        searchBut = new javax.swing.JButton();

        setBackground(new java.awt.Color(246, 246, 246));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 135, 144)));

        adBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_search.png"))); // NOI18N
        adBut.setText("Advanced search");
        adBut.setContentAreaFilled(false);
        adBut.setFocusPainted(false);
        adBut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adBut.setIconTextGap(15);
        adBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adButActionPerformed(evt);
            }
        });

        libraryBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/library.png"))); // NOI18N
        libraryBut.setText("Library");
        libraryBut.setContentAreaFilled(false);
        libraryBut.setFocusPainted(false);
        libraryBut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryBut.setIconTextGap(15);
        libraryBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libraryButActionPerformed(evt);
            }
        });
        libraryBut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                libraryButFocusLost(evt);
            }
        });

        recBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/recently.png"))); // NOI18N
        recBut.setText("Recently added");
        recBut.setContentAreaFilled(false);
        recBut.setFocusPainted(false);
        recBut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        recBut.setIconTextGap(15);
        recBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recButActionPerformed(evt);
            }
        });

        searchBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_search.png"))); // NOI18N
        searchBut.setText("Search results");
        searchBut.setContentAreaFilled(false);
        searchBut.setFocusPainted(false);
        searchBut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        searchBut.setIconTextGap(15);
        searchBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(recBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(libraryBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(adBut, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(searchBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(libraryBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(recBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(adBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(searchBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void libraryButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_libraryButActionPerformed
        menuClick(libraryBut, false);
    }//GEN-LAST:event_libraryButActionPerformed

    private void recButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recButActionPerformed
        menuClick(recBut, false);
    }//GEN-LAST:event_recButActionPerformed

    public JButton getButtonRec() {
        return recBut;
    }
    
    public JButton getButtonAdvan() {
        return adBut;
    }
    
    public JButton getButtonLib() {
        return libraryBut;
    }
    private void adButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adButActionPerformed
        menuClick(adBut, false);
    }//GEN-LAST:event_adButActionPerformed

    private void libraryButFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_libraryButFocusLost
    }//GEN-LAST:event_libraryButFocusLost

    private void searchButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButActionPerformed
        menuClick(searchBut, false);
    }//GEN-LAST:event_searchButActionPerformed

    public JButton getButton() {
        return searchBut;
    }

    public void menuClick(JButton button, boolean manual) {
        defaultButtons();
        button.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        button.setForeground(new java.awt.Color(255, 255, 255));


        if (!selected.isVisible()) {
            selected.setVisible(true);
        }
        if (libraryBut.isFocusOwner()) {
            selected.setLocation(0, 16);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/libraryWhite.png")));
        } else if (recBut.isFocusOwner()) {
            selected.setLocation(0, 59);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/recentlyWhite.png")));
        } else if (adBut.isFocusOwner()) {
            selected.setLocation(0, 100);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_searchWhite.png")));
        } else if (searchBut.isFocusOwner()) {
            selected.setLocation(0, 171);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_searchWhite.png")));
        }
        if (manual) {
            selected.setLocation(0, 171);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/icon_searchWhite.png")));
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adBut;
    private javax.swing.JButton libraryBut;
    private javax.swing.JButton recBut;
    private javax.swing.JButton searchBut;
    // End of variables declaration//GEN-END:variables
}
