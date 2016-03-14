/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerTwin;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.Container;
import model.sync.SyncAutoma;
import model.sync.SyncState;
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
    public void testPerformFirstMethod() 
    {
        Container container = new Container();
        container.setAutoma(automa);
        
        int result = controllerFirst.performFirstMethod(container, 2);
        
        System.out.println(result);
    }

    /**
     * Test of isFollowedByAnEndlessLoop method, of class ControllerFirst.
     */
    @Test
    public void testIsFollowedByAnEndlessLoop_SyncAutoma()
    {
        /*ControllerTwin controllerTwin = new ControllerTwin();
        Automa bad = controllerTwin.getBadTwin(automa, 1);
        Automa good = controllerTwin.getGoodTwin(bad);
        SyncAutoma sync = controllerTwin.getSyncTwin(bad, good);
        
        if(controllerFirst.isFollowedByAnEndlessLoop(sync))
        {
            System.out.println("Failed! (tested only on example1)");
        }*/
    }

    /**
     * Test of getFirstAmiguousTransitions method, of class ControllerFirst.
     */
    @Test
    public void testGetFirstAmiguousTransitions()
    {
        /*ControllerTwin controllerTwin = new ControllerTwin();
        Automa bad = controllerTwin.getBadTwin(automa, 1);
        Automa good = controllerTwin.getGoodTwin(bad);
        SyncAutoma sync = controllerTwin.getSyncTwin(bad, good);
        
        Set <SyncTransition> amgiguous = controllerFirst.getFirstAmiguousTransitions(sync, sync.getInitial());
        
        if(!amgiguous.isEmpty())
        {
            fail("Failed! (tested only on example1)");
        }*/
    }
    
}
