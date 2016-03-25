/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.ControllerTwin;
import controller.js.ControllerJSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.Container;
import model.Transition;
import model.sync.SyncAutoma;
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
public class ServiceSyncTwinSecondTest {
    
    private SyncAutoma syncMenoUno;
    private Automa automa;
    private Set <Transition> ti;
    private ControllerTwin controllerTwin; 
    private Map<Integer, Set<Transition>> transizioniAggiunte = new HashMap<>();


    
    public ServiceSyncTwinSecondTest() throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        automa = (Automa) unmarshaller.unmarshal(file);
        controllerTwin = new ControllerTwin();
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
     * Test of getSyncTwin2 method, of class ServiceSyncTwinSecond.
     */
    @Test
    public void testGetSyncTwin2() throws FileNotFoundException, JAXBException
    {
        Automa bad1 = controllerTwin.getBadTwin(automa, 1);
        Automa bad2 = controllerTwin.getBadTwin(bad1, 2);
        Automa good1 = controllerTwin.getGoodTwin(bad1);
        Container container = new Container();
        container.setAutoma(automa);
        container.getBads().put(1, bad1);
        container.getBads().put(2, bad2);
        SyncAutoma sync = controllerTwin.getSyncTwin(bad1, good1);
        
        
        //Computing syncAutoma of level 2
        int level2 = 2;
        // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
        Automa prevBad2 = container.getBads().computeIfAbsent(level2 - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level2 - 1)));
        // Retrieve or generate the bad twin of level i
        Automa nextBad2 = container.getBads().computeIfAbsent(level2, (a) -> (controllerTwin.getBadTwin(prevBad2, level2)));

        Set<Transition> tPrev = prevBad2.getTransitions();
        Set<Transition> tNext = nextBad2.getTransitions();
        tNext.removeAll(tPrev);//removeAll leva da tNext le transizioni che erano presenti anche in tPrev
        transizioniAggiunte.put(level2, tNext);

        ServiceSyncTwinSecond service = new ServiceSyncTwinSecond();
        SyncAutoma sync2 = service.getSyncTwin2(sync, tNext);
        
        
        
        //Computing syncAutoma of level 3
        int level3 = level2 +1;
        Automa bad3 = controllerTwin.getBadTwin(bad2, 3);
        container.getBads().put(3, bad3);
        // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
        Automa prevBad3 = container.getBads().computeIfAbsent(level3 - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level3 - 1)));
        // Retrieve or generate the bad twin of level i
        Automa nextBad3 = container.getBads().computeIfAbsent(level3, (a) -> (controllerTwin.getBadTwin(prevBad3, level3)));

        tPrev = prevBad3.getTransitions();
        tNext = nextBad3.getTransitions();
        tNext.removeAll(tPrev);//removeAll leva da tNext le transizioni che erano presenti anche in tPrev
        transizioniAggiunte.clear();
        transizioniAggiunte.put(level3, tNext);
        SyncAutoma sync3 = service.getSyncTwin2(sync2, tNext);

        
        
        
                
        ControllerJSON cj = new ControllerJSON();
        cj.createAutomaView(sync3, "test/controller/json");
        
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        marshaller.marshal(sync3, System.out);
        
    }
    
}
