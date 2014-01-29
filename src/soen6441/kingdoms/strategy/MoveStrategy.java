package soen6441.kingdoms.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import soen6441.kingdoms.core.Board;
import soen6441.kingdoms.core.BoardPos;
import soen6441.kingdoms.core.Castle;
import soen6441.kingdoms.core.Placeable;
import soen6441.kingdoms.core.Player;
import soen6441.kingdoms.core.Tile;

/**
 * Base class for all strategy classes
 */
public abstract class MoveStrategy {

    /**
     * Utility method to find an empty location on the Board
     *
     * @param context Context
     * @return Position on board that is empty.
     */
    protected static BoardPos findRandomEmptySlotOnBoard(MoveContext context) {
        int i = 0;
        int j = 0;
        do {
            Random r = new Random();
            i = r.nextInt(Board.NUM_ROWS);
            j = r.nextInt(Board.NUM_COLS);
            if (context.getBoard().isEmpty(i, j)) {
                break;
            }
        } while (true);

        BoardPos bp = new BoardPos();
        bp.row = i;
        bp.col = j;

        return bp;
    }

    /**
     *
     */
    protected static BoardPos[] getAllEmptySlotsOnBoard(MoveContext context) {
        
        ArrayList<BoardPos> array = new ArrayList<BoardPos>();
        
        /* for(int i=0;i<Board.NUM_ROWS*Board.NUM_COLS;i++)
         {
         boardPosition=new BoardPos();
            
         }*/

        //boardPosition=new BoardPos();
        for (int i = 0; i < Board.NUM_ROWS; i++) 
        {
            
            for (int j = 0; j < Board.NUM_COLS; j++) 
            {
                if (context.getBoard().isEmpty(i, j)) 
                {
                    BoardPos bp = new BoardPos();
                    bp.row = i;
                    bp.col = j;
                    array.add(bp);
                }
            }
        }
        
        if (array.size() > 0) {
            BoardPos[] boardPosition = new BoardPos[array.size()]; 
            array.toArray(boardPosition);
            return boardPosition;
        }
        return null;
    }

    /**
     * Utility method to see if player still holding a tile
     *
     * @param context Context
     * @return true if player still holding a tile
     */
    protected static boolean playerHasOwnTile(MoveContext context) {
        return context.getPlayer().getNumTiles()!= 0;
    }

    /**
     * Utility method to see if player is holding any castle
     *
     * @param context Context
     * @return true if player still holding at least 1 castle
     */
    protected static boolean playerHasOwnCastle(MoveContext context) {
        return (playerHasOwnCastle(context, Castle.RANK_1)
                || playerHasOwnCastle(context, Castle.RANK_2)
                || playerHasOwnCastle(context, Castle.RANK_3)
                || playerHasOwnCastle(context, Castle.RANK_4));
    }

    /**
     * Utility method to see if player's holding any castle of a specific Rank
     *
     * @param context Context
     * @param castleType Rank of the castle
     * @return true if player is holding any castle of a specific Rank
     */
    protected static boolean playerHasOwnCastle(MoveContext context, String castleType) {
        return context.getPlayer().getNumCastlesByRank(castleType) > 0;
    }

    protected static boolean tileStackIsEmpty(MoveContext context) {
        return context.isEmptyTileStack();
    }
    
    protected  Tile selectTileToPlaceOnboard(int row, int col,MoveContext context) 
    {
          Player currentPlayer=context.getPlayer();
          int max=0;
          int currentScore=0;
          Tile selectedTile=null;
          
        List<Tile> playerTiles;
        playerTiles = new ArrayList<Tile>();
        playerTiles=currentPlayer.getTiles();
        
        Iterator<Tile> i = playerTiles.iterator();
        while (i.hasNext())
        {

            Tile t= i.next();
            //try placing on the board
            context.getBoard().slots[row][col]=t;
           
            
            currentScore=context.getBoard().calculateScoreForPlayer(currentPlayer.getID());
            
            
            if(max<=currentScore)
            {
                max=currentScore;
                selectedTile=t;
                
            }
          
} 
        //empty that position on the board after selecting which tile to place
         playerTiles.remove(selectedTile); 
        context.getBoard().slots[row][col]=null;
        
   return selectedTile;
    }
    //calculate score for placeable another to decide where to place the placeable on the board
    
         public int calculateScoreForPlaceable(int iD, MoveContext context, Placeable p, int row,int col) {
        
                       context.getBoard().slots[row][col]=p;

                   int currentScore=context.getBoard().calculateScoreForPlayer(iD);
                                      context.getBoard().slots[row][col]=null;

                   return(currentScore);

    }

    /**
     * Call by the framework to carry out the action for a given turn
     * @param context Context for a player to take a turn
     */
    abstract public void performAction(MoveContext context);
}
