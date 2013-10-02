
package nrkprosjekt;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mNygaard
 */
public class Test_Martin {
    
    private JFrame f;
    private JPanel p;
    private JButton b1;
    private JLabel lab;
    
    public Test_Martin()
    {
        
        gui();
    }
    
    public void gui()
    {
        
        f = new JFrame("Test1");
        f.setVisible(true);
        f.setSize(600,400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        p = new JPanel();
        p.setBackground(Color.BLUE);
        
        b1 = new JButton("TestButton");
        lab = new JLabel("This is a test label");
        
        p.add(b1);
        p.add(lab);
        
        f.add(p,BorderLayout.SOUTH);
        
        // Halla
        
    }
    
    
}
