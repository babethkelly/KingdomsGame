/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

/**
 * This class defines the type of tiles example: resource,hazards etc... and
 * their value: For example 1 and -1 for resource and hazard tiles respectively.
 * The other tiles will have value 0 indicated by NOVALUE since they have no
 * value it implements placeable since it is one of the type of objects that can
 * be placed on the board.
 */
public class Tile implements Placeable {

    /**
     * Dragon tile
     */
    public static final String DRAGON = "DRAGON";
    /**
     * Resource tile
     */
    public static final String RESOURCE = "RESOURCE";
    /**
     * Mountain tile
     */
    public static final String MOUNTAIN = "MOUNTAIN";
    /**
     * Wizard tile
     */
    public static final String WIZARD = "WIZARD";
    /**
     * Gold mine tile
     */
    public static final String GOLD_MINE = "GOLDMINE";
    /**
     * Hazard tile
     */
    public static final String HAZARD = "HAZARD";
    //kelly
    private String type;
    private int value;

    Tile() {
        type = null;
        value = 0;
    }

    /*
     * Constructor to initialize the type based on its type and value
     */
    Tile(String type, int value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns type of tile
     *
     * @return type of tile
     */
    public String getType() {
        return type;
    }

    /**
     * Returns value of tile
     *
     * @return value of tile
     */
    //zakaria
    public int getValue() {
        return value;
    }

    /**
     * Set value of tile
     *
     * @param val value of tile
     */
    public void setValue(int val) {
        value = val;
    }

    /**
     * Double the value
     */
    public void doubleTheValue() {
        value *= 2;
    }

    /**
     * Return a string representation of this tile
     *
     * @return a string representation of this tile
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Tile[");
        buffer.append("type=").append(type);
        buffer.append(",value=").append(value);
        buffer.append(']');
        return buffer.toString();
    }
}
