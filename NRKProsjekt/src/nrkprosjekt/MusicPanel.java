/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import Info.Style;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Simen
 */
public class MusicPanel extends javax.swing.JPanel {
    
    Calculator c;
    JLabel progressing = new JLabel();
    JLabel progress1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel emptyBar = new JLabel();
    JLabel textInfo = new JLabel();
    JLabel textInfo2 = new JLabel();
    JLabel totalTimet = new JLabel();
    JLabel currentTime = new JLabel();
    boolean play;
    MusicPlayer musicPlayer;
    Thread thread;
    Thread thread2;
    final int FUDGE_FACTOR = 112;
    boolean safe = true;
    Timer songNameTimer;
    Animation songNameAni;
    Animation animation;
    boolean pl = false;
    boolean hasSong = false;
    long test;
    Queue<String> playerInfo;

    /**
     * Creates new form MusicPlayer
     */
    public MusicPanel() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        setName("music");
        playerInfo = new LinkedList<>();
        //musicPlayer = new MusicPlayer(path);
        initComponents();
        c = new Calculator();
        // setSize(100, 100);
        JLabel back = new JLabel();
        back.setSize(2000, 63);
        back.setLocation(0, 1);
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/windowAlt.png")));
        
        
        add(playButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        System.out.println(getBounds().width);
        
        jLabel2.setSize(16, 15);
        jLabel2.setLocation(110, 26);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/spinna.png"))); // NOI18N

        
        
        emptyBar.setSize(563, 7);
        emptyBar.setLocation(113, 30);
        //emptyBar.setBounds(FUDGE_FACTOR, 30, 563, 7);
        emptyBar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/barEmpty.png"))); // NOI18N
        add(emptyBar);
        
        add(jLabel2);
        emptyBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emptyBarMouseClicked(evt);
            }
        });
        
        progressing.setSize(563, 7);
        progressing.setLocation(113, 30);
        progressing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/bar2.png"))); // NOI18N
        add(progressing);
        progressing.setSize(0, 7);
        
        progress1.setSize(563, 7);
        progress1.setLocation(113, 30);
        progress1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/barBackAlt.png"))); // NOI18N
        add(progress1);



        //progress1.setSize(0, 7);

        
        addResizeListener();
        
        
        textInfo.setForeground(new Color(51, 51, 51));
        int temp = (getSize().width / 2) - 50;
        textInfo.setBounds(temp, 8, 150, 15);
        textInfo.setFont(new Font("Calibri Light", 0, 14));
        textInfo.setText("No song selected");
        textInfo.setForeground(Style.getDisabledColor());
        System.out.println(textInfo.getBounds().x + " temp: " + temp);
        //textInfo.setAlignmentY(TOP_ALIGNMENT);
        add(textInfo, BorderLayout.CENTER);
        
        textInfo2.setForeground(new Color(153, 153, 153));
        textInfo2.setBounds(-150, 8, 150, 15);
        textInfo2.setFont(new Font("Calibri Light", 0, 13));
        //textInfo2.setText("Hot Fingers");

        add(textInfo2, BorderLayout.CENTER);

        //totalTimet.setText(musicPlayer.getTotalTime());;
        totalTimet.setBounds(100, 27, 100, 15);
        totalTimet.setForeground(new Color(0, 0, 0));
        add(totalTimet);
        
        currentTime.setText("0:00");
        currentTime.setBounds(100, 27, 100, 15);
        currentTime.setForeground(new Color(0, 0, 0));
        add(currentTime);
        
        add(back);
        loadAnim();
        action();
        
    }
    
    public void setTextInfo(String text) {
        textInfo.setText(text);
    }
    
    public void setTextInfo2(String text) {
        textInfo2.setText(text);
    }
    
    public void loadAnim() {
        
        thread2 = new Thread(new Runnable() {
            public void run() {
                songNameAni = new Animation(textInfo, progress1, textInfo2);
                
            }
        });
        
        thread2.start();
        
        
    }

    public void addMetaInfo(String s) {
        playerInfo.add(s);
        
    }

    public JButton getButton() {
        return playButton;
    }
    
    public void action() {
        animation = new Animation(textInfo2, progress1, textInfo2, 4000, playerInfo);
    }
    
    public void resetMusicPlayer() {
        musicPlayer = null;
    }
    
    public void setSong(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        musicPlayer = new MusicPlayer(filepath);
        //textInfo.setText(musicPlayer.getInfoSong());
        textInfo.setForeground(new Color(51, 51, 51));
        totalTimet.setText(musicPlayer.getTotalTime());
        hasSong = true;
    }
    
    public void stopSong() {
        musicPlayer.pause();
        
    }
    
    public void addResizeListener() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent evt) {
                thread = new Thread(new Runnable() {
                    public void run() {
                        ImageIcon i = (ImageIcon) progress1.getIcon();
                        ImageIcon i2 = (ImageIcon) progressing.getIcon();
                        ImageIcon i3 = (ImageIcon) emptyBar.getIcon();
                        progress1.setIcon(c.dos(i, getPercentage(80)));
                        progress1.setSize(getPercentage(80), 7);
                        
                        emptyBar.setIcon(c.dos(i3, getPercentage(80)));
                        emptyBar.setSize(getPercentage(80), 7);
                        
                        progressing.setIcon(c.dos(i2, getPercentage(80)));
                        
                        if (!pl) {
                            textInfo.setBounds((getSize().width / 2) - 50, 8, 150, 15);
                        } else {
                            textInfo.setBounds(progress1.getLocation().x, 8, 100, 15);
                        }


                        //progressing.setSize(getPercentage(80), 7);
                        int to = emptyBar.getLocation().x + ((int) emptyBar.getSize().getWidth()) + 20;
                        int tre = emptyBar.getLocation().x - 40;
                        totalTimet.setLocation(to, totalTimet.getLocation().y);
                        currentTime.setLocation(tre, currentTime.getLocation().y);
                        
                    }
                });
                
                thread.start();
                
                
                
                
            }
            
            @Override
            public void componentMoved(ComponentEvent e) {
            }
            
            @Override
            public void componentShown(ComponentEvent e) {
            }
            
            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }
    
    public int getPercentage(int prosent) {
        return (getSize().width * prosent) / 100;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(53, 53, 53));
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204)));

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/newPlay.png"))); // NOI18N
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.setPreferredSize(new java.awt.Dimension(35, 35));
        playButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/newPlayOver.png"))); // NOI18N
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playButtonMouseExited(evt);
            }
        });
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(620, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        
        check();
    }//GEN-LAST:event_playButtonActionPerformed
    
    private void playButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_playButtonMouseEntered
    
    private void playButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playButtonMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_playButtonMouseExited
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton playButton;
    // End of variables declaration//GEN-END:variables

    public void check() {
        System.gc();
        if (hasSong) {
            
            if (play) {
                textInfo.setForeground(new Color(51, 51, 51));
                if (songNameAni.isRunning()) {
                    songNameAni.stop();
                }
                if (animation.isRunning()) {
                    animation.stop();
                }
                
                musicPlayer.pause();
                play = false;
                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/spinna.png"))); // NOI18N
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/newPlay.png"))); // NOI18N
                playButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/newPlayOver.png"))); // NOI18N
            } else {
                textInfo.setForeground(Style.getSuccessColor());
                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/spinna.png"))); // NOI18N
                pl = true;
                songNameAni.start();
                animation.start();
                //textInfo.setText("Mabvuto |");
                musicPlayer.play();


                //System.out.println("hei");
                play = true;
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/pause.png"))); // NOI18N
                playButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/pauseOver.png"))); // NOI18N

                checkSong();
                
                
            }
        } else {
            textInfo.setForeground(Style.getErrorColor());
            textInfo.setText("Choose a song to play");
        }
        
    }
    
    public void checkSong() {
        test = System.currentTimeMillis();
        System.gc();
        thread = new Thread(new Runnable() {
            public void run() {
                while (play) {
                    
                    System.gc();
                    
                    
                    try {
                        //System.out.println(doProsent(musicPlayer.getProsent(musicPlayer.calc())));

                        currentTime.setText(musicPlayer.getTime());
                        progressing.setSize(doProsent(musicPlayer.getProsent(musicPlayer.calc())) - 1, 7);
                        jLabel2.setLocation(FUDGE_FACTOR + doProsent(musicPlayer.getProsent(musicPlayer.calc()) - 2), jLabel2.getLocation().y);
                        
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    //System.out.println(musicPlayer.getAudioStream());
                    if (musicPlayer.getAudioStream() == 0) {
                        
                        try {
                            //play = false;
                            currentTime.setText("0:00");
                            progressing.setSize(0, 7);
                            jLabel2.setLocation(110, 26);
                            
                            musicPlayer.initSong();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        check();
                        //System.out.println("Song finished play");
                    }
                    try {
                        // Thread.sleep(1000 / 1);  // milliseconds
                        //thread2.sleep(1000 / 1);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                    }
                }
                
            }
        });
        
        thread.start();
        
    }
    
    public boolean isPlay() {
        return play;
    }
    
    public int doProsent(int prosent) {
        int origin = progress1.getSize().width;
        int calc = origin * prosent / 100;
        return calc;
        
    }
    
    public int prosentOf(int sekunder) {
        float ja = progress1.getSize().width;
        int jas = (int) Math.ceil(sekunder / ja * 100);
        //System.out.println("KAS: " + jas);
        return (int) jas;
    }
    
    private void emptyBarMouseClicked(java.awt.event.MouseEvent evt) {
        
        if (hasSong) {
            
            jLabel2.setLocation(FUDGE_FACTOR + emptyBar.getMousePosition().x, jLabel2.getLocation().y);
            progressing.setSize(emptyBar.getMousePosition().x, progressing.getSize().height);
            Thread threada = new Thread(new Runnable() {
                public void run() {
                    System.gc();
                    
                    try {
                        try {
                            musicPlayer.jumpToSeconds(returnBytesToSkip());
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            });
            threada.start();
            
            
            System.gc();
        }
    }
    
    public int returnBytesToSkip() throws UnsupportedAudioFileException, IOException {
        return musicPlayer.test23(musicPlayer.getSeconds(prosentOf(emptyBar.getMousePosition().x)));
        
    }
    
    public boolean isHasSong() {
        return hasSong;
    }
}
