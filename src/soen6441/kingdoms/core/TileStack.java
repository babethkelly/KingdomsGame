/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An instance of TileStack contains the list of tiles that need to be in the
 * tile supply area on the board. The tiles are defined according to their type
 * and value TileStack defines a method to draw a tile randomly(simulating the
 * shuffling ) and placing on the board and overrides the ToString() method to
 * print the tiles based on their type and value
 *
 */
public class TileStack {
    //kelly

    private static final int HALF_NUM_RESOURCE_TILE = 6;
    private static final int NUM_HAZARD_TILE = 6;
    private static final int NUM_MOUNTAIN_TILE = 2;
    private static final int NUM_DRAGON_TILE = 1;
    private static final int NUM_WIZARD_TILE = 1;
    private static final int NUM_GOLDMINE_TILE = 1;
    private static int NOVALUE = 0;
    private List<Tile> tiles = new ArrayList<Tile>();

    /**
     * When this constructor is called, the list of tiles and their values are
     * initialized, added to an arrayList and ready for the tile supply area
     */
    TileStack() {
        // the value of the 'resource' tile is from 1 to 6 each value appearing exacly twice
        for (int i = 0; i < HALF_NUM_RESOURCE_TILE; i++) {


            tiles.add(new Tile(Tile.RESOURCE, (i + 1)));
            tiles.add(new Tile(Tile.RESOURCE, (i + 1)));
        }

        // the value of the hazard tile -1 to -6 each value appearing once
        for (int j = 0; j < NUM_HAZARD_TILE; j++) {

            tiles.add(new Tile(Tile.HAZARD, -1 * (j + 1)));
        }

        // 'Mountain' 'Wizard' 'GoldMine' and 'Dragon' tile do not have any value
        // NOVALUE argument set to 0
        for (int m = 0; m < NUM_MOUNTAIN_TILE; m++) {
            tiles.add(new Tile(Tile.MOUNTAIN, NOVALUE));

        }
      
    }

    /**
     * Randomly selects a tile from the arrayLIst of tiles since the selection
     * is random, it simulates a shuffling
     *
     * @return a TILE object randomly selected from the arrayList
     */
    public Tile drawTile() {
        //kelly
        //randomly select a number in the range of the current size of the stack/arraylist
        int currentsize = tiles.size();
        Random r = new Random();
        int num = 0;
        if (currentsize > 0) {
            r.nextInt(currentsize);
        }

        // the number randomly selected is used as the index of the tile to  remove from the stack
        // since the tile is randomly selected it works as shuffled.
        Tile t = tiles.remove(num);
        return t;



    }
//place specific tile on the board
  
    public List<Tile> getTiles(){return tiles;}
   
public boolean isEmptyTileStack() {
        return (tiles.isEmpty());
    }

    /**
     * Return a string representation of this tile stack
     *
     * @return a string representation of this tile stack
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("TileStack[");
        buffer.append("tiles=").append(tiles);
        buffer.append(']');
        return buffer.toString();
    }
}
