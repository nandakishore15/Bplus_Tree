package com.nanda;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Class for Leaf Node which extends Node class
 */
class LeafNode extends Node {
    ArrayList<ArrayList<String>> values;
    LeafNode nextLeafNode; //pointer to next leaf node
    LeafNode previousLeafNode; //pointer to previous leaf node

    /**
     * Initialize a leaf node with given key and a list of values.
     * @param key is {@code Double} of key that forms the leaf node.
     * @param value is {@code String} of value that forms the leaf node.
     */
    public LeafNode(Double key, String value) {
        isLeafNode = true;
        keys = new ArrayList<>();
        values = new ArrayList<>();
        keys.add(key);

        ArrayList<String> valueList = new ArrayList<>();
        valueList.add(value);
        values.add(valueList);
    }

    /**
     * Create a leaf node with list of keys and list of list of values.
     * @param keys {@code ArrayList} of keys that are to inserted in the leaf node.
     * @param values {@code ArrayList<ArrayList<String>} of values that are to be inserted in the leaf node.
     */
    public LeafNode(ArrayList<Double> keys, ArrayList<ArrayList<String>> values) {
        isLeafNode = true;
        this.keys = new ArrayList<>(keys);
        this.values = new ArrayList<>();

        for (ArrayList<String> value:values) {
            this.values.add(value);
        }
    }

    /**
     * Insert key value pair into the leaf node in a sorted manner.
     *
     * @param key {@code Double} key that needs to be inserted in a sorted way.
     * @param value {@code ArrayList<String>} of value that is to be inserted along with the key.
     */
    public void insertSorted(Double key, ArrayList<String> value) {
        if (key.compareTo(keys.get(0)) < 0) {
            keys.add(0, key);
            values.add(0, value);
        }
        else if ( key.compareTo( keys.get( keys.size() - 1 ) ) > 0 ) {
            keys.add(key);
            values.add(value);
        }
        else {
            ListIterator<Double> iterator = keys.listIterator();
            while (iterator.hasNext()) {
                if (iterator.next().compareTo(key) > 0) {
                    int position = iterator.previousIndex();
                    keys.add(position, key);
                    values.add(position, value);
                    break;
                }
            }
        }
    }

    /**
     * Add value to the list of values at the index specified.
     * @param index {@code int} index of where value is supposed to be added.
     * @param value {@code String} value which to to be added to the list of values.
     */
    public void addValue(int index, String value){
        values.get(index).add(value);
    }


}
