/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javax.swing.ImageIcon;

/**
 *
 * @author Simen
 */
public class Picture {
    int id;
    String name;
    String res;
    int size;
    ImageIcon bilde;

    public Picture(int id, String name, String res, ImageIcon bilde) {
        this.id = id;
        this.name = name;
        this.res = res;
        this.bilde = bilde;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Picture() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public ImageIcon getBilde() {
        return bilde;
    }

    public void setBilde(ImageIcon bilde) {
        this.bilde = bilde;
    }
    
    
}
