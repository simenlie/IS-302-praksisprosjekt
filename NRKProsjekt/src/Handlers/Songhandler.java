/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Entities.Track;
import Info.Dictionary;
import database.DBConnection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JLabel;

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

    public Songhandler() {
        if (track == null) {
            track = new Track();
        }

        metaUp = new HashMap<>();
        trackUp = new HashMap<>();
        albumUp = new HashMap<>();
        artistUp = new HashMap<>();
        database = new DBConnection();
        readyForUpdate = new HashMap<>();
        values = new HashMap<>();
        if (oldValues == null) {
            oldValues = new HashMap<>();
        }

    }

    public void loadSongInfo(int id) {
        findSong(id);
        findMetadata();
        findArtists();
        findAlbums();

    }

    public void getTopInfo() {
        if (tags != null) {
            getTopInfo(tags);
        }
    }

    private void findSong(int id) {
        track = database.getSong(id);
    }

    private void findMetadata() {
        //findSONG må være kalt først
        track.setMetadata(database.getMetadata(track.getMetaId()));
        if (track.getMetadata() == null) {
            System.out.println("hallo");
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
        System.out.println("Setter old" + oldValues.size());

    }

    public void saveSong() {
        //Save all the data
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

    public void updateSong() throws SQLException {
        createUpdateQueue();
        whereToUpdate();
        System.out.println(readyForUpdate.size());
        System.out.println(trackUp.size());
        if (readyForUpdate.isEmpty()) {
            //ikke oppdater
        } else {
            if (!trackUp.isEmpty()) {
                database.update("SONG", "idSONG", track.getId(), trackUp);
            }
            if (!metaUp.isEmpty()) {
                database.update("METADATA", "idMETADATA", track.getMetaId(), metaUp);
            }
            if (!artistUp.isEmpty()) {
                database.update("ARTIST", "idARTIST", track.getArtists().get(0).getId(), artistUp);
            }
            if (!albumUp.isEmpty()) {
                database.update("ALBUM", "idALBUM", track.getAlbums().get(0).getId(), albumUp);
            }


        }

    }

    public void updateField(String tag, String data) {
        values.put(tag, data);
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public static void main(String[] args) {
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
}
