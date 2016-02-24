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
    private List<String> events;

    /**
     * 
     */
    public Event() 
    {
        this.events = new ArrayList <> ();
    }

    /**
     * 
     * @param event 
     */
    public Event(String event)
    {
        this.events = new ArrayList <> ();
        this.events.add(event);
    }
    
    /**
     * 
     * @return 
     */
    public List<String> getEvents() 
    {
        return events;
    }

    /**
     * 
     * @param events 
     */
    public void setEvents(List<String> events) 
    {
        this.events = events;
    }
    
    
}
