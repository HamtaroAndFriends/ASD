/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
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
    public void testGetSyncTwin() throws JAXBException
    {
        Automa bad = controller.getBadTwin(automa, 1);
        Automa good = controller.getGoodTwin(bad);
        SyncAutoma sync = controller.getSyncTwin(bad, good);
        
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        marshaller.marshal(sync, System.out);
    }
    
}
