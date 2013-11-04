/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Entities.Album;
import Entities.Artist;
import Entities.Track;
import Info.Dictionary;
import database.DBConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Simen
 */
public class Songhandler {
    
    static Track track;
    DBConnection database;
    static HashMap<String, JLabel> tags;
    static HashMap<String, String> values;
    static HashMap<String, String> oldValues;
    HashMap<String, String> metaUp;
    HashMap<String, String> trackUp;
    HashMap<String, String> albumUp;
    HashMap<String, String> artistUp;
    HashMap<String, String> readyForUpdate;
    HashMap<String, String> readyForInsert;
    HashMap<String, String> AAS;
    ArrayList<Integer> newArtists;
    ArrayList<Integer> newAlbums;
    File picture;
    int pictureID;
    
    public Songhandler() throws SQLException {
        if (track == null) {
            track = new Track();
        }
        AAS = new HashMap<>();
        metaUp = new HashMap<>();
        trackUp = new HashMap<>();
        albumUp = new HashMap<>();
        artistUp = new HashMap<>();
        database = new DBConnection();
        readyForUpdate = new HashMap<>();
        readyForInsert = new HashMap<>();
        newArtists = new ArrayList<>();
        newAlbums = new ArrayList<>();
        values = new HashMap<>();
        
        if (oldValues == null) {
            oldValues = new HashMap<>();
        }
        
    }
    
    public void loadSongInfo(int id) throws FileNotFoundException, SQLException, IOException {
        findSong(id);
        findMetadata();
        findArtists();
        findAlbums();
        findImage();
       
    }
    
    public void findImage() throws FileNotFoundException, SQLException, IOException {
        System.out.println(track.getId() + " " + track.getArtists().get(0).getId() + " " + track.getAlbums().get(0).getId());
        track.setPic(database.hentFil(track.getId(), track.getArtists().get(0).getId(), track.getAlbums().get(0).getId()));
        System.out.println("NASSS");
    }
    
    public void getTopInfo() {
        if (tags != null) {
            getTopInfo(tags);
            setImage();
        }
    }
    
    private void findSong(int id) {
        track = database.getSong(id);
    }
    
    private void findMetadata() {
        //findSONG må være kalt først
        track.setMetadata(database.getMetadata(track.getMetaId()));
        if (track.getMetadata() == null) {
        }
        
        
    }
    
    private void findArtists() {
        track.setArtists(database.getSongArtists(track.getId()));
    }
    
    private void findAlbums() {
        track.setAlbums(database.getSongAlbums(track.getId()));
        track.setArtistsAndAlbums();
        track.fillHashMap();
        oldValues = track.getValues();
    }
    
    public void saveSong() throws SQLException, FileNotFoundException {
        newArtists = new ArrayList<>();
        newAlbums = new ArrayList<>();
        if (Dictionary.getDictionaryString() == null) {
            Dictionary dic = new Dictionary();
        }
        int idSong = 0;
        createInsertQueue();
        /**
         * System.out.println("----------------"); for (String s :
         * readyForInsert.keySet()) { System.out.println(s + " " +
         * readyForInsert.get(s)); } System.out.println("----------------");
         *
         */
        whereToInsert();
        
        database.insert("METADATA", metaUp);
        
        trackUp.put("idMETADATA", database.getLastID() + "");
        
        database.insert("SONG", trackUp);
        idSong = database.getLastID();
        //AAS.put("idSONG", database.getLastID() + "");
        albumUpdater(idSong);
        
    }
    
