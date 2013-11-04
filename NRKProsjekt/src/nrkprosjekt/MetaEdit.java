/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Entities.Album;
import Entities.Artist;
import Entities.Picture;
import Handlers.Songhandler;
import Info.Path;
import Info.Tags;
import database.UpdateHandler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;

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
    Thread thread2;
    ArrayList<UpdateHandler> updates;
    int simluationSpeed = 1000;
    int counter = 0;
    HashMap<String, Component> tags;
    SaveDialog saveDialog;
    FileDialog filedialog;
    Calculator calc;
    ImageViewer imageViewer;
    Icon fullRes;
    double fileSize;
    boolean square;
    Songhandler songhandler;
    boolean updating;
    ArrayList<Integer> albumDeletions;
    ArrayList<Integer> artistDeletions;
    ImageIcon imageArt;
    File coverArt;
    SearchPanel searchPanel;
    int curRow;

    /**
     * Creates new form metaEdit
     */
    public MetaEdit(java.awt.Frame parent, boolean modal) throws IOException, SQLException {
        super(parent, modal);
        //setTitle("Edit Track: Mabvuto (1988)");
        tags = new HashMap<>();
        albumDeletions = new ArrayList<>();
        artistDeletions = new ArrayList<>();
        updates = new ArrayList<>();
        initComponents();
        error.setVisible(false);
        initProgressBar();
        addMouseListeners();
        loadMetaEditDialog();
        font = new java.awt.Font("Calibri Light", 0, 24);
        saveDialog = new SaveDialog(new javax.swing.JFrame(), true);
        saveDialog.getMeta(this);
        loadFont();
        fillMap();
        calc = new Calculator();
        crop.setVisible(false);

        songhandler = new Songhandler();
        filedialog = new FileDialog(new javax.swing.JFrame(), true, 1);

        addActionFileChooser(filedialog.getFileChooser());
        setTabbed();
        jTabbedPane1.setBackgroundAt(0, new Color(255, 255, 255));
        jTabbedPane1.setBackgroundAt(1, new Color(235, 235, 235));
        jTabbedPane2.setBackgroundAt(0, new Color(255, 255, 255));
        jTabbedPane2.setBackgroundAt(1, new Color(235, 235, 235));
        imageStatus.setVisible(false);

    }

    MetaEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCurRow(int row) {
        curRow = row;
    }

    public void setSearchPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    public void loadImage(String path) throws IOException {
        square = false;

        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        initImage(path, new ImageIcon(path));


    }

    public void initImage(String path, final ImageIcon icon) throws IOException {
        File f = new File(path);



        double fa = f.length();
        fileSize = fa / 1024 / 1024;
        double fileSizeKB = fa / 1024;

        if (icon.getIconHeight() == icon.getIconWidth()) {
            square = true;
        }
        //imgPreview.setIcon(calc.cropImage(buffered, 120, 120));
        sizeInfo.setText(icon.getIconWidth() + " x " + icon.getIconHeight());
        filS.setText(String.format("%.2f", fileSizeKB) + " KB (" + String.format("%.2f", fileSize) + " MB)");
        imgPreview.setIcon(calc.resizeImage(icon, icon.getIconWidth(), icon.getIconHeight()));
        imageArt = (ImageIcon) calc.resizeImage(icon, icon.getIconWidth(), icon.getIconHeight());
        fullRes = icon;
        if (fileSize > 2) {
            filS.setForeground(new Color(243, 69, 65));
        } else {
            filS.setForeground(new Color(0, 204, 0));
        }
        if (square) {
            squareInfo.setText("Yes");
            squareInfo.setForeground(new Color(0, 204, 0));
        } else {
            squareInfo.setText("No");
            squareInfo.setForeground(new Color(243, 69, 65));
        }
        if (fileSize < 2 && square) {
            imageStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/sucess.png")));
            imageStatus.setToolTipText("Good match");
            crop.setVisible(false);
            crop.setText("Use SmartCropper");
            imageArt = (ImageIcon) calc.resizeImage(icon, icon.getIconWidth(), icon.getIconHeight());
            initImg();
        } else {
            imageStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/error.png")));
            imageStatus.setToolTipText("Bad match");
            crop.setVisible(true);
        }
        imageStatus.setVisible(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    public void fill(Component text, String name) {
        text.setName(name);
        tags.put(name, text);
    }

    public void setExImage(Picture pic) {
        IMG.setText(pic.getName());
        sizeInfo.setText(pic.getRes());
        filS.setText(pic.getSize() + "");
        squareInfo.setText("Yes");
        imgPreview.setIcon(pic.getBilde());
    }

    public JButton getSaveButton() {
        return jButton2;
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

        //DISSE er ikke initialisert
        JTextField ISON = new JTextField();

        fill(ISON, "ISON");
        fill(ILEN, "ILEN");
        fill(ICON, "ICON");

        fill(ICOM, "ICOM");
        fill(ILYR, "ILYR");
        fill(IPLA, "IPLA");
        fill(IREG, "IREG");
        fill(IDIS, "IDIS");
        fill(IVIL, "IVIL");
        fill(IPEO, "IPEO");
        fill(IDIG, "IDIG");

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

        mouseTextField(ILEN);
        mouseTextField(ICON);
        mouseTextField(ICOM);
        mouseTextField(ILYR);
        mouseTextField(IPLA);
        mouseTextField(IREG);
        mouseTextField(IDIS);
        mouseTextField(IVIL);
        mouseTextField(IPEO);
        mouseTextField(IDIG);


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

        } else {
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

    public HashMap<String, Component> getTags() {
        return tags;
    }

    public void loadFont() {
        //jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        //subTitle.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        //jLabel1.getGraphics();
        //jLabel1.getGraphics().getFontMetrics(font);
    }

    public void loadMetaEditDialog() {
        setResizable(false);
        setSize(690, 700);
        setLocationRelativeTo(null);
    }

    public void dos(ArrayList<Artist> artists) {
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("Artists");
        dm.addColumn("Language");
        dm.addColumn("Place");
        dm.addColumn("Country");
        Object row[] = new Object[5];
        for (Artist a : artists) {
            row[0] = a.getId();
            row[1] = a.getIART();
            row[2] = a.getILAN();
            row[3] = a.getIPLA();
            row[4] = a.getICON();
            dm.addRow(row);
            setColors("");
        }

        artistTable.setModel(dm);
        artistTable.getColumnModel().getColumn(0).setMinWidth(0);
        artistTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        artistTable.getColumnModel().getColumn(0).setMaxWidth(0);

    }

    public void dos2(ArrayList<Album> albums) {
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("Album");
        Object row[] = new Object[2];
        for (Album a : albums) {
            row[0] = a.getId();
            row[1] = a.getIALB();
            dm.addRow(row);
        }

        albumTable.setModel(dm);
        setColors("IALB");
        albumTable.getColumnModel().getColumn(0).setMinWidth(0);
        albumTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        albumTable.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    public void prepareArtists() {
        int size = artistTable.getRowCount();
        ArrayList<Artist> tempArtists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int id = (Integer) artistTable.getValueAt(i, 0);
            String IART1 = (String) artistTable.getValueAt(i, 1);
            String ILAN1 = (String) artistTable.getValueAt(i, 2);
            String IPLA1 = (String) artistTable.getValueAt(i, 3);
            String ICON1 = (String) artistTable.getValueAt(i, 4);
            Artist artist = new Artist(id, ILAN1, ICON1, IART1, IPLA1);
            tempArtists.add(artist);
        }
        songhandler.getTrack().setArtists(tempArtists);
    }

    public void prepareAlbums() {
        int size = albumTable.getRowCount();
        ArrayList<Album> tempAlbums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            System.out.println(albumTable.getValueAt(i, 0));
            int id = (Integer) albumTable.getValueAt(i, 0);
            String IALB1 = (String) albumTable.getValueAt(i, 1);
            Album album = new Album(id, IALB1);
            tempAlbums.add(album);
        }
        System.out.println("STOE " + tempAlbums.size());

        songhandler.getTrack().setAlbums(tempAlbums);
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
        // jLabel1.setText("Metadata for " + text);
        //subTitle.setText("By " + subText);
    }

    public void setTabbed() {
        jTabbedPane2.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = Color.pink;
                lightHighlight = new Color(213, 223, 229);
                shadow = new Color(240, 240, 240);
                darkShadow = new Color(213, 223, 229);
                focus = new Color(213, 223, 229);
                //tabPane.setBackgroundAt(tabPane.getSelectedIndex(), darkShadow);
                //getBackground();
                //tabPane.setBackground(UIManager.getColor("TabbedPane.selected"));
                //tabPane.getSelectedComponent().setBackground(focus);
                ;


            }
        });
        jTabbedPane1.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = Color.pink;
                lightHighlight = new Color(213, 223, 229);
                shadow = new Color(240, 240, 240);
                darkShadow = new Color(213, 223, 229);
                focus = new Color(213, 223, 229);
                //tabPane.setBackgroundAt(tabPane.getSelectedIndex(), darkShadow);
                //getBackground();
                //tabPane.setBackground(UIManager.getColor("TabbedPane.selected"));
                //tabPane.getSelectedComponent().setBackground(focus);
                ;


            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        checkCopy = new javax.swing.JCheckBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ICMT = new javax.swing.JTextArea();
        IKEY = new javax.swing.JTextField();
        IGNR = new javax.swing.JTextField();
        INAM = new javax.swing.JTextField();
        IDIS = new javax.swing.JTextField();
        ICOM = new javax.swing.JTextField();
        ILYR = new javax.swing.JTextField();
        IREG = new javax.swing.JTextField();
        IDIG = new javax.swing.JTextField();
        IVIL = new javax.swing.JTextField();
        IPEO = new javax.swing.JTextField();
        ILEN = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        IPLA = new javax.swing.JTextField();
        ICON = new javax.swing.JTextField();
        ILAN = new javax.swing.JTextField();
        IART = new javax.swing.JTextField();
        IALB = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        albumTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        artistTable = new javax.swing.JTable();
        jCheckBox2 = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        ISRC = new javax.swing.JTextField();
        ISFT = new javax.swing.JTextField();
        ISBJ = new javax.swing.JTextField();
        IMED = new javax.swing.JTextField();
        ISRF = new javax.swing.JTextField();
        ICOP = new javax.swing.JTextField();
        ITCH = new javax.swing.JTextField();
        ICRD = new javax.swing.JTextField();
        IENG = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        IMG = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        imgPreview = new javax.swing.JLabel();
        sizeInfo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlabels = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        filS = new javax.swing.JLabel();
        squareInfo = new javax.swing.JLabel();
        imageStatus = new javax.swing.JLabel();
        crop = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

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

        checkCopy.setBackground(new java.awt.Color(250, 250, 250));
        checkCopy.setText("Create a copy on the harddrive");

        jTabbedPane2.setBackground(new java.awt.Color(250, 250, 250));
        jTabbedPane2.setFocusable(false);
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));
        jPanel6.setLayout(null);

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

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(20, 180, 610, 100);

        IKEY.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IKEY.setForeground(new java.awt.Color(153, 153, 153));
        IKEY.setText("Keywords");
        IKEY.setToolTipText("Keywords");
        IKEY.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IKEY);
        IKEY.setBounds(20, 60, 190, 30);

        IGNR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IGNR.setForeground(new java.awt.Color(153, 153, 153));
        IGNR.setText("Genre");
        IGNR.setToolTipText("Genre");
        IGNR.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IGNR);
        IGNR.setBounds(230, 20, 190, 30);

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
        jPanel6.add(INAM);
        INAM.setBounds(20, 20, 190, 30);

        IDIS.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IDIS.setForeground(new java.awt.Color(153, 153, 153));
        IDIS.setText("District");
        IDIS.setToolTipText("District");
        IDIS.setPreferredSize(new java.awt.Dimension(170, 30));
        IDIS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDISActionPerformed(evt);
            }
        });
        jPanel6.add(IDIS);
        IDIS.setBounds(230, 140, 190, 30);

        ICOM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICOM.setForeground(new java.awt.Color(153, 153, 153));
        ICOM.setText("Composer");
        ICOM.setToolTipText("Composer");
        ICOM.setPreferredSize(new java.awt.Dimension(170, 30));
        ICOM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICOMActionPerformed(evt);
            }
        });
        jPanel6.add(ICOM);
        ICOM.setBounds(440, 20, 190, 30);

        ILYR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ILYR.setForeground(new java.awt.Color(153, 153, 153));
        ILYR.setText("Lyric Writer");
        ILYR.setToolTipText("Lyric Writer");
        ILYR.setPreferredSize(new java.awt.Dimension(170, 30));
        ILYR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ILYRActionPerformed(evt);
            }
        });
        jPanel6.add(ILYR);
        ILYR.setBounds(440, 60, 190, 30);

        IREG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IREG.setForeground(new java.awt.Color(153, 153, 153));
        IREG.setText("Region");
        IREG.setToolTipText("Region");
        IREG.setPreferredSize(new java.awt.Dimension(170, 30));
        IREG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IREGActionPerformed(evt);
            }
        });
        jPanel6.add(IREG);
        IREG.setBounds(20, 140, 190, 30);

        IDIG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IDIG.setForeground(new java.awt.Color(153, 153, 153));
        IDIG.setText("Original date");
        IDIG.setToolTipText("Original date");
        IDIG.setPreferredSize(new java.awt.Dimension(170, 30));
        IDIG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDIGActionPerformed(evt);
            }
        });
        jPanel6.add(IDIG);
        IDIG.setBounds(230, 60, 190, 30);

        IVIL.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IVIL.setForeground(new java.awt.Color(153, 153, 153));
        IVIL.setText("Village");
        IVIL.setToolTipText("Village");
        IVIL.setPreferredSize(new java.awt.Dimension(170, 30));
        IVIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IVILActionPerformed(evt);
            }
        });
        jPanel6.add(IVIL);
        IVIL.setBounds(20, 100, 190, 30);

        IPEO.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IPEO.setForeground(new java.awt.Color(153, 153, 153));
        IPEO.setText("People Group");
        IPEO.setToolTipText("People Group");
        IPEO.setPreferredSize(new java.awt.Dimension(170, 30));
        IPEO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPEOActionPerformed(evt);
            }
        });
        jPanel6.add(IPEO);
        IPEO.setBounds(440, 100, 190, 30);

        ILEN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ILEN.setForeground(new java.awt.Color(153, 153, 153));
        ILEN.setText("Length");
        ILEN.setToolTipText("Length");
        ILEN.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(ILEN);
        ILEN.setBounds(230, 100, 190, 30);

        jTabbedPane2.addTab("General information", jPanel6);

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));
        jPanel5.setLayout(null);

        IPLA.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IPLA.setForeground(new java.awt.Color(153, 153, 153));
        IPLA.setText("Place");
        IPLA.setToolTipText("Place");
        IPLA.setPreferredSize(new java.awt.Dimension(170, 30));
        IPLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPLAActionPerformed(evt);
            }
        });
        IPLA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IPLAKeyPressed(evt);
            }
        });
        jPanel5.add(IPLA);
        IPLA.setBounds(20, 70, 140, 30);

        ICON.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICON.setForeground(new java.awt.Color(153, 153, 153));
        ICON.setText("Country/Area");
        ICON.setToolTipText("Country/Area");
        ICON.setPreferredSize(new java.awt.Dimension(170, 30));
        ICON.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ICONKeyPressed(evt);
            }
        });
        jPanel5.add(ICON);
        ICON.setBounds(170, 70, 140, 30);

        ILAN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ILAN.setForeground(new java.awt.Color(153, 153, 153));
        ILAN.setText("Language");
        ILAN.setToolTipText("Language");
        ILAN.setPreferredSize(new java.awt.Dimension(170, 30));
        ILAN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ILANKeyPressed(evt);
            }
        });
        jPanel5.add(ILAN);
        ILAN.setBounds(170, 30, 140, 30);

        IART.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IART.setForeground(new java.awt.Color(153, 153, 153));
        IART.setText("Artist");
        IART.setToolTipText("Artist");
        IART.setPreferredSize(new java.awt.Dimension(145, 30));
        IART.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IARTKeyPressed(evt);
            }
        });
        jPanel5.add(IART);
        IART.setBounds(20, 30, 140, 30);

        IALB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IALB.setForeground(new java.awt.Color(153, 153, 153));
        IALB.setText("Album");
        IALB.setToolTipText("Album");
        IALB.setPreferredSize(new java.awt.Dimension(170, 30));
        IALB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IALBKeyPressed(evt);
            }
        });
        jPanel5.add(IALB);
        IALB.setBounds(340, 30, 290, 30);

        albumTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Album"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        albumTable.setRowHeight(26);
        albumTable.setShowHorizontalLines(false);
        albumTable.setShowVerticalLines(false);
        albumTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                albumTableMouseClicked(evt);
            }
        });
        albumTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                albumTableKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(albumTable);
        albumTable.getColumnModel().getColumn(0).setMinWidth(0);
        albumTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        albumTable.getColumnModel().getColumn(0).setMaxWidth(0);

        jPanel5.add(jScrollPane3);
        jScrollPane3.setBounds(340, 70, 290, 190);

        artistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Artist", "Language", "Place", "Country"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        artistTable.setRowHeight(26);
        artistTable.setShowHorizontalLines(false);
        artistTable.setShowVerticalLines(false);
        artistTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                artistTableMouseClicked(evt);
            }
        });
        artistTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                artistTableKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(artistTable);
        artistTable.getColumnModel().getColumn(0).setMinWidth(0);
        artistTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        artistTable.getColumnModel().getColumn(0).setMaxWidth(0);

        jPanel5.add(jScrollPane4);
        jScrollPane4.setBounds(20, 110, 290, 150);

        jCheckBox2.setBackground(new java.awt.Color(250, 250, 250));
        jCheckBox2.setText("No album");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox2);
        jCheckBox2.setBounds(340, 260, 290, 23);

        jTabbedPane2.addTab("Artists and Albums", jPanel5);

        jTabbedPane1.setBackground(new java.awt.Color(250, 250, 250));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTabbedPane1CaretPositionChanged(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 600));
        jPanel1.setLayout(null);

        ISRC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISRC.setForeground(new java.awt.Color(153, 153, 153));
        ISRC.setText("Source Supplier");
        ISRC.setToolTipText("Source Supplier");
        ISRC.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISRC);
        ISRC.setBounds(20, 10, 190, 30);

        ISFT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISFT.setForeground(new java.awt.Color(153, 153, 153));
        ISFT.setText("Software Package");
        ISFT.setToolTipText("Software Package");
        ISFT.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISFT);
        ISFT.setBounds(20, 50, 190, 30);

        ISBJ.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISBJ.setForeground(new java.awt.Color(153, 153, 153));
        ISBJ.setText("Subjects");
        ISBJ.setToolTipText("Subjects");
        ISBJ.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISBJ);
        ISBJ.setBounds(20, 90, 190, 30);

        IMED.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IMED.setForeground(new java.awt.Color(153, 153, 153));
        IMED.setText("Original Medium");
        IMED.setToolTipText("Original Medium");
        IMED.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IMED);
        IMED.setBounds(440, 10, 190, 30);

        ISRF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISRF.setForeground(new java.awt.Color(153, 153, 153));
        ISRF.setText("Digitization Source");
        ISRF.setToolTipText("Digitization Source");
        ISRF.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISRF);
        ISRF.setBounds(230, 90, 190, 30);

        ICOP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICOP.setForeground(new java.awt.Color(153, 153, 153));
        ICOP.setText("Copyright");
        ICOP.setToolTipText("Copyright");
        ICOP.setPreferredSize(new java.awt.Dimension(170, 30));
        ICOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICOPActionPerformed(evt);
            }
        });
        jPanel1.add(ICOP);
        ICOP.setBounds(230, 50, 190, 30);

        ITCH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ITCH.setForeground(new java.awt.Color(153, 153, 153));
        ITCH.setText("Digitizer");
        ITCH.setToolTipText("Digitizer");
        ITCH.setPreferredSize(new java.awt.Dimension(170, 30));
        ITCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ITCHActionPerformed(evt);
            }
        });
        jPanel1.add(ITCH);
        ITCH.setBounds(230, 10, 190, 30);

        ICRD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICRD.setForeground(new java.awt.Color(153, 153, 153));
        ICRD.setText("Creation Date");
        ICRD.setToolTipText("Creation Date");
        ICRD.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ICRD);
        ICRD.setBounds(440, 50, 190, 30);

        IENG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IENG.setForeground(new java.awt.Color(153, 153, 153));
        IENG.setText("Engineers");
        IENG.setToolTipText("Engineers");
        IENG.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IENG);
        IENG.setBounds(440, 90, 190, 30);

        jTabbedPane1.addTab("Technical information", jPanel1);

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setLayout(null);

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
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(410, 10, 40, 30);

        IMG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IMG.setForeground(new java.awt.Color(153, 153, 153));
        IMG.setText("Image");
        IMG.setToolTipText("Image");
        IMG.setEnabled(false);
        IMG.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel2.add(IMG);
        IMG.setBounds(10, 10, 390, 30);

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        imgPreview.setForeground(new java.awt.Color(71, 170, 221));
        imgPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgPreview.setText("No image selected");
        imgPreview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imgPreview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgPreviewMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgPreviewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgPreviewMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgPreview, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3);
        jPanel3.setBounds(470, 10, 150, 150);

        sizeInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sizeInfo.setText("N/A");
        jPanel2.add(sizeInfo);
        sizeInfo.setBounds(100, 60, 140, 14);

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Image resolution:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 60, 100, 14);

        jlabels.setForeground(new java.awt.Color(102, 102, 102));
        jlabels.setText("Square:");
        jPanel2.add(jlabels);
        jlabels.setBounds(10, 80, 40, 14);

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Image size:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 100, 90, 14);

        filS.setText("N/A");
        jPanel2.add(filS);
        filS.setBounds(70, 100, 160, 14);

        squareInfo.setText("N/A");
        jPanel2.add(squareInfo);
        squareInfo.setBounds(50, 80, 140, 14);

        imageStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/sucess.png"))); // NOI18N
        imageStatus.setToolTipText("Good match");
        jPanel2.add(imageStatus);
        imageStatus.setBounds(160, 120, 40, 40);

        crop.setFont(new java.awt.Font("Calibri Light", 0, 13)); // NOI18N
        crop.setForeground(new java.awt.Color(255, 255, 255));
        crop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/smartCr.png"))); // NOI18N
        crop.setText("Use SmartCropper");
        crop.setContentAreaFilled(false);
        crop.setFocusPainted(false);
        crop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        crop.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/smartCrO.png"))); // NOI18N
        crop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cropActionPerformed(evt);
            }
        });
        jPanel2.add(crop);
        crop.setBounds(10, 120, 130, 40);

        jTabbedPane1.addTab("Cover", jPanel2);

        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Metadata for Mabvuto");

        error.setForeground(new java.awt.Color(255, 51, 51));
        error.setText("ErrorMessage");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(error, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkCopy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(error, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(276, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void INAMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_INAMFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_INAMFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (artistTable.getRowCount() == 0) {
            error.setVisible(true);
            error.setText("A track must have an artist");
        }
        if (albumTable.getRowCount() == 0) {
            error.setVisible(true);
            error.setText("Check 'No album' if this track has no album");
        }
        System.out.println("");
        if (artistTable.getRowCount() != 0 && albumTable.getRowCount() != 0) {
            System.out.println("HERE");
            error.setVisible(false);
            for (int i : albumDeletions) {
                songhandler.deleteWhat("idALBUM", i);
            }

            for (int i : artistDeletions) {
                songhandler.deleteWhat("idARTIST", i);
            }
            System.out.println("HERE");
            saveDialog = new SaveDialog(new javax.swing.JFrame(), true);
            saveDialog.getMeta(this);
            saveDialog.save();
            saveDialog.setVisible(true);
            System.out.println("HERE");
            if (checkCopy.isSelected()) {
                System.out.println("Skriver kopi...");
            }
            for (Tags t : Tags.values()) {
                songhandler.updateField(t.toString(), getText(t.toString()));
            }
            prepareAlbums();
            prepareArtists();
            if (coverArt != null) {

                songhandler.setFile(coverArt);

            }

            if (updating) {
                try {
                    System.out.println("here2");
                    try {
                        songhandler.updateSong();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    System.out.println("here");
                    try {
                        songhandler.saveSong();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        //cleanup oldFIles
        File dir = new File(Path.path);
        HashMap<String, File> tempMap = new HashMap<>();
        for (File f : dir.listFiles()) {
            if (!f.getName().equals("artist.png")) {
                tempMap.put(f.getName(), f);
            }

        }


        for (String s : tempMap.keySet()) {
            System.out.println(tempMap.get(s).delete());
            //tempMap.get(s).delete();
        }

        searchPanel.content.lib.updateTable();
        searchPanel.getTable().setRowSelectionInterval(curRow,curRow);

    }//GEN-LAST:event_jButton2ActionPerformed

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

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
            //DENNE HALVFUNGERER
            updates.add(new UpdateHandler(ICMT, "METADATA", 1));
        }
    }//GEN-LAST:event_ICMTFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        filedialog = new FileDialog(new javax.swing.JFrame(), true, 1);
        addActionFileChooser(filedialog.getFileChooser());
        filedialog.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void imgPreviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgPreviewMouseClicked
        if (fullRes == null) {
        } else {
            imageViewer = new ImageViewer(new javax.swing.JFrame(), true, fullRes.getIconWidth(), fullRes.getIconHeight());
            imageViewer.setImageHolder(fullRes);
            imageViewer.setVisible(true);
        }


    }//GEN-LAST:event_imgPreviewMouseClicked

    private void imgPreviewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgPreviewMouseEntered
        if (fullRes != null) {
            imgPreview.setText("Click to see full size");
        }

    }//GEN-LAST:event_imgPreviewMouseEntered

    private void imgPreviewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgPreviewMouseExited
        if (fullRes != null) {
            imgPreview.setText("");
        }
    }//GEN-LAST:event_imgPreviewMouseExited

    private void jTabbedPane1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTabbedPane1CaretPositionChanged
        System.out.println("d");
    }//GEN-LAST:event_jTabbedPane1CaretPositionChanged

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        if (jTabbedPane1.getSelectedIndex() == 0) {
            jTabbedPane1.setBackgroundAt(0, new Color(255, 255, 255));
            jTabbedPane1.setBackgroundAt(1, new Color(235, 235, 235));

        } else {
            jTabbedPane1.setBackgroundAt(1, new Color(255, 255, 255));
            jTabbedPane1.setBackgroundAt(0, new Color(235, 235, 235));

        }

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    public void initImg() throws IOException {
        Image img = imageArt.getImage();

        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        String path = Path.path + filedialog.getFileChooser().getSelectedFile().getName();
        ImageIO.write(bi, "jpg", new File(path));

        System.out.println(path);

        coverArt = new File(path);



    }

    private void cropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cropActionPerformed
        thread = new Thread(new Runnable() {
            public void run() {
                crop.setText("Cropping...");
                try {

                    String path = filedialog.getFileChooser().getSelectedFile().getAbsolutePath();
                    Image image = ImageIO.read(new File(path));
                    BufferedImage buffered = (BufferedImage) image;
                    int pixelStart = 0;
                    int pixelX = 0;
                    int xy = 0;
                    if (buffered.getWidth() > buffered.getHeight()) {
                        pixelStart = buffered.getHeight();
                        xy = buffered.getWidth();
                        pixelX = 0;
                    } else {
                        pixelStart = buffered.getWidth();
                        xy = buffered.getHeight();
                        pixelX = 1;
                    }
                    ImageIcon i = (ImageIcon) calc.cropImage(buffered, pixelStart, pixelStart, xy, pixelX);
                    //imageArt = i;

                    initImage(path, i);
                    initImg();
                    sizeInfo.setText(sizeInfo.getText() + " (cropped)");
                    // imgPreview.setIcon(calc.resizeImage(i, i.getIconWidth(), i.getIconHeight()));
                } catch (IOException ex) {
                    Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();



    }//GEN-LAST:event_cropActionPerformed

    private void ITCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ITCHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ITCHActionPerformed

    private void ICOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICOPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICOPActionPerformed

    private void IDISActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDISActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDISActionPerformed

    private void ICOMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICOMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICOMActionPerformed

    private void ILYRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ILYRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ILYRActionPerformed

    private void IPLAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPLAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPLAActionPerformed

    private void IREGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IREGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IREGActionPerformed

    private void IDIGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDIGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDIGActionPerformed

    private void IVILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IVILActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IVILActionPerformed

    private void IPEOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPEOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPEOActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        if (jTabbedPane2.getSelectedIndex() == 0) {
            jTabbedPane2.setBackgroundAt(0, new Color(255, 255, 255));
            jTabbedPane2.setBackgroundAt(1, new Color(235, 235, 235));

        } else {
            jTabbedPane2.setBackgroundAt(1, new Color(255, 255, 255));
            jTabbedPane2.setBackgroundAt(0, new Color(235, 235, 235));

        }
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void IARTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IARTKeyPressed
    }//GEN-LAST:event_IARTKeyPressed

    private void artistTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_artistTableMouseClicked
        IART.setText((String) artistTable.getValueAt(artistTable.getSelectedRow(), 1));
        ILAN.setText((String) artistTable.getValueAt(artistTable.getSelectedRow(), 2));
        IPLA.setText((String) artistTable.getValueAt(artistTable.getSelectedRow(), 3));
        ICON.setText((String) artistTable.getValueAt(artistTable.getSelectedRow(), 4));
    }//GEN-LAST:event_artistTableMouseClicked

    private void artistTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_artistTableKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            DefaultTableModel model = (DefaultTableModel) artistTable.getModel();
            System.out.println("CLick");
            int id = (Integer) artistTable.getValueAt(artistTable.getSelectedRow(), 0);
            model.removeRow(artistTable.getSelectedRow());
            IART.setText("");
            artistDeletions.add(id);


        }
    }//GEN-LAST:event_artistTableKeyPressed

    private void ILANKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ILANKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ILANKeyPressed

    private void IPLAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IPLAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPLAKeyPressed

    private void ICONKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ICONKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int index = artistTable.getModel().getRowCount();
            boolean exists = false;
            boolean update = false;
            for (int i = 0; i < index; i++) {
                if (IART.getText().equals(artistTable.getValueAt(i, 0))) {

                    exists = true;
                }
                if (artistTable.isRowSelected(i)) {
                    update = true;
                }

            }

            if (update) {
                artistTable.setValueAt(artistTable.getValueAt(artistTable.getSelectedRow(), 0), artistTable.getSelectedRow(), 0);
                artistTable.setValueAt(IART.getText(), artistTable.getSelectedRow(), 1);
                artistTable.setValueAt(ILAN.getText(), artistTable.getSelectedRow(), 2);
                artistTable.setValueAt(IPLA.getText(), artistTable.getSelectedRow(), 3);
                artistTable.setValueAt(ICON.getText(), artistTable.getSelectedRow(), 4);
                artistTable.removeRowSelectionInterval(artistTable.getSelectedRow(), artistTable.getSelectedRow());
                setColors("");
            } else {
                if (!exists) {
                    DefaultTableModel model = (DefaultTableModel) artistTable.getModel();
                    model.addRow(new Object[]{0, IART.getText(), ILAN.getText(), IPLA.getText(), ICON.getText()});
                    setColors("");
                } else {
                    IART.setBackground(new Color(255, 204, 204));

                }
            }






        }
    }//GEN-LAST:event_ICONKeyPressed

    private void albumTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_albumTableKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            DefaultTableModel model = (DefaultTableModel) albumTable.getModel();
            int id = (Integer) albumTable.getValueAt(albumTable.getSelectedRow(), 0);
            model.removeRow(albumTable.getSelectedRow());
            IALB.setText("");
            albumDeletions.add(id);


        }
    }//GEN-LAST:event_albumTableKeyPressed

    private void albumTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_albumTableMouseClicked
        IALB.setText((String) albumTable.getValueAt(albumTable.getSelectedRow(), 1));
    }//GEN-LAST:event_albumTableMouseClicked

    private void IALBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IALBKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int index = albumTable.getModel().getRowCount();
            boolean exists = false;
            boolean update = false;
            for (int i = 0; i < index; i++) {
                if (IALB.getText().equals(albumTable.getValueAt(i, 0))) {

                    exists = true;
                }
                if (albumTable.isRowSelected(i)) {
                    update = true;
                }

            }

            if (update) {

                albumTable.setValueAt(albumTable.getValueAt(albumTable.getSelectedRow(), 0), albumTable.getSelectedRow(), 0);
                albumTable.setValueAt(IALB.getText(), albumTable.getSelectedRow(), 1);

                albumTable.removeRowSelectionInterval(albumTable.getSelectedRow(), albumTable.getSelectedRow());
                setColors("IALB");
            } else {
                if (!exists) {
                    DefaultTableModel model = (DefaultTableModel) albumTable.getModel();
                    model.addRow(new Object[]{0, IALB.getText()});
                    setColors("IALB");

                } else {
                    IALB.setBackground(new Color(255, 204, 204));

                }
            }

        }
    }//GEN-LAST:event_IALBKeyPressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        int rowCount = albumTable.getRowCount();
        if (jCheckBox2.isSelected()) {
            IALB.setBackground(new Color(230, 230, 230));
            IALB.setEnabled(false);
            albumTable.setEnabled(false);

            if (rowCount == 0) {
                DefaultTableModel dm = (DefaultTableModel) albumTable.getModel();
                dm.addRow(new Object[]{0, "N/A"});
            }


        } else {
            DefaultTableModel dm = (DefaultTableModel) albumTable.getModel();
            IALB.setEnabled(true);
            IALB.setBackground(new Color(255, 255, 255));
            albumTable.setEnabled(true);

            for (int i = 0; i < rowCount; i++) {
                if (albumTable.getValueAt(i, 1).equals("N/A")) {
                    int ta = (Integer) albumTable.getValueAt(i, 0);
                    dm.removeRow(i);
                    albumDeletions.add(ta);
                }
            }



        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    public void setColors(String alb) {

        if (alb.equals("IALB")) {
            IALB.setText("Album");
            IALB.setForeground(new Color(153, 153, 153));
            IALB.setBackground(new java.awt.Color(255, 255, 255));
            IALB.setBackground(new java.awt.Color(255, 255, 255));
        } else {

            System.out.println("NAAAAA");
            IART.setText("Artist");
            ILAN.setText("Language");
            IPLA.setText("Place");
            ICON.setText("Country/Area");
            IART.setForeground(new Color(153, 153, 153));
            ILAN.setForeground(new Color(153, 153, 153));
            IPLA.setForeground(new Color(153, 153, 153));
            ICON.setForeground(new Color(153, 153, 153));
            IART.setBackground(new java.awt.Color(255, 255, 255));
            ILAN.setBackground(new java.awt.Color(255, 255, 255));
            IPLA.setBackground(new java.awt.Color(255, 255, 255));
            ICON.setBackground(new java.awt.Color(255, 255, 255));
        }
    }

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
                MetaEdit dialog = null;
                try {
                    try {
                        dialog = new MetaEdit(new javax.swing.JFrame(), true);
                    } catch (SQLException ex) {
                        Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
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

    public void addActionFileChooser(final JFileChooser chooser) {
        chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    fileChooserActionPerformed(evt, chooser);
                } catch (IOException ex) {
                    Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt, JFileChooser chooser) throws IOException {
        filedialog.dispose();



        if (evt.getActionCommand().equals("ApproveSelection")) {
            IMG.setText(filedialog.getFileChooser().getSelectedFile().getAbsolutePath());
            imgPreview.setText("");
            loadImage(filedialog.getFileChooser().getSelectedFile().getAbsolutePath());
            jButton3.setText("+");
            revalidate();
            repaint();

        } else if (evt.getActionCommand().equals("CancelSelection")) {
            //System.out.println("can");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IALB;
    private javax.swing.JTextField IART;
    private javax.swing.JTextArea ICMT;
    private javax.swing.JTextField ICOM;
    private javax.swing.JTextField ICON;
    private javax.swing.JTextField ICOP;
    private javax.swing.JTextField ICRD;
    private javax.swing.JTextField IDIG;
    private javax.swing.JTextField IDIS;
    private javax.swing.JTextField IENG;
    private javax.swing.JTextField IGNR;
    private javax.swing.JTextField IKEY;
    private javax.swing.JTextField ILAN;
    private javax.swing.JTextField ILEN;
    private javax.swing.JTextField ILYR;
    private javax.swing.JTextField IMED;
    private javax.swing.JTextField IMG;
    private javax.swing.JTextField INAM;
    private javax.swing.JTextField IPEO;
    private javax.swing.JTextField IPLA;
    private javax.swing.JTextField IREG;
    private javax.swing.JTextField ISBJ;
    private javax.swing.JTextField ISFT;
    private javax.swing.JTextField ISRC;
    private javax.swing.JTextField ISRF;
    private javax.swing.JTextField ITCH;
    private javax.swing.JTextField IVIL;
    private javax.swing.JTable albumTable;
    private javax.swing.JTable artistTable;
    private javax.swing.JCheckBox checkCopy;
    private javax.swing.JButton crop;
    private javax.swing.JLabel error;
    private javax.swing.JLabel filS;
    private javax.swing.JLabel imageStatus;
    private javax.swing.JLabel imgPreview;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel jlabels;
    private javax.swing.JLabel sizeInfo;
    private javax.swing.JLabel squareInfo;
    // End of variables declaration//GEN-END:variables
}
