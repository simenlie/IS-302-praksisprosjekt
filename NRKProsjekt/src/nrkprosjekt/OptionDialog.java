/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Handlers.Songhandler;
import java.awt.Color;
import java.awt.Point;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Simen
 */
public class OptionDialog extends javax.swing.JDialog {

    JLabel pros2;
    Thread thread;
    JLabel label;
    JLabel pros;
    JButton button;
    JButton button2;
    Songhandler songhandler;

    /**
     * Creates new form OptionDialog
     */
    public OptionDialog(java.awt.Frame parent, boolean modal) throws SQLException {

        super(parent, modal);
        setLocationRelativeTo(null);
        init();
        songhandler = new Songhandler();
    }

    public OptionDialog(java.awt.Frame parent, boolean modal, Point pos) throws SQLException {

        super(parent, modal);
        setSize(300, 150);
        setLocationRelativeTo(null);
        //setLocation(pos);
        init();
        songhandler = new Songhandler();
    }

    public void init() {
        label = new JLabel();
        pros = new JLabel();
        pros.setVisible(false);
        pros.setBounds(75, 60, 300, 10);
        pros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/prosessed.png")));

        pros2 = new JLabel();
        pros2.setBounds(75, 60, 0, 10);
        pros2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/prosessedBar.png")));



        label.setText("fdff");
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/messageS.png")));
        button = new JButton("Cancel");
        button.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        button.setForeground(new java.awt.Color(255, 255, 255));
        button.setBounds(20, 90, 170, 40);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonSave.png")));
        button.setContentAreaFilled(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setIconTextGap(-108);
        button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonSaveOver.png")));

        button2 = new JButton("Delete");
        button2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setBounds(210, 90, 170, 40);
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonCancel.png")));
        button2.setContentAreaFilled(false);
        button2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button2.setFocusPainted(false);
        button2.setIconTextGap(-108);
        button2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonCancelOver.png")));


        setUndecorated(true);

        getRootPane().setOpaque(false);
        getContentPane().setBackground(new Color(0, 0, 0, 0));
        setBackground(new Color(0, 0, 0, 0));

        initComponents();
        jLabel2.add(button);
        jLabel2.add(button2);
        label.setBounds(35, 27, jLabel2.getWidth(), 50);
        label.setIconTextGap(10);
        label.setFont(new java.awt.Font("Calibri Light", 0, 16)); // NOI18N
        label.setForeground(new Color(255, 255, 255));
        label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label.setText("Are you sure you want to delete \"Mabvuto\"?");
        jLabel2.add(label);
        //jLabel2.add(pros);
        jLabel2.add(pros2);
        jLabel2.add(pros);
        //setContentPane(jLabel1);
        getContentPane().setBackground(new Color(0, 0, 0, 0));

        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton2ActionPerformed(evt);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OptionDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //this.setVisible(false);
        this.dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) throws InterruptedException {


        thread = new Thread(new Runnable() {
            public void run() {
                pros.setVisible(true);
                label.setText("Deleting file...");
                songhandler.deleteSong();

                while (pros2.getSize().width < 300) {
                    pros2.setSize(pros2.getSize().width + 1, 10);
                }
                label.setText("Deleted");
                button.setText("Close");
                button2.setEnabled(false);
            }
        });
        Thread.sleep(1000 / 120);
        thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/optionDialogs.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OptionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OptionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OptionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OptionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OptionDialog dialog = null;
                try {
                    dialog = new OptionDialog(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(OptionDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
