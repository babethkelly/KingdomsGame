/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PCUser
 */
public class PlayerTest {
    
    public PlayerTest() {
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

  
    /**
     * Test of getCastles method, of class Player.
     */
    @Test
    public void testGetCastles() {
        System.out.println("getCastles");
        GameState state = new GameState(2);
        state.setup(1);
         Player player = state.getPlayer(1);
        Collection expResult = null;
        Collection result = player.getCastles();
        assertFalse(expResult==result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test for placeCastleOnBoard method
     */
    @Test
    public void testPlaceCastleOnBoard() {
        Player player = new Player(1);
        player.getCastles().add(new Castle(Castle.RANK_1, 1));
        player.getCastles().add(new Castle(Castle.RANK_2, 1));
        player.getCastles().add(new Castle(Castle.RANK_2, 1));
        player.getCastles().add(new Castle(Castle.RANK_3, 1));
        Board board = new Board();
        assertTrue(player.placeCastleOnBoard(Castle.RANK_2, board, 1, 1));
        if (player.getNumCastlesByRank(Castle.RANK_2) != 1) {
            fail("Number of rank 2 castle should be 1");
        }        
    }
}
