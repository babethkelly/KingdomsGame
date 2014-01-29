/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import soen6441.kingdoms.io.FileUtils;

/**
 *
 * @author PCUser
 */
public class BoardTest {
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetPlaceableTile() 
    {
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
        int expected=0;
        System.out.println("testing placing tile at particular slot");
        GameState state = new GameState(2);
        state.setup(1);
        
        
        Tile tile = state.getTileStack().drawTile();
                    if (tile != null) {
                        
                        assertTrue(state.getBoard().setPlacable(tile, 0, 0)==expected);
                    }
    }
    
     @Test
    public void testSetPlaceableCastle()
     {
         int expected=0;
         System.out.println("testing placing castle at particular slot");
        GameState state = new GameState(2);
        state.setup(1);
         Player player = state.getPlayer(1);
          if (player != null && player.getCastles().size() > 0) 
          {
                        Castle c = player.getCastles().remove(0);
                        assertTrue(state.getBoard().setPlacable(c, 0, 0)==expected);
                        
                    } 
          else 
          {
                        System.out.println("Error: Invalid player ID or player does not have any castle left");
          }
     
     }
     
     
      @Test
    public void testSetPlaceableTileAndCastle()
     {
         int expected=1;
         System.out.println("testing placing castle at already filled slot ");
        GameState state = new GameState(2);
        state.setup(1);
         Player player = state.getPlayer(1);
          if (player != null && player.getCastles().size() > 0) 
          {
                        Castle c = player.getCastles().remove(0);
                        state.getBoard().setPlacable(c,0,0);
                        Castle c2 = player.getCastles().remove(0);
                        assertTrue(state.getBoard().setPlacable(c2, 0, 0)==expected);
                         Tile tile = state.getTileStack().drawTile();
                         System.out.println("testing placing tile at already filled slot ");
                        assertTrue(state.getBoard().setPlacable(tile, 0, 0)==expected);
                        
                    } 
          else 
          {
                        System.out.println("Error: Invalid player ID or player does not have any castle left");
          }
     
     }
      
     @Test 
     public void testCalculateScore() {
         String xml = FileUtils.readTextFile(new File("testboard.xml"));                  
         Board board = (Board)Serializer.getInstance().objectFromXML(xml);
         System.out.println("Board to test:\n" + board.toString());
         int player1Score = board.calculateScoreForPlayer(1);
         int player2Score = board.calculateScoreForPlayer(2);
         int player3Score = board.calculateScoreForPlayer(3);
         int player4Score = board.calculateScoreForPlayer(4); 
         System.out.println("player1Score=" + player1Score);
         System.out.println("player2Score=" + player2Score);
         System.out.println("player3Score=" + player3Score);
         System.out.println("player4Score=" + player4Score);
         assertEquals(33, player1Score);
         assertEquals(-12, player2Score);
         assertEquals(26, player3Score);
         assertEquals(50, player4Score);
     }

     @Test 
     public void testCalculateScore2() {
         String xml = FileUtils.readTextFile(new File("testboard2.xml"));                  
         Board board = (Board)Serializer.getInstance().objectFromXML(xml);
         System.out.println("Board to test:\n" + board.toString());
         int player1Score = board.calculateScoreForPlayer(1);
         int player2Score = board.calculateScoreForPlayer(2);
         int player3Score = board.calculateScoreForPlayer(3);
         int player4Score = board.calculateScoreForPlayer(4); 
         System.out.println("player1Score=" + player1Score);
         System.out.println("player2Score=" + player2Score);
         System.out.println("player3Score=" + player3Score);
         System.out.println("player4Score=" + player4Score);
         assertEquals(94, player1Score);
         assertEquals(42, player2Score);
         assertEquals(32, player3Score);
         assertEquals(27, player4Score);
     }

     @Test 
     public void testCalculateScore3() {
         String xml = FileUtils.readTextFile(new File("rulebookboard.xml"));                  
         Board board = (Board)Serializer.getInstance().objectFromXML(xml);
         System.out.println("Board to test:\n" + board.toString());
         int player1Score = board.calculateScoreForPlayer(1);
         int player2Score = board.calculateScoreForPlayer(2);
         int player3Score = board.calculateScoreForPlayer(3);
         int player4Score = board.calculateScoreForPlayer(4); 
         System.out.println("player1Score=" + player1Score);
         System.out.println("player2Score=" + player2Score);
         System.out.println("player3Score=" + player3Score);
         System.out.println("player4Score=" + player4Score);
         assertEquals(44, player1Score);
         assertEquals(3, player2Score);
         assertEquals(53, player3Score);
         assertEquals(0, player4Score);         
     }
}
         
     
    

