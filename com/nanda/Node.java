package com.nanda;

import java.util.ArrayList;

/**
 * Class for node
 */
class Node {
    protected boolean isLeafNode; //flag to check if it is a leaf node.
    protected ArrayList<Double> keys; //list of keys

    /**
     * Checks if the node is overfull.
     * @return True if node is overfull else false.
     */
    boolean isOverfull() {
        return keys.size() > 2 * BPlusTree.degree;
    }

}
