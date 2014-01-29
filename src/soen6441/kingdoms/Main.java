/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import soen6441.kingdoms.core.Board;
import soen6441.kingdoms.core.Castle;
import soen6441.kingdoms.core.GameState;
import soen6441.kingdoms.core.Placeable;
import soen6441.kingdoms.strategy.MaximizeStrategy;
import soen6441.kingdoms.strategy.MinimizeStrategy;
import soen6441.kingdoms.strategy.MoveStrategy;
import soen6441.kingdoms.strategy.NewStrategy;
import soen6441.kingdoms.core.Player;
import soen6441.kingdoms.strategy.RandomStrategy;
import soen6441.kingdoms.core.Serializer;
import soen6441.kingdoms.core.Tile;
import soen6441.kingdoms.io.ConsoleUtils;
import soen6441.kingdoms.io.FileUtils;
import soen6441.kingdoms.strategy.HumanStrategy;

/**
 * Main class of the application
 */
public class Main {
    
    static Map<String, MoveStrategy> strategyPool = new Hashtable<String, MoveStrategy>();
         static  int randomDisasterTime;
         static int currentTime;

    static {
        strategyPool.put(RandomStrategy.class.getName(), new RandomStrategy());
        strategyPool.put(MaximizeStrategy.class.getName(), new MaximizeStrategy());
        strategyPool.put(MinimizeStrategy.class.getName(), new MinimizeStrategy());
        strategyPool.put(NewStrategy.class.getName(), new NewStrategy());
        strategyPool.put(HumanStrategy.class.getName(), new HumanStrategy());
    }
    
    static MoveStrategy getStrategy(String name) {
        return strategyPool.get(name);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        GameState state = null;
        //set a random time for the disaster to occur
        Random r=new Random();
       randomDisasterTime= r.nextInt((GameState.MAX_EPOCH*Board.NUM_ROWS*Board.NUM_COLS)/4);
       System.out.println("randomDisasterTime"+randomDisasterTime);
       currentTime=0;

        printStartupMenu();
        int mainChoice = ConsoleUtils.promptUserInteger(1);
        if (mainChoice == 2) {
            System.out.println("Load game from file (default: savedgame.xml)");
            String filename = ConsoleUtils.promptUserString("savedgame.xml");
            String xml = FileUtils.readTextFile(new File(filename));
            state = Serializer.getInstance().fromXML(xml);
            System.out.println("Game loaded from " + filename);
        } 
        else 
        {
            int numPlayers = 4;
            int epoch = 1;

            System.out.println("Please enter number of players:");
            numPlayers = ConsoleUtils.promptUserInteger(numPlayers);

            epoch = 1;

            state = new GameState(numPlayers);
            state.setup(epoch);
        }
        
        chooseStrategyForPlayers(state);

        boolean exit = false;
        boolean loop = true;
        do {
            printTurnInfo(state);
            printDebugMenu();
            int choice = ConsoleUtils.promptUserInteger(0);
            switch (choice) {
                case 0:
                    loop = false;
                    exit = true;
                    break;
                case 1:
                    System.out.println(state);
                    break;
                case 2: {
                    System.out.println("Save game to file (default: savedgame.xml)");
                    String filename = ConsoleUtils.promptUserString("savedgame.xml");
                    FileUtils.writeTextFile(new File(filename), Serializer.getInstance().toXML(state));
                    System.out.println("Game saved to " + filename);
                }
                break;
                case 3: {
                    System.out.println("Load game from file (default: savedgame.xml)");
                    String filename = ConsoleUtils.promptUserString("savedgame.xml");
                    String xml = FileUtils.readTextFile(new File(filename));
                    state.cleanup();
                    state = Serializer.getInstance().fromXML(xml);
                    System.out.println("Game loaded from " + filename);
                    chooseStrategyForPlayers(state);
                }
                break;
                case 4:
                    loop = takesTurn(state);
                    exit = state.isGameEnded();
                    break;
                case 5: {
                    do {
                        loop = takesTurn(state);
                        if (!loop) {
                            exit = state.isGameEnded();
                            break;
                        }
                        if (state.getCurrentPlayerId() == state.getStartingPlayerId()) {
                            break;
                        }
                    } while (true);
                }
                break;
                case 6: {
                    do {
                        loop = takesTurn(state);
                        if (!loop) {
                            exit = state.isGameEnded();
                            break;
                        }
                    } while (true);
                }
                break;
                case 7: {
                    do {
                        takesTurn(state);
                        if (state.isGameEnded()) {
                            loop = false;
                            exit = true;
                            break;
                        }
                    } while (true);
                }
                break;
                default:
                    break;
            }
        } while (!exit);
        
        state.cleanup();
    }
    
