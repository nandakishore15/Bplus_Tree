package com.nanda;

import java.util.ArrayList;

public class BPlusTree {

    public Node root;
    public static int degree;

    /**
     * Initialize BPlusTree with {@code int} degree.
     *
     * @param input_degree {@code int} degree of the Bplustree that is being initialized.
     */
    public void initialize(int input_degree) {
        degree = input_degree;
    }

    /**
     * Insert key value pair into BPlusTree Tree.
     *
     * @param key {@code Double} key that is to be inserted into BPlusTree.
     * @param value {@code Value} key that is to be inserted into BplusTree.
     */
    public void insert(Double key, String value) {
        //If key is already present add the value to list of values.
        if (root != null && search(key) != null) {
            LeafNode leafNode = (LeafNode) searchTree(root, key);

            for(int i = 0; i < leafNode.keys.size(); i++) {
                if( key.compareTo( leafNode.keys.get(i) ) == 0 ) {
                    leafNode.addValue(i, value);
                }
            }
            return;
        }

        //Create entry with key and leaf node as key value pair.
        LeafNode newLeafNode = new LeafNode(key, value);
        MyMap<Double, Node> entry = new MyMap<>(key, newLeafNode);

        if(root == null || root.keys.size() == 0){
            root = entry.getValue();
        }

        MyMap<Double, Node> newChild = getChild(root, entry, null);

        if(newChild == null) {
        }
        else {
            root = new InternalNode(newChild.getKey(), root, newChild.getValue());
        }
    }

    /**
     * Searches BPlusTree for given {@code Double} search_key and returns corresponding value.
     *
     * @param search_key {@code Double} key that needs to be searched in the Bplustree.
     * @return - if key is present returns {@code ArrayList} of values corresponding to the key else returns null.
     */
    public ArrayList<String> search(Double search_key) {
        if(search_key == null || root == null) {
            return null;
        }

        //Search for a leaf node which has the given key.
        LeafNode leafNode = (LeafNode) searchTree(root, search_key);

        //Search inside the leaf node for the given key.
        for(int i = 0; i < leafNode.keys.size(); i++){
            if(search_key.compareTo(leafNode.keys.get(i)) == 0) {
                return leafNode.values.get(i);
            }
        }

        return null;
    }

    /**
     * Search for a leaf node which might contain the {@code Double} key starting from the given {@code Node} node.
     *
     * @param node Starting {@code Node} node.
     * @param search_key {@code Double} key that needs to be searched in the Bpustree.
     * @return {@code Node} leafnode that might contain the key being searched.
     */
    public Node searchTree(Node node, Double search_key) {
        if(node.isLeafNode) {
            return node;
        }
        //if node is an internal node recursively search through the tree until you get a leaf node or you fall off.
        else {
            InternalNode internalNode = (InternalNode)node;

            /*
            If search_key is the less than the leftmost key of the internal node then the search_key is present in the
            leftmost subtree of the internal node, else if the search_key is more than the rightmost key of the internal
            node then the search_key is present in the rightmost subtree of the internal node else look for the spot where the
            search_key falls in between two keys and search key will be present in the subtree at that spot.
             */

            if(search_key.compareTo( internalNode.keys.get(0) ) < 0) {
                return searchTree(internalNode.childNodePointers.get(0), search_key);
            }
            else if( search_key.compareTo( internalNode.keys.get( node.keys.size()-1 ) ) >= 0 ) {
                return searchTree(internalNode.childNodePointers.get(internalNode.childNodePointers.size()-1), search_key);
            }
            else {
                for(int i = 0; i < internalNode.keys.size()-1; i++){
                    if( search_key.compareTo( internalNode.keys.get(i) ) >= 0 && search_key.compareTo( internalNode.keys.get(i+1) ) < 0 ) {
                        return searchTree(internalNode.childNodePointers.get(i+1), search_key);
                    }
                }
            }
            return null;
        }
    }

    /**
     * Search for all keys in the BPlusTree which are in between {@code Double} key1 and {@code Double} key2.
     *
     * @param key1 Leftmost bound of the range being searched
     * @param key2 Rightmost bound of the range being searched.
     * @return {@code String} of key, value pairs where key1 <= key <= key2
     */
    public String range_search(Double key1, Double key2) {
        //Return empty string if tree is empty or if one of the keys are null.
        if (key1 == null || key2 == null || root == null) {
            return "";
        }

        //Search for the leafNode in which key1 might be present.
        LeafNode leafNode = (LeafNode) searchTree(root, key1);

        String result = "";

        /*
        Search in the leaf node for keys such that each key is greater than key1 and less than key2. Append each key to
        the result. If we don't find a key greater than key2 in the current leaf node then move to the next leaf node and
        continue appending to result.
         */

        for (int i = 0; i < leafNode.keys.size(); i++) {

            if (key1.compareTo(leafNode.keys.get(i)) <= 0) {
                while (i < leafNode.keys.size() && key2.compareTo(leafNode.keys.get(i)) >= 0) {

                    for (int x = 0; x < leafNode.values.get(i).size(); x++) {
                        result += "(" + leafNode.keys.get(i) + "," + leafNode.values.get(i).get(x) + "),";
                    }

                    i++;
                    if (i == leafNode.keys.size() && key2.compareTo(leafNode.keys.get(i - 1)) >= 0) {
                        i = 0;
                        leafNode = leafNode.nextLeafNode;
                        if (leafNode == null) {
                            return result;
                        }
                    }
                }
            }
        }
        return result;
        //System.out.print(result);
    }

