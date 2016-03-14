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

/**
 *
 * @author Andrea
 */
public class main {
    public static void main(String args[]) throws JAXBException{
        
         JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example3.xml");
        Automa automa = (Automa) unmarshaller.unmarshal(file);
        ControllerAutoma ca=new ControllerAutoma();
        boolean prova=ca.isVivo(automa);
        System.out.println(prova);
    }
}
