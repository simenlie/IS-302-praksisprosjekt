/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Simen
 */
public class ListHandler {

    ArrayList<Artist> collection;
    HashMap<String, Integer> collectionLookup;

    public ListHandler() {
        collectionLookup = new HashMap<>();
        collection = new ArrayList<>();

    }

    public ArrayList<Artist> getCollection() {
        return collection;
    }

    public int getSize() {
        return collection.size();
    }

    public String getCollectionInString() {
        String output = "";
        int index = 0;
        for (Artist s : collection) {
            if (index == collection.size() - 1) {
                output += s.IART;
            } else {
                output += s.IART + ", ";
            }

            index++;
        }
        return output;
    }

    public int getId(String name) {
        return collectionLookup.get(name);
    }

    public void put(String string, int i) {
        collectionLookup.put(string, i);
    }

    public void add(Artist artist) {
        collection.add(artist);
    }
}
