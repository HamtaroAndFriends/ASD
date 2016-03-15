/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.State;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fede
 */
public class ControllerReachableTest {
    
    public ControllerReachableTest() {
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
     * Test of getReachable method, of class ControllerReachable.
     */
    @Test
    public void testGetReachable() throws JAXBException {
        
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        Automa result = (Automa) unmarshaller.unmarshal(file);
        
        System.out.println("getReachable");
        Automa evil = result;
        ControllerReachable instance = new ControllerReachable();
        Set<State> stateResult = instance.getReachable(evil);
        // TODO review the generated test code and remove the default call to fail.
        
        JAXBContext context2 = JAXBContext.newInstance(State.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        for(State s: stateResult)
        {
            marshaller.marshal(s, System.out);
        }
    }
    
}
