package binding;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
public class BindingTest 
{
    private Automa automa;
    private State sa, sb, sc, sd, se, sf;
    private Event a, b, c, d;
    private Transition tab, tac, tba, tbd, tcf, tce, tdd, tef, tea, tff;
    
    public BindingTest() 
    {
        sa = new State("A");
        sb = new State("B");
        sc = new State("C");
        sd = new State("D");
        se = new State("E");
        sf = new State("F");
        
        a = new Event(); a.getEvents().add("a");
        b = new Event(); b.getEvents().add("b");
        c = new Event(); c.getEvents().add("c");
        d = new Event(); d.getEvents().add("d");
        
        tab = new Transition(sa, sb, b, false, true);
        tac = new Transition(sa, sc, a, false, true);
        tba = new Transition(sb, sa, new Event(), false, false);
        tbd = new Transition(sb, sd, new Event(), false, false);
        tcf = new Transition(sc, sf, new Event(), false, false);
        tce = new Transition(sc, se, d, false, true);
        tdd = new Transition(sd, sd, c, false, true);
        tef = new Transition(se, sf, new Event(), true, false);
        tea = new Transition(se, sa, new Event(), false, false);
        tff = new Transition(sf, sf, c, false, true);
        
        
        automa = new Automa();
        automa.setInitial(sa);
        automa.getStates().add(sa);
        automa.getStates().add(sb);
        automa.getStates().add(sc);
        automa.getStates().add(sd);
        automa.getStates().add(se);
        automa.getStates().add(sf);
        automa.getTransitions().add(tab);
        automa.getTransitions().add(tac);
        automa.getTransitions().add(tba);
        automa.getTransitions().add(tbd);
        automa.getTransitions().add(tcf);
        automa.getTransitions().add(tce);
        automa.getTransitions().add(tdd);
        automa.getTransitions().add(tef);
        automa.getTransitions().add(tea);
        automa.getTransitions().add(tff);

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
    
    @Test
    public void fromXMLToObject() throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        Automa result = (Automa) unmarshaller.unmarshal(file);
    }
}
