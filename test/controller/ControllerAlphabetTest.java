/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
public class ControllerAlphabetTest 
{
    private Automa automa;
    
    public ControllerAlphabetTest() throws JAXBException 
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        this.automa = (Automa) unmarshaller.unmarshal(file);
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
     * Test of isDeterministic method, of class ControllerAlphabet.
     */
    @Test
    public void testIsDeterministic() 
    {
        ControllerAlphabet alphabet = new ControllerAlphabet();
        assertTrue(ControllerAlphabet.isDeterministic(automa));
    }
    
}
