/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Simen
 */
public class DBFirstTime {

    Connection connection;
    private Statement statement;

    public DBFirstTime() throws SQLException {
        createDatabase("root","root","simenTest");
    }

    public void createDatabase(String user, String password, String name) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/?user=" + user + "&password=" + password);
        statement = connection.createStatement();
        int Result = statement.executeUpdate("CREATE DATABASE " + name);
    }

    public static void main(String[] args) throws SQLException {
        new DBFirstTime();
    }
}
