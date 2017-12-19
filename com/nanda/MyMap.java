package com.nanda;

/**
 * Class for map.
 * @param <Key> Class of Key eg. List, int
 * @param <Value> Class of Value eg. List,i nt
 */
public class MyMap<Key, Value> {
    private final Key key;
    private Value value;

    /**
     *
     * @param key key that is to be part of the map
     * @param value value that is to be part of the map
     */
    public MyMap(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key associated with the map.
     * @return key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Gets the value associated with the map
     * @return value
     */
    public Value getValue() {
        return value;
    }
}
