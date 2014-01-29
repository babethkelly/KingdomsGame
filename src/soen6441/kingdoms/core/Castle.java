/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

/**
 * Class representing a castle identified by the rank of the castle:
 * rank1,rank2, etc and the id of the player holding it
 * it also includes a value to a castle depending on its rank
 */
public class Castle implements Placeable {
    // kelly

    private String type;
    private int playerId;
    private int value;
    /**
     * Rank 1 castle
     */
    public static String RANK_1 = "RANK_1";
    /**
     * Rank 2 castle
     */
    public static String RANK_2 = "RANK_2";
    /**
     * Rank 3 castle
     */
    public static String RANK_3 = "RANK_3";
    /**
     * Rank 4 castle
     */
    public static String RANK_4 = "RANK_4";

    Castle() {
        type = null;
    }

    /**
     * Constructor to set the type and player id  of the player holding the
     * castle
     */
    //tarnum
    Castle(String type, int playerId) {
        this.type = type;
        this.playerId = playerId;
        if (type.equals(Castle.RANK_1)) {
            //type = Castle.RANK_2;
            this.value=1;
        } else if (type.equals(Castle.RANK_2)) {
           // type = Castle.RANK_3;
            this.value=2;
        } else if (type.equals(Castle.RANK_3)) {
           // type = Castle.RANK_4;
            this.value=3;
        }
        else if(type.equals(Castle.RANK_4)) {
           // type = "RANK_5";
            this.value=4;
        
    }
    }

    /**
     * Returns castle ranking
     *
     * @return castle ranking
     */
    public String getType() {
        return type;
    }

    /**
     * Increase rank by one
     */
    public void promoteRankByOne() {
        if (this.type.equals(Castle.RANK_1)) {
            //type = Castle.RANK_2;
            this.value=2;
        } else if (type.equals(Castle.RANK_2)) {
           // type = Castle.RANK_3;
            this.value=3;
        } else if (type.equals(Castle.RANK_3)) {
           // type = Castle.RANK_4;
            this.value=4;
        }
        else if(type.equals(Castle.RANK_4)) {
           // type = "RANK_5";
            this.value=5;
        }
    }

    /**
     * Returns playerId
     *
     * @return playerId
     */
    //zakaria
    public int getplayerId() {
        return playerId;
    }

    /**
     * To return the value of a castle depending on the rank
     * @return the value of the castle
     */
    public int getRankValue() {
        int value = 1;
        if (type.equals(RANK_1)) {
            value = 1;
        }
        if (type.equals(RANK_2)) {
            value = 2;
        }
        if (type.equals(RANK_3)) {
            value = 3;
        }
        if (type.equals(RANK_4)) {
            value = 4;
        }
        return value;

    }
    /*
     * Returns he value of castle for score calculation
     * @param c the castle for which value is required
     * @return the value of the castle
     */
    public int getRankValue(Castle c)
    {
        return c.value;
    }
    
    

    /**
     * Return a string representation of this castle
     *
     * @return a string representation of this castle
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Castle[");
        buffer.append("type=").append(type);
        buffer.append(",playerId=").append(playerId);
        buffer.append(']');
        return buffer.toString();
    }
}
