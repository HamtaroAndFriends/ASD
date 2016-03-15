/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.State;
import model.Transition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrea
 */
public class ControllerAutomaTest {
    
    private Automa automa;
    
    /**
     *
     */
    public ControllerAutomaTest() throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example2.xml");
        automa = (Automa) unmarshaller.unmarshal(file);
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
     * Test of isVivo method, of class ControllerAutoma.
     */
    @Test
    public void testIsVivo() {
        System.out.println("isVivo");
        Automa daControllare = automa;
        ControllerAutoma instance = new ControllerAutoma();
        boolean expResult = false;
        boolean result = instance.isVivo(daControllare);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of statiTransizioni method, of class ControllerAutoma.
     */
    @Test
    public void testStatiTransizioni() {
        System.out.println("statiTransizioni");
        List<Transition> t = null;
        int iF = 0;
        ControllerAutoma instance = new ControllerAutoma();
        List<State> expResult = null;
        List<State> result = instance.statiTransizioni(t, iF);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of confrontoStatiIniziali method, of class ControllerAutoma.
     */
    @Test
    public void testConfrontoStatiIniziali() {
        System.out.println("confrontoStatiIniziali");
        List<State> s1 = null;
        List<State> s2 = null;
        ControllerAutoma instance = new ControllerAutoma();
        boolean expResult = false;
        boolean result = instance.confrontoStatiIniziali(s1, s2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of raggiungoOsservabile method, of class ControllerAutoma.
     */
    @Test
    public void testRaggiungoOsservabile() {
        System.out.println("raggiungoOsservabile");
        List<Transition> guasti = null;
        List<State> sIO = null;
        ControllerAutoma instance = new ControllerAutoma();
        boolean expResult = false;
        boolean result = instance.raggiungoOsservabile(guasti, sIO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
