/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Entities.User;
import database.DBConnection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Simen
 */
public class LoginHandler {

    DBConnection database;
    User user;
    List<User> users;

    public LoginHandler() throws SQLException {
        database = new DBConnection();
    }

    public boolean checkUser(String username, String password) {
        User u = database.getUser(username, password);
        if (u == null) {
            return false;
        } else {
            user = u;
            return true;
        }
    }

    public User getUser() {
        return user;
    }

    public void getUsers(JTable table) {
        table.setModel(database.getUsers());
        table.getColumnModel().getColumn(0).setMinWidth(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(20);
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setPreferredWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumn("username").setHeaderValue("Username");
        table.getColumn("user_password").setHeaderValue("Password");
        table.getColumn("admin").setHeaderValue("Rights");
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (table.getValueAt(i, 4).equals("0")) {
                table.setValueAt("All rights", i, 4);
            } else {
                table.setValueAt("Read", i, 4);
            }

        }

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateUser(int id, String password, String admin, String username, boolean insert) throws SQLException {
        database.updateUser(id, password, admin, username, insert);
    }

    public void deleteUser(int id) {
        database.deleteUser(id);
    }
}
