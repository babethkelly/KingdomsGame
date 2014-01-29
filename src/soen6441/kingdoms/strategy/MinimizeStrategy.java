/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.strategy;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
 * Implement strategy that minimize score of others
 */
public class MinimizeStrategy extends MoveStrategy {

    /**
     * Call by the framework to carry out the action for a given turn
     * @param context Context for a player to take a turn
     */
    public void performAction(MoveContext context) {
        
            //create a list of placeables made of tiles and castles that the user can place on board
            ArrayList<Placeable> listOfPlaceables =new ArrayList<Placeable>();

            //retrieving the list of tiles and adding them to the placeables
        Player currentPlayer=context.getPlayer(); 
        List<Tile> playerTiles;
        playerTiles = new ArrayList<Tile>();
        playerTiles=currentPlayer.getTiles();
        Iterator<Tile> i = playerTiles.iterator();
        
        while (i.hasNext())
        {               


            Tile t= i.next();
            listOfPlaceables.add(t);
            
        }
                    //retrieving the list of castles and adding them to the placeables
        List<Castle> playerCastles=new ArrayList<Castle>();
        playerCastles=currentPlayer.getCastles();
        
        Iterator<Castle> j = playerCastles.iterator();
         while(j.hasNext())
         {
             //  System.out.println("castles"+playerCastles.size());
            Castle c= j.next();
            listOfPlaceables.add(c);
         }
         
         
          //retrieve list of empty slots on board
            BoardPos[] bp = getAllEmptySlotsOnBoard(context);
            if(bp!=null)
            {
         
            Placeable tempSelectedPlaceable=null;
         Placeable selectedPlaceable=null;
         int tempSelectedSlotRow=0;
         int tempSelectedSlotCol=0;
         int SelectedSlotRow=0;
         int SelectedSlotCol=0;
          Placeable realSelectedPlaceable=null;
       
         int realTempSelectedSlotCol=0;
         int realSelectedSlotRow=0;
         int realSelectedSlotCol=0;



//get the list of all players
         List<Player> allPlayers=context.getPlayers();
         Iterator<Player> x=allPlayers.iterator();
        //create a list of the others players excluding current player to minimize their score
         List<Player> numOtherPlayers=new ArrayList<Player>();
       
         
         while(x.hasNext())
         {
         Player pl=x.next();
         if(pl.getID()!= currentPlayer.getID())
         { 
           numOtherPlayers.add(pl);  
         }
         }
         //for each player, for each empty slot and for each available placeable,calculate the minimum score

         
       Iterator<Player> y= numOtherPlayers.iterator();
       Iterator<Placeable> k = listOfPlaceables.iterator();

       int realMin=100000;
       int finalMin=100000;
       int tempMin;
          int tempScore=0;
       
         while(y.hasNext())
         {
          Player otherPlayer=y.next();
         
         
         
          finalMin=100000;
          
         
 while (k.hasNext())
        {
            Placeable p= k.next();
            tempMin=100000;
            for(int l=0;l<bp.length;l++)
            {
                
                
                tempScore=calculateScoreForPlaceable(otherPlayer.getID(),context,p,bp[l].row,bp[l].col);
               
                if(tempMin>=tempScore)
                {
                    tempMin=tempScore;
                    tempSelectedPlaceable=p;
         
         tempSelectedSlotRow=bp[l].row;
         tempSelectedSlotCol=bp[l].col;
                    
                    
                }
                
            }
            if(finalMin>=tempMin)
            {
                
            finalMin=tempMin;
            selectedPlaceable=tempSelectedPlaceable;
         
         SelectedSlotRow=tempSelectedSlotRow;
         SelectedSlotCol=tempSelectedSlotCol;
            
                
            }
        }
 if(realMin>=finalMin)
 {
     realMin=finalMin;
            realSelectedPlaceable=selectedPlaceable;
         
        realSelectedSlotRow=SelectedSlotRow;
         realSelectedSlotCol=SelectedSlotCol;
 }
         }
 
 //place the placeable,,depending on if it is a castle or tile
  if(realSelectedPlaceable instanceof Castle)
            {
                System.out.println("castle selected");
                Castle c=(Castle)realSelectedPlaceable;
                
                 context.placePlayerCastleOnBoard(c.getType(),realSelectedSlotRow,realSelectedSlotCol);
            }
            else if (realSelectedPlaceable instanceof Tile)
                    {
                         System.out.println(" tile selected"); 
                       Tile t=(Tile)realSelectedPlaceable;
                     context.placeSpecificPlayerTileOnBoard(t.getType(),t.getValue(),realSelectedSlotRow,realSelectedSlotCol);
                     if(!tileStackIsEmpty(context) && context.getPlayer().getNumTiles()<Player.PLAYER_NUM_TILES)
                    {
                        context.playerDrawsNewTileFromTileStack();
                    }
                       
                    }
  
   
   
            }  

    
   
        
    }

    
       
        
    }

    


   