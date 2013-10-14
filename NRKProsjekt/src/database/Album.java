/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Simen
 */
public class Album {

    int idALBUM;
    String IALB;

    public Album(int idALBUM, String IALB) {
        this.idALBUM = idALBUM;
        this.IALB = IALB;
    }

    public int getIdALBUM() {
        return idALBUM;
    }

    public String getIALB() {
        return IALB;
    }
    
}
