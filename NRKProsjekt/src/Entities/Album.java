/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Simen
 */
public class Album {

    ArrayList<Track> tracks;
    
    int id;
    String IALB;
    
    public Album(int id, String IALB) {
        this.id = id;
        this.IALB = IALB;
        tracks = new ArrayList<>();
    }
    
    public ArrayList<Track> getTracks() {
        return tracks;
    }
    
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
    
    public String getIALB() {
        return IALB;
    }
    
    public void setIALB(String IALB) {
        this.IALB = IALB;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void addTrack(Track track) {
        tracks.add(track);
    }
}
