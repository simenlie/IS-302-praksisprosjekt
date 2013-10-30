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
public class Artist {

    ArrayList<Album> albums;
    int id;
    String ILAN, ICON, IART, IPLA;
  

    public Artist(int id, String ILAN, String ICON, String IART, String IPLA) {
        this.id = id;
        this.ILAN = ILAN;
        this.ICON = ICON;
        this.IART = IART;
        this.IPLA = IPLA;
  
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public String getILAN() {
        return ILAN;
    }

    public void setILAN(String ILAN) {
        this.ILAN = ILAN;
    }

    public String getICON() {
        return ICON;
    }

    public void setICON(String ICON) {
        this.ICON = ICON;
    }

    public String getIART() {
        return IART;
    }

    public void setIART(String IART) {
        this.IART = IART;
    }

    public String getIPLA() {
        return IPLA;
    }

    public void setIPLA(String IPLA) {
        this.IPLA = IPLA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