    private void albumUpdater(int idSong) throws SQLException, FileNotFoundException {
        System.out.println(track.getAlbums().size() + " STORRELSE");
        for (Album a : track.getAlbums()) {
            System.out.println("Simen " + a.getIALB() + " " + a.getId());
            HashMap<String, String> temp = new HashMap<>();
            temp.put("IALB", a.getIALB());
            if (a.getId() > 0) {
                //update
                database.update("ALBUM", "idALBUM", a.getId(), temp);
            } else {
                int exists = database.albumExists(a.getIALB());
                if (exists != 0) {
                    //album is already in database
                    database.update("ALBUM", "idALBUM", exists, temp);
                    newAlbums.add(exists);
                } else {
                    System.out.println("HER");
                    database.insert("ALBUM", temp);
                    newAlbums.add(database.getLastID());
                }
                
            }
            
        }
        
        for (Artist a : track.getArtists()) {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("IART", a.getIART());
            temp.put("IPLA", a.getIPLA());
            temp.put("ICON", a.getICON());
            temp.put("ILAN", a.getILAN());
            if (a.getId() > 0) {
                //update
                database.update("ARTIST", "idARTIST", a.getId(), temp);
            } else {
                int exists = database.artistExists(a.getIART());
                if (exists != 0) {
                    //artisten er allerede i databasen
                    database.update("ARTIST", "idARTIST", exists, temp);
                    newArtists.add(exists);
                } else {
                    database.insert("ARTIST", temp);
                    newArtists.add(database.getLastID());
                }
                
            }
            
        }
        if (picture != null) {
            pictureID = insertFile(picture);
        }
        
        for (int i : newAlbums) {
            for (int j : newArtists) {
                HashMap<String, String> temp = new HashMap<>();
                temp.put("idARTIST", j + "");
                temp.put("idALBUM", i + "");
                temp.put("idSONG", idSong + "");
                if (pictureID != 0) {
                    temp.put("idPIC", pictureID + "");
                }
                database.insert("AAS", temp);
            }
            
        }
        
        if (newAlbums.isEmpty() && !newArtists.isEmpty()) {
            for (Album a : track.getAlbums()) {
                
                for (int j : newArtists) {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put("idARTIST", j + "");
                    temp.put("idALBUM", a.getId() + "");
                    temp.put("idSONG", idSong + "");
                    if (pictureID != 0) {
                        temp.put("idPIC", pictureID + "");
                    }
                    database.insert("AAS", temp);
                }
                
            }
            
        }
        
        if (!newAlbums.isEmpty() && newArtists.isEmpty()) {
            for (Artist a : track.getArtists()) {
                
                for (int j : newAlbums) {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put("idARTIST", a.getId() + "");
                    temp.put("idALBUM", j + "");
                    temp.put("idSONG", idSong + "");
                    if (pictureID != 0) {
                        temp.put("idPIC", pictureID + "");
                    }
                    database.insert("AAS", temp);
                }
                
            }
        }
        if (pictureID != 0) {
            check();
        }
        
    }
    
    public void check() throws SQLException {
        for (Album a : track.getAlbums()) {
            System.out.println("--------------------");
            for (Artist as : track.getArtists()) {

                //if (database.checkImage(track.getId(), as.getId(), a.getId()) == 0) {
                database.updatePIC(track.getId(), as.getId(), a.getId(), pictureID);
                //}
            }
            
        }
        
    }
    
    public void deleteSong() {
        database.deleteSong(track.getId(), "AAS", "idSONG");
        database.deleteSong(track.getId(), "SONG", "idSONG");
        
    }
    
    public void deleteWhat(String type, int id) {
        database.deleteSong2(id, "AAS", type, "idSONG", track.getId());
        
    }
    
    public void createUpdateQueue() {
        
        for (String meta : values.keySet()) {
            if (values.get(meta) != null) {

                //System.out.println(values.get(meta) + " " + (oldValues.get(meta)));
                if (values.get(meta).equals(oldValues.get(meta))) {
                    //luker bort ting som ikke er endret
                } else {
                    String temp = Dictionary.getDictionaryString().get(meta).name;
                    
                    if (temp.equals(values.get(meta))) {
                    } else {
                        if (values.get(meta) != null) {
                            readyForUpdate.put(meta, values.get(meta));
                        }
                    }
                }
            }
        }
    }
    
    public void whereToUpdate() {
        for (String tag : readyForUpdate.keySet()) {
            check(tag, readyForUpdate.get(tag));
        }
        
    }
    
    public void whereToInsert() {
        for (String tag : readyForInsert.keySet()) {
            check(tag, readyForInsert.get(tag));
        }
        
    }
    