    /**
     * Get child entries after insertion.
     * @param node {@code Double} from where we should start recursing for insertion.
     * @param entry Key value pair of {@code Double} key being inserted and {@code Node} node.
     * @param newChild {@code Node} new internal/root/leaf node that was created created.
     * @return {@code Node} new internal/root node that was created created.
     */

    private MyMap<Double, Node> getChild(Node node, MyMap<Double, Node> entry, MyMap<Double, Node> newChild) {
        if(!node.isLeafNode) {
            //Choose subtree of the node where key can be present and recurse on the subtree node.
            InternalNode internalNode = (InternalNode) node;
            int i = 0;
            while(i < internalNode.keys.size()) {
                if(entry.getKey().compareTo(internalNode.keys.get(i)) < 0) {
                    break;
                }
                i++;
            }

            newChild = getChild(internalNode.childNodePointers.get(i), entry, newChild);

            //if child entry has not been split return null else split node and add new entry.
            if(newChild == null) {
                return null;
            } else {
                int j = 0;
                while (j < internalNode.keys.size()) {
                    if(newChild.getKey().compareTo( internalNode.keys.get(j) ) < 0) {
                        break;
                    }
                    j++;
                }

                internalNode.insertSorted(newChild, j);

                //if internal node is overfull split internal node.
                if(!internalNode.isOverfull()) {
                    return null;
                }
                else {
                    newChild = splitInternalNode(internalNode);

                    //split root and create new root node and create new internal nodes
                    if (internalNode == root) {
                        root = new InternalNode(newChild.getKey(), root, newChild.getValue());
                        return null;
                    }
                    return newChild;
                }
            }
        }
        else {
            LeafNode leafNode = (LeafNode)node;
            LeafNode newLeafNode = (LeafNode)entry.getValue();

            //Insert key-value pair into leaf if it has space else split leaf node
            leafNode.insertSorted(entry.getKey(), newLeafNode.values.get(0));

            if(!leafNode.isOverfull()) {
                return null;
            }
            else {
                newChild = splitLeafNode(leafNode);
                //split root and create new root node and create new internal nodes
                if (leafNode == root) {
                    root = new InternalNode(newChild.getKey(), leafNode, newChild.getValue());
                    return null;
                }
                return newChild;
            }
        }
    }


    /**
     * Split a leaf node and return the new right node and splitting key as a map entry {splittingKey, node}.
     *
     * @param leafNode, {@code Node} leaf node that is to be split.
     * @return {@code Map Entry} of splitting key and new node
     */
    public MyMap<Double, Node> splitLeafNode(LeafNode leafNode) {
        ArrayList<Double> newKeys = new ArrayList<>();
        ArrayList<ArrayList<String>> newValues = new ArrayList<>();

        //Keep the first "degree" keys and their and move the remaining keys and values to a new node.
        while(leafNode.keys.size() > degree) {
            newKeys.add(leafNode.keys.get(degree));
            leafNode.keys.remove(degree);
            newValues.add( leafNode.values.get(degree) );
            leafNode.values.remove(degree);
        }

        Double splittingKey = newKeys.get(0); //splitting key is the first key in the newKeys list.
        LeafNode rightNode = new LeafNode(newKeys, newValues); //right node will be a leaf node with new keys and new values.

        // Set next and previous leaf nodes for the node being split and right node.
        LeafNode tmp = leafNode.nextLeafNode;
        leafNode.nextLeafNode = rightNode;
        leafNode.nextLeafNode.previousLeafNode = rightNode;
        rightNode.previousLeafNode = leafNode;
        rightNode.nextLeafNode = tmp;

        return new MyMap<>(splittingKey, rightNode);
    }

    /**
     * Split an internal node and return the new node and the splitting key as a map entry {splittingKey, node}.
     *
     * @param internalNode, {@code Node} internal node that is to be split.
     * @return {@code Map Entry} of splitting key and new node
     */
    public MyMap<Double, Node> splitInternalNode(InternalNode internalNode){
        ArrayList<Double> newKeys = new ArrayList<>();
        ArrayList<Node> newChildNodePointers = new ArrayList<>();

        Double splittingKey = internalNode.keys.get(degree);
        internalNode.keys.remove(degree);

        /*
        Keep the first "degree" key values and "degree+1" child node pointers in the same node
        and move the last "degree" key values and "degree+1" child node pointers to a new node.
        */
        newChildNodePointers.add( internalNode.childNodePointers.get(degree + 1) );
        internalNode.childNodePointers.remove(degree + 1);

        while(internalNode.keys.size() > degree) {
            newKeys.add(internalNode.keys.get(degree));
            internalNode.keys.remove(degree);
            newChildNodePointers.add( internalNode.childNodePointers.get(degree + 1) );
            internalNode.childNodePointers.remove(degree + 1);
        }

        InternalNode rightNode = new InternalNode(newKeys, newChildNodePointers);

        return new MyMap<>(splittingKey, rightNode);
    }
}