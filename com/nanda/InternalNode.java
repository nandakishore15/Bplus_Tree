package com.nanda;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for internal node which extends node.
 */
public class InternalNode extends Node {

    protected ArrayList<Node> childNodePointers; //Pointers to child nodes.

    /**
     * Initialize internal node with given key and child node pointers.
     * @param key {@code Double} key that needs to be in the internal node.
     * @param childNodePointer1 {@code Node} child node pointer.
     * @param childNodePointer2 {@code Node} child node pointer
     */
    public InternalNode(Double key, Node childNodePointer1, Node childNodePointer2) {
        isLeafNode = false;
        keys = new ArrayList<>();
        keys.add(key);
        childNodePointers = new ArrayList<>();
        childNodePointers.add(childNodePointer1);
        childNodePointers.add(childNodePointer2);
    }

    /**
     * Initialize internal node with given keys and child node pointers.
     * @param keys {@code List<Double>} of keys that should be in the internal node.
     * @param childNodePointers {@code List<Node>} of child node pointers that should be in the internal node.
     */
    public InternalNode(List<Double> keys, List<Node> childNodePointers) {
        isLeafNode = false;

        this.keys = new ArrayList<>(keys);
        this.childNodePointers = new ArrayList<>(childNodePointers);

    }

    /**
     * Insert the entry of key child node pointer pair into the node at the given index in a sorted a manner.
     *
     * @param e {@code MyMap<Double, Node>} of key child node pointer pair.
     * @param index {@code int} index where the key child node pointer pair has to be inserted.
     */
    public void insertSorted(MyMap<Double, Node> e, int index) {
        Double key = e.getKey();
        Node child = e.getValue();

        if (index >= keys.size()) {
            keys.add(key);
            childNodePointers.add(child);
        } else {
            keys.add(index, key);
            childNodePointers.add(index+1, child);
        }
    }

}
