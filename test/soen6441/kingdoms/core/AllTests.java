/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author PCUser
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({soen6441.kingdoms.core.BoardTest.class,
                     
                     soen6441.kingdoms.core.GameStateTest.class,
                     soen6441.kingdoms.core.PlayerTest.class,
                     soen6441.kingdoms.core.TileStackTest.class})
public class AllTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
