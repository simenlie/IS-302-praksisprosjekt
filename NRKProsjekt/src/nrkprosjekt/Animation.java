/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Simen
 */
public class Animation {

    JLabel label;
    JLabel label2;
    JLabel label3;
    Timer songNameTimer;
    boolean running;
    Queue<String> strings;

    public Animation(JLabel label, JLabel label2, JLabel label3) {
        this.label = label;
        this.label2 = label2;
        songNameTimer = new Timer(1, tima);
        this.label3 = label3;
        


    }
    
    public Animation(JLabel label, JLabel label2, JLabel label3, int i,Queue<String> s) {
        this.label = label;
        this.label2 = label2;
        songNameTimer = new Timer(i, tim);
        this.label3 = label3;
        strings = s;


    }

    public void start() {
        songNameTimer.start();
        running = true;
    }

    public void stop() {
        songNameTimer.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
    
    
    Action tima = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {


            if (label.getLocation().x > label2.getLocation().x) {
                label.setLocation(label.getLocation().x - 2, label.getLocation().y);
            } else {
                stop();
                label3.setBounds(label2.getLocation().x + 60, 8, 100, 15);
            }



        }
    };
    
      Action tim = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {


            label.setText(strings.peek());
            strings.add(strings.poll());



        }
    };
}
