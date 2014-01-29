/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import soen6441.kingdoms.io.FileUtils;

/**
 * Unit tests for GameState class
 */
public class GameStateTest {   
    /**
     * Test of constructor, of class GameState.
     */
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void testConstructorInvalidNumPlayers() {
        System.out.println("constructor with invalid num players");
        GameState state = new GameState(1);
    }

    /**
     * Test of setup method, of class GameState.
     */
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void testSetupInvalid() {
        System.out.println("setup with invalid epic");
        GameState state = new GameState(2);
        state.setup(2);
    }
    
    /**
     * Test of setup method, of class GameState.
     */
    @Test
    public void testSetup2Players() {
        System.out.println("setup with 2 players");
        GameState state = new GameState(2);
        state.setup(1);
        assertTrue(state.getNumPlayers() == 2);
        testSetupHelper(state, 4, 979);
    }

    /**
     * Test of setup method, of class GameState.
     */
    @Test
    public void testSetup3Players() {
        System.out.println("setup with 3 players");
        GameState state = new GameState(3);
        state.setup(1);
        assertTrue(state.getNumPlayers() == 3);
        testSetupHelper(state, 3, 929);
    }

    /**
     * Test of setup method, of class GameState.
     */
    @Test
    public void testSetup4Players() {
        System.out.println("setup with 4 players");
        GameState state = new GameState(4);
        state.setup(1);
        assertTrue(state.getNumPlayers() == 4);
        testSetupHelper(state, 2, 879);
    }
    
    /**
     * Test save/load of class GameState.
     */
    @Test
    public void testSaveLoad() {
        System.out.println("Save to / load from file");
        GameState stateSave = new GameState(4);
        stateSave.setup(1);
        FileUtils.writeTextFile(new File("testsave.xml"), Serializer.getInstance().toXML(stateSave));

        String xml = FileUtils.readTextFile(new File("testsave.xml"));
        GameState stateLoad = Serializer.getInstance().fromXML(xml);
        assertTrue(stateLoad.getNumPlayers() == 4);
        testSetupHelper(stateLoad, 2, 879);        
    }
    
    private void testSetupHelper(GameState state, int numRank1CastlesPerPlayer, int bankCoinsValue) {
        List<Player> players = state.getPlayers();
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player p = i.next();
            if (p.getCoinsValue() != 50) {
                fail("Each player should have 50 coins value");
            }
            if (p.getNumCastlesByRank(Castle.RANK_1) != numRank1CastlesPerPlayer) {
                fail("Each player should have " + numRank1CastlesPerPlayer + " rank 1 castles");
            }
            if (p.getNumCastlesByRank(Castle.RANK_2) != 3) {
                fail("Each player should have 3 rank 2 castles");
            }
            if (p.getNumCastlesByRank(Castle.RANK_3) != 2) {
                fail("Each player should have 2 rank 3 castles");
            }
            if (p.getNumCastlesByRank(Castle.RANK_4) != 1) {
                fail("Each player should have 1 rank 4 castles");
            }
            if (p.getNumTiles() !=3) {
                fail("Each player should draw 3  tiles and keep them");
            }
        }    
        
        if (state.getBankCoinsValue() != bankCoinsValue) {
            fail("Bank coins value after distribute to players should be " + bankCoinsValue);            
        }
    }
}
