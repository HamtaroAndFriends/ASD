/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twin;

import controller.ControllerTwin;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
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
public class TwinTest {
    
    private Automa automa;
    private ControllerTwin controller;
    
    public TwinTest() throws JAXBException
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testBadTwin1() throws PropertyException, JAXBException 
    {
        Automa result = controller.getBadTwinOne(automa);
        
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        marshaller.marshal(result, System.out);
        
    }
}
