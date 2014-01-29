/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

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
public class TileStackTest {
    
    public TileStackTest() {
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
    public void testDrawTile() {
        
        System.out.println("testing tile is drawn");
        GameState state = new GameState(2);
        state.setup(1);
        
        
        Tile tile = state.getTileStack().drawTile();
        if(tile!=null)
        {
            boolean result= (tile instanceof Tile);
            assertTrue(result==true);
                    
                    }
    }
}
