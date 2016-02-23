/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The definition of the {@link Event} class.
 * 
 * @author Samuele Colombo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Event 
{
    /*
    *
    */
    @XmlElementWrapper
    @XmlElement
    private List <String> rights;
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private List <String> faults;

    /**
     * 
     */
    public Event() 
    {
        this.rights = new ArrayList <> ();
        this.faults = new ArrayList <> ();
    }

    /**
     * 
     * @param events 
     */
    public Event(List<String> events) 
    {
        this.rights = events;
    }

    /**
     * 
     * @return 
     */
    public List<String> getRights() 
    {
        return rights;
    }

    /**
     * 
     * @param rights 
     */
    public void setRights(List<String> rights) 
    {
        this.rights = rights;
    }
    
    
    @XmlAttribute
    public boolean isFault()
    {
        return (faults.isEmpty());
    }
    
}
