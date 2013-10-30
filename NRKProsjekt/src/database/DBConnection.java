/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Entities.Album;
import Entities.Artist;
import Entities.Metadata;
import Entities.Track;
import Info.Tags;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import nrkprosjekt.TableModell;

/**
 *
 * @author Simen
 */
public class DBConnection {

    private Connection connection;
    private Statement statement;
    HashMap<String, String> artist;
    ArrayList<Album2> albumss;

    public DBConnection() {
        artist = new HashMap<>();
        albumss = new ArrayList<>();
        load();
    }

    public HashMap<String, String> getArtistMap(int id) {
        getArtist(id);
        return artist;
    }

    public void load() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(DBSettings.connectionURL + DBSettings.database, DBSettings.database, DBSettings.password);
            statement = connection.createStatement();
        } catch (Exception e) {
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

        DefaultTableModel dm = new TableModell();
        dm.addColumn("");
        try {
            ResultSet rs = null;
            //String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
            String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ICRD from SONG, METADATA, AAS, ALBUM, ARTIST";
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
                // dm.addRow(new Object[]{"", ""});
                teller++;
            }
            System.out.println(dm.getColumnCount());


        } catch (Exception e) {
            System.out.println(e);
        }
        return dm;
    }

    public ArrayList<Album2> getAlbum() {
        return albumss;
    }

    public Track getSong(int id) {
        String query = "select * from SONG SONG where idSONG =" + id + ";";
        Track track = new Track();
        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                track.setId(rs.getInt("idSONG"));
                track.setMetaId(rs.getInt("idMETADATA"));
                track.setICOM(rs.getString(Tags.ICOM.toString()));
                track.setIDIS(rs.getString(Tags.IDIS.toString()));
                track.setIGNR(rs.getString(Tags.IGNR.toString()));
                track.setIKEY(rs.getString(Tags.IKEY.toString()));
                track.setILEN(rs.getString(Tags.ILEN.toString()));
                track.setILYR(rs.getString(Tags.ILYR.toString()));
                track.setINAM(rs.getString(Tags.INAM.toString()));
                track.setIPEO(rs.getString(Tags.IPEO.toString()));
                track.setIREG(rs.getString(Tags.IREG.toString()));
                track.setISON(rs.getString(Tags.ISON.toString()));
                track.setIVIL(rs.getString(Tags.IVIL.toString()));
            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return track;
    }

    public Metadata getMetadata(int metaId) {
        String query = "select * from METADATA where idMETADATA = " + metaId + ";";
        Metadata metadata = new Metadata();
        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                metadata.setId(rs.getInt("idMETADATA"));
                metadata.setICMT(rs.getString(Tags.ICMT.toString()));
                metadata.setICOP(rs.getString(Tags.ICOP.toString()));
                metadata.setICRD(rs.getString(Tags.ICRD.toString()));
                metadata.setIDIG(rs.getString(Tags.IDIG.toString()));
                metadata.setIENG(rs.getString(Tags.IENG.toString()));
                metadata.setIMED(rs.getString(Tags.IMED.toString()));
                metadata.setISBJ(rs.getString(Tags.ISBJ.toString()));
                metadata.setISFT(rs.getString(Tags.ISFT.toString()));
                metadata.setISRC(rs.getString(Tags.ISRC.toString()));
                metadata.setISRF(rs.getString(Tags.ISRF.toString()));
                metadata.setITCH(rs.getString(Tags.ITCH.toString()));
            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return metadata;
    }

    public ArrayList<Album> getAlbum(int id) {
        String query = "select IALB, ALBUM.idALBUM from ALBUM, AAS, ARTIST where ALBUM.idALBUM = AAS.idALBUM and ARTIST.idARTIST = AAS.idARTIST AND ARTIST.idARTIST = " + id + " group by ALBUM.idALBUM;";
        ArrayList<Album> album = new ArrayList<>();

        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                album.add(new Album(rs.getInt("idALBUM"), rs.getString("IALB")));
            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return album;
    }

    public ArrayList<Track> getTracksInAlbum(int id) {

        String query = "select * from SONG,AAS,ALBUM where SONG.idSONG = AAS.idSONG and ALBUM.idALBUM = AAS.idALBUM and ALBUM.idALBUM =" + id + ";";
        ArrayList<Track> tracks = new ArrayList<>();

        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Track track = new Track();
                track.setId(rs.getInt("idSONG"));
                track.setMetaId(rs.getInt("idMETADATA"));
                track.setICOM(rs.getString(Tags.ICOM.toString()));
                track.setIDIS(rs.getString(Tags.IDIS.toString()));
                track.setIGNR(rs.getString(Tags.IGNR.toString()));
                track.setIKEY(rs.getString(Tags.IKEY.toString()));
                track.setILEN(rs.getString(Tags.ILEN.toString()));
                track.setILYR(rs.getString(Tags.ILYR.toString()));
                track.setINAM(rs.getString(Tags.INAM.toString()));
                track.setIPEO(rs.getString(Tags.IPEO.toString()));
                track.setIREG(rs.getString(Tags.IREG.toString()));
                track.setISON(rs.getString(Tags.ISON.toString()));
                track.setIVIL(rs.getString(Tags.IVIL.toString()));
                tracks.add(track);
            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return tracks;
    }

    public ArrayList<Album> getSongAlbums(int id) {

        String query = "select IALB, ALBUM.idALBUM from ALBUM, AAS, SONG where ALBUM.idALBUM = AAS.idALBUM and SONG.idSONG = AAS.idSONG AND SONG.idSONG = " + id + " group by ALBUM.idALBUM;";

        ArrayList<Album> album = new ArrayList<>();
        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                album.add(new Album(rs.getInt("idALBUM"), rs.getString("IALB")));
            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return album;
    }

    public ArrayList<Artist> getSongArtists(int id) {


        String query = "select ARTIST.idARTIST,IART, ICON, ILAN, IPLA from ARTIST, AAS, SONG where ARTIST.idARTIST = AAS.idARTIST and SONG.idSONG = AAS.idSONG AND SONG.idSONG = " + id + " group by ARTIST.idARTIST;";

        ArrayList<Artist> artista = new ArrayList<>();
        try {

            ResultSet rs = null;
            rs = statement.executeQuery(query);


            while (rs.next()) {
                artista.add(new Artist(rs.getInt("idARTIST"), rs.getString("ILAN"), rs.getString("ICON"), rs.getString("IART"), rs.getString("IPLA")));
            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return artista;
    }

    public ArrayList<Track> getTableNew() {
        ArrayList<Track> tracks = new ArrayList<>();

        try {
            ResultSet rs = null;
            //String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
            String selectQ = "select * from SONG, METADATA, AAS, ALBUM, ARTIST";
            String QueryString = selectQ + " WHERE SONG.idSONG = AAS.idSONG AND METADATA.idMETADATA = SONG.idMETADATA AND ALBUM.idALBUM = AAS.idALBUM AND ARTIST.idARTIST = AAS.idARTIST GROUP BY SONG.idSONG";
            rs = statement.executeQuery(QueryString);


            while (rs.next()) {
                Track track = new Track();
                track.setId(rs.getInt("idSONG"));
                track.setMetaId(rs.getInt("idMETADATA"));
                track.setICOM(rs.getString(Tags.ICOM.toString()));
                track.setIDIS(rs.getString(Tags.IDIS.toString()));
                track.setIGNR(rs.getString(Tags.IGNR.toString()));
                track.setIKEY(rs.getString(Tags.IKEY.toString()));
                track.setILEN(rs.getString(Tags.ILEN.toString()));
                track.setILYR(rs.getString(Tags.ILYR.toString()));
                track.setINAM(rs.getString(Tags.INAM.toString()));
                track.setIPEO(rs.getString(Tags.IPEO.toString()));
                track.setIREG(rs.getString(Tags.IREG.toString()));
                track.setISON(rs.getString(Tags.ISON.toString()));
                track.setIVIL(rs.getString(Tags.IVIL.toString()));
                tracks.add(track);
            }




        } catch (Exception e) {
            System.out.println(e);
        }
        return tracks;
    }

    public void update(String table, String idType, int id, HashMap<String, String> data) throws SQLException {

        String startQ = "update " + table + " set ";
        int index = 0;
        for (String s : data.keySet()) {
            if (index < data.size() - 1) {
                startQ += s + " = " + "'" + data.get(s) + "',";
            } else {
                startQ += s + " = " + "'" + data.get(s) + "'";
            }
            index++;
        }
        startQ += " where " + idType + " = " + id + ";";
        System.out.println(startQ);



        statement = connection.createStatement();

        PreparedStatement pstmt = connection.prepareStatement(startQ);
        pstmt.executeUpdate();





    }
}
