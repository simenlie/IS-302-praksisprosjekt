/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.Component;

/**
 *
 * @author Simen
 */
public class UpdateHandler {

    Component comp;
    String table;
    int id;

    public UpdateHandler(Component comp, String table, int id) {
        this.comp = comp;
        this.table = table;
        this.id = id;

    }

    public Component getComp() {
        return comp;
    }

    public void setComp(Component comp) {
        this.comp = comp;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
