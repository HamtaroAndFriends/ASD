package binding;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class Binding 
{
    private Automa automa;
    private State s1, s2, s3, s4;
    private Event e1, e2, e3, e4;
    private Transition t1, t2, t3, t4;
    
    public Binding() 
    {
        s1 = new State("s1");
        s2 = new State("s2");
        s1 = new State("s3");
        s2 = new State("s3");
        
        e1 = new Event(); e1.getRights().add("e1");
        e2 = new Event(); e2.getRights().add("e2");
        e3 = new Event(); e3.getRights().add("e3");
        e4 = new Event(); e4.getRights().add("e4");
        
        t1 = new Transition(s1, s2, e1, true);
        t2 = new Transition(s3, s1, e2, true);
        t3 = new Transition(s4, s2, e3, true);
        t4 = new Transition(s2, s3, e4, true);
        
        automa = new Automa();
        automa.getStates().add(s1);
        automa.getStates().add(s2);
        automa.getStates().add(s3);
        automa.getStates().add(s4);
        automa.getObservables().add(t1);
        automa.getObservables().add(t2);
        automa.getObservables().add(t3);
        automa.getObservables().add(t4);
        
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void fromObjectToXML() throws JAXBException 
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        marshaller.marshal(automa, System.out);
    }
}
