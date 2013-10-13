/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JTextField;

/**
 *
 * @author Simen
 */
public class Update {
    
    ArrayList<UpdateHandler> updates;
    Queue<String> updateQueue;
    
    public Update(ArrayList<UpdateHandler> updates) {
        this.updates = updates;
        updateQueue = new LinkedList<>();
        
    }
    
    public void updateQuery() {
        
        for (UpdateHandler c : updates) {
            String beginning = "UPDATE " + c.getTable() + " SET";
            JTextField temp = (JTextField) c.getComp();
            beginning += temp.getName() + " = " + "'" + temp.getText() + "' WHERE idARTIST = " + c.getId();
            updateQueue.add(beginning);
        }
        
    }
}
