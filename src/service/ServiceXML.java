/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Automa;
import model.sync.SyncAutoma;

/**
 *
 * @author Samuele Colombo
 */
public class ServiceXML 
{
    /**
     * 
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws JAXBException 
     */
    public static Automa readAutoma(String path) throws FileNotFoundException, JAXBException
    {
        File file = new File(path);
        
        if(file.exists())
        {
            JAXBContext context = JAXBContext.newInstance(Automa.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Automa) unmarshaller.unmarshal(file);
        }
        else
        {
            throw new FileNotFoundException();
        }
    }
    
    /**
     * 
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws JAXBException 
     */
    public static SyncAutoma readSyncAutoma(String path) throws FileNotFoundException, JAXBException
    {
        File file = new File(path);
        
        if(file.exists())
        {
            JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (SyncAutoma) unmarshaller.unmarshal(file);
        }
        else
        {
            throw new FileNotFoundException();
        }
    }
    
    /**
     * 
     * @param automa
     * @param file
     * @throws JAXBException 
     */
    public static void writeAutoma(Automa automa, File file) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(Automa.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(automa, file);
    }
    
    /**
     * 
     * @param automa
     * @throws JAXBException 
     */
    public static void writeAutoma(SyncAutoma automa) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(automa, System.out);
    }
    
    /**
     * 
     * @param automa
     * @param file
     * @throws JAXBException 
     */
    public static void writeSyncAutoma(SyncAutoma automa, File file) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(automa, file);
    }
    
    /**
     * 
     * @param automa
     * @throws JAXBException 
     */
    public static void writeSyncAutoma(SyncAutoma automa) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(SyncAutoma.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(automa, System.out);
    }
}
