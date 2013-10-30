/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Info;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Simen
 */
public class PriorityTags {
    
    static ArrayList<String> priority;
    static HashMap<String, String> priority2;
    Dictionary dictionary;

    public PriorityTags() {
        priority = new ArrayList<>();
        
        dictionary = new Dictionary();
        priority.add(Dictionary.getDictionary().get(Tags.INAM).name);
        priority.add(Dictionary.getDictionary().get(Tags.IART).name);
        priority.add(Dictionary.getDictionary().get(Tags.IALB).name);
        priority.add(Dictionary.getDictionary().get(Tags.ILEN).name);
        priority.add(Dictionary.getDictionary().get(Tags.IGNR).name);
        priority.add(Dictionary.getDictionary().get(Tags.ICRD).name);
        priority2 =  new HashMap<>();
        priority2.put("INAM", Dictionary.getDictionary().get(Tags.INAM).name);
        priority2.put("IART", Dictionary.getDictionary().get(Tags.IART).name);
        priority2.put("IALB", Dictionary.getDictionary().get(Tags.IALB).name);
        priority2.put("ILEN", Dictionary.getDictionary().get(Tags.ILEN).name);
        priority2.put("IGNR", Dictionary.getDictionary().get(Tags.IGNR).name);
        priority2.put("ICRD", Dictionary.getDictionary().get(Tags.ICRD).name);
    
    }

 

    static public ArrayList<String> getPriority() {
        return priority;
    }

    public static HashMap<String, String> getPriority2() {
        return priority2;
    }
    
    
    
    
}
