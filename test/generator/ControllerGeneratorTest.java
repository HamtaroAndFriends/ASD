/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import controller.generator.ControllerGenerator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import model.Automa;
import model.Event;
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
 * @author Samuele Colombo
 */
public class ControllerGeneratorTest 
{
    private ControllerGenerator generator;
    
    public ControllerGeneratorTest() 
    {
        generator = new ControllerGenerator();
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
     * Test of generateAutoma method, of class ControllerGenerator.
     * @throws javax.xml.bind.JAXBException
     */
    @Test
    public void testGenerateAutoma() throws JAXBException 
    {
        Automa automa = generator.generateAutoma(30, 50, 100, 0.99, 0.80);
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        marshaller.marshal(automa, System.out);
    }

    /**
     * Test of generateStates method, of class ControllerGenerator.
     * @throws javax.xml.bind.JAXBException
     */
    @Test
    public void testGenerateStates() throws JAXBException
    {
        List <State> states = generator.generateStates(100);
        JAXBContext context = JAXBContext.newInstance(State.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        
        for(State s : states)
        {
            //marshaller.marshal(s, System.out);
        }
        
    }

    /**
     * Test of generateTransitions method, of class ControllerGenerator.
     * @throws javax.xml.bind.JAXBException
     */
    @Test
    public void testGenerateTransitions() throws JAXBException
    {
        List <State> states = generator.generateStates(100);
        List <Event> events = generator.generateEvents(100);
        List <Transition> transitions = generator.generateTransitions(states, events, 100, 0.99, 0.80);
        JAXBContext context = JAXBContext.newInstance(Transition.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        
        for(Transition t : transitions)
        {
            //marshaller.marshal(t, System.out);
        }
    }

    /**
     * Test of generateEvents method, of class ControllerGenerator.
     * @throws javax.xml.bind.JAXBException
     */
    @Test
    public void testGenerateEvents() throws JAXBException 
    {
        List <Event> events = generator.generateEvents(100);
        JAXBContext context = JAXBContext.newInstance(Event.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        
        for(Event e : events)
        {
            //marshaller.marshal(e, System.out);
        }
    }
    
}
