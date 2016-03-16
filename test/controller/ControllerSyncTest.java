/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.js.ControllerJSON;
import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.sync.SyncAutoma;
import model.sync.SyncState;
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
public class ControllerSyncTest {
    
    private Automa automa;
    private ControllerTwin controller; 
    
    public ControllerSyncTest() throws JAXBException 
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        automa = (Automa) unmarshaller.unmarshal(file);
        controller = new ControllerTwin();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() 
    {
        
    }
    
    @After
    public void tearDown() 
    {
        
    }
    /**
     * Test of getSyncTwin method, of class ControllerTwin.
     */
    @Test
    public void testGetSyncTwin() throws JAXBException, FileNotFoundException
    {
        Automa bad1 = controller.getBadTwin(automa, 1);
        Automa bad2 = controller.getBadTwin(bad1, 2);
        Automa bad3 = controller.getBadTwin(bad2, 3);
        Automa good3 = controller.getGoodTwin(bad3);
        //Automa good2 = controller.getGoodTwin(bad2);
        //Automa good1 = controller.getGoodTwin(bad1);
        SyncAutoma sync = controller.getSyncTwin(bad3, good3);
        
        ControllerJSON cj = new ControllerJSON();
        cj.createAutomaView(sync, "test/controller/json");
        
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        //marshaller.marshal(sync, System.out);
    }
    
}
