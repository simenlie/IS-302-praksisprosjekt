/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Simen
 */
public class DBConnection {

    private Connection connection;
    private Statement statement;
    ListHandler artists;
    ListHandler albums;
    final int ARTIST_CHOSEN = 0;
    final int ALBUM_CHOSEN = 1;
    HashMap<String, String> artist;
    ArrayList<Album> albumss;

    public DBConnection() {
        artists = new ListHandler();
        albums = new ListHandler();
        artist = new HashMap<>();
        albumss = new ArrayList<>();
        load();
    }

    public HashMap<String, String> getArtistMap(int id) {
        getArtist(id);
        return artist;
    }

    public ListHandler getArtists() {
        return artists;
    }

    public ListHandler getAlbums() {
        return albums;
    }

    public void load() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(DBSettings.connectionURL + DBSettings.database, DBSettings.database, DBSettings.password);
            statement = connection.createStatement();
        } catch (Exception e) {
        }


    }

    public void getManyRelation(int id, int choose) {
        String query;
        String albumQuery = "select IALB, ALBUM.idALBUM from ALBUM, AAS, SONG where ALBUM.idALBUM = AAS.idALBUM and SONG.idSONG = AAS.idSONG AND SONG.idSONG = " + id + " group by ALBUM.idALBUM;";
        String artistQuery = "select ARTIST.idARTIST,IART, ICON, ILAN from ARTIST, AAS, SONG where ARTIST.idARTIST = AAS.idARTIST and SONG.idSONG = AAS.idSONG AND SONG.idSONG = " + id + " group by ARTIST.idARTIST;";
        if (choose == ALBUM_CHOSEN) {
            query = albumQuery;
        } else {
            query = artistQuery;
        }

        try {

            ResultSet rs = null;
            rs = statement.executeQuery(query);


            while (rs.next()) {
                if (choose == ALBUM_CHOSEN) {
                    albums.add(new Artist(rs.getInt("idALBUM"), rs.getString("IALB")));
                    albums.put(rs.getString("IALB"), rs.getInt("idALBUM"));
                } else {
                    artists.add(new Artist(rs.getInt("idARTIST"), rs.getString("IART")));
                    artists.put(rs.getString("IART"), rs.getInt("idARTIST"));

                }

            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
    }

    public void getArtist(int id) {
        String query = "select ARTIST.idARTIST,IART, ICON, ILAN from ARTIST, AAS, SONG where ARTIST.idARTIST = AAS.idARTIST and SONG.idSONG = AAS.idSONG AND ARTIST.idARTIST = " + id + " group by ARTIST.idARTIST;";


        try {

            ResultSet rs = null;
            rs = statement.executeQuery(query);


            while (rs.next()) {
                artist.put("idARTIST", rs.getString("idARTIST"));
                artist.put("IART", rs.getString("IART"));
                artist.put("ICON", rs.getString("ICON"));
                artist.put("ILAN", rs.getString("ILAN"));



            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
    }

    public DefaultTableModel getTable() {

        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("");
        try {
            ResultSet rs = null;
            String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
            String QueryString = selectQ + " WHERE SONG.idSONG = AAS.idSONG AND METADATA.idMETADATA = SONG.idMETADATA AND ALBUM.idALBUM = AAS.idALBUM AND ARTIST.idARTIST = AAS.idARTIST GROUP BY SONG.idSONG";
            rs = statement.executeQuery(QueryString);
            ResultSetMetaData rsmd = rs.getMetaData();
            //Coding to get columns-
            int cols = rsmd.getColumnCount();
            String c[] = new String[cols];
            for (int i = 0; i < cols; i++) {
                c[i] = rsmd.getColumnName(i + 1);
                dm.addColumn(c[i]);
            }
            //get data from rows
            Object row[] = new Object[cols + 1];
            int teller = 1;
            while (rs.next()) {
                for (int i = 1; i < cols + 1; i++) {
                    row[i] = rs.getString(i);
                }
                dm.addRow(row);
                teller++;

            }
            while (teller < 10) {
                dm.addRow(new Object[]{"", ""});
                teller++;
            }
            System.out.println(dm.getColumnCount());


        } catch (Exception e) {
            System.out.println(e);
        }
        return dm;
    }

    public ArrayList<Album> getAlbum() {
        return albumss;
    }

    public void getAlbums(int id) {
        String query = "select IALB, ALBUM.idALBUM from ALBUM, AAS, ARTIST where ALBUM.idALBUM = AAS.idALBUM and ARTIST.idARTIST = AAS.idARTIST AND ARTIST.idARTIST = " + id + " group by ALBUM.idALBUM;";

        try {

            ResultSet rs = null;
            rs = statement.executeQuery(query);


            while (rs.next()) {
                albumss.add(new Album(rs.getInt("idALBUM"), rs.getString("IALB")));
            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
    }
}
