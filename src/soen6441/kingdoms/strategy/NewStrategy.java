/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.strategy;

import soen6441.kingdoms.core.BoardPos;
import soen6441.kingdoms.core.Castle;

/**
 * Implement a custom strategy that combines both the minimize / maximize strategies
 */
public class NewStrategy extends MoveStrategy {

    private MoveStrategy minimizeStrategy = new MinimizeStrategy();
    private MoveStrategy maximizeStrategy = new MaximizeStrategy();
    
    /**
     * Call by the framework to carry out the action for a given turn
     * @param context Context for a player to take a turn
     */
    public void performAction(MoveContext context) {
String choice="";
        int numPlayers = context.getNumPlayers();
        int index = 0;
        //the first round a tile is drawned randomly and placed on an empty slot
        if (context.isFirstRound()) {
            //draw a tile and place it on a random emptyslot on the board
            BoardPos bp = findRandomEmptySlotOnBoard(context);
            context.placePlayerTileOnBoard(bp.row, bp.col);
            // draw a new tile only if tilestack is empty and the player has less than 3 tiles 
            if(!tileStackIsEmpty(context) && context.getPlayer().getNumTiles()<context.getPlayer().PLAYER_NUM_TILES)
                    {
                        context.playerDrawsNewTileFromTileStack();
                    }
        } 
        else if (context.isLastRound()) {

            choice = Castle.RANK_1;
            BoardPos bp = findRandomEmptySlotOnBoard(context);

            context.placePlayerCastleOnBoard(choice, bp.row, bp.col);

        }
        else if(context.getBoard().isMoreThanHalfFull())
        {
            maximizeStrategy.performAction(context);
            
        }
        else
        {
            minimizeStrategy.performAction(context);
        }
    }
}
        