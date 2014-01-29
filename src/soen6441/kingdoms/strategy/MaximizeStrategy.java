/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import soen6441.kingdoms.core.BoardPos;
import soen6441.kingdoms.core.Castle;
import soen6441.kingdoms.core.Placeable;
import soen6441.kingdoms.core.Player;
import soen6441.kingdoms.core.Tile;

/**
 * Implement strategy that maximize score
 */
public class MaximizeStrategy extends MoveStrategy {
    

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



         
         
          Iterator<Placeable> k = listOfPlaceables.iterator();
          int finalMax=-10000;
          int tempMax;
          int tempScore=0;
 while (k.hasNext())
        {
            Placeable p= k.next();
            tempMax=-10000;
            for(int l=0;l<bp.length;l++)
            {
                
                
                tempScore=calculateScoreForPlaceable(currentPlayer.getID(),context,p,bp[l].row,bp[l].col);
               
                if(tempMax<=tempScore)
                {
                    tempMax=tempScore;
                    tempSelectedPlaceable=p;
         
         tempSelectedSlotRow=bp[l].row;
         tempSelectedSlotCol=bp[l].col;
                    
                    
                }
                
            }
            if(finalMax<=tempMax)
            {
                
            finalMax=tempMax;
            selectedPlaceable=tempSelectedPlaceable;
         
         SelectedSlotRow=tempSelectedSlotRow;
         SelectedSlotCol=tempSelectedSlotCol;
            
                
            }
        }
 
 
  if(selectedPlaceable instanceof Castle)
            {
                System.out.println("castle selected");
                Castle c=(Castle)selectedPlaceable;
                
                 context.placePlayerCastleOnBoard(c.getType(),SelectedSlotRow,SelectedSlotCol);
            }
            else if (selectedPlaceable instanceof Tile)
                    {
                         System.out.println(" tile selected"); 
                       Tile t=(Tile)selectedPlaceable;
                     context.placeSpecificPlayerTileOnBoard(t.getType(),t.getValue(),SelectedSlotRow,SelectedSlotCol);
                     if(!tileStackIsEmpty(context) && context.getPlayer().getNumTiles()<Player.PLAYER_NUM_TILES)
                    {
                        context.playerDrawsNewTileFromTileStack();
                    }
                       
                    }
  
   
   
            }  

    
     }   
        
       

   
       
        
    }
    
        
    
        
