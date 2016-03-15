/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.json;

import controller.js.ControllerJSON;
import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
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
public class ControllerJSONTest {
    
    private Automa automa;
    private ControllerJSON controller;
    
    public ControllerJSONTest() throws JAXBException 
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        automa = (Automa) unmarshaller.unmarshal(file);
        controller = new ControllerJSON();
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
     * Test of getStateJSON method, of class ControllerJSON.
     */
    @Test
    public void testGetStateJSON()
    {
        //System.out.println(controller.getStateJSON(automa));
        
    }

    /**
     * Test of getTransitionJSON method, of class ControllerJSON.
     */
    @Test
    public void testGetTransitionJSON() 
    {
        //System.out.println(controller.getTransitionJSON(automa));
    }
    
    /**
     * Test of getTransitionJSON method, of class ControllerJSON.
     */
    @Test
    public void testGetAutomaJSON() 
    {
        System.out.println(controller.getAutomaJSON(automa));
    }
    
    @Test
    public void createAutomaView() throws FileNotFoundException
    {
        controller.createAutomaView(automa, "test\\controller\\json");
    }
    
}
