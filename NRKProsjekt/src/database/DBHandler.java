/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import nrkprosjekt.MetaEdit;

/**
 *
 * @author Simen
 */
public class DBHandler {

    Insert insert;
    MetaEdit temp;

    public DBHandler() throws IOException, SQLException {
        temp = new MetaEdit(new JFrame(), true);
        insert = new Insert(temp.getTags());
    }

    public void update() {
    }

    public void insert() {
    }

    public void delete() {
    }

    public static void main(String[] args) throws IOException, SQLException {
        new DBHandler();
    }
}
