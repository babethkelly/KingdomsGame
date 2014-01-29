package soen6441.kingdoms.core;

import soen6441.kingdoms.strategy.MoveContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * GameState is a container for all data structure used in the game logic
 */
public class GameState implements MoveContext {

    public static int MAX_EPOCH = 6;
    public static int GOLD_LIMIT = 500;
    
    private Board board = null;
    private List<Player> players = new ArrayList<Player>();
    private int currentEpoch = 0;
    private int numPlayers = 0;
    private int bankCoinsValue = 0;
    private TileStack tileStack = null;
    private int currentPlayerId = 0;
    private int startingPlayerId = 0;
    private static int NUM_INITIAL_TILES_ON_BOARD=3;
   

    class PlayerCoinComparable implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            if (p1.getCoinsValue() > p2.getCoinsValue()) {
                return -1;
            } else if (p1.getCoinsValue() == p2.getCoinsValue()) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * zakaria
     * Create a new GameState with given number of players
     *
     * @param numPlayers Number of players
     * @throws IllegalArgumentException if number of players not between 2 to 4
     */
    public GameState(int numPlayers) throws IllegalArgumentException {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players can only be from 2 to 4");
        }
        this.numPlayers = numPlayers;
    }

    /**
     * Setup game state to start a new epoch
     *
     * @param epoch The epoch about to start
     * @throws IllegalArgumentException if given epoch not between 1 to 3 OR if
     * given epoch is not current epoch + 1
     */
    public void setup(int epoch) throws IllegalArgumentException {
        if (epoch < 1 || epoch > MAX_EPOCH) {
            throw new IllegalArgumentException("Epoch can only be 1 to " + MAX_EPOCH);
        }
        if (epoch != (currentEpoch + 1)) {
            throw new IllegalArgumentException("Unexpected epoch");
        }
       
        
        currentEpoch = epoch;

        // Setup board, empty all slots
        board = new Board();
        
        //place the dragon,goldmine and wizard on the board
        Tile[] tiles=new Tile[NUM_INITIAL_TILES_ON_BOARD];
        tiles[0]=new Tile(Tile.DRAGON,0);
        tiles[1]=new Tile(Tile.GOLD_MINE,0);
        tiles[2]=new Tile(Tile.WIZARD,0);
        BoardPos bp;
        
        for(int i=0;i<NUM_INITIAL_TILES_ON_BOARD;i++)
        {
            
         bp = findRandomEmptySlotOnBoard();
         board.setPlacable(tiles[i], bp.row, bp.col);
        }
        
        
        // Setup tiles stack
        tileStack = new TileStack();

        //19= Copper Coins of value 1
        //12= copper coins of value 5
        //20= silver coins of value 10
        //8= gold coins of vale 50
        //4= gold coins of value 100

        if (currentEpoch == 1) {
            bankCoinsValue = 19 * 1 + 12 * 5 + 20 * 10 + 8 * 50 + 4 * 100;
        }

        // Setup players 
        //tarnum
        for (int i = 0; i < numPlayers; i++) {

            Player player;
            int playerId = i + 1;

            // If first epoch, create players
            if (currentEpoch == 1) {
                player = new Player(playerId);
                players.add(player);

                // Distribute coins of value of 50
                player.setCoinsValue(50);

                bankCoinsValue -= 50;
            } else {
                player = players.get(i);
                
                // Clear all items on hands then re-distributing them later
                player.getTiles().clear();
                player.getCastles().clear();
            }

            // Distribute 3 tiles
            for(int k=0;k<Player.PLAYER_NUM_TILES;k++)
            {
            Tile t=tileStack.drawTile();
            player.getTiles().add(t);
            }

            // Distribute castles according to number of players

            //kelly
            //adding the number of castles of each type
            // 3 castles of rank 2
            //changed the constructor of castles to introduce colors.
            //tarnum
            for (int j = 0; j < 3; j++) {
                player.getCastles().add(new Castle(Castle.RANK_2, playerId));

            }
            // 2 castles of rank 3
            for (int j = 0; j < 2; j++) {
                player.getCastles().add(new Castle(Castle.RANK_3, playerId));

            }
            // 1 castle of rank 4
            player.getCastles().add(new Castle(Castle.RANK_4, playerId));

            // the number of castles of type 1 will differ according to the number of players
            if (numPlayers == 2) {
                // 4 castles of rank 1 
                for (int j = 0; j < 4; j++) {
                    player.getCastles().add(new Castle(Castle.RANK_1, playerId));
                }

            } else if (numPlayers == 3) {
                // 3 castles of rank 1 
                for (int j = 0; j < 3; j++) {
                    player.getCastles().add(new Castle(Castle.RANK_1, playerId));
                }

            } else if (numPlayers == 4) {
                // 2 castles of rank 1 
                for (int j = 0; j < 2; j++) {
                    player.getCastles().add(new Castle(Castle.RANK_1, playerId));
                }

            }

        } // end-for (i)

        currentPlayerId = determineFirstPlayerId();
        startingPlayerId = currentPlayerId;
    }

    /**
     * Returns coins value in the bank
     *
     * @return coins value in the bank
     */
    public int getBankCoinsValue() {
        return this.bankCoinsValue;
    }

    /**
     * Set coins value in the bank
     *
     * @param value Coins value to set in the bank
     */
    public void setBankCoinsValue(int value) {
        this.bankCoinsValue = value;
    }
    
    public void addTileToTilestack(Tile t)
    {
       tileStack.getTiles().add(t);
    }

    /**
     * Returns the board of the game
     *
     * @return the board of the game
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Returns the current epoch
     *
     * @return the current epoch
     */
    public int getCurrentEpoch() {
        return this.currentEpoch;
    }

    /**
     * Returns ID of the current player
     *
     * @return ID of the current player
     */
    public int getCurrentPlayerId() {
        return this.currentPlayerId;
    }

    /**
     * Returns ID of the starting player
     *
     * @return ID of the starting player
     */
    public int getStartingPlayerId() {
        return this.startingPlayerId;
    }

    /**
     * Next player's turn
     */
    public void nextTurn() {
        currentPlayerId++;
        if (currentPlayerId > numPlayers) {
            currentPlayerId = 1;
        }
    }

    /**
     * Returns number of players
     *
     * @return number of players
     */
    public int getNumPlayers() {
        return this.numPlayers;
    }

    /**
     * Returns list of players
     *
     * @return list of players
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Returns a player given the ID
     *
     * @param id ID of the player
     * @return a player
     */
    public Player getPlayer(int id) {
        Iterator<Player> i = getPlayers().iterator();
        while (i.hasNext()) {
            Player p = i.next();
            if (p != null && p.getID() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns the stack of tiles
     *
     * @return the stack of tiles
     */
    public TileStack getTileStack() {
        return tileStack;
    }

    /**
     * Determine who will be the first play when starting a new epoch
     *
     * @return ID of the player who will start first
     */
    private int determineFirstPlayerId() {
        if (currentEpoch == 1) {
            // Randomly choose one player to take the first turn
            Random r = new Random();
            return r.nextInt(numPlayers) + 1;
        } else {
            // Player with the most gold start first
            List<Player> sortedPlayers = new ArrayList<Player>();
            List<Player> players = getPlayers();
            Iterator<Player> i = players.iterator();
            while (i.hasNext()) {
                Player p = i.next();
                sortedPlayers.add(p);
            }
            Collections.sort(sortedPlayers, new PlayerCoinComparable());
            return sortedPlayers.get(0).getID();
        }
    }

    /**
     * Return a string representation of this game state
     *
     * @return a string representation of this game state
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n=== GameState[\n");
        buffer.append("currentEpoch=").append(currentEpoch);
        buffer.append(",bankCoinsValue=").append(bankCoinsValue);
        buffer.append(",currentPlayerId=").append(currentPlayerId);
        buffer.append(",startingPlayerId=").append(startingPlayerId);
        buffer.append(",numPlayers=").append(numPlayers);
        buffer.append("\n\nplayers:\n");
        Iterator<Player> i = getPlayers().iterator();
        while (i.hasNext()) {
            Player p = i.next();
            buffer.append(p);
            buffer.append("\n\n");
        }
        buffer.append("board:\n").append(board);
        buffer.append("\n\ntileStack:\n").append(tileStack);
        buffer.append("\n]");
        return buffer.toString();
    }

    /**
     * Obtain the player that is going to make the move
     *
     * @return the player that is going to make the move
     */
    public Player getPlayer() {
        return getPlayer(currentPlayerId);
    }

    /**
     * Player places its own tile onto the board. It will fail if the given
     * board location is not empty OR if player is not holding a tile
     *
     * @param boardRow The index of the row where the object should be placed
     * @param boardCol The index of the column where the object should be placed
     * @return true if successful, false otherwise
     */
    public boolean placePlayerTileOnBoard(int boardRow, int boardCol) {
        Player currentPlayer = getPlayer();
        Board board = getBoard();
        return currentPlayer.placeTileOnBoard(board, boardRow, boardCol);
    }
    public boolean placeSpecificPlayerTileOnBoard(String tileType,int tileValue,int boardRow, int boardCol) {
        Player currentPlayer = getPlayer();
        Board board = getBoard();
        return currentPlayer.placeSpecificTileOnBoard(tileType,tileValue,board, boardRow, boardCol);
    }


    /**
     * Player places its own castle onto the board. It will fail if the given
     * board location is not empty OR if player does not have the given type of
     * castle left
     *
     * @param castleType Castle type. One of Castle.RANK_X constant
     * @param boardRow The index of the row where the object should be placed
     * @param boardCol The index of the column where the object should be placed
     * @return true if successful, false otherwise
     */
    public boolean placePlayerCastleOnBoard(String castleType, int boardRow, int boardCol) {
        Player currentPlayer = getPlayer();
        Board board = getBoard();
        return currentPlayer.placeCastleOnBoard(castleType, board, boardRow, boardCol);
    }

   
    /*
     * The player call this function to  draw a new tile from tileStack immediately after placing one of its tile
     * so that at the end of its turn he always has 3 tiles
     * @return true if the tile was drawn successfully 
     */
     
    public boolean playerDrawsNewTileFromTileStack()
    {
       Tile tile=getTileStack().drawTile();
       if(tile!=null)
       {
       Player currentPlayer=getPlayer();
       currentPlayer.getTiles().add(tile); 
       return true;
       }
       return false;
       
    }
    
     /**
     * Check if it is the last round during an epoch
     *
     * @return true if it is the last round during an epoch
     */
    public boolean isLastRound() {
        return (getBoard().getNumEmptySlots() <= numPlayers);
    }

    public boolean isEmptyTileStack() {
        return (getTileStack().isEmptyTileStack());
    }

    /**
     * Check if it is the 1st round during an epoch
     *
     * @return true if it is the 1st round during an epoch
     */
    public boolean isFirstRound() {
        return (getBoard().getNumNonEmptySlots() < numPlayers);
    }

  
    
    /**
     * Check if it is in last epoch and board is full OR if any player gold exceeded the limit
     * @return true if it is in last epoch and board is full, false otherwise
     */
    public boolean isGameEnded() {
        List<Player> players = getPlayers();
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player p = i.next();
            int score = p.getCoinsValue();
            if (score >= GameState.GOLD_LIMIT) {
                return true;
            }
        }
        
        return getCurrentEpoch() == GameState.MAX_EPOCH && getBoard().isFull();
    }
    
    public BoardPos findRandomEmptySlotOnBoard() {
        int i = 0;
        int j = 0;
        do {
            Random r = new Random();
            i = r.nextInt(Board.NUM_ROWS);
            j = r.nextInt(Board.NUM_COLS);
            if (board.isEmpty(i, j)) {
                break;
            }
        } while (true);

        BoardPos bp = new BoardPos();
        bp.row = i;
        bp.col = j;

        return bp;
    }
    
    /**
     * Clean up state
     */
    public void cleanup() {
        Iterator<Player> i = getPlayers().iterator();
        while (i.hasNext()) {
            Player p = i.next();
            p.destroy();
        }        
    }
}


