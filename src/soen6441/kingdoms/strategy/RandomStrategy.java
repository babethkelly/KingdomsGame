/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import soen6441.kingdoms.core.BoardPos;
import soen6441.kingdoms.core.Castle;
import soen6441.kingdoms.core.Tile;

/**
 * Implements a random player
 */
public class RandomStrategy extends MoveStrategy {

    /**
     * Call by the framework to carry out the action for a given turn
     * @param context Context for a player to take a turn
     */
    public void performAction(MoveContext context) {

        // Determine what action is possible
        final int ACTION_PUT_OWN_TILE = 0;
        final int ACTION_PUT_OWN_CASTLE = 1;
      
        List<Integer> availableChoices = new ArrayList<Integer>();

        // Check if the player has a tile
        if (playerHasOwnTile(context)) {
            availableChoices.add(ACTION_PUT_OWN_TILE);
        }

        // Check if the player has any castle
        if (playerHasOwnCastle(context)) {
            availableChoices.add(ACTION_PUT_OWN_CASTLE);
        }
  

        // Pick a random action


        Random r = new Random();
        
        int index = r.nextInt(availableChoices.size());
        int action = availableChoices.get(index);

        if (action == ACTION_PUT_OWN_TILE) {
            // Draw tile to put on board
            BoardPos bp = findRandomEmptySlotOnBoard(context);
            //check which tile to place on the board
                  
                  //  Tile t=selectTileToPlaceOnboard(bp.row, bp.col,context);
                    //place the selectec tile on board
                    // context.getBoard().setPlacable(t,bp.row, bp.col);
            
            
          context.placePlayerTileOnBoard(bp.row, bp.col);
            
            if(!tileStackIsEmpty(context) && context.getPlayer().getNumTiles()<context.getPlayer().PLAYER_NUM_TILES)
                    {
                        context.playerDrawsNewTileFromTileStack();
                    }

        } else if (action == ACTION_PUT_OWN_CASTLE) {
            // Put castle onto board
            List<String> choices = new ArrayList<String>();
            if (playerHasOwnCastle(context, Castle.RANK_1)) {
                choices.add(Castle.RANK_1);
            }
            if (playerHasOwnCastle(context, Castle.RANK_2)) {
                choices.add(Castle.RANK_2);
            }
            if (playerHasOwnCastle(context, Castle.RANK_3)) {
                choices.add(Castle.RANK_3);
            }
            if (playerHasOwnCastle(context, Castle.RANK_4)) {
                choices.add(Castle.RANK_4);
            }
            Random rc = new Random();
            int indexC = r.nextInt(choices.size());
            String castleType = choices.get(indexC);

            BoardPos bp = findRandomEmptySlotOnBoard(context);
            context.placePlayerCastleOnBoard(castleType, bp.row, bp.col);

        } 
    }
}
