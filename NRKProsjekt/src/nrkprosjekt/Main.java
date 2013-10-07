/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import jwawfile.BadRIFFException;
import jwawfile.Metadata;
import jwawfile.Tag;

/**
 *
 * @author Simen
 */
public class Main extends javax.swing.JFrame {

    HashMap<String, JPanel> panels;
    FileDialog fileDialog;
    RecenSearchDialog recentSearchDialog;
    ArrayList<String> searches;
    TopPanel topPanel;
    JFrame tempF;
    ContentPanel content;
    boolean nos = true;
    Thread thread;
    File currentFile;
    File chosenFile;
    Metadata metadata;
    MetaEdit metaEdit;
    JButton back;
    JButton forward;
    long start;
    long slutt;
    Thread thread2;
    Loader l;
    String loaderInformation;

    /**
     * Creates new form Main
     */
    public Main() throws LineUnavailableException, IOException, IOException, UnsupportedAudioFileException, InterruptedException, InvocationTargetException {
        start = System.currentTimeMillis();
        System.out.println("Start");
        l = new Loader(new javax.swing.JFrame(), true);

        thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("a");
                initComponents();
                try {
                    initialize();
                    l.dispose();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep(1000 / 1);  // milliseconds
                } catch (InterruptedException ex) {
                }
            }
        });
        thread.start();



        l.setVisible(true);



        //thread.interrupt();

        System.out.println(System.currentTimeMillis() - start);
    }

    public void initialize() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        panels = new HashMap<>();
        recentSearchDialog = new RecenSearchDialog(new javax.swing.JFrame(), false);
        searches = new ArrayList<>();
        content = new ContentPanel();
        l.setLoadingInfo("Initializing UI");
        setTitle("Music Database Organizer");
        fileDialog = new FileDialog(new javax.swing.JFrame(), true);
        metaEdit = new MetaEdit(new javax.swing.JFrame(), true);

        l.setLoadingInfo("Initializing Window properties");
        loadMainComponents();
        l.setLoadingInfo("Loading panels");
        topPanel = new TopPanel();
        loadPanel(topPanel, BorderLayout.NORTH);
        loadPanel(new LeftPanel(), BorderLayout.WEST);
        String defPath = System.getProperty("user.home") + "\\Desktop";

        l.setLoadingInfo("Loading Music player");
        loadPanel(new MusicPanel(defPath + "\\Mabvuto.wav"), BorderLayout.SOUTH);
        loadPanel(content, null);

        loadScrollBar();



        back = topPanel.getButton2();
        forward = topPanel.getButton3();

        l.setLoadingInfo("Setting up listeners");

        addKeyListener(topPanel.getSearch());
        addActionButton(topPanel.getButton());
        addActionGoBack(topPanel.getButton2());
        addActionGoForward(topPanel.getButton3());
        LeftPanel h = (LeftPanel) panels.get("Left");
        recAction(h.getButtonRec());
        libAction(h.getButtonLib());
        addActionFileChooser(fileDialog.getFileChooser());
        addMouseListener(content.getTable());

        System.out.println("1");


    }

    public void loadMainComponents() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(600, 300));

    }

    private void loadScrollBar() {
        JScrollPane scroll = new javax.swing.JScrollPane();
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.WEST);
        scroll.setViewportView(panels.get("Left"));
    }

    public void loadPanel(JPanel panel, String pos) {
        panels.put(panel.getName(), panel);
        add(panel, pos);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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

                    UIManager.getLookAndFeelDefaults().put("Table:\"Table.cellRenderer\".background",
                            new ColorUIResource(new java.awt.Color(51, 51, 51)));


                    UIManager.put("Table.alternateRowColor", new java.awt.Color(243, 242, 242));

                    UIManager.put("TableHeader.background", new java.awt.Color(81, 81, 81));
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {


                try {
                    new Main().setVisible(true);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        });
    }

    public void addKeyListener(final JTextField textBox) {

        textBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBoxKeyPressed(evt, textBox);
            }
        });

    }

    public void addMouseListener(JTable table) {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTableMouseClicked(evt);
            }
        });

    }

    public void addActionGoBack(final JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackActionPerformed(evt, button);
            }
        });

    }

    public void addActionGoForward(final JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goForwardActionPerformed(evt, button);
            }
        });

    }

    private void goBackActionPerformed(java.awt.event.ActionEvent evt, JButton but) {
        System.out.println("Go back");
        but.setEnabled(content.navBack());
        forward.setEnabled(true);
    }

    private void goForwardActionPerformed(java.awt.event.ActionEvent evt, JButton but) {
        System.out.println("Go back");
        but.setEnabled(content.navForward());
        back.setEnabled(true);
    }

    private void searchTableMouseClicked(java.awt.event.MouseEvent evt) {

        if (evt.getButton() == 1 && content.isLink()) {
            System.out.println("left clicked");
            content.showPanel("artist");
            back.setEnabled(true);
            forward.setEnabled(content.isLastPage());

            //searchTable.getValueAt(selectedRow, selectedCol);
        }
    }

    public void addActionButton(JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
    }

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {
        fileDialog.setVisible(true);
        fileDialog.getChosenFile();
    }

    private void searchBoxKeyPressed(java.awt.event.KeyEvent evt, final JTextField textBox) {
        int keyCode = evt.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER && topPanel.isCtrlDown()) {
            recentSearchDialog = new RecenSearchDialog(new javax.swing.JFrame(), false);
            recentSearchDialog.updateList(searches);
            for (JLabel j : recentSearchDialog.getlist()) {
                mouseRecentSearches(j);
            }

            recentSearchDialog.setLocation(evt.getComponent().getLocationOnScreen().x, evt.getComponent().getLocationOnScreen().y + 41);
            recentSearchDialog.setVisible(true);
            System.out.println(textBox.getLocation().x);
        } else if (keyCode == KeyEvent.VK_ENTER) {
            searches.add(0, textBox.getText());
            System.out.println("Searching");
            content.showPanel("search");
            content.getSearchPanel().setShowResult(textBox.getText());


            LeftPanel temp = (LeftPanel) panels.get("Left");
            temp.menuClick(temp.getButton(), true);
        }
    }

    public void mouseRecentSearches(final JLabel label) {

        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt, label);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt, label);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt, label);
            }
        });


    }

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt, JLabel label) {
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
    }

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt, JLabel label) {
        label.setFont(new java.awt.Font("Tahoma", 0, 11));
    }

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt, JLabel label) {
        searches.add(0, label.getText());
        System.out.println("Searching");
        content.showPanel("search");
        content.getSearchPanel().setShowResult(label.getText());


        LeftPanel temp = (LeftPanel) panels.get("Left");
        temp.menuClick(temp.getButton(), true);
        recentSearchDialog.dispose();
    }

    public void addActionFileChooser(final JFileChooser chooser) {
        chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    fileChooserActionPerformed(evt, chooser);
                } catch (BadRIFFException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt, JFileChooser chooser) throws BadRIFFException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        fileDialog.setVisible(false);


        if (evt.getActionCommand().equals("ApproveSelection")) {
            chosenFile = chooser.getSelectedFile();

            metadata = new Metadata(chosenFile.getAbsolutePath());
            metadata.getTag(Tag.IART);

            metaEdit.setText(Tag.IART.toString(), "s");
            metaEdit.setTextTitles(metadata.getTag(Tag.INAM), metadata.getTag(Tag.IART));
            for (Tag s : Tag.values()) {
                String tempString = metadata.getTag(s);
                String temp = tempString.substring(0, tempString.length() - 1);
                metaEdit.setText(s.toString(), temp);
                System.out.println(s.toString() + " : " + metadata.getTag(s));
            }


            metaEdit.setVisible(true);

            MusicPanel p = (MusicPanel) panels.get("music");
            p.setSong(chosenFile.getAbsolutePath());
        } else if (evt.getActionCommand().equals("CancelSelection")) {
            //System.out.println("can");
        }
    }

    public void recAction(JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recButActionPerformed(evt);
            }
        });

    }

    private void recButActionPerformed(java.awt.event.ActionEvent evt) {
        content.showRecContent();
    }
    
       public void libAction(JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libButActionPerformed(evt);
            }
        });

    }
       
       private void libButActionPerformed(java.awt.event.ActionEvent evt) {
        content.showLibraryContent();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
