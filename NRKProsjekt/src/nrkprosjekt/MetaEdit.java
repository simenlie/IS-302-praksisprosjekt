/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Simen
 */
public class MetaEdit extends javax.swing.JDialog {

    Font font;
    JLabel progress = new JLabel();
    JLabel progresse = new JLabel();
    JLabel suc = new JLabel();
    JLabel textInfo = new JLabel("Saving...");
    Thread thread;
    int simluationSpeed = 1000;
    int counter = 0;
    HashMap<String, Component> tags;
    SaveDialog saveDialog;

    /**
     * Creates new form metaEdit
     */
    public MetaEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        tags = new HashMap<>();
        initComponents();
        initProgressBar();
        addMouseListeners();
        loadMetaEditDialog();
        font = new java.awt.Font("Calibri Light", 0, 24);
        saveDialog = new SaveDialog(new javax.swing.JFrame(), true);
        saveDialog.getMeta(this);
        loadFont();
        fillMap();


    }

    public void fill(Component text, String name) {
        text.setName(name);
        tags.put(name, text);
    }

    public void fillMap() {
        fill(INAM, "INAM");
        fill(IART, "IART");
        fill(IGNR, "IGNR");
        fill(IKEY, "IKEY");
        fill(ISRF, "ISRF");
        fill(IMED, "IMED");
        fill(IENG, "IENG");
        fill(ITCH, "ITCH");
        fill(ISRC, "ISRC");
        fill(ICOP, "ICOP");
        fill(ISFT, "ISFT");
        fill(ICRD, "ICRD");
        fill(IALB, "IALB");
        fill(ILAN, "ILAN");


        fill(ICMT, "ICMT");
        fill(ISBJ, "ISBJ");

    }

    public void addMouseListeners() {
        mouseTextField(INAM);
        mouseTextField(IART);
        mouseTextField(IGNR);
        mouseTextField(IKEY);
        mouseTextField(ISRF);
        mouseTextField(IMED);
        mouseTextField(IENG);
        mouseTextField(ITCH);
        mouseTextField(ISRC);
        mouseTextField(ICOP);
        mouseTextField(ISFT);
        mouseTextField(ICRD);
        mouseTextField(IALB);
        mouseTextField(ILAN);
        //mouseTextField(ICMT);
        mouseTextField(ISBJ);
    }

    public void setText(String tag, String data) {
        if (tags.containsKey(tag)) {

            if (data.contains("finnes ikke")) {
                //nothing
            } else {
                if (tag.equals("ICMT")) {
                    JTextArea temp = (JTextArea) tags.get(tag);
                    temp.setText(data);
                } else {
                    JTextField temp = (JTextField) tags.get(tag);
                    temp.setText(data);

                }
                tags.get(tag).setForeground(new java.awt.Color(44, 47, 54));
                tags.get(tag).setBackground(new java.awt.Color(232, 255, 232));
            }

        }
    }

    public String getText(String tag) {
        if (tags.containsKey(tag)) {


            if (tag.equals("ICMT")) {
                JTextArea temp = (JTextArea) tags.get(tag);
                return temp.getText();
            } else {
                JTextField temp = (JTextField) tags.get(tag);
                return temp.getText();

            }



        }
        return null;
    }

    public void loadFont() {
        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        subTitle.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        jLabel1.getGraphics();
        jLabel1.getGraphics().getFontMetrics(font);
    }

    public void loadMetaEditDialog() {
        setResizable(false);
        setSize(500, 600);
        setLocationRelativeTo(null);
    }

    public void initProgressBar() {

        textInfo.setBounds(30, 550, 70, 10);
        textInfo.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        jPanel1.add(textInfo);
        textInfo.setVisible(false);


        suc.setBounds(400, 545, 20, 20);
        suc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/sucess3.png")));
        jPanel1.add(suc);
        suc.setVisible(false);

        progress.setBounds(95, 550, 0, 10);
        progress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/prosessedBar.png")));
        jPanel1.add(progress);

        progresse.setBounds(95, 550, 300, 10);
        progresse.setText("f");
        progresse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/prosessed.png")));
        jPanel1.add(progresse);




    }

    public void setTextTitles(String text, String subText) {
        jLabel1.setText("Metadata for " + text);
        subTitle.setText("By " + subText);
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
        IART = new javax.swing.JTextField();
        ILAN = new javax.swing.JTextField();
        INAM = new javax.swing.JTextField();
        IGNR = new javax.swing.JTextField();
        IKEY = new javax.swing.JTextField();
        ISRF = new javax.swing.JTextField();
        IMED = new javax.swing.JTextField();
        IENG = new javax.swing.JTextField();
        ITCH = new javax.swing.JTextField();
        ISRC = new javax.swing.JTextField();
        ICOP = new javax.swing.JTextField();
        ISFT = new javax.swing.JTextField();
        ICRD = new javax.swing.JTextField();
        IALB = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        ISBJ = new javax.swing.JTextField();
        subTitle = new javax.swing.JLabel();
        checkCopy = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        IMG = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ICMT = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 600));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(44, 47, 54));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Metadata for Mabvuto");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 480, 40);

        IART.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IART.setForeground(new java.awt.Color(153, 153, 153));
        IART.setText("Artist");
        IART.setToolTipText("Artist");
        IART.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IART);
        IART.setBounds(260, 70, 170, 30);

        ILAN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ILAN.setForeground(new java.awt.Color(153, 153, 153));
        ILAN.setText("Language");
        ILAN.setToolTipText("Language");
        ILAN.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ILAN);
        ILAN.setBounds(70, 350, 170, 30);

        INAM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        INAM.setForeground(new java.awt.Color(153, 153, 153));
        INAM.setText("Track");
        INAM.setToolTipText("Track");
        INAM.setPreferredSize(new java.awt.Dimension(170, 30));
        INAM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                INAMFocusGained(evt);
            }
        });
        jPanel1.add(INAM);
        INAM.setBounds(70, 70, 170, 30);

        IGNR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IGNR.setForeground(new java.awt.Color(153, 153, 153));
        IGNR.setText("Genre");
        IGNR.setToolTipText("Genre");
        IGNR.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IGNR);
        IGNR.setBounds(70, 110, 170, 30);

        IKEY.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IKEY.setForeground(new java.awt.Color(153, 153, 153));
        IKEY.setText("Keywords");
        IKEY.setToolTipText("Keywords");
        IKEY.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IKEY);
        IKEY.setBounds(260, 110, 170, 30);

        ISRF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISRF.setForeground(new java.awt.Color(153, 153, 153));
        ISRF.setText("Digitization Source");
        ISRF.setToolTipText("Digitization Source");
        ISRF.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISRF);
        ISRF.setBounds(70, 150, 170, 30);

        IMED.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IMED.setForeground(new java.awt.Color(153, 153, 153));
        IMED.setText("Original Medium");
        IMED.setToolTipText("Original Medium");
        IMED.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IMED);
        IMED.setBounds(260, 150, 170, 30);

        IENG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IENG.setForeground(new java.awt.Color(153, 153, 153));
        IENG.setText("Engineers");
        IENG.setToolTipText("Engineers");
        IENG.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IENG);
        IENG.setBounds(70, 190, 170, 30);

        ITCH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ITCH.setForeground(new java.awt.Color(153, 153, 153));
        ITCH.setText("Digitizer");
        ITCH.setToolTipText("Digitizer");
        ITCH.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ITCH);
        ITCH.setBounds(260, 190, 170, 30);

        ISRC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISRC.setForeground(new java.awt.Color(153, 153, 153));
        ISRC.setText("Source Supplier");
        ISRC.setToolTipText("Source Supplier");
        ISRC.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISRC);
        ISRC.setBounds(70, 230, 170, 30);

        ICOP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICOP.setForeground(new java.awt.Color(153, 153, 153));
        ICOP.setText("Copyright");
        ICOP.setToolTipText("Copyright");
        ICOP.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ICOP);
        ICOP.setBounds(260, 230, 170, 30);

        ISFT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISFT.setForeground(new java.awt.Color(153, 153, 153));
        ISFT.setText("Software Package");
        ISFT.setToolTipText("Software Package");
        ISFT.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISFT);
        ISFT.setBounds(70, 270, 170, 30);

        ICRD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICRD.setForeground(new java.awt.Color(153, 153, 153));
        ICRD.setText("Creation Date");
        ICRD.setToolTipText("Creation Date");
        ICRD.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ICRD);
        ICRD.setBounds(260, 270, 170, 30);

        IALB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IALB.setForeground(new java.awt.Color(153, 153, 153));
        IALB.setText("Album");
        IALB.setToolTipText("Album");
        IALB.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IALB);
        IALB.setBounds(260, 310, 170, 30);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonCancel.png"))); // NOI18N
        jButton1.setText("Cancel");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setIconTextGap(-108);
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonCancelOver.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(260, 500, 170, 40);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonSave.png"))); // NOI18N
        jButton2.setText("Save");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setIconTextGap(-105);
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/buttonSaveOver.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(70, 500, 170, 40);

        ISBJ.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISBJ.setForeground(new java.awt.Color(153, 153, 153));
        ISBJ.setText("Subjects");
        ISBJ.setToolTipText("Subjects");
        ISBJ.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISBJ);
        ISBJ.setBounds(70, 310, 170, 30);

        subTitle.setForeground(new java.awt.Color(66, 68, 72));
        subTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subTitle.setText("subTitle");
        subTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(subTitle);
        subTitle.setBounds(0, 44, 490, 20);

        checkCopy.setText("Create a copy on the harddrive");
        jPanel1.add(checkCopy);
        checkCopy.setBounds(70, 470, 180, 23);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/addPic.png"))); // NOI18N
        jButton3.setText("+");
        jButton3.setAlignmentY(0.0F);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setIconTextGap(0);
        jButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/addPicO.png"))); // NOI18N
        jPanel1.add(jButton3);
        jButton3.setBounds(390, 350, 39, 30);

        IMG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IMG.setForeground(new java.awt.Color(153, 153, 153));
        IMG.setText("Image");
        IMG.setToolTipText("Image");
        IMG.setEnabled(false);
        IMG.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IMG);
        IMG.setBounds(260, 350, 170, 30);

        ICMT.setColumns(20);
        ICMT.setForeground(new java.awt.Color(153, 153, 153));
        ICMT.setRows(2);
        ICMT.setText("Comments");
        ICMT.setToolTipText("Comments");
        ICMT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ICMTMouseClicked(evt);
            }
        });
        ICMT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ICMTFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(ICMT);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(70, 390, 360, 70);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void INAMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_INAMFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_INAMFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        saveDialog = new SaveDialog(new javax.swing.JFrame(), true);
        saveDialog.getMeta(this);
        saveDialog.save();
        saveDialog.setVisible(true);

        if (checkCopy.isSelected()) {
            System.out.println("Skriver kopi...");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ICMTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ICMTMouseClicked
        if (ICMT.getText().equals(ICMT.getToolTipText())) {
            ICMT.setText("");
            ICMT.setForeground(new java.awt.Color(44, 47, 54));

        }
    }//GEN-LAST:event_ICMTMouseClicked

    private void ICMTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICMTFocusLost
        if (ICMT.getText().equals("")) {
            ICMT.setBackground(new java.awt.Color(255, 255, 255));
            ICMT.setForeground(new java.awt.Color(153, 153, 153));

            ICMT.setText(ICMT.getToolTipText());
        } else {
            ICMT.setBackground(new java.awt.Color(232, 255, 232));
        }
    }//GEN-LAST:event_ICMTFocusLost

    private void tekstClicked(java.awt.event.MouseEvent evt, JTextField text) {
        if (text.getText().equals(text.getToolTipText())) {
            text.setText("");
            text.setForeground(new java.awt.Color(44, 47, 54));

        }

    }

    private void textFocusLost(java.awt.event.FocusEvent evt, JTextField text) {
        if (text.getText().equals("")) {
            text.setBackground(new java.awt.Color(255, 255, 255));
            text.setForeground(new java.awt.Color(153, 153, 153));

            text.setText(text.getToolTipText());
        } else {
            text.setBackground(new java.awt.Color(232, 255, 232));
        }

    }

    private void textFocusGained(java.awt.event.FocusEvent evt, JTextField text) {
        if (text.getText().equals(text.getToolTipText())) {
            text.setText("");
            text.setForeground(new java.awt.Color(44, 47, 54));
        }

    }

    private void mouseTextField(final JTextField textField) {
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tekstClicked(evt, textField);
            }
        });
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textFocusLost(evt, textField);
            }

            public void focusGained(java.awt.event.FocusEvent evt) {
                textFocusGained(evt, textField);
            }
        });

    }

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
            java.util.logging.Logger.getLogger(MetaEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MetaEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MetaEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MetaEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MetaEdit dialog = new MetaEdit(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField IALB;
    private javax.swing.JTextField IART;
    private javax.swing.JTextArea ICMT;
    private javax.swing.JTextField ICOP;
    private javax.swing.JTextField ICRD;
    private javax.swing.JTextField IENG;
    private javax.swing.JTextField IGNR;
    private javax.swing.JTextField IKEY;
    private javax.swing.JTextField ILAN;
    private javax.swing.JTextField IMED;
    private javax.swing.JTextField IMG;
    private javax.swing.JTextField INAM;
    private javax.swing.JTextField ISBJ;
    private javax.swing.JTextField ISFT;
    private javax.swing.JTextField ISRC;
    private javax.swing.JTextField ISRF;
    private javax.swing.JTextField ITCH;
    private javax.swing.JCheckBox checkCopy;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel subTitle;
    // End of variables declaration//GEN-END:variables
}
