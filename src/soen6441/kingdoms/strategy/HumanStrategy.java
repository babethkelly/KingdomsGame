/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.strategy;

import java.util.List;
import soen6441.kingdoms.core.Castle;
import soen6441.kingdoms.core.Tile;
import soen6441.kingdoms.io.ConsoleUtils;



/**
 * Implement the strategy for Human player
 */
public class HumanStrategy extends MoveStrategy {

    /**
     * Call by the framework to carry out the action for a given turn
     *
     * @param context Context for a player to take a turn
     */
    public void performAction(MoveContext context) {
        boolean loop = true;
        do {
            printMenu(context);
            int choice = ConsoleUtils.promptUserInteger(0);
            switch (choice) {
                case 0: {
                    System.out.println(context.getPlayer() + "\n" + context.getBoard());
                }
                break;
                case 2: {
                    System.out.println("Please provide castle rank:");
                    int r = ConsoleUtils.promptUserInteger(1);
                    String castleType = "";
                    if (r == 1) {
                        castleType = Castle.RANK_1;
                    } else if (r == 2) {
                        castleType = Castle.RANK_2;
                    } else if (r == 3) {
                        castleType = Castle.RANK_3;
                    } else if (r == 4) {
                        castleType = Castle.RANK_4;
                    }
                    System.out.println("Please provide row index:");
                    int x = ConsoleUtils.promptUserInteger(0);
                    System.out.println("Please provide column index:");
                    int y = ConsoleUtils.promptUserInteger(0);
                    if (context.placePlayerCastleOnBoard(castleType, x, y)) {
                        loop = false;
                    } else {
                        System.out.println("ERROR: You do not have any castle with rank " + r + "OR Board not empty at (" + x + ", " + y + ")");
                    }
                }
                break;
                case 1: {  
                    System.out.println("Please provide row index:");
                    int x = ConsoleUtils.promptUserInteger(0);
                    System.out.println("Please provide column index:");
                    int y = ConsoleUtils.promptUserInteger(0);
                    
                    int t = -1;
                    List<Tile> tiles = context.getPlayer().getTiles();
                    if (tiles.size() > 0) {
                        System.out.println("Please provide tile to place:");
                        for (int i = 0; i < tiles.size(); i++) {
                            System.out.println((i + 1) + ". " + tiles.get(i).getType() + ", value " + tiles.get(i).getValue());
                        }
                        t = ConsoleUtils.promptUserInteger(1) - 1;                        
                    }
                    
                    boolean ret = true;
                    if (t >= 0) {
                        ret = context.placeSpecificPlayerTileOnBoard(tiles.get(t).getType(), tiles.get(t).getValue(), x, y);
                    }
                    if (ret) {
                        loop = false;
                        if (!tileStackIsEmpty(context) && context.getPlayer().getNumTiles() < context.getPlayer().PLAYER_NUM_TILES) {
                            context.playerDrawsNewTileFromTileStack();
                        }
                    } else {
                        System.out.println("ERROR: Board not empty at (" + x + ", " + y + ")");
                    }
                }
                break;
                default:
                    break;
            }
        } while (loop);
    }

    private static void printMenu(MoveContext context) {
        System.out.println("\n== Actions for Human player (ID=" + context.getPlayer().getID() + ") ==");
        System.out.println("0. Print state of player and the board");
        System.out.println("1. Put tile onto the board");
        System.out.println("2. Put own castle onto the board");
    }
}
