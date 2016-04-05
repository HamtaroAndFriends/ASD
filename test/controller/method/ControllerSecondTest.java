/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.Container;
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
public class ControllerSecondTest 
{
    private Automa automa;
    private ControllerSecond second;
    
    public ControllerSecondTest() throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        automa = (Automa) unmarshaller.unmarshal(file);
        second = new ControllerSecond();
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
     * Test of performSecondMethod method, of class ControllerSecond.
     */
    @Test
    public void testPerformSecondMethod()
    {
        Container container = new Container();
        container.setAutoma(automa);
        
        int result = second.performSecondMethod(container, 3);
        
        System.out.println(result);
    }

    /**
     * Test of isFollowedByAnEndlessLoop method, of class ControllerSecond.
     */
    @Test
    public void testIsFollowedByAnEndlessLoop_SyncAutoma() {
    }

    /**
     * Test of getFirstAmiguousTransitions method, of class ControllerSecond.
     */
    @Test
    public void testGetFirstAmiguousTransitions() {
    }

    /**
     * Test of isFollowedByAnEndlessLoop method, of class ControllerSecond.
     */
    @Test
    public void testIsFollowedByAnEndlessLoop_SyncAutoma_SyncTransition() {
    }
    
}
