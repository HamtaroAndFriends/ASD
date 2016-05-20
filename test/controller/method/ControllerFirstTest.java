/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.Container;
import model.sync.SyncAutoma;
import model.sync.SyncTransition;
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
public class ControllerFirstTest {
    
    private Automa automa;
    private ControllerFirst controllerFirst;
    
    public ControllerFirstTest() throws JAXBException 
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        automa = (Automa) unmarshaller.unmarshal(file);
        controllerFirst = new ControllerFirst();
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
     * Test of performFirstMethod method, of class ControllerFirst.
     */
    @Test
    public void testPerformFirstMethod() throws JAXBException 
    {
        Container container = new Container();
        container.setAutoma(automa);
        
        int result = controllerFirst.performFirstMethod(container, 3);
        
        System.out.println(result);
    }

    /**
     * Test of isFollowedByAnEndlessLoop method, of class ControllerFirst.
     */
    @Test
    public void testIsFollowedByAnEndlessLoop_SyncAutoma() throws FileNotFoundException, JAXBException
    {
        SyncAutoma automa = service.ServiceXML.readSyncAutomaXML("test/binding/ex4.xml");
        
        
        if(controllerFirst.isFollowedByAnEndlessLoop(automa))
        {
            System.out.println("CICLO");
        }
    }

    /**
     * Test of getFirstAmbiguousTransitions method, of class ControllerFirst.
     */
    @Test
    public void testGetFirstAmiguousTransitions() throws FileNotFoundException, JAXBException
    {
        SyncAutoma automa = service.ServiceXML.readSyncAutomaXML("test/binding/ex4.xml");
        //ControllerTwin controllerTwin = new ControllerTwin();
        //Automa bad = controllerTwin.getBadTwin(automa, 1);
        //Automa good = controllerTwin.getGoodTwin(bad);
        //SyncAutoma sync = controllerTwin.getSyncTwin(bad, good);
        
        Set <SyncTransition> ambiguous = controllerFirst.getFirstAmbiguousTransitions(automa);
        
        for(SyncTransition t : ambiguous){
            System.out.println(t.getStart().getName());
            System.out.println(t.getEnd().getName());
            System.out.println("\n");
        }
        
    }
    
}
