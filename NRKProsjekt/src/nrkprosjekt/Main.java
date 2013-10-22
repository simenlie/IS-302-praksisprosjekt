/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    MusicPanel musicPanel;
    FileDialog fileDialog;
    RecenSearchDialog recentSearchDialog;
    ArrayList<String> searches;
    TopPanel topPanel;
    LeftPanel leftPanel;
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
    String curDir;
    Login login;

    /**
     * Creates new form Main
     */
    public Main() throws LineUnavailableException, IOException, IOException, UnsupportedAudioFileException, InterruptedException, InvocationTargetException {
        authentication();

    }

    private void authentication() {
        login = new Login(new javax.swing.JFrame(), true);
        login.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        System.out.println("Username: admin");
        System.out.println("Password: admin");

        login.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        login.getUsername().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordKeyPressed(evt);
            }
        });

        login.getPassword().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordKeyPressed(evt);
            }
        });
        login.setVisible(true);
    }

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {
        loginCheck();
    }

    private void loginCheck() {

        if (login.authenticate()) {
            login.dispose();
            startProgram();

        };
    }

    private void passwordKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginCheck();

        }
    }

    private void startProgram() {
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

    public void initContent() throws IOException {
        Thread thread22 = new Thread(new Runnable() {
            public void run() {
                Load l = new Load();
                add(l, null);
                try {
                    content = new ContentPanel();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                content.setBorder(null);
                loadPanelA(content, null);
                remove(l);

                repaint();
                revalidate();

            }
        });
        thread22.start();

    }

    public void initLibraries() throws IOException {
        Thread threads = new Thread(new Runnable() {
            public void run() {



                repaint();
                revalidate();
            }
        });
        threads.start();

    }

    public void initialize() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        panels = new HashMap<>();
        recentSearchDialog = new RecenSearchDialog(new javax.swing.JFrame(), false);
        searches = new ArrayList<>();
        //content = new ContentPanel();
        l.setLoadingInfo("Initializing UI");
        setTitle("Music Database Organizer - " + login.user + " " + login.rights);
        fileDialog = new FileDialog(new javax.swing.JFrame(), true);
        metaEdit = new MetaEdit(new javax.swing.JFrame(), true);

        l.setLoadingInfo("Initializing Window properties");
        loadMainComponents();
        l.setLoadingInfo("Loading panels");
        topPanel = new TopPanel();
        leftPanel = new LeftPanel();
        loadPanel(topPanel, BorderLayout.NORTH);
        loadPanel(leftPanel, BorderLayout.WEST);
        String defPath = System.getProperty("user.home") + "\\Desktop";
        musicPanel = new MusicPanel();
        l.setLoadingInfo("Loading Music player");
        loadPanel(musicPanel, BorderLayout.SOUTH);
        //content.setBorder(null);
        //loadPanel(content, null);


        loadScrollBar();



        back = topPanel.getButton2();
        forward = topPanel.getButton3();

        l.setLoadingInfo("Setting up listeners");

        addKeyListener(topPanel.getSearch());
        addActionButton(topPanel.getButton());
        addActionGoBack(topPanel.getButton2());
        addActionGoForward(topPanel.getButton3());

        recAction(leftPanel.getButtonRec());
        libAction(leftPanel.getButtonLib());
        advanAction(leftPanel.getButtonAdvan());
        searchButAction(leftPanel.getButtonSearch());
        addActionFileChooser(fileDialog.getFileChooser());
        //addMouseListener(content.getTable());
        //addMouseListener(content.getTable2());

        System.out.println("1");

        initContent();

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

    public void loadPanelA(JPanel panel, String pos) {
        panels.put(panel.getName(), panel);
        //add(panel, pos);
        add(panel, null, 0);
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


                    UIManager.put("Table.alternateRowColor", new java.awt.Color(236, 235, 232));

                    UIManager.put("TableHeader.background", new java.awt.Color(236, 235, 232));
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

                UIManager.put("ComboBox.background", new ColorUIResource(Color.yellow));
                UIManager.put("JTextField.background", new ColorUIResource(Color.yellow));
                UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.magenta));
                UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.blue));
                UIManager.put("ComboBox.disabledBackground", new Color(212, 212, 210));
                UIManager.put("ComboBox.disabledForeground", Color.BLACK);


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

        but.setEnabled(content.goBack());
        checkNavigation();
        forward.setEnabled(true);
    }

    public void checkNavigation() {
        if (content.getCurrentPage() instanceof SearchPanel) {
            leftPanel.menuClick(leftPanel.getButtonSearch(), false);
        }
        if (content.getCurrentPage() instanceof LibraryOverviewPanel) {
            leftPanel.menuClick(leftPanel.getButtonLib(), false);
        }
        if (content.getCurrentPage() instanceof AdvancedSearchPanel) {
            leftPanel.menuClick(leftPanel.getButtonAdvan(), false);
        }
        if (content.getCurrentPage() instanceof RecentlyAddedPanel) {
            leftPanel.menuClick(leftPanel.getButtonRec(), false);
        }

    }

    private void goForwardActionPerformed(java.awt.event.ActionEvent evt, JButton but) {
        System.out.println("Go back");

        but.setEnabled(content.goForward());
        checkNavigation();
        back.setEnabled(true);
    }

    private void searchTableMouseClicked(java.awt.event.MouseEvent evt) {

        if (evt.getButton() == 1 && content.isLink() || content.isLink2()) {

            content.showPanel("artist");
            back.setEnabled(true);


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
        fileDialog = new FileDialog(new javax.swing.JFrame(), true);
        addActionFileChooser(fileDialog.getFileChooser());
        if (curDir != null) {
            fileDialog.getFileChooser().setCurrentDirectory(new File(curDir));
        }


        if (musicPanel.isPlay()) {
            musicPanel.stopSong();
        }

        fileDialog.setVisible(true);


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



            leftPanel.menuClick(leftPanel.getButton(), true);
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



        leftPanel.menuClick(leftPanel.getButton(), true);
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
        curDir = fileDialog.getFileChooser().getCurrentDirectory().getAbsolutePath();

        if (evt.getActionCommand().equals("ApproveSelection")) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            chosenFile = chooser.getSelectedFile();

            metadata = new Metadata(chosenFile.getAbsolutePath());

            metadata.getTag(Tag.IART);

            metaEdit.setText(Tag.IART.toString(), "s");
            metaEdit.setTextTitles(metadata.getTag(Tag.INAM), metadata.getTag(Tag.IART));
            for (Tag s : Tag.values()) {
                String tempString = metadata.getTag(s);

                String nyL = tempString.substring(tempString.length() - 1);
                String temp;
                if (!nyL.matches("[a-zA-Z]+")) {
                    temp = tempString.substring(0, tempString.length() - 1);
                } else {
                    temp = tempString;
                }


                //System.out.println("2");
                //String temp = tempString.substring(0, tempString.length() - 1);
                metaEdit.setText(s.toString(), temp);

                //System.out.println(s.toString() + " : " + metadata.getTag(s));
            }


            metaEdit.setVisible(true);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));


            musicPanel.setSong(chosenFile.getAbsolutePath());
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
        
        back.setEnabled(content.canGoBack);
        forward.setEnabled(false);
        
        
        Thread recentlyThread = new Thread(new Runnable() {
            public void run() {
                try {
                    content.load();
                    if (content.recentlyA == null) {
                        content.initPanel("recently");
                    } else {
                        //content.lib.updateTable();
                        //System.out.println("Er ikke null");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                content.showPanel("recently");
            }
        });
        recentlyThread.start();
        
        
        
    }

    public void advanAction(JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advanActionPerformed(evt);
            }
        });

    }

    public void searchButAction(JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButActionPerformed(evt);
            }
        });

    }

    private void advanActionPerformed(java.awt.event.ActionEvent evt) {
        
        back.setEnabled(content.canGoBack);
        forward.setEnabled(false);
        
        
        
        Thread advanThread = new Thread(new Runnable() {
            public void run() {
                try {
                    content.load();
                    if (content.advancedS == null) {
                        content.initPanel("advanced");
                    } else {

                        //content.lib.updateTable();
                        //System.out.println("Er ikke null");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                content.showPanel("advancedS");
            }
        });
        advanThread.start();
        

    }

    private void SearchButActionPerformed(java.awt.event.ActionEvent evt) {

        back.setEnabled(content.canGoBack);
        forward.setEnabled(false);

        Thread searchThread = new Thread(new Runnable() {
            public void run() {
                try {
                    content.load();
                    content.initPanel("search");

                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                addMouseListener(content.getTable());

                content.showPanel("search");
            }
        });
        searchThread.start();



    }

    public void libAction(JButton button) {
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    libButActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void libButActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        Thread libThread = new Thread(new Runnable() {
            public void run() {
                try {
                    leftPanel.setLoadVis();
                    
                    if (content.lib == null) {
                        content.load();
                        content.initPanel("lib");
                        content.initPanel("search");
                    } else {

                        content.lib.updateTable();
                        //System.out.println("Er ikke null");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                addMouseListener(content.getTable2());

                content.showPanel("library");
                leftPanel.setLoadVis();
            }
        });
        libThread.start();

        //addMouseListener(content.getTable());

        back.setEnabled(content.canGoBack);
        forward.setEnabled(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
