/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Simen
 */
public class Insert {

    HashMap<String, Component> data;
    int metaId, artistId, albumId, songId;

    public Insert(HashMap<String, Component> data) {
        this.data = data;

    }

    public String getMetadataQuery() {
        //WORKING
        String query = "INSERT INTO METADATA(ICMT,ICOP, ICRD, IENG, IMED, ISBJ, ISFT, ISRC, ISRF, ITCH) ";
        String[] newData = {ga("ICMT", 0), gt("ICOP", 0), gt("ICRD", 0), gt("IENG", 0), gt("IMED", 0), gt("ISBJ", 0), gt("ISFT", 0), gt("ISRC", 0), gt("ISRF", 0), gt("ITCH", 1)};
        String values = queryValueMaker(newData);
        String result = query + values;
        //metaId = primary key
        return result;
    }

    public String getSongQuery(String metaId) {
        //WORKING NB: ILEN ikke initialisert i GUI, ISONG blir BLOB etterhvert
        String query = "INSERT INTO SONG(idMETADATA, IGNR, IKEY, ILEN, INAM, ISON) ";
        String[] newData = {metaId, gt("IGNR", 0), gt("IKEY", 0), gt("ILEN", 0), gt("INAM", 0), gt("ISON", 1)};
        String values = queryValueMaker(newData);
        String result = query + values;
        //songId = songID
        return result;
    }

    public String getAlbumQuery() {
        //WORKING
        String query = "INSERT INTO ALBUM(IALB) ";
        String[] newData = {gt("IALB", 1)};
        String values = queryValueMaker(newData);
        String result = query + values;
        //albumId = albumID
        return result;

    }

    public String getArtistQuery() {
        //WORKING. NB:ICON not initialised in MetaEdit
        String query = "INSERT INTO ARTIST(IART,ICON,ILAN) ";
        String[] newData = {gt("IART", 0), gt("ICON", 0), gt("ILAN", 1)};
        String values = queryValueMaker(newData);
        String result = query + values;
        //artistId = aritisID
        return result;
    }

    public void getAASQuery(int albumId, int artistId, int songId) {
        //WORKING
        String query = "INSERT INTO AAS(idALBUM, idARTIST,idSONG) ";
        String values = "VALUES(" + albumId + "," + artistId + "," + songId + ")";
        String result = query + values;
        //artistId = aritisID
        System.out.println(result);
    }

    //Calculators and helpers
    
    public String gt(String tag, int last) {
        JTextField temp = (JTextField) data.get(tag);
        if (last == 0) {
            return "'" + temp.getText() + "',";
        } else {
            return "'" + temp.getText() + "'";
        }

    }

    public String ga(String tag, int last) {
        JTextArea temp = (JTextArea) data.get(tag);
        if (last == 0) {
            return "'" + temp.getText() + "',";
        } else {
            return "'" + temp.getText() + "'";
        }
    }

    public String queryValueMaker(String[] values) {
        String beginning = "VALUES(";
        for (String value : values) {
            beginning += value;
        }
        String end = ")";
        beginning += end;
        return beginning;
    }
}
