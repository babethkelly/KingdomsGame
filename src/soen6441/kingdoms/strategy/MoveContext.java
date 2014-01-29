package soen6441.kingdoms.strategy;

import java.util.List;
import soen6441.kingdoms.core.Board;
import soen6441.kingdoms.core.Player;
import soen6441.kingdoms.core.TileStack;

/**
 * Abstracting game state information available for a player before performing a
 * move and action available to a player when perform a move
 */
public interface MoveContext {

    /**
     * Obtain the player that is going to make the move
     *
     * @return the player that is going to make the move
     */
    
    public Player getPlayer();

    /**
     * Obtain the current board state
     *
     * @return the current board state
     */
    public Board getBoard();
    
    /**
     * retrieve a list of players
     */
    public List<Player> getPlayers();

    /**
     * Player places its own tile onto the board. It will fail if the given
     * board location is not empty OR if player is not holding a tile
     *
     * @param boardRow The index of the row where the object should be placed
     * @param boardCol The index of the column where the object should be placed
     * @return true if successful, false otherwise
     */
    public boolean placePlayerTileOnBoard(int boardRow, int boardCol);
     public boolean placeSpecificPlayerTileOnBoard(String tileType,int tileValue,int boardRow, int boardCol);

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
    public boolean placePlayerCastleOnBoard(String castleType, int boardRow, int boardCol);

    /**
     * Player picks a card from tile stack and places it onto the board. It will
     * fail if the given board location is not empty OR can not draw from tile
     * stack
     *
     * @param boardRow The index of the row where the object should be placed
     * @param boardCol The index of the column where the object should be placed
     * @return true if successful, false otherwise
     */
    
    
    public boolean isFirstRound();

    /**
     * Check if it is the last round during an epoch
     *
     * @return true if it is the last round during an epoch
     */
    public boolean isLastRound();

    /**
     * This statistic is used to help in the strategies definition The player
     * gets this status to determine whether we are at the first round of the
     * game
     *
     * @return the number of players currently playing
     */
    public int getNumPlayers();

    /**
     * to check if the tileStack is empty
     */
    public boolean isEmptyTileStack();
    
    public TileStack getTileStack();
    
    /**
     * The player call this function to  draw a new tile from tileStack immediately after placing one of its tile
     * so that at the end of its turn he always has 3 tiles
     * @return true if the tile was drawn successfully 
     */
    public boolean playerDrawsNewTileFromTileStack();
}