    public void createInsertQueue() {
        if (readyForInsert == null) {
            readyForInsert = new HashMap<>();
            
        }
        for (String meta : values.keySet()) {
            if (values.get(meta) != null) {
                
                String temp = Dictionary.getDictionaryString().get(meta).name;
                
                if (!temp.equals(values.get(meta))) {
                    
                    if (values.get(meta) != null) {
                        readyForInsert.put(meta, values.get(meta));
                    }
                }
                
                
            }
        }
    }
    
    private void check(String tag, String data) {
        switch (tag) {
            case "IART":
                artistUp.put(tag, data);
                break;
            case "INAM":
                trackUp.put(tag, data);
                break;
            case "IGNR":
                trackUp.put(tag, data);
                break;
            case "IKEY":
                trackUp.put(tag, data);
                break;
            case "ISRF":
                metaUp.put(tag, data);
                break;
            case "IMED":
                metaUp.put(tag, data);
                break;
            case "IENG":
                metaUp.put(tag, data);
                break;
            case "ITCH":
                metaUp.put(tag, data);
                break;
            case "ISRC":
                metaUp.put(tag, data);
                break;
            case "ICOP":
                metaUp.put(tag, data);
                break;
            case "ISFT":
                metaUp.put(tag, data);
                break;
            case "ICRD":
                metaUp.put(tag, data);
                break;
            case "IALB":
                albumUp.put(tag, data);
                break;
            case "ILAN":
                artistUp.put(tag, data);
                break;
            case "ICMT":
                metaUp.put(tag, data);
                break;
            case "ISBJ":
                metaUp.put(tag, data);
                break;
            case "ISON":
                trackUp.put(tag, data);
                break;
            case "ILEN":
                trackUp.put(tag, data);
                break;
            case "ICON":
                artistUp.put(tag, data);
                break;
            case "ICOM":
                trackUp.put(tag, data);
                break;
            case "ILYR":
                trackUp.put(tag, data);
                break;
            case "IPLA":
                artistUp.put(tag, data);
                break;
            case "IREG":
                trackUp.put(tag, data);
                break;
            case "IDIS":
                trackUp.put(tag, data);
                break;
            case "IVIL":
                trackUp.put(tag, data);
                break;
            case "IPEO":
                trackUp.put(tag, data);
                break;
            case "IDIG":
                metaUp.put(tag, data);
                break;
            
        }
    }
    
    public void updateSong() throws SQLException, FileNotFoundException {
        newArtists = new ArrayList<>();
        newAlbums = new ArrayList<>();
        createUpdateQueue();
        whereToUpdate();
        if (readyForUpdate.isEmpty()) {
            //ikke oppdater
        } else {
            if (!trackUp.isEmpty()) {
                database.update("SONG", "idSONG", track.getId(), trackUp);
            }
            if (!metaUp.isEmpty()) {
                database.update("METADATA", "idMETADATA", track.getMetaId(), metaUp);
            }
            
            
        }
        albumUpdater(track.getId());
    }
    
    public void updateField(String tag, String data) {
        //System.out.println(values.size());
        values.put(tag, data);
    }
    
    public Track getTrack() {
        return track;
    }
    
    public void setTrack(Track track) {
        this.track = track;
    }
    
    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
        Songhandler s = new Songhandler();
        s.loadSongInfo(1);
        System.out.println(s.getTrack().getIGNR() + s.getTrack().getAlbums().size());
        
    }
    
    public void getTopInfo(HashMap<String, JLabel> tags) {
        for (String s : tags.keySet()) {
            tags.get(s).setText(track.getValues().get(s));
        }
        this.tags = tags;
    }
    
    public void setImage() {
        if (!tags.isEmpty()) {
            
            tags.get("PIC").setIcon(track.getPic().getBilde());
        }
        
    }
    
    public int insertFile(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        database.insertImage(fis, file, "200x200");
        int id = database.getLastID();
        System.out.println(id);
        return id;
    }
    
    public void setFile(File file) {
        picture = file;
    }
    
    public void getAlbums(Artist artist) {
        artist.setAlbums(database.findAnArtistAlbums(artist.getId()));
    }
    
    public DefaultTableModel getTracks(int id){
        return database.getTracks(id);
    
    }
}
