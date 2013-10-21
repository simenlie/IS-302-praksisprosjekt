/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Info;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Simen
 */
public class Style {

    static String font;

    public static Color getErrorColor() {
        return new Color(243, 69, 65);
    }

    public static Color getSuccessColor() {
        return new Color(0, 153, 51);
    }

    public static Font getFont(int size) {
        return new Font(font, 0, size);
    }

    public static Font getBoldFont(int size) {
        return new Font(font, 1, size);
    }

    public static void setFont(String fonte) {
        font = fonte;
    }
}
