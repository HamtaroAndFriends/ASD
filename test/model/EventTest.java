/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Samuele Colombo
 */
public class EventTest {
    private Event a, b;
    private Event ab, ba;
    
    public EventTest() 
    {
        a = new Event();
        a.getEvents().add("a");
        b = new Event();
        b.getEvents().add("b");
        ab = new Event();
        ab.getEvents().add("a");
        ab.getEvents().add("b");
        ba = new Event();
        ba.getEvents().add("b");
        ba.getEvents().add("a");
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
     * Test of equals method, of class Event.
     */
    @Test
    public void testEquals()
    {
        assertEquals(a, a);
        //assertEquals(b, a);
        assertEquals(ba, ba);
        assertEquals(ba, ab);
        
    }
    
}
