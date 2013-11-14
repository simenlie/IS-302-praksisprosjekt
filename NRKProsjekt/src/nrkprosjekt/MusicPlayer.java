package nrkprosjekt;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

/**
 *
 * @author Simen
 */
public class MusicPlayer {

    File file;
    AudioPlayer player;
    AudioDataStream audioStream;
    boolean playing;
    FileInputStream in;
    int size;
    byte[] byteArray;
    AudioData audiodata;

    /**
     * @param args the command line arguments
     */
    public MusicPlayer(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        file = new File(path);
        player = AudioPlayer.player;
        initSong();
    }

    public MusicPlayer() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        player = AudioPlayer.player;
        initSong();
    }

    public void setSongFile(String path) {
        file = new File(path);

    }

    public int getAudioStream() {
        return audioStream.available();

    }

    public String getInfoSong() {
        return file.getName();

    }

    public void playSong() throws FileNotFoundException, IOException {
        FileInputStream in = new FileInputStream(file);
        int size = (int) file.length();
        byte[] byteArray = new byte[size];
        in.read(byteArray);


        AudioData audiodata = new AudioData(byteArray);

        audioStream = new AudioDataStream(audiodata);
        System.out.println(audioStream.available());
        //audioStream.skip(9000000);
        player.player.start(audioStream);



    }

    public void initSong() throws FileNotFoundException, IOException {
        if (in == null) {
            in = new FileInputStream(file);
            size = (int) file.length();
            byteArray = new byte[size];
            in.read(byteArray);
            audiodata = new AudioData(byteArray);
        }
        
        System.gc();
        audioStream = new AudioDataStream(audiodata);


    }

    public void play() {
        playing = true;
        player.player.start(audioStream);
    }

    public boolean isAlive() {
        return player.isAlive();
    }

    public void pause() {
        playing = false;
        player.player.stop(audioStream);
    }

    public void jumpToSeconds(int sec) throws FileNotFoundException, IOException {
        if (player.isAlive()) {
            player.stop(audioStream);
            player = null;
            audioStream = null;

            System.gc();
            player = AudioPlayer.player;

            //System.out.println("JA lever");
        }
        System.gc();
        initSong();
        audioStream.skip(sec);

        if (playing) {
            player.player.start(audioStream);
        }
        System.gc();



    }

    public int test23(int prosent) throws UnsupportedAudioFileException, IOException {
        //System.out.println("ANNET");

        //System.out.println("Fil lengde: " + file.length());
        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file);
        AudioFormat format2 = audioInputStream2.getFormat();
        long audioFileLength = file.length();
        int frameSize = format2.getFrameSize();
        float frameRate = format2.getFrameRate();
        float durationInSeconds2 = (audioFileLength / (frameSize * frameRate));
        float durMinutes = durationInSeconds2 / 60;
        float bytesPerSec = file.length() / durationInSeconds2;
        //System.out.println("Sekunder: " + durationInSeconds2 + " Minutter : " + durMinutes + " bytePerSek : " + bytesPerSec);
        float prosentRegn = (prosent * durationInSeconds2) / 100;
        int na = (int) prosentRegn * (int) bytesPerSec;
        int nas = (int) (prosent * bytesPerSec);


        //System.out.println(prosentRegn + " NAS: " + nas);

        //returnerer bytes på disse sekunder
        return nas;

    }

    public float getSongSeconds() throws UnsupportedAudioFileException, IOException {
        //System.out.println("ANNET");

        //System.out.println("Fil lengde: " + file.length());
        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file);
        AudioFormat format2 = audioInputStream2.getFormat();
        long audioFileLength = file.length();
        int frameSize = format2.getFrameSize();
        float frameRate = format2.getFrameRate();
        float durationInSeconds2 = (audioFileLength / (frameSize * frameRate));


        //System.out.println(prosentRegn + " NAS: " + nas);

        //returnerer bytes på disse sekunder
        return durationInSeconds2;

    }

    public int getProsent(int prosent) throws UnsupportedAudioFileException, IOException {
        //System.out.println("PROSENT");
        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file);
        AudioFormat format2 = audioInputStream2.getFormat();
        long audioFileLength = file.length();
        int frameSize = format2.getFrameSize();
        float frameRate = format2.getFrameRate();
        float durationInSeconds2 = (audioFileLength / (frameSize * frameRate));
        float durMinutes = durationInSeconds2 / 60;
        float bytesPerSec = file.length() / durationInSeconds2;
        float prosentRegn = (prosent * durationInSeconds2) / 100;
        int prosentRegn2 = (int) Math.ceil(prosent / durationInSeconds2 * 100);
        int na = (int) prosentRegn * (int) bytesPerSec;
        //System.out.println("Sekunder: " + prosentRegn2 + " Minutter : " + durMinutes + " bytePerSek : " + bytesPerSec);

        //returnerer prosent (putter man inn 35, vil man få ut 10)
        return prosentRegn2;

    }

    public float getBytesPerSec() throws UnsupportedAudioFileException, IOException {
        //System.out.println("PROSENT");
        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file);
        AudioFormat format2 = audioInputStream2.getFormat();
        long audioFileLength = file.length();
        int frameSize = format2.getFrameSize();
        float frameRate = format2.getFrameRate();
        float durationInSeconds2 = (audioFileLength / (frameSize * frameRate));
        float durMinutes = durationInSeconds2 / 60;
        float bytesPerSec = file.length() / durationInSeconds2;

        return bytesPerSec;

    }

    public int calc() throws UnsupportedAudioFileException, IOException {
        return (int) getSongSeconds() - (int) (audioStream.available() / getBytesPerSec());


    }
    //returnerer sekunder av prosenten int prosent = prosenten, return = sekunder

    public int getSeconds(int prosent) throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file);
        AudioFormat format2 = audioInputStream2.getFormat();
        long audioFileLength = file.length();
        int frameSize = format2.getFrameSize();
        float frameRate = format2.getFrameRate();
        float durationInSeconds2 = (audioFileLength / (frameSize * frameRate));
        int prosentRegn2 = (int) Math.ceil(durationInSeconds2 * prosent / 100);
        return prosentRegn2;
    }

    public String getTime() throws UnsupportedAudioFileException, IOException {
        String time = calc() + "";
        BigDecimal sec = new BigDecimal(time);


        long longVal = sec.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        if (secs < 10) {
            return mins + ":0" + secs;
        }
        return mins + ":" + secs;

    }

    public String getTotalTime() throws UnsupportedAudioFileException, IOException {
        String time = getSongSeconds() + "";
        BigDecimal sec = new BigDecimal(time);


        long longVal = sec.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        if (secs < 10) {
            return mins + ":0" + secs;
        }
        return mins + ":" + secs;

    }
}
