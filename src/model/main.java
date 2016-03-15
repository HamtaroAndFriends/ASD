/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerAutoma;
import controller.ControllerTwin;
import controller.generator.ControllerGenerator;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.sync.SyncAutoma;

/**
 *
 * @author Fede
 */
public class main {

    public static void main(String args[]) throws JAXBException
    {
        ControllerTwin controller = new ControllerTwin(); 

        
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ControllerGenerator g = new ControllerGenerator();
        Automa a = g.generateAutoma(2, 2, 10, 0.5, 0.99);
        
        Automa bad1 = controller.getBadTwin(a, 1);
        Automa bad2 = controller.getBadTwin(bad1, 2);
        Automa bad3 = controller.getBadTwin(bad2, 3);
        Automa good3 = controller.getGoodTwin(bad3);
        SyncAutoma sync = controller.getSyncTwin(bad3, good3);
        
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  true);
        marshaller.marshal(sync, System.out);

        /*ArrayList <String> uno = new ArrayList();
        ArrayList <String> due = new ArrayList();
        uno.add("a");
        uno.add("b");
        uno.add("c");
        due.add("a");
        due.add("b");
        due.add("c");
        System.out.println("uno: "+ uno.toString());
        System.out.println("due: "+ due.toString());
        System.out.println("esito: "+ uno.equals(due));*/
    }
}
