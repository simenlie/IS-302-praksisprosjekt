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
public class Track {

    HashMap<String, String> values;
    static int id;
    static int metaId;
    String INAM, IGNR, IKEY, ISON, ILEN, ICOM, ILYR, IREG, IDIS, IVIL, IPEO;
    ArrayList<Album> albums;
    ArrayList<Artist> artists;
    Metadata metadata;
    String artist;
    String album;

    public Track() {
        values = new HashMap<>();
    

    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public int getMetaId() {
        return metaId;
    }

    public void setArtistsAndAlbums() {
        album = "";
        artist = "";
        int index = 0;
        for (Album a : albums) {
            if (index == albums.size() - 1) {
                album += a.getIALB();
            } else {
                album += a.getIALB() + ",";
            }

            index++;
        }
        int index2 = 0;
        for (Artist a : artists) {
            if (index2 == artists.size() - 1) {
                artist += a.getIART();
            } else {
                artist += a.getIART() + ",";
            }
            index2++;
        }
    }

    public HashMap<String, String> getValues() {

        return values;
    }

    public void fillHashMap() {
        values.put("INAM", INAM);
        values.put("IART", artist);
        values.put("IGNR", IGNR);
        values.put("IKEY", IKEY);
        values.put("ISRF", metadata.ISRF);
        values.put("IMED", metadata.IMED);
        values.put("IENG", metadata.IENG);
        values.put("ITCH", metadata.ITCH);
        values.put("ISRC", metadata.ISRC);
        values.put("ICOP", metadata.ICOP);
        values.put("ISFT", metadata.ISFT);
        values.put("ICRD", metadata.ICRD);
        values.put("IALB", album);
        values.put("ILAN", artists.get(0).ILAN);
        values.put("ICMT", metadata.ICMT);
        values.put("ISBJ", metadata.ISBJ);
        values.put("ILEN", ILEN);
        values.put("ICON", artists.get(0).ICON);

        values.put("ICOM", ICOM);
        values.put("ILYR", ILYR);
        values.put("IPLA", artists.get(0).IPLA);
        values.put("IREG", IREG);
        values.put("IDIS", IDIS);
        values.put("IVIL", IVIL);
        values.put("IPEO", IPEO);
        values.put("IDIG", metadata.IDIG);
        values.put("ISON", ISON);



        //new tags





    }

    public void setMetaId(int metaId) {
        this.metaId = metaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getINAM() {
        return INAM;
    }

    public void setINAM(String INAM) {
        this.INAM = INAM;
    }

    public String getIGNR() {
        return IGNR;
    }

    public void setIGNR(String IGNR) {
        this.IGNR = IGNR;
    }

    public String getIKEY() {
        return IKEY;
    }

    public void setIKEY(String IKEY) {
        this.IKEY = IKEY;
    }

    public String getISON() {
        return ISON;
    }

    public void setISON(String ISON) {
        this.ISON = ISON;
    }

    public String getILEN() {
        return ILEN;
    }

    public void setILEN(String ILEN) {
        this.ILEN = ILEN;
    }

    public String getICOM() {
        return ICOM;
    }

    public void setICOM(String ICOM) {
        this.ICOM = ICOM;
    }

    public String getILYR() {
        return ILYR;
    }

    public void setILYR(String ILYR) {
        this.ILYR = ILYR;
    }

    public String getIREG() {
        return IREG;
    }

    public void setIREG(String IREG) {
        this.IREG = IREG;
    }

    public String getIDIS() {
        return IDIS;
    }

    public void setIDIS(String IDIS) {
        this.IDIS = IDIS;
    }

    public String getIVIL() {
        return IVIL;
    }

    public void setIVIL(String IVIL) {
        this.IVIL = IVIL;
    }

    public String getIPEO() {
        return IPEO;
    }

    public void setIPEO(String IPEO) {
        this.IPEO = IPEO;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
}