    private static void chooseStrategyForPlayers(GameState state) {
        System.out.println("1. Random Strategy");
        System.out.println("2. Maximize Strategy");
        System.out.println("3. Minimize Strategy");
        System.out.println("4. Max/Min Strategy");
        System.out.println("5. Human Strategy");

        List<Player> players = state.getPlayers();
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player p = i.next();
            System.out.println("Please choose a strategy for player " + p.getID());
            int strategy = ConsoleUtils.promptUserInteger(1);
            String sname = RandomStrategy.class.getName();
            if (strategy == 2) {
                sname = MaximizeStrategy.class.getName();
            } else if (strategy == 3) {
                sname = MinimizeStrategy.class.getName();
            } else if (strategy == 4) {
                sname = NewStrategy.class.getName();
            } else if (strategy == 5) {
                sname = HumanStrategy.class.getName();
            }
            p.setStrategy(getStrategy(sname));
        }
    }

     private static boolean checkEpochEnds(GameState state) {
        boolean epochEnds = false;
        
        if (state.getBoard().isFull()) {
            System.out.println("Board full!");
            epochEnds = true;
            
        } else {
            List<Player> players1 = state.getPlayers();
            Iterator<Player> j = players1.iterator();
            while (j.hasNext()) {
                Player p = j.next();
                int score = p.getCoinsValue() + state.getBoard().calculateScoreForPlayer(p.getID());
                if (score >= GameState.GOLD_LIMIT) {
                    System.out.println("Player " + p.getID() + " reached gold limit!");
                    System.out.println("\nCONGRATULATIONS PLAYER "+p.getID()+". YOU WIN!!!!");
                    epochEnds = true;
                }
            }
        }
        
        if (epochEnds) {
            int currentEpoch = state.getCurrentEpoch();
            System.out.println("\n=== EPOCH " + currentEpoch + " ENDED ===");
            System.out.println(state);
            List<Player> players = state.getPlayers();
            Iterator<Player> i = players.iterator();
            while (i.hasNext()) {
                Player p = i.next();
                int score = state.getBoard().calculateScoreForPlayer(p.getID());
                int actual = score;
                if (score < 0
                        && (p.getCoinsValue() + score) < 0) {
                    actual = -1 * p.getCoinsValue();
                }
                p.setCoinsValue(p.getCoinsValue() + actual);
                state.setBankCoinsValue(state.getBankCoinsValue() - actual);
                if (score > 0) {
                    System.out.println("Player " + p.getID() + " (score for this epoch: " + score + ") collected " + actual + " coins from the bank. Total so far " + p.getCoinsValue() + " coins.");
                } else {
                    System.out.println("Player " + p.getID() + " (score for this epoch: " + score + ") paid " + (actual * -1) + " coins to the bank. Total so far " + p.getCoinsValue() + " coins.");
                }
            } 
            
            //added just for the sake of checking
            if((currentEpoch+1)!=7)
            state.setup(currentEpoch + 1);
            
        } else {
            state.nextTurn();
        }
            
        return epochEnds;
    }

    private static boolean takesTurn(GameState state) {
        Player currentPlayer = state.getPlayer();
        currentPlayer.performMove(state);
       //set and check current time and disaster time
       currentTime++;
       //check if disater has occured
       if(currentTime==randomDisasterTime) {
           System.out.println("disaster has occurred");
           
          //// try {
               disasterHasoccured(state);
          // } catch (Exception ex) {               
          // }
       }
           
        if (checkEpochEnds(state)) {
            return false;
        }
        return true;
    }

    private static void printStartupMenu() {
        System.out.println("                 |ZZzzz");
        System.out.println("                 |");
        System.out.println("                 |");
        System.out.println("    |ZZzzz      /^\\            |ZZzzz");
        System.out.println("    |          |~~~|           |");
        System.out.println("    |        |^^^^^^^|        / \\");
        System.out.println("   /^\\       |[]+    |       |~~~|");
        System.out.println("|^^^^^^^|    |    +[]|       |   |");
        System.out.println("|    +[]|/\\/\\/\\/\\^/\\/\\/\\/\\/|^^^^^^^|");
        System.out.println("|+[]+   |~~~~~~~~~~~~~~~~~~|    +[]|");
        System.out.println("|       |  []   /^\\   []   |+[]+   |");
        System.out.println("|   +[]+|  []  || ||  []   |   +[]+|");
        System.out.println("|[]+    |      || ||       |[]+    |");
        System.out.println("|_______|------------------|_______|");
        System.out.println("\nWelcome to KingdomsGame - By SOEN6441 (Winter 2013) Team F");
        System.out.println("1. Start a new game");
        System.out.println("2. Load a saved game");
    }

    private static void printTurnInfo(GameState state) {
        System.out.println("\n== Turn's Info ==");
        System.out.println("Epoch " + state.getCurrentEpoch() + ", Current player " + state.getPlayer().toString());
        List<Player> players = state.getPlayers();
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player p = i.next();
            int score = state.getBoard().calculateScoreForPlayer(p.getID());
            System.out.println("Player " + p.getID() + " 's score " + score + " for current epoch");
        }
    }

    private static void printDebugMenu() {
        System.out.println("\n== Game Menu ==");
        System.out.println("0. EXIT");
        System.out.println("1. Print game state");
        System.out.println("2. Save game state to file");
        System.out.println("3. Load game state from file");
        System.out.println("4. Ask current player to take its turn");
        System.out.println("5. Ask players to take all their turns to complete 1 round");
        System.out.println("6. Ask players to take all their turns until current epoch is ended");
        System.out.println("7. Ask players to take all their turns until game is ended");
    }

    private static void disasterHasoccured(GameState state) {
        GameState currentState=state;
        Board board=currentState.getBoard();
        Placeable pl;
        //select a randon row
        Random r =new Random();
        int randomRow=r.nextInt(5);
        int randomCol=r.nextInt(6);
        int randomWidth=0;
        
                int randomHeigth=0;

        boolean found=false;
        
        boolean specialCondition=true;
        while(specialCondition)
        {
            
        
        while(!found)
        {
            randomWidth=r.nextInt(5);
        if(randomWidth>=2&& randomWidth<=4)
        {
            
        found=true;
        
        }
        }
        
        //select a random col
        found=false;
        while(!found)
        {
            randomHeigth=r.nextInt(5);
        if(randomHeigth>=2&& randomHeigth<=4)
        {
            
        found=true;
        
        }}
        if(!(randomRow==2&&randomHeigth==4))
        {
            specialCondition=false;
            System.out.println("not Special Condition");
           
        }
         found=false;
        }
        System.out.println("random row"+randomRow+"random col"+randomCol+" random width"+randomWidth+"random height"+randomHeigth);
        int playerID;
        Castle c;
        Player player_castle;
        int offsetRow=0;
        int offsetCol=0;
        if((randomRow+randomHeigth)>5)
        {
         offsetRow=-1;
         
        }
        else
        {  offsetRow=+1;}
        
        if((randomCol+randomWidth)>6)
        { 
           offsetCol=-1;
            
        }
        else
            offsetCol=+1;
        
        
        for(int i=0;i<randomHeigth;i++)
        {    
        for(int j=0;j<randomWidth;j++)
        {
                pl=board.slots[randomRow+(offsetRow)*i][randomCol+(offsetCol)*j]; 
                board.slots[randomRow+(offsetRow)*i][randomCol+(offsetCol)*j]=null;
            if(pl instanceof Tile)
            {
                Tile t=(Tile)pl;
                if(!board.isDragonTile(t)&&!board.isWizardTile(t)&&!board.isGoldMineTile(t))
                {state.addTileToTilestack((Tile)pl); } 
            }
            if(pl instanceof Castle)
            {
                c= (Castle)pl;
             playerID=  c.getplayerId();
              player_castle=state.getPlayer(playerID);
              player_castle.getCastles().add(c);
        }
        }
        }
        
        
        
        
        //traverse the square
        //if tile return to tile stack
        //if castle return to player
    }
}
