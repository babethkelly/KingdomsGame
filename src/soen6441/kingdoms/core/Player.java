/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import soen6441.kingdoms.strategy.MoveContext;
import soen6441.kingdoms.strategy.MoveStrategy;

/**
 * This class represents the player with its id, the value of coins that he has,
 * its initial tile, the set of castles he gets.
 *
 * @author kkm_iu
 */
public class Player implements Runnable {

    private int id = 0;
    private int coinsValue = 0;
    //private Tile tile = null;
    
    private List<Castle> castles = new ArrayList<Castle>();
     private List<Tile> tiles = new ArrayList<Tile>();
    private MoveStrategy strategy = null;
    public static int PLAYER_NUM_TILES=3;
    
    // Thread managment
    class ThreadManager {
        public final static boolean THREADED = true;
        public Object lock = new Object();
        public Object outLock = new Object();
        public MoveContext moveContext = null;
        public boolean interrupted = false;
    }
    
    private ThreadManager threadMngr = new ThreadManager();    
    
    /**
     * Constructor for the player
     *
     * @param id ID of the player from 1 to 4
     */
    public Player(int id) {
        this.id = id;
    }

    /**
     * Obtain ID of the player
     *
     * @return the id of the player
     */
    public int getID() {
        return id;
    }

    /**
     * Ask strategy to perform the move
     *
     * @param context reference to MoveContext
     */
    public void performMove(MoveContext context) {
        if (ThreadManager.THREADED) {
            threadMngr.moveContext = context;
            synchronized (threadMngr.lock) {
                threadMngr.lock.notify();
            }
            synchronized (threadMngr.outLock) {
                try {
                    threadMngr.outLock.wait();
                } catch (InterruptedException ex) {
                    System.out.println(Thread.currentThread().getName() + " got interrupted");
                }
            }
        } else {
            strategy.performAction(context);
        }
    }

    /**
     * Set strategy used by the player
     *
     * @param strategy strategy used by the player
     */
    public void setStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
        if (ThreadManager.THREADED) {
            Thread t = new Thread(this, "Player " + id + " Thread");
            t.start();
        }        
    }

    /**
     * set the current coins value of the player
     *
     * @param value the value of the coins of the player after an operation like
     * giving the money to the bank or getting money from the bank
     */
    public void setCoinsValue(int value) {
        coinsValue = value;
    }

    /**
     * To get the value of the coins of this player
     *
     * @return the value of the coins of the player
     */
    public int getCoinsValue() {
        return coinsValue;
    }

    /**
     * to set the tile that the player draws at the beginning
     *
     * @param tile an instance of the tile class
     */
    
  /*  public void setTile(Tile tile) {
        this.tile = tile;
    }*/
    /**
     * To get one tile from the tile supply
     *
     * @return the tile removed from the tile supply area
     */
    
public List<Tile> getTiles() {
        return tiles;
    }

//Select one tile from three tile to place on the board
public Tile getTileFromOwnTiles()
{
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
    /**
     * Returns the list of castles of this player
     *
     * @return the list of castles of this player
     */
    public List<Castle> getCastles() {
        return castles;
    }

    /**
     * Returns number of castles by rank
     *
     * @param type Ranking of castles
     * @return Number of castles of that rank
     */
    public int getNumCastlesByRank(String type) {
        int count = 0;
        Iterator<Castle> i = castles.iterator();
        while (i.hasNext()) {
            if (i.next().getType().equals(type)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Return number of tiles
     * @return number of tiles
     */
    public int getNumTiles()
    {
        return tiles.size();
    }

    /**
     * Place player's tile onto the board
     *
     * @param board The board
     * @param boardRow The index of the row where the object should be placed
     * @param boardCol The index of the column where the object should be placed
     * @return true if successful, false otherwise
     */
    boolean placeTileOnBoard(Board board, int boardRow, int boardCol) {
       
        if (board == null || getNumTiles() == 0) 
{
            return false;
        }
        

        Iterator<Tile> i = tiles.iterator();
        while (i.hasNext()) 
{
            Tile t= i.next();
            
            
                 if (board.setPlacable(t, boardRow, boardCol) == 0) {
            System.out.println(">>> Player " + getID() + " places own tile " + t.toString() + " onto board (" + boardRow + ", " + boardCol + ")");
           
   i.remove();
              return true;  }
                 }
            
        
           return false;
            
      
}
  
    boolean placeSpecificTileOnBoard(String tileType,int tileValue, Board board, int boardRow, int boardCol) {
      
        if (board == null || getNumTiles() == 0) 
{
            return false;
        }
        

        Iterator<Tile> i = tiles.iterator();
        while (i.hasNext()) 
{
            Tile t= i.next();
            
            if (t.getType().equals(tileType)&& t.getValue()==tileValue)
            {
            
            
                 if (board.setPlacable(t, boardRow, boardCol) == 0) {
            System.out.println(">>> Player " + getID() + " places own tile " + t.toString() + " onto board (" + boardRow + ", " + boardCol + ")");
         i.remove();  
     return true;
                }
                break;
            }
        }
        return false;
    }
    
   

    /**
     * Player player's castle onto the board
     *
     * @param castleType Rank of castle to place
     * @param board The board
     * @param boardRow The index of the row where the object should be placed
     * @param boardCol The index of the column where the object should be placed
     * @return
     */
    boolean placeCastleOnBoard(String castleType, Board board, int boardRow, int boardCol) {
        if (board == null || getNumCastlesByRank(castleType) == 0) 
{
            return false;
        }

        Iterator<Castle> i = castles.iterator();
        while (i.hasNext()) {
            Castle c = i.next();
            if (c.getType().equals(castleType)) 
{
                if (board.setPlacable(c, boardRow, boardCol) == 0) 
{
                    System.out.println(">>> Player " + getID() + " places own castle " + c.toString() + " onto board (" + boardRow + ", " + boardCol + ")");
                    i.remove();
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    /**
     * Thread run method
     */
    public void run() {
        do {
            synchronized (threadMngr.lock) {
                try {
                    threadMngr.lock.wait();
                    if (!threadMngr.interrupted) {
                        strategy.performAction(threadMngr.moveContext);
                    }
                } catch (InterruptedException ex) {
                    System.out.println(Thread.currentThread().getName() + " got interrupted");
                }
            } 
            synchronized (threadMngr.outLock) {
                threadMngr.outLock.notify();
            }
        } while (!threadMngr.interrupted);
    }
    
    /**
     * Release lock on thread so it can be terminated
     */
    public void destroy() {
        if (ThreadManager.THREADED) {
            threadMngr.interrupted = true;
            synchronized (threadMngr.lock) {
                threadMngr.lock.notify();
            }
        }
    }

    /**
     * Return a string representation of this player
     *
     * @return a string representation of this player
     */
    public String toString() {
        String strategyName = "<Undefined>";
        if (strategy != null) {
            strategyName = strategy.getClass().getName();
        }
        
        StringBuilder buffer = new StringBuilder();
        buffer.append("Player[");
        buffer.append("id=").append(id);
        buffer.append(",strategyName=").append(strategyName);
        buffer.append(",coinsValue=").append(coinsValue);
        buffer.append(",tile=").append(tiles);
        buffer.append(",\ncastles=").append(castles);
        buffer.append("]");
        return buffer.toString();
    }
}
