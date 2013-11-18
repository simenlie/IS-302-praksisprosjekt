/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Entities.Album;
import Entities.Artist;
import Entities.Metadata;
import Entities.Picture;
import Entities.Track;
import Entities.User;
import Info.Path;
import Info.Tag;
import Info.Tags;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
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
    HashMap<String, String> testData;

    public DBConnection() throws SQLException {
        testData = new HashMap<>();
        artist = new HashMap<>();
        albumss = new ArrayList<>();
        load();
        //testData.put("INAM", "Simen");
        //testData.put("IKEY", "Simen, martin");
        //insert("SONG", testData);
        //System.out.println(getLastID());
        getSongAlbums(1);

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

    public User getUser(String username, String password) {
        String query = "select * from users where username = '" + username + "' and user_password = '" + password + "'";
        User u = null;
        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            if (rs.next()) {

                u = new User(rs.getInt("idUSER"), rs.getString("username"), rs.getString("user_password"), rs.getBoolean("admin"));
            }
        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return u;
    }

    public DefaultTableModel getUsers() {

        DefaultTableModel dm = new TableModell();
        dm.addColumn("");
        try {
            ResultSet rs = null;
            String query = "select * from users";
            rs = statement.executeQuery(query);
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

        } catch (Exception e) {
            System.out.println(e);
        }
        return dm;
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

    public int getFirst() throws SQLException {
        String query = "select idSONG FROM SONG  order by idSONG ASC limit 1";
        int id = 0;
        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt("idSONG");
            }


        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return id;
    }

    public void calc(HashMap<String, Tag> tags) {
        System.out.println("------------------");
        String starter = "(";
        int index = 0;
        for (String s : tags.keySet()) {
            if (index == tags.size() - 1) {
                starter += " (" + s + " IS NULL)";
            } else {
                starter += " (" + s + " IS NULL) +";
            }

            index++;
        }
        starter += ") AS sum_null";
        System.out.println(starter);
        System.out.println("------------------");
    }

    public String getLargeString() {
        String du = "((ISBJ IS NULL) + (ICOM IS NULL) + (ICON IS NULL) + (IGNR IS NULL) + (ISFT IS NULL) + (ICOP IS NULL) + (IART IS NULL) + (IVIL IS NULL) + (IDIS IS NULL) + (IENG IS NULL) + (IALB IS NULL) + (IPEO IS NULL) + (IPLA IS NULL) + (ITCH IS NULL) + (IKEY IS NULL) + (ISON IS NULL) + (ILYR IS NULL) + (IREG IS NULL) + (IDIG IS NULL) + (INAM IS NULL) + (ICMT IS NULL) + (ICRD IS NULL) + (ISRF IS NULL) + (ILAN IS NULL) + (ISRC IS NULL) + (ILEN IS NULL) + (IMED IS NULL)) AS sum_null";
        return du;
    }

    public DefaultTableModel getTable() {

        DefaultTableModel dm = new TableModell();
        dm.addColumn("");
        try {
            ResultSet rs = null;
            //String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
            String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ICRD," + getLargeString() + " from SONG, METADATA, AAS, ALBUM, ARTIST";
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

    public void updateUser(int id, String password, String admin, String username, boolean insert) throws SQLException {
        int bo;
        if (admin.equals("All rights")) {
            bo = 0;
        } else {
            bo = 1;
        }
        String query;
        if (insert) {
            query = "insert into users(username,user_password,admin) values('" + username + "','" + password + "'," + bo + ");";
        } else {
            query = "update users set user_password = '" + password + "',admin = '" + bo + "' where idUSER = " + id + ";";
        }
        statement = connection.createStatement();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.executeUpdate();
    }

    public void deleteUser(int id) {
        String query = "delete from users where idUSER = " + id + ";";

        try {
            statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }

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

    public void insert(String table, HashMap<String, String> data) throws SQLException {

        String startQ = "insert into " + table;
        String whatToInsert = " (";
        String values = " (";
        int index = 0;
        for (String s : data.keySet()) {
            if (index < data.size() - 1) {
                whatToInsert += s + ",";
                values += "'" + data.get(s) + "',";

            } else {
                whatToInsert += s;
                values += "'" + data.get(s) + "'";

            }
            index++;
        }
        startQ += whatToInsert + ") VALUES" + values + ")";
        System.out.println(startQ);
        //startQ += " where " + idType + " = " + id + ";";




        statement = connection.createStatement();

        PreparedStatement pstmt = connection.prepareStatement(startQ);
        pstmt.executeUpdate();





    }

    public int getLastID() {

        String query = "SELECT LAST_INSERT_ID();";
        int id = 0;

        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return id;
    }

    public void deleteSong(int id, String table, String type) {
        String query = "delete from " + table + " where " + type + " = " + id + ";";

        try {
            statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }

    }

    public void deleteSong2(int id, String table, String type, String type2, int id2) {
        String query = "delete from " + table + " where " + type + " = " + id + " and " + type2 + "= " + id2 + ";";

        try {
            statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        System.out.println("deleted");

    }

    public static void main(String[] args) throws SQLException {
        new DBConnection();
    }

    public int albumExists(String IALB) {
        //ikke begynt på. Trenger for å ikke oprrette mange album og artister med samme navn. 
        int id = 0;
        String query = "select IFNULL(idALBUM,0) from ALBUM where IALB = '" + IALB + "'";

        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt(1);

            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return id;
    }

    public int artistExists(String IART) {
        //ikke begynt på. Trenger for å ikke oprrette mange album og artister med samme navn. 
        int id = 0;
        String query = "select IFNULL(idARTIST,0) from ARTIST where IART = '" + IART + "'";

        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt(1);

            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return id;
    }

    public DefaultTableModel simpleSearch(String searchWords, HashMap<String, Tag> tags) {

        DefaultTableModel dm = new TableModell();
        dm.addColumn("");
        try {
            ResultSet rs = null;
            //String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
            String initialQuery = "select SONG.idSONG,INAM, IART,IALB, ILEN, IGNR,ICRD, " + getLargeString() + " from ARTIST, ALBUM, SONG, METADATA, AAS where METADATA.idMETADATA = SONG.idMETADATA and SONG.idSONG = AAS.idSONG and ALBUM.idALBUM = AAS.idALBUM and ARTIST.idARTIST = AAS.idARTIST and (";
            String whereLines = "";
            int index = 0;
            for (String tag : tags.keySet()) {
                if (index == tags.size() - 1) {
                    whereLines += tag + " like '%" + searchWords + "%'";
                } else {
                    whereLines += tag + " like '%" + searchWords + "%'" + " or ";
                }

                index++;
            }

            String last = ") group by INAM;";
            initialQuery += whereLines + last;
            System.out.println(initialQuery);
            rs = statement.executeQuery(initialQuery);
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

    public DefaultTableModel advancedSearch(HashMap<String, JTextField> tags) {

        DefaultTableModel dm = new TableModell();
        dm.addColumn("");
        try {
            HashMap<String, String> tempChecker = new HashMap<>();
            ResultSet rs = null;
            //String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
           String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ICRD," + getLargeString() + " from SONG, METADATA, AAS, ALBUM, ARTIST";
           
            String initialQuery = selectQ + " where METADATA.idMETADATA = SONG.idMETADATA and SONG.idSONG = AAS.idSONG and ALBUM.idALBUM = AAS.idALBUM and ARTIST.idARTIST = AAS.idARTIST and (";
            String whereLines = "";
            int index = 0;
            for (String tag : tags.keySet()) {
                if (!tags.get(tag).getText().equals("")) {
                    tempChecker.put(tag, tags.get(tag).getText());
                }
            }
            for (String taga : tempChecker.keySet()) {
                if (index == tempChecker.size() - 1) {
                    whereLines += taga + " like '%" + tags.get(taga).getText() + "%'";
                } else {
                    whereLines += taga + " like '%" + tags.get(taga).getText() + "%'" + " and ";
                }
                index++;
            }




            String last = ") group by INAM;";
            initialQuery += whereLines + last;
            System.out.println(initialQuery);
            rs = statement.executeQuery(initialQuery);
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

    public int insertImage(FileInputStream fra, File file, String rez) {
        int id = 0;
        try {

            int lon = (int) file.length();
            statement = connection.createStatement();

            PreparedStatement pstmt = connection.prepareStatement("insert into PICTURE(IPIC, IIMG, IRES,ISIZ) values ( ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, file.getName());
            pstmt.setBinaryStream(2, (InputStream) fra, (int) file.length());
            pstmt.setString(3, rez);
            pstmt.setInt(4, lon);
            pstmt.executeUpdate();

            fra.close();
        } catch (Exception e) {

            System.out.println("Unable to create." + e);


        }
        return id;
    }

    public int checkImage(int idSOng, int idARTIST, int idALBUM) {
        String query = "select ifnull(idPIC,0) from AAS where idSONG = " + idSOng + " and idARTIST = " + idARTIST + " and idALBUM = " + idALBUM + ";";
        int id = 0;
        try {
            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt(1);

            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return id;
    }

    public void updatePIC(int songId, int artistId, int albumId, int picID) throws SQLException {
        String startQ = "update AAS set idPIC = " + picID + " where idSONG = " + songId + " and idARTIST = " + artistId + " and idALBUM = " + albumId + ";";
        System.out.println(startQ);
        statement = connection.createStatement();
        PreparedStatement pstmt = connection.prepareStatement(startQ);
        pstmt.executeUpdate();
    }

    public Picture hentFil(int idSONG, int idARTIST, int idALBUM) throws FileNotFoundException, SQLException, IOException {
        boolean hasImg = false;
        String query = "select AAS.idPIC,IPIC,IIMG,ISIZ,IRES from AAS,PICTURE where AAS.idPIC = PICTURE.idPIC and idSONG = " + idSONG + " and idARTIST = " + idARTIST + " and idALBUM = " + idALBUM + ";";
        System.out.println(query);
        String lagring = Path.path + "dette.jpg";
        Picture pic = new Picture();
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            pic.setName(resultSet.getString("IPIC"));
            pic.setId(resultSet.getInt(1));
            pic.setSize(resultSet.getInt("ISIZ"));
            pic.setRes(resultSet.getString("IRES"));
            hasImg = true;
            String name = resultSet.getString(1);
            String description = resultSet.getString(2);


            File image = new File(lagring);
            FileOutputStream fos = new FileOutputStream(image);

            byte[] buffer = new byte[1];
            InputStream is = resultSet.getBinaryStream(3);
            while (is.read(buffer) > 0) {
                fos.write(buffer);
            }
            fos.close();
        }
        Image images = null;
        if (hasImg) {
            images = ImageIO.read(new File(lagring));
            ImageIcon i = new ImageIcon(images);
            pic.setBilde(i);
        } else {
            pic.setBilde(new javax.swing.ImageIcon(getClass().getResource("/nrkprosjekt/graphics/artist.png")));
        }


        return pic;
    }

    public ArrayList<Album> findAnArtistAlbums(int id) {
        ArrayList<Album> tempAlbums = new ArrayList<>();
        String query = "select ALBUM.idALBUM, IALB, IART, ARTIST.idARTIST from ALBUM,ARTIST,AAS where AAS.idALBUM = ALBUM.idALBUM and AAS.idARTIST = ARTIST.idARTIST and ARTIST.idARTIST = " + id + " group by ALBUM.idALBUM;";
        try {
            ResultSet rs = null;

            rs = statement.executeQuery(query);
            while (rs.next()) {
                tempAlbums.add(new Album(rs.getInt(1), rs.getString("IALB")));
            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }
        return tempAlbums;
    }

    public void getTracksInAlbums(int idAlb) {
        String query = "select SONG.idSONG,INAM, ILEN, ISON, IALB from SONG, ALBUM, AAS where AAS.idSONG = SONG.idSONG and AAS.idALBUM = ALBUM.idALBUM and ALBUM.idALBUM = " + idAlb + " group by SONG.idSONG";
        ArrayList<Track> tempTracks = new ArrayList<>();
        try {
            ResultSet rs = null;

            rs = statement.executeQuery(query);
            while (rs.next()) {
                Track track = new Track();
                //track.setid
                //tempAlbums.add(new Album(rs.getInt(1), rs.getString("IALB")));
            }

        } catch (Exception e) {
            System.out.println("Unable to create." + e);
        }

    }

    public DefaultTableModel getTracks(int idAlb) {

        DefaultTableModel dm = new TableModell();
        dm.addColumn("");
        try {
            ResultSet rs = null;
            //String selectQ = "select SONG.idSONG,INAM,IART,IALB,ILEN,IGNR,ISON,IKEY, IMED,ISRF,IENG,ITCH,ISRC,ICOP,ISFT,ICRD,ICMT,ISBJ,ILAN,ICON from SONG, METADATA, AAS, ALBUM, ARTIST";
            String query = "select SONG.idSONG,INAM, ILEN, IALB from SONG, ALBUM, AAS where AAS.idSONG = SONG.idSONG and AAS.idALBUM = ALBUM.idALBUM and ALBUM.idALBUM = " + idAlb + " group by SONG.idSONG";

            rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            //Coding to get columns-
            int cols = rsmd.getColumnCount();
            String c[] = new String[cols];
            for (int i = 1; i < cols; i++) {
                c[i] = rsmd.getColumnName(i + 1);
                dm.addColumn(c[i]);
            }
            //get data from rows
            Object row[] = new Object[cols + 1];

            while (rs.next()) {
                for (int i = 1; i < cols + 1; i++) {
                    row[i] = rs.getString(i);
                }
                dm.addRow(row);


            }

            System.out.println(dm.getColumnCount());


        } catch (Exception e) {
            System.out.println(e);
        }
        return dm;
    }
}
