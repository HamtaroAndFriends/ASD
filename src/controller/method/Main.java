/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerAutoma;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.Container;

/**
 *
 * @author Andrea
 */
public class Main {
    public static void main(String args[]) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("test/binding/example1.xml");
        Automa automa;
        ControllerFirst controllerFirst;
        ControllerAutoma ca=new ControllerAutoma();
        automa = (Automa) unmarshaller.unmarshal(file);
        controllerFirst = new ControllerFirst();
       // System.out.println(ca.isVivo(automa));
       ControllerFirst cf=new ControllerFirst();
       ControllerSecond cs=new ControllerSecond();
       Container container = new Container();
       container.setAutoma(automa);
       //System.out.println(cf.performFirstMethod(container, 4));
       System.out.println(cs.performSecondMethod(container, 4));
    }
        
}
