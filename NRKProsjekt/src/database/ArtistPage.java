/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;

/**
 *
 * @author Simen
 */
public class ArtistPage {

    public static int artistID;
    public static int songID;
    ArrayList<Album2> albums;
    DBConnection db;

    public ArtistPage() {
        albums = new ArrayList<>();
        db = new DBConnection();
    }

    public void addAlbum(ArrayList<Album2> a) {
        albums = a;
    }
}
