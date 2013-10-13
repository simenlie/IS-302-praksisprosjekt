/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import database.UpdateHandler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

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

    /**
     * Creates new form metaEdit
     */
    public MetaEdit(java.awt.Frame parent, boolean modal) throws IOException {
        super(parent, modal);
        //setTitle("Edit Track: Mabvuto (1988)");
        tags = new HashMap<>();
        updates = new ArrayList<>();
        initComponents();
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


        filedialog = new FileDialog(new javax.swing.JFrame(), true, 1);

        addActionFileChooser(filedialog.getFileChooser());
        setTabbed();
        jTabbedPane1.setBackgroundAt(0, new Color(255, 255, 255));
        jTabbedPane1.setBackgroundAt(1, new Color(235, 235, 235));
        imageStatus.setVisible(false);

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
        JTextField ILEN = new JTextField();
        JTextField ICON = new JTextField();
        fill(ISON, "ISON");
        fill(ILEN, "ILEN");
        fill(ICON, "ICON");

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
        setSize(500, 650);
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
        // jLabel1.setText("Metadata for " + text);
        //subTitle.setText("By " + subText);
    }

    public void setTabbed() {
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
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ICMT = new javax.swing.JTextArea();
        IENG = new javax.swing.JTextField();
        ILAN = new javax.swing.JTextField();
        IKEY = new javax.swing.JTextField();
        ICRD = new javax.swing.JTextField();
        IGNR = new javax.swing.JTextField();
        IALB = new javax.swing.JTextField();
        INAM = new javax.swing.JTextField();
        IART = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        ISRC = new javax.swing.JTextField();
        ISFT = new javax.swing.JTextField();
        ISBJ = new javax.swing.JTextField();
        IMED = new javax.swing.JTextField();
        ISRF = new javax.swing.JTextField();
        ICOP = new javax.swing.JTextField();
        ITCH = new javax.swing.JTextField();
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

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("General information"));
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
        jScrollPane1.setBounds(20, 180, 410, 60);

        IENG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IENG.setForeground(new java.awt.Color(153, 153, 153));
        IENG.setText("Engineers");
        IENG.setToolTipText("Engineers");
        IENG.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IENG);
        IENG.setBounds(20, 140, 190, 30);

        ILAN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ILAN.setForeground(new java.awt.Color(153, 153, 153));
        ILAN.setText("Language");
        ILAN.setToolTipText("Language");
        ILAN.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(ILAN);
        ILAN.setBounds(240, 140, 190, 30);

        IKEY.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IKEY.setForeground(new java.awt.Color(153, 153, 153));
        IKEY.setText("Keywords");
        IKEY.setToolTipText("Keywords");
        IKEY.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IKEY);
        IKEY.setBounds(20, 100, 190, 30);

        ICRD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICRD.setForeground(new java.awt.Color(153, 153, 153));
        ICRD.setText("Creation Date");
        ICRD.setToolTipText("Creation Date");
        ICRD.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(ICRD);
        ICRD.setBounds(240, 100, 190, 30);

        IGNR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IGNR.setForeground(new java.awt.Color(153, 153, 153));
        IGNR.setText("Genre");
        IGNR.setToolTipText("Genre");
        IGNR.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IGNR);
        IGNR.setBounds(20, 60, 190, 30);

        IALB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IALB.setForeground(new java.awt.Color(153, 153, 153));
        IALB.setText("Album");
        IALB.setToolTipText("Album");
        IALB.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IALB);
        IALB.setBounds(240, 60, 190, 30);

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

        IART.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IART.setForeground(new java.awt.Color(153, 153, 153));
        IART.setText("Artist");
        IART.setToolTipText("Artist");
        IART.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel6.add(IART);
        IART.setBounds(240, 20, 190, 30);

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
        ISRC.setBounds(10, 10, 190, 30);

        ISFT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISFT.setForeground(new java.awt.Color(153, 153, 153));
        ISFT.setText("Software Package");
        ISFT.setToolTipText("Software Package");
        ISFT.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISFT);
        ISFT.setBounds(10, 50, 190, 30);

        ISBJ.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ISBJ.setForeground(new java.awt.Color(153, 153, 153));
        ISBJ.setText("Subjects");
        ISBJ.setToolTipText("Subjects");
        ISBJ.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ISBJ);
        ISBJ.setBounds(10, 90, 190, 30);

        IMED.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IMED.setForeground(new java.awt.Color(153, 153, 153));
        IMED.setText("Original Medium");
        IMED.setToolTipText("Original Medium");
        IMED.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(IMED);
        IMED.setBounds(10, 130, 190, 30);

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
        jPanel1.add(ICOP);
        ICOP.setBounds(230, 50, 190, 30);

        ITCH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ITCH.setForeground(new java.awt.Color(153, 153, 153));
        ITCH.setText("Digitizer");
        ITCH.setToolTipText("Digitizer");
        ITCH.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel1.add(ITCH);
        ITCH.setBounds(230, 10, 190, 30);

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
        jButton3.setBounds(220, 10, 40, 30);

        IMG.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IMG.setForeground(new java.awt.Color(153, 153, 153));
        IMG.setText("Image");
        IMG.setToolTipText("Image");
        IMG.setEnabled(false);
        IMG.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel2.add(IMG);
        IMG.setBounds(10, 10, 210, 30);

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
        jPanel3.setBounds(280, 10, 150, 150);

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
        imageStatus.setBounds(12, 120, 40, 40);

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
        crop.setBounds(60, 120, 130, 40);

        jTabbedPane1.addTab("Cover", jPanel2);

        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Metadata for Mabvuto");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkCopy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(242, Short.MAX_VALUE))
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
            //DENNE HALVFUNGERER
            updates.add(new UpdateHandler(ICMT, "METADATA", 1));
        }
    }//GEN-LAST:event_ICMTFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
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
                    initImage(path, i);
                    sizeInfo.setText(sizeInfo.getText() + " (cropped)");
                    // imgPreview.setIcon(calc.resizeImage(i, i.getIconWidth(), i.getIconHeight()));
                } catch (IOException ex) {
                    Logger.getLogger(MetaEdit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();



    }//GEN-LAST:event_cropActionPerformed

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
                    dialog = new MetaEdit(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton crop;
    private javax.swing.JLabel filS;
    private javax.swing.JLabel imageStatus;
    private javax.swing.JLabel imgPreview;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlabels;
    private javax.swing.JLabel sizeInfo;
    private javax.swing.JLabel squareInfo;
    // End of variables declaration//GEN-END:variables
}
