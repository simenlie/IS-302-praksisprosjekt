/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Simen
 */
public class Calculator {

    public Icon changeImage(ImageIcon icon, int width) {
        int imageW = icon.getIconWidth(); //My Button width  
        int imageH = icon.getIconHeight();//My button height  

        Image img = icon.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 140, 199, imageW, imageH, null, null);
        Icon newIcon = new ImageIcon(bi);
        return newIcon;
    }

    public Icon dos(ImageIcon icon, int width) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, 7, java.awt.Image.SCALE_SMOOTH);
        Icon newIcon = new ImageIcon(newimg);
        return newIcon;
    }

    public Icon resizeImage(ImageIcon icon, int width, int height) {

        System.out.println(width + " " + height);


        int maxWidth = 146;
        int maxHeight = 146;

        if (width > height) {
            if (maxWidth < width || maxHeight < height) {
                int w = 146;
                int percent = (height * 100) / width;
                System.out.println(percent);
                int h = percent * maxWidth / 100;
                Image img = icon.getImage();
                Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                Icon newIcon = new ImageIcon(newimg);
                return newIcon;
            }
        } else {
            if (maxWidth < width || maxHeight < height) {
                int h = 146;
                int percent = (width * 100) / height;
                System.out.println(percent);
                int w = percent * maxHeight / 100;
                Image img = icon.getImage();
                Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                Icon newIcon = new ImageIcon(newimg);
                return newIcon;
            }
        }

        return icon;


    }

    public Icon cropImage(BufferedImage src, int width, int height, int w, int in) {
        System.out.println(width + " SIm ");
        int calc = w-height;
        int h = calc / 2;
        BufferedImage dest = null;
        if (in == 0) {
            dest = src.getSubimage(h, 0, width, height);
        } else {
            dest = src.getSubimage(0, h, width, height);
        }

        Icon i = new ImageIcon(dest);
        return i;
    }
}
