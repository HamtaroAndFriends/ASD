/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
public class Event implements Cloneable
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
    
    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() 
    {
        int hash = 5;
        Collections.sort(this.events);
        hash = 31 * hash + Objects.hashCode(this.events);
        return hash;
    }

    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
        {
            return true;
        }
        
        if (obj == null) 
        {
            return false;
        }
        
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        
        final Event other = (Event) obj;
        
        /*if (!Objects.equals(this.events, other.events))
        {
            return false;
        }*/
        
        // The simplest way
        if(this.events.size() == other.events.size())
        {
            Collections.sort(this.events);
            Collections.sort(other.events);
            
            return Objects.equals(this.events, other.events);
        }
        
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Event event = (Event) super.clone(); 
        event.events = new ArrayList<>(events);
        return event;
    }
}
